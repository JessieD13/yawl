package dao;

/**
 * UserHasApplication entity. @author MyEclipse Persistence Tools
 */

public class UserHasApplication implements java.io.Serializable {

	// Fields

	private UserHasApplicationId id;

	// Constructors

	/** default constructor */
	public UserHasApplication() {
	}

	/** full constructor */
	public UserHasApplication(UserHasApplicationId id) {
		this.id = id;
	}

	// Property accessors

	public UserHasApplicationId getId() {
		return this.id;
	}

	public void setId(UserHasApplicationId id) {
		this.id = id;
	}

}