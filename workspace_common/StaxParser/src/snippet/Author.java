package snippet;
import java.util.*;

public class Author implements Comparable<Author>{
	private int numOfPublications;
	private Set<String> names;
	
	public Author() {this.names = new HashSet<String>(); this.numOfPublications = 0;}
	
	//setters
	void addAuthorName(String name){this.names.add(name); }
	
	//getters
	int getNumOfPublications(){ return this.numOfPublications; }
	String getName(){return this.names.iterator().next().toString(); }
	Set<String> getNames() { return this.names; } 
	
	void incrementPub(){this.numOfPublications += 1; }
	
	public String toString(){ 
		return getName();
	}
	
	public int compareTo(Author a)
	{
		return Integer.valueOf(this.numOfPublications).compareTo(a.numOfPublications);
	}
}
