package action;

import java.util.Map;
import java.util.Set;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.resourcing.QueueSet;
import org.yawlfoundation.yawl.resourcing.WorkQueue;

import service.WorkQueueService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AdminAction extends ActionSupport {
	
	private Set<WorkItemRecord> items;
	private WorkQueueService workQueueService = new WorkQueueService();
	
	private Map<String, Object> session = ActionContext.getContext().getSession();
	
	public String unoffered() {
		QueueSet queueSet = workQueueService.getAdminQueue();
		items = queueSet.getQueuedWorkItems(WorkQueue.UNOFFERED);
		return "success";
		
	}
	
	
	/*public void CreateTable() {
	        //解析hibernate.cfg.xml配置文件
	        Configuration conf = new Configuration().configure();
	        SchemaExport export = new SchemaExport(conf);
	        //根据配置文件生成表格，第一个参数是否显示建表语句，第二个参数是否生成表格
	        export.create(true, false);
	}*/
	
	public String worklisted() {		
		items = workQueueService.getWorkQueue((String) session.get("userid"), "worklisted");
		return "success";
		
	}
	

	/************************************************/
	public Set<WorkItemRecord> getItems() {
		return items;
	}

	public void setItems(Set<WorkItemRecord> items) {
		this.items = items;
	}
}