package part1;

public abstract class Edge {
	public final Node parent;
	public final Node child;

	public Edge(Node parent, Node child) {
		this.parent = parent;
		this.child = child;
	}

	public Node isChild(Node n) {
		if (n.equals(child))
			return child;
		return null;
	}

	public Node isParent(Node n) {
		if (n.equals(parent))
			return parent;
		return null;
	}

	public String toString() {
		return parent.name +" => "+ child.name;
	}
}
