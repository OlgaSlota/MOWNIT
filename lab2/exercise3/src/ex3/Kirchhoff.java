package zad3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.jgrapht.alg.cycle.PatonCycleBase;
import org.jgrapht.graph.*;

public final class Kirchhoff {

    public static void main(String [] args)
    
    {   Kirchhoff k = new Kirchhoff();    
        ListenableUndirectedGraph<String, Edge> stringGraph = createStringGraph();
        int n = stringGraph.edgeSet().size();
    	System.out.println(n);
        double[] b = new double[n];
        double[][] matrix = new double[n][n];
        
        int rows = k.firstKirchhoff(matrix, stringGraph);
        
        List<List<String>> cycles = k.findCycles(stringGraph);
    	
        k.secondKirchhoff(matrix, b, stringGraph, n, rows,cycles);
        		
		// My solution:
		GaussJordan gj = new GaussJordan();	
		double res[] = new double[n];
		res = gj.gaussJordan(matrix, b, n);
		File results = new File("results.txt");
		
		try {
			PrintWriter pw = new PrintWriter(results);
					
			for(Edge e : stringGraph.edgeSet()){
				e.setAmperage(res[e.index]);
				pw.println(e.u+ " "+e.v+ " "+e.amper);
			}
			pw.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Issues with file writing");
		}
		
		if(ResultsChecker.check(res, stringGraph, cycles)==0)
			System.out.println("Computation is correct!");
		else 
			System.out.println("Computation is incorrect!");
	
		
		k.launchScript("ex3.py", "results.txt");
	
       }
    public void launchScript( String filePath, String data){
		

		String [] command = { "python",filePath, data}; //preparing a line for a system
		ProcessBuilder pb = new ProcessBuilder(command); 
		Process p;		
		try{
			p = pb.start();
		
		} catch (IOException e){
			System.out.println("Error while launching python");
		}
	
    }
	static ListenableUndirectedGraph<String, Edge> createStringGraph()  {
    	ListenableUndirectedGraph<String, Edge> g =
            new ListenableUndirectedGraph<String, Edge>(Edge.class);
      
        Scanner sc;
        try {
			sc= new Scanner(new File("data.txt"));
			int counter =0;
			boolean last = false;
			while(sc.hasNextLine()){
				String line[] = sc.nextLine().split(" ");
				if(!line[0].contains("SEM")){
					g.addVertex(line[0]);
					g.addVertex(line[1]);
					Edge e ;
					if(!last)
						e= new Edge(line[0],line[1], Double.parseDouble(line[2]), counter);
					else 
						e= new Edge(line[0],line[1], Double.parseDouble(line[2]), counter, true);
					
					if(!g.containsEdge(e.u, e.v))
							counter++;
					g.addEdge(e.u, e.v,e);
					
				}
				else last = true;
			}
			
	        return g;	
			
		} catch (FileNotFoundException e) {
			System.out.println("Wrong path to file with graph data.");
			return null;
		}
       
    }
    public int firstKirchhoff(double[][] m, ListenableUndirectedGraph<String, Edge> g){
    	int row =0 ;
    	for(String v:g.vertexSet()){
    		for(Edge e :g.edgesOf(v)){
    			m[row][e.index] = 1;
    		}
    		row++;
    	}
    	for(int i=0 ; i<m.length ; i++)
    	m[row][i] = 0;
    	row--;
    	return row;
    }
    
   public List<List<String>> findCycles( ListenableUndirectedGraph<String, Edge> g){
    	PatonCycleBase<String, Edge> cycleBase = new PatonCycleBase<String, Edge>();
    	cycleBase.setGraph(g);
    	List<List<String>> cycles= cycleBase.findCycleBase();
    	
    	Comparator comp = new ListComparator();
    	cycles.sort(comp);
    	return cycles;
    }
    
    public void secondKirchhoff(double[][] m, double[] b, ListenableUndirectedGraph<String, Edge> g,int n, int row,List<List<String>> cycles){
    	
    	boolean stop = false;
    	for( List<String> cycle : cycles){
    		if(!stop){
    			for(String vertex: cycle )
    				for(Edge edge: g.edgesOf(vertex)){
    					if(cycle.contains(edge.u)&&cycle.contains(edge.v)){
    					if(row < n ){
    						m[row][edge.index] = edge.R;
    						if(edge.isSEM){
    							m[row][edge.index] =0;
    							b[row]=edge.R;
    						}
    				}
    					else stop = true;
    				}	
    				}		
    		row++;
    		}
       	}
    }
}
