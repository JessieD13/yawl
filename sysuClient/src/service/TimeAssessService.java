package service;

import com.opensymphony.xwork2.ActionContext;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by apple on 2018/3/18.
 */
public class TimeAssessService {
    private AccomRecordService accomRecordService = new AccomRecordService();
    private Map<String, Object> session = ActionContext.getContext().getSession();

    public String calTimeSurplus(HashMap<Integer,WorkItemRecord> items, int order) {
        String userid = (String) session.get("userid");
        long  curTime, estStartTime;
        WorkItemRecord w = items.get(order);
        curTime = System.currentTimeMillis();
        estStartTime = curTime;
        for (int i = 1; i < order; i++) {
            WorkItemRecord wi = items.get(i);
            String recordTime = accomRecordService.getRecordTime(userid, wi.getSpecURI(), wi.getTaskName());
            if (recordTime != null) {
                estStartTime += Long.parseLong(recordTime);
            } else {
                estStartTime += Long.parseLong(wi.getExecuteTime());
            }
        }
        long lateststarttime = Long.parseLong(w.getLatestStartTime());
        String recordTime2 = accomRecordService.getRecordTime(userid, w.getSpecURI(), w.getTaskName());
        long executetime;
        if (recordTime2 != null) {
            executetime = Long.parseLong(recordTime2);
        } else {
            executetime = Long.parseLong(w.getExecuteTime());
        }
        double surplus = (lateststarttime + executetime - estStartTime)/(double)executetime;

        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String result = df.format(surplus);//返回的是String类型
        return result;
    }

    public Integer overallQueueAsses(ArrayList<Double> itemsurplus) {
        int barelycount = 0;
        int size = itemsurplus.size();
        Iterator<Double> it = itemsurplus.iterator();
        while (it.hasNext()) {
            double t = it.next();
            if (t > 1 && t < 1.5) barelycount++;
            if (t < 1) {
                return 0;
            }
        }
        if (barelycount <= (size/2)) return 2;
        return 1;
    }
}
