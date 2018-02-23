package org.yawlfoundation.yawl.resourcing.allocators;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.resourcing.TaskDispatcher;
import org.yawlfoundation.yawl.resourcing.datastore.eventlog.EventLogger;
import org.yawlfoundation.yawl.resourcing.resource.Participant;
import org.yawlfoundation.yawl.resourcing.calendar.*;


/**
 * A selector of participants to responsible for the workitem
 * 
 * @author limingyao v0.1, 01/12/2015
 */


public class ParticipantPicker extends AbstractAllocator{

	public ParticipantPicker(){}
	
	private static final Logger _log = Logger.getLogger(TaskDispatcher.class) ;
	
	public boolean isAllCritical(Set<Participant> pSet){
		if( pSet!=null && !pSet.isEmpty()){
			for( Participant p : pSet){
				if ( !p.isCritical() )
					return false;
			}
		}
		return true;
	}
	
	public boolean isAllFlexible(Set<Participant> pSet){
		if( pSet!=null && !pSet.isEmpty()){
			for( Participant p : pSet){
				if ( !p.isFlexible() )
					return false;
			}
		}
		return true;
	}
	
	public void excludeFlexibleOnes(Set<Participant> pSet){
		if( pSet!=null && !pSet.isEmpty()){
			
			Iterator<Participant> iter = pSet.iterator();
			while(iter.hasNext()){
				Participant p = iter.next();
				if( p.isFlexible() ){
					iter.remove();
				}
			}
		}
	}
	
	public void excludeCriticalOnes(Set<Participant> pSet){
		if( pSet!=null && !pSet.isEmpty()){
			
			Iterator<Participant> iter = pSet.iterator();
			while(iter.hasNext()){
				Participant p = iter.next();
				if( p.isCritical() ){
					iter.remove();
					System.out.println("excludecritical: " + p.getFullName());
				}
			}
		}
	}
	
	public Participant chooseOne(WorkItemRecord wir, Set<Participant> pSet){
		Participant choice = null;
		
		if( pSet!=null && !pSet.isEmpty()){
			_log.info("The workitem " + wir.getID() + "'s distribution set includes " + getParticipantAsString(wir, pSet));
			
			double best = Double.MAX_VALUE;
			long bestHistory = Long.MAX_VALUE;
			long minWorkload = Long.MAX_VALUE;
			
			for( Participant p : pSet){
				/*double ability = calAbility(p,wir);
				if ( ability >= 0 && ability < best){
					choice = p;
					best = ability;
				}*/
				long history = getHistoryStartDuration(p, wir);
				long workload = p.calWorkload();
				if( minWorkload == 0){
					if(workload == 0){
						if (history != 0 && (bestHistory == 0 || history < bestHistory)){
							bestHistory = history;
							choice = p;
						}
					}
				}
				else { // minWorkload != 0
					if( workload == 0){
						minWorkload = 0;
						bestHistory = history;
						choice = p;
					}
					else if( history != 0 && bestHistory != 0){						
						double ability = calAbility(history, workload);
						if( ability >= 0 && ability < best){
							bestHistory = history; minWorkload = workload;
							best = ability;
							choice = p;
						}
					}
					else { // workload != 0 && (history==0 || bestHistory==0)
						if( workload < minWorkload){
							minWorkload = workload;
							bestHistory = history;
							choice = p;
						}
					}
				}
			}
		}
		
		return choice;
	}
	
	//Refer to Paper(Node load model)
	private double calAbility(Participant p, WorkItemRecord wir){
		long history = getHistoryStartDuration(p, wir);
		long workload = p.calWorkload();
		 
		double hCoe = 0.3;    //historical ability coefficient
		double wCoe = 0.7;    //Current workload coefficient
		
		return ((double)history * hCoe + (double)workload * wCoe );
		//if (workload == 0 || history == 0)
		//	return 0;
		//return ((double)workload / (double)history);
	}
	
	private double calAbility(long history, long workload){
		double hCoe = 0.3;    //historical ability coefficient
		double wCoe = 0.7;    //Current workload coefficient
		
		return ((double)history * hCoe + (double)workload * wCoe );
	}
	
