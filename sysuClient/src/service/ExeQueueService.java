package service;

import com.opensymphony.xwork2.ActionContext;
import com.sun.tools.javac.util.*;
import dao.ExeQueue;
import dao.ExeQueueDAO;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.resourcing.resource.Participant;
import org.yawlfoundation.yawl.resourcing.rsInterface.ResourceGatewayException;
import org.yawlfoundation.yawl.resourcing.rsInterface.ResourceMarshaller;
import org.yawlfoundation.yawl.resourcing.rsInterface.WorkQueueGatewayClientAdapter;

import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by apple on 2018/3/4.
 */
public class ExeQueueService {
    ExeQueueDAO exeQueueDAO = new ExeQueueDAO();
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

    public Set<WorkItemRecord> getAllocatedQueue(String userid) {

        if(this.connected()) {
            Participant pa = this.getParticipantFromUserid(userid);
            //Participant pa = this.getParticipantFromUserid(userid); uesrid="admin"时有问题
            try {
                return wqAdapter.getQueuedWorkItems(pa.getID(), 1, _handle);
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

    public HashMap<Integer, WorkItemRecord> getExecutingQueue(String userid) {
        HashMap<Integer, WorkItemRecord> result = new HashMap<>();
        Set<WorkItemRecord> offerdItems = getAllocatedQueue(userid);
        Iterator<WorkItemRecord> it = offerdItems.iterator();
        while (it.hasNext()) {
            WorkItemRecord item = it.next();
            List list =  exeQueueDAO.findByWir_id(item.getID());
            if (!list.isEmpty()) {
                ExeQueue e = (ExeQueue) list.get(0);
                if (!e.getParticipant().equals(userid)) {
                    e.setParticipant(userid);
                    e.setOrder(exeQueueDAO.getCount(userid));
                }
                result.put(e.getOrder(), item);
            } else {
                int order = addExeItem(item.getID(), userid);
                result.put(order, item);
            }
        }
        return result;
    }

    public HashMap<Integer, WorkItemRecord> getDeadlineQueue(String userid) {
        HashMap<Integer, WorkItemRecord> result = new HashMap<Integer, WorkItemRecord>();
        Set<WorkItemRecord> items = getAllocatedQueue(userid);
        List<WorkItemRecord> itemsList = new LinkedList<WorkItemRecord>();
        Iterator<WorkItemRecord> it = items.iterator();
        while(it.hasNext()) {
            WorkItemRecord w = it.next();
            itemsList.add(w);
        }
        Collections.sort(itemsList, new Comparator<WorkItemRecord>() {

            @Override
            public int compare(WorkItemRecord w1, WorkItemRecord w2) {
                Long l1 = Long.parseLong(w1.getLatestStartTime());
                Long l2 = Long.parseLong(w2.getLatestStartTime());
                long i = l1-l2;
                if(i == 0){
                    return (int)(Long.parseLong(w1.getStartTime()) - Long.parseLong(w2.getStartTime()));
                }
                return (int)i;
            }
        });
        for (int i = 1; i <= itemsList.size(); i++) {
            WorkItemRecord item = itemsList.get(i-1);
            result.put(i, item);
            exeQueueDAO.updateOrder(i, item.getID());
        }
        return result;
    }

    public HashMap<Integer, WorkItemRecord> getEnableQueue(String userid) {
        HashMap<Integer, WorkItemRecord> result = new HashMap<Integer, WorkItemRecord>();
        Set<WorkItemRecord> items = getAllocatedQueue(userid);
        List<WorkItemRecord> itemsList = new LinkedList<WorkItemRecord>();
        Iterator<WorkItemRecord> it = items.iterator();
        while(it.hasNext()) {
            WorkItemRecord w = it.next();
            itemsList.add(w);
        }
        Collections.sort(itemsList, new Comparator<WorkItemRecord>() {

            @Override
            public int compare(WorkItemRecord w1, WorkItemRecord w2) {
                Long l1 = Long.parseLong(w1.getEnablementTimeMs());
                Long l2 = Long.parseLong(w2.getEnablementTimeMs());
                long i = l1-l2;
                if(i == 0){
                    return (int)(Long.parseLong(w1.getExecuteTime()) - Long.parseLong(w2.getExecuteTime()));
                }
                return (int)i;
            }
        });
        for (int i = 1; i <= itemsList.size(); i++) {
            WorkItemRecord item = itemsList.get(i-1);
            result.put(i, item);
            exeQueueDAO.updateOrder(i, item.getID());
        }
        return result;
    }

    public HashMap<Integer, WorkItemRecord> getExeTimeQueue(String userid) {
        HashMap<Integer, WorkItemRecord> result = new HashMap<Integer, WorkItemRecord>();
        Set<WorkItemRecord> items = getAllocatedQueue(userid);
        List<WorkItemRecord> itemsList = new LinkedList<WorkItemRecord>();
        Iterator<WorkItemRecord> it = items.iterator();
        while(it.hasNext()) {
            WorkItemRecord w = it.next();
            itemsList.add(w);
        }
        Collections.sort(itemsList, new Comparator<WorkItemRecord>() {

            @Override
            public int compare(WorkItemRecord w1, WorkItemRecord w2) {
                Long l1 = Long.parseLong(w1.getExecuteTime());
                Long l2 = Long.parseLong(w2.getExecuteTime());
                long i = l1-l2;
                if(i == 0){
                    return (int)(Long.parseLong(w1.getEnablementTimeMs()) - Long.parseLong(w2.getEnablementTimeMs()));
                }
                return (int)i;
            }
        });
        for (int i = 1; i <= itemsList.size(); i++) {
            WorkItemRecord item = itemsList.get(i-1);
            result.put(i, item);
            exeQueueDAO.updateOrder(i, item.getID());
        }
        return result;
    }



    public int addExeItem(String wir_id, String userid) {
        ExeQueue e= new ExeQueue();
        e.setWir_id(wir_id);
        e.setParticipant(userid);
        int order = exeQueueDAO.getCount(userid) + 1;
        e.setOrder(order);
        if (exeQueueDAO.findByExample(e).isEmpty()) {
            exeQueueDAO.save(e);
        }
        return order;
    }

    public boolean findItem(String wir_id) {
        List l = exeQueueDAO.findByWir_id(wir_id);
        if (l.isEmpty()) {
            System.out.println("\n\nnot found\n\n");
            return false;
        }
        return true;
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

    public void updateData(String userid) {
        List<ExeQueue> list = exeQueueDAO.findAllByPa(userid);
        Set<WorkItemRecord> list_w = getAllocatedQueue(userid);
        if (!list.isEmpty()) {
            for (int i = 0; i <list.size(); i++) {
                String wir_id = list.get(i).getWir_id();
                Iterator<WorkItemRecord> it = list_w.iterator();
                boolean flag =false;
                while(it.hasNext()) {
                    WorkItemRecord w = it.next();
                    if (w.getID().equals(wir_id)) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    exeQueueDAO.delete(list.get(i));
                }
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
        ExeQueue e = (ExeQueue) exeQueueDAO.findByWir_id(selectedItem).get(0);
        exeQueueDAO.delete(e);
    }

    public void upward(){}
}
