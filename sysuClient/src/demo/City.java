package demo;

import java.util.Date;

public class City {
	
	private String location;
	private String region;
	private double latitude;
	private double longitude;

	
	private double delayFactor; //延迟惩罚系数	
	private Date appointmentTime;
	private double consuming; //seconds
	
	public City( double latitude, double longitude, String location, String region){
		this.latitude = latitude;
		this.longitude = longitude;
		this.location = location;
		this.region = region;
	}

	public City(double latitude, double longitude, String location, String region, double factor, Date appointment, double consuming){
		this.latitude = latitude;
		this.longitude = longitude;
		this.location = location;
		this.region = region;
		
		this.delayFactor = factor;
		this.appointmentTime = appointment;
		this.consuming = consuming;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public double getDelayFactor() {
		return delayFactor;
	}

	public void setDelayFactor(double delayFactor) {
		this.delayFactor = delayFactor;
	}

	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public double getConsuming() {
		return consuming;
	}

	public void setConsuming(double consuming) {
		this.consuming = consuming;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}