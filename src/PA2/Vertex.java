package PA2;

import java.util.ArrayList;

public class Vertex {
	public String name;
	public ArrayList<Vertex> edges;
	
	public Vertex(String name){
		this.name = name;
		this.edges = new ArrayList<Vertex>();
	}
}
