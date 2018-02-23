package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yawlfoundation.yawl.engine.interfce.SpecificationData;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

import service.SpecService;
import service.WorkQueueService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class WorkdeskAction extends ActionSupport {
	
	private SpecService specServ = new SpecService();
	private WorkQueueService workQueueService = new WorkQueueService();
	private Map<String, Object> session = ActionContext.getContext().getSession();
	
	private Map<String, String> data;
	
	public String refreshWorkQueue() {
		data = new HashMap<String, String>();
		
		Set<WorkItemRecord> items = workQueueService.getWorkQueue((String)session.get("userid"), "offered");
		if (items != null && !items.isEmpty()) data.put("offered", items.size()+"");
		else data.put("offered", "");
		
		items = workQueueService.getWorkQueue((String)session.get("userid"), "allocated");
		if (items != null && !items.isEmpty()) data.put("allocated", items.size()+"");
		else data.put("allocated", "");
		
		items = workQueueService.getWorkQueue((String)session.get("userid"), "started");
		if (items != null && !items.isEmpty()) data.put("started", items.size()+"");
		else data.put("started", "");
		
		items = workQueueService.getWorkQueue((String)session.get("userid"), "suspended");
		if (items != null && !items.isEmpty()) data.put("suspended", items.size()+"");
		else data.put("suspended", "");

		return SUCCESS;
	}
	
	public String refreshCaseMgt() {
		data = new HashMap<String, String>();
		
		Set<SpecificationData> specs = specServ.getLoadedSpecList();
		if (specs != null && !specs.isEmpty()) data.put("available", specs.size()+"");
		else data.put("available", "");
		
		int caseCount = 0;
		if (specs != null) {
			for (SpecificationData s : specs) {
				List<String> ids = specServ.getRunningCases(s.getID());
				if (ids != null) {
					caseCount += ids.size();
				}
			}
		}
		if (caseCount != 0) data.put("running", caseCount+"");
		else data.put("running", "");
		
		return SUCCESS;
	}

	// getter and setter
	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
}
