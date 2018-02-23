package service;

import dao.Application;
import dao.Parameter;
import dao.ParameterDAO;

public class ParamService {
	
	ParameterDAO paramDao = new ParameterDAO();

	public void add(Application app, String name, String value, String type) {
		
		Parameter ins = new Parameter(app, name, value, type);
		
		paramDao.save(ins);
		
	}
	
	public void add(Application app, String name, String type) {
		
		Parameter ins = new Parameter(app, name, type);
		
		paramDao.save(ins);
		
	}

	public Parameter getParam(Integer id) {
		
		return paramDao.findById(id);
	}

}