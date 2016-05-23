package maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MazeGraph {

	private static int vertices;
	private final int vertical;
	private final int horizontal;
    private static int[][] adj_matrix;
 
    public MazeGraph(int v, int h) 
    {
        vertical = v;
        horizontal = h;
    	vertices = v * h;
        adj_matrix = new int[vertices + 1][vertices + 1];
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
    	for (int i = 0; i <= vertices; i++) //for every vertex
    	{
    		
    		for (int j = 0; j <= vertices; j++){
    			
    			if (adj_matrix[j][i] == 1){
    				System.out.println("There is an Edge from Vertex (" 
    						+ Integer.toString(i) + ") to Vertex (" + Integer.toString(j) + ")");		
    			}
    			
    		}
    	
    	}
    }
    
    public static void depthFirstPrint(MazeGraph g, int start)
    {
       Integer[ ] marked = new Integer[vertices + 1];
       
       //a stack to store the path we determine best.
       Stack<Integer> perfect_path = new Stack<Integer>();
       
       depthFirstRecurse(g, start, marked, perfect_path);
       
       while (!perfect_path.isEmpty()){
    	   Integer displayval = perfect_path.pop();
    	   System.out.print(displayval + " -> ");
       }
       
    }
    
    public static void depthFirstRecurse(MazeGraph g, int v, Integer[ ] marked, Stack<Integer> perfect_path)
    {
       int[ ] connections = g.neighbors(v);
       int i;
       
       perfect_path.push(v);
       
       Integer nextNeighbor;
       
       marked[v] = 1;
       
       
       // Traverse all the neighbors, looking for unmarked vertices:
       for (i = 0; i < connections.length; i++)
       {
          nextNeighbor = connections[i];
          if ((marked[nextNeighbor] == null))
          {  
        	  depthFirstRecurse(g, nextNeighbor, marked, perfect_path);
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
       return vertices;
    }
    
    public int[] neighbors(int vertex)
    {
    	int[] nResult;
        
    	int i;
    	int count;

    	count = 0;
    	
    	for (i = 0; i < vertices; i++)
    	{
    		if (adj_matrix[vertex][i] == 1)
    			count++;
    	}

    	nResult = new int[count];
    	
    	count = 0;
    	for (i = 0; i < vertices; i++)
    	{
    		if (adj_matrix[vertex][i] == 1)
    			nResult[count++] = i;
    	}

    	return nResult;
    }
}
