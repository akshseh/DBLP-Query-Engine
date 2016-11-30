package snippet;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.util.*;

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
	
	protected void makeComponents()
	{
	  queryLabel = new JLabel("Query: ");
      queryChoice = new JComboBox(new String[] {"query" ,"Query 1", "Query 2" });    
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
	
	protected void makeTable()
	{
		String column[]={"S.No","Author","Title","Pages","Year","Volume","Journal/BookTitle","URL"};
		jt=new JTable(data,column);  
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.setRowHeight(21);
		jt.getColumnModel().getColumn(0).setPreferredWidth(40);
		jt.getColumnModel().getColumn(1).setPreferredWidth(150);
		jt.getColumnModel().getColumn(2).setPreferredWidth(170);
		jt.getColumnModel().getColumn(3).setPreferredWidth(90);
		jt.getColumnModel().getColumn(4).setPreferredWidth(90);
		jt.getColumnModel().getColumn(5).setPreferredWidth(70);
		jt.getColumnModel().getColumn(6).setPreferredWidth(150);
		jt.getColumnModel().getColumn(7).setPreferredWidth(180);	
	}
	
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

//group layout 
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
	
   public DBLP_GUI()
   {
	  control = new Main();
	  this.currentIndex = 0;
      setTitle("GUI");
      setSize(900, 510);
      
      makeComponents();
      
      integrateListeners();
      
      GroupLayout layout = new GroupLayout(getContentPane());setLayout(layout);
      makeHorizontalGroup(layout);
      layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] { queryChoice ,searchBy });
      makeVerticalGroup(layout);
   }

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
 
   public void displayNextData(List<Publication> pubList){
	   resetTable();
	   for(int i=this.currentIndex; i<this.currentIndex+20; i++){
		   fillRow(pubList.get(i), i-this.currentIndex);
	   }
	   this.currentIndex += 20;
   }	
   
   public void displayNextData(Set<Author> authors){
	   resetTable();
	   int count = 0;
	   while(count < 20 && authorIterator.hasNext()){
		   count += 1;
		   currentIndex += 1;
		   data[count][0] = String.valueOf(currentIndex);
		   data[count][1] = authorIterator.next().toString();
	   }
   }
   
   private void makeInvisible(Component... components){
	   for(int i=0; i<components.length; i++)
		   components[i].setVisible(false);
   }
   
   private void makeVisible(Component... components)
   {
	   for(int i=0; i<components.length; i++)
		   components[i].setVisible(true);
   }
   
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
			 
		   }
	   }
   }
   
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
				  currentIndex = 0;
				  errorLabel.setText("");
				  String queryString = nameTitleTags.getText();
				  if(pubList != null)
					  pubList.clear();
				  pubList = control.getPublicationByAuthor(queryString);
				  control.sortPublicationByDate(pubList);
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
	   
	   private void queryTwo()
	   {
		  String num1 = noPubl.getText().toString();
		  currentIndex = 0;
		  try {
			  if(num1 == null)
			  {
				  errorLabel.setText("Please specify the number of publications.");
			  }
			  int numP = Integer.valueOf(num1);
			  if(numP < 0)
			  {
				  errorLabel.setText("Specify number of publications as a valid positive Integer.");
			  }
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
		  catch(Exception exc)
		  {
			  errorLabel.setText("Specify number of publications as a valid positive Integer.");
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
		  }
	   }
   }
   
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