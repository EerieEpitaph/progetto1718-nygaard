package it.uniba.model;

import java.util.ArrayList;

import it.uniba.workdata.User;

/**
 * 
 * Abstract behavior of a generic Graph rappresent social network
 */
public abstract class AbstractGraph {

	/**
	 * Generate a graph of mentions starting with an specified Channel
	 * 
	 * @param _inChannel
	 *            <i>String</i> specified channel to parse
	 */
	abstract void generate(String _inChannel);

	/**
	 * Check if the graph is Empty
	 * 
	 * @return <i>boolean</i> if the graph is Empty
	 */
	abstract boolean isEmpty();

	/**
	 * Check if the node is contained in the graph
	 * 
	 * @param node
	 *            <i>User</i>
	 * @return <i>boolean</i> if the <i>node</i> is present in the graph
	 */
	abstract boolean containsNode(User node);

	/**
	 * Abstrac function for finding all edge out degree of specified user
	 * 
	 * @param node
	 *            <i>User</i>
	 * @return <i>Arraylist</i> of Edge contains (<i>From,To,Weight</i>) for each
	 *         edge
	 */
	abstract ArrayList<Edge> edgesOutDegree(User node);

	/**
	 * Abstrac function for finding all edge in degree of specified user
	 * 
	 * @param node
	 *            <i>User</i>
	 * @return <i>Arraylist</i> of Edge contains (<i>From,To,Weight</i>) for each
	 *         edge
	 */
	abstract ArrayList<Edge> edgesInDegree(User node);
}
