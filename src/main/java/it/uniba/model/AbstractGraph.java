package it.uniba.model;

import java.util.ArrayList;

import it.uniba.workdata.User;

public abstract class AbstractGraph {

	abstract void generate(String _inChannel);

	abstract boolean isEmpty();

	abstract boolean containsNode(User node);

	abstract ArrayList<Edge> edgesOutDegree(User node);

	abstract ArrayList<Edge> edgesInDegree(User node);
}
