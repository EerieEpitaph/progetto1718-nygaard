package it.uniba.workdata;

import java.util.LinkedList;

public final class User {
	private String id;
	private LinkedList<String> team_id = new LinkedList<String>();;
	private String name;
	private String real_name;
	
	public User(String _id, LinkedList<String> teams, String _name, String _real_name)
	{
		id = _id;
		name = _name;
		real_name = _real_name;
		team_id = teams;
	}
	
	/****************** GETTER  **************************/
	public String get_id()
	{
		return id;
	}
	public LinkedList<String> get_teamId()
	{
		return team_id;
	}
	public String get_name()
	{
		return name;
	}
	public String get_realname()
	{
		return real_name;
	}

	/****************** SETTER  **************************/
	public void set_id(String value)
	{
		id = value;
	}
	public void set_team_id(LinkedList<String> value)
	{
		team_id = value;
	}
	public void set_name(String value)
	{
		name = value;
	}
	public void name(String value)
	{
		real_name = value;
	}
	
}
