package project;

import java.util.Scanner;
import java.util.Stack;


import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;
import java.util.LinkedList;
import java.lang.Math;
import java.util.Random;

public class MazeFire{
	public static final double inf = Double.POSITIVE_INFINITY;
	// maze
	public static Node mazeArr[][];

	// Stack for keeping track of shortest path
	public static Stack<String> fringe = new Stack<>();
	public static Queue<String> fringeBFS = new LinkedList<>();
	public static Queue<String> fringeStrategyOne = new LinkedList<>();
	public static Queue<String> fringeStrategyTwo = new LinkedList<>();
	public static PriorityQueue<String> fringeA = new PriorityQueue<>(new Comparator<String>(){
		public int compare(String s1, String s2){
			String distance1 = s1.substring(s1.indexOf("|")+1);
			String distance2 = s2.substring(s2.indexOf("|")+1);
			double distance1Num =Double.parseDouble(distance1);
			double distance2Num =Double.parseDouble(distance2);
			if(distance1Num < distance2Num){
				return -1;
			}
			if(distance1Num > distance2Num){
				return 1;
			}
			return 0;
		}
	});
    


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
		updateMazeGenerator(size);
		printMaze(size);
		
		
//		mazeArr[0][0].id="S";
//      mazeArr[0][1].id="_";
// 		mazeArr[0][2].id="B";
//		mazeArr[0][3].id="_";
//		mazeArr[0][4].id="B";
//      mazeArr[0][5].id="B";
// 		mazeArr[0][6].id="_";
//		mazeArr[0][7].id="_";
//		mazeArr[0][8].id="B";
//		mazeArr[0][9].id="_";
//		 
// 		mazeArr[1][0].id="B";
//		mazeArr[1][1].id="_";
//		mazeArr[1][2].id="_";
//		mazeArr[1][3].id="B";
//		mazeArr[1][4].id="_";
//		mazeArr[1][5].id="B";
//		mazeArr[1][6].id="_";
//		mazeArr[1][7].id="_";
//		mazeArr[1][8].id="_";
//		mazeArr[1][9].id="_";
//
//		mazeArr[2][0].id="B";
//		mazeArr[2][1].id="B";
//		mazeArr[2][2].id="B";
//		mazeArr[2][3].id="_";
//		mazeArr[2][4].id="B";
//		mazeArr[2][5].id="_";
//		mazeArr[2][6].id="_";
//		mazeArr[2][7].id="_";
//		mazeArr[2][8].id="_";
//		mazeArr[2][9].id="B";
//
//		mazeArr[3][0].id="B";
//		mazeArr[3][1].id="_";
//		mazeArr[3][2].id="_";
//		mazeArr[3][3].id="_";
//		mazeArr[3][4].id="B";
//		mazeArr[3][5].id="_";
//		mazeArr[3][6].id="_";
//		mazeArr[3][7].id="_";
//		mazeArr[3][8].id="B";
//		mazeArr[3][9].id="_";
//
//
//		mazeArr[4][0].id="B";
//		mazeArr[4][1].id="_";
//		mazeArr[4][2].id="B";
//		mazeArr[4][3].id="_";
//		mazeArr[4][4].id="G";
//		mazeArr[4][5].id="_";
//		mazeArr[4][6].id="B";
//		mazeArr[4][7].id="_";
//		mazeArr[4][8].id="_";
//		mazeArr[4][9].id="_";
//		
//		mazeArr[5][0].id="B";
//      mazeArr[5][1].id="_";
// 		mazeArr[5][2].id="B";
//		mazeArr[5][3].id="_";
//		mazeArr[5][4].id="B";
//      mazeArr[5][5].id="B";
// 		mazeArr[5][6].id="B";
//		mazeArr[5][7].id="_";
//		mazeArr[5][8].id="B";
//		mazeArr[5][9].id="_";
//
//		mazeArr[6][0].id="_";
//      mazeArr[6][1].id="_";
// 		mazeArr[6][2].id="_";
//		mazeArr[6][3].id="_";
//		mazeArr[6][4].id="B";
//      mazeArr[6][5].id="B";
// 		mazeArr[6][6].id="_";
//		mazeArr[6][7].id="B";
//		mazeArr[6][8].id="_";
//		mazeArr[6][9].id="B";
//
//		mazeArr[7][0].id="B";
//      mazeArr[7][1].id="_";
// 		mazeArr[7][2].id="_";
//		mazeArr[7][3].id="B";
//		mazeArr[7][4].id="B";
//      mazeArr[7][5].id="_";
// 		mazeArr[7][6].id="B";
//		mazeArr[7][7].id="_";
//		mazeArr[7][8].id="B";
//		mazeArr[7][9].id="_";
//
//		mazeArr[8][0].id="_";
//      mazeArr[8][1].id="_";
// 		mazeArr[8][2].id="_";
//		mazeArr[8][3].id="_";
//		mazeArr[8][4].id="_";
//      mazeArr[8][5].id="_";
// 		mazeArr[8][6].id="_";
//		mazeArr[8][7].id="_";
//		mazeArr[8][8].id="_";
//		mazeArr[8][9].id="_";
//
//		mazeArr[9][0].id="_";
//      mazeArr[9][1].id="B";
// 		mazeArr[9][2].id="_";
//		mazeArr[9][3].id="_";
//		mazeArr[9][4].id="B";
//      mazeArr[9][5].id="B";
// 		mazeArr[9][6].id="_";
//		mazeArr[9][7].id="_";
//		mazeArr[9][8].id="_";
//		mazeArr[9][9].id="G";
		

