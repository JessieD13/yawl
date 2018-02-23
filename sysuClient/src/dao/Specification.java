package dao;

import java.util.HashSet;
import java.util.Set;

/**
 * Specification entity. @author MyEclipse Persistence Tools
 */

public class Specification implements java.io.Serializable {

	// Fields

	private Integer id;
	private String identifier;
	private String specXml;

	private Set userHasSpecifications = new HashSet(0);
	// Constructors

	/** default constructor */
	public Specification() {
	}
	
	public Specification(String identifier) {
		this.identifier = identifier;
	}

	/** full constructor */
	public Specification(String identifier, String specXml) {
		this.identifier = identifier;
		this.specXml = specXml;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getSpecXml() {
		return this.specXml;
	}

	public void setSpecXml(String specXml) {
		this.specXml = specXml;
	}

	public Set getUserHasSpecifications() {
		return userHasSpecifications;
	}

	public void setUserHasSpecifications(Set userHasSpecifications) {
		this.userHasSpecifications = userHasSpecifications;
	}
	
	
}