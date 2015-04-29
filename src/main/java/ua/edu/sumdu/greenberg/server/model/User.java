package ua.edu.sumdu.greenberg.server.model;

/**
 * This class create a user.
 */
public class User {
	private String name;

	/**
	 * This is constructor the new user.
	 * @param name - name user.
	 */
	public User(String name) {
		setName(name);
	}

	/**
	 * This method gets the name.
	 * @return name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method sets the name.
	 * @param name - name.
	 */
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof User)) return false;
		User other = (User) obj;
		if (!this.name.equals(other.name)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}