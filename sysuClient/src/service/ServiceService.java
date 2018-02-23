package service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.yawlfoundation.yawl.elements.YAWLServiceReference;
import org.yawlfoundation.yawl.resourcing.rsInterface.WorkQueueGatewayClientAdapter;

import com.opensymphony.xwork2.ActionContext;

public class ServiceService{
	String _handle;
	String _userName = "admin";
	String _password = "YAWL";
	String _defURI = "http://localhost:8080/resourceService/workqueuegateway";
	
	WorkQueueGatewayClientAdapter wqAdapter = new WorkQueueGatewayClientAdapter(_defURI);
	Map<String, Object> session = ActionContext.getContext().getSession();

    private boolean connected() {
        if (!wqAdapter.checkConnection(_handle)) {
            _handle = wqAdapter.connect(_userName, _password) ;
            return wqAdapter.successful(_handle) ;
        }
        else return true ;
    }
    
    public Set<YAWLServiceReference> getRegisteredService() {
    	Set<YAWLServiceReference> result = null;
    	if (connected()) {
    		try {
				result = wqAdapter.getRegisteredServices(_handle);
			} catch (IOException e) {
				e.printStackTrace();
				result = new HashSet<YAWLServiceReference>();
			}
    	}
    	return result;
    }
    
    public String removeRegisteredService(String id) {
    	if (connected()) {
    		try {
				return wqAdapter.removeRegisteredService(id, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return "<failure>";
    }
    
    public String addRegisteredService(String uri, String name, String pwd, String doc) {
    	if (connected()) {
    		try {
    			YAWLServiceReference service = new YAWLServiceReference(uri, null, name, pwd, doc);
				return wqAdapter.addRegisteredService(service, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return "<failure>";
    }
}