package org.yawlfoundation.yawl.resourcing.calendar;

import java.util.ArrayList;
import java.util.Calendar;

public class PublicHoliday {
    
	public static ArrayList<Calendar> holidayList = new ArrayList<Calendar>();
	public static int year = Calendar.getInstance().get(Calendar.YEAR);
	
	public PublicHoliday(){
		NewYearsDay();
		MayDay();
		ChristmasDay();
		NationalDay();
	}

    public Calendar NewYearsDay(){
    	Calendar c = Calendar.getInstance();
    	c.set(year, Calendar.JANUARY, 1);
    	holidayList.add(c);
    	return c;
    }
    
    public Calendar MayDay(){
    	Calendar c = Calendar.getInstance();
    	c.set(year, Calendar.MAY, 1);
    	holidayList.add(c);
    	return c;
    }

    public ArrayList<Calendar> NationalDay(){
    	int month = Calendar.SEPTEMBER;
    	Calendar cal = Calendar.getInstance();
    	ArrayList<Calendar> c = new ArrayList<Calendar>();
    	for(int i = 1; i <= 7; i++) {
    		cal.set(year, month, i);
        	holidayList.add(cal);
        	c.add(cal);
    	}
    	return c;
    }
    
    public Calendar ChristmasDay(){
    	Calendar c = Calendar.getInstance();
    	c.set(year, Calendar.NOVEMBER, 25);
    	holidayList.add(c);
    	return c;
    }

    public boolean isHoliday(Calendar date){
        for(Calendar c : holidayList) {
        	if (c.get(Calendar.YEAR) == date.get(Calendar.YEAR)
        			&& c.get(Calendar.MONTH) == date.get(Calendar.MONTH)
        			&& c.get(Calendar.DATE) == date.get(Calendar.DATE)) {
        		return true;
        	}
        }
        return false;
        
        /*judgement of weekend
         * 	Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); 
        if (dayOfWeek == 1 || dayOfWeek == 7){
            return true;
        } else {
            return false;
        }
        */ 


    }

}
