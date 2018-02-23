package dao;

/**
 * UserHasSpecificationId entity. @author MyEclipse Persistence Tools
 */

public class UserHasSpecificationId implements java.io.Serializable {

	// Fields

	private User user;
	private Specification specification;

	// Constructors

	/** default constructor */
	public UserHasSpecificationId() {
	}

	/** full constructor */
	public UserHasSpecificationId(User user, Specification specification) {
		this.user = user;
		this.specification = specification;
	}

	// Property accessors

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Specification getSpecification() {
		return this.specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserHasSpecificationId))
			return false;
		UserHasSpecificationId castOther = (UserHasSpecificationId) other;

		return ((this.getUser() == castOther.getUser()) || (this.getUser() != null
				&& castOther.getUser() != null && this.getUser().equals(
				castOther.getUser())))
				&& ((this.getSpecification() == castOther.getSpecification()) || (this
						.getSpecification() != null
						&& castOther.getSpecification() != null && this
						.getSpecification()
						.equals(castOther.getSpecification())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUser() == null ? 0 : this.getUser().hashCode());
		result = 37
				* result
				+ (getSpecification() == null ? 0 : this.getSpecification()
						.hashCode());
		return result;
	}

}