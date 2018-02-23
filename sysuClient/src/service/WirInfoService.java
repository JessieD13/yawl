package service;

import java.util.List;

import dao.Caseinfo;
import dao.CaseinfoDAO;
import dao.Wirinfo;
import dao.WirinfoDAO;

public class WirInfoService {
	
	private WirinfoDAO WirinfoDAO = new WirinfoDAO();
	private CaseinfoDAO caseinfoDao = new CaseinfoDAO();
	private WorkQueueService workqueueServ = new WorkQueueService();

	public Wirinfo findByItemid(String itemid) {
		Wirinfo info = new Wirinfo();
		
		List<?> list = WirinfoDAO.findByItemid(itemid);
		if( list.size() != 0 ){
			info = (Wirinfo) list.get(0);
		}
		
		return info;
	}

	public void save(String itemid, String location, String region, String latitude,
			String longitude, String appointment, String consuming,
			String delayFactor) {
		
		List<?> list = WirinfoDAO.findByItemid(itemid);
		if(list.size() == 0) {
			
			String caseid = workqueueServ.getWorkItem(itemid).getCaseID();
			Caseinfo caseinfo;
			List<?> list2 = caseinfoDao.findByCaseid(caseid);
			if(list2.size() == 0){
				caseinfo = new Caseinfo(caseid);
				caseinfoDao.save(caseinfo);
			} else {
				caseinfo = (Caseinfo) list2.get(0);
			}
			
			Wirinfo info = new Wirinfo(caseinfo, itemid, location,
					Double.parseDouble(latitude),
					Double.parseDouble(longitude),
					region,
					Double.parseDouble(delayFactor),
					appointment, 
					Double.parseDouble(consuming)
					);
			
			WirinfoDAO.save(info);
		
		} else {
			
			Wirinfo info = (Wirinfo) list.get(0);
			
			info.setItemid(itemid);
			info.setLocation(location);
			info.setRegion(region);
			info.setLatitude(Double.parseDouble(latitude));
			info.setLongitude(Double.parseDouble(longitude));
			info.setAppointmentTime(appointment);
			info.setConsuming(Double.parseDouble(consuming));
			info.setDelayFactor(Double.parseDouble(delayFactor));
			
			WirinfoDAO.attachDirty(info);
		}
		

		
	}

}
