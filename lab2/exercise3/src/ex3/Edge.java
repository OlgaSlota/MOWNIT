package zad3;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Edge extends DefaultWeightedEdge {

	int index;
	double R ;
	String u;
	String v;
	boolean isSEM;
	double amper;
	void setAmperage(double I){
		this.amper=I;
	}
	
	public Edge(String u, String v, double r, int index){
		this.u = u;
		this.v = v;
		this.R = r;
		this.index = index;
		this.isSEM= false;
	}
	public Edge(String u, String v, double epsilon, int index , boolean isSEM){
		this.u = u;
		this.v = v;
		this.R = epsilon;
		this.index = index;
		this.isSEM = isSEM;
	}
	 @Override
     public String toString() {
         return String.valueOf(getWeight());
     }
}
