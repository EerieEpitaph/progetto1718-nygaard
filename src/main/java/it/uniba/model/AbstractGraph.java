package it.uniba.model;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import it.uniba.controller.ExceptionsHandler;
import it.uniba.workdata.Message;
import it.uniba.workdata.User;

/**
 * 
 * Abstract behavior of a generic Graph representing a social network
 */
public abstract class AbstractGraph {

	/**
	 * Generate a graph of mentions starting with an specified Channel
	 * 
	 * @param inChannel
	 *            <i>String</i> specified channel to parse
	 */
	abstract void generate(String inChannel, Map<String, ArrayList<Message>> message, Map<String, User> users)
			throws ExceptionsHandler;

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
	abstract boolean containsNode(User node) throws ExceptionsHandler;

	/**
	 * Abstract function for finding all edges out degree of specified user
	 * 
	 * @param node
	 *            <i>User</i>
	 * @return <i>ArrayList</i> of Edge containing (<i>From,To,Weight</i>) for each
	 *         edge
	 */
	abstract Collection<Edge> edgesOutDegree(User node);

	/**
	 * Abstract function for finding all edges in degree of specified user
	 * 
	 * @param node
	 *            <i>User</i>
	 * @return <i>ArrayList</i> of Edge containing (<i>From,To,Weight</i>) for each
	 *         edge
	 * @throws ExceptionsHandler 
	 */
	abstract Collection<Edge> edgesInDegree(User node);
}
