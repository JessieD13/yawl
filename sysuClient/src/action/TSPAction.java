package action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;
import org.json.JSONObject;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

import service.WirInfoService;
import service.WorkQueueService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.Wirinfo;
import demo.City;
import demo.TravellingSalesman;

@SuppressWarnings("serial")
public class TSPAction extends ActionSupport {
	
	private String queueType;
	private String originLatitude;
	private String originLongitude;
	private String originLocation;
	private String originRegion;
	
	private String optimalJson;
	
	private WorkQueueService workQueueServ = new WorkQueueService();
	private WirInfoService wirinfoServ = new WirInfoService();
	
	private Map<String, Object> session = ActionContext.getContext().getSession();
	
	public String tspRoute() {
		
		Set<WorkItemRecord> items = workQueueServ.getWorkQueue((String) session.get("userid"), "started");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<City> Cities = new ArrayList<City>();
		
		try {
			
			Cities.add(new City(Double.parseDouble(originLatitude), Double.parseDouble(originLongitude), originLocation, originRegion, 0, sdf.parse("2014-3-2 12:00:00"), 0) );
			
			if(items != null) {
				
				Iterator<WorkItemRecord> it = items.iterator();
				
				while(it.hasNext()) {
					Wirinfo info = wirinfoServ.findByItemid(it.next().getID());
					Cities.add(new City(info.getLatitude(),
							info.getLongitude(),
							info.getLocation(),
							info.getRegion(),
							info.getDelayFactor(),
							sdf.parse(info.getAppointmentTime()),
							info.getConsuming())
					);
					
				}
			}
			
/*			List<City> Cities2 = new ArrayList<City>();
			Cities2.add(new City(23.131482, 113.268771, "公园前","广州", 1, sdf.parse("2014-3-3 12:00:00"), 60));
			Cities2.add(new City(23.112338, 113.276245, "市二宫","广州", 1, sdf.parse("2014-3-3 13:00:00"), 60));
			Cities2.add(new City(23.100903, 113.262519, "凤凰新村","广州", 1, sdf.parse("2014-3-3 15:00:00"), 60));
			Cities2.add(new City(23.114864, 113.247643, "黄沙","广州", 1, sdf.parse("2014-3-3 16:00:00"), 60));
			Cities2.add(new City(23.124386, 113.248685, "长寿路","广州", 1, sdf.parse("2014-3-3 17:00:00"), 60));
			Cities2.add(new City(23.131698, 113.252566, "陈家祠","广州", 1, sdf.parse("2014-3-3 18:00:00"), 60));
			Cities2.add(new City(23.131366, 113.262483, "西门口","广州", 1, sdf.parse("2014-3-3 19:00:00"), 60));*/
			  
			TravellingSalesman t = new TravellingSalesman(Cities);
			IChromosome optimal = t.findOptimalPath(null);
			System.out.println("Solution: ");
			System.out.println(optimal);
			System.out.println("Score " + (Integer.MAX_VALUE / 2 - optimal.getFitnessValue()));
			
			Gene[] genes = optimal.getGenes();
			StringBuffer str = new StringBuffer();
			for(int i=1; i<genes.length; i++){
				IntegerGene gene = (IntegerGene) genes[i];
				City city = Cities.get(gene.intValue());
				str.append("第"+ i +"个任务执行地点"+city.getLocation()
						+",任务耗时 "+city.getConsuming()+";");
			}
			
			optimalJson = str.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/*
	 * getter and setter
	 */
	
	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	public String getOriginLatitude() {
		return originLatitude;
	}

	public void setOriginLatitude(String originLatitude) {
		this.originLatitude = originLatitude;
	}

	public String getOriginLongitude() {
		return originLongitude;
	}

	public void setOriginLongitude(String originLongitude) {
		this.originLongitude = originLongitude;
	}

	public String getOriginLocation() {
		return originLocation;
	}

	public void setOriginLocation(String originLocation) {
		this.originLocation = originLocation;
	}

	public String getOriginRegion() {
		return originRegion;
	}

	public void setOriginRegion(String originRegion) {
		this.originRegion = originRegion;
	}

	public String getOptimalJson() {
		return optimalJson;
	}

	public void setOptimalJson(String optimalJson) {
		this.optimalJson = optimalJson;
	}
}