package PA2;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class WikiCrawler {

	static final String BASE_URL = "https://en.wikipedia.org";
	private int max;
	private String seedUrl;
	private String fileName;
	// Used to make sure that only the first 'max' pages are visited
	private int count;
	// Used for politness when crawling wikipedia.
	private int pageVisited;

	/**
	 * Constructs the crawler that will help create the web graph.
	 * 
	 * @param seedUrl
	 *            The first relative address or root vertex
	 * @param max
	 *            Max number of pages to be crawled
	 * @param filename
	 *            File name for the output graph
	 */
	public WikiCrawler(String seedUrl, int max, String filename) {
		this.seedUrl = seedUrl;
		this.max = max;
		this.fileName = filename;
		this.count = 0;
		this.pageVisited = 0;
	}

	/**
	 * This method extracts the links from the html page info contained in the
	 * parameter doc.
	 * 
	 * @param doc
	 *            - The
	 * @return arrList - an ArrayList containing the valid links from the doc
	 */
	public ArrayList<String> extractLinks(String doc) {
		ArrayList<String> links = new ArrayList<String>();

		// Create a scanner that parses doc and extracts the valid links
		int index = doc.indexOf("<p>");
		if (index == -1) {
			index = doc.indexOf("<P>");
		}
		String newDoc = doc.substring(index, doc.length() - 1);

		Scanner in = new Scanner(newDoc);
		while (in.hasNext()) {

			String check = in.next();
			if (check.contains("href=")) {
				String link = check.substring(6, check.length() - 1);
				if (link.matches("(/wiki/)(.+)") && !(link.contains("#") || link.contains(":"))) {
					// Valid link add to arraylist
					links.add(link);
				}
			}
		}

		in.close();
		return links;
	}

	/**
	 * Crawls the wikipedia site starting from the seedURL and goes until the
	 * WikiCrawlers max number.
	 * 
	 * @param seedURL
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void crawl() throws IOException, InterruptedException {
		// for each node. call extract links and then go through those...

		// Filewriter to write the graph to a file
		FileWriter out = new FileWriter(this.fileName);
		// First line of the file, the max number of pages to be visited.
		out.write(this.max + "\n");

		Queue<String> q = new LinkedList<String>();
		List<String> visited = new LinkedList<String>();

		q.add(this.seedUrl);
		visited.add(this.seedUrl);
		this.count++;

		while (!q.isEmpty()) {

			// currentPage is the first element of q
			String currentPage = q.poll();
			
			// Access page here and get the html as a string
			URL url = new URL(BASE_URL + currentPage);
			InputStream in = url.openStream();
			BufferedReader html = new BufferedReader(new InputStreamReader(in));
			String doc = "";
			String line = html.readLine();
			while (line != null) {
				doc += " " + line;
				line = html.readLine();
				//System.out.println(doc);
			}

			// Extracting all of the valid links from the currentPage
			ArrayList<String> links = extractLinks(doc);

			// Used for printing the graph to a file.
			String fileOut = "";

			// For each of the valid links on the current page, if the link is
			// not in visited,
			// and we have not found our max number of pages yet, add it to the
			// queue and visited
			// and add the edge to the graph.
			for (String link : links) {

				if (!visited.contains((String) link) && this.count < this.max) {

					// Adding the line containing the node and edge between them
					// for the file graph
					fileOut += currentPage + " " + link + "\n";

					// Adding the page to the queue and visited list
					q.add(link);
					visited.add(link);

					// Incrementing the number of pages visited
					this.count++;

				}
				// Checking to make sure there are no self loops and that the
				// link is among our
				// visited pages.
				else if (!link.equals(currentPage) && visited.contains((String) link)) {
					// Checking to make sure a node does not have multiple edges
					// to one other node
					if (!fileOut.contains(link)) {
						fileOut += currentPage + " " + link + "\n";
					}
				}
			}
			
			//Removes the new line character from the last edge
			if(q.isEmpty()){
				fileOut = fileOut.substring(0, fileOut.length() - 1);
				out.write(fileOut);
			} else { //Otherwise, writes the edge to the file with a newline char
				out.write(fileOut);
			}
			
			// Politeness check. For every 100 pages visited, we wait 3 seconds
			// to reduce server load.
			this.pageVisited++;
			if (this.pageVisited % 100 == 0) {
				// System.out.println("Sleeping");
				Thread.sleep(3000);
			}
		}
		
		out.close();
	}

}
