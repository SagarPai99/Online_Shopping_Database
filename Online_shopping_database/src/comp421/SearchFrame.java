package comp421;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;


//sql operation in button submit to be implemented  --done


public class SearchFrame extends JFrame{
	
	JButton submitButton = new JButton("SUBMIT");
	JButton backButton = new JButton("BACK");
	
	JTextField nameTF = new JTextField();
	JTextField typeTF = new JTextField();
	JTextField ModuleNumTF = new JTextField();
	JTextField BrandTF = new JTextField();
	SearchFrame frame = this;
	int userid; 
	SQL sql;
	MainFrame mainFrame;
	
	public SearchFrame(int id,SQL sqlo,MainFrame mainFrame)
	{
		userid = id;
		sql = sqlo;
		this.mainFrame = mainFrame;
		Panel buttonPanel = new Panel();
		buttonPanel.setBackground(new Color(254, 187, 56));
	    SearchListener listener = new SearchListener();
	    backButton.setBackground(new Color(0, 0, 51));
	    backButton.setForeground(Color.WHITE);
	    backButton.setBounds(10, 321, 246, 58);
	    backButton.addActionListener(listener);
		buttonPanel.setLayout(null);
		buttonPanel.add(backButton);
	    getContentPane().add(buttonPanel);
	    Panel searchContentPanel = new Panel();
	    searchContentPanel.setBounds(31, 47, 479, 251);
	    buttonPanel.add(searchContentPanel);
	    searchContentPanel.setLayout(null);
	    JLabel label = new JLabel("Name :");
	    label.setFont(new Font("Tahoma", Font.BOLD, 11));
	    label.setForeground(Color.WHITE);
	    label.setBounds(10, 29, 204, 34);
	    searchContentPanel.add(label);
	    nameTF.setBounds(250, 29, 204, 34);
	    searchContentPanel.add(nameTF);
	    JLabel label_1 = new JLabel("Type :");
	    label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
	    label_1.setForeground(Color.WHITE);
	    label_1.setBounds(10, 88, 204, 34);
	    searchContentPanel.add(label_1);
	    typeTF.setBounds(250, 88, 204, 34);
	    searchContentPanel.add(typeTF);
	    JLabel label_2 = new JLabel("Module Number :");
	    label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
	    label_2.setForeground(Color.WHITE);
	    label_2.setBounds(10, 146, 204, 34);
	    searchContentPanel.add(label_2);
	    ModuleNumTF.setBounds(250, 146, 204, 34);
	    searchContentPanel.add(ModuleNumTF);
	    JLabel label_3 = new JLabel("Brand :");
	    label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
	    label_3.setForeground(Color.WHITE);
	    label_3.setBounds(10, 203, 204, 34);
	    searchContentPanel.add(label_3);
	    BrandTF.setBounds(250, 203, 204, 34);
	    searchContentPanel.add(BrandTF);
	    
	    Panel titlePanel = new Panel();
	    titlePanel.setFont(new Font("Tahoma", Font.PLAIN, 24));
	    titlePanel.setLocation(78, 10);
	    buttonPanel.add(titlePanel);
	    titlePanel.setLayout(null);
	    // title panel 
		JLabel lblSearchForThe = new JLabel("SEARCH FOR THE PRODUCT");
		lblSearchForThe.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSearchForThe.setForeground(Color.WHITE);
		lblSearchForThe.setBounds(79, 0, 252, 17);
		titlePanel.add(lblSearchForThe);
		titlePanel.setSize(407,33);
		submitButton.setBackground(new Color(0, 0, 51));
		submitButton.setForeground(Color.WHITE);
		submitButton.setBounds(309, 321, 240, 58);
		buttonPanel.add(submitButton);
		submitButton.addActionListener(listener);
	}
	
	
	
	   private void setUpNewUI(int id, SQL sql,MainFrame mainFrame)
	    {
	        
	        SearchFrame frame = new SearchFrame(id,sql,mainFrame);
	        
	        frame.setTitle("Online Electronic Shop");
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setSize(580, 450);
	       frame.setVisible(true);
	       // frame.pack();
	    }
	   
