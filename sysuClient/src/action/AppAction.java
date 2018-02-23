package action;

import java.util.HashMap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;


import dao.Application;
import dao.Parameter;
import service.AppService;
import service.GetService;
import service.ParamService;
import util.specEntity;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AppAction extends ActionSupport{
	
	private List<Application> apps;
	private List<specEntity> specs;
	
	private String dynamicJson;
	private String appsJson;
	private String appJson;
	
	private String appName;
	private String appUrl;
	private String appDesc;
	
	private String endPoint;
	private String nameSpace;
	private String opName;
	private String soapAction;
	
	private String paramName;
	private String paramType;
		
	private String appid;
	
	private AppService appServ = new AppService();
	private ParamService paramServ = new ParamService();
	
	private Map<String, Object> session = ActionContext.getContext().getSession();
	
	private GetService getService=new GetService();
	
	public String execute(){ return SUCCESS; }
	
	public String available() {
		/*apps = appServ.getAvai((String) session.get("userid"));
		
		/*** for JSON ***/
	/*	JSONArray json = new JSONArray();
		for(Application app: apps) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("appid", app.getId().toString() );
 			m.put("appName", app.getAppName() );
			
			json.put(m);
		}

		appsJson = json.toString();*/
		if((String)session.get("userid") != null){
			String user =  (String)session.get("userid");
			if(user.equals("admin")){
				appsJson=getService.getAvailableServiceList(0); //int userid = 0; 标记管理员
			}
			else{
				appsJson=getService.getAvailableServiceList(Integer.parseInt((String)session.get("userid")));
			}
		}
		
		//appsJson=getService.getAvailableServiceList(Integer.parseInt((String)session.get("userid")));
		/*** END ***/
		System.out.print(appsJson.toString());
		
		//apps.clear();
		apps = getService.parseAppsJson(appsJson.toString());
		
		
		return SUCCESS;
	}

	public String all() {
		//apps = appServ.findAll();
		
		/*** for JSON ***/
	/*	JSONArray json = new JSONArray();
		for(Application app: apps) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("appid", app.getId().toString() );
 			m.put("appName", app.getAppName() );
			m.put("appDesc", app.getAppDesc() );
			
			if(appServ.find(app.getId(), (String) session.get("userid")) != null){
				m.put("available", "true");
			} else {
				m.put("available", "false");
			}
			json.put(m);
		}

		appsJson = json.toString();		
		/*** END ***/
		
		System.out.print((String)session.get("userid")+"!!\n");
		if((String)session.get("userid") != null){
			String user =  (String)session.get("userid");
			if(user.equals("admin")){
				appsJson=getService.getAllService(0); //int userid = 0; 标记管理员
			}
			else{
				appsJson=getService.getAllService(Integer.parseInt((String)session.get("userid")));
			}
		}
		
		//apps.clear();
		apps = getService.parseAppsJson(appsJson.toString());
		
		return SUCCESS;
	}
	
	public String getAllSpec() {
		System.out.print((String)session.get("userid")+"!!\n");
		if((String)session.get("userid") != null){
			String user =  (String)session.get("userid");
			if(user.equals("admin")){
				appsJson=getService.getAllSpec("0"); //int userid = 0; 标记管理员
			}
			else{
				appsJson=getService.getAllSpec((String)session.get("userid"));
			}
		}
		
		//apps.clear();
		specs = getService.parseSpecJson(appsJson.toString());
		
		return SUCCESS;
	}
	
	
	public String addSimpleJson() {
		appServ.addSimpleApp("simple", appName, appUrl, appDesc);
		
		//newAppJson = new JSONObject(app).toString();
		//apps = appServ.findAll();
		
		return SUCCESS;
	}

	public String addSimple() {
		
		appServ.addSimpleApp("simple", appName, appUrl, appDesc);
		
		apps = appServ.findAll();
		return SUCCESS;
	}
	
	public String apply() {
				
	//	appServ.addUha(Integer.parseInt(appid), (String) session.get("userid"));
		
		//apps = appServ.findAll();
		try {
			getService.applyService(Integer.parseInt(appid), Integer.parseInt((String) session.get("userid")));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return SUCCESS;
	}
	
	public String remove() throws NumberFormatException, Exception {
		
		//appServ.deleteUha(Integer.parseInt(appid), (String) session.get("userid"));
		
		getService.deleteService(Integer.parseInt(appid),Integer.parseInt((String) session.get("userid")));
		return SUCCESS;
	}
	
	public String addDynamicJson() {
		
		try {
			JSONObject json = new JSONObject(dynamicJson);
			
			appName = (String) json.get("appName");
			appUrl = (String) json.get("appUrl");
			appDesc = (String) json.get("appDesc");

			appServ.addSimpleApp("webserv", appName, appUrl, appDesc);
			
			Application newApp = appServ.findByName(appName);
			
			paramServ.add(newApp, "EndPoint", (String) json.get("endPoint"), "URI");
			paramServ.add(newApp, "NameSpace", (String) json.get("nameSpace"), "URI");
			paramServ.add(newApp, "OperationName", (String) json.get("opName"), "String");
			paramServ.add(newApp, "SOAPAction", (String) json.get("soapAction"), "URI");

			Iterator<?> it = json.keys();
			int paramNum = (json.length()-7)/2;
			
			while (it.hasNext() && paramNum!= 0) {
                String key = (String) it.next();

                if(key.startsWith("paramName")) {
                	paramName = (String) json.get(key);
                	
                	String[] ss = key.split("_");
                	paramType = (String) json.get("paramType_" + ss[1]);
                	paramServ.add(newApp, paramName, paramType);
                	
                	paramNum--;
                }
			}
			
			//newAppJson = new JSONObject(newApp).toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//apps = appServ.findAll();
		return SUCCESS;
	}
	
	public String addDynamic() {
		
		appServ.addSimpleApp("webserv", appName, appUrl, appDesc);
		
		Application app = appServ.findByName(appName);
		
		paramServ.add(app, "EndPoint", endPoint, "URI");
		paramServ.add(app, "NameSpace", nameSpace, "URI");
		paramServ.add(app, "OperationName", opName, "String");
		paramServ.add(app, "SOAPAction", soapAction, "URI");
		
		//System.out.println("paramType: "+paramType);
		
		paramServ.add(app, paramName, paramType);
		
		apps = appServ.findAll();		
		return SUCCESS;
	}
	
	public String call() {
		
		/*Application app = appServ.getApp(Integer.parseInt(appid));
		
		Map<String, String> m = new HashMap<String, String>();
		
		m.put("type", app.getAppType());
		m.put("name", app.getAppName());
		m.put("desc", app.getAppDesc());
		m.put("url", app.getAppUrl());
		Set<?> params = app.getParameters();
		
		if(params.size() != 0) {
			Map<String, String> paramsMap = new HashMap<String, String>();
			
			for(Object o : params){
				Parameter param = (Parameter) o;
				if(param.getValue() == null)
					paramsMap.put(param.getName(), param.getType());
			}
			String paramJson = new JSONObject(paramsMap).toString();
			m.put("params", paramJson);
		}
		
		*/
		String userid = (String)session.get("userid");
		Map<String,String> map=getService.getCallService(Integer.parseInt(appid), userid);
		appJson = new JSONObject(map).toString();
		return SUCCESS;
	}
	
	/*
	 * getter & setter
	 */
	
	public List<Application> getApps() {
		return apps;
	}

	public void setApps(List<Application> apps) {
		this.apps = apps;
	}
	
	public List<specEntity> getSpecs() {
		return specs;
	}

	public void setSpecs(List<specEntity> specs) {
		this.specs = specs;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public String getAppJson() {
		return appJson;
	}

	public void setAppJson(String appJson) {
		this.appJson = appJson;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getSoapAction() {
		return soapAction;
	}

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getDynamicJson() {
		return dynamicJson;
	}

	public void setDynamicJson(String dynamicJson) {
		this.dynamicJson = dynamicJson;
	}

	public String getAppsJson() {
		return appsJson;
	}

	public void setAppsJson(String appsJson) {
		this.appsJson = appsJson;
	}

}