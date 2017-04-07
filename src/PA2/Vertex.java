/**
 * @author Ben Theisen
 * @author Michael Rupert
 */
package PA2;

import java.util.ArrayList;

public class Vertex {
	public String name;
	public ArrayList<Vertex> edges;
	public ArrayList<String> previousVertex;
	
	public boolean marked;
	
	public Vertex(String name){
		this.name = name;
		this.edges = new ArrayList<Vertex>();
		this.marked = false;
		this.previousVertex = new ArrayList<String>();
	}
}
