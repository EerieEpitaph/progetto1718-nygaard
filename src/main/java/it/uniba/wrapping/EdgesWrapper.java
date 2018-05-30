package it.uniba.wrapping;

import java.util.Collection;

import it.uniba.model.Edge;
import it.uniba.workdata.User;

public final class EdgesWrapper {
	final private Object[] edgesWrapped;

	public EdgesWrapper(final Collection<Edge> edges) {
		edgesWrapped = edges.toArray();
	}

	public float getWeight(final int position) {
		final Edge edgeTMP = new Edge((Edge) (edgesWrapped[position]));
		return edgeTMP.getWeigth();
	}

	public String getFromRealName(final int position) {
		final Edge edgeTMP = new Edge((Edge) (edgesWrapped[position]));
		final User from = new User(edgeTMP.getFrom());
		return from.getRealName();
	}

	public String getToRealName(final int position) {
		final Edge edgeTMP = new Edge((Edge) (edgesWrapped[position]));
		final User toTMP = new User(edgeTMP.getTo());
		return toTMP.getRealName();
	}

	public boolean isEmpty() {
		return (edgesWrapped.length == 0);
	}

	public int size() {
		return edgesWrapped.length;
	}

}
