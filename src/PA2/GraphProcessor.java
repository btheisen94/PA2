/**
 * @author Ben Theisen
 * @author Michael Rupert
 */
package PA2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
//import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
//import java.util.Stack;

public class GraphProcessor {

	public int numVertices;
	public HashMap<String, Vertex> graph;
	public HashMap<String, Vertex> r_graph;
	public HashMap<String, Integer> FinishTime;
	public ArrayList<ArrayList<String>> stronglyConnected;
	public ArrayList<String> dfsPath;
	public int s_index = 0;
	public int counter = 0;
	
	public GraphProcessor(String filePath) throws FileNotFoundException {
		graphMaker(filePath);
		createSCCs();
	
	}

	private void graphMaker(String filePath) throws FileNotFoundException {
		// Open a file, scanner that file, populate the hashmap

		File file = new File(filePath);
		Scanner in = new Scanner(file);
		this.numVertices = in.nextInt();
		this.graph = new HashMap<String, Vertex>(this.numVertices, (float) 1.0);
		this.r_graph = new HashMap<String, Vertex>(this.numVertices, (float) 1.0);

		// String currentLine = in.nextLine();
		String name = "";
		String name2 = "";
		while (in.hasNext()) {
			// Scanner lineScan = new Scanner(in.nextLine());

			name = in.next();
			/*
			 * Normal Graph building
			 */
			if (!graph.containsKey(name)) {
				Vertex vertex = new Vertex(name);
				graph.put(name, vertex);
			}
			if(!in.hasNext()){
				in.close();
				return;
			}
			name2 = in.next();
			// System.out.println(name2);
			if (!graph.containsKey(name2)) {
				Vertex vertex2 = new Vertex(name2);
				graph.put(name2, vertex2);
				// Add edge from name -> name2
				graph.get(name).edges.add(graph.get(name2));
			} else {
				// Add edge from name -> name2
				graph.get(name).edges.add(graph.get(name2));
			}
			
			/*
			 * Reverse Graph Building
			 */
			
			if (!r_graph.containsKey(name)) { // Reverse Graph hasn't gotten name1
				Vertex vertex = new Vertex(name);
				r_graph.put(name, vertex);
			}
			if (!r_graph.containsKey(name2)) { // Reverse Graph hasn't gotten name 2
				Vertex vertex2 = new Vertex(name2);
				r_graph.put(name2, vertex2);
				// Add edge from name2 -> name
				r_graph.get(name2).edges.add(graph.get(name));
			} else {
				// Add edge from name2 -> name2
				r_graph.get(name2).edges.add(graph.get(name));
			}
			
			// lineScan.close();
		}

		in.close();

	}

	/**
	 * Returns the out degree of the given vertex.
	 * 
	 * @param v
	 *            String representing the vertex
	 * @return Number of edges leaving this vertex
	 */
	public int outDegree(String v) {
		return this.graph.get(v).edges.size();
	}

	/**
	 * Returns true if the given vertices are in the same strongly connected
	 * component
	 * 
	 * @param u
	 *            First vertex
	 * @param v
	 *            Second vertex
	 * @return Boolean, true if vertices are in the same component, false
	 *         otherwise
	 */
	public boolean sameComponent(String u, String v) {
		for (ArrayList<String> component : this.stronglyConnected) {
			if (component.contains(u) && component.contains(v))
				return true;
		}
		return false;
	}

	/**
	 * Returns all of the vertices of the strongly connected component that v
	 * belongs to.
	 * 
	 * @param v
	 *            A vertex in a strongly connected component
	 * @return An ArrayList of all the vertices in the same component as v
	 */
	public ArrayList<String> componentVertices(String v) {
		ArrayList<String> temp = new ArrayList<String>();
		for (ArrayList<String> component : this.stronglyConnected) {
			if (component.contains(v)) {
				temp = component;
				break;
			}
		}
		return temp;
	}

	/**
	 * The size of the largest strongly connected components of the graph.
	 * 
	 * @return largest component size
	 */
	public int largestComponent() {

		int max = 0;

		for (ArrayList<String> component : this.stronglyConnected) {
			int temp = component.size();
			if (temp > max) {
				max = temp;
			}
		}
		return max;
	}
	

	/**
	 * Returns the number of strongly connected components in the graph.
	 * 
	 * @return Array of strongly connected components length
	 */
	public int numComponents() {
		return this.stronglyConnected.size();
	}

