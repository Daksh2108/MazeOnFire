package project;

import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MazeFire {

	// maze
	public static Node mazeArr[][];

	// Stack for keeping track of shortest path
	public static Stack<String> fringe = new Stack<>();
	//public static Queue<String> fringeBFS = new Queue<>();
	public static Queue<String> fringeBFS = new PriorityQueue<>();

	public static void main(String args[]) {
		// Ask user for dimensions
		System.out.print("Enter dimenstion size: ");
		Scanner sc1 = new Scanner(System.in);
		int size = sc1.nextInt();

		// Ask user for probability maze is filled
		System.out.print("Enter probability cell is filled: ");
		Scanner sc2 = new Scanner(System.in);
		double prob = sc2.nextDouble();

		mazeGenerator(size, prob);
//		mazeArr[0][0].id="_";
//		mazeArr[0][1].id="B";
//		mazeArr[0][2].id="_";
//		mazeArr[0][3].id="B";
//		mazeArr[1][0].id="B";
//		mazeArr[1][1].id="B";
//		mazeArr[1][2].id="B";
//		mazeArr[1][3].id="B";
//		mazeArr[2][0].id="_";
//		mazeArr[2][1].id="B";
//		mazeArr[2][2].id="_";
//		mazeArr[2][3].id="_";
//		mazeArr[3][0].id="_";
//		mazeArr[3][1].id="B";
//		mazeArr[3][2].id="_";
//		mazeArr[3][3].id="_";

		while(true){
			System.out.println("Enter 1 for Problem 1");
			System.out.println("Enter 2 for Problem 2");
			System.out.println("Enter 3 for Problem 3");
			System.out.println("Enter 4 for Problem 4");
			System.out.println("Enter 9 to Quit");
			Scanner sc3 = new Scanner(System.in);
			int menu = sc3.nextInt();
			switch (menu) {
				case 1:
					printMaze(size);
					break;
				case 2:
					printMaze(size);
					System.out.print("Enter starting position (row by col) comma separated: ");
					Scanner sc4 = new Scanner(System.in);
					String startPosition = sc4.nextLine().replaceAll("\\s", "");
					System.out.print("Enter goal position (row by col) comma separated: ");
					Scanner sc5 = new Scanner(System.in);
					String goalPosition = sc5.nextLine().replaceAll("\\s", "");
					problem2(startPosition, goalPosition, size);
					break;
				case 3:
					printMaze(size);
					System.out.print("Enter starting position (row by col) comma separated: ");
					Scanner sc6 = new Scanner(System.in);
					String startPositionBFS = sc6.nextLine().replaceAll("\\s", "");
					System.out.print("Enter goal position (row by col) comma separated: ");
					Scanner sc7 = new Scanner(System.in);
					String goalPositionBFS = sc7.nextLine().replaceAll("\\s", "");
					problem3(startPositionBFS, goalPositionBFS, size);
					break;
					
				case 9:
					System.out.println("Program ended");
					System.exit(0);

			}
		}
	}
	
	// method to construct maze with blocks
	public static void mazeGenerator(int size, double prob) {
		mazeArr = new Node[size][size];
		
		for(int i=0;i<mazeArr.length;i++) {
			for(int j=0;j<mazeArr.length;j++) {
				mazeArr[i][j]=new Node("", null,i,j);
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// making first cell empty
				if (i == 0 && j == 0) {
					mazeArr[i][j].id = "_";
					continue;
				}
				// making last cell empty
				if (i == size - 1 && j == size - 1) {
					mazeArr[i][j].id="_";
					break;
				}
				boolean filled = probability(prob);
				if (filled) {
					mazeArr[i][j].id= "B";
				} else {
					mazeArr[i][j].id = "_";
				}
			}
		}
	}

	// method to calculate if cell is filled based on probability
	public static boolean probability(double prob) {
		double a = Math.random();
		if (prob >= a) {
			return true;
		}
		return false;
	}

	// method to print the maze
	public static void printMaze(int size) {
		int count1 = 0, count2 = 0;
		for (int i = 0; i < size; i++) {
			System.out.print(count1 + " ");
			count1++;
		}
		System.out.println();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(mazeArr[i][j].id + " ");
			}
			System.out.print(count2);
			count2++;
			System.out.println();
		}
	}
	
	// method to solve problem 2
	public static boolean problem2(String startPostion, String goalPostion, int size) {
		String startToken[] = startPostion.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPostion.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);

		fringe.push(startPostion);
		ArrayList<String> closedSet = new ArrayList<>();
		
		while (!fringe.isEmpty()) {
			String currentState = fringe.pop();
			String currentToken[] = currentState.split(",");
			int currentStateRow = Integer.parseInt(currentToken[0]);
			int currentStateCol = Integer.parseInt(currentToken[1]);
			
			if (currentState.equals(goalPostion)) {
				printPath(mazeArr[goalRow][goalCol]);
				return true;
			}
			ArrayList<String> children = findChildren(currentState, size);
			for(int i=0;i<children.size();i++) {
				String getChildIndex =children.get(i);
				String token[] = getChildIndex.split(",");
				
				int row3=Integer.parseInt(token[0]);
				int col3=Integer.parseInt(token[1]);
				if(row3 > size-1 || col3> size-1 || row3 < 0 || col3 < 0) {
					children.remove(i);
					i=0;
				}
			}

			for (int i = 0; i < children.size(); i++) {
				String getChildIndex =children.get(i);
				String token[] = getChildIndex.split(",");
				int row = Integer.parseInt(token[0]);
				int col = Integer.parseInt(token[1]);

				if (!mazeArr[row][col].id.equals("B") && !closedSet.contains(getChildIndex)) {
					fringe.push(getChildIndex);
				    mazeArr[row][col].prev=mazeArr[currentStateRow][currentStateCol];
				}
			}
			closedSet.add(currentState);
		}
		System.out.println("Path does not exist");
		return false;
	}

	// method to find children for that cell
	public static ArrayList<String> findChildren(String currentCell, int size) {
		String currentToken[] = currentCell.split(",");
		int row = Integer.parseInt(currentToken[0]);
		int col = Integer.parseInt(currentToken[1]);
		ArrayList<String> children = new ArrayList<>();
		/*
		 * 0,0-> Down and Right col=0-> Right,up or Down col=size->left, up or Down
		 * row=0->left,right or down row=size->left,right,up size,size->up or left
		 * 
		 */
		if (row == 0 && col == 0) {
			children.add("1,0");
			children.add("0,1");
			return children;
		}
		if (row == size - 1 && col == size - 1) {
			// up
			String row2 = row - 1 + "";
			children.add(row2 + "," + col);
			// left
			String col2 = col - 1 + "";
			children.add(row + "," + col2);
			return children;
		}
		if (col == 0) {
			// right
			String col2=col+1+"";
			children.add(row + "," + col2);
			// up
			String row2 = row - 1 + "";
			children.add(row2 + "," + col);
			// down
			String row3 = row + 1 + "";
			children.add(row3 + "," + col);
			return children;
		}
		if (row == 0) {
			// left
			String col2 = col - 1 + "";
			children.add(row + "," + col2);
			// right
			String col3 = col + 1 + "";
			children.add(row + "," + col3);
			// down
			String row2 = row + 1 + "";
			children.add(row2 + "," + col);
			return children;
		}
		if (row == size - 1) {
			// left
			String col2 = col - 1 + "";
			children.add(row + "," + col2);
			// right
			String col3 = col + 1 + "";
			children.add(row + "," + col3);
			// up
			String row2 = row - 1 + "";
			children.add(row2 + "," + col);
			return children;
		}
		if (col == size - 1) {
			// up
			String row2 = row - 1 + "";
			children.add(row2 + "," + col);
			// left
			String col2 = col - 1 + "";
			children.add(row + "," + col2);
			// down
			String row3 = row + 1 + "";
			children.add(row3 + "," + col);
			return children;
		}
		// general case
		String row2 = row + 1 + "";
		String row3 = row - 1 + "";
		String col2 = col + 1 + "";
		String col3 = col - 1 + "";
		children.add(row2 + "," + col);
		children.add(row3 + "," + col);
		children.add(row + "," + col2);
		children.add(row + "," + col3);
		return children;
	}
	
	//method to print the path
	public static void printPath(Node goal){
        ArrayList<String> arr = new ArrayList<>();
		Node ptr=goal;
		while(ptr!=null){
			arr.add(ptr.row +","+ptr.col);
			ptr=ptr.prev;
		}
		System.out.println("Path:");
		for(int i=arr.size()-1; i>=0;i--) {
			System.out.println(arr.get(i));
		}
	}
	// method to solve problem 3
	public static boolean problem3(String startPostion, String goalPostion, int size) {
		String startToken[] = startPostion.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPostion.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);
		
		fringeBFS.add(startPostion);
		ArrayList<String> closedSet = new ArrayList<>();
		
		while (!fringeBFS.isEmpty()) {
			String currentState = fringeBFS.remove();
			String currentToken[] = currentState.split(",");
			int currentStateRow = Integer.parseInt(currentToken[0]);
			int currentStateCol = Integer.parseInt(currentToken[1]);
			
			if (currentState.equals(goalPostion)) {
				printPath(mazeArr[goalRow][goalCol]);
				return true;
			}
			ArrayList<String> children = findChildren(currentState, size);
			for(int i=0;i<children.size();i++) {
				String getChildIndex =children.get(i);
				String token[] = getChildIndex.split(",");
				
				int row3=Integer.parseInt(token[0]);
				int col3=Integer.parseInt(token[1]);
				if(row3 > size-1 || col3> size-1 || row3 < 0 || col3 < 0) {
					children.remove(i);
					i=0;
				}
			}

			for (int i = 0; i < children.size(); i++) {
				String getChildIndex =children.get(i);
				String token[] = getChildIndex.split(",");
				int row = Integer.parseInt(token[0]);
				int col = Integer.parseInt(token[1]);

				if (!mazeArr[row][col].id.equals("B") && !closedSet.contains(getChildIndex)) {
					fringeBFS.add(getChildIndex);
				    mazeArr[row][col].prev=mazeArr[currentStateRow][currentStateCol];
				}
			}
			closedSet.add(currentState);
		}
		System.out.println("Path does not exist");
		return false;
	}
}
