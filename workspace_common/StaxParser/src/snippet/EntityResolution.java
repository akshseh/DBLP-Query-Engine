package snippet;
import java.util.*;

import java.io.FileNotFoundException;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

interface SAXHandlerFactoryInterface {
	public void makeSAXParser();
}

class SAXHandlerFactory implements SAXHandlerFactoryInterface {
	
	DefaultHandler handler;
	
	public SAXHandlerFactory(DefaultHandler handler)
	{
		this.handler = handler;
	}
	
	@Override
	public void makeSAXParser()
	{
		try
		{
			File inputFile = new File("dblp.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser saxParser = factory.newSAXParser();
	        saxParser.getXMLReader().setFeature("http://xml.org/sax/features/validation", true);
	        saxParser.parse(inputFile, handler);
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace(); }
	}
}

public class EntityResolution {
	
	public Map<String, Author> ourMainMap;
	
	public EntityResolution()
	{
		this.ourMainMap = new HashMap<String, Author>();
	}
	
	
	public void parseWWW()
	{
		DefaultHandler handler = new UserHandlerWWW(this.ourMainMap);
		SAXHandlerFactory factory = new SAXHandlerFactory(handler);
		factory.makeSAXParser();
	}
	
	public void parseAllAuthors()
	{
		DefaultHandler handler = new UserHandlerAuthors(this.ourMainMap);
		SAXHandlerFactory factory = new SAXHandlerFactory(handler);
		factory.makeSAXParser();
	}
	
	public List<Publication> parsePublicationByAuthor(String queryAuthorName)
	{
		List<Publication> publicationsFound = new ArrayList<Publication>();
		Author queryAuthor = this.ourMainMap.get(queryAuthorName);
		DefaultHandler handler = new UserHandlerAuthorPublication(queryAuthor, publicationsFound);
		SAXHandlerFactory factory = new SAXHandlerFactory(handler);
		factory.makeSAXParser();
		return publicationsFound;
	}
	
	public Map<Integer, List<Publication> > parsePublicationByTitle(List<String> listTitle)
	{
		Map<Integer, List<Publication> > publMap = new TreeMap<Integer, List<Publication> >(new Comparator<Integer>()
				{
					public int compare(Integer a, Integer b)
					{
						return b.compareTo(a);
					}
				}); 
		DefaultHandler handler = new UserHandlerTitlePublication(listTitle, this.ourMainMap, publMap);
		SAXHandlerFactory factory = new SAXHandlerFactory(handler);
		factory.makeSAXParser();
		return publMap;
	}
	
}

//parse for multiple names of same author
class UserHandlerWWW extends DefaultHandler{
	private Map<String, Author> nameMap;
	boolean bAuthorName = false;
	boolean bWWW = false;
	Author author;
	
	public UserHandlerWWW(Map<String, Author> givenMap){
		this.nameMap = givenMap; 
	}
	 public void startElement(String uri,String localname,String qname,Attributes attributes) throws SAXException{
		if(qname.equalsIgnoreCase("www")){
			author = new Author();
			bWWW = true;
		}
		if(qname.equalsIgnoreCase("author") && bWWW){
			bAuthorName = true;
		}
	}
	 
	 public void characters(char ch[],int start, int length)throws SAXException{
		 if(bAuthorName){
			 author.addAuthorName(new String(ch, start, length));
			 this.bAuthorName = false;
		 }
	 }
	 
	 public void endElement(String uri,String localname,String qname) throws SAXException {
		 if(qname.equalsIgnoreCase("www")) {
			 for(String i : author.getNames())
			 {
				 this.nameMap.put(i, author);
			 }
			 bWWW = false;
		 }
	 }
}

//parse for Authors and their number of publications
class UserHandlerAuthors extends DefaultHandler {
	private Map<String, Author> nameMap;
	boolean bAuthorName = false;
	boolean bPubl = false;
	Author newAuthor;
	
	public UserHandlerAuthors(Map<String, Author> givenMap) {
		this.nameMap = givenMap;
	}
	
	public void startElement(String uri,String localname,String qname,Attributes attributes) throws SAXException{
		if(qname.equalsIgnoreCase("article") || qname.equalsIgnoreCase("inproceedings") || qname.equalsIgnoreCase("proceedings") || qname.equalsIgnoreCase("book") || qname.equalsIgnoreCase("incollection") || qname.equalsIgnoreCase("masterthesis") || qname.equalsIgnoreCase("phdthesis")) {
			bPubl = true;
		}
		if(qname.equalsIgnoreCase("author") && bPubl){
			bAuthorName = true;
		}
	}
	 
