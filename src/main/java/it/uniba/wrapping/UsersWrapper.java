package it.uniba.wrapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import it.uniba.workdata.User;

public final class UsersWrapper {
	private final Map<String, User> users;

	public UsersWrapper(final Map<String, User> mapUsers) {
		// users = new HashMap<String, User>();
		users = mapUsers;
	}

	public String getRealName(final String keyUser) {
		final User usTmp = new User(users.get(keyUser));
		return usTmp.getRealName();
	}

	public String getName(final String keyUser) {
		final User usTmp = new User(users.get(keyUser));
		return usTmp.getRealName();
	}

	public String getRealName(final int position) {
		final Collection<User> usCollection = new ArrayList<User>(users.values());
		final Object[] usersTmp = usCollection.toArray();
		final User usTmp = new User((User) usersTmp[position]);
		return usTmp.getRealName();
	}

	public String getName(final int position) {
		final Collection<User> usCollection = new ArrayList<User>(users.values());
		final Object[] usersTmp = usCollection.toArray();
		final User usTmp = new User((User) usersTmp[position]);
		return usTmp.getRealName();
	}

	public String getDisplayNameNorm(final int position) {
		final Collection<User> usCollection = new ArrayList<User>(users.values());
		final Object[] usersTmp = usCollection.toArray();
		final User usTmp = new User((User) usersTmp[position]);
		return usTmp.getDisplayNameNorm();
	}

	public String getId(final int position) {
		final Collection<User> usCollection = new ArrayList<User>(users.values());
		final Object[] usersTmp = usCollection.toArray();
		final User usTmp = new User((User) usersTmp[position]);
		return usTmp.getId();
	}

	public Collection<User> values() {
		return users.values();
	}
	// inutilizzato
	// public Map<String, User> valuesMap() {
	// return users;
	// }

	public int size() {
		return users.size();
	}
}