package project;

import java.util.Scanner;

public class MazeFire {

	// maze
	public static char mazeArr[][];

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

                System.out.println("Enter 1 for Problem 1");
                System.out.println("Enter 2 for Problem 2");
                System.out.println("Enter 3 for Problem 3");
                System.out.println("Enter 4 for Problem 4");
                Scanner sc3 = new Scanner(System.in);
                int menu = sc3.nextInt();
                switch(menu){
                        case 1:
                                printMaze(size);
                                break;
                        case 2:

                }       


	}

	// method to construct maze with blocks
	public static void mazeGenerator(int size, double prob) {
		mazeArr = new char[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// making first cell empty
				if (i == 0 && j == 0) {
					mazeArr[i][j] = '_';
					continue;
				}
				// making last cell empty
				if (i == size - 1 && j == size - 1) {
					mazeArr[i][j] = '_';
					break;
				}
				boolean filled = probability(prob);
				if (filled) {
					mazeArr[i][j] = 'B';
				} else {
					mazeArr[i][j] = '_';
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

	//method to print the maze
	public static void printMaze(int size){
		int count1=0,count2 = 0;
		for(int i=0; i<size; i++){
				System.out.print(count1 + " ");
				count1++;
		}
		System.out.println();
		for(int i=0; i<size; i++){
				for(int j=0; j<size; j++){
						System.out.print(mazeArr[i][j] + " ");
				}
				System.out.print(count2);
				count2++;
				System.out.println();
		}
	}

        //method to solve problem 2
		public static 

       
}
