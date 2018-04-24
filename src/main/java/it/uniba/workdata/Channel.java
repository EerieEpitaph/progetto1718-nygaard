package it.uniba.workdata;

import java.util.LinkedList;
import java.util.Date;

public final class Channel 
{
	private String id_channel;
	private Date date_creation;
	private String creator;
	private LinkedList<String> member_list = new LinkedList<String>();
	private String name;
	
	public Channel(String _id, Date _date, String _creator, LinkedList<String> _list_member, String _name)
	{
		id_channel = _id;
		date_creation = _date; 
		creator = _creator;
		member_list = _list_member; 
		name = _name; 
	}

	/****************** GETTER  **************************/
	public String getId()
	{
		return id_channel;
	}
	public LinkedList<String> getMemberList()
	{
		return member_list;
	}
	public String getName()
	{
		return name; 
	}
	public Date getDateCreation()
	{
		return date_creation;
	}
	public String getCreator() 
	{
		return creator;
	}

	/****************** SETTER  **************************/
	public void setId(String value)
	{
		id_channel = value;
	}
	public void setMembeList(LinkedList<String> value)
	{
		member_list = value;
	}
	public void setName(String value)
	{
		name = value;
	}
	public void setCreator(String value)
	{
		creator = value;
	}
	public void setDateCreation(Date value)
	{
		date_creation = value;
	}
	
	
}
