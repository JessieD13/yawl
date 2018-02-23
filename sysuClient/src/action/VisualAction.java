package action;

import java.util.Map;

import org.json.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import service.SpecXMLService;

@SuppressWarnings("serial")
public class VisualAction extends ActionSupport {

	private SpecXMLService xmlServ = new SpecXMLService();
	
	private String identifier;
	private String caseid;
	
	private JSONObject ioJson;
	private JSONObject taskJson;
	private JSONObject flowJson;
	
	public String process() {
		
		Map<String, Map<String, String>> io = xmlServ.getIOVertex(identifier, caseid);
		ioJson = new JSONObject(io);
		
		Map<String, Map<String, String>> task = xmlServ.getTaskVertex(identifier, caseid);
		taskJson = new JSONObject(task);
		
		Map<String, Map<String, String>> flow = xmlServ.getFLowVertex(identifier);
		flowJson = new JSONObject(flow);
		
		return SUCCESS;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getCaseid() {
		return caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	public JSONObject getIoJson() {
		return ioJson;
	}

	public void setIoJson(JSONObject ioJson) {
		this.ioJson = ioJson;
	}

	public JSONObject getTaskJson() {
		return taskJson;
	}

	public void setTaskJson(JSONObject taskJson) {
		this.taskJson = taskJson;
	}

	public JSONObject getFlowJson() {
		return flowJson;
	}

	public void setFlowJson(JSONObject flowJson) {
		this.flowJson = flowJson;
	}
	
}
