package search;

import java.util.Objects;

class Word implements Comparable<Word>{
	String content;
	
	public Word(String c){
		this.content=c;
	}
	 @Override
	    public boolean equals(Object w){
		 return  this.content.equals(w.toString());
	}
	 @Override
	  public int hashCode() {
	    return Objects.hash(this.content );
	  }
	 
	 @Override
	    public String toString(){
		 return this.content;
	 }
	@Override
	public int compareTo(Word w) {
		int result= this.content.compareTo(w.content);
		if (result==0)
			return result;
		else if( this.content.contains(w.toString())||w.toString().contains(this.content))
			return 0;
		else return result;
	}
}

