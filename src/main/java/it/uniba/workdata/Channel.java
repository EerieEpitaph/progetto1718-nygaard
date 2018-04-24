package it.uniba.workdata;

import java.util.LinkedList;
import java.util.Date;

public final class Channel 
{
	private String id;
	private Date date_creation;
	private String creator;
	private LinkedList<String> member_list = new LinkedList<String>();
	private String name;
	
	public Channel(String _id, Date date, String founder, LinkedList<String> list_member,String _name)
	{
		id = _id;
		date_creation = date; 
		creator = founder;
		member_list = list_member; 
		name = _name; 
	}

	/****************** GETTER  **************************/
	public String get_id()
	{
		return id;
	}
	public LinkedList<String> get_member_list()
	{
		return member_list;
	}
	public String get_name()
	{
		return name; 
	}
	public Date get_date_creation()
	{
		return date_creation;
	}
	public String get_founder() 
	{
		return creator;
	}

	/****************** SETTER  **************************/
	public void set_id(String value)
	{
		id = value;
	}
	public void set_member_list(LinkedList<String> value)
	{
		member_list = value;
	}
	public void set_name(String value)
	{
		name = value;
	}
	public void set_creator(String value)
	{
		creator = value;
	}
	public void set_date_creation(Date value)
	{
		date_creation = value;
	}
	
	
}
