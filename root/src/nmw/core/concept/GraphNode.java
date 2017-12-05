package nmw.core.concept;

public class GraphNode {
	
	int index;
	
	GraphNode left, right;

	public GraphNode(int index) {
		super();
		this.index = index;
	}

	@Override
	public String toString() {
		return " Node [index=" + index + ", left=" + left + ", right=" + right + "]";
	}

		
}
