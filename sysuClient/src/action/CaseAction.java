package action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.SpecificationData;

import service.GetService;
import service.SpecService;
import service.LogService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CaseAction extends ActionSupport {
	
	private SpecService specServ = new SpecService();
	
	private Set<SpecificationData> specs;
	private List<HashMap<String,String>> cases;
	private String selectedSpec;
	private String selectedCase;
	private File ywl;
	private String ywlFileName;
	private boolean isPartialReq;
	
	private String casesJson;
	private String mineCasesJson;
	private String caseid;
	private String caseHistoryJson;
	
	private String caseRunningJson;
	
	private String identifier;
	private String version;
	private String uri;
	
	private LogService logServ = new LogService();
	private Map<String, Object> session = ActionContext.getContext().getSession();
	
	public String execute() { return SUCCESS; }
	
	public String manage() {
		specs = specServ.getLoadedSpecList();
		if (isPartialReq) {
			return "partial";
		} else {
			return "success";
		}
	}
	
	public String launch() {
		specs = specServ.getLoadedSpecList();
		String ids[] = selectedSpec.split(":");
		YSpecificationID selectedID = new YSpecificationID(ids[0], ids[1], "");
		// when launch failure, caseID will be set <failure>
		String caseID = specServ.launchCase(selectedID);
		if (isPartialReq) {
			return "partial";
		} else {
			return "success";
		}
	}
	
	public String launchSpec() {
		YSpecificationID yid = new YSpecificationID(identifier, version, uri);
		String result = specServ.launchCase(yid);
	
		//记录日志到服务管理中心
		GetService gs = new GetService();
		String userid = (String) session.get("userid");
		try {
			gs.launchSpec(userid, identifier, version, uri, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JSONArray ja = new JSONArray();
		
		Set<SpecificationData> specs = specServ.getLoadedSpecList();
		if(specs != null) {
			for(SpecificationData s : specs) {
				List<String> cases = specServ.getRunningCases(s.getID());
				if(cases != null){
					for(String caseid: cases){
						Map<String, String> m = new HashMap<String, String>();
						m.put("id", caseid);
						m.put("specname", s.getName());
						m.put("specversion", s.getSpecVersion());
						ja.put(m);
					}
					
				}
			}
		}
		
		caseRunningJson = ja.toString();
		
		return SUCCESS;
	}
	
	public String loadRunningCases() {
		JSONArray ja = new JSONArray();
		
		Set<SpecificationData> specs = specServ.getLoadedSpecList();
		if(specs != null) {
			for(SpecificationData s : specs) {
				List<String> cases = specServ.getRunningCases(s.getID());
				if(cases != null){
					for(String caseid: cases){
						Map<String, String> m = new HashMap<String, String>();
						m.put("id", caseid);
						m.put("specname", s.getName());
						m.put("specversion", s.getSpecVersion());
						m.put("specuri", s.getSpecURI());
						m.put("specidentifier", s.getID().getIdentifier());
						ja.put(m);
					}
					
				}
			}
		}
		
		caseRunningJson = ja.toString();
		return SUCCESS;
	}
	
	public String cancelCase() {
		String result = specServ.cancelCase(selectedCase);

		System.out.print("\nresult:"+result+"identifier:"+identifier+"version:"+version+"uri:"+uri+"\n");
		
		//距离cancel流程日志到服务管理中心
		GetService gs = new GetService();
		String userid = (String) session.get("userid");
		try {
			gs.cancelSpec(userid, identifier, version, uri, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONArray ja = new JSONArray();
		
		Set<SpecificationData> specs = specServ.getLoadedSpecList();
		if(specs != null) {
			for(SpecificationData s : specs) {
				List<String> cases = specServ.getRunningCases(s.getID());
				if(cases != null){
					for(String caseid: cases){
						Map<String, String> m = new HashMap<String, String>();
						m.put("id", caseid);
						m.put("specname", s.getName());
						m.put("specversion", s.getSpecVersion());
						ja.put(m);
					}
					
				}
			}
		}
		
		caseRunningJson = ja.toString();
		return SUCCESS;
	}
	
	public String running() {
		specs = specServ.getLoadedSpecList();
		cases = new ArrayList<HashMap<String, String>>();
		if (specs != null) {
			for (SpecificationData s : specs) {
				String name = s.getName();
				String version = s.getSpecVersion();
				String identifier = s.getSpecIdentifier();
				List<String> ids = specServ.getRunningCases(s.getID());
				if (ids != null) {
					for (String id : ids) {
						HashMap<String, String> c = new HashMap<String, String>();
						c.put("id", id);
						c.put("identifier", identifier);
						c.put("name", name);
						c.put("ver", version);
						cases.add(c);
					}
				}
			}
		}
		if (isPartialReq) {
			return "partial";
		} else {
			return "success";
		}
	}
	
	public String cancel() {
		specServ.cancelCase(selectedCase);
		return running();
	}
	
	public String upload() {
		String result = specServ.upload(ywl, ywlFileName);
		if (result.startsWith("<failure>")) {
			System.out.println(result);
		}
		return "success";
	}
	
	public String participate() {
		
		Map<String, Map<String, String>> map = logServ.getCaseParticipate((String) session.get("userid"));
		
		casesJson = this.toJson(map);
		
		return SUCCESS;		
	}
	
	public String participateOfComplete() {
		Map<String, Map<String, String>> map = logServ.getCaseParticipateOfComplete((String) session.get("userid"));
		casesJson = this.toJson(map);
		return SUCCESS;		
	}
	
	public String launchByMe() {
		//Map<String, Map<String, String>> map = logServ.getCaseLaunchByMe((String) session.get("userid"));
		//mineCasesJson = this.toJson(map);	
		
		mineCasesJson = logServ.getCaseLaunchByMe((String) session.get("userid"));
		
		return SUCCESS;
	}
	
	private String toJson(Map<String, Map<String, String>> map) {
		
		JSONArray ja = new JSONArray();
		
		for(String key : map.keySet()) {
			Map<String, String> m = map.get(key);
			
			m.put("caseid", key);
			ja.put(m);
		}

		return ja.toString();
	}
	
	public String history() {
		List<Map<String, String>> list = logServ.getCasehistory(caseid);
		
		JSONArray ja = new JSONArray();
		
		for(int i = 0 ; i<list.size(); i++) {
			ja.put(list.get(i));
		}
		
		caseHistoryJson = ja.toString();
		
		return SUCCESS;
	}

	/********************************************/
	public Set<SpecificationData> getSpecs() {
		return specs;
	}

	public void setSpecs(Set<SpecificationData> specs) {
		this.specs = specs;
	}

	public void setSelectedSpec(String selectedSpec) {
		this.selectedSpec = selectedSpec;
	}

	public String getSelectedSpec() {
		return selectedSpec;
	}

	public String getSelectedCase() {
		return selectedCase;
	}

	public void setSelectedCase(String selectedCase) {
		this.selectedCase = selectedCase;
	}

	public List<HashMap<String,String>> getCases() {
		return cases;
	}

	public void setCases(List<HashMap<String, String>> cases) {
		this.cases = cases;
	}

	public boolean getIsPartialReq() {
		return isPartialReq;
	}

	public void setIsPartialReq(boolean isPartialReq) {
		this.isPartialReq = isPartialReq;
	}

	public void setYwl(File ywl) {
		this.ywl = ywl;
	}

	public void setYwlFileName(String ywlFileName) {
		this.ywlFileName = ywlFileName;
	}

	public String getCasesJson() {
		return casesJson;
	}

	public void setCasesJson(String casesJson) {
		this.casesJson = casesJson;
	}

	public String getMineCasesJson() {
		return mineCasesJson;
	}

	public void setMineCasesJson(String mineCasesJson) {
		this.mineCasesJson = mineCasesJson;
	}

	public String getCaseid() {
		return caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	public String getCaseHistoryJson() {
		return caseHistoryJson;
	}

	public void setCaseHistoryJson(String caseHistoryJson) {
		this.caseHistoryJson = caseHistoryJson;
	}

	public String getCaseRunningJson() {
		return caseRunningJson;
	}

	public void setCaseRunningJson(String caseRunningJson) {
		this.caseRunningJson = caseRunningJson;
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