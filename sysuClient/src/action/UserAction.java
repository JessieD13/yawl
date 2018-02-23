package action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.GetService;
import service.UserService;
import util.test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UserAction extends ActionSupport{
	
	private String userid;
	private String passwd;
	private UserService userService = new UserService();
	//private GetService getService=new GetService();
	
	
	String rolesJson;
	
	

	public String getRolesJson() {
		return rolesJson;
	}


	public void setRolesJson(String rolesJson) {
		this.rolesJson = rolesJson;
	}


	public String signin() {	
	// test t=new test();
	// t.postForm();
  	//String ss=gs.getAvailableServiceList(1);
		if( userid.trim().equals("admin") && passwd.equals("YAWL")){
			
			Map<String, Object> session = ActionContext.getContext().getSession();
			//session.put("userid", "0");  //标记管理员
			session.put("userid", userid);
			session.put("password", "YAWL");
			return "admin";
		}
		
		/*else{
			GetService gs = new GetService();
			try {
				String loginresult = gs.loginVerify(userid, passwd);
				JSONArray json = JSONArray.fromObject(loginresult ); // 棣栧厛鎶婂瓧绗︿覆杞垚 JSONArray  瀵硅薄
				  System.out.println(json.toString()+"="+loginresult+"\n") ;
				  Map<String ,String> mp=new HashMap<String,String>();
			         if(json.size()>0){
			           for(int i=0;i<json.size();i++){// 閬嶅巻 jsonarray 鏁扮粍锛屾妸姣忎竴涓璞¤浆鎴?json 瀵硅薄
			             JSONObject job = json.getJSONObject(i); 
			             if(job.getString("userid").equals(userid)){
			            	 if(job.getString("LoginVerify").equals("success")){  //鐧诲綍鎴愬姛
			     				 
			     				
			            		// if( userService.login(userid.trim(), passwd))	{
			            		//	 gs.getAvailableServiceList(Integer.parseInt(userid.trim()));
			            			 return "user";
			            		// }else{
			            		//	 return "error";
			            		// }
			            	 }else{
			            		 return "error";
			            	 }	 
			             }
			           }
			         }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error";
			}
			return "user";
		}*/
		
		else if( userService.login(userid.trim(), passwd))	{
			System.out.print(passwd+"...\n");
			//getService.getAvailableServiceList(Integer.parseInt(userid.trim()));
			return "user";
		}
		
		else return "error"; 

	}
	
	
	public String getAllRole(){
		rolesJson = userService.getRole(userid, passwd);
		return "success";
	}

	/*************************************************/

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
