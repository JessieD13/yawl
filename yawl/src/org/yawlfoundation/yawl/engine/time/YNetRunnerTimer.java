package org.yawlfoundation.yawl.engine.time;

import org.yawlfoundation.yawl.engine.YEngine;
import org.yawlfoundation.yawl.engine.YPersistenceManager;
import org.yawlfoundation.yawl.exceptions.YPersistenceException;

public class YNetRunnerTimer implements YTimedObject {

    private String _ownerID;     		// the owner work item
    private long _endTime ;
    private boolean _persisting ;
    
    public YNetRunnerTimer() {}		// for hibernate

    public YNetRunnerTimer(String workItemID, long msec, boolean persisting) {
        _ownerID = workItemID ;
        _persisting = persisting ;
        _endTime = YTimer.getInstance().schedule(this, msec) ;
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
        return (other instanceof YNetRunnerTimer) &&
                ((getOwnerID() != null) ?
                 getOwnerID().equals(((YNetRunnerTimer) other).getOwnerID()) :
                 super.equals(other));
    }

    public int hashCode() {
        return (getOwnerID() != null) ? getOwnerID().hashCode() : super.hashCode();
    }

	@Override
	public void handleTimerExpiry() {
		// TODO Auto-generated method stub
		System.out.println("Net runner timer expiry!");
		persistThis(false);
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		persistThis(false) ;
	}

}
