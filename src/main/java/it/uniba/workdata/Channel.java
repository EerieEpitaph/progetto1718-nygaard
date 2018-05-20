package it.uniba.workdata;

import java.util.LinkedList;

public final class Channel {
	private String id;
	private Long created;
	private String creator;
	private LinkedList<String> members = new LinkedList<String>();
	private String name;

	public Channel(final String _id, final Long _created, final String _creator, final LinkedList<String> _members,
			final String _name) {
		id = _id;
		created = _created;
		creator = _creator;
		members = _members;
		name = _name;
	}

	/****************** GETTER **************************/
	public String getId() {
		return id;
	}

	public LinkedList<String> getMemberList() {
		return members;
	}

	public String getName() {
		return name;
	}

	public Long getDateCreation() {
		return created;
	}

	public String getCreator() {
		return creator;
	}

	/****************** SETTER **************************/
	public void setId(String value) {
		id = value;
	}

	public void setMembeList(LinkedList<String> value) {
		members = value;
	}

	public void setName(String value) {
		name = value;
	}

	public void setCreator(String value) {
		creator = value;
	}

	public void setDateCreation(Long value) {
		created = value;
	}

}
