import java.util.*;

/** PUBLICATION CLASS */

public class Publication {
	private List<Author> authors; 
	private String Title;
	private String pages;
	private String Journal;
	private String URL;
	private int year;
	private String volume;
	
	/*! Constructor 
     * 
     *  Creates an arrayList of Authors. 
     */
	public Publication(){
		authors = new ArrayList<Author>();
		this.Title = "";
		this.pages = "";
		this.Journal = "";
		this.URL = "";
		this.volume = "";
	}
	/*!  Another constructor
     *   This takes the Title as the parameter
     */
	public Publication(String title){ 
		this.Title = title; 
		authors = new ArrayList<Author>();
	}
	
	/*!  
     *  GETTER METHODS
     */
	List<Author> getAuthors(){return authors;}
	String getTitle(){return Title;}
	String getJournal(){return Journal;}
	String getPages(){return pages;}
	int getYear(){return year;}
	String getVolume(){ return volume;}
	String getURL(){return URL;}
	
	/*!  
     *   SETTER METHODS
     */
	void setTitle(String Title){this.Title = Title;	}
	void setJournal(String jour){this.Journal = jour;}
	void setPages(String Pag){this.pages = Pag;}
	void setVolume(String vol){this.volume = vol;}
	void setYear(int year){this.year = year;}
	void setURL(String URL ){this.URL = URL;}
	void addAuthor(Author author){ this.authors.add(author); }
	
	/*!  
     *  Converts the Author Details to a String  
     */
	public String toString()
	{
		String ans = "Authors : ";
		for(Author a : this.authors)
		{
			ans  += a.toString();
			ans += ", ";
		}
		ans += "\n Title: " + this.Title;
		ans += "\n Pages: " + this.pages;
		ans += "\n Year: " + this.year;
		ans += "\n Volume: " + this.volume;
		ans += "\n Journal: " + this.Journal;
		ans += "\n URL: " + this.URL;
		return ans;
	}
	
}