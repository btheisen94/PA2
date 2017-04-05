package PA2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class GraphProcessor {

	public int numVertices;
	public HashMap<String, Vertex> graph;
	public ArrayList<String>[] stronglyConnected;

	public GraphProcessor(String filePath) throws FileNotFoundException {
		graphMaker(filePath);
		// Need to call DFS searching here to create the list of strongly
		// connected components
	}

	private void graphMaker(String filePath) throws FileNotFoundException {
		// Open a file, scanner that file, populate the hashmap

		File file = new File(filePath);
		Scanner in = new Scanner(file);
		this.numVertices = in.nextInt();
		this.graph = new HashMap<String, Vertex>(this.numVertices, (float) 1.0);

		// String currentLine = in.nextLine();
		String name = "";
		String name2 = "";
		while (in.hasNext()) {
			// Scanner lineScan = new Scanner(in.nextLine());

			name = in.next();
			// System.out.println(name);
			if (!graph.containsKey(name)) {
				Vertex vertex = new Vertex(name);
				graph.put(name, vertex);
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
		for (ArrayList<String> component : stronglyConnected) {
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
		for (ArrayList<String> component : stronglyConnected) {
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

		for (ArrayList<String> component : stronglyConnected) {
			int temp = 0;
			for (String site : component) {
				temp += outDegree(site);
			}
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
		return stronglyConnected.length;
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
		// TODO
		ArrayList<String> path = new ArrayList<String>();
		boolean found = false;
		// Handling if the same vertex is passed in for both parameters
		if (u.equals(v)) {
			path.add(u);
			path.add(v);
			return path;
		} else {
			Queue<String> q = new LinkedList<String>();
			List<String> visited = new LinkedList<String>();

			q.add(u);
			visited.add(u);
			while (!q.isEmpty() && !found) {

				String current = q.poll();
				for (Vertex connected : this.graph.get((String) current).edges) {
					if (connected.name.equals(v)) {
						found = true;
						//Add to path list? break both loops
						//TODO
					} else if (!visited.contains(connected.name)) {
						// Add to queue
						q.add(connected.name);
						// Add to visited
						visited.add(connected.name);
						// Add to path listing thing
					} else {
						// ????
					}
				}

			}
		}

		/*
		 * Input <G(V,E)> Source S Dist[s] = 0 T = {s} while(T != v) Consider
		 * all edges that go from T to v-T For every <V,v>
		 * 
		 */

		/*
		 * Dist[v] will eventually hold the length of the shortest path from s
		 * to v
		 */
		return path;
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
	public ArrayList<String> dfsPath(String u, String v) {
		ArrayList<String> path = new ArrayList<String>();
		boolean found = false;
		// Handling if the same vertex is passed in for both parameters
		if (u.equals(v)) {
			path.add(u);
			path.add(v);
			return path;
		} else {
			Stack<String> q = new Stack<String>();
			List<String> visited = new LinkedList<String>();

			q.add(u);
			visited.add(u);
			while (!q.isEmpty() && !found) {
				String current = q.peek();
				for (Vertex connected : this.graph.get((String) current).edges) {
					if (connected.name.equals(v)) {
						found = true;
						//Add to path list? break both loops
						//TODO
					} else if (!visited.contains(connected.name)) {
						// Add to queue
						q.add(connected.name);
						// Add to visited
						visited.add(connected.name);
						// Add to path listing thing
					} else {
						q.pop();
					}
				}
			}
		}
				
		return path;
	}
	
	public void computeOrder(){
		
	}
	/*
	 * Generate a graph with reversed edges
	 */
	public HashMap<String, Vertex> reverseGraph(){
		HashMap<String, Vertex> graph_r = new HashMap<String, Vertex>(this.numVertices, (float) 1.0);
		
		for(Map.Entry<String, Vertex> vert : this.graph.entrySet()) {
			String key = vert.getKey();
			Vertex value = vert.getValue();
			
			for(Vertex connected : value.edges){
				// Empty edges list
				Vertex oldVert = value;
				oldVert.edges = new ArrayList<Vertex>();
				
				Vertex newVert = new Vertex(connected.name);
				newVert.edges.add(oldVert);
				graph_r.put(connected.name, newVert);
			}
			
		}
		
		return graph_r;
	}
	

	public static void main(String args[]) throws IOException, InterruptedException {
		long start = System.currentTimeMillis();
		WikiCrawler w = new WikiCrawler("/wiki/Computer_Science", 100, "WikiCS.txt");
		w.crawl();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		GraphProcessor gp = new GraphProcessor("WikiCS.txt");
		System.out.println(gp.reverseGraph().toString());
		// System.out.println(gp.graph.toString());
		// System.out.println(gp.outDegree("/wiki/Complexity_theory"));
		// System.out.println(gp.outDegree("/wiki/Complex_system"));
		// System.out.println(gp.outDegree("/wiki/Complex_systems"));
		// System.out.println(gp.outDegree("/wiki/Complexity_theory_and_organizations"));
		// System.out.println(gp.outDegree("/wiki/Complexity_economics"));

	}
}
