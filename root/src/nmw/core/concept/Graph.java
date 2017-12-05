package nmw.core.concept;

public class Graph {

	public static void main(String[] args) {

		GraphNode n = null;
		
		n = insert(n, 9);
		n = insert(n, 5);
		n = insert(n, 4);
		n = insert(n, 10);
		n = insert(n, 8);
		n = insert(n, 2);
		n = insert(n, 12);
		
		System.out.println(n);
		
	}
	
	public static GraphNode insert(GraphNode currentNode, int value){
		
		if(currentNode == null){
			
			currentNode = new GraphNode(value);
			
		} else{
		
			if(currentNode.index >= value){
				
				currentNode.left = insert(currentNode.left, value);
				
			}else{
				
				currentNode.right = insert(currentNode.right, value);
			}
		}
		
			
			
				
		return currentNode;
	}
	
	
//	RESULTANT OUTPUT	
//	 Node [index=9, left= Node [index=5, left= Node [index=4, left= Node [index=2, left=null, right=null], right=null], right= Node [index=8, left=null, right=null]], right= Node [index=10, left=null, right= Node [index=12, left=null, right=null]]]



}
