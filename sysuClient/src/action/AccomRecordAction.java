package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import service.AccomRecordService;

import java.util.Map;

/**
 * Created by apple on 2018/3/25.
 */
public class AccomRecordAction extends ActionSupport {

    private Map<String, Object> session = ActionContext.getContext().getSession();
    private AccomRecordService accomRecordService = new AccomRecordService();

    private String selectedItem;

    public String doRecord() {
        String userid = (String)session.get("userid");
        accomRecordService.updateRecord(userid, selectedItem);
        return SUCCESS;
    }

    public String getSelectedItem() {
        return selectedItem;
    }
    public void setSelectedItem(String s) {
        this.selectedItem = s;
    }
}
