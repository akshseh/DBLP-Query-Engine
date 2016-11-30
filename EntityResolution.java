import java.util.*;

import java.io.FileNotFoundException;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//! Interface for SAXParsing Handler  Factory
interface SAXHandlerFactoryInterface {
	public void makeSAXParser();
}
//! Factory Interface
/*! 
 *   creates the parsing logic using the handler provided.
*/
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
//! WWW Parsing
/*! 
 * Parsing for multiple names of the same author 
 */
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
//! Handler class for Authors and Publications
/*! 
 * Parse for authors and their number of Publications 
 */
class UserHandlerAuthors extends DefaultHandler {
	private Map<String, Author> nameMap;
	boolean bAuthorName = false;
	boolean bPubl = false;
	Author newAuthor;
	
	//!Constructor
	public UserHandlerAuthors(Map<String, Author> givenMap) {
		this.nameMap = givenMap;
	}
	
	//! callback for start element done by SAX Parser
	/*! 
	 * Check the type for start and element and mark the corresponding boolean variable. 
	 */
	public void startElement(String uri,String localname,String qname,Attributes attributes) throws SAXException{
		if(qname.equalsIgnoreCase("article") || qname.equalsIgnoreCase("inproceedings") || qname.equalsIgnoreCase("proceedings") || qname.equalsIgnoreCase("book") || qname.equalsIgnoreCase("incollection") || qname.equalsIgnoreCase("masterthesis") || qname.equalsIgnoreCase("phdthesis")) {
			bPubl = true;
		}
		if(qname.equalsIgnoreCase("author") && bPubl){
			bAuthorName = true;
		}
	}
	 
	//! callback for characters done by SAX Parser
	/*! 
	 * ch stores the text within the opening and ending tags in xml file. 
	 * Match the author of the publication with the query author and create
	 * the author instance.
	 */
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

	//! callback for endelement done by SAX parser
	/*! 
	 * When end element is a publication, store the auhtor created in the author map. 
	 */
	 public void endElement(String uri,String localname,String qname) throws SAXException {
		 if(qname.equalsIgnoreCase("article") || qname.equalsIgnoreCase("inproceedings") || qname.equalsIgnoreCase("proceedings") || qname.equalsIgnoreCase("book") || qname.equalsIgnoreCase("incollection") || qname.equalsIgnoreCase("masterthesis") || qname.equalsIgnoreCase("phdthesis")) {
			 bPubl = false;
		 }
	 }
}
//! User Handler for AuthorPublication
/*! 
 *  Parsing for Publications pertaining to the Given author
 */
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
	
	//! Constructor
	public UserHandlerAuthorPublication(Author author, List<Publication> publ){ 
		this.author = author; 
		this.publications = publ;
		this.AuthorFound = false;
	}
	
	List<Publication> getPublications(){ return this.publications; }
	
	//! callback for start element done by SAX Parser
	/*! 
	 * Check the type for start and element and mark the corresponding boolean variable. 
	 */
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
	 
	//! callback for characters done by SAX Parser
	/*! 
	 * ch stores the text within the opening and ending tags in xml file. 
	 * Match the author of the publication with the query author and create
	 * the publication instance. 
	 */
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
			 if(this.AuthorFound) newPubl.setTitle(new String(ch, start, length)); 
			 this.bTitle = false;
		 }
		 if(bPages){
			 if(this.AuthorFound) newPubl.setPages(new String(ch, start, length)); 
			 this.bPages = false; 
		 }
		 if(bYear){
			 if(this.AuthorFound) newPubl.setYear(Integer.valueOf(new String(ch, start, length)));
			 this.bYear = false;
		 }
		 if(bVolume){
			 if(this.AuthorFound) newPubl.setVolume(new String(ch, start, length));
			 this.bVolume = false;
		 }
		 if(bJournal){
			 if(this.AuthorFound) newPubl.setJournal(new String(ch, start, length));
			 this.bJournal = false; 
		 }
		 if(bUrl){
			 if(this.AuthorFound) newPubl.setURL(new String(ch, start, length));
			 this.bUrl = false;
		}
	 }
	 
	//! callback for endelement done by SAX parser
	/*! 
	 * When end element is a publication, store the publication created in the result list. 
	 */
	 public void endElement(String uri,String localname,String qName) throws SAXException {
		 if(qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("inproceedings") || qName.equalsIgnoreCase("proceedings") || qName.equalsIgnoreCase("book") || qName.equalsIgnoreCase("incollection") || qName.equalsIgnoreCase("masterthesis") || qName.equalsIgnoreCase("phdthesis")) {
			 if(this.AuthorFound){
				 if(newPubl != null){
					 this.publications.add(newPubl);}
				 this.result += 1;
				 this.AuthorFound = false;
			 }
			 bPubl = false;
		 }
	 }
}

/*! 
 * Parse for Publication pertaining to Title Tags  
 */
class UserHandlerTitlePublication extends DefaultHandler {
	Map<Integer, List<Publication> > matchedPubl;
	Map<String, Author> ourMainMap;
	List<String> queryTitle;
	int numberOfMatches;
	boolean bPubl = false,bAuthorName = false,bTitle = false,bPages = false;
	boolean bYear = false,bVolume = false,bJournal = false,bUrl = false;
	Publication newPubl;
	Author instanceAuthor; 
	
	//! Constructor
	public UserHandlerTitlePublication(List<String> titleQuery, Map<String, Author> givenMap, Map<Integer, List<Publication> > publMap) {
		this.queryTitle = titleQuery;
		this.ourMainMap = givenMap;
		this.matchedPubl = publMap;
		this.numberOfMatches = 0;
	}
	
	//! callback for start element done by SAX Parser
	/*! 
	 * Check the type for start and element and mark the corresponding boolean variable. 
	 */
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
	
	//! callback for characters done by SAX Parser
	/*! 
	 * ch stores the text within the opening and ending tags in xml file. 
	 * Match the title of the publication with the 
	 * query title and store the number of matches. 
	 */
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
				 for(String j : splitTitle) {
					 if(i.equalsIgnoreCase(j) | other1.equalsIgnoreCase(j) | other2.equalsIgnoreCase(j) | other3.equalsIgnoreCase(j))
						 this.numberOfMatches += 1;
				 }
			 }
			 if(this.numberOfMatches > 0) {
				 this.newPubl = new Publication();
				 this.newPubl.setTitle(gotTitle);
			 }
			 bTitle = false;
		 }
		 if(bPages){ 
			 if(this.numberOfMatches > 0) newPubl.setPages(new String(ch, start, length));
			 bPages = false;
		 }
		 if(bYear){ 
			 if(this.numberOfMatches > 0) newPubl.setYear(Integer.valueOf(new String(ch, start, length))); 
			 bYear = false; 
		 }
		 if(bVolume){
			 if(this.numberOfMatches > 0) newPubl.setVolume(new String(ch, start, length)); 
			 bVolume = false; 
		 }
		 if(bJournal){ 
			 if(this.numberOfMatches > 0) newPubl.setJournal(new String(ch, start, length)); 
			 bJournal = false;
		 }
		 if(bUrl){
			 if(this.numberOfMatches > 0) newPubl.setURL(new String(ch, start, length));
			 bUrl = false; 
		 }
	 }
	 
	//! callback for endelement done by SAX parser
	/*! 
	 * When the end element is article, inproceedings etc (publications), 
	 * then store the created publication in the map with the key as number of matches. 
	 */
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