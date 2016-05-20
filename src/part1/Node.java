package part1;

import java.util.ArrayList;

public abstract class Node {
	public final int name; // i regret not having strings for these; this will not work in a larger network.

	private final ArrayList<Edge> parents;
	private final ArrayList<Edge> children;

	private int trueInstances = 1; // MIN VALUE IS ONE
	private int falseInstances = 1;

	/** Requires a unique name relative to all nodes that will ever exist in the same graph. */
	public Node(int name) {
		parents = new ArrayList<Edge>();
		children = new ArrayList<Edge>();
		this.name = name;
	}

	public boolean addChild(int name) {
		return children.add(new EdgeInst(this,new NodeInst(name)));
	}

	public Node getChild(Node n) {
		if (children.size() == 0)
			return null;
		for (Edge e : children) {
			Node node = e.isChild(n);
			if (node != null)
				return node;
		}
		return null;
	}

	/** Get the child at that index */
	public Node getChild(int index) {
		if (index > children.size())
			return null;
		return children.get(index).child;
	}

	/** Returns null if none of this node's immediate children
	 * have that name. */
	public Node getChildByName(int name) {
		if (children.size() == 0)
			return null;
		for (Edge e : children) {
			if (e.child.name == name)
				return e.child;
		}
		return null;
	}

	public Node getParent(Node n) {
		if (parents.size() == 0)
			return null;
		for (Edge e : parents) {
			Node node = e.isParent(n);
			if (node != null)
				return node;
		}
		return null;
	}

	Node findDescendant(int i) {
		if (children.size() < i)
			return children.get(i).child;
		return null;
	}

	public String toStringDescendants() {
		String s = name+" [T"+trueInstances+"|F"+ falseInstances +"] "+(children.size() != 0 ? "=>":"");
		String whitespace = "  ";
		for (int i = 0; i < children.size(); i++) {
			s+= "\n" + whitespace + children.get(i).child.toStringDescendants(whitespace);
		}
		return s;
	}

	public String toStringDescendants(String whitespace) {
		String s = name+" [T"+trueInstances+"|F"+ falseInstances +"] "+(children.size() != 0 ? "=>":"");
		whitespace += "  ";
		for (int i = 0; i < children.size(); i++) {
			s+= "\n" + whitespace + children.get(i).child.toStringDescendants(whitespace);
		}
		return s;
	}

	public String toString() {
		String s = name+" [T"+trueInstances +"|F"+ falseInstances +"] => ";
		for (Edge e : children) {
			s += e.child.name +",";
		}
		return s;
	}

	public boolean equals(Node n) {
		return name == n.name;
	}


	/*
	 * Instance methods
	 *
	 */

	 void addTrueInstances(int i) {
		trueInstances += Math.abs(i);
	}

	 void transferTrueInstancesTo(Node to, int i) {
		if (i >= trueInstances) {
			if (i == trueInstances) {
				System.err.println("You can't have all of them--I need one!");
			} else {
				System.err.println("Cannot transfer "+i+" instances from a node with only "+trueInstances+"--transferring all instead.");
			}
			i = trueInstances - 1;
		}
		this.subtractTrueInstances(i);
		to.addTrueInstances(i);
	}

	 void swapTrueInstancesWith(Node n) {
		int yours = n.trueInstances;
		this.trueInstances = yours;
		n.trueInstances = trueInstances;
	}

	 void subtractTrueInstances(int i) {
		if (i > trueInstances) {
			trueInstances -= i;
		} else {
			trueInstances = 1;
		}
	}



	 void addFalseInstances(int i) {
		falseInstances += Math.abs(i);
	}

	 void transferFalseInstancesTo(Node to, int i) {
		if (i >= falseInstances) {
			if (i == falseInstances) {
				System.err.println("You can't have all of them--I need one!");
			} else {
				System.err.println("Cannot transfer "+i+" falseInstances from a node with only "+falseInstances+"--transferring all instead.");
			}
			i = falseInstances - 1;
		}
		this.subtractFalseInstances(i);
		to.addFalseInstances(i);
	}

	 void swapFalseInstancesWith(Node n) {
		int yours = n.falseInstances;
		this.falseInstances = yours;
		n.falseInstances = falseInstances;
	}

	 void subtractFalseInstances(int i) {
		if (i > falseInstances) {
			falseInstances -= i;
		} else {
			falseInstances = 1;
		}
	}
}
