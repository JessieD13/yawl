package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

//import service.LabelService;
import service.SpecService;
import service.WorkQueueService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class PileAction extends ActionSupport{
	
	private WorkQueueService workQueueServ = new WorkQueueService();
	//private LabelService labelServ = new LabelService();
	private SpecService specServ = new SpecService();
	
	private Map<String, Object> session = ActionContext.getContext().getSession();
	
	private String queueType;
	private String itemsJson;
	
	public String loadPileQueue() {

		Set<WorkItemRecord> items = workQueueServ.getWorkQueue((String) session.get("userid"), queueType);
		
		if(items == null) items = new HashSet<WorkItemRecord>();

		Map<String, List<WorkItemRecord>> m = new HashMap<String, List<WorkItemRecord>>();	
		for(WorkItemRecord i: items) {
			WorkItemRecord item = workQueueServ.getWorkItem(i.getID());
			/**
			 * 这样绕个弯是有原因的!!!
			 * 必须用getWorkItem()方法取得item，这样取得的WorkitemRecord的信息是完整的
			 * 
			 * 不知道为什么从getQueuedWorkItems()获取的Set<WorkItemRecord>中的item信息会不完整???? 然后isEdited的值会不正确 。。。非常的不科学
			 */
			
			YSpecificationID yid = new YSpecificationID(item.getSpecIdentifier(), item.getSpecURI(), item.getSpecVersion());

			String taskname = item.getTaskName();
			
			String key = yid+":"+taskname;
			
			if(m.containsKey(key)) {
				List<WorkItemRecord> tmp = m.get(key);
				tmp.add(item);
				m.put(key, tmp);
			} else {
				List<WorkItemRecord> tmp = new ArrayList<WorkItemRecord>();
				tmp.add(item);
				m.put(key, tmp);
			}
		}
		
		Map<String, List<String>> m2 = new HashMap<String, List<String>>();
		Iterator<String> iter = m.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			List<WorkItemRecord> val = m.get(key);
			
			String specname = specServ.getSpecName(val.get(0).getSpecIdentifier(), 
					val.get(0).getSpecVersion(), 
					val.get(0).getSpecURI()
					);
			String taskname = val.get(0).getTaskName();
			String newkey = specname + ": " + taskname;
			List<String> newval = new ArrayList<String>();
			for(WorkItemRecord item : val ){
				String status = item.getStatus();
				if(item.isEdited()) status += ";Edited";
				else status += ";NotEdited";
				newval.add(item.getID() + ";" + status);
			}
			m2.put(newkey, newval);
		}

		itemsJson = new JSONObject(m2).toString();
		
		return SUCCESS;
	}

	
	/*
	 * getter and setter
	 */

	public String getItemsJson() {
		return itemsJson;
	}

	public void setItemsJson(String itemsJson) {
		this.itemsJson = itemsJson;
	}

	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}
	
}