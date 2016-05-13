package crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
 
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class FileCrawler {
	public static int ind;
	public static void main(String[] args) throws IOException {
 
		File dir = new File(".");
		
		String loc = dir.getCanonicalPath() + File.separator + "record.txt";
		System.out.println(loc);
		FileWriter fstream = new FileWriter(loc, true);
		BufferedWriter out = new BufferedWriter(fstream);
		out.newLine();
		out.close();
 
		processPage("https://en.wikipedia.org/wiki/Universe");
 
		File file = new File(loc);
 
		if (file.delete()) {
 
		}
	}
 
	public static boolean checkExist(String s, File fin) throws IOException {
 
		FileInputStream fis = new FileInputStream(fin);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
 
		String aLine = null;
		while ((aLine = in.readLine()) != null) {
				if (aLine.trim().contains(s)) {
				in.close();
				fis.close();
				return true;
			}
		}
 
		in.close();
		fis.close();
 
		return false;
	}
 
	public static void processPage(String URL) throws IOException {
		
		if ( URL.contains("disambiguation")||URL.contains("Wikipedia")
				||URL.contains("MediaWiki")||URL.contains("File")
				||URL.contains("#mw-head")||URL.contains("#p-search")
				||URL.contains("Special")||URL.contains("Template")
				||URL.contains("Portal")||URL.contains("Help")||URL.contains("Category")){
			return;
		}
		// invalid link
		if (URL.contains(".pdf") || URL.contains("@") 
				|| URL.contains("adfad") || URL.contains(":80")
				|| URL.contains("fdafd") || URL.contains(".jpg")
				|| URL.contains(".pdf") || URL.contains(".jpg")				
				|| URL.contains(".svg") || URL.contains(".png")
				|| URL.contains(".zip") || URL.contains(".tar"))

			return;

		File dir = new File(".");
		String loc = dir.getCanonicalPath() + File.separator + "record.txt";
 
	
		
		if (URL.contains("en.wikipedia.org/wiki") && !URL.endsWith("/")) {
 
		} else if(URL.contains("en.wikipedia.org/wiki") && URL.endsWith("/")){
			URL = URL.substring(0, URL.length()-1);
		}else{
				return;
		}
 
		File file = new File(loc);
 
		boolean e = checkExist(URL, file);
		if (!e) {
			System.out.println("Processing :  " + URL);
		
			FileWriter fstream = new FileWriter(loc, true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(URL);
			out.newLine();
			out.close();
 
			Document doc = null;
			try {
				doc = Jsoup.connect(URL).get();
			} catch (IOException e1) {
				System.out.print("IOException");
				return;
			}
 
			if (doc.text().contains("am")) {
				ind++;
				BufferedWriter bout = new BufferedWriter(new FileWriter(Integer.toString(ind)+".txt", true));
				
				bout.write(doc.text());				
				bout.close();
	 
			}			
 
			Elements questions = doc.select("a[href]");
			for (Element link : questions) {
				processPage(link.attr("abs:href"));
			}
		} else {
				return;
		}
 
	}
}