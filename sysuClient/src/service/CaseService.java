package service;

import java.util.List;

import dao.Caseinfo;
import dao.CaseinfoDAO;

public class CaseService {
	
	CaseinfoDAO caseinfoDao = new CaseinfoDAO();

	public Caseinfo findbyCaseid(String caseid) {
		List<?> list = caseinfoDao.findByCaseid(caseid);
		
		if(list.size() == 0) return null;
		
		return (Caseinfo) list.get(0); //list的size只能是1或者0
	}

	public void addPriority(String selectedCase, String priority, String string) {
		List<?> list = caseinfoDao.findByCaseid(selectedCase);
		
		if(list.size() == 0) {
			Caseinfo caseinfo = new Caseinfo(selectedCase);
			caseinfo.setPriority(priority);
			caseinfoDao.save(caseinfo);
		} else {
			Caseinfo caseinfo = (Caseinfo) list.get(0);
			caseinfo.setPriority(priority);
			caseinfoDao.attachDirty(caseinfo);
		}
		
	}

	public void addDifficulty(String selectedCase, String difficulty,
			String string) {
		List<?> list = caseinfoDao.findByCaseid(selectedCase);
		
		if(list.size() == 0) {
			Caseinfo caseinfo = new Caseinfo(selectedCase);
			caseinfo.setDifficulty(difficulty);
			caseinfoDao.save(caseinfo);
		} else {
			Caseinfo caseinfo = (Caseinfo) list.get(0);
			caseinfo.setDifficulty(difficulty);
			caseinfoDao.attachDirty(caseinfo);
		}
		
	}

	public void addClientLevel(String selectedCase, String clientLevel,
			String string) {
		List<?> list = caseinfoDao.findByCaseid(selectedCase);
		
		if(list.size() == 0) {
			Caseinfo caseinfo = new Caseinfo(selectedCase);
			caseinfo.setClientLevel(clientLevel);
			caseinfoDao.save(caseinfo);
		} else {
			Caseinfo caseinfo = (Caseinfo) list.get(0);
			caseinfo.setClientLevel(clientLevel);
			caseinfoDao.attachDirty(caseinfo);
		}
		
	}

}
