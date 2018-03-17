package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.Caseinfo;
import dao.ExeQueue;
import org.json.JSONArray;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import service.CaseService;
import service.ExeQueueService;
import service.SpecService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 2018/3/2.
 */
public class ExeQueueAction extends ActionSupport {
    private HashMap<Integer, WorkItemRecord> exeItems = new HashMap<Integer, WorkItemRecord>();
    private ExeQueueService exeQueueServ = new ExeQueueService();
    private CaseService casServ = new CaseService();
    private SpecService specServ = new SpecService();
    private Map<String, Object> session = ActionContext.getContext().getSession();


    private String itemsJson;

    public String execute() {
        return SUCCESS;
    }

    public String loadExecuting() {
        String userid = (String)session.get("userid");
        exeQueueServ.updateData(userid);
        HashMap<Integer, WorkItemRecord> exeItems = exeQueueServ.getExecutingQueue(userid);
        itemsJson = this.toJson(exeItems);
        return SUCCESS;
    }

    public String loadDeadline() {
        String userid = (String)session.get("userid");
        HashMap<Integer, WorkItemRecord> exeItems = exeQueueServ.getDeadlineQueue(userid);
        itemsJson = this.toJson(exeItems);
        return SUCCESS;
    }

    public String loadEnable() {
        String userid = (String)session.get("userid");
        HashMap<Integer, WorkItemRecord> exeItems = exeQueueServ.getEnableQueue(userid);
        itemsJson = this.toJson(exeItems);
        return SUCCESS;
    }

    public String loadExetime() {
        String userid = (String)session.get("userid");
        HashMap<Integer, WorkItemRecord> exeItems = exeQueueServ.getExeTimeQueue(userid);
        itemsJson = this.toJson(exeItems);
        return SUCCESS;
    }


    private String toJson(HashMap<Integer, WorkItemRecord> exeItems) {
        JSONArray json = new JSONArray();
        if (exeItems != null) {
            int count = exeItems.size();
            for (int i = 1; i <= count; i++) {
                Map<String, String> m = new HashMap<String, String>();
                WorkItemRecord item = exeQueueServ.getWorkItem(exeItems.get(i).getID());
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
                m.put("executeTime", item.getExecuteTime());
                m.put("latestStartTime", item.getLatestStartTime());

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
    public String getItemsJson() {
        return itemsJson;
    }

    public void setItemsJson(String items) {
        this.itemsJson = items;
    }


}