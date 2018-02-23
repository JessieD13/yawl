package dao;

/**
 * Parameter entity. @author MyEclipse Persistence Tools
 */

public class Parameter implements java.io.Serializable {

	// Fields

	private Integer id;
	private Application application;
	private String name;
	private String value;
	private String type;

	// Constructors

	/** default constructor */
	public Parameter() {
	}

	/** minimal constructor */
	public Parameter(Application application, String name, String type) {
		this.application = application;
		this.name = name;
		this.type = type;
	}

	/** full constructor */
	public Parameter(Application application, String name, String value,
			String type) {
		this.application = application;
		this.name = name;
		this.value = value;
		this.type = type;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}