	private String getParticipantAsString(WorkItemRecord wir, Set<Participant> pSet){
		String ret = "";
		if( pSet != null){
			for( Participant p : pSet){
				Double ability = calAbility(p,wir);
				ret = ret + p.getName() + ":" + ability.toString() + "; ";
			}
		}
		return ret;
	}
/*
	private long getHistoryStartDuration(Participant p, WorkItemRecord wir){
		Map<String, Long> durationMap = getAvgDurations(
                EventLogger.event.start, EventLogger.event.complete, wir);
        
        if (durationMap.containsKey(p.getID())) {
        	return durationMap.get(p.getID());
        }
        else {
        	return 0;
        }
	}
  */
		private long getHistoryStartDuration(Participant p, WorkItemRecord wir){
			Map<String, Long> durationMap = getAvgDurations(
	                EventLogger.event.start, EventLogger.event.complete, wir);
	        
	        if (durationMap.containsKey(p.getID())) {
	        	return durationMap.get(p.getID());
	        }
	        else {
	        	return 0;
	        }
		}
	@Override
	public Participant performAllocation(Set<Participant> resources,
			WorkItemRecord wir) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO chenyinye: exclude the participants that cannot finish task considering their unavailable time 
	public void excludeUnavailableResource(WorkItemRecord wir, Set<Participant> distributionSet) throws RemoteException {
		ResourceCalendar rcalendar = ResourceCalendar.getInstance();
		// Calendar today = Calendar.getInstance();

		// calculate the latest finish time for the work item
		long latestStartTime = Long.parseLong(wir.getLatestStartTime());
		long executeTime = Long.parseLong(wir.getExecuteTime());
		Calendar calLatestStartTime = Calendar.getInstance();
		calLatestStartTime.setTimeInMillis((latestStartTime)); 
		Calendar calLatestFinishTime = Calendar.getInstance();
		calLatestFinishTime.setTimeInMillis((latestStartTime + executeTime)); 
		
		Iterator iter = distributionSet.iterator();
		while (iter.hasNext()) {
			Participant p = (Participant)iter.next();

			if (p != null) {
				System.out.println("              name is: " + p.getFullName());

				boolean isLate = false;
				boolean isToday = true;

				/*
				Calendar date = new GregorianCalendar(
						today.get(Calendar.YEAR),
						today.get(Calendar.MONTH),
						today.get(Calendar.DATE));
				final Calendar date = new GregorianCalendar(2017, Calendar.JANUARY, 1);
				System.out.println(date.getTime());
				*/
				Calendar date = Calendar.getInstance();
				
				long workload = p.calWorkload(wir.getDelayRate());
				long exeTime = executeTime;
				
				// calculate the finish timepoint
				while (!isLate && workload > 0) {
					long timespan = rcalendar.availableTimeSpanAtDate(p, date, isToday);

					workload -= timespan;

					if (workload < 0) {  // finish within this day
						date.setTimeInMillis(date.getTimeInMillis() + workload);
					} else if (workload == 0) {  // finish when this day ends
						date.setTimeInMillis(rcalendar.getNextWorkingTime(p, date));
					} else {  // unable to finish
						if (isToday) {
							date = new GregorianCalendar(
									date.get(Calendar.YEAR),
									date.get(Calendar.MONTH),
									date.get(Calendar.DATE));
							isToday = false;
						}
						date.add(Calendar.DATE, 1);
					}

					if (date.after(calLatestStartTime))
						isLate = true;
				}

				while (!isLate && exeTime > 0) {
					long timespan = rcalendar.availableTimeSpanAtDate(p, date, isToday);

					exeTime -= timespan;
					if (exeTime < 0) {  // finish within this day
						date.setTimeInMillis(date.getTimeInMillis() + exeTime);
					} else if (exeTime == 0) {  // finish when this day ends
						date.setTimeInMillis(rcalendar.getNextWorkingTime(p, date));
					} else {  // unable to finish
						if (isToday) {
							date = new GregorianCalendar(
									date.get(Calendar.YEAR),
									date.get(Calendar.MONTH),
									date.get(Calendar.DATE));
							isToday = false;
						}
						date.add(Calendar.DATE, 1);
					}

					if (date.after(calLatestFinishTime))
						isLate = true;
				}

				System.out.println("              date is: " + date.getTime());
				System.out.println("latest finish time is: " + calLatestFinishTime.getTime());

				if (isLate) iter.remove();
			}
		}
		
		System.out.println(distributionSet.size());
	}
}
