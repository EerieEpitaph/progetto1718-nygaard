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
	private final User from;
	/*
	 * <b>User</b> user mentioned by _from
	 */
	private final User to;
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
		return from.hashCode();
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
		// if (!(obj instanceof Edge)) {
		// return false;
		// }
//		if ((from.equals(((Edge) obj).getFrom())) && (to.equals(((Edge) obj).getTo()))) {
			return ((from.equals(((Edge) obj).getFrom())) && (to.equals(((Edge) obj).getTo())));
//		} else {
//			return false;
//		}

	}

}
