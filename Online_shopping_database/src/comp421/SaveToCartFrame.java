package comp421;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.*;



//need to add button back -done 
//sql code to be implemented in button editor -done
// case no result is empty, will not have table, but implement an panel with notice 
public class SaveToCartFrame extends JPanel 
{
	
	int userid; 
	SQL sql;
	java.sql.ResultSet rs = null;
	JTable table = null;
	JButton backButton = new JButton("Back");
	SearchFrame searchFrame = null;
	
     public SaveToCartFrame(int id,SQL sqlo,java.sql.ResultSet rs,SearchFrame searchFrame) throws SQLException{
    	 super(new GridLayout(1,0));
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
    	 this.searchFrame = searchFrame;
    	 System.out.println("Here2");
    	 table = new JTable(new goodsTableModule());
    	 //table.setPreferredSize(new Dimension(1400,70));
    	 table.setFillsViewportHeight(true);
    	 table.setRowHeight(50);
    	 JScrollPane scrollPane = new JScrollPane(table); 
    	 initColumnSizes(table);
    	 setUpAddCartColumn(table,table.getColumnModel().getColumn(9));
    	 this.add(scrollPane);
    	
  
    	// to be editted  
    	 
     }
     
     
     private void setUpAddCartColumn(JTable table, TableColumn column) throws SQLException {
	     
         column.setCellEditor(new MyButtonEditor());
         column.setCellRenderer(new MyButtonRender());
		
	}
     public class MyButtonRender implements TableCellRenderer
     {
    	   private JPanel panel;

    	    private JButton button;
    	    
    	    
    	    public  MyButtonRender()
    	    {
    	        this.initButton();

    	        this.initPanel();

    	        this.panel.add(this.button);
    	    }
    	   
    	    private void initButton()
    	    {
    	        this.button = new JButton("Add");

    	        this.button.setBounds(10, 10, 80, 30);
    	    }
    	   
