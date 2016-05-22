package maze;

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
