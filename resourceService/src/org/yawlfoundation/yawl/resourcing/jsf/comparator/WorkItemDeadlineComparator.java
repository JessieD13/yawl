/*
 * Copyright (c) 2004-2012 The YAWL Foundation. All rights reserved.
 * The YAWL Foundation is a collaboration of individuals and
 * organisations who are committed to improving workflow technology.
 *
 * This file is part of YAWL. YAWL is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * YAWL is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with YAWL. If not, see <http://www.gnu.org/licenses/>.
 */

package org.yawlfoundation.yawl.resourcing.jsf.comparator;

import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

import java.util.Comparator;

/**
 * Allows Workitems to be sorted on enablement time for display in age order in workqueues
 *
 * Author: Michael Adams
 * Creation Date: 15/01/2008
 */
public class WorkItemDeadlineComparator implements Comparator<WorkItemRecord> {

    /*
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(WorkItemRecord wir1, WorkItemRecord wir2)	{

        // if one object is null, ignore it and return the other as having precedence
        if (wir1 == null) return -1;
        if (wir2 == null) return 1;

        String status1 = wir1.getResourceStatus();
        String status2 = wir2.getResourceStatus();
        if( status1.equals(WorkItemRecord.statusResourceOffered) ||
        		status1.equals(WorkItemRecord.statusResourceAllocated)){
	        // compare enablement times
	        long wir1LST = StringToLong(wir1.getLatestStartTime());
	        long wir2LST = StringToLong(wir2.getLatestStartTime());
	        
            int result = getDifference(wir1LST, wir1LST);

            // if identical enable times, sort on caseid
            if (result == 0)
                return compareCaseIDs(wir1, wir2);
            else
                return result;  
	        
        }
        
        else {	//(status1.equals(WorkItemRecord.statusResourceStarted))
        	long wir1LFT = StringToLong(wir1.getLatestStartTime()+wir1.getExecuteTime());
	        long wir2LFT = StringToLong(wir2.getLatestStartTime()+wir2.getExecuteTime());
	        
            int result = getDifference(wir1LFT, wir1LFT);

            // if identical enable times, sort on caseid
            if (result == 0)
                return compareCaseIDs(wir1, wir2);
            else
                return result;  
        }
	}


    /** if there's a problem comparing enablement times, as a secondary choice
     *  compare on caseids (loose assumption: caseids get allocated in chronological
     *  order - which is not *always* true)
     */
    private int compareCaseIDs(WorkItemRecord wir1, WorkItemRecord wir2) {
        String caseID1 = wir1.getCaseID();
        String caseID2 = wir2.getCaseID();
        int result = caseID1.compareTo(caseID2);

        // if identical enablement times *and* caseid's, make an arbitrary decision
        if (result == 0) result = 1;

        return result;
    }


    private long StringToLong(String s) {
        try {
            return new Long(s);
        }
        catch (NumberFormatException nfe) {
            return 0;
        }
    }


    private int getDifference(long a, long b) {
        long difference = a - b;

        // guard against integer overrun
        if (difference > Integer.MAX_VALUE)
            difference = 1;
        else if (difference < Integer.MIN_VALUE)
            difference = -1;

        return (int) difference;
    }

}
