package thething.one.cyto.dataobjects;

import java.util.HashSet;
import java.util.Set;

public class CytoWrapper {

	private Set<CytoNode> nodes;
	private Set<CytoNode> edges;
	
	
	public CytoWrapper() {
		 nodes = new HashSet<>();
		 edges = new HashSet<>();
	}

	public CytoWrapper(Set<CytoNode> nodes, Set<CytoNode> edges) {
		super();
		this.nodes = nodes;
		this.edges = edges;
	}

	public Set<CytoNode> getNodes() {
		return nodes;
	}
	public void setNodes(Set<CytoNode> nodes) {
		this.nodes = nodes;
	}

	public Set<CytoNode> getEdges() {
		return edges;
	}
	public void setEdges(Set<CytoNode> edges) {
		this.edges = edges;
	}
	
	
}
