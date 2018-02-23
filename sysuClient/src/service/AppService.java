package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dao.Application;
import dao.ApplicationDAO;
import dao.User;
import dao.UserDAO;
import dao.UserHasApplication;
import dao.UserHasApplicationDAO;
import dao.UserHasApplicationId;

public class AppService {
	
	UserDAO userDao = new UserDAO();
	ApplicationDAO appDao = new ApplicationDAO();
	UserHasApplicationDAO uhaDao = new UserHasApplicationDAO();
	
	@SuppressWarnings("unchecked")
	public List<Application> findAll() {
		
		return (List<Application>) appDao.findAll();
	}
	
	public void addSimpleApp(String type, String name, String url, String desc) {
				
		Application ins= new Application();
		ins.setAppName(name);
		ins.setAppType(type);
		ins.setAppUrl(url);
		ins.setAppDesc(desc);
		
		appDao.save(ins);
	}
	
	public UserHasApplication find(Integer appid, String userid) {
		
		User user = userDao.findById(userid);
		Application app = appDao.findById(appid);
		
		UserHasApplicationId uhaId = new UserHasApplicationId(user, app);
		
		return uhaDao.findById(uhaId);
	}
	
	public Application findByName(String appName) {
		return (Application) appDao.findByAppName(appName).get(0);
	}
	
	public List<Application> getAvai(String userid) {
		List<Application> apps = new ArrayList<Application>();
		
		User u = userDao.findById(userid);
		
		if(u != null) {
			Set<?> uhas = u.getUserHasApplications();
			
			for(Object o: uhas) {
				UserHasApplication uha = (UserHasApplication) o;
				apps.add(uha.getId().getApplication() );
			}
		}
		
		return apps;
	}
	
	public void addUha(Integer appid, String userid) {
		User user = userDao.findById(userid);
		
/*		if(user == null) {
			User ins = new User();
			ins.setId(userid);
			userDao.save(ins);
			user = userDao.findById(userid);
		}
		*/
		Application app = appDao.findById(appid);
		
		UserHasApplicationId uhaId = new UserHasApplicationId(user, app);
		
		uhaDao.save(new UserHasApplication(uhaId));
	}
	
	public void deleteUha(Integer appid, String userid) {
		User user = userDao.findById(userid);
		Application app = appDao.findById(appid);
		UserHasApplicationId uhaId = new UserHasApplicationId(user, app);
		UserHasApplication uha = uhaDao.findById(uhaId);
		uhaDao.delete(uha);
		
	}

	public Application getApp(Integer appid) {
		
		return appDao.findById(appid);
	}
	
}