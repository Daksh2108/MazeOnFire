package project;

import java.util.Scanner;
import java.util.Stack;


import java.util.ArrayList;
import java.util.Arrays;
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

	// Stack for keeping of fringe for DFS
	public static Stack<String> fringe = new Stack<>();
	//queue used for fringe for BFS
	public static Queue<String> fringeBFS = new LinkedList<>();
	//queue used for fringe for Strategy 1
	public static Queue<String> fringeStrategyOne = new LinkedList<>();
	//queue used for fringe for Strategy 2
	public static Queue<String> fringeStrategyTwo = new LinkedList<>();
	//queue used for fringe for Strategy 2
	public static Queue<String> fringeStrategyThree = new LinkedList<>();
	//global closed set for strategy 3
	public static ArrayList<String> closedSet3 = new ArrayList<>();
	//priority queue used for fringe for A*
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
	//priority queue used for fringe for Strategy 3
	public static PriorityQueue<String> probabilityFringe = new PriorityQueue<>(new Comparator<String>(){
		@Override
		public int compare(String s1, String s2) {
			String prob1 = s1.substring(s1.indexOf("|")+1);
			String prob2 = s2.substring(s2.indexOf("|")+1);
			double prob1Num =Double.parseDouble(prob1);
			double prob2Num =Double.parseDouble(prob2);
			if(prob1Num < prob2Num){
				return -1;
			}
			if(prob1Num > prob2Num){
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
		
		while (true) {
			System.out.println("Enter 1 for Problem 1");
			System.out.println("Enter 2 for Problem 2");
			System.out.println("Enter 3 for Problem 3");
			System.out.println("Enter 5 for Strategy 1 in fire maze");
			System.out.println("Enter 6 for Strategy 2 in fire maze");
			System.out.println("Enter 7 for Strategy 3 in fire maze");
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
				
			case 7:
				System.out.println("Enter a q value for the fire");
				Scanner sc7 = new Scanner(System.in);
				double q3 = sc7.nextDouble();
				fireGenerator(size);
				System.out.println("Fire started (labeled F)");
				printMaze(size);
				System.out.println();
				probabilityRunner(startPosition, goalPosition, size, q3);
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
				mazeArr[i][j] = new Node("", null, i, j,0,0,0);
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
		boolean checkEmpty = false;
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				if(mazeArr[i][j].id.equals("_")) {
					checkEmpty = true;
				}
			}
		}
		if(checkEmpty) {
			int i = (int)(Math.random() * size);
			int j = (int)(Math.random() * size);
			while(mazeArr[i][j].id.equals("B")) {
				i = (int)(Math.random() * size);
				j = (int)(Math.random() * size);
			}
			mazeArr[i][j].id = "F";
		}
	}
	
	//method to advance fire
	public static void advanceFire(double q) {
		Node[][] copy = new Node[mazeArr.length][mazeArr.length];
		for(int i=0; i<mazeArr.length; i++) {
			for(int j=0; j<mazeArr.length; j++) {
				copy[i][j] = new Node("", null,0,0,0.0,0.0,0);
				copy[i][j].id = mazeArr[i][j].id;
			}
		}
		for(int i=0; i<mazeArr.length; i++) {
			for(int j=0; j<mazeArr.length; j++) {
				if(!mazeArr[i][j].id.equals("F") && !mazeArr[i][j].id.equals("B")) {
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
		mazeArr[0][0].id = "S";
		mazeArr[size-1][size-1].id = "G";
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
		if(row == 0 && col == size-1) {
			//left
			String col2 = col - 1 + "";
			children.add(row + "," + col2);
			//down
			String row2 = row + 1 + "";
			children.add(row2 + "," + col);
			return children;
		}
		if(row == size-1 && col == 0) {
			//up
			String row2 = row - 1 + "";
			children.add(row2 + "," + col);
			//right
			String col2 = col + 1 + "";
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

	// method to solve problem 3 BFS
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
	//method to run problem 3 A*
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
	
	//method to find the euclidean distance between a spot and the goal
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
	
	//method to perform strategy 1 (BFS)
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
		
		//check to see if initial position is on fire
		if(mazeArr[0][0].id.equals("F")) {
			System.out.println("Agent is on fire in the beginning");
			System.out.println();
			return;
		}
		
		//check to see if goal position is on fire
		if(mazeArr[goalRow][goalCol].id.equals("F")) {
			System.out.println("Goal position is on fire");
			System.out.println();
			return;
		}

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
	
	//method that moves agent along and checks if goal is reached or not
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
			//check to see if goal position is on fire
			if(mazeArr[goalRow][goalCol].id.equals("F")) {
				System.out.println("Goal position is on fire");
				return;
			}
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

	//method used to move agent every step and report current status
	public static void strategy2(String startPosition, String goalPosition, int size, double q){
		String startToken[] = startPosition.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPosition.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);

		ArrayList<String> path = new ArrayList<>();
		
		
		//check to see if initial position is on fire
		if(mazeArr[0][0].id.equals("F")) {
			System.out.println("Agent is on fire in the beginning");
			System.out.println();
			return;
		}
		
		//check to see if goal position is on fire
		if(mazeArr[goalRow][goalCol].id.equals("F")) {
			System.out.println("Goal position is on fire");
			System.out.println();
			return;
		}
		
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
			//check to see if goal position is on fire
			if(mazeArr[goalRow][goalCol].id.equals("F")) {
				System.out.println("Goal position is on fire");
				return;
			}
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

	//method used to do BFS for strategy 2
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
	
	//method used to set the probability a cell will catch on fire in the maze
	public static void probabilityRunner(String startPosition, String goalPosition, int size, double q){
		String startToken[] = startPosition.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPosition.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);

		//make 10 copies of the mazeArr
		Node trial1[][] = new Node[size][size];
		Node trial2[][] = new Node[size][size];
		Node trial3[][] = new Node[size][size];
		Node trial4[][] = new Node[size][size];
		Node trial5[][] = new Node[size][size];
		Node trial6[][] = new Node[size][size];
		Node trial7[][] = new Node[size][size];
		Node trial8[][] = new Node[size][size];
		Node trial9[][] = new Node[size][size];
		Node trial10[][] = new Node[size][size];

		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				trial1[i][j] = new Node("",null,0,0,0.0,0.0,0);
				trial2[i][j] = new Node("",null,0,0,0.0,0.0,0);
				trial3[i][j] = new Node("",null,0,0,0.0,0.0,0);
				trial4[i][j] = new Node("",null,0,0,0.0,0.0,0);
				trial5[i][j] = new Node("",null,0,0,0.0,0.0,0);
				trial6[i][j] = new Node("",null,0,0,0.0,0.0,0);
				trial7[i][j] = new Node("",null,0,0,0.0,0.0,0);
				trial8[i][j] = new Node("",null,0,0,0.0,0.0,0);
				trial9[i][j] = new Node("",null,0,0,0.0,0.0,0);
				trial10[i][j] = new Node("",null,0,0,0.0,0.0,0);

			}
		}
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				trial1[i][j].id = mazeArr[i][j].id;
				trial1[i][j].prev = mazeArr[i][j].prev;
				trial1[i][j].row = mazeArr[i][j].row;
				trial1[i][j].col = mazeArr[i][j].col;
				trial1[i][j].distance = mazeArr[i][j].distance;
				trial1[i][j].eucliDistance = mazeArr[i][j].eucliDistance;
				trial1[i][j].prob = mazeArr[i][j].prob;
				
				trial2[i][j].id = mazeArr[i][j].id;
				trial2[i][j].prev = mazeArr[i][j].prev;
				trial2[i][j].row = mazeArr[i][j].row;
				trial2[i][j].col = mazeArr[i][j].col;
				trial2[i][j].distance = mazeArr[i][j].distance;
				trial2[i][j].eucliDistance = mazeArr[i][j].eucliDistance;
				trial2[i][j].prob = mazeArr[i][j].prob;
				
				trial3[i][j].id = mazeArr[i][j].id;
				trial3[i][j].prev = mazeArr[i][j].prev;
				trial3[i][j].row = mazeArr[i][j].row;
				trial3[i][j].col = mazeArr[i][j].col;
				trial3[i][j].distance = mazeArr[i][j].distance;
				trial3[i][j].eucliDistance = mazeArr[i][j].eucliDistance;
				trial3[i][j].prob = mazeArr[i][j].prob;
				
				trial4[i][j].id = mazeArr[i][j].id;
				trial4[i][j].prev = mazeArr[i][j].prev;
				trial4[i][j].row = mazeArr[i][j].row;
				trial4[i][j].col = mazeArr[i][j].col;
				trial4[i][j].distance = mazeArr[i][j].distance;
				trial4[i][j].eucliDistance = mazeArr[i][j].eucliDistance;
				trial4[i][j].prob = mazeArr[i][j].prob;
				
				trial5[i][j].id = mazeArr[i][j].id;
				trial5[i][j].prev = mazeArr[i][j].prev;
				trial5[i][j].row = mazeArr[i][j].row;
				trial5[i][j].col = mazeArr[i][j].col;
				trial5[i][j].distance = mazeArr[i][j].distance;
				trial5[i][j].eucliDistance = mazeArr[i][j].eucliDistance;
				trial5[i][j].prob = mazeArr[i][j].prob;

				trial6[i][j].id = mazeArr[i][j].id;
				trial6[i][j].prev = mazeArr[i][j].prev;
				trial6[i][j].row = mazeArr[i][j].row;
				trial6[i][j].col = mazeArr[i][j].col;
				trial6[i][j].distance = mazeArr[i][j].distance;
				trial6[i][j].eucliDistance = mazeArr[i][j].eucliDistance;
				trial6[i][j].prob = mazeArr[i][j].prob;
				
				trial7[i][j].id = mazeArr[i][j].id;
				trial7[i][j].prev = mazeArr[i][j].prev;
				trial7[i][j].row = mazeArr[i][j].row;
				trial7[i][j].col = mazeArr[i][j].col;
				trial7[i][j].distance = mazeArr[i][j].distance;
				trial7[i][j].eucliDistance = mazeArr[i][j].eucliDistance;
				trial7[i][j].prob = mazeArr[i][j].prob;
				
				trial8[i][j].id = mazeArr[i][j].id;
				trial8[i][j].prev = mazeArr[i][j].prev;
				trial8[i][j].row = mazeArr[i][j].row;
				trial8[i][j].col = mazeArr[i][j].col;
				trial8[i][j].distance = mazeArr[i][j].distance;
				trial8[i][j].eucliDistance = mazeArr[i][j].eucliDistance;
				trial8[i][j].prob = mazeArr[i][j].prob;
				
				trial9[i][j].id = mazeArr[i][j].id;
				trial9[i][j].prev = mazeArr[i][j].prev;
				trial9[i][j].row = mazeArr[i][j].row;
				trial9[i][j].col = mazeArr[i][j].col;
				trial9[i][j].distance = mazeArr[i][j].distance;
				trial9[i][j].eucliDistance = mazeArr[i][j].eucliDistance;
				trial9[i][j].prob = mazeArr[i][j].prob;
				
				trial10[i][j].id = mazeArr[i][j].id;
				trial10[i][j].prev = mazeArr[i][j].prev;
				trial10[i][j].row = mazeArr[i][j].row;
				trial10[i][j].col = mazeArr[i][j].col;
				trial10[i][j].distance = mazeArr[i][j].distance;
				trial10[i][j].eucliDistance = mazeArr[i][j].eucliDistance;
				trial10[i][j].prob = mazeArr[i][j].prob;
			}
		}

		//run bfs algo on all trial mazes and at the end increment counter for prob by 1 if cell is on fire
		trial1 = strategy3Trial(trial1, startPosition, goalPosition, size, q);
		trial2 = strategy3Trial(trial2, startPosition, goalPosition, size, q);
		trial3 = strategy3Trial(trial3, startPosition, goalPosition, size, q);
		trial4 = strategy3Trial(trial4, startPosition, goalPosition, size, q);
		trial5 = strategy3Trial(trial5, startPosition, goalPosition, size, q);
		trial6 = strategy3Trial(trial6, startPosition, goalPosition, size, q);
		trial7 = strategy3Trial(trial7, startPosition, goalPosition, size, q);
		trial8 = strategy3Trial(trial8, startPosition, goalPosition, size, q);
		trial9 = strategy3Trial(trial9, startPosition, goalPosition, size, q);
		trial10 = strategy3Trial(trial8, startPosition, goalPosition, size, q);
		//run through every trial maze and add up all the prob counters for each cell and set prob in mazeArr
		updateProb(trial1);
		updateProb(trial2);
		updateProb(trial3);
		updateProb(trial4);
		updateProb(trial5);
		updateProb(trial6);
		updateProb(trial7);
		updateProb(trial8);
		updateProb(trial9);
		updateProb(trial10);
		//run priority queue bfs with probability as comparator
		strategy3(startPosition, goalPosition, size, q);
		
	}
	//method to update prob field
	public static void updateProb(Node[][] trial){
		for(int i=0; i<trial.length; i++){
			for(int j=0; j<trial.length; j++){
				if(trial[i][j].id.equals("F")){
					mazeArr[i][j].prob++;
				}
			}
		}
		
	}
	//method to move agent every step and report status
	public static void strategy3(String startPosition, String goalPosition, int size, double q){
		String startToken[] = startPosition.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPosition.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);

		ArrayList<String> path = new ArrayList<>();
		
		//check to see if initial position is on fire
		if(mazeArr[0][0].id.equals("F")) {
			System.out.println("Agent is on fire in the beginning");
			System.out.println();
			return;
		}
		
		//check to see if goal position is on fire
		if(mazeArr[goalRow][goalCol].id.equals("F")) {
			System.out.println("Goal position is on fire");
			System.out.println();
			return;
		}
		
		
		//run a while loop until starting position reaches the goal or gets trapped
		while(!mazeArr[startRow][startCol].id.equals("G")){
			//check if agent is at goal
			//we need to check if the agent is trapped, if so break
			//run BFS with each starting position
			path = strategy3BFS(startPosition, goalPosition, size, q);
			
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
			//check to see if goal position is on fire
			if(mazeArr[goalRow][goalCol].id.equals("F")) {
				System.out.println("Goal position is on fire");
				printMaze(size);
				System.out.println();
				return;
			}
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
			closedSet3.add(startPosition);
	        printMaze(size);
	        System.out.println();
		}
		System.out.println("Agent successfully made it to goal");
	}
	
	//method used to run modified A* on maze
	public static ArrayList<String> strategy3BFS(String startPosition, String goalPosition, int size, double q){
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
		
		probabilityFringe.clear();
		probabilityFringe.add(startPosition + "|" + mazeArr[startRow][startCol].prob);
		//ArrayList<String> closedSet = new ArrayList<>();
		ArrayList<String> arr = new ArrayList<>();
		ArrayList<String> closedSet = new ArrayList<>();

		while (!probabilityFringe.isEmpty()) {
			String currentState = probabilityFringe.remove();
			String currentToken[] = currentState.split(",");
			currentStateRow = Integer.parseInt(currentToken[0]);
			currentStateCol = Integer.parseInt(currentToken[1].substring(0,currentToken[1].indexOf("|")));

			if (mazeArr[currentStateRow][currentStateCol].id.equals("G")) {
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
				if(token[1].indexOf("|") == -1){
					col3 = Integer.parseInt(token[1]);
				}else{
					col3 = Integer.parseInt(token[1].substring(0,token[1].indexOf("|")));
				}
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
				if(token[1].indexOf("|") == -1){
					col = Integer.parseInt(token[1]);
				}else{
					col = Integer.parseInt(token[1].substring(0,token[1].indexOf("|")));
				}
				if (!mazeArr[row][col].id.equals("B") && !mazeArr[row][col].id.equals("F") && !closedSet.contains(getChildIndex + "|" + mazeArr[row][col].prob) && !closedSet3.contains(getChildIndex)) {
					probabilityFringe.add(row + "," + col + "|" + mazeArr[row][col].prob);
					mazeArr[row][col].prev = mazeArr[currentStateRow][currentStateCol];
				}
			}
			closedSet.add(currentState);
		}
		return arr;
	}

	//****************************************Future Predicton Of Fire*************************************************************************/
	//method used to move agent every step in trial maze
	public static Node[][] strategy3Trial(Node trial[][], String startPosition, String goalPosition, int size, double q){
		String startToken[] = startPosition.split(",");
		int startRow = Integer.parseInt(startToken[0]);
		int startCol = Integer.parseInt(startToken[1]);

		String endToken[] = goalPosition.split(",");
		int goalRow = Integer.parseInt(endToken[0]);
		int goalCol = Integer.parseInt(endToken[1]);

		ArrayList<String> path = new ArrayList<>();
		
		//run a while loop until starting position reaches the goal or gets trapped
		while(!trial[startRow][startCol].id.equals("G")){
			//check if agent is at goal
			//we need to check if the agent is trapped, if so break
			//run BFS with each starting position
			path = strategy3TrialBFS(trial,startPosition, goalPosition, size, q);
			
			if(path.size() == 0){
				//System.out.println("There is no path from the agent's current position that reaches the goal");
				return trial;
			}
			//make the agent move to the next step
			//change starting position to next step (agent's current location)
			startPosition = path.get(1);
			String nextToken[] = startPosition.split(",");
			startRow = Integer.parseInt(nextToken[0]);
			startCol = Integer.parseInt(nextToken[1]);
			//generate fire
			advanceFireTrial(trial, q);
			//check to see if agent's cell caught on fire
			if(trial[startRow][startCol].id.equals("F")){
				return trial;
			}
		}
		return trial;
	}
	//method used to run BFS on the trial mazes
	public static ArrayList<String> strategy3TrialBFS(Node trial[][],String startPosition, String goalPosition, int size, double q){
			//resetting the prev pointers to null
			for(int i=0; i<size; i++) {
				for(int j=0; j<size; j++) {
					trial[i][j].prev = null;
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
			
			fringeStrategyThree.clear();
			fringeStrategyThree.add(startPosition);
			ArrayList<String> closedSet = new ArrayList<>();
			ArrayList<String> arr = new ArrayList<>();
	
			while (!fringeStrategyThree.isEmpty()) {
				String currentState = fringeStrategyThree.remove();
				String currentToken[] = currentState.split(",");
				currentStateRow = Integer.parseInt(currentToken[0]);
				currentStateCol = Integer.parseInt(currentToken[1]);
	
				if (currentState.equals(goalPosition)) {
					//strategy1Supplement(goalRow, goalCol, q);
					Node ptr = trial[goalRow][goalCol];
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
	
					if (!trial[row][col].id.equals("B") && !trial[row][col].id.equals("F") && !closedSet.contains(getChildIndex)) {
						fringeStrategyThree.add(getChildIndex);
						trial[row][col].prev = trial[currentStateRow][currentStateCol];
					}
				}
				closedSet.add(currentState);
			}
			return arr;
	}
		
	//method to advance fire on trial mazes
	public static void advanceFireTrial(Node trial[][], double q) {
		Node[][] copy = new Node[trial.length][trial.length];
		for(int i=0; i<trial.length; i++) {
			for(int j=0; j<trial.length; j++) {
				copy[i][j] = new Node("", null,0,0,0.0,0.0,0);
				copy[i][j].id = trial[i][j].id;
			}
		}
		for(int i=0; i<trial.length; i++) {
			for(int j=0; j<trial.length; j++) {
				if(!trial[i][j].id.equals("F") && !trial[i][j].id.equals("B") && !trial[i][j].id.equals("S") && !trial[i][j].id.equals("G")) {
					//check how many neighbors are on fire
					int k = neighborFireCheckTrial(trial,i,j,trial.length);
					double prob = 1 - (Math.pow(1-q, k));
					if(Math.random() <= prob) {
						copy[i][j].id = "F";
					}
				}
			}
		}
		//set mazeArr equal to copy
		for(int i=0; i<trial.length; i++) {
			for(int j=0; j<trial.length; j++) {
				trial[i][j].id = copy[i][j].id;
			}
		}
	}
	//method to check number of neighbors on fire
	public static int neighborFireCheckTrial(Node trial[][],int row, int col, int size) {
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
			if(trial[childRow][childCol].id.equals("F")) {
				count++;
			}
		}
		return count;
	}
	
}
