package it.uniba.workdata;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Models a single workspace channel.
 */
public final class Channel {

	/**
	 * Channel identfier as found in "channels.json".
	 */

	@SerializedName(value = "id")
	private String idChannel;

	/**
	 * Timestamp at which the channel was created.
	 */
	private Long created;

	/**
	 * User identifier of the creator.
	 */
	private String creator;

	/**
	 * Members of the channel listed by their user identifier.
	 */
	private List<String> members = new LinkedList<String>();

	/**
	 * Channel identfier as found in channels.json.
	 */
	private String name;

	/**
	 * Empty Channel's constructor.
	 */
	public Channel() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}

	/**
	 * User's constructor.
	 * 
	 * @param channel
	 *            channel to copy
	 */
	public Channel(final Channel channel) {
		this(channel.getId(), channel.getDateCreation(), channel.getCreator(), channel.getMemberList(),
				channel.getName());
	}

	/**
	 * Constructs a new Channel.
	 * 
	 * @param id2
	 *            channel id
	 * @param created2
	 *            creation timestamp
	 * @param creator2
	 *            founder's id
	 * @param members2
	 *            list of members
	 * @param name2
	 *            channel name
	 */
	public Channel(final String id2, final Long created2, final String creator2, final List<String> members2,
			final String name2) {
		this.idChannel = id2;
		this.created = created2;
		this.creator = creator2;
		this.members = members2;
		this.name = name2;
	}

	/**
	 * Returns the channel ID as String.
	 * 
	 * @return ID as String
	 */
	public String getId() {
		return idChannel;
	}

	/**
	 * Returns the list of members.
	 * 
	 * @return List<String> of IDs
	 */
	public List<String> getMemberList() {
		return members;
	}

	/**
	 * Returns the name of the channel as String.
	 * 
	 * @return channel name as String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the channel timestamp as Long.
	 * 
	 * @return timestamp as Long
	 */
	public Long getDateCreation() {
		return created;
	}

	/**
	 * Return the founder's ID as String.
	 * 
	 * @return founder's ID as String
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Override of Channel's hashCode
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/**
	 * Override of Channel's equals
	 */
	@Override
	public boolean equals(final Object objecto) {
		if (objecto instanceof Channel) {
			Channel oChannel = (Channel) objecto;
			return (oChannel.getId().equals(idChannel) && oChannel.getMemberList().equals(members)
					&& oChannel.getName().equals(name));
		}
		return false;
	}
}
