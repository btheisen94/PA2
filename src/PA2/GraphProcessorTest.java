package PA2;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class GraphProcessorTest {
	
	GraphProcessor gp;
	
	@Before
	public void setUp() throws FileNotFoundException{
		this.gp = new GraphProcessor("testCS.txt");
	}

	@Test
	public void testGraphMaker() throws FileNotFoundException {
		assertEquals(gp.numVertices, 5);
		assertEquals(gp.outDegree("/wiki/Computer_Science"), 4);
		assertEquals(gp.outDegree("/wiki/Computation"), 3);
		assertEquals(gp.outDegree("/wiki/Procedure_(computer_science)"), 2);
		assertEquals(gp.outDegree("/wiki/Computer"), 4);
		
		assertEquals(3, gp.r_graph.get("/wiki/Computer_Science").edges.size());
		assertEquals(1, gp.r_graph.get("/wiki/Computer").edges.size());
		assertEquals(2, gp.r_graph.get("/wiki/Computation").edges.size());
		assertEquals(3, gp.r_graph.get("/wiki/Procedure_(computer_science)").edges.size());
	}
	
	@Test
	public void testFullDFS() throws FileNotFoundException {
		ArrayList<String> expectedDFS = new ArrayList<String>();
		expectedDFS.add("/wiki/Computation");
		expectedDFS.add("/wiki/Computer_Science");
		expectedDFS.add("/wiki/Computer");
		expectedDFS.add("/wiki/Procedure_(computer_science)");
		expectedDFS.add("/wiki/Algorithm");
		assertEquals(expectedDFS, gp.dfsPath);	
	}
	
	@Test
	public void testSCC(){
		gp.SCC(gp.graph);
		assertEquals(gp.maxOutDegree(), 99);
		assertEquals(2, gp.largestComponent());
		assertEquals(9, gp.stronglyConnected.size());
	}

}
