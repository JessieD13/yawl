package action;

import java.util.List;
import org.yawlfoundation.yawl.resourcing.resource.Participant;
import service.ResourceService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ParticipantAction extends ActionSupport{

	private String infoSelect;
	private List<Participant> allParticipants;
	private ResourceService resourceService = new ResourceService();
	
	public String all() {
		allParticipants = resourceService.getParticipants();

		return SUCCESS;
	}
	
	public String create() {
		return SUCCESS;
	}
	
	public String info() {

		String select = this.getInfoSelect();  
		System.out.println("select: "+select);
		
		return SUCCESS;
	}
	
	/********************************************************/

	public String getInfoSelect() {
		return infoSelect;
	}

	public void setInfoSelect(String infoSelect) {
		this.infoSelect = infoSelect;
	}

	public List<Participant> getAllParticipants() {
		return allParticipants;
	}

	public void setAllParticipants(List<Participant> allParticipants) {
		this.allParticipants = allParticipants;
	}

}
