import java.util.*;

public class Author implements Comparable<Author>
{
	private int numOfPublications;
	private Set<String> names; /*!< This set contains all the names of the author */
	/*! 
	 *  Constructor
     */
	public Author()
	{  
		this.names = new HashSet<String>();  /*!< Hash Set initialized */
		this.numOfPublications = 0;
	}
	/*!  
     *  Getter Methods 
     */
	void addAuthorName(String name){   this.names.add(name); }
	int getNumOfPublications()     {   return this.numOfPublications; }
	String getName()
	{
		String name = this.names.iterator().next().toString(); 
		if(name != null)
			return  name;
		else
			return "";
	}
	Set<String> getNames(){ return this.names; } 
	/*!  
     * Increments the Number of publications by one.  
     */
	void incrementPub(){
		this.numOfPublications += 1; }
	/*!  
     *  Returns the names of the Author  
     */
	public String toString(){ 
		return getName();}
	
	/*!  
     *   Comparator function to compare the number of Publications
     */
	public int compareTo(Author a)
	{
		return Integer.valueOf(this.numOfPublications).compareTo(a.numOfPublications);
	}
}
