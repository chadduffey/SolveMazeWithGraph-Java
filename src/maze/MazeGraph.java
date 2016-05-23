package maze;

public class MazeGraph {

	private static int vertices;
	private final int vertical;
	private final int horizontal;
    private static int[][] adj_matrix;
    
    private static int path_found;
 
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

       depthFirstRecurse(g, start, marked);
  
    }
    
    public static void depthFirstRecurse(MazeGraph g, int v, Integer[ ] marked)
    {
    	
    	int[ ] connections = g.neighbors(v);
    	int i;

    	Integer nextNeighbor;

    	marked[v] = 1;

    	if (v < vertices)
    		System.out.print(v + " -> ");
    	else
    		System.out.print(v);
    	
    	if (v == vertices){
    		path_found = 1;
    		return;
    	}

    	// Traverse all the neighbors, looking for unmarked vertices:
    	for (i = 0; i < connections.length; i++)
    	{
    		nextNeighbor = connections[i];
    		if ((marked[nextNeighbor] == null))
    		{  
    			if (!(path_found == 1))
    				depthFirstRecurse(g, nextNeighbor, marked);
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
    	
    	for (i = 0; i <= vertices; i++)
    	{
    		if (adj_matrix[vertex][i] == 1)
    			count++;
    	}

    	nResult = new int[count];
    	
    	count = 0;
    	for (i = 0; i <= vertices; i++)
    	{
    		if (adj_matrix[vertex][i] == 1)
    			nResult[count++] = i;
    	}

    	return nResult;
    }
}
