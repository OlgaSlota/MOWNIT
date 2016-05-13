package search;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class Arguments {

	public static void bagOfWords(String[] input, Map<Word,Integer> words , String path ){
		
		int size = words.size();
		int [] q = new int [size];
		
		Dictionary d = new Dictionary();
		ArrayList<String> stemmed= (ArrayList<String>) d.stem(input);

		for(String elem : stemmed ){
			Word word=new Word(elem.toLowerCase());
			
			if(words.containsKey(word))
				q[words.get(word)]++;
		}
		try {
			PrintWriter pr = new PrintWriter(path);
			for ( int elem : q)
				pr.println(elem);
			pr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Wrong path to save asked words");
		}
		
	}
}
