package lab2;

import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

public class LU2 {

		void gaussJordan(double A [][], int N,double[][] L){ 
		
			for(int col=0 ; col<N ; col++){
				this.pivoting(col, N, A);
				for(int row=col+1; row<N ; row++ ){
					this.calculateRow(row, col, A,  N,L); 
				}
			}
		}
		
		void calculateRow(int row , int col, double A[][], int N, double[][] L){
			double multi =  A[row][col]/A[col][col];		
			for ( int i = 0 ; i<N; i++){
				A[row][i] -= multi * A[col][i];	
				}
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
		
		void pivoting(int start, int N , double A[][]){
			int x =0;
			double max =0;
			int j =start ;	
			for ( int i = start ; i<N ; i++){
				if (A[i][j]>Math.abs(max)){
					max = A[i][j];
					x= i;
				}	
			}
			this.swapRows(A[start], A[x],N);
		}
		
		void showResults(int n, double A [][] , double [][] L ){
			
			LU2 z = new LU2();	
			z.gaussJordan(A, n,L);
			System.out.println("My solution");
			z.printMatrix(A,n);
			System.out.println();
			z.printMatrix(L,n);
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
	public boolean check(int SIZE, double OriginalMatrix[][], double Matrix[][] , double[][] L) {
		double CheckMatrix[][] = new double[SIZE][SIZE];
		
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				double result = 0.0;
				
				for(int s = 0; s < SIZE; s++) {
					result = result + (L[i][s] * Matrix[s][j]);
				}
				CheckMatrix[i][j] = result;			
			}
		}
		boolean isTheSame = true;
		
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(Math.abs(OriginalMatrix[i][j] - CheckMatrix[i][j]) > 1) {
					return false;
				}
			}
		}
		return isTheSame;
	}
	
	public static void main(String[] args) {
		
		Random r = new Random();
		int n= 10;
		double A[][] = new double [n][n];
		
		for (int i=0; i< n ; i++)
			for(int j =0 ; j< n ; j++){
				A[i][j] = r.nextDouble()*100 * r.nextInt(100)-50;
					}
		RealMatrix m = new Array2DRowRealMatrix(A);
		
		double Original[][] = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				Original[i][j] = A[i][j];
			}
		}
	LU2 lu = new LU2();
	double[][]L = lu.prepareL(n);
	lu.showResults(n, A,L);
	if(lu.check(n, Original, A, L))
		System.out.println("Factorization correct");
	else 
		System.out.println("Factorization incorrect!\n");
	
	//****************************************
	
	LUDecomposition LU = new LUDecomposition(m);
	System.out.println("Library LU function");
	System.out.println("\nU");
	lu.printMatrix(LU.getU().getData(),n);	
	System.out.println("\nL");
	lu.printMatrix(LU.getL().getData(),n);
	}
}
