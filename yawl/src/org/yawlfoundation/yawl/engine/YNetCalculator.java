package org.yawlfoundation.yawl.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.yawlfoundation.yawl.elements.YCondition;
import org.yawlfoundation.yawl.elements.YExternalNetElement;
import org.yawlfoundation.yawl.elements.YNet;
import org.yawlfoundation.yawl.elements.YTask;

import cn.edu.sysu.ss.workflow.temporal.TimePoint;
import cn.edu.sysu.ss.workflow.temporal.TimeSpan;

public class YNetCalculator {

	public static void initTemporalStates(String caseId, YNet net, TimePoint start, TimePoint end, double level) {
		Queue<YExternalNetElement> queue = new LinkedList<YExternalNetElement>();
    	
    	for (YExternalNetElement elem : net.getInputCondition().getPostsetElements()) {
    		// it should be a task
    		if (elem instanceof YTask) {
    			YTask task = (YTask)elem;
    			task.setRegularStartTime(start);
    			queue.offer(elem);
    			
    			//limingyao: time adjustment algorithm
    			//task.addExecuteTimeForCases(caseId, task.getMaxExecuteTime().multiply(level).getMillisecond());
    		}
    	}
    	// calculate RST(Regular Start Time)
    	while (!queue.isEmpty()) {
    		YTask cur = (YTask)queue.poll();
    		// BFS, add post task to travel the net
    		// TODO determine the split type to deal with different structure
    		for (YExternalNetElement nextCond : cur.getPostsetElements()) {
    			// it should be a condition
    			if (nextCond instanceof YCondition) {
    				for (YExternalNetElement next : nextCond.getPostsetElements()) {
    					// again, it should be
    					if (next instanceof YTask) {
    						queue.offer(next);
    					}
    				}
    			}
    		}
    		// calculate the possible RST of the current task
    		// TODO determine the join type to deal with different structure
    		TimePoint rst = start;
    		for (YExternalNetElement preCond : cur.getPresetElements()) {
    			if (preCond instanceof YCondition) {
    				for (YExternalNetElement pre : preCond.getPresetElements()) {
    					if (pre instanceof YTask) {
    						YTask preTask = (YTask)pre;
    						TimePoint preRst = preTask.getRegularStartTime();
    						if(preRst != null){					
	    						preRst = preRst.forward(preTask.getMaxExecuteTime().multiply(level));
	    						if (rst.before(preRst)) {
	    							rst = preRst;
	    						}
    						}
    					}
    				}
    			}
    		}
    		// update RST of current task
    		if (cur.getRegularStartTime() == null || cur.getRegularStartTime().before(rst)) {
    			cur.setRegularStartTime(rst);
    			//System.out.println("-----------------Set task " + cur.getName() + "'s rst is " + rst.toString());
    		}
    	}
    	
    	// calculate LST(Latest Start Time)
    	for (YExternalNetElement elem : net.getOutputCondition().getPresetElements()) {
    		// it should be a task
    		if (elem instanceof YTask) {
    			YTask task = (YTask)elem;
    			task.setLatestStartTime(end.backward(task.getMaxExecuteTime().multiply(level)));
    			queue.offer(elem);
    		}
    	}
    	// calculate LST(Latest Start Time)
    	while (!queue.isEmpty()) {
    		YTask cur = (YTask)queue.poll();
    		// BFS
    		// TODO determine the join type to deal with different structure
    		for (YExternalNetElement preCond : cur.getPresetElements()) {
    			// it should be a condition
    			if (preCond instanceof YCondition) {
    				for (YExternalNetElement pre : preCond.getPresetElements()) {
    					// it should be a task, assert?
    					if (pre instanceof YTask) {
    						queue.offer(pre);
    					}
    				}
    			}
    		}
    		// calculate the possible LFT of the current task
    		// TODO determine the split type to deal with different structure
    		TimePoint lst = end;
    		for (YExternalNetElement postCond : cur.getPostsetElements()) {
    			if (postCond instanceof YCondition) {
    				for (YExternalNetElement post : postCond.getPostsetElements()) {
    					if (post instanceof YTask) {
    						YTask postTask = (YTask)post;
    						TimePoint postLst = postTask.getLatestStartTime();
    						if(postLst != null){
	    						postLst = postLst.backward(cur.getMaxExecuteTime().multiply(level));
	    						if (postLst.before(lst)) {
	    							lst = postLst;
	    						}
    						}
    					}
    				}
    			}
    		}
    		// update LFT
    		if (cur.getLatestStartTime() == null || lst.before(cur.getLatestStartTime())) {
    			cur.setLatestStartTime(lst);
    		}
    	}
	}
	
