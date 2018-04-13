package nmw.core.concept;

import java.util.HashSet;
import java.util.Set;

public class Tree {

	public static void main(String[] args) {

		Node n = new Node(0);

		insert(n, 9);
		insert(n, 5);
		insert(n, 4);
		insert(n, 10);
		insert(n, 8);
		insert(n, 2);
		insert(n, 12);
		insert(n, 11);
		
//		System.out.println("ROOT = "+findRoot(n).index);
		System.out.println("IN-ORDER TRAVERSAL");
		inOrderTraversal(n);
		System.out.println("PRE-ORDER TRAVERSAL");
		preOrderTraversal(n);
		System.out.println("POST-ORDER TRAVERSAL");
		postOrderTraversal(n);
	}
	
	public static Node insert(Node currentNode, int value){
		
		if(currentNode == null){
			
			currentNode = new Node(value);
			
		} else{
		
			if(currentNode.index >= value){
				
				currentNode.left = insert(currentNode.left, value);
				
			}else{
				
				currentNode.right = insert(currentNode.right, value);
			}
		}
		
		return currentNode;
	}
	
	public static void printTree(Node n){
		
		if(n.left != null)
			printTree(n.left);
		
		System.out.println("Node - " + n.index);
		
		if(n.right != null)
			printTree(n.right);
	}
	
	public static Node findRoot(Node n){
		
		Set<Integer> indexes = new HashSet<>();
		
		getUniqueIndex(n, indexes);
		
		int rootIndex = -1;
		
		for(int i : indexes){
			
			if(isRoot(n, i)){
				rootIndex = i;
				break;
			}
		}
		
		return getNodeByIndex(n, rootIndex);
	}
	
	public static void trimNode(Node tn){
		
		if(tn != null){
			
			tn.right = null;
			tn.left = null;
		}
	}
	
	public static Node getNodeByIndex(Node n, int index){
		
		if(n.index == index)
			return n;
		else if(n.left != null && n.left.index == index)
			return n.left;
		else if(n.right != null && n.right.index == index)
			return n.right;

		return null;
	}
	
	public static void getUniqueIndex(Node n, Set<Integer> output){
		
		if(n.left != null)
			getUniqueIndex(n.left, output);

		if(n.right != null)
			getUniqueIndex(n.right, output);
		
		output.add(n.index);
	}
	
	public static void remove(Node tn){
		
	}
	
	public static boolean isRoot(final Node tree, final int index){
		
		boolean output = true;
		
		if(((tree.left != null && tree.left.index == index) || (tree.right != null  && tree.right.index == index)))
			return false;
			
		if(tree.left != null)
			output &= isRoot(tree.left, index);
		
		if(tree.right != null)
			output &= isRoot(tree.right, index);
		
		return output;
	}
	
	public static void inOrderTraversal(Node n){
		
		if(n.left != null)
			inOrderTraversal(n.left);
		
		System.out.println("Node - " + n.index);
		
		if(n.right != null)
			inOrderTraversal(n.right);
	}
	
	public static void preOrderTraversal(Node n){
		
		System.out.println("Node - " + n.index);
		
		if(n.left != null)
			preOrderTraversal(n.left);
		
		if(n.right != null)
			preOrderTraversal(n.right);
	}
	
	public static void postOrderTraversal(Node n){
		
		if(n.left != null)
			postOrderTraversal(n.left);
		
		if(n.right != null)
			postOrderTraversal(n.right);
		
		System.out.println("Node - " + n.index);
	}
	
	
//	RESULTANT OUTPUT	
//	 Node [index=9, left= Node [index=5, left= Node [index=4, left= Node [index=2, left=null, right=null], right=null], right= Node [index=8, left=null, right=null]], right= Node [index=10, left=null, right= Node [index=12, left=null, right=null]]]



}
