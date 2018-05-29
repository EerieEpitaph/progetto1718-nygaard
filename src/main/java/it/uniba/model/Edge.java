package it.uniba.model;

import it.uniba.workdata.User;

/**
 * Rappresent a generic Edge composed of a user write mention (<i>From</i>),
 * user mentioned (<i>To</i>) and numbers of mention (<i>weight</i>)
 */
public final class Edge {
	/*
	 * <b>User</b> user write mention
	 */
	private final User userFrom;
	/*
	 * <b>User</b> user mentioned by _from
	 */
	private final User userTo;
	/*
	 * numbers of mention of user
	 */
	private float weight;

	/**
	 * Costructor of Edge
	 * 
	 * @param fromIn
	 *            <b>User</b> user write mention
	 * @param toIn
	 *            <b>User</b> user mentioned by fromIn
	 * @param weightIn
	 *            <i>float</i> numbers of mention
	 */
	public Edge(final User fromIn, final User toIn, final float weightIn) {
		userFrom = fromIn;
		userTo = toIn;
		weight = weightIn;
	}

	public Edge(final Edge edgeToClone) {
		this(edgeToClone.userFrom, edgeToClone.userTo, edgeToClone.weight);
	}

	public Edge() {
		userTo = null;
		userFrom = null;
	}

	/**
	 * @return <b>User</b> write mention
	 */
	public User getFrom() {
		return userFrom;
	}

	/**
	 * 
	 * @return <b> user mentioned by <i>_from</i>
	 */
	public User getTo() {
		return userTo;
	}

	/**
	 * 
	 * @return number of mention by _from
	 */
	public float getWeigth() {
		return weight;
	}

	/**
	 * Change current weight of Edge
	 * 
	 * @param newWeigth
	 *            <i>float</i> is the new weight
	 */
	public void changeWeigth(final float newWeigth) {
		weight = newWeigth;
	}

	// Se ci sono problemi
	@Override
	public int hashCode() {
		return userFrom.hashCode();
	}

	/**
	 * Ovverride of Equal now two edge are equal if they have same user _from and
	 * same user _to
	 * 
	 * @param obj
	 *            <b>Edge</b> to analyze
	 * @return <i>boolean<i> if two edge are equal
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Edge) {
			return ((userFrom.equals(((Edge) obj).getFrom())) && (userTo.equals(((Edge) obj).getTo())));
		} else {
			return false;
		}
	}

}
