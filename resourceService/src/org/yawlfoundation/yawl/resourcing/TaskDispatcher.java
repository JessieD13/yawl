package org.yawlfoundation.yawl.resourcing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.Date;

import java.rmi.RemoteException;

import javax.persistence.Cache;

import org.apache.log4j.Logger;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.resourcing.allocators.*;
import org.yawlfoundation.yawl.resourcing.interactions.AbstractInteraction;
import org.yawlfoundation.yawl.resourcing.resource.Participant;
import org.yawlfoundation.yawl.resourcing.calendar.ResourceCalendar;

import EDU.oswego.cs.dl.util.concurrent.FJTask.Par;

/**
 * @author limingyao v0.1, 16/11/2015
 */
public class TaskDispatcher implements Runnable{
	
	private static TaskDispatcher _me;
	
	private ResourceManager _rm;
	private SystemQueueManager _sqm;
	private ParticipantPicker _pp;
	
	private static final Logger _log = Logger.getLogger(TaskDispatcher.class) ;

	private long _initialDelay;
	private long _period;
	
	private TaskDispatcher(){
		super();
		_rm = ResourceManager.getInstance();
		_sqm = SystemQueueManager.getInstance();
		_pp = new ParticipantPicker();
		_initialDelay = 30;
		_period = 5;
	}
	
	public static TaskDispatcher getInstance(){
		if(_me == null) _me = new TaskDispatcher();
		return _me;
	}
	
