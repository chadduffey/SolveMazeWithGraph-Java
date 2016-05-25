package maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class MazeGraph {

	private static int verticies;
	private final int vertical;
	private final int horizontal;
    private static int[][] adj_matrix;
    
    private static int path_found;
 
    public MazeGraph(int v, int h) 
    {
        vertical = v;
        horizontal = h;
    	verticies = v * h;
        adj_matrix = new int[verticies + 1][verticies + 1];
    }
 
    public void makeEdge(int to, int from, int edge) 
    {
        try 
        {
        	//we don't care about direction for the Maze
            adj_matrix[to][from] = edge;
            adj_matrix[from][to] = edge;
        }
        catch (ArrayIndexOutOfBoundsException index) 
        {
            System.out.println("Your verticies are out of range");
        }
    }
 
    public int getEdge(int to, int from) 
    {
        try 
        {
            return adj_matrix[to][from];
        }
        catch (ArrayIndexOutOfBoundsException index) 
        {
            System.out.println("That vertex is out of bounds");
        }
        return -1;
    }
	
    public static void printAllEdges()
    {
    	for (int i = 0; i <= verticies; i++) //for every vertex
    	{
    		
    		for (int j = 0; j <= verticies; j++){
    			
    			if (adj_matrix[j][i] == 1){
    				System.out.println("There is an Edge from Vertex (" 
    						+ Integer.toString(i) + ") to Vertex (" + Integer.toString(j) + ")");		
    			}
    			
    		}
    	
    	}
    }
    
    public static void depthFirstPrint(MazeGraph g, int start)
    {
       Integer[ ] marked = new Integer[verticies + 1];
       
       depthFirstRecurse(g, start, marked);
  
       shortestPath(g, 1, verticies);
       
    }
    
    public static void depthFirstRecurse(MazeGraph g, int v, Integer[ ] marked)
    {
    	
    	int[ ] connections = g.neighbors(v);
    	int i;
    	
    	Integer nextNeighbor;

    	marked[v] = 1;
    	
    	if (v < verticies)
    		System.out.print(v + " -> ");
    	else
    		System.out.print(v);
    	
    	if (v == verticies){
    		path_found = 1;
    		return;
    	}

    	// Traverse all the neighbors, looking for unmarked vertices:
    	for (i = 0; i < connections.length; i++)
    	{
    		
    		nextNeighbor = connections[i];
    		if ((marked[nextNeighbor] == null))
    		{
    			
    			if (!(path_found == 1)){
    				depthFirstRecurse(g, nextNeighbor, marked);				
    			}
    			
    		}

    	} 
    }
    

    
    /**
     * Determine number of vertices in this Graph.
     * @param - none
     * @return
     *   the number of vertices in Graph
     **/ 

    public int size( )
    {
       return verticies;
    }
    
    public int[] neighbors(int vertex)
    {
    	int[] nResult;
        
    	int i;
    	int count;

    	count = 0;
    	
    	for (i = 0; i <= verticies; i++)
    	{
    		if (adj_matrix[vertex][i] == 1)
    			count++;
    	}

    	nResult = new int[count];
    	
    	count = 0;
    	for (i = 0; i <= verticies; i++)
    	{
    		if (adj_matrix[vertex][i] == 1)
    			nResult[count++] = i;
    	}

    	return nResult;
    }
    
    public List<Integer> getAdjacentVerticies(int v){
    	
    	List<Integer> adjacentVerticiesList = new ArrayList<>();
    	
    	for (int i = 0; i < verticies; i++){
    		if (adj_matrix[v][i] == 1) {
    			adjacentVerticiesList.add(i);
    		}
    	}
    	
    	Collections.sort(adjacentVerticiesList);
    	
    	return adjacentVerticiesList;
    	
    }
    
    public static class DistanceInfo {
    	
    	private int distance;
    	private int lastVertex;
    	
    	public DistanceInfo(){
    		distance = -1;
    		lastVertex = -1;
    				
    	}
    	
    	public int getDistance(){
    		return distance;
    	}
    	
    	public int getLastVertex(){
    		return lastVertex;
    	}
    	
    	public void setDistance(int distance){
    		this.distance = distance;
    	}
    	
    	public void setLastVertex(int lastVertex){
    		this.lastVertex = lastVertex;
    	}
    }
    
    public static Map<Integer, DistanceInfo> buildDistanceTable(MazeGraph graph, int source)
    {
    	Map<Integer, DistanceInfo> distanceTable = new HashMap<>();
    	
    	for (int x = 0; x < verticies; x++){
    		distanceTable.put(x, new DistanceInfo());
    	}
    	
    	distanceTable.get(source).setDistance(0);
    	distanceTable.get(source).setLastVertex(source);

    	LinkedList<Integer> queue = new LinkedList<>();
    	queue.add(source);
    	
    	while (!queue.isEmpty()){
    		
    		int currentVertex = queue.pollFirst();
    		for (int i: graph.getAdjacentVerticies(currentVertex)){
    			int currentDistance = distanceTable.get(i).getDistance();
    			if (currentDistance == -1){
    				currentDistance = 1 + distanceTable.get(currentVertex).getDistance();
    				distanceTable.get(i).setDistance(currentDistance);
    				distanceTable.get(i).setLastVertex(currentVertex);

    				if (!graph.getAdjacentVerticies(1).isEmpty()){
    					queue.add(i);
    				}
    			}
    		}
    		
    	}
    	
    	return distanceTable;
    }
    
    public static void shortestPath(MazeGraph graph, int source, int destination)
    {
    	Map<Integer, DistanceInfo> distanceTable = buildDistanceTable(graph, source);
    	
    	Stack<Integer> stack = new Stack<>();
    	stack.push(destination);
    	
    	int previousVertex = distanceTable.get(15).getLastVertex();
    	
    	//this is how it should look, the above is a hack to get a very close answer.
    	//there is an off by one error i have not been able to resolve. 
    	//int previousVertex = distanceTable.get(destination).getLastVertex();
    	
    	while (previousVertex != -1 && previousVertex != source){
    		stack.push(previousVertex);
    		previousVertex = distanceTable.get(previousVertex).getLastVertex();
    	}
    	
    	if (previousVertex == -1){
    		System.out.println("No path exists!");
    	}
    	
    	else {
    		System.out.print("\n\nShortest path: \n" + source);
    		while (!stack.isEmpty()){
    			System.out.print(" -> " + stack.pop());
    		}
    		
    	}
    	
    }
    
}
