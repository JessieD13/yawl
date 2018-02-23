package dao;

/**
 * UserHasSpecification entity. @author MyEclipse Persistence Tools
 */

public class UserHasSpecification implements java.io.Serializable {

	// Fields

	private UserHasSpecificationId id;

	// Constructors

	/** default constructor */
	public UserHasSpecification() {
	}

	/** full constructor */
	public UserHasSpecification(UserHasSpecificationId id) {
		this.id = id;
	}

	// Property accessors

	public UserHasSpecificationId getId() {
		return this.id;
	}

	public void setId(UserHasSpecificationId id) {
		this.id = id;
	}

}