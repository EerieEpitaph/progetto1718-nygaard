package it.uniba.model;

import it.uniba.workdata.User;

public final class Edge {
	final User _from;
	final User _to;
	float _weigth;

	public Edge(final User _fromIn, final User _toIn, final float _weigthIn) {
		_from = _fromIn;
		_to = _toIn;
		_weigth = _weigthIn;
	}

	public User getFrom() {
		return _from;
	}

	public User getTo() {
		return _to;
	}

	public float getWeigth() {
		return _weigth;
	}

	public void changeWeigth(float newWeigth) {
		_weigth = newWeigth;
	}
	
	
	public boolean equals(Object obj) {
		if(_from.equals(((Edge) obj).getFrom()) &&
				_to.equals(((Edge) obj).getTo()))
			return true;
		else
			return false;
				
	}
	 
	
}
