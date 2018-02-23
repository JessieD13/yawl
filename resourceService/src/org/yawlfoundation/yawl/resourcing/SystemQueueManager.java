package org.yawlfoundation.yawl.resourcing;

import java.util.HashSet;

import org.apache.log4j.Logger;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.resourcing.resource.Participant;

/**
 * @author limingyao v0.1, 16/11/2015
 */

public class SystemQueueManager {

	// Singleton
	private static SystemQueueManager _me;

	private SystemQueueSet _sqSet;
	private boolean _isScheduling;
	//private int _pendingQueueNum;

	private static final Logger _log = Logger.getLogger(TaskDispatcher.class);
	
	private SystemQueueManager(boolean isScheduling, int routeTo) {
		_isScheduling = isScheduling;
		//_pendingQueueNum = routeTo;
	}

	public static SystemQueueManager getInstance() {
		if (_me == null)
			_me = new SystemQueueManager(false, WorkQueue.SCHEDULING);
		return _me;
	}
	
	public void setIsScheduling(boolean isScheduling) {
		_isScheduling = isScheduling;
	}

	public boolean getIsScheduling() {
		return _isScheduling;
	}

	/*public void setPendingQueueNum(int routeTo) {
		_pendingQueueNum = routeTo;
	}

	public int getPendingQueueNum() {
		return _pendingQueueNum;
	}
	
	public void swapPendingQueue(int queue) {
		if (queue == WorkQueue.SCHEDULING) {
			setPendingQueueNum(WorkQueue.WAITING);
		} else {
			setPendingQueueNum(WorkQueue.SCHEDULING);
		}
	}
	
	public void addToWaiting(WorkItemRecord wir) {
		//int queue = getPendingQueueNum();
		_sqSet.addToQueue(wir, queue);
		
		_log.info("The workitem " + wir.getID() + " is added to queue " 
				+ WorkQueue.getQueueName(queue));
	}*/
	
	public void createWorkQueues(boolean persisting) {
		_sqSet = new SystemQueueSet(persisting);
	}
	
	public SystemQueueSet getWorkQueues() {
		//refreshQueues();
		return _sqSet;
	}	
	
	public void addToScheduling(WorkItemRecord wir){
		_sqSet.addToQueue(wir, WorkQueue.SCHEDULING);
		_log.info("The workitem " + wir.getID() + " is added to system scheduling queue.");
		
	}
	
	public void addToWaiting(WorkItemRecord wir){
		_sqSet.addToQueue(wir, WorkQueue.WAITING);
		_log.info("The workitem " + wir.getID() + " is added to system waiting queue.");
	}
	
	public void removeCaseFromAllQueues(String caseID) {
		_sqSet.removeCaseFromAllQueues(caseID);
	}
	
	public void removeFromAllQueues(WorkItemRecord wir) {
		_sqSet.removeFromQueue(wir, WorkQueue.SCHEDULING);
		_sqSet.removeFromQueue(wir, WorkQueue.WAITING);
	}
	
	public void removeFromQueue(WorkItemRecord wir, int queue){
		_sqSet.removeFromQueue(wir, queue);
	}
	
	public void purgeQueue(int queue) {
		_sqSet.purgeQueue(queue);
	}
	
	public boolean qScheduleNotEmpty() {
		return (_sqSet.getQueueSize(WorkQueue.SCHEDULING) > 0);
	}
	/*private void refreshQueues() {
		if (_sqSet == null)
			_sqSet = SystemQueueSet.getInstance();
		_sqSet.purgeQueue(WorkQueue.SCHEDULING);
		_sqSet.purgeQueue(WorkQueue.WAITING);

	}*/
	
}
