package action;

import java.util.Map;

import service.CaseService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CaseinfoAction extends ActionSupport{
	
	private String selectedCase;
	private String priority;
	private String difficulty;
	private String clientLevel;
	
	private CaseService caseServ = new CaseService();
	private Map<String, Object> session = ActionContext.getContext().getSession();
	
	public String addPriority() {
		
		caseServ.addPriority(selectedCase, priority, (String) session.get("userid"));
		
		return SUCCESS;
	}
	
	public String addDifficulty() {
		
		caseServ.addDifficulty(selectedCase, difficulty, (String) session.get("userid"));
		
		return SUCCESS;
	}
	
	public String addClientLevel() {
		
		caseServ.addClientLevel(selectedCase, clientLevel, (String) session.get("userid"));
		
		return SUCCESS;
	}
	
	/*
	 * getter setter
	 */

	public String getSelectedCase() {
		return selectedCase;
	}

	public void setSelectedCase(String selectedCase) {
		this.selectedCase = selectedCase;
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