package it.uniba.model;

import it.uniba.workdata.User;

public interface IsnaGraph {

	void generateGraph();

	boolean containsNode(User node);

	void edgesOutDegree(User node);

	void edgesInDegree(User node);

	void edgesOutDegreeWeighted(User node);

	void edgesInDegreeWeighted(User node);
}