    	    private void initPanel()
    	    {
    	        this.panel = new JPanel();

    	        this.panel.setLayout(null);
    	    }


			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				
			        return this.panel;
			}
     }
     
     public class MyButtonEditor extends DefaultCellEditor
     {
    	 private JButton button;
         private JPanel panel;
         
		public MyButtonEditor() 
		{
			super(new JTextField());
			 this.setClickCountToStart(1);
			 
	            this.initButton();
	 
	            this.initPanel();
	            
	            this.panel.add(this.button);
	       
		}
		  private void initButton()
          {
              this.button = new JButton("Add");
   
              // set size and positon 
              this.button.setBounds(10, 10, 80, 30);
   
              
              this.button.addActionListener(new ActionListener()
              {   
                  public void actionPerformed(ActionEvent e)
                  {
                   
                      int row = table.getSelectedRow();
                     
                      /*insert into Save_To_Shopping_Cart
                      values(userid, pid, addTime, quantity);*/
                        
                      String code = "select quantity from Save_To_Shopping_Cart Where userid = "+userid+" and pid = "+table.getValueAt(row, 0);
                      java.sql.ResultSet result = sql.QueryExchte(code);
                      /*int q=0;
                      try{while(result.next())
                      {
                    	  q=Integer.parseInt(result.getString(1));
                    	  
                      }}
                      catch(Exception f)
                      {
                    	  System.out.println("lol");
                    	  
                      }*/
                 
                      try{result.beforeFirst();}
                      catch(Exception f) {System.out.println("lol");}
                      int f=getSize(result);
                      System.out.println("This is the size"+f);
                      String sqlCode = "";
                      if(f==0)
                      {
                    	  sqlCode += "insert into";
                    	  sqlCode += " Save_To_Shopping_Cart values(";
                    	  sqlCode += userid + ", ";
                    	  sqlCode += table.getValueAt(row, 0)+", ";// pid
                    	  Date d = new Date();
                    	  SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                    	  sqlCode +="\'"+ sdf.format(d)+"\'"+ ", ";
                    
                    	  sqlCode += (0+1)+")";
                      }
                      else 
                      {
                    	  try {
                    		  result.beforeFirst();
                    		  if(result.next())
                    		  {
                    			  sqlCode += "UPDATE Save_To_Shopping_Cart Set quantity = " + (result.getInt(1)+1);
                        		  sqlCode += " where userid = "+ userid + " and pid = "+  table.getValueAt(row, 0);
                        		   
                    		  }
                    		  
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                      }
                      System.out.println(sqlCode);
                      sql.WriteExecute(sqlCode);
     
                  }
              });
   
          }
		  
		  private void initPanel()
	        {
	            this.panel = new JPanel();
	 
	            
	            this.panel.setLayout(null);
	        }
	        @Override
	        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	        {
               return this.panel;
	        }
	 

		

		
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
		for(int i = 0;i<10;i++)
	   { 
			column = table.getColumnModel().getColumn(i);
		   if(i == 1|| i == 0)
			  column.setPreferredWidth(40);
		   if(i == 2)
			   column.setPreferredWidth(120);
		   if(i == 3)
			   column.setPreferredWidth(450);
		   if(i == 4|| i ==5)
			   column.setPreferredWidth(200);
		   if(i == 6)
			   column.setPreferredWidth(100);
		   if(i == 7|| i == 8)
			   column.setPreferredWidth(75);
		   if(i == 9)
			   column.setPreferredWidth(100);
		   
	   }
		
		/* goodsTableModule model = (goodsTableModule)table.getModel();
	     TableColumn column = null;
	     Component comp = null;
	     int headerWidth = 0;
	     int cellWidth = 0;
	     
	     Object[] longValue = model.longValue;
	     
	     TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
	     for(int i = 0; i<9; i++)
	     {
	    	 column = table.getColumnModel().getColumn(i);
	    	 
	    	 comp = headerRenderer.getTableCellRendererComponent(table, longValue[i], false, false, 0, 0);
	    	 headerWidth = comp.getPreferredSize().width;
	    	   comp = table.getDefaultRenderer(model.getColumnClass(i)).
                       getTableCellRendererComponent(
                           table, longValue[i],
                           false, false, 0, i);
             cellWidth = comp.getPreferredSize().width;
             
             column.setPreferredWidth(Math.max(headerWidth, cellWidth));
	    	 
	     }*/
	}


	class goodsTableModule extends AbstractTableModel{

		
		private String[] columnNames={"PID","Shop ID","brand","Product Name","Type","Model Number","color"
         ,"amount","Price","Add to Cart"};
    	 private Object[][] data;
    	 public final Object[] longValue = {new Integer(10),new Integer(100),"Microsoft","'DELL Ultra HD 4k Monitor P2715Q 27-Inch Screen LED-Lit Monitor",
    			           "computer accessories","759944-0010","black","100","1000","100000"};
    	 
    	 
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
				    data[count][6] = rs.getString(7);
				    data[count][7] = rs.getString(8);
				    data[count][8] = rs.getString(9);
				    data[count][9] = "";
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
        titlePanel.add(new JLabel("Select products to build order"));
        JPanel buttonPanel = new JPanel(new GridLayout(1,1));
         // buttonPanel.setLayout(null);
        backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				searchFrame.setVisible(true);
				frame.dispose();
				//other implements on close 
			}
        });
        backButton.setBounds(400, 50, 200, 50);
        buttonPanel.add(backButton);
        SaveToCartFrame newContentPane = new SaveToCartFrame(id, sqlo,rs, searchFrame);

        frame.add(titlePanel,BorderLayout.NORTH);
  
        frame.add(this,BorderLayout.CENTER);
        
        //buttonPanel.setSize(1000,200);
        frame.add(buttonPanel,BorderLayout.SOUTH);
        
        frame.setSize(1400,800);
        
        //Display the window.
        
        frame.setVisible(true);
		
		
	}
	

	public static void invoke(int id,SQL sql, java.sql.ResultSet rs,SearchFrame searchFrame) throws SQLException
	{
		SaveToCartFrame frame = new SaveToCartFrame(id,sql,rs,searchFrame);
		frame.createUI(id, sql, rs);
       
	}
	
	
	
	
}