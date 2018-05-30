package it.uniba.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import it.uniba.model.Edge;
import it.uniba.workdata.Channel;
import it.uniba.workdata.User;

public class MasterWrapper {

	public static final class EdgesWrapper {
		final private Object[] edgesWrapped;

		public EdgesWrapper(final Collection<Edge> edges) {
			edgesWrapped = edges.toArray();
		}

		public float getWeight(final int position) {
			final Edge edgeTMP = new Edge((Edge) (edgesWrapped[position]));
			return edgeTMP.getWeigth();
		}

		public String getFromRealName(final int position) {
			final Edge edgeTMP = new Edge((Edge) (edgesWrapped[position]));
			final User from = new User(edgeTMP.getFrom());
			return from.getRealName();
		}

		public String getToRealName(final int position) {
			final Edge edgeTMP = new Edge((Edge) (edgesWrapped[position]));
			final User toTMP = new User(edgeTMP.getTo());
			return toTMP.getRealName();
		}

		public boolean isEmpty() {
			return (edgesWrapped.length == 0);
		}

		public int size() {
			return edgesWrapped.length;
		}
	}

	public static final class ChannelsWrapper {
		private Map<String, Channel> channels = new HashMap<String, Channel>();

		public ChannelsWrapper(final Map<String, Channel> mapChannels) {
			channels = mapChannels;
		}

		public ChannelsWrapper(final Collection<Channel> mapChannels) {
			for (final Channel channelIn : mapChannels) {
				channels.put(channelIn.getId(), channelIn);
			}
		}

		public Collection<Channel> values() {
			return channels.values();
		}

		public Channel get(final String keyCh) {
			return new Channel(channels.get(keyCh));
		}

		public boolean containsKey(final String keyCh) {
			return channels.containsKey(keyCh);
		}
	}

	public static final class UsersWrapper {
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

		public Map<String, User> valuesMap() {
			return users;
		}

		public int size() {
			return users.size();
		}
	}

	// public static final class MentionGraphWrapper {
	// private final Map<String, User> users;
	//
	// public UsersWrapper(final Map<String, User> mapUsers) {
	// // users = new HashMap<String, User>();
	// users = mapUsers;
	// }
	//
	// public String getRealName(final String keyUser) {
	// final User usTmp = new User(users.get(keyUser));
	// return usTmp.getRealName();
	// }
	//
	// public String getName(final String keyUser) {
	// final User usTmp = new User(users.get(keyUser));
	// return usTmp.getRealName();
	// }
	//
	// public Collection<User> values() {
	// return users.values();
	// }
	// public Map<String, User> valuesMap() {
	// return users;
	// }
	// }
}
