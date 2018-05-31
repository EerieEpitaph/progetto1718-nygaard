package it.uniba.wrapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import it.uniba.workdata.Channel;

/**
 * This class is used to access to the value of the Channels. (It's an example
 * of to respect the Law of Demeter).
 */
public final class ChannelsWrapper {
	private Map<String, Channel> channels = new HashMap<String, Channel>();

	/**
	 * ChannelsWrapper's constructor.
	 * 
	 * @param mapChannels
	 *            <i>Map(String, Channel)</i>
	 */
	public ChannelsWrapper(final Map<String, Channel> mapChannels) {
		channels = mapChannels;
	}

	// inutilizzato
	// public ChannelsWrapper(final Collection<Channel> mapChannels) {
	// for (final Channel channelIn : mapChannels) {
	// channels.put(channelIn.getId(), channelIn);
	// }
	// }
	/**
	 * Returns a <i>Collection</i> of channels.
	 * 
	 * @return <i>Collection</i> of channels
	 */
	public Collection<Channel> values() {
		return channels.values();
	}

	/**
	 * Returns a <b>channel</b> specified,
	 * 
	 * @param keyCh
	 *            <i>String</i> channel's name
	 * @return <i>Channel</i> channel specified
	 */
	public Channel get(final String keyCh) {
		return new Channel(channels.get(keyCh));
	}

	/**
	 * Returns a <i>boolean</i> true if the <b>channel</b> with the id specified exists
	 * else false.
	 * 
	 * @param keyCh
	 *            <i>String</i> channel's name
	 * @return <i>boolean</i> true if exists else false
	 * 
	 */
	public boolean containsKey(final String keyCh) {
		return channels.containsKey(keyCh);
	}
}
