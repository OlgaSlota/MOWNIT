package ex3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


public class Sudoku {

	Random rand = new Random();
	
	int[][] currentState;
	boolean empty[][] = new boolean[9][9]; // which fields are still empty 
	boolean emptyInitially[][] = new boolean[9][9];
	int empties = 0 ;
	int fixed[] = new int[10]; // how many times each digit occurs in initial board

	
	private int [][] prepareBoard(String path){
		int [][] board = new int [9][9];
		 Scanner sc;
	     try {
			sc= new Scanner(new File(path));
			int row = 0 ;
			while(sc.hasNextLine()){
				String line[] = sc.nextLine().split(" ");
				for ( int col = 0 ; col<9 ; col++){
					if(line[col].equals("x")){
						board[row][col] = 0; //empty field
						empty[row][col]=true;
						empties++;
					}
					else{
						int number = Integer.parseInt(line[col]);
						board[row][col] = number;
						fixed[number]++;
					}
			}
				row++;
			}
			System.out.println("empty fields: " +empties);
			sc.close();
	     }
	 catch (FileNotFoundException e) {
		System.out.println("Wrong path to Sudoku.");
		return null;
	}
		return board;
	}
	
	
	private void initialize(int[][] board){
		 for (int i = 0; i < 9; i++)
			   for(int j=0 ; j<9 ; j++)
				  emptyInitially[i][j]=empty[i][j];
		 
		currentState = new int [9][9];
		 for (int i = 0; i < 9; i++)
		   for(int j=0 ; j<9 ; j++)
			   if(board[i][j]!=0)
				   currentState[i][j] = board[i][j];
		 
		 for (int i = 1; i < 10; i++){
			 int missing = 9- fixed[i];
			
			 for(int j=0; j<missing;j++){
				 int x = rand.nextInt(9);
				 int y = rand.nextInt(9); 
				
				 while(!empty[x][y]){
					x = rand.nextInt(9);
					y = rand.nextInt(9); 
				}
				currentState[x][y] = i;
				empty[x][y]=false;
				empties--;	 
			 }
	}
	}
	
	
	private double getCost(int[][]board){
		
		double sum =0;
		for (int i = 0 ; i<9 ; i++){
			sum+= checkRow(board[i]);
			sum+= checkColumn(i, board);}
			
		for ( int i =0 ; i<9 ; i+=3)
			for( int j=0 ; j<9 ; j+=3)
			sum+= checkSquare(i,j,board);
		
		return sum;
	}
	
	
	private int checkColumn(int c , int [][] board){
		
		int repeated=0;
		int contained[] = new int[10];
		for(int i = 0 ; i<9 ; i++)
			contained[board[i][c]]++;
		
		for(int i = 1 ; i<10 ; i++)
			if(contained[i]>0)
				repeated+=contained[i]-1;
				
		return repeated;
	}
	
	
	private int checkRow(int []row){
		
		int repeated = 0 ; 
		int contained[] = new int[10];
		for(int i = 0 ; i<9 ; i++)
			contained[row[i]]++;
		
		for(int i = 1 ; i<10 ; i++)
			if(contained[i]>0)
				repeated+=contained[i]-1;
				
		return repeated;
	}
	
	
	private int checkSquare(int a, int b, int [][] board){
		
		int repeated = 0 ; 
		int contained[] = new int[10];
		for(int i = a ; i<a+3 ; i++)
			for (int j = b ; j<b+3 ; j++)	
			contained[board[i][j]]++;
		
		for(int i = 1 ; i<10 ; i++)
			if(contained[i]>0)
				repeated+=contained[i]-1;
				
		return repeated;
	}
	
	
	public double anneal(String path ,double T0)
	{
		int[][] d = prepareBoard(path);
		this.initialize(d);
				 
		int [][] nextState;
	    int iteration = -1;

		double temp=T0;
	    double deltaCost = 0;
	    double coolingRate = 0.9999999;
	    double absoluteTemperature = 0.001;

	    double cost = getCost(currentState);

	    System.out.println("First computed cost= "+cost);
	    
	    while (temp > absoluteTemperature && cost!=0.0){	    	
	    
	    	nextState = getNext(currentState);

	        deltaCost = getCost(nextState) - cost;

	        if ((deltaCost < 0) || (cost > 0 && 
	             Math.exp(-deltaCost / temp) > rand.nextDouble()))
	        {
	            for (int i = 0; i < 9; i++)
	            	for( int j=0 ; j<9 ; j++)
	                currentState[i][j] = nextState[i][j];

	            cost = deltaCost + cost;
	        }
	        temp *= coolingRate;
	        iteration++;
	    }

	   System.out.println("iterations : "+iteration+"\nFinal order: ");
	   for (int i = 0; i < 9; i++){
		   for(int j=0 ; j<9 ; j++)
			   System.out.print( currentState[i][j]+ "  " );
		   System.out.println();
		   }
	   System.out.println("\n Final cost = "+cost);
	   return cost;
	}
	
	
	private int[][] getNext(int[][] current) {
		
		int [][] next = new int [9][9];
		 for (int i = 0; i < 9; i++)
			 for ( int j =0 ; j<9 ; j++)
				 next[i][j]= current[i][j];
		 
		 int ind[] = new int [4]; // indexes of 2 positions to swap
		
		 for (int i = 0; i < 3; i+=2){
		 ind[i]= rand.nextInt(9);
		 ind[i+1]= rand.nextInt(9);
		
		 while(!emptyInitially[ind[i]][ind[i+1]]){
			 ind[i]= rand.nextInt(9);
			 ind[i+1]= rand.nextInt(9);
		 }
		 }
		int tmp = next[ind[0]][ind[1]];
		next[ind[0]][ind[1]] = next[ind[2]][ind[3]];
		next[ind[2]][ind[3]] = tmp;
		
		return next;
	}

	public static void main(String[] args) {
		
		Sudoku s = new Sudoku();
		s.anneal("sudoku.txt",500);
	}

}
