package nmw.core.concept;

public class Node {
	
	int index;
	
	Node left, right;

	public Node(int index) {
		super();
		this.index = index;
	}

	@Override
	public String toString() {
		return " Node [index=" + index + ", left=" + left + ", right=" + right + "]";
		
//		return "[" + left + ", " + index +  ", " + right + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (index != other.index)
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}
	
}
