package dao;

import java.util.HashSet;
import java.util.Set;

/**
 * Application entity. @author MyEclipse Persistence Tools
 */

public class Application implements java.io.Serializable {

	// Fields

	private Integer id;
	private String appName;
	private String appType;
	private String appUrl;
	private String appDesc;
	private Set parameters = new HashSet(0);
	private Set userHasApplications = new HashSet(0);

	// Constructors

	/** default constructor */
	public Application() {
	}

	/** minimal constructor */
	public Application(String appName, String appType, String appUrl,
			String appDesc) {
		this.appName = appName;
		this.appType = appType;
		this.appUrl = appUrl;
		this.appDesc = appDesc;
	}

	/** full constructor */
	public Application(String appName, String appType, String appUrl,
			String appDesc, Set parameters, Set userHasApplications) {
		this.appName = appName;
		this.appType = appType;
		this.appUrl = appUrl;
		this.appDesc = appDesc;
		this.parameters = parameters;
		this.userHasApplications = userHasApplications;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppUrl() {
		return this.appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getAppDesc() {
		return this.appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public Set getParameters() {
		return this.parameters;
	}

	public void setParameters(Set parameters) {
		this.parameters = parameters;
	}

	public Set getUserHasApplications() {
		return this.userHasApplications;
	}

	public void setUserHasApplications(Set userHasApplications) {
		this.userHasApplications = userHasApplications;
	}

}