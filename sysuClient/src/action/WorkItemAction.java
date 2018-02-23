package action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONObject;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

import service.WorkQueueService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class WorkItemAction extends ActionSupport{
	
	private Set<WorkItemRecord> items;
	private String selectedItem;
	private WorkQueueService workQueueService = new WorkQueueService();
	private String direct;
	private String source;
	private String complete;
	private WorkItemRecord selectedWir;
	private Set<YParameter> params;
	private Map<String, String> wirMap;
	private boolean isPartialReq;
	
	private String useridTo;
	private String paramsJson;
	
	private Map<String, Object> session = ActionContext.getContext().getSession();	
	
	public String accept() {
		workQueueService.accept((String) session.get("userid"), selectedItem);
		items = workQueueService.getWorkQueue((String) session.get("userid"), "offered");
		
		if (isPartialReq) {
			return "partial";
		} else {
			return "success";
		}
	}
	
	public String acceptstart() {
		workQueueService.acceptstart((String) session.get("userid"), selectedItem);
		items = workQueueService.getWorkQueue((String) session.get("userid"), "offered");
		if (isPartialReq) {
			return "partial";
		} else {
			return "success";
		}
	}
	
	public String start() {
		workQueueService.start((String) session.get("userid"), selectedItem);
		items = workQueueService.getWorkQueue((String) session.get("userid"), "allocated");
		return "success";
	}
	
	public String complete() {
		if (selectedItem != null)
			workQueueService.complete((String)session.get("userid"), selectedItem);
		items = workQueueService.getWorkQueue((String) session.get("userid"), "started");
		if (isPartialReq) {
			return "partial";
		} else {
			return "success";
		}
	}
	
	public String deallocate() {
		workQueueService.deallocate((String) session.get("userid"), selectedItem);
		items = workQueueService.getWorkQueue((String) session.get("userid"), "allocated");
		return "success";
	}
	
	public String reallocate() {
		workQueueService.reallocate(useridTo, selectedItem);
		items = workQueueService.getWorkQueue((String) session.get("userid"), "allocated");
		return SUCCESS;
	}
	
	public String view() {
		params = workQueueService.getWorkItemParameters(selectedItem);
		selectedWir = workQueueService.getWorkItem(selectedItem);
		Element wirData = selectedWir.getUpdatedData() != null ? selectedWir.getUpdatedData() : selectedWir.getDataList();
		if (wirData != null) {
			for (YParameter p : params) {
				String value = wirData.getChildText(p.getName()); 
				if (value != null) {
					p.setDefaultValue(value);
				}
			}
		}
		if (!selectedWir.getCustomFormURL().equals("")) {
			direct = workQueueService.getCustomFormURL((String) session.get("userid"), selectedWir, source);
			if (isPartialReq) {
				direct += "&isPartialReq=true";
			}
			return "custom";
		} else {
			if (isPartialReq) {
				return "partial";
			} else {
				return "dynamic";
			}
		}
	}
	
	public String viewJson() {
		Map<String, String> m = new HashMap<String, String>();
		Set<YParameter> params = workQueueService.getWorkItemParameters(selectedItem);
		WorkItemRecord wir = workQueueService.getWorkItem(selectedItem);
		Element wirData = wir.getUpdatedData() != null ? wir.getUpdatedData() : wir.getDataList();
		
		if (!wir.getCustomFormURL().equals("")) {
			m.put("customForm", wir.getCustomFormURL());
		}
		
		if (wirData != null) {
			for (YParameter p : params) {
				String value = wirData.getChildText(p.getName()); 
				if (value != null) {
					p.setDefaultValue(value);
					m.put(p.getName(), p.getDataTypeName()+":"+p.getDefaultValue());
				}else {
					m.put(p.getName(), p.getDataTypeName());
				}
				
			}
		}
		
		paramsJson = new JSONObject(m).toString();
		
		return SUCCESS;
	}
	
	public String update() {
		selectedWir = workQueueService.getWorkItem(selectedItem);
		Element wirData = selectedWir.getUpdatedData() != null ? selectedWir.getUpdatedData() : selectedWir.getDataList();
		if (wirMap != null) {
			for (String param : wirMap.keySet()) {
				Element e = wirData.getChild(param);
				if (e == null) {
					e = new Element(param);
					e.setText(wirMap.get(param));
					wirData.addContent(e);
				} else {
					e.setText(wirMap.get(param));
				}
			}
		}
		workQueueService.update(selectedItem, new XMLOutputter(Format.getCompactFormat()).outputString(wirData));
		direct = source + "?isPartialReq=" + String.valueOf(isPartialReq); 
		if (complete != null && complete.equals("true")) {
			direct += "&selectedItem=" + selectedItem;
		}
		return "success";
	}
	
	public String updateJson() {
		selectedWir = workQueueService.getWorkItem(selectedItem);

		Element wirData = selectedWir.getUpdatedData() != null ? selectedWir.getUpdatedData() : selectedWir.getDataList();
		if (wirMap != null) {
			for (String param : wirMap.keySet()) {
				Element e = wirData.getChild(param);
				if (e == null) {
					e = new Element(param);
					e.setText(wirMap.get(param));
					wirData.addContent(e);
				} else {
					e.setText(wirMap.get(param));
				}
			}
		}
		workQueueService.update(selectedItem, new XMLOutputter(Format.getCompactFormat()).outputString(wirData));
		
		return SUCCESS;
	}

	public String delegate() {
		workQueueService.delegate((String) session.get("userid"), useridTo, selectedItem); //需要useridTo
		items = workQueueService.getWorkQueue((String) session.get("userid"), "allocated");
		return "success";
	}
	
	public String skip() {
		workQueueService.skip((String) session.get("userid"), selectedItem);
		items = workQueueService.getWorkQueue((String) session.get("userid"), "allocated");
		return "success";
	}
	
	public String suspend() {
		workQueueService.suspend((String) session.get("userid"), selectedItem);

		return SUCCESS;
	}
	
	public String unsuspend() {	
		workQueueService.unsuspend((String) session.get("userid"), selectedItem);

		return SUCCESS;
	}
	
	public String pile() {
		workQueueService.pile((String) session.get("userid"), selectedItem);
		
		return SUCCESS;
	}
	
	/******************************************************/
	
	public String getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}

	public Set<WorkItemRecord> getItems() {
		return items;
	}

	public void setItems(Set<WorkItemRecord> items) {
		this.items = items;
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public WorkItemRecord getSelectedWir() {
		return selectedWir;
	}

	public void setSelectedWir(WorkItemRecord selectedWir) {
		this.selectedWir = selectedWir;
	}

	public boolean getIsPartialReq() {
		return isPartialReq;
	}

	public void setIsPartialReq(boolean isPartialReq) {
		this.isPartialReq = isPartialReq;
	}

	public Set<YParameter> getParams() {
		return params;
	}

	public void setParams(Set<YParameter> params) {
		this.params = params;
	}

	public Map<String, String> getWirMap() {
		return wirMap;
	}

	public void setWirMap(Map<String, String> wirMap) {
		this.wirMap = wirMap;
	}

	public String getUseridTo() {
		return useridTo;
	}

	public void setUseridTo(String useridTo) {
		this.useridTo = useridTo;
	}

	public String getParamsJson() {
		return paramsJson;
	}

	public void setParamsJson(String paramsJson) {
		this.paramsJson = paramsJson;
	}

}