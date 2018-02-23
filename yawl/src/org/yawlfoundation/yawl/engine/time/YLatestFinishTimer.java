package org.yawlfoundation.yawl.engine.time;

import org.yawlfoundation.yawl.engine.YEngine;
import org.yawlfoundation.yawl.engine.YExpiryTask;
import org.yawlfoundation.yawl.engine.YPersistenceManager;
import org.yawlfoundation.yawl.engine.YWorkItem;
import org.yawlfoundation.yawl.engine.YWorkItemStatus;
import org.yawlfoundation.yawl.exceptions.YPersistenceException;

import javax.xml.datatype.Duration;
import java.util.Date;
import java.util.Set;

public class YLatestFinishTimer implements YTimedObject {

    private String _ownerID;     		// the owner work item
    private long _endTime ;
    private boolean _persisting ;

    public YLatestFinishTimer() {}		// for hibernate

    public YLatestFinishTimer(String workItemID, long msec, boolean persisting) {
        _ownerID = workItemID ;
        _persisting = persisting ;
        _endTime = YTimer.getInstance().schedule(this, new Date(msec)) ;
    }


    public String getOwnerID() { return _ownerID; }

    public void setOwnerID(String id) { _ownerID = id; }

    public long getEndTime() { return _endTime; }

    public void setEndTime(long time) { _endTime = time; }

    public void setPersisting(boolean persist) { _persisting = persist; }
    

    public void persistThis(boolean insert) {
        if (_persisting) {
            YPersistenceManager pmgr = YEngine.getPersistenceManager();
            if (pmgr != null) {
                try {
                    boolean localTransaction = pmgr.startTransaction();
                    if (insert) pmgr.storeObjectFromExternal(this);
                    else pmgr.deleteObjectFromExternal(this);
                    if (localTransaction) pmgr.commit();
                }
                catch (YPersistenceException ype) {
                    // handle exc.
                }
            }
        }
    }


    public boolean equals(Object other) {
        return (other instanceof YLatestFinishTimer) &&
                ((getOwnerID() != null) ?
                 getOwnerID().equals(((YLatestFinishTimer) other).getOwnerID()) :
                 super.equals(other));
    }

    public int hashCode() {
        return (getOwnerID() != null) ? getOwnerID().hashCode() : super.hashCode();
    }

            
    public void handleTimerExpiry() {
        YEngine engine = YEngine.getInstance();
        YWorkItem item = engine.getWorkItem(_ownerID) ;
        if (item != null) {
        	//engine.getNetRunner(item).updateTimerState(item.getTask(), State.expired);
        	
        	// limingyao: set the expiry flag be true
        	YExpiryTask  expiryTask =  new YExpiryTask();
        	expiryTask.handleTimerExpiry(item);
        	
            try {
                if (item.getStatus().equals(YWorkItemStatus.statusExecuting)) {
                	engine.getAnnouncer().announceLFTimerExpiryEvent(item);
                	engine.completeWorkItem(item, item.getDataString(), null,
                            YEngine.WorkItemCompletion.Fail) ;
                }
                else {
                	// workitem should only be statusExecuting 
                }
            }
            catch (Exception e) {
                // handle exc.
            }
        }
        persistThis(false) ;                                 // unpersist this timer
    }


    // unpersist this timer when the workitem is cancelled
    public void cancel() {
        persistThis(false) ;
    }

}