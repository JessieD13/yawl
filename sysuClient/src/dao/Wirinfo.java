package dao;

/**
 * Wirinfo entity. @author MyEclipse Persistence Tools
 */

public class Wirinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Caseinfo caseinfo;
	private String itemid;
	private String location;
	private Double latitude;
	private Double longitude;
	private String region;
	private Double delayFactor;
	private String appointmentTime;
	private Double consuming;

	// Constructors

	/** default constructor */
	public Wirinfo() {
	}

	/** minimal constructor */
	public Wirinfo(Caseinfo caseinfo, String itemid) {
		this.caseinfo = caseinfo;
		this.itemid = itemid;
	}

	/** full constructor */
	public Wirinfo(Caseinfo caseinfo, String itemid, String location,
			Double latitude, Double longitude, String region,
			Double delayFactor, String appointmentTime, Double consuming) {
		this.caseinfo = caseinfo;
		this.itemid = itemid;
		this.location = location;
		this.latitude = latitude;
		this.longitude = longitude;
		this.region = region;
		this.delayFactor = delayFactor;
		this.appointmentTime = appointmentTime;
		this.consuming = consuming;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Caseinfo getCaseinfo() {
		return this.caseinfo;
	}

	public void setCaseinfo(Caseinfo caseinfo) {
		this.caseinfo = caseinfo;
	}

	public String getItemid() {
		return this.itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Double getDelayFactor() {
		return this.delayFactor;
	}

	public void setDelayFactor(Double delayFactor) {
		this.delayFactor = delayFactor;
	}

	public String getAppointmentTime() {
		return this.appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public Double getConsuming() {
		return this.consuming;
	}

	public void setConsuming(Double consuming) {
		this.consuming = consuming;
	}

}