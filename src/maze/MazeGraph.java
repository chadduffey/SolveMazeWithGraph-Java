package maze;

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
    
}