		while (true) {
			clearFire(size);
			System.out.println("Enter 1 for Problem 1");
			System.out.println("Enter 2 for Problem 2");
			System.out.println("Enter 3 for Problem 3");
			System.out.println("Enter 4 for Problem 4");
			System.out.println("Enter 5 for Strategy 1 in fire maze");
			System.out.println("Enter 6 for Strategy 2 in fire maze");
			System.out.println("Enter 9 to Quit");
			Scanner sc3 = new Scanner(System.in);
			int menu = sc3.nextInt();
			String startPosition = "0,0";
			String goalPosition = (size-1) + "," + (size-1);
			switch (menu) {
			case 1:
				printMaze(size);
				break;
			case 2:
				printMaze(size);
				problem2(startPosition, goalPosition, size);
				break;
			case 3:
				System.out.println("Enter 1 for BFS and 2 for A*");
				Scanner sc4 = new Scanner(System.in);
				String checkAlgo=sc4.nextLine();
				printMaze(size);
				if(checkAlgo.equals("1")){
					problem3BFS(startPosition, goalPosition, size);	
				}
				if(checkAlgo.equals("2")){
					problem3A(startPosition,goalPosition, size);
				}
				break;
			case 4:
				break;
			case 5:
				System.out.println("Enter a q value for the fire");
				Scanner sc5 = new Scanner(System.in);
				double q1 = sc5.nextDouble();
				fireGenerator(size);
				System.out.println("Fire started (labeled F)");
				printMaze(size);
				System.out.println();
				strategy1(startPosition, goalPosition, size, q1);
				break;
			case 6:
				System.out.println("Enter a q value for the fire");
				Scanner sc6 = new Scanner(System.in);
				double q2 = sc6.nextDouble();
				fireGenerator(size);
				System.out.println("Fire started (labeled F)");
				printMaze(size);
				System.out.println();
				strategy2(startPosition, goalPosition, size, q2);
				break;
			case 9:
				System.out.println("Program ended");
				System.exit(0);

			}
		}
	}

	// method to update startPostion and goalPostion
	public static void updateMazeGenerator(int size) {
		mazeArr[0][0].id = "S";
		mazeArr[size-1][size-1].id = "G";
	}

	// method to construct maze with blocks
	public static void mazeGenerator(int size, double prob) {
		mazeArr = new Node[size][size];

		for (int i = 0; i < mazeArr.length; i++) {
			for (int j = 0; j < mazeArr.length; j++) {
				mazeArr[i][j] = new Node("", null, i, j,0,0);
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
					mazeArr[i][j].id = "_";
					break;
				}
				boolean filled = probability(prob);
				if (filled) {
					mazeArr[i][j].id = "B";
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
	
	//method to add fire to the maze
	public static void fireGenerator(int size) {
		int i = (int)(Math.random() * size);
		int j = (int)(Math.random() * size);
		while(mazeArr[i][j].id.equals("S") || mazeArr[i][j].id.equals("G") || mazeArr[i][j].id.equals("B")) {
			i = (int)(Math.random() * size);
			j = (int)(Math.random() * size);
		}
		mazeArr[i][j].id = "F";
	}
	
	//method to advance fire
	public static void advanceFire(double q) {
		Node[][] copy = mazeArr;
		for(int i=0; i<mazeArr.length; i++) {
			for(int j=0; j<mazeArr.length; j++) {
				if(!mazeArr[i][j].id.equals("F") && !mazeArr[i][j].id.equals("B") && !mazeArr[i][j].id.equals("S") && !mazeArr[i][j].id.equals("G")) {
					//check how many neighbors are on fire
					int k = neighborFireCheck(i,j,mazeArr.length);
					double prob = 1 - (Math.pow(1-q, k));
					if(Math.random() <= prob) {
						copy[i][j].id = "F";
					}
				}
			}
		}
		//set mazeArr equal to copy
		for(int i=0; i<mazeArr.length; i++) {
			for(int j=0; j<mazeArr.length; j++) {
				mazeArr[i][j].id = copy[i][j].id;
			}
		}
	}
	//method to check number of neighbors on fire
	public static int neighborFireCheck(int row, int col, int size) {
		String currentState = row + "," + col; 
		ArrayList<String> children = findChildren(currentState, size);
		//filter out the out of bounds children
		for (int i = 0; i < children.size(); i++) {
			String getChildIndex = children.get(i);
			String token[] = getChildIndex.split(",");

			int row3 = Integer.parseInt(token[0]);
			int col3 = Integer.parseInt(token[1]);
			if (row3 > size - 1 || col3 > size - 1 || row3 < 0 || col3 < 0) {
				children.remove(i);
				i = -1;
			}
		}
		int count = 0;
		//count number of children on fire
		for(int i=0; i<children.size(); i++) {
			String token[] = children.get(i).split(",");
			int childRow = Integer.parseInt(token[0]);
			int childCol = Integer.parseInt(token[1]);
			if(mazeArr[childRow][childCol].id.equals("F")) {
				count++;
			}
		}
		return count;
	}
	
	//method to clear the fire
	public static void clearFire(int size) {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				if(mazeArr[i][j].id.equals("F")) {
					mazeArr[i][j].id = "_";
				}
			}
		}
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
	public static void problem2(String startPosition, String goalPosition, int size) {
		String startToken[] = startPosition.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPosition.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);
        int currentStateRow=0;
        int currentStateCol=0;
		fringe.push(startPosition);
		ArrayList<String> closedSet = new ArrayList<>();

		while (!fringe.isEmpty()) {
			String currentState = fringe.pop();
			String currentToken[] = currentState.split(",");
			currentStateRow = Integer.parseInt(currentToken[0]);
			currentStateCol = Integer.parseInt(currentToken[1]);

			if (currentState.equals(goalPosition)) {
				printPath(mazeArr[goalRow][goalCol]);
				return;
			}
			ArrayList<String> children = findChildren(currentState, size);
			for (int i = 0; i < children.size(); i++) {
				String getChildIndex = children.get(i);
				String token[] = getChildIndex.split(",");

				int row3 = Integer.parseInt(token[0]);
				int col3 = Integer.parseInt(token[1]);
				if (row3 > size - 1 || col3 > size - 1 || row3 < 0 || col3 < 0) {
					children.remove(i);
					i = -1;
				}
			}

			for (int i = 0; i < children.size(); i++) {
				String getChildIndex = children.get(i);
				String token[] = getChildIndex.split(",");
				int row = Integer.parseInt(token[0]);
				int col = Integer.parseInt(token[1]);

				if (!mazeArr[row][col].id.equals("B") && !closedSet.contains(getChildIndex)) {
					fringe.push(getChildIndex);
					mazeArr[row][col].prev = mazeArr[currentStateRow][currentStateCol];
				}
			}
			closedSet.add(currentState);
		}
		printPath(mazeArr[currentStateRow][currentStateCol]);
		System.out.println("Path does not exist");
		//return;
	}

	// method to find children for that cell
	public static ArrayList<String> findChildren(String currentCell, int size) {
		String currentToken[] = currentCell.split(",");
		int row = Integer.parseInt(currentToken[0]);
		int col = 0;
		if(currentToken[1].indexOf("|") == -1){
			col = Integer.parseInt(currentToken[1]);
		}else{
			col = Integer.parseInt(currentToken[1].substring(0,currentToken[1].indexOf("|")));
		}
		
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
			String col2 = col + 1 + "";
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

	// method to print the path
	
	public static void printPath(Node goal) {
		ArrayList<String> arr = new ArrayList<>();
		Node ptr = goal;
		while (ptr != null) {
			arr.add("("+ptr.row + "," + ptr.col+")");
			ptr = ptr.prev;
		}
		System.out.println("Path:(row,col)");
		for (int i = arr.size() - 1; i >= 0; i--) {
			System.out.println(arr.get(i));
		}
	}

	// method to solve problem 3
	public static void problem3BFS(String startPosition, String goalPosition, int size) {
		String startToken[] = startPosition.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPosition.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);

		int currentStateRow=0;
		int currentStateCol=0;

		fringeBFS.add(startPosition);
		ArrayList<String> closedSet = new ArrayList<>();

		while (!fringeBFS.isEmpty()) {
			String currentState = fringeBFS.remove();
			String currentToken[] = currentState.split(",");
			currentStateRow = Integer.parseInt(currentToken[0]);
			currentStateCol = Integer.parseInt(currentToken[1]);

			if (currentState.equals(goalPosition)) {
				printPath(mazeArr[goalRow][goalCol]);
				return;
			}
			ArrayList<String> children = findChildren(currentState, size);
			for (int i = 0; i < children.size(); i++) {
				String getChildIndex = children.get(i);
				String token[] = getChildIndex.split(",");

				int row3 = Integer.parseInt(token[0]);
				int col3 = Integer.parseInt(token[1]);
				if (row3 > size - 1 || col3 > size - 1 || row3 < 0 || col3 < 0) {
					children.remove(i);
					i = -1;
				}
			}

			for (int i = 0; i < children.size(); i++) {
				String getChildIndex = children.get(i);
				String token[] = getChildIndex.split(",");
				int row = Integer.parseInt(token[0]);
				int col = Integer.parseInt(token[1]);
				

				if (!mazeArr[row][col].id.equals("B") && !closedSet.contains(getChildIndex)) {
					fringeBFS.add(getChildIndex);
					mazeArr[row][col].prev = mazeArr[currentStateRow][currentStateCol];
				}
			}
			closedSet.add(currentState);
		}
		printPath(mazeArr[currentStateRow][currentStateCol]);
		System.out.println("Path does not exist");
		//return;
	}
	public static void problem3A(String startPositionA, String goalPositionA,int size){
		ArrayList<String> closedSet = new ArrayList<>();
		String startToken[] = startPositionA.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPositionA.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);
		
		//marking all the distances to be infinity  
		for(int i=0;i<mazeArr.length;i++){
			for(int j=0;j<mazeArr.length;j++){
				mazeArr[i][j].distance=inf;
			}
		}
        
		
		mazeArr[startRow][startCol].distance = 0;
		fringeA.add(startPositionA + "|" + mazeArr[startRow][startCol].distance);
		//removed prev[root] = root
		int currentRow=0;
		int currentCol=0;
		while(!fringeA.isEmpty()){
			String currentState = fringeA.poll();
			String currentToken[] = currentState.split(",");
		    currentRow = Integer.parseInt(currentToken[0]);
			currentCol = Integer.parseInt(currentToken[1].substring(0,currentToken[1].indexOf("|")));

			if(!closedSet.contains(currentState)){
				ArrayList<String> children = findChildren(currentState, size);
				for (int i = 0; i < children.size(); i++) {
					String getChildIndex = children.get(i);
					String token[] = getChildIndex.split(",");

					int row3 = Integer.parseInt(token[0]);
					int col3 = Integer.parseInt(token[1]);
					
					if ((row3 > (size - 1)) || (col3 > (size - 1)) || (row3 < 0) || (col3 < 0) ) {
						children.remove(i);
						i = -1;
					}
				}

				//removing blocked cells from children list
				for(int i=0;i<children.size();i++) {
					String temp=children.get(i);
					String token[] = temp.split(",");
					int row = Integer.parseInt(token[0]);
					int col=0;
					if(token[1].indexOf("|") == -1){
						col = Integer.parseInt(token[1]);
					}else{
						col = Integer.parseInt(token[1].substring(0,token[1].indexOf("|")));
					}
					
					if(mazeArr[row][col].id.equals("B")) {
						children.remove(i);
						i=-1;
					}
				}
				
				for (int i = 0; i < children.size(); i++) {
					String getChildIndex = children.get(i);
					String token[] = getChildIndex.split(",");
					int row = Integer.parseInt(token[0]);
					int col=0;
					if(token[1].indexOf("|") == -1){
						col = Integer.parseInt(token[1]);
					}else{
						col = Integer.parseInt(token[1].substring(0,token[1].indexOf("|")));
					}
					double d = mazeArr[currentRow][currentCol].distance;
					double estimation = euclidean(getChildIndex,goalPositionA);
               
					if(d + 1 + estimation < mazeArr[row][col].distance){
						mazeArr[row][col].distance = d + 1;
						fringeA.add(row + "," + col + "|" + (mazeArr[row][col].distance + estimation));
						mazeArr[row][col].prev = mazeArr[currentRow][currentCol];
					}
				}
				closedSet.add(currentState);
			}
		}
		if(mazeArr[goalRow][goalCol].prev == null){
			printPath(mazeArr[currentRow][currentCol]);
			System.out.println("Path does not exist");
			return;
		}
		printPath(mazeArr[goalRow][goalCol]);
		
	}

	public static double euclidean(String currentState, String goalState){
		String startToken[] = currentState.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = 0;
		if(startToken[1].indexOf("|") == -1){
			startCol = Integer.parseInt(startToken[1]);
		}else{
			startCol = Integer.parseInt(startToken[1].substring(0,startToken[1].indexOf("|")));
		}

		String endToken[] = goalState.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);

		return Math.sqrt(((goalCol-startCol)*(goalCol-startCol)) + ((goalRow-startRow)*(goalRow-startRow)));
	}
	
	public static void strategy1(String startPosition, String goalPosition,int size, double q) {
		//compute the shortest path and follow it until agent is trapped and burns or makes his way out
		String startToken[] = startPosition.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPosition.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);

		int currentStateRow=0;
		int currentStateCol=0;

		fringeStrategyOne.add(startPosition);
		ArrayList<String> closedSet = new ArrayList<>();

		while (!fringeStrategyOne.isEmpty()) {
			String currentState = fringeStrategyOne.remove();
			String currentToken[] = currentState.split(",");
			currentStateRow = Integer.parseInt(currentToken[0]);
			currentStateCol = Integer.parseInt(currentToken[1]);

			if (currentState.equals(goalPosition)) {
				strategy1Supplement(goalRow, goalCol, q);
				return;
			}
			ArrayList<String> children = findChildren(currentState, size);
			for (int i = 0; i < children.size(); i++) {
				String getChildIndex = children.get(i);
				String token[] = getChildIndex.split(",");

				int row3 = Integer.parseInt(token[0]);
				int col3 = Integer.parseInt(token[1]);
				if (row3 > size - 1 || col3 > size - 1 || row3 < 0 || col3 < 0) {
					children.remove(i);
					i = -1;
				}
			}

			for (int i = 0; i < children.size(); i++) {
				String getChildIndex = children.get(i);
				String token[] = getChildIndex.split(",");
				int row = Integer.parseInt(token[0]);
				int col = Integer.parseInt(token[1]);

				if (!mazeArr[row][col].id.equals("B") && !mazeArr[row][col].id.equals("F") && !closedSet.contains(getChildIndex)) {
					fringeStrategyOne.add(getChildIndex);
					mazeArr[row][col].prev = mazeArr[currentStateRow][currentStateCol];
				}
			}
			closedSet.add(currentState);
		}
		printPath(mazeArr[currentStateRow][currentStateCol]);
		System.out.println("Path does not exist");
	}

	public static void strategy1Supplement(int goalRow, int goalCol, double q){
		//construct the path
		ArrayList<String> arr = new ArrayList<>();
		Node ptr = mazeArr[goalRow][goalCol];
		while (ptr != null) {
			arr.add(ptr.row + "," + ptr.col);
			ptr = ptr.prev;
		}
		Collections.reverse(arr);
	
		//loop through path one step at a time and at every step increase fire
		int currentRow = 0;
		int currentCol = 0;
		for(int i=1; i<arr.size(); i++){
			String token[] = arr.get(i).split(",");
			currentRow = Integer.parseInt(token[0]);
			currentCol = Integer.parseInt(token[1]);
			System.out.println("Step taken: " + currentRow + "," + currentCol);
			advanceFire(q);
			printMaze(mazeArr.length);
			System.out.println();
			//check to see if agent's cell caught on fire
			if(mazeArr[currentRow][currentCol].id.equals("F")){
				System.out.println("Agent caught on fire");
				return;
			}
			if(i+1<arr.size()){
				String nextindex= arr.get(i+1);
				String token3[]=nextindex.split(",");
				int nextRow = Integer.parseInt(token3[0]);
				int nextCol = Integer.parseInt(token3[1]);
				if(mazeArr[nextRow][nextCol].id.equals("B") || mazeArr[nextRow][nextCol].id.equals("F")){
					System.out.println("Agent's next step " + "(" + nextRow + "," + nextCol + ")" + " leads into a blockage/fire");
					System.out.println("Agent's path before getting trapped:");
					printPath(mazeArr[currentRow][currentCol]);
					return;
				}
			}
		}
		System.out.println("Agent reached goal safely");
		printPath(mazeArr[goalRow][goalCol]);
	}

	public static void strategy2(String startPosition, String goalPosition, int size, double q){
		String startToken[] = startPosition.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPosition.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);

		ArrayList<String> path = new ArrayList<>();
		//run a while loop until starting position reaches the goal or gets trapped
		while(!mazeArr[startRow][startCol].id.equals("G")){
			//check if agent is at goal
			//we need to check if the agent is trapped, if so break
			//run BFS with each starting position
			path = strategy2BFS(startPosition, goalPosition, size, q);
			
			if(path.size() == 0){
				System.out.println("There is no path from the agent's current position that reaches the goal");
				return;
			}
			//make the agent move to the next step
			//change starting position to next step (agent's current location)
			startPosition = path.get(1);
			String nextToken[] = startPosition.split(",");
			startRow = Integer.parseInt(nextToken[0]);
			startCol = Integer.parseInt(nextToken[1]);
			//generate fire
			advanceFire(q);
			//check to see if agent's cell caught on fire
			if(mazeArr[startRow][startCol].id.equals("F")){
				System.out.println("Step taken: " + "(" + startPosition + ")");
				printMaze(size);
				System.out.println();
				System.out.println("Agent caught on fire");
				return;
			}
			//print step they took and maze
			System.out.println("Step taken: " + "(" + startPosition + ")");
	        printMaze(size);
	        System.out.println();
		}
		System.out.println("Agent successfully made it to goal");
	}

	public static ArrayList<String> strategy2BFS(String startPosition, String goalPosition, int size, double q){
		//resetting the prev pointers to null
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				mazeArr[i][j].prev = null;
			}
		}
		String startToken[] = startPosition.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPosition.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);
		
		int currentStateRow=0;
		int currentStateCol=0;
		
		fringeStrategyTwo.clear();
		fringeStrategyTwo.add(startPosition);
		ArrayList<String> closedSet = new ArrayList<>();
		ArrayList<String> arr = new ArrayList<>();

		while (!fringeStrategyTwo.isEmpty()) {
			String currentState = fringeStrategyTwo.remove();
			String currentToken[] = currentState.split(",");
			currentStateRow = Integer.parseInt(currentToken[0]);
			currentStateCol = Integer.parseInt(currentToken[1]);

			if (currentState.equals(goalPosition)) {
				//strategy1Supplement(goalRow, goalCol, q);
				Node ptr = mazeArr[goalRow][goalCol];
				while (ptr != null) {
					arr.add(ptr.row + "," + ptr.col);
					ptr = ptr.prev;
				}
				Collections.reverse(arr);
				return arr;
			}
			ArrayList<String> children = findChildren(currentState, size);
			for (int i = 0; i < children.size(); i++) {
				String getChildIndex = children.get(i);
				String token[] = getChildIndex.split(",");

				int row3 = Integer.parseInt(token[0]);
				int col3 = Integer.parseInt(token[1]);
				if (row3 > size - 1 || col3 > size - 1 || row3 < 0 || col3 < 0) {
					children.remove(i);
					i = -1;
				}
			}

			for (int i = 0; i < children.size(); i++) {
				String getChildIndex = children.get(i);
				String token[] = getChildIndex.split(",");
				int row = Integer.parseInt(token[0]);
				int col = Integer.parseInt(token[1]);

				if (!mazeArr[row][col].id.equals("B") && !mazeArr[row][col].id.equals("F") && !closedSet.contains(getChildIndex)) {
					fringeStrategyTwo.add(getChildIndex);
					mazeArr[row][col].prev = mazeArr[currentStateRow][currentStateCol];
				}
			}
			closedSet.add(currentState);
		}
		return arr;
	}

}
