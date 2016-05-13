
package search;

import java.io.Serializable;
import java.util.Objects;

public class Position  implements Serializable{

	Integer m ;
	Integer n ;
	public Position(int a , int b){
		this.m = a ;
		this.n = b;
	}
	 @Override
	    public String toString(){
		 return "("+this.m+", "+this.n+")";
	 }
	 @Override
	  public int hashCode() {
	    return Objects.hash( m, n );
	  }

	  @Override
	  public boolean equals( Object obj ) {
		  
		if (this == obj)  
			return true;
	    if ( obj instanceof Position ) {
	      return (((Position)obj).n == n && ((Position)obj).m == m);
	    }

	    return false;
	  }
}
