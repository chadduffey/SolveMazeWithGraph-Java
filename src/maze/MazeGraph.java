package maze;

public class MazeGraph {

	private final int vertices;
	private final int vertical;
	private final int horizontal;
    private int[][] adj_matrix;
 
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
	
}
