package project;

public class Node {
	 String id="";
	 Node prev=null;
	 int row=0;
	 int col=0;
	
	public Node(String id, Node prev,int row, int col) {
		this.id=id;
		this.prev=prev;
		this.row=row;
		this.col=col;
		
	}
	
}
