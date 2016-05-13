package search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MatrixHandler {
	private String path;
	private Dictionary dict;
	int N ;
	int M;
	
	
	public MatrixHandler(String path) {
		this.path = path;
		this.dict = new Dictionary();
		
	}

	public Map<Word,Integer> getWords(){
		return dict.getWords();
	}
	
	public List<String> bestFiles(int k, String path, int svd, int rank){
		runPython("correlation.py", M, N, k, svd, rank);

		List<String> result = new ArrayList<String>(k);
		List<File> files = dict.getFiles();
		String[] contents;
		try {
			contents = new String(Files.readAllBytes( Paths.get(path))).split(" ");
		
		int fileNumber ;
		
		for(String elem : contents){
			fileNumber = Integer.parseInt(elem);
			result.add(files.get(fileNumber).getName());
		}
		} catch (IOException e) {
			System.out.println("Couldn't read file numbers returned by python script");
		}
		
		return result ;
	}
	
	public void setIDF(boolean f){
		dict.setIDF(f);
	}
	public Map<Position,Double> filledMatrix(){
		
		dict.scan(this.path);
		
		this.N = 1028;//dict.filesNumber();
		this.M = 78914;//dict.wordsNumber();

		return dict.fillMatrix();
	}
	
	
    public void saveMatrix(String path){
    	dict.saveMatrix(path);
    }
    
    public void runPython( String filePath, int m , int n ,int k, int svd,int rank){
		

		String [] command = { "python3",filePath, Integer.toString(m),Integer.toString(n),Integer.toString(k), Integer.toString(svd),Integer.toString(rank)}; 
		ProcessBuilder pb = new ProcessBuilder(command); 
		Process p;		
		try{
			p = pb.start();
		p.waitFor();
		} catch (IOException e){
			System.out.println("Error while launching python");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    }
}
