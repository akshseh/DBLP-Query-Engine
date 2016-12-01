import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.util.*;

/*!		GUI CLASS 
 * 	 All the creation and the initialization (Components and the table ) 
 * 	 is being done here.
 */
public class GUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	protected List<Publication> pubList;
	protected Map<Integer, List<Publication> > pubMap;
	protected Set<Author> authors; 
	protected Main control;
	protected JComboBox queryChoice,searchBy;
	protected JCheckBox sortByRelevance,sortByYear;
	protected JScrollPane pane;
	protected JTextArea queryResults,noPubl,nameTitleTags,sinceYear,customRangeTo,customRangeFrom;
	protected JButton next,reset,search;
	protected JTable jt;
	protected JLabel nameTitleTagsLabel,PubLabel, rangeLabel, sinceYearLabel, searchLabel, queryLabel, errorLabel, resultNumberLabel;
	protected int currentIndex;
	protected Iterator<Author> authorIterator;
	String data[][] = { {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},
  		  {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},
  		  {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},
  		  {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""}};
	
	protected void resetTable()
	{
		for(int i=0; i<20; i++)
		{
			for(int j=0; j<8; j++)
			{
				data[i][j] = "";
			}
		}
	}
	/*! 
	 * 	Creates all the components required for the GUI.
	 */
	   
	protected void makeComponents()
	{
	  queryLabel = new JLabel("Query: ");
      queryChoice = new JComboBox(new String[] {"query" ,"Query 1", "Query 2", "Query 3"});    
      PubLabel = new JLabel("No.of Publication");
      searchLabel = new JLabel("Search By: ");
      searchBy = new JComboBox(new String[] { "search by","By Author","By Title Tags"});
      nameTitleTagsLabel = new JLabel("Name/Title Tags: ");
      sinceYearLabel = new JLabel("Since Year: ");
      rangeLabel = new JLabel("Custom Range: ");
      customRangeFrom = new JTextArea();
      customRangeTo = new JTextArea();
      customRangeTo.setEditable(true);
      customRangeTo.setText("YYYY");
      customRangeFrom.setText("YYYY");
      customRangeFrom.setEditable(true);
      sinceYear = new JTextArea();
      sinceYear.setEditable(true);
      sinceYear.setText("YYYY");
      noPubl = new JTextArea();
      sortByRelevance = new JCheckBox("Sort By Relevance");
      sortByYear = new JCheckBox("Sort By Year");     
      errorLabel = new JLabel();
      resultNumberLabel = new JLabel();
      nameTitleTags = new JTextArea();
      makeTable();
      queryResults = new JTextArea();
      pane = new JScrollPane(jt);
      pane.setBorder(BorderFactory.createEtchedBorder());
      search = new JButton("Search");
      reset = new JButton("reset");
      next = new JButton("next");
	}
	/*! 
	 * 	Creates and initializes the Table for Query Results.
	 */
	   
	protected void makeTable()
	{
		String column[]={"S.No","Author","Title","Pages","Year","Volume","Journal/BookTitle","URL"};
		jt=new JTable(data,column);  
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.setRowHeight(22);
		jt.getColumnModel().getColumn(0).setPreferredWidth(40);
		jt.getColumnModel().getColumn(1).setPreferredWidth(150);
		jt.getColumnModel().getColumn(2).setPreferredWidth(170);
		jt.getColumnModel().getColumn(3).setPreferredWidth(90);
		jt.getColumnModel().getColumn(4).setPreferredWidth(90);
		jt.getColumnModel().getColumn(5).setPreferredWidth(70);
		jt.getColumnModel().getColumn(6).setPreferredWidth(150);
		jt.getColumnModel().getColumn(7).setPreferredWidth(180);	
	}
	/*! 
	 * 	creates the horizontal Group for the Group Layout of the GUI
	 */
	   
	protected void makeHorizontalGroup(GroupLayout layout)
	   {
		   layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		              .addGroup(layout.createSequentialGroup().addContainerGap()
		              		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup().addContainerGap()
		            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            				.addGroup(GroupLayout.Alignment.TRAILING,layout.createSequentialGroup()
		            						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
		            								.addComponent(queryLabel)
		            								.addComponent(searchLabel)
		            								.addComponent(PubLabel)
		            								.addComponent(nameTitleTagsLabel)
		            								.addComponent(sinceYearLabel)
		            								.addComponent(rangeLabel)
		            								.addComponent(search)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
		                                    		 .addComponent(queryChoice)
		                                             .addComponent(searchBy)
		                                             .addComponent(noPubl)
		                                            .addComponent(nameTitleTags)
		                                            .addComponent(sinceYear)
		                                            .addGroup(layout.createSequentialGroup().addContainerGap()
		                                                    .addComponent(customRangeTo).addGap(8)
		                                                    .addComponent(customRangeFrom))
		                                            .addComponent(reset)
		                                            
		                                            ).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                      )
		            				.addComponent(sortByYear)
		            				.addComponent(sortByRelevance)
		            				.addGap(30)
		                            .addComponent(errorLabel)
		                            .addGap(20)
		                            .addComponent(resultNumberLabel)
		            				).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		            				.addComponent(pane)
		            				.addComponent(next)
		            				.addContainerGap()
		            		)))
		            );
	   }
	/*! 
	 * 	creates the Vertical Group for the Group Layout of the GUI
	 */
	   
	   protected void makeVerticalGroup(GroupLayout layout)
	   {
		   layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup().addContainerGap()
		            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            				.addComponent(pane, GroupLayout.Alignment.TRAILING)
		            				.addComponent(next, GroupLayout.Alignment.TRAILING)
		            				.addGroup(layout.createSequentialGroup()
		            						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                                          .addComponent(queryChoice)
		                                          .addComponent(queryLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                                          .addComponent(searchBy)
		                                          .addComponent(searchLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                                            .addComponent(nameTitleTagsLabel)
		                                            .addComponent(nameTitleTags)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                                            .addComponent(PubLabel)
		                                            .addComponent(noPubl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                                            .addComponent(sinceYearLabel)
		                                            .addComponent(sinceYear)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                                            .addComponent(rangeLabel)
		                                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                                            		.addComponent(customRangeFrom)
		                                            		.addComponent(customRangeTo))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                    .addComponent(sortByYear, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                    .addComponent(sortByRelevance, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
		                                    		.addComponent(search)
		                                    		.addComponent(reset)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGap(80).addComponent(resultNumberLabel).addGap(40).addComponent(errorLabel).addGap(120))).addContainerGap()
		                        ));
	   }
}
/*! 
 * 	the main GUI class where the GUI is being created using the
 * group layout and the action listeners are added to it.
 */
