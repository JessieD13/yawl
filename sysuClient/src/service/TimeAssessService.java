package service;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by apple on 2018/3/18.
 */
public class TimeAssessService {
    public String calTimeSurplus(HashMap<Integer,WorkItemRecord> items, int order) {
        long  curTime, estStartTime;
        WorkItemRecord w = items.get(order);
        curTime = System.currentTimeMillis();
        estStartTime = curTime;
        for (int i = 1; i < order; i++) {
            estStartTime += Long.parseLong(items.get(i).getExecuteTime());
        }
        long lateststarttime = Long.parseLong(w.getLatestStartTime());
        long executetime = Long.parseLong(w.getExecuteTime());
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
