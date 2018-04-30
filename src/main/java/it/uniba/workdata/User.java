package it.uniba.workdata;

public final class User
{
	private String id;
	private String team_id;
	private String name;
	private String real_name;
	
	public User(String _id, String _teams, String _name, String _real_name)
	{
		id = _id;
		name = _name;
		real_name = _real_name;
		team_id = _teams;
	}
	
	/****************** GETTER  **************************/
	public String getId()
	{
		return id;
	}
	public String getTeamId()
	{
		return team_id;
	}
	public String getName()
	{
		return name;
	}
	public String getRealName()
	{
		return real_name;
	}

	/****************** SETTER  **************************/
	public void setId(String value)
	{
	    id = value;
	}
	public void setTeamId(String value)
	{
		team_id = value;
	}
	public void setName(String value)
	{
		name = value;
	}
	public void setRealName(String value)
	{
		real_name = value;
	}
	
}
