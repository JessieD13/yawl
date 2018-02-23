package action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.yawlfoundation.yawl.elements.YAWLServiceReference;

import service.GetService;
import service.ServiceService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ServiceAction extends ActionSupport {
	private ServiceService serviceService = new ServiceService();
	
	private String serviceURI;
	private String serviceName;
	private String servicePwd;
	private String serviceDoc;
	private String selectedSerivce;
	private Set<YAWLServiceReference> serviceReferences;
	
	private String servsJson;
	
	public String execute() { return SUCCESS;	}
	
	/*
	 * Json
	 */
	public String loadRegisteredServs() {
		
		Set<YAWLServiceReference> servs = serviceService.getRegisteredService();
		
		JSONArray ja = new JSONArray();
		
		for(YAWLServiceReference serv : servs) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", serv.getServiceID());
			m.put("name", serv.getServiceName());
			m.put("uri", serv.getURI());
			m.put("doc", serv.getDocumentation());
			
			ja.put(m);
		}
		
		servsJson = ja.toString();
		
		return SUCCESS;
	}
	
	public String registered() {
		serviceReferences = serviceService.getRegisteredService();
		return "success";
	}
	
	public String register() {

		System.out.println(serviceURI+","+ serviceName+","+ servicePwd+","+ serviceDoc);
		String result = serviceService.addRegisteredService(serviceURI, serviceName, servicePwd, serviceDoc);
		System.out.println(result);
		
		if(result.startsWith("<failure>") == false){
			//注册服务到服务管理中心
			GetService gs = new GetService();
			Map<String, Object> session = ActionContext.getContext().getSession();
			int userId = Integer.parseInt((String)session.get("userid"));
			try {
				gs.registerService(userId, serviceName, serviceURI, serviceDoc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Set<YAWLServiceReference> servs = serviceService.getRegisteredService();
		
		JSONArray ja = new JSONArray();
		
		for(YAWLServiceReference serv : servs) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", serv.getServiceID());
			m.put("name", serv.getServiceName());
			m.put("uri", serv.getURI());
			m.put("doc", serv.getDocumentation());
			
			ja.put(m);
		}
		
		servsJson = ja.toString();
		
		//serviceReferences = serviceService.getRegisteredService();
		
		return SUCCESS;
	}
	
	public String remove() {
		//服务管理中心同时删除该服务
		GetService gs = new GetService();
		try {
			gs.removeService(Integer.parseInt(selectedSerivce));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serviceService.removeRegisteredService(selectedSerivce);
		Set<YAWLServiceReference> servs = serviceService.getRegisteredService();
		
		JSONArray ja = new JSONArray();
		
		for(YAWLServiceReference serv : servs) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", serv.getServiceID());
			m.put("name", serv.getServiceName());
			m.put("uri", serv.getURI());
			m.put("doc", serv.getDocumentation());
			
			ja.put(m);
		}
		
		servsJson = ja.toString();
		
		//serviceReferences = serviceService.getRegisteredService();
		return SUCCESS;
	}
	
	/**getter setter**/

	public String getServiceURI() {
		return serviceURI;
	}

	public void setServiceURI(String serviceURI) {
		this.serviceURI = serviceURI;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServicePwd() {
		return servicePwd;
	}

	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}

	public String getServiceDoc() {
		return serviceDoc;
	}

	public void setServiceDoc(String serviceDoc) {
		this.serviceDoc = serviceDoc;
	}

	public Set<YAWLServiceReference> getServiceReferences() {
		return serviceReferences;
	}

	public void setServiceReferences(Set<YAWLServiceReference> serviceReferences) {
		this.serviceReferences = serviceReferences;
	}

	public String getSelectedSerivce() {
		return selectedSerivce;
	}

	public void setSelectedSerivce(String selectedSerivce) {
		this.selectedSerivce = selectedSerivce;
	}

	public String getServsJson() {
		return servsJson;
	}

	public void setServsJson(String servsJson) {
		this.servsJson = servsJson;
	}
	
}
