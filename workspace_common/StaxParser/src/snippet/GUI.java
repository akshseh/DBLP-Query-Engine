package snippet;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.util.*;

public class GUI
{

}

//group layout 
class DBLP_GUI extends JFrame
{
	private List<Publication> pubList;
	private Map<Integer, List<Publication> > pubMap;
	private Set<Author> authors; 
	private Main control;
	private JComboBox queryChoice,searchBy;
	private JCheckBox sortByRelevance,sortByYear;
	private JScrollPane pane;
	private JTextArea queryResults,noPubl,nameTitleTags,sinceYear,customRangeTo,customRangeFrom;
	private JButton next,reset,search;
	private JTable jt;
	private JLabel nameTitleTagsLabel,PubLabel, rangeLabel, sinceYearLabel, searchLabel, queryLabel, errorLabel, resultNumberLabel;
	private int currentIndex;
	private Iterator<Author> authorIterator;
	String data[][] = { {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},
  		  {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},
  		  {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},
  		  {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""}};
   	
	private void resetTable()
	{
		for(int i=0; i<20; i++)
		{
			for(int j=0; j<8; j++)
			{
				data[i][j] = "";
			}
		}
	}
	
   public DBLP_GUI()
   {
	  control = new Main();
	  this.currentIndex = 0;
      setTitle("GUI");
      setSize(900, 510);

      // components
     queryLabel = new JLabel("Query: ");
      queryChoice = new JComboBox(new String[] {"query" ,"Query 1", "Query 2" });
      queryChoice.addActionListener(new SelectQuery());
      PubLabel = new JLabel("No.of Publication");
      searchLabel = new JLabel("Search By: ");
      searchBy = new JComboBox(new String[] { "search by","By Author","By Title Tags"});
     // searchBy.addActionListener(listener);
      nameTitleTagsLabel = new JLabel("Name/Title Tags: ");
      sinceYearLabel = new JLabel("Since Year: ");
      rangeLabel = new JLabel("Custom Range: ");
      customRangeFrom = new JTextArea();
      customRangeTo = new JTextArea();
      customRangeTo.setEditable(true);
      customRangeTo.setText("YYYY");
      customRangeFrom.setText("YYYY");
      customRangeFrom.setEditable(true);
      //customRange.addActionListener();
      sinceYear = new JTextArea();
      sinceYear.setEditable(true);
      sinceYear.setText("YYYY");
      noPubl = new JTextArea();
     // sinceYear.addActionListener();
      sortByRelevance = new JCheckBox("Sort By Relevance");
    //  sortByRelevance.addActionListener(listener);
      sortByYear = new JCheckBox("Sort By Year");
     // sortByYear.addActionListener(listener);
      
      errorLabel = new JLabel();
      resultNumberLabel = new JLabel();
      
      nameTitleTags = new JTextArea();
        
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
      
      queryResults = new JTextArea();
      pane = new JScrollPane(jt);
      pane.setBorder(BorderFactory.createEtchedBorder());
      search = new JButton("Search");
      //search.addActionListener();
      searchListener listenSearch = new searchListener();
      search.addActionListener(listenSearch);
      reset = new JButton("reset");
      reset.addActionListener(new resetListener());
      next = new JButton("next");
      nextData nextListener = new nextData();
      next.addActionListener(nextListener);
      //reset.addActionListener();
      GroupLayout layout = new GroupLayout(getContentPane());setLayout(layout);
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

      layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] { queryChoice ,searchBy });
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
   
   public void fillRow(Publication p, int i)
   {
	   data[i][0] = String.valueOf(i + this.currentIndex);
	   data[i][1] = p.getAuthors().get(0).getName();
	   data[i][2] = p.getTitle();
	   data[i][3] = p.getPages();
	   data[i][4] = String.valueOf(p.getYear());
	   data[i][5] = p.getVolume();
	   data[i][6] = p.getJournal();
	   data[i][7] = p.getURL();
   }
 
   public void displayNextData(List<Publication> pubList)
   {
	   resetTable();
	   int i = this.currentIndex;
	   while(i < this.currentIndex+20 && i < pubList.size())
	   {
		   try
		   {
			   if(this.customRangeFrom.getText().equals("YYYY"))
			   {
				   if(this.customRangeTo.getText().equals("YYYY"))
				   {
					   fillRow(pubList.get(i), i-this.currentIndex);
					   this.currentIndex += 1;
					   i++;
				   }
				   else
				   {
					   
				   }   
			   }
		   }
		   catch(Exception exc)
		   {
			   errorLabel.setText("Please provide a valid year!");
		   }
	   }
	   
   }	
   
   
   public void displayNextData(Set<Author> authors)
   {
	   int count = 0;
	   while(count < 20 && authorIterator.hasNext())
	   {
		   count += 1;
		   currentIndex += 1;
		   data[count][0] = String.valueOf(currentIndex);
		   data[count][1] = authorIterator.next().toString();
	   }
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
			   searchBy.setVisible(false);
			   sortByRelevance.setVisible(false);
			   sortByYear.setVisible(false);
			   sinceYear.setVisible(false);
			   customRangeTo.setVisible(false);
			   customRangeFrom.setVisible(false);
			   search.setVisible(false);
			   reset.setVisible(false);
			   searchLabel.setVisible(false);
			   sinceYearLabel.setVisible(false);
			   rangeLabel.setVisible(false);
			   nameTitleTagsLabel.setVisible(false);
			   nameTitleTags.setVisible(false);
			   queryLabel.setVisible(false);
			   noPubl.setVisible(false);
			   PubLabel.setVisible(false);
			   errorLabel.setVisible(true);
			   resultNumberLabel.setVisible(true);
			   errorLabel.setText("");
			   resultNumberLabel.setText("");
			   break;
		   case "Query 1" :
			   searchBy.setVisible(true);
			   sortByRelevance.setVisible(true);
			   sortByYear.setVisible(true);
			   sinceYear.setVisible(true);
			   customRangeTo.setVisible(true);
			   customRangeFrom.setVisible(true);
			   search.setVisible(true);
			   reset.setVisible(true);
			   searchLabel.setVisible(true);
			   sinceYearLabel.setVisible(true);
			   rangeLabel.setVisible(true);
			   nameTitleTagsLabel.setVisible(true);
			   nameTitleTags.setVisible(true);
			   queryLabel.setVisible(true);
			   noPubl.setVisible(false);
			   PubLabel.setVisible(false);
			   errorLabel.setVisible(true);
			   resultNumberLabel.setVisible(true);
			   errorLabel.setText("");
			   resultNumberLabel.setText("");
			   break;
		   case "Query 2" :
			   searchBy.setVisible(false);
			   sortByRelevance.setVisible(false);
			   sortByYear.setVisible(false);
			   sinceYear.setVisible(false);
			   customRangeTo.setVisible(false);
			   customRangeFrom.setVisible(false);
			   search.setVisible(true);
			   reset.setVisible(true);
			   searchLabel.setVisible(false);
			   sinceYearLabel.setVisible(false);
			   rangeLabel.setVisible(false);
			   nameTitleTags.setVisible(false);
			   nameTitleTagsLabel.setVisible(false);
			   queryLabel.setVisible(false);
			   noPubl.setVisible(true);
			   PubLabel.setVisible(true);
			   errorLabel.setVisible(true);
			   resultNumberLabel.setVisible(true);
			   errorLabel.setText("");
			   resultNumberLabel.setText("");
			   break;
			 
		   }
	   }
   }
   
   class searchListener implements ActionListener {
	   public void actionPerformed(ActionEvent e)
	   {
		  String selectedQuery = queryChoice.getSelectedItem().toString();
		  currentIndex = 0;
		  switch(selectedQuery)
		  {
		  case "Query 1" :
			  System.out.println("query 1 encountered.");
			  String selectedSearch = searchBy.getSelectedItem().toString();
			  if(selectedSearch.equals("search by"))
			  {
				  errorLabel.setText("Please select the search by option.");
				  
			  }
			  else if(selectedSearch.equals("By Author"))
			  {
				  System.out.println("Name/Title Tag : " + nameTitleTags.getText());
				  if(nameTitleTags.getText() == null  || nameTitleTags.getText().equals(""))
				  {
					  errorLabel.setText("Please specify Name/Title query string.");
				  }
				  else
				  {
					  System.out.println("Calculating for author's publications." );
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
				  {
					  errorLabel.setText("Please specify title tag.");
				  }
				  else
				  {
					  errorLabel.setText("");
					  if(pubMap != null)
						  pubMap.clear();
					  pubMap = control.getPublicatonByTitle(nameTitleTags.getText());
					  if(pubList != null)
						  pubList.clear();
					  for(Map.Entry<Integer,List<Publication> > entry : pubMap.entrySet())
					  {
						  pubList.addAll(entry.getValue());
					  }
					  if(sortByRelevance.isSelected() && sortByYear.isSelected())
					  {
						  errorLabel.setText("Please select only one of the two sorting parameters.");
					  }
					  else if(sortByRelevance.isSelected()){
						  displayNextData(pubList);
						  if(pubList.size() > 0)
							  resultNumberLabel.setText(String.valueOf(pubList.size()));
						  else
							  resultNumberLabel.setText("No result!");
					  }
					  else if(sortByYear.isSelected()){errorLabel.setText("");
						  control.sortPublicationByDate(pubList);
						  displayNextData(pubList);
						  if(pubList.size() > 0)
							  resultNumberLabel.setText(String.valueOf(pubList.size()));
						  else
							  resultNumberLabel.setText("No result!");
					  }
					  else
					  {
						  errorLabel.setText("Please select sorting parameter!");
					  }
			  		}
			   }
			  break;
		  case "Query 2" :
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