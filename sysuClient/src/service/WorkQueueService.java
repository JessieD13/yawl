package service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.resourcing.QueueSet;
import org.yawlfoundation.yawl.resourcing.resource.Participant;
import org.yawlfoundation.yawl.resourcing.rsInterface.ResourceGatewayException;
import org.yawlfoundation.yawl.resourcing.rsInterface.ResourceMarshaller;
import org.yawlfoundation.yawl.resourcing.rsInterface.WorkQueueGatewayClientAdapter;

import com.opensymphony.xwork2.ActionContext;

public class WorkQueueService{
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
    
	public Participant getParticipantFromUserid(String userid) {
		if(this.connected()) {
			try {				
				return wqAdapter.getParticipantFromUserID(userid, _handle);			
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Participant getParticipant(String pid) {
		if (this.connected()) {
			try {
				
				return wqAdapter.getParticipant(pid, _handle);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean isAdmin(String userid) {
		Participant pa = this.getParticipantFromUserid(userid);
		
		if(pa.isAdministrator()) return true;
		else return false;
		
	}
	
	public QueueSet getAdminQueue() {
		if (this.connected()) {
			try {
				
				return wqAdapter.getAdminQueues(_handle);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Set<WorkItemRecord> getWorkQueue(String userid, String queueType) {
		
		if(this.connected()) {
			Participant pa = this.getParticipantFromUserid(userid);
			//Participant pa = this.getParticipantFromUserid(userid); uesrid="admin"时有问题
			try {
				
				if(queueType.equals("offered"))
					return wqAdapter.getQueuedWorkItems(pa.getID(), 0, _handle);
				else if(queueType.equals("allocated"))
					return wqAdapter.getQueuedWorkItems(pa.getID(), 1, _handle);
				else if(queueType.equals("started"))
					return wqAdapter.getQueuedWorkItems(pa.getID(), 2, _handle);
				else if(queueType.equals("suspended"))
					return wqAdapter.getQueuedWorkItems(pa.getID(), 3, _handle);
				else if(queueType.equals("unoffered") && this.isAdmin(userid))
					return wqAdapter.getQueuedWorkItems(pa.getID(), 4, _handle);
				else if(queueType.equals("worklisted") && this.isAdmin(userid))
					return wqAdapter.getQueuedWorkItems(pa.getID(), 5, _handle);
				
				else return null;
				/*
				 *UNDEFINED = -1 ;
				 *OFFERED = 0 ;
				 *ALLOCATED = 1 ;
				 *STARTED = 2 ;
				 *SUSPENDED = 3 ;
				 *UNOFFERED = 4 ;                  // administrator only
				 *WORKLISTED = 5 ;                 // administrator only
				 */
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	public void accept(String userid, String selectedItem) {
		if(this.connected()) {			
			Participant pa = this.getParticipantFromUserid(userid);			
			try {
				wqAdapter.acceptOffer(pa.getID(), selectedItem, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void start(String userid, String selectedItem) {
		if(this.connected()) {			
			Participant pa = this.getParticipantFromUserid(userid);
			try {
				wqAdapter.startItem(pa.getID(), selectedItem, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void acceptstart(String userid, String selectedItem) {
		this.accept(userid, selectedItem);
		this.start(userid, selectedItem);
		
	}

	public void deallocate(String userid, String selectedItem) {
		if(this.connected()) {			
			Participant pa = this.getParticipantFromUserid(userid);
			try {
				wqAdapter.deallocateItem(pa.getID(), selectedItem, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void reallocate(String userid, String selectedItem) {
		if(this.connected()) {			
			Participant pa = this.getParticipantFromUserid(userid);
			try {
				wqAdapter.reallocateItem(pa.getID(), selectedItem, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(String itemID, String dataString) {
		if (this.connected()) {
			try {
				
				String result = wqAdapter.updateWorkItemData(itemID, dataString, _handle);
				
				System.out.println("update:"+result+";"+wqAdapter.getWorkItem(itemID, _handle));
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void complete(String userid, String selectedItem) {
		if (this.connected()) {
			Participant pa = this.getParticipantFromUserid(userid);
			try {
				
				wqAdapter.completeItem(pa.getID(), selectedItem, _handle);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
	}

	public void skip(String userid, String selectedItem) {
		if(this.connected()) {			
			Participant pa = this.getParticipantFromUserid(userid);
			try {
				wqAdapter.skipItem(pa.getID(), selectedItem, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void pile(String userid, String selectedItem) {
		if(this.connected()) {			
			Participant pa = this.getParticipantFromUserid(userid);
			try {
				wqAdapter.pileItem(pa.getID(), selectedItem, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void suspend(String userid, String selectedItem) {
		if(this.connected()) {			
			Participant pa = this.getParticipantFromUserid(userid);
			try {
				wqAdapter.suspendItem(pa.getID(), selectedItem, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void unsuspend(String userid, String selectedItem) {
		if(this.connected()) {			
			Participant pa = this.getParticipantFromUserid(userid);
			try {
				wqAdapter.unsuspendItem(pa.getID(), selectedItem, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
	}

	public String getCustomFormURL(String userid, WorkItemRecord wir, String source) {
		StringBuilder url = new StringBuilder(wir.getCustomFormURL());
		if (this.connected()) {
			Participant pa = this.getParticipantFromUserid(userid);
			if (url != null) {
				// should check session timeout value here
				url.append(url.indexOf("?") == -1 ? "?" : "&")
				.append("workitem=")
				.append(wir.getID())
				.append("&participantid=")
				.append(pa.getID())
				.append("&handle=")
				.append(_handle)
				.append("&source=")
				.append(source);
			}
		}
		return url.toString();
	}
	
	public void delegate(String useridFrom, String useridTo, String selectedItem) {
		if(this.connected()) {			
			Participant pFrom = this.getParticipantFromUserid(useridFrom);
			Participant pTo = this.getParticipantFromUserid(useridTo);

			try {
				
				wqAdapter.delegateItem(pFrom.getID(), pTo.getID(), selectedItem, _handle);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public WorkItemRecord getWirFromStartedQueue(String userid, String selectedItem) {
		WorkItemRecord wir = null;
		
    	Set<WorkItemRecord> wirs = this.getWorkQueue(userid, "started");
    	
    	Iterator<WorkItemRecord> iter = wirs.iterator();
    	while(iter.hasNext()) {
    		wir = iter.next();
    		if(wir.getID().equals(selectedItem))
    			return wir;
    	}
    	
    	return wir;
	}
	
	public WorkItemRecord getWorkItem(String selectedItem) {
		WorkItemRecord wir = null;
		if (connected()) {
			ResourceMarshaller marshaller = new ResourceMarshaller();
			try {
				
				wir = marshaller.unmarshallWorkItemRecord(wqAdapter.getWorkItem(selectedItem, _handle));
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
			
		}
		return wir;
	}
	
	public Set<YParameter> getWorkItemParameters(String itemID) {
		Set<YParameter> result = new HashSet<YParameter>();
		if (connected()) {
			try {
				
				result = wqAdapter.getWorkItemParameters(itemID, _handle);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String getWorkItemDataSchema(String itemID) {
		String result = null;
		if (connected()) {
			try {
				
				result = wqAdapter.getWorkItemDataSchema(itemID, _handle);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceGatewayException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void sync() {
		if (connected()) {
			try {
				wqAdapter.synchroniseCaches(_handle);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}