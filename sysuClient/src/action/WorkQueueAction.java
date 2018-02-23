package action;

import service.CaseService;
import service.SpecService;
import service.WorkQueueService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.Caseinfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

@SuppressWarnings("serial")
public class WorkQueueAction extends ActionSupport{
	
	private Set<WorkItemRecord> items = new HashSet<WorkItemRecord>();
	
	private WorkQueueService workQueueServ = new WorkQueueService();
	private CaseService casServ = new CaseService();
	private SpecService specServ = new SpecService();
	private Map<String, Object> session = ActionContext.getContext().getSession();
	
	private boolean isPartialReq;
	
	/*****new*****/
	private String itemsJson;
	
	private String priority;
	private String difficulty;
	private String clientLevel;
	private String queueType;
	
	public String execute() { return SUCCESS;}
	
	/**
	 * 加载started队列 返回json
	 */
	public String loadStarted() {
		Set<WorkItemRecord> items = workQueueServ.getWorkQueue((String) session.get("userid"), "started");
		
		itemsJson = this.toJson(items);
		
		return SUCCESS;
	}
	
	/**
	 * 加载allocated队列 返回json
	 */
	public String loadAllocated() {
		Set<WorkItemRecord> items = workQueueServ.getWorkQueue((String) session.get("userid"), "allocated");
		
		itemsJson = this.toJson(items);
		
		return SUCCESS;
	}
	
	/**
	 * 加载offered队列 返回json
	 */	
	public String loadOffered() {
		Set<WorkItemRecord> items = workQueueServ.getWorkQueue((String) session.get("userid"), "offered");
		
		itemsJson = this.toJson(items);
		
		return SUCCESS;
	}
	
	/**
	 * 加载suspended队列 返回json
	 */	
	public String loadSuspended() {
		Set<WorkItemRecord> items = workQueueServ.getWorkQueue((String) session.get("userid"), "suspended");

		itemsJson = this.toJson(items);

		return SUCCESS;
	}
	
	/**
	 * 加载标签队列 返回json
	 */	
	public String loadPriorityQueue() {
		Set<WorkItemRecord> items = new HashSet<WorkItemRecord>();
		
		Set<WorkItemRecord> tmp = workQueueServ.getWorkQueue((String) session.get("userid"), queueType);	
		if(tmp != null) {
			for( WorkItemRecord wir : tmp) {
				Caseinfo caseinfo = casServ.findbyCaseid(wir.getCaseID());
				if( caseinfo != null && caseinfo.getPriority().equals(priority) ) {
					items.add(wir);
				}
			}
		}

		itemsJson = this.toJson(items);
		
		return SUCCESS;
	}
	public String loadDifficultyQueue() {
		Set<WorkItemRecord> items = new HashSet<WorkItemRecord>();
		
		Set<WorkItemRecord> tmp = workQueueServ.getWorkQueue((String) session.get("userid"), queueType);	
		if(tmp != null) {
			for( WorkItemRecord wir : tmp) {
				Caseinfo caseinfo = casServ.findbyCaseid(wir.getCaseID());
				if( caseinfo != null && caseinfo.getDifficulty().equals(difficulty) ) {
					items.add(wir);
				}
			}
		}

		itemsJson = this.toJson(items);
		
		return SUCCESS;
	}
	
	public String loadClientLevelQueue() {
		Set<WorkItemRecord> items = new HashSet<WorkItemRecord>();
		
		Set<WorkItemRecord> tmp = workQueueServ.getWorkQueue((String) session.get("userid"), queueType);	
		if(tmp != null) {
			for( WorkItemRecord wir : tmp) {
				Caseinfo caseinfo = casServ.findbyCaseid(wir.getCaseID());
				if( caseinfo != null && caseinfo.getClientLevel().equals(clientLevel) ) {
					items.add(wir);
				}
			}
		}

		itemsJson = this.toJson(items);
		
		return SUCCESS;
	}
	
	private String toJson(Set<WorkItemRecord> items) {
		JSONArray json = new JSONArray();
		
		if(items != null) {
			
			Iterator<WorkItemRecord> it = items.iterator();
			
			while(it.hasNext()) {
				Map<String, String> m = new HashMap<String, String>();
				
				WorkItemRecord item = workQueueServ.getWorkItem(it.next().getID());
				/**
				 * 这样绕个弯是有原因的!!!
				 * 必须用getWorkItem()方法取得item，这样取得的WorkitemRecord的信息是完整的
				 * 
				 * 不知道为什么从getQueuedWorkItems()获取的Set<WorkItemRecord>中的item信息会不完整???? 然后isEdited的值会不正确 。。。非常的不科学
				 */
				
				String specIdentifier = item.getSpecIdentifier();
				String specURI = item.getSpecURI();
				String specVersion = item.getSpecVersion();
				
				m.put("spec", specServ.getSpecName(specIdentifier, specVersion, specURI) );
				m.put("id", item.getID() );
				m.put("case", item.getCaseID() );
				m.put("task", item.getTaskName() );
				m.put("status", item.getStatus() );
				
				if(item.isEdited())
					m.put("isEdited", "true");
				else
					m.put("isEdited", "false");
				
				Caseinfo casinfo = casServ.findbyCaseid(item.getCaseID());
				if( casinfo != null && casinfo.getPriority() != null) {
					m.put("priority", casinfo.getPriority());
				} 
				
				if( casinfo != null && casinfo.getDifficulty() != null) {
					m.put("difficulty", casinfo.getDifficulty());
				} 
				
				if( casinfo != null && casinfo.getClientLevel() != null) {
					m.put("clientLevel", casinfo.getClientLevel());
				}
				
				json.put(m);
			}
		}
		
		return json.toString();
	}
	
	/****end of JSON***/
		
	public String offered() {
		items = workQueueServ.getWorkQueue((String) session.get("userid"), "offered");
		if (isPartialReq) {
			return "partial";
		} else {
			return "success";
		}
		
	}

	public String allocated() {		
		items = workQueueServ.getWorkQueue((String) session.get("userid"), "allocated");
		return "success";
		
	}
	
	public String started() {		
		items = workQueueServ.getWorkQueue((String) session.get("userid"), "started");
		if (isPartialReq) {
			return "partial";
		} else {
			return "success";
		}
		
	}
	
	public String suspended() {		
		items = workQueueServ.getWorkQueue((String) session.get("userid"), "suspended");
		return "success";
		
	}

	/*************************************************/

	public Set<WorkItemRecord> getItems() {
		return items;
	}

	public void setItems(Set<WorkItemRecord> items) {
		this.items = items;
	}

	public String getItemsJson() {
		return itemsJson;
	}

	public void setItemsJson(String itemsJson) {
		this.itemsJson = itemsJson;
	}

	public boolean getIsPartialReq() {
		return isPartialReq;
	}

	public void setIsPartialReq(boolean isPartialReq) {
		this.isPartialReq = isPartialReq;
	}

	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getClientLevel() {
		return clientLevel;
	}

	public void setClientLevel(String clientLevel) {
		this.clientLevel = clientLevel;
	}

}
