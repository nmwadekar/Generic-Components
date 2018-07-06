package nmw.core.concept;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DirectedGraph {

	private Set<Vertex> vertices = new HashSet<DirectedGraph.Vertex>();
	private Set<Edge> edges = new HashSet<DirectedGraph.Edge>();
	
	public static void main(String[] args) {

		DirectedGraph G = new DirectedGraph();
		
		Vertex a = G.new Vertex("A");
		Vertex b = G.new Vertex("B");
		Vertex c = G.new Vertex("C");
		Vertex d = G.new Vertex("D");
		Vertex e = G.new Vertex("E");
		Vertex f = G.new Vertex("F");
		
		G.vertices.add(a);
		G.vertices.add(b);
		G.vertices.add(c);
		G.vertices.add(d);
		G.vertices.add(e);
		
		Edge ab = G.new Edge(a,b);
		Edge ac = G.new Edge(a,c);
		Edge cd = G.new Edge(c,d);
		Edge ea = G.new Edge(e,a);
		
		G.edges.add(ab);
		G.edges.add(ac);
		G.edges.add(cd);
		G.edges.add(ea);
		
		findEdgeFromVertexTest(G, e, b, null);
		
		test_2();
	}
	
	private static void test_2(){
		
DirectedGraph G = new DirectedGraph();
		
		Vertex a = G.new Vertex("A");
		Vertex b = G.new Vertex("B");
		Vertex c = G.new Vertex("C");
		Vertex d = G.new Vertex("D");
		Vertex e = G.new Vertex("E");
		Vertex f = G.new Vertex("F");
		Vertex g = G.new Vertex("G");
		Vertex h = G.new Vertex("H");
		
		G.vertices.add(a);
		G.vertices.add(b);
		G.vertices.add(c);
		G.vertices.add(d);
		G.vertices.add(e);
		G.vertices.add(f);
		G.vertices.add(g);
		G.vertices.add(h);
		
		Edge ab = G.new Edge(a,b);
		Edge af = G.new Edge(a,f);
		Edge fe = G.new Edge(f,e);
		Edge be = G.new Edge(b,e);
		Edge bc = G.new Edge(b,c);
		Edge bd = G.new Edge(b,d);
		Edge cd = G.new Edge(c,d);
		Edge eg = G.new Edge(e,g);
		Edge dg = G.new Edge(d,g);
		Edge dh = G.new Edge(d,h);
		Edge gh = G.new Edge(g,h);
		
		G.edges.add(ab);
		G.edges.add(af);
		G.edges.add(fe);
		G.edges.add(be);
		G.edges.add(bc);
		G.edges.add(bd);
		G.edges.add(cd);
		G.edges.add(eg);
		G.edges.add(dg);
		G.edges.add(dh);
		G.edges.add(gh);
		
		findEdgeFromVertexTest(G, a, g, null);
	}
	
	private static Vertex findEdgeFromVertexTest(DirectedGraph G, Vertex from, Vertex to, String currentVertex){
		
//		System.out.println("processing for - from - "+from+" to "+to+" current "+currentVertex);
		
		if(currentVertex == null)
			currentVertex = from.toString();
		
		for(Edge e : G.edges){
			
			if(from.equals(e.getFromVertex())){
				
//				System.out.println("1-"+from);
//				System.out.println("2-"+e.getToVertex());
				
				if(to.equals(e.getToVertex())){
					
					currentVertex = currentVertex+e.getToVertex().toString();
					
					return G.new Vertex(currentVertex);
					
				}
				
//				System.out.println("+"+currentVertex);
				
				Vertex current = findEdgeFromVertexTest(G, e.getToVertex(), to, currentVertex+e.getToVertex().toString());
				
				if(current !=null)
					System.out.println("PATH - " + current);
				
			}
		}
		
		return null;
	}
	
/*	public static void printPath(MyDirectedGraph G, Vertex from, Vertex to){
		
		
		
	}*/
	
	private class Vertex {
		
		private String name;

		public Vertex(String name) {
			super();
			this.name = name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
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
			Vertex other = (Vertex) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		private DirectedGraph getOuterType() {
			return DirectedGraph.this;
		}

		@Override
		public String toString() {
			return "["+ name +"]";
		}
		
	}
	
	private class Edge {
		
		private Vertex a, b;

		public Edge(Vertex a, Vertex b) {
			super();
			this.a = a;
			this.b = b;
		}
		
		public Vertex getFromVertex(){
			return a;
		}
		
		public Vertex getToVertex(){
			return b;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((a == null) ? 0 : a.hashCode());
			result = prime * result + ((b == null) ? 0 : b.hashCode());
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
			Edge other = (Edge) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (a == null) {
				if (other.a != null)
					return false;
			} else if (!a.equals(other.a))
				return false;
			if (b == null) {
				if (other.b != null)
					return false;
			} else if (!b.equals(other.b))
				return false;
			return true;
		}

		private DirectedGraph getOuterType() {
			return DirectedGraph.this;
		}
		
		
	}
}