	//TODO: limingyao: time adjustment algorithm
	//@param time<0, ahead of time, >0 overtime;
	public static void refreshTemporalStates(String caseId, YNet net, YTask task, long time){  
		long caseValidTime = net.getSpecification().getCaseValidTime().getMillisecond();
		Set<YExternalNetElement> postElements = task.getPostsetElements();
		Iterator<YExternalNetElement> iter = postElements.iterator();
		YTask firstModifiedTask = null;
		long firstModifiedRST = 0; 
		if(iter.hasNext()){
			firstModifiedTask = ((YTask)iter.next());
			firstModifiedRST = firstModifiedTask.getRegularStartTime().getMillisecond();
		}
		double factor = 1 + (double)time/(double)(caseValidTime - firstModifiedRST);
		
		Queue<YExternalNetElement> queue = new LinkedList<YExternalNetElement>();
    	
    	for (YExternalNetElement elem : net.getInputCondition().getPostsetElements()) {
    		// it should be a task
    		if (elem instanceof YTask) {
    			YTask task1 = (YTask)elem;    			
    			queue.offer(elem);
    		}
    	}
    	// calculate RST(Regular Start Time)
    	while (!queue.isEmpty()) {
    		YTask cur = (YTask)queue.poll();
    		if (cur.getID().equals(firstModifiedTask.getID())){
    			queue.clear();
    			cur.addExecuteTimeForCases(caseId,	(long) (factor* cur.getExecuteTimeForCases().get(caseId)));
    			queue.offer(cur);
    		}
    		// BFS, add post task to travel the net
    		// TODO determine the split type to deal with different structure
    		for (YExternalNetElement nextCond : cur.getPostsetElements()) {
    			// it should be a condition
    			if (nextCond instanceof YCondition) {
    				for (YExternalNetElement next : nextCond.getPostsetElements()) {
    					// again, it should be
    					if (next instanceof YTask) {    						
    						((YTask) next).addExecuteTimeForCases(caseId,	(long) (factor* ((YTask) next).getExecuteTimeForCases().get(caseId)));
    						queue.offer(next);
    					}
    				}
    			}
    		}
    		
    	}
    	calTemporalStates(caseId, net);
	}
	
	private static void calTemporalStates(String caseId, YNet net){
		Queue<YExternalNetElement> queue = new LinkedList<YExternalNetElement>();
    	TimePoint start = new TimePoint();   //TODO:should be the case start time
    	TimePoint end = start.forward(net.getSpecification().getCaseValidTime());
    	for (YExternalNetElement elem : net.getInputCondition().getPostsetElements()) {
    		// it should be a task
    		if (elem instanceof YTask) {
    			YTask task = (YTask)elem;
    			task.setRegularStartTime(start);
    			queue.offer(elem);
    		}
    	}
    	// calculate RST(Regular Start Time)
    	while (!queue.isEmpty()) {
    		YTask cur = (YTask)queue.poll();
    		// BFS, add post task to travel the net
    		// TODO determine the split type to deal with different structure
    		for (YExternalNetElement nextCond : cur.getPostsetElements()) {
    			// it should be a condition
    			if (nextCond instanceof YCondition) {
    				for (YExternalNetElement next : nextCond.getPostsetElements()) {
    					// again, it should be
    					if (next instanceof YTask) {
    						queue.offer(next);
    					}
    				}
    			}
    		}
    		// calculate the possible RST of the current task
    		// TODO determine the join type to deal with different structure
    		TimePoint rst = start;
    		for (YExternalNetElement preCond : cur.getPresetElements()) {
    			if (preCond instanceof YCondition) {
    				for (YExternalNetElement pre : preCond.getPresetElements()) {
    					if (pre instanceof YTask) {
    						YTask preTask = (YTask)pre;
    						TimePoint preRst = preTask.getRegularStartTime();
    						preRst = preRst.forward(new TimeSpan(preTask.getExecuteTimeForCases().get(caseId)));
    						if (rst.before(preRst)) {
    							rst = preRst;
    						}
    					}
    				}
    			}
    		}
    		// update RST of current task
    		if (cur.getRegularStartTime() == null || cur.getRegularStartTime().before(rst)) {
    			cur.setRegularStartTime(rst);
    		}
    	}
    	
    	// calculate LST(Latest Start Time)
    	for (YExternalNetElement elem : net.getOutputCondition().getPresetElements()) {
    		// it should be a task
    		if (elem instanceof YTask) {
    			YTask task = (YTask)elem;
    			task.setLatestStartTime(end.backward(new TimeSpan(task.getExecuteTimeForCases().get(caseId))));
    			queue.offer(elem);
    		}
    	}
    	// calculate LST(Latest Start Time)
    	while (!queue.isEmpty()) {
    		YTask cur = (YTask)queue.poll();
    		// BFS
    		// TODO determine the join type to deal with different structure
    		for (YExternalNetElement preCond : cur.getPresetElements()) {
    			// it should be a condition
    			if (preCond instanceof YCondition) {
    				for (YExternalNetElement pre : preCond.getPresetElements()) {
    					// it should be a task, assert?
    					if (pre instanceof YTask) {
    						queue.offer(pre);
    					}
    				}
    			}
    		}
    		// calculate the possible LFT of the current task
    		// TODO determine the split type to deal with different structure
    		TimePoint lst = end;
    		for (YExternalNetElement postCond : cur.getPostsetElements()) {
    			if (postCond instanceof YCondition) {
    				for (YExternalNetElement post : postCond.getPostsetElements()) {
    					if (post instanceof YTask) {
    						YTask postTask = (YTask)post;
    						TimePoint postLst = postTask.getLatestStartTime();
    						postLst = postLst.backward(new TimeSpan(cur.getExecuteTimeForCases().get(caseId)));
    						if (postLst.before(lst)) {
    							lst = postLst;
    						}
    					}
    				}
    			}
    		}
    		// update LFT
    		if (cur.getLatestStartTime() == null || lst.before(cur.getLatestStartTime())) {
    			cur.setLatestStartTime(lst);
    		}
    	}
	}
}
