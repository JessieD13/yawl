package service;

import com.opensymphony.xwork2.ActionContext;
import dao.AccomRecord;
import dao.AccomRecordDAO;
import dao.ExeQueueDAO;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.resourcing.resource.Participant;
import org.yawlfoundation.yawl.resourcing.rsInterface.ResourceGatewayException;
import org.yawlfoundation.yawl.resourcing.rsInterface.WorkQueueGatewayClientAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2018/3/23.
 */
public class AccomRecordService {
    private String _handle;
    private String _userName = "admin";
    private String _password = "YAWL";
    private String _defURI = "http://localhost:8080/resourceService/workqueuegateway";

    private WorkQueueGatewayClientAdapter wqAdapter = new WorkQueueGatewayClientAdapter(_defURI);
    private Map<String, Object> session = ActionContext.getContext().getSession();
    private WorkQueueService workQueueService = new WorkQueueService();
    private AccomRecordDAO accomRecordDAO = new AccomRecordDAO();

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

    public void updateRecord(String userid, String selectedItem) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        WorkItemRecord w = workQueueService.getWorkItem(selectedItem);
        if (w != null) {
            String startTime = w.getStartTimeMs();
            System.out.println("\n" + startTime + "\n");
            System.out.println("\n" + currentTime + "\n");
            Long exeTime = Long.parseLong(currentTime) - Long.parseLong(startTime);
            String spec = w.getSpecURI();
            String task = w.getTaskName();

            List<AccomRecord> list = accomRecordDAO.findRecord(userid, spec, task);
            if (!list.isEmpty()) {
                AccomRecord a = list.get(0);
                int times = a.getCompletedTimes();
                Long oldTime = Long.parseLong(a.getAverageExeTime());
                Long calTime = (exeTime + oldTime * times)/(times +1);
                accomRecordDAO.updateAverExeTime(userid, spec, task, calTime.toString(), times+1);
            } else {
                addRecord(userid, spec, task, String.valueOf(exeTime));
            }

        }
    }
    public void addRecord(String userid, String spec, String task, String exeTime) {
        AccomRecord accomRecord = new AccomRecord(userid, spec, task, exeTime, 1);
        accomRecordDAO.save(accomRecord);
    }

    public String getRecordTime(String userid, String spec, String task) {
        List list = accomRecordDAO.findRecord(userid, spec, task);
        if (!list.isEmpty()) {
            AccomRecord a = (AccomRecord) list.get(0);
            return a.getAverageExeTime();
        } else {
            return null;
        }
    }
}
