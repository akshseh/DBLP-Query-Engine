import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
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
               FontFrame frame = new FontFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            }
         });
   }
}

//group layout 
class FontFrame extends JFrame
{
   public FontFrame()
   {
      setTitle("GUI");
      setSize(600, 600);

      ActionListener listener = new FontAction();

      // components

      JLabel queryLabel = new JLabel("Query: ");

      queryChoice = new JComboBox(new String[] {"" ,"Query 1", "Query 2" });

      queryChoice.addActionListener(listener);

      JLabel searchLabel = new JLabel("Search By: ");

      searchBy = new JComboBox(new String[] { "","By relevance", "By date"});

      searchBy.addActionListener(listener);

      sortByRelevance = new JCheckBox("Sort By Relevance");
      sortByRelevance.addActionListener(listener);

      sortByYear = new JCheckBox("Sort By Year");
      sortByYear.addActionListener(listener);
      nameTitleTags = new JTextArea();
      sample = new JTextArea();
      sample.setText("No queries to be displayed ! ");
      sample.setEditable(false);
      sample.setLineWrap(true);
      sample.setBorder(BorderFactory.createEtchedBorder());

      pane = new JScrollPane(sample);

      GroupLayout layout = new GroupLayout(getContentPane());
      setLayout(layout);
      
      layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap()
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            				.addGroup(GroupLayout.Alignment.TRAILING,layout.createSequentialGroup()
            						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            								.addComponent(queryLabel)
            								.addComponent(searchLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                          	.addComponent(searchBy)
                                            .addComponent(queryChoice))
                                    )
            				.addComponent(sortByYear).
            				addComponent(sortByRelevance)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            		.addComponent(pane).addContainerGap()
            		)
            );

      layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] { queryChoice ,searchBy });
      
      layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap()
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            				.addComponent(pane, GroupLayout.Alignment.TRAILING)
            				.addGroup(layout.createSequentialGroup()
            						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                          .addComponent(queryChoice)
                                          .addComponent(queryLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                          .addComponent(searchBy)
                                          .addComponent(searchLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(sortByYear, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(sortByRelevance, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap()
                        )
            );
   }

   private JComboBox queryChoice;
   private JComboBox searchBy;
   private JCheckBox sortByRelevance;
   private JCheckBox sortByYear;
   private JScrollPane pane;
   private JTextArea sample;
   private JTextArea nameTitleTags;
   

 
  private class FontAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
      }
   }
}
