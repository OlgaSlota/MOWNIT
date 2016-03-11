package lab2;

import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class LibSolution {
	
		public static void main(String[] argv)
		{	
			Random r = new Random();
			int n = 800;
		
			double A[][] = new double [n][n];
			double b[] = new double [n];
			
			for (int i=0; i< n ; i++)
				for(int j =0 ; j< n ; j++){
					A[i][j] = r.nextDouble()*100 * r.nextInt(100)-50;
					b[j]= r.nextDouble()*2;
						}
			RealMatrix coefficients = new Array2DRowRealMatrix(A,false);
			RealVector constants = new ArrayRealVector(b, false);
			
			// My solution:
			GaussJordan gj = new GaussJordan();	
			gj.showResults(n,A,b);
			
	        // Library version of solution for comparison:
			DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
			System.out.println("Library solution:");
			RealVector solution = solver.solve(constants);
			for (int i=0; i<n; i++ )
				System.out.print(solution.getEntry(i) + " ");
			}
				
				
				
				}

