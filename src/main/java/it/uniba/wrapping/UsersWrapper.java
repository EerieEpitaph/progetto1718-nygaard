package it.uniba.wrapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import it.uniba.workdata.User;

/**
 * This class is used to access to the value of the Users. (It's an example of
 * to respect the Law of Demeter).
 */
public final class UsersWrapper {
	private final Map<String, User> users;

	/**
	 * UsersWrapper's constructor.
	 * 
	 * @param mapUsers
	 *            <i>Map(String, User)</i>
	 */
	public UsersWrapper(final Map<String, User> mapUsers) {
		// users = new HashMap<String, User>();
		users = mapUsers;
	}

	/**
	 * Returns the real name of the user specified.
	 * 
	 * @param keyUser
	 *            <i>String</i>
	 * @return <i>String</i> real name of the user
	 */
	public String getRealName(final String keyUser) {
		final User usTmp = new User(users.get(keyUser));
		return usTmp.getRealName();
	}

	/**
	 * Returns the name of the user specified.
	 * 
	 * @param keyUser
	 *            <i>String</i>
	 * @return <i>String</i> name of the user
	 */
	public String getName(final String keyUser) {
		final User usTmp = new User(users.get(keyUser));
		return usTmp.getRealName();
	}

	/**
	 * Returns the real name of the user in the specified position.
	 * 
	 * @param position
	 *            <i>int</i>
	 * @return <i>String</i> real name of the user
	 */
	public String getRealName(final int position) {
		final Collection<User> usCollection = new ArrayList<User>(users.values());
		final Object[] usersTmp = usCollection.toArray();
		final User usTmp = new User((User) usersTmp[position]);
		return usTmp.getRealName();
	}

	/**
	 * Returns the name (email) of the user in the specified position.
	 * 
	 * @param position
	 *            <i>int</i>
	 * @return <i>String</i> name (email) of the user
	 */
	public String getName(final int position) {
		final Collection<User> usCollection = new ArrayList<User>(users.values());
		final Object[] usersTmp = usCollection.toArray();
		final User usTmp = new User((User) usersTmp[position]);
		return usTmp.getRealName();
	}

	/**
	 * 
	 * Returns the display name (normalized) of the user in the specified position.
	 * 
	 * @param position
	 *            <i>int</i>
	 * @return <i>String</i> display name (normalized) of the user
	 */
	public String getDisplayNameNorm(final int position) {
		final Collection<User> usCollection = new ArrayList<User>(users.values());
		final Object[] usersTmp = usCollection.toArray();
		final User usTmp = new User((User) usersTmp[position]);
		return usTmp.getDisplayNameNorm();
	}

	/**
	 * Returns the id of the user in the specified position.
	 * 
	 * @param position
	 *            <i>int</i>
	 * @return <i>String</i> id of the user
	 */
	public String getId(final int position) {
		final Collection<User> usCollection = new ArrayList<User>(users.values());
		final Object[] usersTmp = usCollection.toArray();
		final User usTmp = new User((User) usersTmp[position]);
		return usTmp.getId();
	}

	/**
	 * Returns a collection of users.
	 * 
	 * @return <i>Collection</i> of users
	 */
	public Collection<User> values() {
		return users.values();
	}
	// inutilizzato
	// public Map<String, User> valuesMap() {
	// return users;
	// }

	/**
	 * Returns the number of the users.
	 * 
	 * @return <i>int</i> number of users
	 */
	public int size() {
		return users.size();
	}
}