class DBLP_GUI extends GUI
{
	private static final long serialVersionUID = 1L;
	
	private void integrateListeners()
	{
		queryChoice.addActionListener(new SelectQuery());
		searchListener listenSearch = new searchListener();
	    search.addActionListener(listenSearch);
	    nextData nextListener = new nextData();
	    next.addActionListener(nextListener);
	    reset.addActionListener(new resetListener());
	}
	/*!			CONSTRUCTOR 
	 * 	Calls the Group Layout Functions and integrates listeners to it.
	 */
	
   public DBLP_GUI()
   {
	  control = new Main();
	  this.currentIndex = 0;
      setTitle("DBLP QUERY ENGINE");
      setSize(900, 510);
      
      makeComponents();
      
      integrateListeners();
      
      makeInvisible(searchBy, sortByRelevance, sortByYear, sinceYear, customRangeFrom, 
			   customRangeTo, search, reset, searchLabel, sinceYearLabel, rangeLabel, 
			   nameTitleTags, nameTitleTagsLabel, queryLabel, noPubl, PubLabel);
	   makeVisible(errorLabel, resultNumberLabel);
	   errorLabel.setText("");
	   resultNumberLabel.setText("");
      
      GroupLayout layout = new GroupLayout(getContentPane());setLayout(layout);
      makeHorizontalGroup(layout);
      layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] { queryChoice ,searchBy });
      makeVerticalGroup(layout);
   }
	/*! 
	 * 	Stores the data to the corresponding rows.
	 */
   private void fillRow(Publication p, int i){
	   data[i][0] = String.valueOf(i + this.currentIndex);
	   data[i][1] = p.getAuthors().get(0).getName();
	   data[i][2] = p.getTitle();
	   data[i][3] = p.getPages();
	   data[i][4] = String.valueOf(p.getYear());
	   data[i][5] = p.getVolume();
	   data[i][6] = p.getJournal();
	   data[i][7] = p.getURL();
   }
	/*! 
	 * 	Displays the next 20 rows of the data from the List
	 */

   public void displayNextData(List<Publication> listPub){
	   if(listPub == null)
	   {
		   System.out.print("The list is null");
	   }
	   else
	   {
		   resetTable();
		   for(int i=this.currentIndex; i<this.currentIndex+20 && i<listPub.size(); i++){
			   fillRow(listPub.get(i), i-this.currentIndex);
		   }
		   this.currentIndex += 20;
	   }
   }	
	/*! 
	 * 	Displays the next 20 rows of the data from the Set of authors
	 */
   
   public void displayNextData(Set<Author> authors){
	   int count = 0;
	   resetTable();
	   while(count < 20 && authorIterator.hasNext()){
		   this.currentIndex += 1;
		   data[count][0] = String.valueOf(currentIndex);
		   data[count][1] = authorIterator.next().toString();
		   count += 1;
	   }
   }
	/*! 
	 * 	A function for Setting the visibility of the variable number of arguments to false.
	 */
   
   private void makeInvisible(Component... components){
	   for(int i=0; i<components.length; i++)
		   components[i].setVisible(false);
   }
	/*! 
	 * 	A function for Setting the visibility of the variable number of arguments to true.
	 */

   private void makeVisible(Component... components)
   {
	   for(int i=0; i<components.length; i++)
		   components[i].setVisible(true);
   }
	/*! 
	 * 	A function for prediction of the number of publications based on the 
	 *  the data provided till the specified year, considering only the non-zero values.
	 */

   private Map<Integer, Integer> tillYear(List<Publication> p, int toYear)
   {
	   Map<Integer, Integer> predMap = new HashMap<Integer, Integer>();
	   for(Publication i : p)
	   {
		   if(i.getYear() <= toYear)
		   {
			   if(!predMap.containsKey(i.getYear()))
				   predMap.put(i.getYear(), 0);
			   int a = predMap.get(i.getYear());
			   predMap.put(i.getYear(), a+1);
		   }
	   }
	   return predMap;
   }
	/*! 
	 * 	A function for Custom range text box where we specify the range of years
	 * for the publications to be printed.
	 */

   private List<Publication> checkRange(List<Publication> p){
	   List<Publication> linkedP = new ArrayList<Publication>();
	   if(customRangeFrom.getText().equals("YYYY") && customRangeTo.getText().equals("YYYY")){
		   return p;
	   }
	   else if(customRangeFrom.getText().equals("YYYY")){
		   try {
			   int to = Integer.valueOf(customRangeTo.getText());
			   for(Publication i : p){
				   if(i.getYear() <= to)
					   linkedP.add(i);
			   }
		   } catch (Exception e) {
			   errorLabel.setText("Please provide a valid year!");
			   return p;
		   }
	   }
	   else if(customRangeTo.getText().equals("YYYY"))
	   {
		   try {
			   int from = Integer.valueOf(customRangeTo.getText());
			   for(Publication i : p){
				   if(i.getYear() >= from)
					   linkedP.add(i);
			   }
		   } catch (Exception e) {
			   errorLabel.setText("Please provide a valid year!");
			   return p;
		   }
	   }
	   else{
		   try {
			   int to = Integer.valueOf(customRangeFrom.getText());
			   int from = Integer.valueOf(customRangeFrom.getText());
			   for(Publication i : p){
				   if(i.getYear() >= from && i.getYear() <= to)
					   linkedP.add(i);
			   }
		   } catch (Exception e) {
			   errorLabel.setText("Please provide a valid year!");
			   return p;
		   }
	   }
	   return linkedP;
   }
	/*! 
	 * 	A function for Since year functionality where we specify the year from
	 *  which we want the author's publications to be printed/returned.
	 */

   private List<Publication> checkSinceYear(List<Publication> p)
   {
	   List<Publication> linkedP = new ArrayList<Publication>();
	   if(!sinceYear.getText().equals("YYYY"))
	   {
		   try
		   {
			   int since = Integer.valueOf(sinceYear.getText());
			   System.out.println(since);
			   for(Publication i : p){
				   if(i.getYear() >= since)
					   linkedP.add(i);
			   }
		   }
		   catch(Exception e){
			   errorLabel.setText("Please provide a valid year");
			   return p;
		   }
	   }
	   else{
		   return p;
	   }
	   return linkedP;
   }
	/*! 
	 * 	Action Listener class for the query selection.
	 */
   class SelectQuery implements ActionListener 
   {
	   public void actionPerformed(ActionEvent e)
	   {
		   JComboBox box = (JComboBox)e.getSource();
		   String selectedQuery = box.getSelectedItem().toString();
		   switch (selectedQuery)
		   {
		   case "query" :
			   makeInvisible(searchBy, sortByRelevance, sortByYear, sinceYear, customRangeFrom, 
					   customRangeTo, search, reset, searchLabel, sinceYearLabel, rangeLabel, 
					   nameTitleTags, nameTitleTagsLabel, queryLabel, noPubl, PubLabel);
			   makeVisible(errorLabel, resultNumberLabel);
			   errorLabel.setText("");
			   resultNumberLabel.setText("");
			   break;
		   case "Query 1" :
			   makeVisible(searchBy, sortByRelevance, sortByYear, sinceYear, customRangeFrom, 
					   customRangeTo, search, reset, searchLabel, sinceYearLabel, rangeLabel, 
					   nameTitleTags, nameTitleTagsLabel, queryLabel, errorLabel, resultNumberLabel);
			   makeInvisible(noPubl, PubLabel);
			   errorLabel.setText("");
			   resultNumberLabel.setText("");
			   break;
		   case "Query 2" :
			   makeInvisible(searchBy, sortByRelevance, sortByYear, sinceYear, customRangeFrom,
					   customRangeTo, searchLabel, sinceYearLabel, 
					   rangeLabel, nameTitleTags, nameTitleTagsLabel, queryLabel);
			   makeVisible(noPubl, search, reset, PubLabel, errorLabel, resultNumberLabel);
			   errorLabel.setText("");
			   resultNumberLabel.setText("");
			   break;
		   case "Query 3" :
			   makeInvisible(searchBy, sortByRelevance, sortByYear, customRangeFrom,
					   customRangeTo, searchLabel, rangeLabel,  queryLabel, noPubl, PubLabel);
			   makeVisible(sinceYearLabel, nameTitleTags, nameTitleTagsLabel, search, reset, errorLabel, resultNumberLabel, sinceYear);
			   sinceYearLabel.setText("Up To Year");
			   errorLabel.setText("");
			   resultNumberLabel.setText("");
		   }
	   }
   }
   /*! 
    * 	ActionListener class for the SEARCH button
    *  This implements the action listener for the search button for 
    *  all the queries separately and returns the required error if some
    *  required field is not selected
    */
   
   class searchListener implements ActionListener {
	   private void queryOne()
	   {
		  String selectedSearch = searchBy.getSelectedItem().toString();
		  if(selectedSearch.equals("search by"))
			  errorLabel.setText("Please select the search by option.");
		  else if(selectedSearch.equals("By Author"))
		  {
			  if(nameTitleTags.getText() == null  || nameTitleTags.getText().equals(""))
				  errorLabel.setText("Please specify Name/Title query string.");
			  else
			  {
				  System.out.println("Started working on query one.");
				  currentIndex = 0;
				  errorLabel.setText("");
				  String queryString = nameTitleTags.getText();
				  if(pubList != null)
					  pubList.clear();
				  pubList = control.getPublicationByAuthor(queryString);
				  System.out.println("Query one processed");
				  control.sortPublicationByDate(pubList);
				  
				  pubList = checkRange(pubList);
				  pubList = checkSinceYear(pubList);
				  displayNextData(pubList);
				  if(pubList.size() > 0)
					  resultNumberLabel.setText(String.valueOf(pubList.size()));
				  else
					  resultNumberLabel.setText("No result!");
			  }  
		  }
		  else if(selectedSearch.equals("By Title Tags"))
		  {
			  if(nameTitleTags.getText() == null || nameTitleTags.getText().equals(""))
				  errorLabel.setText("Please specify title tag.");
			  else
			  {
				  errorLabel.setText("");
				  if(pubMap != null)
					  pubMap.clear();
				  pubMap = control.getPublicatonByTitle(nameTitleTags.getText());
				  if(pubList != null)
					  pubList.clear();
				  for(Map.Entry<Integer,List<Publication> > entry : pubMap.entrySet())
					  pubList.addAll(entry.getValue());
				  pubList = checkRange(pubList);
				  pubList = checkSinceYear(pubList);
				  if(sortByRelevance.isSelected() && sortByYear.isSelected())
					  errorLabel.setText("Please select only one of the two sorting parameters.");
				  else if(sortByRelevance.isSelected()){
					  displayNextData(pubList);
					  if(pubList.size() > 0)
						  resultNumberLabel.setText(String.valueOf(pubList.size()));
					  else
						  resultNumberLabel.setText("No result!");
				  }
				  else if(sortByYear.isSelected()){
					  errorLabel.setText("");
					  control.sortPublicationByDate(pubList);
					  displayNextData(pubList);
					  if(pubList.size() > 0)
						  resultNumberLabel.setText(String.valueOf(pubList.size()));
					  else
						  resultNumberLabel.setText("No result!");
				  }
				  else
					  errorLabel.setText("Please select sorting parameter!");
		  		}
		   }
	   }
	   
	   private void queryTwo(){
		  String num1 = noPubl.getText().toString();
		  currentIndex = 0;
		  try {
			  if(num1 == null)
				  errorLabel.setText("Please specify the number of publications.");
			  int numP = Integer.valueOf(num1);
			  if(numP < 0)
				  errorLabel.setText("Specify number of publications as a valid positive Integer.");
			  else
			  {
				  errorLabel.setText("");
				  authors = control.getMoreThanK(numP);
				  if(authors != null)
					  authorIterator  = authors.iterator();
				  displayNextData(authors);
				  if(authors.size() > 0)
					  resultNumberLabel.setText(String.valueOf(authors.size()));
				  else
					  resultNumberLabel.setText("No result!");
			  }
		  }
		  catch(Exception exc){
			  errorLabel.setText("Specify number of publications as a valid positive Integer.");
		  }
	   }
	   
	   private void queryThree(){
		   String authorName = nameTitleTags.getText();
		   int year = 0;
		   try
		   {
			   year = Integer.valueOf(sinceYear.getText());
			   if(pubList != null)
					  pubList.clear();
				 pubList = control.getPublicationByAuthor(authorName);
				 Map<Integer, Integer> predictionMap = tillYear(pubList, year);
				 errorLabel.setText("");
				 Integer predictionPub = control.prediction(predictionMap, year+1);
				 resultNumberLabel.setText(String.valueOf(predictionPub));
		   }
		   catch(Exception e)
		   {
			   errorLabel.setText("Please provide a valid year!");
		   }
		   
	   }
	   
	   public void actionPerformed(ActionEvent e)
	   {
		  String selectedQuery = queryChoice.getSelectedItem().toString();
		  currentIndex = 0;
		  switch(selectedQuery)
		  {
		  case "Query 1" :
			  queryOne();
			  break;
		  case "Query 2" :
			  queryTwo();
			  break;
		  case "Query 3":
			  queryThree();
			  break;
		  }
	   }
   }
   /*! 
    * 	ActionListener class for the NEXT button
    */
   class nextData implements ActionListener {
	   public void actionPerformed(ActionEvent e)
	   {
		   String selectedQuery = queryChoice.getSelectedItem().toString();
		   if(selectedQuery.equals("Query 1"))
		   {
			   errorLabel.setText("");
			   displayNextData(pubList);
		   }
		   else if(selectedQuery.equals("Query 2"))
		   {
			   errorLabel.setText("");
			   displayNextData(authors);
		   }
	   }
   }
	/*!  ActionListener class for RESET button
    *   Resets all the texts and the selected items and clears the table.
    */ 
   class resetListener implements ActionListener {
	   public void actionPerformed(ActionEvent e)
	   {
		   resetTable();
		   nameTitleTags.setText("");
		   customRangeFrom.setText("YYYY");
		   customRangeTo.setText("YYYY");
		   sinceYear.setText("YYYY");
		   noPubl.setText("");
		   errorLabel.setText("");
		   resultNumberLabel.setText("");
		   searchBy.setSelectedItem("search by");
	   }
   }
}

