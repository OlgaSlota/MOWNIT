package zad3;

import java.util.List;

import org.jgrapht.graph.*;

public class ResultsChecker {

	public static double check(double res[],  ListenableUndirectedGraph<String, Edge> g, List<List<String>> cycles){
		int sumNode=0;
		
		for( String vertex : g.vertexSet()){
			sumNode=0;
			for(Edge e: g.edgesOf(vertex)){
				sumNode+= e.amper;
			}
			if(sumNode>0.5)
				return sumNode;
		}
		
		int sumCycle=0;
		for(List<String> c : cycles){
			sumCycle=0;
			for(String vertex: c)
				for(Edge edge: g.edgesOf(vertex)){
					if(c.contains(edge.u)&&c.contains(edge.v)){
						if(!edge.isSEM)
							sumCycle+= (edge.R*edge.amper);
						else
							sumCycle-= edge.R;
					}
						
					}
			if(sumCycle>0.5)
				return sumCycle;
		}
		
		return 0;
		
	}
	
	
}
