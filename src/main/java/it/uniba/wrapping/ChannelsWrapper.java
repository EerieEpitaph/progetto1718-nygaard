package it.uniba.wrapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import it.uniba.workdata.Channel;

/**
 * 
 * 
 *
 */
public final class ChannelsWrapper {
	private Map<String, Channel> channels = new HashMap<String, Channel>();

	public ChannelsWrapper(final Map<String, Channel> mapChannels) {
		channels = mapChannels;
	}
	// inutilizzato
	// public ChannelsWrapper(final Collection<Channel> mapChannels) {
	// for (final Channel channelIn : mapChannels) {
	// channels.put(channelIn.getId(), channelIn);
	// }
	// }

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