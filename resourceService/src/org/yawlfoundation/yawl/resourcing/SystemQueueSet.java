package org.yawlfoundation.yawl.resourcing;

import org.jdom.Element;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.resourcing.datastore.WorkItemCache;
import org.yawlfoundation.yawl.resourcing.datastore.eventlog.EventLogger;
import org.yawlfoundation.yawl.resourcing.resource.Participant;
import org.yawlfoundation.yawl.util.JDOMUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A repository of system queues used to distribute workitems
 * 
 * @author limingyao v0.1, 16/11/2015
 */

public class SystemQueueSet {
	// auto-distributed queues
	private WorkQueue _qScheduling;  //high priority queue
	private WorkQueue _qWaiting;	//low priority queue
	
	private boolean _persisting;

	public SystemQueueSet(boolean persisting) {
		_persisting = persisting;
	}

	public void setPersisting(boolean persisting) {
		_persisting = persisting;
	}

	public boolean getPersisting() {
		return _persisting;
	}

	public int getQueueSize(int queue) {
		if (isNullQueue(queue))
			return 0;
		else
			return getQueue(queue).getQueueSize();
	}

	private boolean isNullQueue(int queue) {
		return getQueue(queue) == null;
	}

	/** instantiates the queue if it is not yet instantiated */
	private void checkQueueExists(int queue) {
		if (isNullQueue(queue)) {
			WorkQueue newQueue = new WorkQueue("system", queue, _persisting);
			switch (queue) {
			case WorkQueue.SCHEDULING:
				_qScheduling = newQueue;
				break;
			case WorkQueue.WAITING:
				_qWaiting = newQueue;
			}
		}
	}

	public WorkQueue getQueue(int queue) {
		switch (queue) {
		case WorkQueue.SCHEDULING:
			return _qScheduling;
		case WorkQueue.WAITING:
			return _qWaiting;
		}
		return null;
	}

	public WorkQueue setQueue(WorkQueue queue) {
		switch (queue.getQueueType()) {
		case WorkQueue.SCHEDULING:
			_qScheduling = queue;
			break;
		case WorkQueue.WAITING:
			_qWaiting = queue;
		}
		return null;
	}

	public void addToQueue(WorkItemRecord wir, int queue) {
		checkQueueExists(queue);
		getQueue(queue).add(wir, true);
	}

	/*
	 * public void addToQueue(WorkItemRecord wir, int queue, boolean log) {
	 * checkQueueExists(queue) ; getQueue(queue).add(wir, log); }
	 * 
	 * 
	 * public void addToQueue(int queue, WorkQueue queueToAdd) {
	 * checkQueueExists(queue) ; getQueue(queue).addQueue(queueToAdd); }
	 */

	public void removeFromQueue(WorkItemRecord wir, int queue) {
		if (!isNullQueue(queue))
			getQueue(queue).remove(wir);
	}

	public void removeFromQueue(WorkQueue queueToRemove, int queue) {
		if (!isNullQueue(queue))
			getQueue(queue).removeQueue(queueToRemove);
	}

	public void removeCaseFromQueue(String caseID, int queue) {
		if (!isNullQueue(queue))
			getQueue(queue).removeCase(caseID);
	}

	public void removeFromAllQueues(WorkItemRecord wir) {
		for (int queue = WorkQueue.SCHEDULING; queue <= WorkQueue.WAITING; queue++)
			removeFromQueue(wir, queue);
	}

	public void removeCaseFromAllQueues(String caseID) {
		for (int queue = WorkQueue.SCHEDULING; queue <= WorkQueue.WAITING; queue++)
			removeCaseFromQueue(caseID, queue);
	}

	public void cleanseQueue(WorkItemCache cache, int queue) {
		if (!isNullQueue(queue))
			getQueue(queue).cleanse(cache);
	}

	public Set<WorkItemRecord> getQueuedWorkItems(int queue) {
		if (isNullQueue(queue))
			return null;
		else
			return getQueue(queue).getAll();
	}

	public void purgeQueue(int queue) {
		if (!isNullQueue(queue))
			getQueue(queue).clear();
	}

	public String toXML() {
		StringBuilder xml = new StringBuilder("<SystemQueueSet>");
		for (int queue = WorkQueue.SCHEDULING; queue <= WorkQueue.WAITING; queue++) {
			if (!isNullQueue(queue))
				xml.append(getQueue(queue).toXML());
		}
		xml.append("</SystemQueueSet>");
		return xml.toString();
	}

	public void fromXML(String xml) {
		fromXML(JDOMUtil.stringToElement(xml));
	}

	public void fromXML(Element element) {
		if (element != null) {
			Iterator itr = element.getChildren().iterator();
			while (itr.hasNext()) {
				WorkQueue wq = new WorkQueue();
				wq.fromXML((Element) itr.next());
				setQueue(wq);
			}
		}
	}
}