	 public void characters(char ch[],int start, int length)throws SAXException{
		 if(bAuthorName){
			 String name = new String(ch, start, length);
			 if(!this.nameMap.containsKey(name))
			 {
				 newAuthor = new Author();
				 newAuthor.addAuthorName(name);
				 this.nameMap.put(name, newAuthor);
			 }
			 newAuthor = this.nameMap.get(name);
			 newAuthor.incrementPub();
			 this.bAuthorName = false;
		 }
	 }
	 
	 public void endElement(String uri,String localname,String qname) throws SAXException {
		 if(qname.equalsIgnoreCase("article") || qname.equalsIgnoreCase("inproceedings") || qname.equalsIgnoreCase("proceedings") || qname.equalsIgnoreCase("book") || qname.equalsIgnoreCase("incollection") || qname.equalsIgnoreCase("masterthesis") || qname.equalsIgnoreCase("phdthesis")) {
			 bPubl = false;
		 }
	 }
}

//parse for publications pertaining to given author
class UserHandlerAuthorPublication extends DefaultHandler {
	private List<Publication> publications; 
	public Author author;
	private boolean AuthorFound;
	private boolean bPubl = false;
	private boolean bAuthorName = false;
	private boolean bTitle = false;
	private boolean bPages = false;
	private boolean bYear = false;
	private boolean bVolume =  false;
	private boolean bJournal = false;
	private boolean bUrl = false;
	private Publication newPubl;
	private int result = 0;
	
	public UserHandlerAuthorPublication(Author author, List<Publication> publ){ 
		this.author = author; 
		this.publications = publ;
		this.AuthorFound = false;
	}
	
	List<Publication> getPublications(){ return this.publications; }
	
	public void startElement(String uri,String localname,String qname,Attributes attributes) throws SAXException{
		if(qname.equalsIgnoreCase("article") || qname.equalsIgnoreCase("inproceedings") || qname.equalsIgnoreCase("proceedings") || qname.equalsIgnoreCase("book") || qname.equalsIgnoreCase("incollection") || qname.equalsIgnoreCase("masterthesis") || qname.equalsIgnoreCase("phdthesis")) {
			bPubl = true;
		}
		if(qname.equalsIgnoreCase("author") && bPubl){
			bAuthorName = true;
		}
		if(qname.equalsIgnoreCase("title") && bPubl) bTitle = true;
		if(qname.equalsIgnoreCase("pages") && bPubl) bPages = true;
		if(qname.equalsIgnoreCase("year") && bPubl) bYear = true;
		if(qname.equalsIgnoreCase("volume") && bPubl) bVolume = true;
		if((qname.equalsIgnoreCase("journal") | qname.equalsIgnoreCase("booktitle")) && bPubl) bJournal = true;
		if(qname.equalsIgnoreCase("url") && bPubl) bUrl = true;	
	}
	 
	 public void characters(char ch[],int start, int length)throws SAXException{
		 if(bAuthorName){
			 String name = new String(ch, start, length);
			 for(String i : author.getNames()){
				 if(i.equalsIgnoreCase(name)){
					 this.AuthorFound = true;
					 newPubl = new Publication();
					 newPubl.addAuthor(this.author);
					 break;
				 }
			 }
			 this.bAuthorName = false;
		 }
		 
		 if(bTitle){ 
			 if(this.AuthorFound)
			 {
				 this.newPubl.setTitle(new String(ch, start, length)); 
			 }
			 this.bTitle = false;
		 }
		 if(bPages){
			 if(this.AuthorFound)
				 newPubl.setPages(new String(ch, start, length)); 
			 this.bPages = false; 
		 }
		 if(bYear){
			 if(this.AuthorFound)
				 newPubl.setYear(Integer.valueOf(new String(ch, start, length)));
			 this.bYear = false;
		 }
		 if(bVolume){
			 if(this.AuthorFound)
				 newPubl.setVolume(new String(ch, start, length));
			 this.bVolume = false;
		 }
		 if(bJournal){
			 if(this.AuthorFound)
				 newPubl.setJournal(new String(ch, start, length));
			 this.bJournal = false; 
		 }
		 if(bUrl){
			 if(this.AuthorFound)
				 newPubl.setURL(new String(ch, start, length));
			 this.bUrl = false;
		}
	 }
	 
	 public void endElement(String uri,String localname,String qName) throws SAXException {
		 if(qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("inproceedings") || qName.equalsIgnoreCase("proceedings") || qName.equalsIgnoreCase("book") || qName.equalsIgnoreCase("incollection") || qName.equalsIgnoreCase("masterthesis") || qName.equalsIgnoreCase("phdthesis")) {
			 if(this.AuthorFound)
			 {
				 if(newPubl != null)
				 {
					 this.publications.add(newPubl);
				 }
				 this.result += 1;
				 this.AuthorFound = false;
			 }
			 bPubl = false;
		 }
	 }
	 
