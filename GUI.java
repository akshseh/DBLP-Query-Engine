import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

public class GUI
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               DBLP_GUI frame = new DBLP_GUI();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            }
         });
   }
}

//group layout 
class DBLP_GUI extends JFrame
{
	private JComboBox queryChoice;
	private JComboBox searchBy;
	private JCheckBox sortByRelevance;
	private JCheckBox sortByYear;
	private JScrollPane pane;
	private JTextArea queryResults;
	private JTextArea nameTitleTags;
   	private JTextArea sinceYear;
   	private JTextArea customRangeTo;
   	private JTextArea customRangeFrom;
   	private JButton search;
	private JButton next;
	private JButton reset;
	private JTable jt;
   	
   public DBLP_GUI()
   {
      setTitle("GUI");
      setSize(900, 510);

      ActionListener listener = new DblpAction();
      // components
      JLabel queryLabel = new JLabel("Query: ");
      queryChoice = new JComboBox(new String[] {"query" ,"Query 1", "Query 2" });
      queryChoice.addActionListener(listener);

      JLabel searchLabel = new JLabel("Search By: ");
      searchBy = new JComboBox(new String[] { "search by","By relevance","By date"});
     // searchBy.addActionListener(listener);
      JLabel nameTitleTagsLabel = new JLabel("Name/Title Tags: ");
      JLabel sinceYearLabel = new JLabel("Since Year: ");
      JLabel rangeLabel = new JLabel("Custom Range: ");
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
     // sinceYear.addActionListener();
      sortByRelevance = new JCheckBox("Sort By Relevance");
    //  sortByRelevance.addActionListener(listener);
      sortByYear = new JCheckBox("Sort By Year");
     // sortByYear.addActionListener(listener);
      nameTitleTags = new JTextArea();
      String data[][]={ {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},
    		  {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},
    		  {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},
    		  {"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""}};  
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
      reset = new JButton("reset");
      next = new JButton("next");
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
            								.addComponent(nameTitleTagsLabel)
            								.addComponent(sinceYearLabel)
            								.addComponent(rangeLabel)
            								.addComponent(search)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                          	.addComponent(searchBy)
                                            .addComponent(queryChoice)
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
                                    		.addComponent(reset)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGap(220))).addContainerGap()
                        ));
   }
 
  private class DblpAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
      }
   }
}