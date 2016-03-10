package lab2;

import java.util.Random;

public class GaussJordan {

	double[] gaussJordan(double A [][], double b[], int N){
		double x[] = new double[N];  // array of unknowns x0, x1, x2, x3,..,xN
		int ind[] = new int[N];  // array with indexes of x, to operate on it before we find actual values of x
		
		for (int i=0; i<N ; i++){
			ind[i]=i;
	}
		for(int col=0 ; col<N ; col++){
			this.pivoting(col, N, A, b, ind);
			for(int row=0; row<N ; row++ ){
				if(row ==col && row == N - 1)
					break;
				if(row==col )
					row++;
				
				this.calculateRow(row, col, A, b , N);  // A[row] -=( A[row][col]/A[col][col]) * A[col]
			}
		}
		for ( int i=0 ; i< N ; i++){
			x[i] = b[i]/A[ind[i]][ind[i]];
		}
		
		return x;
	}
	
	void calculateRow(int row , int col, double A[][] ,double b[], int N){
		double multi =  A[row][col]/A[col][col];		
		for ( int i = 0 ; i<N; i++){
			A[row][i] -= multi * A[col][i];	
			}
		b[row] -= multi * b[col];
		
	}
	
	void swap(double x , double y){
		double tmp = x;
		x=y;
		y=tmp;
	}
	
	void swapRows(double [] first, double [] second , int N){
		for ( int i =0 ; i<N ; i++){
			this.swap(first[i], second[i]);
		}
	}
	
	void pivoting(int start, int N , double A[][], double b[], int ind[]){
		int x =0;
		int y =0 ;
		double max =0;
		for (int i =start ; i< N ; i++ )
		for ( int j = start ; j<N ; j++){
			if (A[i][j]>Math.abs(max)){
				max = A[i][j];
				x= i;
				y =j;
			}	
		}
		
		for (int i=0 ; i<N ; i++){
			this.swap(A[i][start], A[i][y]);
	}	
		this.swap(ind[start], ind[y]);
		
		this.swapRows(A[start], A[x],N);
		this.swap(b[start], b[x]);
		
	}
	public static void main( String [] args){
		
		long begin =System.nanoTime();
		
		int n = 600;
		Random r = new Random();
		GaussJordan z = new GaussJordan();	
			double A [][]= new double[n][n];
			double b[] = new double[n];
			
			for (int i=0; i< n ; i++)
				for(int j =0 ; j< n ; j++){
					A[i][j] = r.nextDouble();
					b[j]= r.nextDouble();
						}
	/*		
			A[0][0]= 2.;
			A[0][1] = 4.;
			A[1][0] = 3.;
			A[1][1] = -2.;
			
			b[0] = 22;
			b[1] = 9;
	*/		
			double res[] = new double[n];
			res = z.gaussJordan(A, b, n);
			for(int i =0 ; i<n ; i++){
				System.out.print(res[i]+"   ");	
			}
			System.out.println("\n\nMATRIX AFTER CALCULATION:\n");
			
			
			for(int i =0 ; i<n ; i++){
				for(int j =0 ; j<n ; j++)
					System.out.printf("%.8f ",A[i][j]);
				System.out.println();
			}
			
			System.out.println("\nTotal time : " + (System.nanoTime()-begin) + " ns");
	}
	
	}

