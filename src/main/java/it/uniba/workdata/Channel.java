package it.uniba.workdata;

import java.util.LinkedList;
import java.util.List;

/**
 * Models a single workspace channel.
 */
public final class Channel {

	/**
	 * Channel identfier as found in "channels.json".
	 */
	private String id;

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
	 * Constructs a new Channel.
	 * @param id2 channel id
	 * @param created2 creation timestamp
	 * @param creator2 founder's id
	 * @param members2 list of members
	 * @param name2 channel name
	 */
	public Channel(final String id2, final Long created2, final String creator2, final List<String> members2,
			final String name2) {
		this.id = id2;
		this.created = created2;
		this.creator = creator2;
		this.members = members2;
		this.name = name2;
	}

	/**
	 * Returns the channel ID as String.
	 * @return ID as String
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the list of members.
	 * @return List<String> of IDs
	 */
	public List<String> getMemberList() {
		return members;
	}

	/**
	 * Returns the name of the channel as String.
	 * @return channel name as String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the channel timestamp as Long.
	 * @return timestamp as Long
	 */
	public Long getDateCreation() {
		return created;
	}

	/**
	 * Return the founder's ID as String.
	 * @return founder's ID as String
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Sets a new ID.
	 * @param value new id
	 */
	public void setId(final String value) {
		id = value;
	}

	/**
	 * Sets a new member list.
	 * @param value new list
	 */
	public void setMembeList(final List<String> value) {
		members = value;
	}

	/**
	 * Sets a new channel name.
	 * @param value new channel name
	 */
	public void setName(final String value) {
		name = value;
	}

	/**
	 * Sets a new founded.
	 * @param value new founder id
	 */
	public void setCreator(final String value) {
		creator = value;
	}

	/**
	 * Sets a new creation time.
	 * @param value new timestamp as Long
	 */
	public void setDateCreation(final Long value) {
		created = value;
	}

}
