import java.io.*;
import java.util.*;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		int numNodes = graph.getNbNodes();
		
		int[][] Graph = new int[numNodes][numNodes];  			 // We initialize a graph using a 2-D array.
		for (int u = 0; u < numNodes; u++) 
		{
			for (int v = 0; v < numNodes; v++) 
			{
				if (graph.getEdge(u, v) != null && graph.getEdge(u, v).weight > 0) 
				{
					Graph[u][v] = 1; 
				}
			}
		}
		boolean visited[] = new boolean[numNodes];  			// An array to check if the node has been visited or not.
		Stack.add(source); 										// adding the source node as the first element in our arraylist 
		boolean connected;
		int lastNode; 											// An int to store the last node for our stack.
		while (!Stack.isEmpty())
		 {
			lastNode = Stack.get(Stack.size() - 1);
			if (lastNode == destination) 
			{
				break;
			}
			
			connected = false;
			visited[lastNode] = true;
			
			for (int i = 0; i < numNodes; i++) 
			{
				if (Graph[lastNode][i] == 1 && !visited[i]) 
				{
					Stack.add(i);
					connected = true;
					break;
				}
			}
			if (!connected) 
			{
				Stack.remove(Stack.size() - 1);
			}
		}
		return Stack;
	}
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer = "";
		int maxFlow = 0;
		int numNodes = graph.getNbNodes();

		WGraph rGraph = new WGraph(graph);   									// Create a WGraph object to make a residual graph
		WGraph tGraph = new WGraph(graph);										// A temp graph object
		
		ArrayList<Integer> tempStack;											// temporary arrayList to store nodes for each edge

		for (int i = 0; i < rGraph.getEdges().size(); i++)
		{
			int[] rNodes = rGraph.getEdges().get(i).nodes;
			if (rGraph.getEdge(rNodes[1], rNodes[0]) == null) 
			{
				Edge e = new Edge(rNodes[1], rNodes[0], 0);
				rGraph.addEdge(e);
			}
		}
	
		for (int i = 0; i < tGraph.getEdges().size(); i ++) 
		{
			int[] tNodes = tGraph.getEdges().get(i).nodes;
			graph.setEdge(tNodes[0], tNodes[1], 0);
		}
		
		int u, v;  
		while (!pathDFS (source, destination, rGraph).isEmpty())  				// Find the maximum flow through the path found. 
		 {
			tempStack = pathDFS(source, destination, rGraph);
			int flow = Integer.MAX_VALUE;   									// Path_flow 

			for (int i = tempStack.size() - 1; i > 0; i--) 
			{
				u = tempStack.get(i - 1);
				v = tempStack.get(i);
				flow = Math.min(flow, rGraph.getEdge(u, v).weight);
			}

			for (int i = tempStack.size() - 1; i > 0; i--) 						// We then update the residual capcity of the edges
			{																	// and reverse edges along the path.
				u = tempStack.get(i - 1);
				v = tempStack.get(i);
				int positive_flow = rGraph.getEdge(u, v).weight - flow;
				int backward_flow = rGraph.getEdge(v, u).weight + flow;

				rGraph.setEdge(u, v, positive_flow);
				rGraph.setEdge(v, u, backward_flow);
				
				if (tGraph.getEdge(u, v) != null) 
				{
					graph.setEdge(u, v, graph.getEdge(u, v).weight + flow);
				}
				else {
					graph.setEdge(v, u, graph.getEdge(v, u).weight - flow);
				}
			}
			maxFlow += flow;													// Finally we add the our path flow to our maximum flow.
		}
		 
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer ("fordfulkersonOutput" + ".txt", answer);
		System.out.println(answer);
	}	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
	
}
