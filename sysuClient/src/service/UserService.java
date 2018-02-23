package service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.yawlfoundation.yawl.resourcing.rsInterface.ResourceGatewayClientAdapter;
import org.yawlfoundation.yawl.resourcing.rsInterface.ResourceGatewayException;
import org.yawlfoundation.yawl.resourcing.rsInterface.WorkQueueGatewayClientAdapter;
import com.opensymphony.xwork2.ActionContext;

public class UserService{
	String _handle;
	String _userName = "admin";
	String _password = "YAWL";
	String _defURI = "http://localhost:8080/resourceService/workqueuegateway";
	
	String _roleURI = "http://localhost:8080/resourceService/gateway";
	ResourceGatewayClientAdapter resAdapter = new ResourceGatewayClientAdapter(_roleURI);
	
	WorkQueueGatewayClientAdapter wqAdapter = new WorkQueueGatewayClientAdapter(_defURI);
	Map<String, Object> session = ActionContext.getContext().getSession();

    private boolean connected() {
        if (!wqAdapter.checkConnection(_handle)) {
            _handle = wqAdapter.connect(_userName, _password) ;

            return wqAdapter.successful(_handle) ;
        }
        else return true ;
    }
	
	public boolean login(String userid, String password) {
		System.out.print(password+"...\n");
		String userhandle = wqAdapter.userlogin(userid, password, true);  //不要加密密码
		System.out.print(userhandle+"sss\n");
		try {
			if(this.connected() && wqAdapter.isValidUserSession(userhandle)) {
				session.put("userhandle", userhandle);
				session.put("userid", userid);
				session.put("password", password);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;

	}

	public void logout() {
		String userhandle = (String) session.get("userhandle");
		
		wqAdapter.userlogout(userhandle);
		
		session.remove("userhandle");
		session.remove("userid");
	}
	
	public String getRole(String userid, String password){
		Map<String, String> roles = new HashMap<String, String>();
		if(login( userid, password)){
			try {
				roles = resAdapter.getRoleIdentifiers(_handle);
				JSONArray json=new JSONArray();
				json.put(roles);
				System.out.println("_handle:" + _handle+"/n");
				System.out.println("getRolesFromResource:" + json.toString()+"/n");
				return json.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		else return null;
	}

}