	@Override
	public void run(){
		try {
		//TODO handle the scheduling
		_sqm.setIsScheduling(true);
		
		/*int queueNum = _sqm.getPendingQueueNum();
		swapPendingQueue();*/
		WorkQueue schedule = _sqm.getWorkQueues().getQueue(WorkQueue.SCHEDULING);
		
		
		//Modify if to while
		while( schedule != null && !schedule.isEmpty()){
			List<WorkItemRecord> wirs = schedule.getSortedWorkItem();
			
			for(WorkItemRecord wir : wirs){
				//nextWir = iter.next().getValue();				
				//_log.info("Begin dispatching workitem " + wir.getID());
				if( isReady(wir) ){
					//_log.info("Begin dispatching workitem " + wir.getID());
					doDispatch(wir);
					_sqm.removeFromAllQueues(wir);
				}
				else{
					//_log.info("Add workitem " + wir.getID() + " to waiting queue.");
					_sqm.removeFromQueue(wir, WorkQueue.SCHEDULING);
					_sqm.addToWaiting(wir);
				}
				//s_log.info("Dispatcher workitem " + wir.getID() + " succefully");
			}
			
			WorkQueue wait = _sqm.getWorkQueues().getQueue(WorkQueue.WAITING);
			if(wait!=null && !wait.isEmpty()){
				List<WorkItemRecord> waitWirs = wait.getSortedWorkItem();
				for(WorkItemRecord waitWir: waitWirs){
					if(_sqm.qScheduleNotEmpty())
						break;    //Preemptive
					
					//_log.info("Begin dispatching workitem " + waitWir.getID() + " in waiting queue.");
					doDispatch(waitWir);
					_sqm.removeFromAllQueues(waitWir);
				}
			}
			
			//_sqm.purgeQueue(queueNum);
			
		}
				
		_sqm.setIsScheduling(false);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void dispatch(){
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		
		setDispatchProps();
		
		//schdule after each period with the first time scheduling delay
		service.scheduleAtFixedRate(new TaskDispatcher(), 
				_initialDelay, _period, TimeUnit.SECONDS);
	}
	
	public void setInitialDelay(long iniDelay) { _initialDelay = iniDelay; }
	
	public long getInitialDelay() { return _initialDelay; }
	
	public void setPeriod(long period) { _period = period; }
	
	public long getPeriod() { return _period; }
	
	/*private void swapPendingQueue(){
		int queue = _sqm.getPendingQueueNum();
		_sqm.swapPendingQueue(queue);
	}*/
	
	private void setDispatchProps(){
		InputStream in = getClass().getResourceAsStream("taskDispatcher.properties");
		Properties p = new Properties();
		try{
    		p.load(in);
    		in.close();
    		setInitialDelay(Long.parseLong( p.getProperty("initialDelay") ) );
    		setPeriod(Long.parseLong( p.getProperty("period") ) );
		}catch(IOException el){
			el.printStackTrace();
		}		
	}
	
	private boolean isReady(WorkItemRecord wir){
		int status = getProgressStatus(wir);
		boolean isCritical = wir.isCritical();
		Set<Participant> distributionSet = getDistributionSet(wir);
		
		if(status >=0 )      //late 
			return true;				
		else if(isCritical)  //early and uncritical;
			return false;
		else if(_pp.isAllCritical(distributionSet) && _pp.isAllFlexible(distributionSet))
			//early, critical and has no available participants
			return false;
		else
			//early, critical and has available participants
			return true;
			
	}
	
	private void doDispatch(WorkItemRecord wir) throws RemoteException {
		if( wir != null){
			int status = getProgressStatus(wir);
			
			Set<Participant> distributionSet = getDistributionSet(wir);
			
			if(distributionSet!=null && !distributionSet.isEmpty()){
				
				switch(status){
				case -1: handleEarlyWorkItemDispatch(wir, distributionSet); break;
				case 0: handleNormalWorkItemDispatch(wir, distributionSet); break;
				case 1: handleLateWorkItemDispatch(wir, distributionSet); break; 
				}
			}
			else {
				 _log.warn("Allocation of resource for workitem " + wir.getID() +
                         " resulted in an empty distribution set. The workitem will be" +
                         " passed to an administrator for manual distribution.");

               // put workitem in admin's unoffered queue & DONE
               addToAdminUnofferedQueue(wir);
			}
		}
	}
	
	private Set<Participant> getDistributionSet(WorkItemRecord wir){
		if( wir != null){
			ResourceMap rMap = _rm.getResourceMap(wir) ;
			
			return rMap.getOfferInteraction().getDistributionSet();
		}
		
		return null;
	}
	
	private int getProgressStatus(WorkItemRecord wir){
		return wir.getProgressStatus();
	}
	
	private void addToAdminUnofferedQueue(WorkItemRecord wir) {
        _rm.getWorkItemCache().updateResourceStatus(wir, WorkItemRecord.statusResourceUnoffered);
        ResourceAdministrator.getInstance().addToUnoffered(wir);
    }
	
	private void handleEarlyWorkItemDispatch(WorkItemRecord wir, Set<Participant> distributionSet) throws RemoteException {
		if( !wir.isCritical() && !wir.isEverExpired() ){
			offerToRole(wir, distributionSet);
		}
		else {
			if( !_pp.isAllCritical(distributionSet))
				_pp.excludeCriticalOnes(distributionSet);
			
			System.out.println("after exclude:");
			for (Participant p : distributionSet) {
				System.out.println(p.getFullName());
			}
			// chenyinye
			_pp.excludeUnavailableResource(wir, distributionSet);
					
			Participant choice =  _pp.chooseOne(wir, distributionSet);
			if( choice != null) 
				doAllocate(wir, choice);
		}
	}
	
	private void offerToRole(WorkItemRecord wir, Set<Participant> pSet) throws RemoteException {
		if( pSet!=null && !pSet.isEmpty() ){
			// for each participant in set, place workitem on their offered queue
            for (Participant p : pSet) {
                QueueSet qs = p.getWorkQueues() ;
                if (qs == null) qs = p.createQueueSet(_rm.isPersisting());
                
                _rm.getWorkItemCache().updateResourceStatus(wir,
    					WorkItemRecord.statusResourceOffered);
                qs.addToQueue(wir, WorkQueue.OFFERED);
                _rm.announceModifiedQueue(p.getID()) ;
                
                _rm.getResourceMap(wir).addToOfferedSet(wir, p);
            }
            
            _log.info("The workitem " + wir.getID() + " is offered to "
            		+ pSet.toArray());
		}
	}
	
	private void handleNormalWorkItemDispatch(WorkItemRecord wir, Set<Participant> distributionSet) throws RemoteException {
		//distributionset copy
		Set<Participant> copy_distributionSet = new HashSet<Participant>();
		for(Participant p : distributionSet) {
			copy_distributionSet.add(p);
		}
		
		if( !wir.isCritical() && !wir.isEverExpired() ){
			if( !_pp.isAllCritical(distributionSet))
				_pp.excludeCriticalOnes(distributionSet);
			if( !_pp.isAllFlexible(distributionSet))
				_pp.excludeFlexibleOnes(distributionSet);
			
			System.out.println("after exclude:");
			for (Participant p : distributionSet) {
				System.out.println(p.getFullName());
			}
			
			// chenyinye
			_pp.excludeUnavailableResource(wir, distributionSet);
			//xuyang
			if(distributionSet.size() == 0) {
				distributionSet = copy_distributionSet;
				_pp.excludeUnavailableResource(wir, distributionSet);
			}
			if(distributionSet.size() == 0)
				ResourceAdministrator.getInstance().addToUnoffered(wir);
			
			Participant choice =  _pp.chooseOne(wir, distributionSet);
			if( choice != null) 
				doAllocate(wir, choice);
		}
		else {
			if( !_pp.isAllCritical(distributionSet))
				_pp.excludeCriticalOnes(distributionSet);

			System.out.println("after exclude:");
			for (Participant p : distributionSet) {
				System.out.println(p.getFullName());
			}
			// chenyinye
			_pp.excludeUnavailableResource(wir, distributionSet);
			//xuyang
			if(distributionSet.size() == 0) {
				distributionSet = copy_distributionSet;
				_pp.excludeUnavailableResource(wir, distributionSet);
			}
			if(distributionSet.size() == 0)
				ResourceAdministrator.getInstance().addToUnoffered(wir);
			
			Participant choice =  _pp.chooseOne(wir, distributionSet);
			if( choice != null) 
				doAllocate(wir, choice);
		}
	}
	
	private void handleLateWorkItemDispatch(WorkItemRecord wir, Set<Participant> distributionSet) throws RemoteException {
		//distributionset copy
		Set<Participant> copy_distributionSet = new HashSet<Participant>();
		for(Participant p : distributionSet) {
			copy_distributionSet.add(p);
		}
		
		if( !wir.isCritical() && !wir.isEverExpired() ){
		
			if( !_pp.isAllCritical(distributionSet))
				_pp.excludeCriticalOnes(distributionSet);
			if( !_pp.isAllFlexible(distributionSet))
				_pp.excludeFlexibleOnes(distributionSet);
			
			System.out.println("after exclude:");
			for (Participant p : distributionSet) {
				System.out.println(p.getFullName());
			}
			// chenyinye
			_pp.excludeUnavailableResource(wir, distributionSet);
			//xuyang
			if(distributionSet.size() == 0) {
				distributionSet = copy_distributionSet;
				_pp.excludeUnavailableResource(wir, distributionSet);
			}
			if(distributionSet.size() == 0)
				ResourceAdministrator.getInstance().addToUnoffered(wir);
			
			Participant choice =  _pp.chooseOne(wir, distributionSet);
			if( choice != null) 
				doAllocate(wir, choice);
		}
		else {
			if( !_pp.isAllCritical(distributionSet))
				_pp.excludeCriticalOnes(distributionSet);
			
			System.out.println("after exclude:");
			for (Participant p : distributionSet) {
				System.out.println(p.getFullName());
				copy_distributionSet.remove(p);
			}
			System.out.println("[copy] after exclude:");
			for (Participant p : distributionSet) {
				System.out.println(p.getFullName());
			}
			// chenyinye
			_pp.excludeUnavailableResource(wir, distributionSet);
			//xuyang
			if(distributionSet.size() == 0) {
				distributionSet = copy_distributionSet;
				_pp.excludeUnavailableResource(wir, distributionSet);
			}
			if(distributionSet.size() == 0)
				ResourceAdministrator.getInstance().addToUnoffered(wir);

			Participant choice =  _pp.chooseOne(wir, distributionSet);
			if( choice != null) 
				doStart(wir, choice);
		}
	}
	
	private void doAllocate(WorkItemRecord wir, Participant p){
		Set<Participant> participantSet = new HashSet<Participant>();
		
		if( p != null){
			QueueSet qs = p.getWorkQueues();
			if (qs == null)
				qs = p.createQueueSet(_rm.isPersisting());
			qs.addToQueue(wir, WorkQueue.ALLOCATED);
			_rm.getWorkItemCache().updateResourceStatus(wir,
					WorkItemRecord.statusResourceAllocated);
			
			_log.info("The workitem " + wir.getID() + "(" + getWorkItemPropsAsString(wir) + ")" +
					" is allocated to participant " + p.getName() );
		}
		else{
			_log.warn("The system distributor '"
					+ "' has been unable to allocate workitem '"
					+ wir.getID()
					+ "' to a participant. The workitem has been passed to the "
					+ "administrator's unoffered queue for manual allocation.");
			addToAdminUnofferedQueue(wir);
		}
	}
	
	private void doStart(WorkItemRecord wir, Participant p){
		boolean started = false;
		QueueSet qs = p.getWorkQueues();
		if (qs == null)
			qs = p.createQueueSet(_rm.isPersisting());

		
		started = _rm.startImmediate(p, wir);
		if (!started) {
			qs.addToQueue(wir, WorkQueue.ALLOCATED);
			_rm.getWorkItemCache().updateResourceStatus(wir,
					WorkItemRecord.statusResourceAllocated);
			
			_log.warn("The workitem '"
					+ wir.getID()
					+ "' could not be "
					+ "automatically started. The workitem has been placed on "
					+ "the participant's allocated queue.");			
		}
		else{
			_log.info("The workitem " + wir.getID() + "(" + getWorkItemPropsAsString(wir) + ")" +
					" is successfully started by participant " + p.getName() );
		}
	}
	
	private String getWorkItemPropsAsString(WorkItemRecord wir){
		String ret = "";
		if ( wir.isCritical() || wir.isEverExpired())
			ret += "critical,";
		else ret += "not critical,";
		
		if ( wir.getDelayRate() > 0 )
			ret += "late";
		else ret += "early";
		
		return ret;
	}
}
