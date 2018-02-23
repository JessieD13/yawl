package org.yawlfoundation.yawl.resourcing.resource;

import org.yawlfoundation.yawl.util.StringUtil;

import cn.edu.sysu.ss.workflow.temporal.TimePoint;

public class ParticipantPosition {
	
	/******************* properties ********************/
	public TimePoint startTime;
	public TimePoint endTime;
	
	/******************* constructors ********************/
	public ParticipantPosition(){}		// for hibernate
	
	/******************* getter and setter ********************/
	public TimePoint getStartTime() {
		return startTime;
	}
	public void setStartTime(TimePoint startTime) {
		this.startTime = startTime;
	}
	public TimePoint getEndTime() {
		return endTime;
	}
	public void setEndTime(TimePoint endTime) {
		this.endTime = endTime;
	}
	
	public boolean checkConstrain() {
		TimePoint now = new TimePoint();
		if (now.before(endTime) && startTime.before(now))
			return true;
		else
			return false;
	}
	
	public String toXML() {
		StringBuilder xml = new StringBuilder();
		xml.append(String.format("<participantposition>")) ;
        xml.append(StringUtil.wrapEscaped(String.valueOf(startTime.getMillisecond()), "start"));
        xml.append(StringUtil.wrapEscaped(String.valueOf(endTime.getMillisecond()), "end"));
        xml.append("</participantposition>");
        return xml.toString() ;
	}
	
}
