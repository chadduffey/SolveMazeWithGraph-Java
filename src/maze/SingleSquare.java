package maze;

/**
* <h1>Square Object for converting Maze to Graph</h1>
* A single square object will be created for every character in a map file
* this way, we can work out exactly what is around each character and how it
* fits into the overall picture programmatically
*
* @author  Chad Duffey
* @version 1.0
* @since   2016-05-24
*/

public class SingleSquare {

	//we are going to map every index of the map into this structure
	//add it to a list to be parsed.
	
	public String content;
	public String left;
	public String right;
	public String top;
	public String bottom;
	public String type; //divider ? D or *
	
	SingleSquare(String c, String l, String r, String t, String b, String ty){
		content = c;
		left = l;
		right = r;
		top = t;
		bottom = b;
		type = ty;
	}
	
	public String toString(){
		return "Content: " + content + ", Left: " + left + ", Right: " + right + ", Top: " + top + ", Bottom: " + bottom + ", Type: " + type;
	}
	
}
