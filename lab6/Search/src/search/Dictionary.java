package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.tartarus.snowball.ext.englishStemmer;

public class Dictionary implements Serializable {
	
	private Map<Word,Integer> wordsMap;
	private List<File> filesList;
	private int N ;
	private int M ;
	private Map<Position,Double> matrix ;
	private List<List<String>> words;
	private boolean IDF;
	private int[] fileswithWord;
	
	public Dictionary() {
		this.wordsMap = new TreeMap<Word,Integer>();
		this.filesList = new ArrayList<File>();
		this.matrix = new HashMap<Position,Double>();
		this.words = new ArrayList<List<String>>();
	}
	
	public File[] listFiles(String path){
		
        File dir = new File(path);
		
		if(dir.isDirectory() == false) {
			System.out.println("Error! Wrong path to documents directory!");
			return null;
		}
		return dir.listFiles();
	}

	
	public void scan(String path) {
		
		File[] files = listFiles(path);
		int fIndex = 0;
		int wIndex =0;
		for(File file : files) {
			filesList.add(file);
			try {
				String contents =new String(Files.readAllBytes( Paths.get(file.getPath())))
						.replaceAll("['\",.*()&^%$#!?></\\{}_|~\\+=;:\\[\\]-]", " ");
				
				words.add(stem(contents.toLowerCase().split("\\s")));
				
				
				for( String w : words.get(fIndex)){
					Word word = new Word(w);
					 
						if(!wordsMap.containsKey(word)){
							wordsMap.put(word,wIndex);
							wIndex++;
						}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			fIndex++;
		}
	
		M = wIndex;	
		N = fIndex;
	}
	
	
	public Map<Position, Double> fillMatrix(){
		
	    fileswithWord = new int[M + 1]; // IDF
	    
		for(int n =0 ; n<N ; n++){
		for( String w : words.get(n)){
			Word word = new Word(w);
			
			if(wordsMap.containsKey(word)){
				int m = wordsMap.get( word );
		        Position p = new Position(m, n);
		        if (!matrix.containsKey(p)) {
		            matrix.put(p, 0.0);
		            
		            fileswithWord[m]++; // IDF
		        } 
		        matrix.put(p, 1+matrix.get(p));
		}
		}
	    }
		return matrix;
	}
	
	public void saveMatrix(String path){
		
	    PrintWriter matrixWriter;
		try {
			matrixWriter = new PrintWriter( path );
		
	    for (Map.Entry<Position, Double> entry : matrix.entrySet()) {
	        if ( IDF ) {
	        
	          matrix.put( entry.getKey(),
	                      entry.getValue()*( Math.log10( N / (fileswithWord[entry.getKey().m]) ) ) );
	        }

	        matrixWriter.println( entry.getKey().m + " " + entry.getKey().n + " " + entry.getValue()+" " );
	      }
	      matrixWriter.close();
		} catch (FileNotFoundException e) {
			System.out.println("Wrong path for matrix to save");
		}
	}
	
	
	public List<String> stem(String[] input) {
		
	    List<String> output=new ArrayList<>();
	    englishStemmer s = new englishStemmer();
	    
	    for(String word:input){
	    	
	    	s.setCurrent(word);
	    	
	    	if (s.stem())
	    	   output.add(s.getCurrent());
	    	
	    }
	    return output;
	}
	

	public void setIDF(boolean f){
		IDF = f;
	}
	public int filesNumber(){
		return N;
	}
	public int wordsNumber(){
		return M;
	}
	
	public Map<Word,Integer> getWords() {
		return wordsMap;
	}
	public List<File> getFiles() {
		return filesList;
	}


}