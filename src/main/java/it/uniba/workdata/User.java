package it.uniba.workdata;

import com.google.gson.annotations.SerializedName;

/**
 * This class models a single user, as found on users.json.
 */
public class User {

	/*
	 * User's id.
	 */
	@SerializedName(value = "id")
	private String idUser;

	/*
	 * Id of the user's team.
	 */

	@SerializedName(value = "team_id")
	private String teamId;

	/*
	 * User's name.
	 */
	private String name;

	/*
	 * User's real name.
	 */
	@SerializedName(value = "real_name")
	private String realName;

	/*
	 * User's sub-profile.
	 */
	private final Profile profile;

	/**
	 * User's constructor.
	 */
	public User() {
		profile = new Profile();
		// This constructor is intentionally empty. Nothing special is needed here.
	}

	/**
	 * This class models a Profile, which is a sub-object inside a json User.
	 */
	public static final class Profile {

		/*
		 * As found on the .json profile.
		 */
		@SerializedName(value = "display_name_normalized")
		private final String displayNameNorm;

		/**
		 * Profile's constructor.
		 */
		public Profile() {
			displayNameNorm = "";
		}

		/**
		 * Constructor for a new Profile.
		 * 
		 * @param displName
		 *            displayed name
		 */
		public Profile(final String displName) {
			displayNameNorm = displName;
		}

		/**
		 * Returns the display name.
		 * 
		 * @return display name
		 */
		public String getDisplayNameNorm() {
			return displayNameNorm;
		}
	}

	/**
	 * User's constructor.
	 * 
	 * @param id2
	 *            User identifier
	 * @param teams2
	 *            User team
	 * @param name2
	 *            User name
	 * @param realName2
	 *            User real name
	 * @param profile2
	 *            User profile
	 */
	public User(final String id2, final String teams2, final String name2, final String realName2,
			final Profile profile2) {
		idUser = id2;
		name = name2;
		realName = realName2;
		teamId = teams2;
		profile = profile2;
	}

	/**
	 * User's constructor.
	 * 
	 * @param user
	 *            user to copy
	 */
	public User(final User user) {
		this(user.getId(), user.getTeamId(), user.getName(), user.getRealName(), user.getProfile());
	}

	/**
	 * Equals overriding.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}

		final User tempUser = new User(((User) obj));
		final String userId = tempUser.getId();
		return idUser.equals(userId);
	}

	/**
	 * Returns user id.
	 * 
	 * @return User's id
	 */
	public String getId() {
		return idUser;
	}

	/**
	 * Returns team id.
	 * 
	 * @return User's team id
	 */
	public String getTeamId() {
		return teamId;
	}

	/**
	 * Returns user name.
	 * 
	 * @return User's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns user real name.
	 * 
	 * @return User's real name
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * Returns user profile normalized name.
	 * 
	 * @return User's profile normalized name
	 */
	public String getDisplayNameNorm() {
		return profile.getDisplayNameNorm();
	}

	/**
	 * Returns user profile.
	 * 
	 * @return User's profile
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * Override of User's hashCode
	 */
	@Override
	public int hashCode() {
		return idUser.hashCode();
	}
}