	/**
	 * Returns the shortest path between the two given nodes. Returns the path
	 * in order as an array list
	 * 
	 * @param u
	 *            The starting vertex
	 * @param v
	 *            The ending vertex
	 * @return An ArrayList containing the shortest path between the two,
	 *         returns an empty list if there is no path
	 */
	public ArrayList<String> bfsPath(String u, String v) {

		ArrayList<String> path = new ArrayList<String>();
		
		boolean found = false;
		// Handling if the same vertex is passed in for both parameters
		if (u.equals(v)) {
			path.add(u);
			path.add(v);
			return path;
		} else if (!this.graph.containsKey(u) || !this.graph.containsKey(v)) {
			return path;
		} else {
			
			//Set each vertex visited flag to false
			for(String flag : this.graph.keySet()){
				this.graph.get(flag).marked = false;
				this.graph.get(flag).previousVertex.clear();
			}
			
			Queue<String> q = new LinkedList<String>();
			//List<String> visited = new LinkedList<String>();

			q.add(u);
			this.graph.get(u).marked = true;
			
			while (!q.isEmpty() && !found) {

				String current = q.poll();
				
				for (Vertex connected : this.graph.get((String) current).edges) {
					System.out.println(connected.name + " " + v);
					if (!this.graph.get(connected.name).marked) {
						// Add to queue
						if(connected.name.equals(v)){
							this.graph.get(v).previousVertex.add(current);
							found = true;
							break;
						}
						q.add(connected.name);
						// Add to visited
						this.graph.get(connected.name).marked = true;
						// visited.add(connected.name);
						// Add to path listing thing
						this.graph.get(connected.name).previousVertex.add(current);
						
					} else {
						if(connected.name.equals(v)){
							this.graph.get(v).previousVertex.add(current);
							found = true;
							break;
						}
						this.graph.get(connected.name).previousVertex.add(current);
					}
				}
			}
		}

		if (found == false) {
			return path;
		} else {
			//Setup up return path here and return.
			path.add(v);
			String current = v;
			while(!current.equals(u)){
				path.add(this.graph.get(current).previousVertex.get(0));
				current = this.graph.get(current).previousVertex.get(0);
			}
			Collections.reverse(path);
			return path;
		}
	}
	

	/**
	 * Helper method to unmark (visited = false) all the vertices in the graph.
	 */
	private void unmarkAll(){
		for(Map.Entry<String, Vertex> E: this.graph.entrySet()){ // unmark every V
			E.getValue().marked = false;
		}
	}
	
	/**
	 * DFS traversal to find the SCCs of the graph
	 * 
	 * @param v Vertex name
	 * @param component Holds the vertices string names of the strongly connected component
	 */
	private void utilDFS(String v, ArrayList<String> component){
		this.r_graph.get(v).marked = true;
		component.add(v);
		
		for(Vertex x : this.r_graph.get(v).edges){
			if(!this.r_graph.get(x.name).marked){
				utilDFS(x.name, component);
			}
		}
	}
	
	/**
	 * Computes the order of the vertices in the graph, used for finding the SCCs
	 * @param v Vertex name
	 * @param stack Holds the order of the vertices as they are visited
	 */
	private void fillOrder(String v, Stack<String> stack){
		this.graph.get(v).marked = true;
		
		for(Vertex x : this.graph.get(v).edges){
			if(!this.graph.get(x.name).marked){
				fillOrder(x.name, stack);
			}
		}
		stack.push(v);
	}
	
	/**
	 * Creates the ArrayLists that contain the strongly connected components.
	 */
	private void createSCCs(){
		Stack<String> stack = new Stack<String>();
		this.stronglyConnected = new ArrayList<ArrayList<String>>();
		
		unmarkAll();
		
		for(String x : this.graph.keySet()){
			if(!this.graph.get(x).marked){
				fillOrder(x, stack);
			}
		}
		
		unmarkAll();
		
		while(!stack.empty()){
			String current = stack.pop();
			ArrayList<String> component = new ArrayList<String>();
			
			if(!this.r_graph.get(current).marked){
				utilDFS(current, component);
				this.stronglyConnected.add(component);
				//System.out.println(this.stronglyConnected.size());
				//System.out.println(component.toString());
			}
		}
		
		
		
	}
	
	public static void main(String args[]) throws IOException, InterruptedException {
		//long start = System.currentTimeMillis();
		//WikiCrawler w = new WikiCrawler("/wiki/Computer_Science", 500, "WikiCS.txt");
		//w.crawl();
		//long end = System.currentTimeMillis();
		//System.out.println(end - start);
		GraphProcessor gp = new GraphProcessor("WikiCS.txt");
		//ArrayList<String> path = gp.bfsPath("/wiki/Computer", "/wiki/Algorithm");
		//System.out.println(path.toString());
		//gp.createSCCs();
		int max = 0;
		String maxVertex = "";
		for(String x : gp.graph.keySet()){
			if(gp.graph.get(x).edges.size() > max){
				max = gp.graph.get(x).edges.size();
				maxVertex = x;
			}
		}
		System.out.println("Vertex with largest out degree: " + maxVertex + ", with a degree of: " + max);
		System.out.println("Number of components: " + gp.numComponents());
		System.out.println("Largest component: " + gp.largestComponent());

	}
}
