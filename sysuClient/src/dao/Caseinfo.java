package dao;

import java.util.HashSet;
import java.util.Set;

/**
 * Caseinfo entity. @author MyEclipse Persistence Tools
 */

public class Caseinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String caseid;
	private String priority;
	private String difficulty;
	private String clientLevel;
	private Set wirinfos = new HashSet(0);

	// Constructors

	/** default constructor */
	public Caseinfo() {
	}

	/** minimal constructor */
	public Caseinfo(String caseid) {
		this.caseid = caseid;
	}

	/** full constructor */
	public Caseinfo(String caseid, String priority, String difficulty,
			String clientLevel, Set wirinfos) {
		this.caseid = caseid;
		this.priority = priority;
		this.difficulty = difficulty;
		this.clientLevel = clientLevel;
		this.wirinfos = wirinfos;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCaseid() {
		return this.caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getClientLevel() {
		return this.clientLevel;
	}

	public void setClientLevel(String clientLevel) {
		this.clientLevel = clientLevel;
	}

	public Set getWirinfos() {
		return this.wirinfos;
	}

	public void setWirinfos(Set wirinfos) {
		this.wirinfos = wirinfos;
	}

}