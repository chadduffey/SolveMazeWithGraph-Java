package maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		int mapHorizontal = 0;
		int mapVertical = 0;
		
		//----------------
		//Read in the file
		//----------------
		
		//define file
		final String mazefile = "maze01.mz";
		
		//open the Maze file
		String line = null;
		
		//list to save the Maze lines
		List<String> mazeList = new ArrayList<String>();
		
		try {
			
			//open file
			FileReader fileReader = new FileReader(mazefile);
			
			//wrap in buffered reader
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			//parse
			while ((line = bufferedReader.readLine()) != null ){
				//System.out.println(line); //print for debug only
				mazeList.add(line);
			}
			
			//close
			bufferedReader.close();
			
		} catch (FileNotFoundException ex){
			
			System.out.println("Unable to open the input file");
			System.out.println(ex);
		
		} catch (IOException ex){
		
			System.out.println("Unable to read the input file");
			System.out.println(ex);
			
		}
		
		//print the maze
		System.out.println("Original Maze: ");
		for (int i = 1; i < mazeList.size(); i++){
			System.out.println(mazeList.get(i));
		}
		
		//read map dimensions from line 0
		int o = 0;
		for ( String retvar : mazeList.get(0).split("\\s+", 2)){
			if (o == 0)
				mapVertical = Integer.parseInt(retvar);
			if (o == 1)
				mapHorizontal = Integer.parseInt(retvar);
			o++;
		}
		
		System.out.println();
		System.out.println("Expected Verticies:\t" + (mapHorizontal * mapVertical));
		System.out.println("Horizontal Verticies:\t" + mapHorizontal);
		System.out.println("Vertical Vertices:\t" + mapVertical);
		System.out.println();
		
		//--------------
		//Set up the Map
		//--------------
		
		//Create a list that tells us what is going on with 
		//every single square on the board
		
		List<SingleSquare> allSquares = new ArrayList<SingleSquare>();
		
		//Parse all lines of the maze - add each square to the list
		for (int i = 1; i < mazeList.size(); i++){
			
			String tmpC;
			String tmpL;
			String tmpR;
			String tmpT;
			String tmpB;
			String tmpType;
			
			String tmpStr = mazeList.get(i);
			
			//parse one line char by char
			for (int x = 0; x < tmpStr.length(); x++){
				
				//check for the open first square
				char c;
				if (i == 2 && x == 0){
					c = 'S';
				}
				else
				{
					if ((i%2)==0)
					{
						//even
						if ((x%2) == 0){
							if (tmpStr.charAt(x) == '|')
								c = '|';
							else
								c = 'w';
						}		
						else {
							c = tmpStr.charAt(x);
						}
					}
					else
					{
						c = tmpStr.charAt(x);
					}
				}
				
				//process each section of the square object
				
				//C
				tmpC = Character.toString(c);
				
				//L
				if (x == 0)
					tmpL = "X";
				else
					tmpL = Character.toString(tmpStr.charAt(x - 1));
				
				//R
				if (x == tmpStr.length() - 1)
					tmpR = "X";
				else
					tmpR = Character.toString(tmpStr.charAt(x + 1));
				
				//T
				if (i == 1)
				{
					tmpT = "X";
				}
				else
				{
					String tmpTStr = mazeList.get(i - 1);
					char Tc = tmpTStr.charAt(x);
					tmpT = Character.toString(Tc);
				}
				
				//B
				if (i == mazeList.size() -1)
				{
					tmpB = "X";
				}
				else
				{
					String tmpBStr = mazeList.get(i + 1);
					char Tb = tmpBStr.charAt(x);
					tmpB = Character.toString(Tb);
				}
				
				//Type
				if((i%2)==0)
				{
					// even
					tmpType = "*";
				}
				else
				{
					// odd
					tmpType = "D";
				}
				
				allSquares.add(new SingleSquare(tmpC, tmpL, tmpR, tmpT, tmpB, tmpType));
				
			} // end of processing single line
			
			
		} //end of processing all squares
		
		//------------------ List of All Squares Created
		
		
		//set up the Graph
		int v = 0;	
		MazeGraph graph;
		graph = new MazeGraph(mapHorizontal, mapVertical);
		List<Integer> vertexSquares = new ArrayList<Integer>();
		
		//identify vertices and update UI
		System.out.println("Maze component breakdown: ");
		for (int i = 0; i < allSquares.size(); i++){
			System.out.println(allSquares.get(i).toString());
			if (allSquares.get(i).content.equals(" ") && allSquares.get(i).type.equals("*"))
			{			
				v++;
				vertexSquares.add(i);
			}	
		}
		
		System.out.println();
		System.out.println("Components processed: " + allSquares.size());
		System.out.println("Verticies Identified: " + v);

		System.out.println("\nThe Verticies:");
		//broken out into rows
		int hCount = 0;
		for (int i = 0; i < vertexSquares.size(); i++)
		{	
				System.out.println(Integer.toString(i) + ") " + allSquares.get(vertexSquares.get(i)));
				hCount++;
				
				//map into the graph object
				//we should only need look right and down because of ordering
				if (allSquares.get(vertexSquares.get(i)).right.equals(" ") ){
					//we have an edge to the right
					graph.makeEdge(i + 2, i + 1, 1);
				}
				if (allSquares.get(vertexSquares.get(i)).bottom.equals(" ")){
					//we have an edge underneath
					
					if (i < v - mapHorizontal)
						graph.makeEdge(i + mapHorizontal + 1, i + 1, 1);
				}
				
				//reset the row
				if (hCount >= mapHorizontal){
					hCount = 0;
					System.out.println();
				}
		}
		
		try {
			
			System.out.println("\nThe Adjacency Matrix:");
			
			System.out.print("  ");
			
			for (int i = 1; i <= v; i++)
				System.out.print(i + " ");
			System.out.println();
			
			for (int i = 1; i <= v; i++)
			{
				System.out.print(i + " ");
				for (int j = 1; j <= v; j++)
					System.out.print( graph.getEdge(i, j) + " "); 
				System.out.println();
			}

			
		} catch (Exception E){
			
			System.out.println("Something went wrong");
		}
	
		//Print out all Edges
		System.out.println("\nPrinting all Edges:");
		MazeGraph.printAllEdges();
		System.out.println();
		
		//Test neighbors.
		//take out of final.
		
//		int[] test_n = graph.neighbors(15);
//		for (int i = 0; i < test_n.length; i++)
//			System.out.println(test_n[i]); 
		
		//Depth First Print
		System.out.println("A path out of the Maze: ");
		MazeGraph.depthFirstPrint(graph, 1);
		
	} // main
	
} //Main class 
