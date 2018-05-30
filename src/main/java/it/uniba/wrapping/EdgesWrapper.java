package it.uniba.wrapping;

import java.util.Collection;

import it.uniba.model.Edge;
import it.uniba.workdata.User;

/**
 * This class is used to access to the value of the Edges. (It's an example of
 * to respect the Law of Demeter).
 */
public final class EdgesWrapper {
	 private final Object[] edgesWrapped;

	/**
	 * EdgesWrapper's constructor.
	 * 
	 * @param edges
	 *            <i>Collection</i> of edges
	 */
	public EdgesWrapper(final Collection<Edge> edges) {
		edgesWrapped = edges.toArray();
	}

	/**
	 * Returns the number of mentions of the edge in the position specified.
	 * 
	 * @param position
	 *            <i>int</i>
	 * @return <i>float</i> number of mention
	 */
	public float getWeight(final int position) {
		final Edge edgeTMP = new Edge((Edge) (edgesWrapped[position]));
		return edgeTMP.getWeigth();
	}

	/**
	 * Returns the real name of the user in the position specified that is mentioned
	 * from someone.
	 * 
	 * @param position
	 *            <i>int</i>
	 * @return <i>String</i> the real name of the user mentioned
	 */
	public String getFromRealName(final int position) {
		final Edge edgeTMP = new Edge((Edge) (edgesWrapped[position]));
		final User from = new User(edgeTMP.getFrom());
		return from.getRealName();
	}

	/**
	 * Returns the real name of the user in the position specified that has
	 * mentioned someone.
	 * 
	 * @param position
	 *            <i>int</i>
	 * @return <i>String</i> the real name of the user that has mentioned someone
	 */
	public String getToRealName(final int position) {
		final Edge edgeTMP = new Edge((Edge) (edgesWrapped[position]));
		final User toTMP = new User(edgeTMP.getTo());
		return toTMP.getRealName();
	}

	/**
	 * Returns true if there aren't edges else false.
	 * 
	 * @return <i>boolean</i> true if it's empty
	 */
	public boolean isEmpty() {
		return (edgesWrapped.length == 0);
	}

	/**
	 * Returns the number of the edges.
	 * 
	 * @return <i>int</i> number of edges
	 */
	public int size() {
		return edgesWrapped.length;
	}

}
