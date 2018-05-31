package it.uniba.wrapping;

import java.util.Set;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import it.uniba.workdata.User;

/**
 * 
 * Wrapper of Guava's Graph
 *
 */
public final class MentionGraphWrapper {

	private final MutableValueGraph<User, Integer> snagraph;

	/**
	 * Constructor of Wrapper
	 */
	public MentionGraphWrapper() {
		snagraph = ValueGraphBuilder.directed().build();
	}

	/**
	 * Check if Node is present in the graph
	 * 
	 * @param usContained
	 *            User in the graph
	 * @return <i>boolean</i> if <b>Node</b> is present in the graph
	 */
	public boolean contains(final User usContained) {
		return snagraph.nodes().contains(usContained);
	}

	/**
	 * Check if graph is empty
	 * 
	 * @return <i>boolean</i> if graph is Empty
	 */
	public boolean isEmpty() {
		return snagraph.nodes().isEmpty();
	}

	/**
	 * Add an user in the graph
	 * 
	 * @param nodeIn
	 *            User new user in the net
	 */
	public void addNode(final User nodeIn) {
		snagraph.addNode(nodeIn);
	}

	/**
	 * Check if Exists an Edge linking two Users
	 * 
	 * @param nodeIn
	 *            User write mention
	 * @param nodeOu
	 *            User mentioned by NodeIn
	 * @return <i>boolean<i> if nodeIn has mentioned nodeOu
	 */
	public boolean hasEdgeConnecting(final User nodeIn, final User nodeOu) {
		return snagraph.hasEdgeConnecting(nodeIn, nodeOu);
	}

	/**
	 * Return a value of Edge
	 * 
	 * @param nodeIn
	 *            User write mention
	 * @param nodeOut
	 *            User mentioned by NodeIn
	 * @return a value represents weight
	 */
	public int edgeValue(final User nodeIn, final User nodeOut) {
		return snagraph.edgeValue(nodeIn, nodeOut).get();
	}

	/**
	 * 
	 * Delete an Edge in the graph
	 * 
	 * @param nodeIn
	 *            User write mention
	 * @param nodeOut
	 *            User mentioned by NodeIn
	 */
	public void removeEdge(final User nodeIn, final User nodeOut) {
		snagraph.removeEdge(nodeIn, nodeOut);
	}

	/**
	 * Create an Edge in the graph
	 * 
	 * @param nodeIn
	 *            User write mention
	 * @param nodeOut
	 *            User mentioned by NodeIn
	 * @param weight
	 *            <i>int</i> numbers of mentions
	 */
	public void putEdgeValue(final User nodeIn, final User nodeOut, final int weight) {
		snagraph.putEdgeValue(nodeIn, nodeOut, weight);
	}

	/**
	 * 
	 * @param nodeIn
	 *            User node in the graph
	 * @return <i>int</i> numbers of mentions
	 */
	public int inDegree(final User nodeIn) {
		return snagraph.inDegree(nodeIn);
	}

	/**
	 * Nodes contained from graph
	 * 
	 * @return all nodes present in the graph
	 */
	public Set<User> nodes() {
		return snagraph.nodes();
	}

	/**
	 * All users adjacent of an User
	 * 
	 * @param nodeIn
	 *            User node in the Graph
	 * @return Set of Users represents the all adjacentNodes of nodeIn
	 */
	public Set<User> adjacentNodes(final User nodeIn) {
		return snagraph.adjacentNodes(nodeIn);
	}
}
