package it.uniba.model;

import it.uniba.workdata.User;

/**
 * Represents a generic Edge composed of a user write mention (<i>From</i>),
 * user mentioned (<i>To</i>) and numbers of mentions (<i>weight</i>)
 */
public final class Edge {
	/*
	 * <b>User</b> user write mention
	 */
	private final User from;
	/*
	 * <b>User</b> user mentioned by _from
	 */
	private final User to;
	/*
	 * numbers of user mentions
	 */
	private float weight;

	/**
	 * Constructor of Edge
	 * 
	 * @param fromIn
	 *            <b>User</b> user write mention
	 * @param toIn
	 *            <b>User</b> user mentioned by fromIn
	 * @param weightIn
	 *            <i>float</i> numbers of mention
	 */
	public Edge(final User fromIn, final User toIn, final float weightIn) {
		from = fromIn;
		to = toIn;
		weight = weightIn;
	}

	/**
	 * @return <b>User</b> write mention
	 */
	public User getFrom() {
		return from;
	}

	/**
	 * 
	 * @return <b> user mentioned by <i>_from</i>
	 */
	public User getTo() {
		return to;
	}

	/**
	 * 
	 * @return number of mention by _from
	 */
	public float getWeigth() {
		return weight;
	}

	/**
	 * Change current weight of the Edge
	 * 
	 * @param newWeight
	 *            <i>float</i> is the new weight
	 */
	public void changeWeight(final float newWeight) {
		weight = newWeight;
	}

	// Se ci sono problemi
	@Override
	public int hashCode() {
		return from.hashCode();
	}

	/**
	 * Override of the <i>equals</i> method; Two edges are equal if they have same <i>_from</i> and
	 * <i>_to</i> user
	 * 
	 * @param obj
	 *            <b>Edge</b> to compare
	 * @return a <i>boolean<i> value indicating if the two edges are equal
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Edge) {
			return ((from.equals(((Edge) obj).getFrom())) && (to.equals(((Edge) obj).getTo())));
		} else {
			return false;
		}
	}

}
