package project;

public class Node {
	 String id="";
	 Node prev=null;
	 int row=0;
	 int col=0;
	 double distance=0.0;
	 double eucliDistance=0.0;
	
	public Node(String id, Node prev,int row, int col,double distance,double eucliDistance) {
		this.id=id;
		this.prev=prev;
		this.row=row;
		this.col=col;
		this.distance=distance;
		this.eucliDistance=eucliDistance;
	}
	
}
