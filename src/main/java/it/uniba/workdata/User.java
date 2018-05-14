package it.uniba.workdata;

public class User {
	public final class Profile {
		private String display_name_normalized;

		public Profile(String _display_name_normalized) {
			display_name_normalized = _display_name_normalized;
		}

		public String getDisplayNameNorm() {
			return display_name_normalized;
		}
	}

	private String id;
	private String team_id;
	private String name;
	private String real_name;
	private Profile profile;

	public User(final String _id, final String _teams, final String _name, final String _real_name) {
		id = _id;
		name = _name;
		real_name = _real_name;
		team_id = _teams;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return id.equals(((User) obj).id);
	}

	/****************** GETTER **************************/
	public String getId() {
		return id;
	}

	public String getTeamId() {
		return team_id;
	}

	public String getName() {
		return name;
	}

	public String getRealName() {
		return real_name;
	}

	public String getDisplayNameNorm() {
		return profile.getDisplayNameNorm();
	}

	/****************** SETTER **************************/
	public void setId(String value) {
		id = value;
	}

	public void setTeamId(String value) {
		team_id = value;
	}

	public void setName(String value) {
		name = value;
	}

	public void setRealName(String value) {
		real_name = value;
	}

}
