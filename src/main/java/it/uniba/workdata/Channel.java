package it.uniba.workdata;

import java.io.Serializable;
import java.util.LinkedList;

public final class Channel  implements Serializable
{
	private String id;
	private Long created;
	private String creator;
	private LinkedList<String> members = new LinkedList<String>();
	private String name;
	private static final long serialVersionUID = 1L;
			
	public Channel(String _id, Long _created, String _creator, LinkedList<String> _members, String _name)
	{
		id = _id;
		created = _created; 
		creator = _creator;
		members = _members; 
		name = _name; 
	}

	/****************** GETTER  **************************/
	public String getId()
	{
		return id;
	}
	public LinkedList<String> getMemberList()
	{
		return members;
	}
	public String getName()
	{
		return name; 
	}
	public Long getDateCreation()
	{
		return created;
	}
	public String getCreator() 
	{
		return creator;
	}

	/****************** SETTER  **************************/
	public void setId(String value)
	{
	    id = value;
	}
	public void setMembeList(LinkedList<String> value)
	{
	    members = value;
	}
	public void setName(String value)
	{
		name = value;
	}
	public void setCreator(String value)
	{
		creator = value;
	}
	public void setDateCreation(Long value)
	{
	    created = value;
	}
	
	
}
