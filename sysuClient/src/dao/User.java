package dao;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private String id;
	private Set userHasApplications = new HashSet(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String id) {
		this.id = id;
	}

	/** full constructor */
	public User(String id, Set userHasApplications) {
		this.id = id;
		this.userHasApplications = userHasApplications;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set getUserHasApplications() {
		return this.userHasApplications;
	}

	public void setUserHasApplications(Set userHasApplications) {
		this.userHasApplications = userHasApplications;
	}

}