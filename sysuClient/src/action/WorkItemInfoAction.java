package action;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import dao.Wirinfo;
import service.WirInfoService;

@SuppressWarnings("serial")
public class WorkItemInfoAction extends ActionSupport{
	
	private String selectedItem;
	private String infosJson;
	
	private String location;
	private String region;
	private String latitude;
	private String longitude;
	private String appointment;
	private String consuming;
	private String delayFactor;
	
	private WirInfoService wirinfoServ = new WirInfoService();
	
	public String readInfo() {
		Map<String, String> m = new HashMap<String, String>();
		
		Wirinfo info = wirinfoServ.findByItemid(selectedItem);
		
		if(info.getLocation() != null)
			m.put("location", info.getLocation());	//位置
			
		if(info.getRegion() != null)
			m.put("region", info.getRegion());		//地区
		
		if(info.getLatitude() != null)
			m.put("latitude", info.getLatitude().toString());		//纬度
		
		if(info.getLongitude() != null)
			m.put("longitude", info.getLongitude().toString());	//经度
		
		if(info.getAppointmentTime() != null)
			m.put("appointment", info.getAppointmentTime());		//服务时间点
		
		if(info.getConsuming() != null)
			m.put("consuming", info.getConsuming().toString());	//耗费时间
		
		if(info.getDelayFactor() != null)
			m.put("delayFactor", info.getDelayFactor().toString());	//延迟惩罚系数
		
		infosJson = new JSONObject(m).toString();
		
		return SUCCESS;
	}
	
	public String saveInfo() {
		wirinfoServ.save(selectedItem, location, region, latitude, longitude, appointment, consuming, delayFactor);
		
		return SUCCESS;
	}
	
	/*
	 * getter setter
	 */
	public String getInfosJson() {
		return infosJson;
	}

	public void setInfosJson(String infosJson) {
		this.infosJson = infosJson;
	}

	public String getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public String getConsuming() {
		return consuming;
	}

	public void setConsuming(String consuming) {
		this.consuming = consuming;
	}

	public String getDelayFactor() {
		return delayFactor;
	}

	public void setDelayFactor(String delayFactor) {
		this.delayFactor = delayFactor;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
