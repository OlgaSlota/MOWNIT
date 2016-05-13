package search;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainClass {

	public static void main(String[] args) {
		
		int k = 10;  // number of best files to find 
		int svd = 1;  // [0/1]
		int rank = 180;  // in case of SVD usage 
		
		long start = System.nanoTime();		

		String dirPath = System.getProperty("user.dir")+"/Universe";
		

// PRINT INFO--------------------------------------------------------------------------
		
		System.out.print("Searching for : ");
		for(String w : args)
			System.out.print(w+" ");
		
		System.out.println(" in directory "+ dirPath);
		
		System.out.println( "With settings: \nnumber of best files to find "+k );
		if(svd==1)
			System.out.println("SVD with rank = "+ rank);
		else 
			System.out.println("SVD off");
//---------------------------------------------------------------------------------------		
		
		MatrixHandler mh = new MatrixHandler(dirPath);
	
		mh.setIDF(false);
		mh.filledMatrix();		
		mh.saveMatrix("matrix.txt");
		
		System.out.println( "\nwords : "+mh.M+" \nfiles: "+ mh.N);
		
		Arguments.bagOfWords(args, mh.getWords(),"q.txt");
				
		List<String> results = mh.bestFiles(k, "topK.txt", svd, rank);
	
		System.out.println(results);
		System.out.println("execution time in seconds: "+(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-start)));
		
	}


}
