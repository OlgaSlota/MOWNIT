package lab2;

import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

public class LU {

		double[] gaussJordan(double A [][], double b[], int N,double[][] L){
			double x[] = new double[N];  // array of unknowns x0, x1, x2, x3,..,xN
			int ind[] = new int[N];  // array with indexes of x, to operate on it before we find actual values of x
			 
			for (int i=0; i<N ; i++){
				ind[i]=i;
		}
			for(int col=0 ; col<N ; col++){
				this.pivoting(col, N, A, b, ind);
				for(int row=col+1; row<N ; row++ ){
					this.calculateRow(row, col, A, b , N,L); 
				}
			}
			for ( int i=0 ; i< N ; i++){
				x[i] = b[i]/A[i][i];
			}
			
			return x;
		}
		
		void calculateRow(int row , int col, double A[][] ,double b[], int N, double[][] L){
			double multi =  A[row][col]/A[col][col];		
			for ( int i = 0 ; i<N; i++){
				A[row][i] -= multi * A[col][i];	
				}
			b[row] -= multi * b[col];
			L[row][col]=multi;
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
			int j =start ;	
			for ( int i = start ; i<N ; i++){
				if (A[i][j]>Math.abs(max)){
					max = A[i][j];
					x= i;
					y =j;
				}	
			}
			this.swapRows(A[start], A[x],N);
			this.swap(b[start], b[x]);
			
		}
		
		void showResults(int n, double A [][] , double b[], double [][] L ){
			
			LU z = new LU();	
					
				double res[] = new double[n];
				res = z.gaussJordan(A, b, n,L);
				
				System.out.println("U MATRIX AFTER CALCULATION:\n");
				for(int i =0 ; i<n ; i++){
					for(int j =0 ; j<n ; j++)
						System.out.printf("%16f ",A[i][j]);
					System.out.println();
				}
				System.out.println("L MATRIX AFTER CALCULATION:\n");
				for(int i =0 ; i<n ; i++){
					for(int j =0 ; j<n ; j++)
						System.out.printf("%16f ",L[i][j]);
					System.out.println();
				}
				System.out.println("Solution:");
				for(int i =0 ; i<n ; i++){
					System.out.print(res[i]+" ");	
				}
				
				System.out.println("\n");
		}
		
	double[][] prepareL(int n){
		double [] [] L = new double[n][n];
		for (int i=0; i< n ; i++)
			for(int j =0 ; j< n ; j++){
				if(i==j)
					L[i][i]=1;
				else
				L[i][j] = 0;
			}
							
		return L;
	}
	
	void printMatrix(double[][] M, int n){
		for( int k=0;k <n ; k++){
			for( int j=0;j <n ; j++)
				System.out.printf("%16f  ",M[k][j]);
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Random r = new Random();
		int n= 10;
		double A[][] = new double [n][n];
		double b[] = new double [n];
		
		
		
		for (int i=0; i< n ; i++)
			for(int j =0 ; j< n ; j++){
				A[i][j] = r.nextDouble()*100 * r.nextInt(100)-50;
				b[j]= r.nextDouble()*2;
					}
		RealMatrix m = new Array2DRowRealMatrix(A);

	LU lu = new LU();
	double[][]L = lu.prepareL(n);
	lu.showResults(n, A, b,L);
	

	//****************************************
	
	LUDecomposition LU = new LUDecomposition(m);
	System.out.println("Library LU function");
	System.out.println("\nU");
	lu.printMatrix(LU.getU().getData(),n);	
	System.out.println("\nL");
	lu.printMatrix(LU.getL().getData(),n);
	
	}

}
