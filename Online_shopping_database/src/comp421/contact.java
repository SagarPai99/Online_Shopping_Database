package comp421;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.*;



public class contact extends JPanel
{
	
	int userid; 
	SQL sql;
	java.sql.ResultSet rs = null;
	JTable table = null;
	JButton backButton = new JButton("Back");
	MainFrame mainFrame = null;
	
     public contact(int id,SQL sqlo,java.sql.ResultSet rs,MainFrame mainFrame) throws SQLException{
    	 super(new GridLayout(1,0));
    	 setBackground(new Color(0, 0, 51));
    	 System.out.println("Here1");
    	 userid = id;
    	 sql = sqlo;
    	 this.rs = rs;
    	 System.out.println(this.rs);
    	 System.out.println("Here");
    	 /*while(this.rs.next())
    	 {
    		 System.out.println(this.rs.getString(1));
    	 }*/
    	 rs.last();
    	 int size=rs.getRow();
	     rs.beforeFirst();
	     System.out.println("Size:"+size);
    	 this.mainFrame = mainFrame;
    	 System.out.println("Here2");
    	 table = new JTable(new goodsTableModule());
    	 mainFrame.setBackground(new Color(255, 153, 0));
    	 //table.setPreferredSize(new Dimension(1400,70));
    	 table.setFillsViewportHeight(true);
    	 table.setRowHeight(50);
    	 JScrollPane scrollPane = new JScrollPane(table); 
    	 scrollPane.setBackground(new Color(0, 0, 51));
    	 initColumnSizes(table);
    	// setUpAddCartColumn(table,table.getColumnModel().getColumn(4));
    	 this.add(scrollPane);
    	
  
    	// to be editted  
    	 
     }
     
     
   

    private int getSize(java.sql.ResultSet resultSet)
    {
    	int rowCount = 0;
        try {
            while(resultSet.next())
            {
            	rowCount+=1;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return rowCount;
    }

	private void initColumnSizes(JTable table) {
	    TableColumn column = null;
		for(int i = 0;i<6;i++)
	   { 
			column = table.getColumnModel().getColumn(i);
		   if(i == 1|| i == 0)
			  column.setPreferredWidth(240);
		   if(i == 2)
			   column.setPreferredWidth(450);
		   if(i == 3)
			   column.setPreferredWidth(120);
		   if(i == 4)
			   column.setPreferredWidth(100);
		   if(i == 5)
			   column.setPreferredWidth(100);
		  /* if(i == 7|| i == 8)
			   column.setPreferredWidth(75);
		   if(i == 9)
			   column.setPreferredWidth(100);
		   */
	   }
		
		
	}


	class goodsTableModule extends AbstractTableModel{

		
		private String[] columnNames={"Brand","Street Address","City","State","Start Time","End Time"};
    	 private Object[][] data;
    	 public final Object[] longValue = {"Microsoft","'2045 Sanguinet Street'","Quebec","10:00 am","6:00 pm"};
    			          // "computer accessories"};//,"759944-0010","black","100","1000","100000"};
    	 
    	 
    	 public goodsTableModule() throws SQLException
    	 {
    		 super();
    		 data = initData();
    		 System.out.println("Sagar"+data);
    	 }
    	 
    	 
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return  columnNames.length;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return data.length;
			//return 0;
		}
        
		@Override
		 public String getColumnName(int col) {
            return columnNames[col];
        }
		
		@Override
		public Object getValueAt(int row, int col) {
			// TODO Auto-generated method stub
			//System.out.println("get data at row " + row +" column "+ col);
			
			return data[row][col];
		}
		
		@Override  
        public boolean isCellEditable(int row, int column)  
        {  
            
            if (column == 9)  
            {  
                return true;  
            }  
            else  
            {  
                return false;  
            }  
        } 
		
		public Object[][] initData() throws SQLException
		{
			int count = 0;
			Object[][] data  = null;
			if(rs != null)
			{
				System.out.println("THis is "+rs);
				int r=0;
				while(rs.next())
				{
					System.out.println("1 point"+rs.getString(1));
					r+=1;
				}
				rs.beforeFirst();
				//r=0;
				/*while(rs.next())
				{
					System.out.println("1 point"+rs.getString(1));
					r+=1;
				}
				r=0;
				while(rs.next())
				{
					System.out.println("1 point"+rs.getString(1));
					r+=1;
				}*/
			   data = new Object[r][10];
			   System.out.println(r+"yolo");
			   System.out.println(data.length);
			if(data.length!=0)
				while(rs.next())
				{
					data[count][0] = rs.getString(1);
				    data[count][1] = rs.getString(2);
				    data[count][2] = rs.getString(3);	
				    data[count][3] = rs.getString(4);	
				    data[count][4] = rs.getString(5);
				    data[count][5] = rs.getString(6);
				    //data[count][6] = rs.getString(7);
				    //data[count][7] = rs.getString(8);
				    //data[count][8] = rs.getString(9);
				    //data[count][9] = "";
				    count++;
				}
			else
			 return null;
			}
			rs.beforeFirst();
			return data;
		}
		
		 public Class getColumnClass(int c) {
	            return getValueAt(0, c).getClass();
	        }
		 	
		
   	 
     }

	private void createUI(int id, SQL sqlo,java.sql.ResultSet rs) throws SQLException
	{
		
		
		JFrame frame = new JFrame("TableRenderDemo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //Create and set up the content pane.
       /*         *newContentPane.setOpaque(true); //content panes must be opaque
        *frame.setContentPane(newContentPane);
        */
        JPanel titlePanel = new JPanel(new GridLayout(1,1));
        titlePanel.add(new JLabel("Contact Us"));
        JPanel buttonPanel = new JPanel(new GridLayout(1,1));
         // buttonPanel.setLayout(null);
        backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				mainFrame.setVisible(true);
				frame.dispose();
				//other implements on close 
			}
        });
        backButton.setBounds(400, 50, 200, 50);
        buttonPanel.add(backButton);
        //SaveToCartFrame newContentPane = new SaveToCartFrame(id, sqlo,rs, searchFrame);

        frame.getContentPane().add(titlePanel,BorderLayout.NORTH);
  
        frame.getContentPane().add(this,BorderLayout.CENTER);
        
        //buttonPanel.setSize(1000,200);
        frame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
        
        frame.setSize(1400,800);
        
        //Display the window.
        
        frame.setVisible(true);
		
		
	}
	

	public static void invoke(int id,SQL sql, java.sql.ResultSet rs,MainFrame mainFrame) throws SQLException
	{ 
		contact frame = new contact(id,sql,rs,mainFrame);
		frame.createUI(id, sql, rs);
       frame.setVisible(true);
	}
	
	
	
	
}