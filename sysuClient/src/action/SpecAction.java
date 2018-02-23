package action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.SpecificationData;

import service.GetService;
import service.SpecService;
import util.specEntity;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.Specification;

@SuppressWarnings("serial")
public class SpecAction extends ActionSupport{
	
	private String specsJson;
	private String SpecInfoJson;
	private String selectedSpec;
	private File ywl;
	private String ywlFileName;
	
	private String identifier;
	private String version;
	private String uri;
	
	private SpecService specServ = new SpecService();
	
	private Map<String, Object> session = ActionContext.getContext().getSession();
	
	public String execute(){ return SUCCESS; }
	
/*	public String upload() {
		String result = specServ.upload(ywl, ywlFileName);
		if (result.startsWith("<failure>")) {
			System.out.println(result);
		}
		
		return SUCCESS;
	}*/
	
	public String getMySpec() {
		/*Set<SpecificationData> specs = specServ.getLoadedSpecList();
		
		JSONArray ja = new JSONArray();
				
		for(SpecificationData spec: specs) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("name", spec.getName());
			m.put("uri", spec.getSpecURI());
			m.put("version", spec.getSpecVersion());
			m.put("status", spec.getStatus());
			m.put("documentation", spec.getDocumentation());
			m.put("identifier", spec.getSpecIdentifier());
			
			ja.put(m);
		}*/
		
		/**
		 * 从服务管理中心加载
		 */
		GetService gs = new GetService();
		specsJson = gs.getMySpec((String)session.get("userid"));
		System.out.print("specsJson"+specsJson);
		//specsJson = ja.toString();
		
		return SUCCESS;
	}
	
	public String allSpecsMonitor() {
		//Set<SpecificationData> specs = specServ.getLoadedSpecList();
		
		JSONArray followed = new JSONArray();
		JSONArray unfollowed = new JSONArray();
		
		/**
		 * 从服务管理中心加载
		 */
		GetService gs = new GetService();
		specsJson = gs.loadSpecFromSSH();
		System.out.print("specsJson"+specsJson);
		List<specEntity> specifications = new ArrayList<specEntity>();
		specifications = gs.parseSpecJson(specsJson);
		for(int i = 0; i < specifications.size(); i++){
			Map<String, String> m = new HashMap<String, String>();
			m.put("name", specifications.get(i).getName());
			m.put("uri", specifications.get(i).getUri());
			m.put("version", specifications.get(i).getVersion());
			m.put("documentation", specifications.get(i).getDocumentation());
			m.put("identifier", specifications.get(i).getIdentifier());
			System.out.print((String) session.get("userid")+"lll");
			if(specServ.isFollowed(specifications.get(i).getIdentifier(), (String) session.get("userid")) == true) {
				
				followed.put(m);
				
			} else { unfollowed.put(m); }
		}
		
		
		
		
		/*for(SpecificationData spec: specs) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("name", spec.getName());
			m.put("uri", spec.getSpecURI());
			m.put("version", spec.getSpecVersion());
			m.put("status", spec.getStatus());
			m.put("documentation", spec.getDocumentation());
			m.put("identifier", spec.getSpecIdentifier());
			
			if(specServ.isFollowed(spec.getSpecIdentifier(), (String) session.get("userid")) == true) {
				
				followed.put(m);
				
			} else { unfollowed.put(m); }
	
		}*/
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("followed", followed.toString());
		m.put("unfollowed", unfollowed.toString());
		
		specsJson = new JSONObject(m).toString();
		
		return SUCCESS;
	}
	
	public String follow() {
		
		specServ.addFollow(selectedSpec, (String) session.get("userid"));
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("identifier", selectedSpec);
		
		return SUCCESS;
	}
	
	public String unfollow() {
		
		specServ.deleteFollow(selectedSpec, (String) session.get("userid"));
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("identifier", selectedSpec);
		
		return SUCCESS;
	}
	
	public String unloadSpec() {
		YSpecificationID yid = new YSpecificationID(identifier, version, uri);
		System.out.print(identifier);
		
		String result = specServ.unload(yid);
		if (result.startsWith("<failure>")) {
			System.out.println(result);
		}
		
		Set<SpecificationData> specs = specServ.getLoadedSpecList();
		
		JSONArray ja = new JSONArray();
				
		for(SpecificationData spec: specs) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("name", spec.getName());
			m.put("uri", spec.getSpecURI());
			m.put("version", spec.getSpecVersion());
			m.put("status", spec.getStatus());
			m.put("documentation", spec.getDocumentation());
			m.put("identifier", spec.getSpecIdentifier());
			
			ja.put(m);
		}

		specsJson = ja.toString();
		
		return SUCCESS;
	}
	
	public String getSpecInfo(){
		SpecInfoJson = specServ.getSpecInfo(identifier, version, uri);
		return SUCCESS;
	}
	
	public String downloadLog(){
		SpecInfoJson = specServ.downloadLog(identifier, version, uri);
		return SUCCESS;
	}
	
	/***************************/

	public String getSpecsJson() {
		return specsJson;
	}

	public void setSpecsJson(String specsJson) {
		this.specsJson = specsJson;
	}
	
	
	public String getSpecInfoJson() {
		return SpecInfoJson;
	}

	public void setSpecInfoJson(String specInfoJson) {
		SpecInfoJson = specInfoJson;
	}

	public String getSelectedSpec() {
		return selectedSpec;
	}

	public void setSelectedSpec(String selectedSpec) {
		this.selectedSpec = selectedSpec;
	}

	public File getYwl() {
		return ywl;
	}

	public void setYwl(File ywl) {
		this.ywl = ywl;
	}

	public String getYwlFileName() {
		return ywlFileName;
	}

	public void setYwlFileName(String ywlFileName) {
		this.ywlFileName = ywlFileName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}


}