	 public int getResult() { return this.result; }
}

//parse for publications pertaining to title tags
class UserHandlerTitlePublication extends DefaultHandler {
	Map<Integer, List<Publication> > matchedPubl;
	Map<String, Author> ourMainMap;
	List<String> queryTitle;
	int numberOfMatches;
	boolean bPubl = false;
	boolean bAuthorName = false;
	boolean bTitle = false;
	boolean bPages = false;
	boolean bYear = false;
	boolean bVolume =  false;
	boolean bJournal = false;
	boolean bUrl = false;
	Publication newPubl;
	Author instanceAuthor; 
	
	public UserHandlerTitlePublication(List<String> titleQuery, Map<String, Author> givenMap, Map<Integer, List<Publication> > publMap) {
		this.queryTitle = titleQuery;
		this.ourMainMap = givenMap;
		this.matchedPubl = publMap;
		this.numberOfMatches = 0;
	}
	
	public void startElement(String uri,String localname,String qname,Attributes attributes) throws SAXException{
		if(qname.equalsIgnoreCase("article") || qname.equalsIgnoreCase("inproceedings") || qname.equalsIgnoreCase("proceedings") || qname.equalsIgnoreCase("book") || qname.equalsIgnoreCase("incollection") || qname.equalsIgnoreCase("masterthesis") || qname.equalsIgnoreCase("phdthesis")) {
			bPubl = true;
		}
		if(qname.equalsIgnoreCase("author") && bPubl){bAuthorName = true;}
		if(qname.equalsIgnoreCase("title") && bPubl) bTitle = true;
		if(qname.equalsIgnoreCase("pages") && bPubl) bPages = true;
		if(qname.equalsIgnoreCase("year") && bPubl) bYear = true;
		if(qname.equalsIgnoreCase("volume") && bPubl) bVolume = true;
		if((qname.equalsIgnoreCase("journal") | qname.equalsIgnoreCase("booktitle")) && bPubl) bJournal = true;
		if(qname.equalsIgnoreCase("url") && bPubl) bUrl = true;	
	}
	 
	 public void characters(char ch[],int start, int length)throws SAXException{	 
		 if(bAuthorName){
			 String gotName = new String(ch, start, length);
			 this.instanceAuthor = this.ourMainMap.get(gotName);
			 bAuthorName = false;
		 }
		 if(bTitle){
			 String gotTitle = new String(ch, start, length);
			 String[] splitTitle = gotTitle.split(" ");
			 for(String i : this.queryTitle)
			 {
				 String other1 = i+"."; String other2 = i+":"; String other3 = i+",";
				 for(String j : splitTitle)
				 {
					 if(i.equalsIgnoreCase(j) | other1.equalsIgnoreCase(j) | other2.equalsIgnoreCase(j) | other3.equalsIgnoreCase(j))
						 this.numberOfMatches += 1;
				 }
			 }
			 if(this.numberOfMatches > 0)
			 {
				 this.newPubl = new Publication();
				 this.newPubl.setTitle(gotTitle);
			 }
			 bTitle = false;
		 }
		 if(bPages){ 
			 if(this.numberOfMatches > 0)
				 newPubl.setPages(new String(ch, start, length));
			 bPages = false;
		 }
		 if(bYear){ 
			 if(this.numberOfMatches > 0)
				 newPubl.setYear(Integer.valueOf(new String(ch, start, length))); 
			 bYear = false; 
		 }
		 if(bVolume){
			 if(this.numberOfMatches > 0)
			 	newPubl.setVolume(new String(ch, start, length)); 
			 bVolume = false; 
		 }
		 if(bJournal){ 
			 if(this.numberOfMatches > 0)
				 newPubl.setJournal(new String(ch, start, length)); 
			 bJournal = false;
		 }
		 if(bUrl){
			 if(this.numberOfMatches > 0)
				 newPubl.setURL(new String(ch, start, length));
			 bUrl = false; 
		 }
	 }
	 
	 public void endElement(String uri,String localname,String qname) throws SAXException {
		 if(qname.equalsIgnoreCase("article") || qname.equalsIgnoreCase("inproceedings") || qname.equalsIgnoreCase("proceedings") || qname.equalsIgnoreCase("book") || qname.equalsIgnoreCase("incollection") || qname.equalsIgnoreCase("masterthesis") || qname.equalsIgnoreCase("phdthesis")) {
			 if(this.numberOfMatches > 0){
				newPubl.addAuthor(this.instanceAuthor);
			 	if(!this.matchedPubl.containsKey(this.numberOfMatches))
			 	{
			 		this.matchedPubl.put(this.numberOfMatches, new ArrayList<Publication>());
			 	}
			 	List<Publication> gotPublication = this.matchedPubl.get(this.numberOfMatches);
			 	gotPublication.add(newPubl);
			 }
			 bPubl = false;
			 this.numberOfMatches = 0;
		 }
	 }
}