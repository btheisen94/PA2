package PA2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GraphProcessor {

	public int numVertices;
	public HashMap<String, Vertex> graph;
	public ArrayList<String>[] stronglyConnected;
	

	public GraphProcessor(String filePath) throws FileNotFoundException {
		// Open a file, scanner that file, populate the hashmap

		File file = new File(filePath);
		Scanner in = new Scanner(file);
		this.numVertices = in.nextInt();
		this.graph = new HashMap<String, Vertex>(this.numVertices, (float) 1.0);
		
		//String currentLine = in.nextLine();
		String name = "";
		String name2 = "";
		while (in.hasNext()) {
			//Scanner lineScan = new Scanner(in.nextLine());
			
			name = in.next();
			//System.out.println(name);
			if(!graph.containsKey(name)){
				Vertex vertex = new Vertex(name);
				graph.put(name, vertex);
			}
				
			name2 = in.next();
			//System.out.println(name2);
			if(!graph.containsKey(name2)){
				Vertex vertex2 = new Vertex(name2);
				graph.put(name2, vertex2);
				//Add edge from name -> name2
				graph.get(name).edges.add(graph.get(name2));
			} else {
				//Add edge from name -> name2
				graph.get(name).edges.add(graph.get(name2));
			}
			//lineScan.close();
		}
		
		in.close();
		

	}

	private void graphMaker(String filePath) throws FileNotFoundException {
		try{
		File file = new File(filePath);
		Scanner in = new Scanner(file);
		this.numVertices = in.nextInt();
		this.graph = new HashMap<String, Vertex>(this.numVertices, (float) 1.0);
		
		//String currentLine = in.nextLine();
		String name = "";
		String name2 = "";
		while (in.hasNextLine()) {
			Scanner lineScan = new Scanner(in.nextLine());
			
			name = lineScan.next();
			if(!graph.containsKey(name)){
				Vertex vertex = new Vertex(name);
				graph.put(name, vertex);
			}
				
			name2 = lineScan.next();
			if(!graph.containsKey(name2)){
				Vertex vertex2 = new Vertex(name2);
				graph.put(name2, vertex2);
				//Add edge from name -> name2
				graph.get(name).edges.add(graph.get(name2));
			} else {
				//Add edge from name -> name2
				graph.get(name).edges.add(graph.get(name2));
			}
			lineScan.close();
		}
		
		in.close();
		} catch(FileNotFoundException e){
			System.out.println("File not there?" + e.getMessage());
		}
	}

	public int outDegree(String v) {
		return this.graph.get(v).edges.size();
	}

	public boolean sameComponent(String u, String v) {

		return false;
	}

	public ArrayList<String> componentVertices(String v) {
		ArrayList<String> vertices = new ArrayList<String>();

		return vertices;
	}

	public int largestComponent() {
		
		return 0;
	}

	public int numComponents() {

		return 0;
	}

	public ArrayList<String> bfsPath(String u, String v) {
		ArrayList<String> path = new ArrayList<String>();

		return path;
	}
	
	public static void main(String args[]) throws IOException, InterruptedException {

		WikiCrawler w = new WikiCrawler("/wiki/Complexity_theory", 30, "WikiCS.txt");
		w.crawl();
		
		GraphProcessor gp = new GraphProcessor("/home/btheisen/workspace/PA2/WikiCS.txt");
		//System.out.println(gp.graph.toString());
		System.out.println(gp.outDegree("/wiki/Complexity_theory"));
		System.out.println(gp.outDegree("/wiki/Complex_system"));
		System.out.println(gp.outDegree("/wiki/Complex_systems"));
		System.out.println(gp.outDegree("/wiki/Complexity_theory_and_organizations"));
		System.out.println(gp.outDegree("/wiki/Complexity_economics"));
		
	}
}
