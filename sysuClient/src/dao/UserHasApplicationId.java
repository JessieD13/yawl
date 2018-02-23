package dao;

/**
 * UserHasApplicationId entity. @author MyEclipse Persistence Tools
 */

public class UserHasApplicationId implements java.io.Serializable {

	// Fields

	private User user;
	private Application application;

	// Constructors

	/** default constructor */
	public UserHasApplicationId() {
	}

	/** full constructor */
	public UserHasApplicationId(User user, Application application) {
		this.user = user;
		this.application = application;
	}

	// Property accessors

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserHasApplicationId))
			return false;
		UserHasApplicationId castOther = (UserHasApplicationId) other;

		return ((this.getUser() == castOther.getUser()) || (this.getUser() != null
				&& castOther.getUser() != null && this.getUser().equals(
				castOther.getUser())))
				&& ((this.getApplication() == castOther.getApplication()) || (this
						.getApplication() != null
						&& castOther.getApplication() != null && this
						.getApplication().equals(castOther.getApplication())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUser() == null ? 0 : this.getUser().hashCode());
		result = 37
				* result
				+ (getApplication() == null ? 0 : this.getApplication()
						.hashCode());
		return result;
	}

}