	   static public void invoke(int id, SQL sql, MainFrame mainFrame)
	   {
		   SearchFrame frame = new SearchFrame(id,sql,mainFrame);
		   frame.setUpNewUI(id,sql,mainFrame);
	   }
	    
	   
	   public class SearchListener implements ActionListener{
		    

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				// System.out.println("Back Button clicked ");
				if(event.getSource() ==  submitButton)
			    	 {
			    	 // to be implemented 
					String name = nameTF.getText().trim();
				    String type = typeTF.getText().trim();
				    String modelNum = ModuleNumTF.getText().trim();
				    String brand = BrandTF.getText().trim();
				    String sqlCode = ""; 
				    
				    /* select *
				    from product
				    where name = name and type = type and modelNumber = modelNumber and brand = brand;
					*/
				    
				    sqlCode += "select * from product ";
					int notBlank = 0;
					if((name.length())!=0 || ( type.length() != 0) ||(modelNum.length()!= 0)|| (brand.length()!= 0))		  
					   sqlCode += "where ";
				     
					if(name.length()!=0)
					{
						notBlank = 1;
						sqlCode += "lower(name) like \'%"+name.toLowerCase()+"%\'";
					}
					if(type.length() != 0)
					{
						if(notBlank == 1)
							sqlCode += " and ";
						else 
							notBlank = 1;
						sqlCode += "lower(type) like "+"\'%"+type.toLowerCase()+"%\'";
					}
					if(modelNum.length()!= 0)
					{
						if(notBlank == 1)
							sqlCode += " And ";
						else 
							notBlank = 1;
						sqlCode += "lower(modelNumber) like "+"\'%"+ modelNum.toLowerCase()+"%\'";
					}
					if(brand.length()!= 0)
					{
						if(notBlank ==1)
							sqlCode += " And ";
						sqlCode += "lower(brand) like "+"\'%"+brand.toLowerCase()+"%\'";
					}
					sqlCode+= " order by brand,price";   
					  System.out.println(sqlCode);
			    	  java.sql.ResultSet rs = sql.QueryExchte(sqlCode);
			    	  
			    	  
			    	  int rowCount = 0;
			          try {
			        	  boolean b;
			        	  while(rs.next())
			        	  {
			        		  System.out.println(rs.getString(2));
			        		  rowCount+=1;
			        	  }
			          } catch (Exception e) {
			              // TODO: handle exception
			              e.printStackTrace();
			          }
			        	  if(rowCount==0)
			        	  {
			        		  JOptionPane.showMessageDialog(null, "No Result is found", "NO Result",JOptionPane.OK_OPTION);
			        	  }
			              else if(rowCount!=0)
			        	  {
			        		  try
			        		  {
			        			  while(rs.next())
					        	  {
					        		  System.out.println(rs.getString(2));
					        		  
					        	  }
			        			  SaveToCartFrame.invoke(userid, sql, rs, frame);
			        			  frame.setVisible(false);
			        		  }
			        		  catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			        		}
			    	 }
				 else if (event.getSource() == backButton)
			     {
			    	 //System.out.println("Back Button clicked ");
			    	 mainFrame.setVisible(true);
			    	 frame.dispose();
			    	 //close and invoke original one 
			    	 //to be implemented 
			     
			     }
			        	  /*else
			        	  {
			        		  mainFrame.setVisible(true);
			        		  frame.dispose();  
			        	  }*/
			        		  /* catch (Exception e) {
			              // TODO: handle exception
			              e.printStackTrace();
			          }*/
			          /*if(rowCount == 0)			   
			          	JOptionPane.showMessageDialog(null, "No Result is found", "NO Result",JOptionPane.OK_OPTION);			      	
			          else 
			        	  try 
			          {
			        		  while(rs.next())
				        	  {
				        		  System.out.println(rs.getString(2));
				        		  
				        	  }
							SaveToCartFrame.invoke(userid, sql, rs, frame);
							frame.setVisible(false);
						}  
			          catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	  //open SaveToCartFrame with rs, sql, and userid 
			    	 }*/
			    /* else if (event.getSource() == backButton)
			     {
			    	 //System.out.println("Back Button clicked ");
			    	 mainFrame.setVisible(true);
			    	 frame.dispose();
			    	 //close and invoke original one 
			    	 //to be implemented 
			     
			     }*/
			}

		}
	   
	 }