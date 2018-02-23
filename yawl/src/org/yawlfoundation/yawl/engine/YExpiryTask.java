package org.yawlfoundation.yawl.engine;

import org.yawlfoundation.yawl.engine.YEngine;
import org.yawlfoundation.yawl.engine.YPersistenceManager;
import org.yawlfoundation.yawl.engine.YWorkItem;
import org.yawlfoundation.yawl.engine.YWorkItemStatus;
import org.yawlfoundation.yawl.exceptions.YPersistenceException;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

public class YExpiryTask  {

    private String itemID;     		// the unique id of task(specID:taskID)
    //private long _expiryTime ;
    private boolean persisting ;

    public YExpiryTask() {}		// for hibernate

    public YExpiryTask(String itemID, boolean persisting) {
    	this.itemID = itemID ;
        this.persisting = persisting ;
        //_expiryTime = YTimer.getInstance().schedule(this, new Date(msec)) ;
    }


    public String getItemID() { return itemID; }

    public void setItemID(String id) { itemID = id; }

    //public long getEndTime() { return _endTime; }

    //public void setEndTime(long time) { _endTime = time; }

    public void setPersisting(boolean persist) { persisting = persist; }
    

    public void persistThis(boolean insert) {
        if (persisting) {
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
                catch (Exception ex){
                	// handle MySQLIntegrityConstraintViolationException
                }
            }    
        }
    }


    public boolean equals(Object other) {
        return (other instanceof YExpiryTask) &&
                ((getItemID() != null) ?
                		getItemID().equals(((YExpiryTask) other).getItemID()) :
                 super.equals(other));
    }

    public int hashCode() {
        return (getItemID() != null) ? getItemID().hashCode() : super.hashCode();
    }

            
    public void handleTimerExpiry(YWorkItem item) {
        if (item != null) {
            String specID = item.getSpecificationID().getIdentifier();
            String taskID = item.getTaskID();
            itemID = specID + ":" + taskID;
        }
        persistThis(true) ;                                 // persist this expiry task
    }


    // unpersist this timer when the workitem is cancelled
    public void cancel() {
        persistThis(false) ;
    }

}