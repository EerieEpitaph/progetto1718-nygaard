package it.uniba.model;

import it.uniba.workdata.User;

/**
 * Rappresent a generic Edge composed of a user write mention (<i>From</i>),
 * user mentioned (<i>To</i>) and numbers of mention (<i>weight</i>)
 */
public final class Edge {
	/**
	 * <b>User</b> user write mention
	 */
	final User _from;
	/**
	 * <b>User</b> user mentioned by _from
	 */
	final User _to;
	/**
	 * numbers of mention of user
	 */
	float _weigth;

	/**
	 * Costructor of Edge
	 */
	public Edge(final User _fromIn, final User _toIn, final float _weigthIn) {
		_from = _fromIn;
		_to = _toIn;
		_weigth = _weigthIn;
	}

	/**
	 * 
	 * @return <b>User</b> write mention
	 */
	public User getFrom() {
		return _from;
	}

	/**
	 * 
	 * @return <b> user mentioned by <i>_from</i>
	 */
	public User getTo() {
		return _to;
	}

	/**
	 * 
	 * @return number of mention by _from
	 */
	public float getWeigth() {
		return _weigth;
	}

	/**
	 * Change current weight of Edge
	 * 
	 * @param newWeigth
	 *            <i>float</i> is the new weight
	 */
	public void changeWeigth(float newWeigth) {
		_weigth = newWeigth;
	}

	/**
	 * Ovverride of Equal now two edge are equal if they have same user _from and
	 * same user _to
	 * 
	 * @return <i>boolean<i> if two edge are equal
	 */
	public boolean equals(Object obj) {
		if (_from.equals(((Edge) obj).getFrom()) && _to.equals(((Edge) obj).getTo()))
			return true;
		else
			return false;

	}

}
