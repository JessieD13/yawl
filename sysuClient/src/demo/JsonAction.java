package demo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class JsonAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String userAge;
	
	private String result;

	public String execute() throws Exception {
		
		Map<String, String> resultmap = new HashMap<String, String>();
		resultmap.put("key", "姓名:"+userName+", 年龄:"+userAge);
		JSONObject jo = new JSONObject(resultmap);
		
		this.result = jo.toString();
		
		return SUCCESS;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}