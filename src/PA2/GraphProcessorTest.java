package PA2;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;


public class GraphProcessorTest {

	@Test
	public void testFullDFS() throws FileNotFoundException {
		GraphProcessor gp = new GraphProcessor("testCS.txt");
		assertEquals(gp.numVertices, 4);
		
		
	}

}
