package comp421;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MainFrame extends JFrame {
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Register");
	JButton searchProductButton = new JButton ("Search Product");
	JButton buyButton = new JButton ("Shopping Cart/Purchase");
	JButton addAddressButton = new JButton("Add a new Address");
	JButton QuitButton = new JButton("Quit");
	JLabel noticeString = new JLabel("Please login or Register first");
	JLabel reminderString = new JLabel("");
	JButton myOrders = new JButton("My orders");
	JButton ct = new JButton("Contact Us");
	JButton st = new JButton("Stores");
	JLabel imageLabel1 = new JLabel();
    
	MainFrame mainFrame = this;
	SQL sql = null;
	int userid;
	
	public MainFrame() throws Exception
	{
		getContentPane().setBackground(new Color(254, 187,56));
		//setPreferredSize(new Dimension(500, 500));
		// run initial the sql, build connection 
		sql = new SQL();
		userid = 0;
		 
		// button panel for frame
		 JPanel mainArc  = new JPanel();
		 mainArc.setBackground(new Color(0, 0, 51));
		 mainArc.setBounds(0, 0, 638, 163);
	    // listener for main frame, need sql passed to subframe 
	     MainButtonListener listener = new MainButtonListener(sql); //need to pass the sql class and userid 
	    loginButton.setBorderPainted(false);
	    loginButton.setBackground(new Color(0, 0, 51));
	    //loginButton.setBackground(Color.BLACK);
	    loginButton.setForeground(Color.WHITE);
	    loginButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    loginButton.setBounds(11, 11, 192, 60);
	
	    loginButton.addActionListener(listener);
	    mainArc.setLayout(null);
	    mainArc.add(loginButton);
	    registerButton.setBorderPainted(false);
	    registerButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    registerButton.setForeground(Color.WHITE);
	    registerButton.setBackground(new Color(0, 0, 51));
	    registerButton.setBounds(223, 11, 193, 60);
	    registerButton.addActionListener(listener);
		mainArc.add(registerButton);
		addAddressButton.setBorderPainted(false);
		addAddressButton.setBackground(new Color(0, 0, 51));
		addAddressButton.setForeground(Color.WHITE);
		addAddressButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addAddressButton.setBounds(426, 11, 193, 60);
		
		addAddressButton.addActionListener(listener);
        addAddressButton.setEnabled(false);
		mainArc.add(addAddressButton);
		searchProductButton.setBorderPainted(false);
		searchProductButton.setBackground(new Color(0, 0, 51));
		searchProductButton.setForeground(Color.WHITE);
		searchProductButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		searchProductButton.setBounds(11, 81, 193, 71);
		searchProductButton.addActionListener(listener);
		searchProductButton.setEnabled(false);
		mainArc.add(searchProductButton);
		buyButton.setBorderPainted(false);
		buyButton.setForeground(Color.WHITE);
		buyButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buyButton.setBackground(new Color(0, 0, 51));
		buyButton.setBounds(214, 81, 244, 71);
		buyButton.addActionListener(listener);
		buyButton.setEnabled(false);
		mainArc.add(buyButton);
		QuitButton.setBorderPainted(false);
		QuitButton.setForeground(Color.WHITE);
		QuitButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		QuitButton.setBackground(new Color(0, 0, 51));
		QuitButton.setBounds(426, 82, 192, 71);
		
		QuitButton.addActionListener(listener);
	    getContentPane().setLayout(null);
	    mainArc.add(QuitButton); 
	    
	    
	    //setUserid(106);
	    getContentPane().add(mainArc);
	    Image img = new ImageIcon(this.getClass().getResource("/best1.PNG")).getImage();
	    Image img1 = new ImageIcon(this.getClass().getResource("/ls.PNG")).getImage();
	    JPanel stringPanel =  new JPanel();
	    stringPanel.setBorder(null);
	    stringPanel.setBackground(new Color(254, 187, 56));
	    stringPanel.setBounds(10, 174, 344, 32);
	    getContentPane().add(stringPanel);
	    noticeString.setForeground(Color.WHITE);
	    noticeString.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    
	    stringPanel.add(noticeString);
	    imageLabel1 = new JLabel(new ImageIcon(img1));
	    
	    imageLabel1.setBounds(96, 205, 173, 163);
	    getContentPane().add(imageLabel1);
	    imageLabel1.setVisible(false);
	    JLabel imageLabel = new JLabel(new ImageIcon(img));
	    imageLabel.setBounds(368, 174, 250, 243);
	    getContentPane().add(imageLabel);
	    myOrders.setFont(new Font("Tahoma", Font.BOLD, 14));
	    myOrders.setForeground(Color.WHITE);
	    myOrders.setBackground(new Color(0, 0, 51));
	    myOrders.setBounds(116, 368, 134, 36);
	    getContentPane().add(myOrders);
	    ct.setFont(new Font("Tahoma", Font.BOLD, 14));
	    ct.setForeground(Color.WHITE);
	    ct.setBackground(new Color(0, 0, 51));
	    ct.setBounds(116, 415, 134, 36);
	    getContentPane().add(ct);
	    ct.addActionListener(listener);
	    st.setLocation(446, 426);
	    st.setSize(92, 25);
	    st.setFont(new Font("Tahoma", Font.BOLD, 14));
	    st.setForeground(Color.WHITE);
	    st.setBackground(new Color(0, 0, 51));
	    //ct.setBounds(116, 415, 134, 36);
	    getContentPane().add(st);
	    st.addActionListener(listener);
	    myOrders.setEnabled(false);
	    myOrders.addActionListener(listener);
	}
	
	
	public void setUserid(int id) throws SQLException
	{
		userid = id;
		noticeString.setText("Welcome user: "+ id);
		System.out.println("Userid is set to "+ userid);
		setAddAddressButtonEnable(true);
		setLoginButtonEnable(false);
		setRegisterEnable(false);
		//check if there is address ;
		String sqlCode = "select * from address where userid = "+id;
		java.sql.ResultSet result = sql.QueryExchte(sqlCode);
		if(result.next())
		{
			setSearchAndBuyButtonEnable(true);
		}
		else
		{
			reminderString.setText("Please add an address before your shopping");
		}
		
		
	}
	
	public void setAddAddressButtonEnable(boolean b)
	{
		addAddressButton.setEnabled(b);
	}
	public void setSearchAndBuyButtonEnable(boolean b)
	{
		reminderString.setText("Please enjoy your shopping!");
		searchProductButton.setEnabled(b);
		buyButton.setEnabled(b);
		myOrders.setEnabled(b);
		imageLabel1.setVisible(b);
	}
	
	public void setLoginButtonEnable(boolean b){
		loginButton.setEnabled(b);
	}
	
	public void setRegisterEnable(boolean b){
		registerButton.setEnabled(b);
	}
	
	
	  public static void main(String[] args) throws Exception
	    {
	        
	        MainFrame frame = new MainFrame();
	        
	        frame.setTitle("Electronic Shop");
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(650, 500);
	        
	        
	       frame.setVisible(true);
	       // frame.pack();
	    }
      	  
	 
	 public class MainButtonListener implements ActionListener {


			SQL sql = null;
			public MainButtonListener(SQL sqlpassed)
			{
			    sql = sqlpassed;	
			}
			
			
			public void actionPerformed(ActionEvent event ) {
				
				if(event.getSource() == loginButton)
				{
					//mainFrame to be passed 
				  Login.invoke(sql,mainFrame);
				  mainFrame.setVisible(false);
				}
				else if(event.getSource() == registerButton)
				{
					//mainFrame to be passed 
					Register.invoke(sql,mainFrame);
					mainFrame.setVisible(false);
				}
				else if (event.getSource() == addAddressButton)
				{
					//mainFrame to be passed 
					System.out.println("userid = "+ userid+ "is passed to addAddressButton");
					AddAddress.invoke(userid,sql,mainFrame);
				
					mainFrame.setVisible(false);
				}
				else if (event.getSource() == searchProductButton)
				{
					//done 
					mainFrame.setVisible(false);
					SearchFrame.invoke(userid,sql,mainFrame);
					
					
				}
				else if(event.getSource() == buyButton)
				{
				 String sqlCode = "select P.name, S.addTime, S.quantity, P.pid, P.price from Save_To_Shopping_Cart S, Product P where S.pid = P.pid and S.userid = "+ userid;
				 java.sql.ResultSet rs = sql.QueryExchte(sqlCode);
				 
				 
				 int rowCount = 0;
		          try {
		              rs.last();
		              rowCount = rs.getRow();
		              rs.first();
		          } catch (Exception e) {
		              // TODO: handle exception
		              e.printStackTrace();
		          }
		          if(rowCount == 0)			   
		          	JOptionPane.showMessageDialog(null, "No product is added into cart yet", "NO Result",JOptionPane.OK_OPTION);			      	
		          else 
					try {
						SetUpOrderFrame.invoke(userid, sql,rs,mainFrame);
						mainFrame.setVisible(false);
					} catch (SQLException e) {
					}
				}
				else if(event.getSource() == myOrders)
				{
					String sqlCode=" select streetaddr,city,state,ordernumber,address.name,totalamount,price "
							+ "from orders natural join orderitem "
							+ "natural join contain "
							+ "natural join deliver_to "
							+ "natural join address where userid="+userid;
					
					java.sql.ResultSet rs = sql.QueryExchte(sqlCode);
					int rowCount = 0;
			          try {
			              rs.last();
			              rowCount = rs.getRow();
			              rs.first();
			          } catch (Exception e) {
			              // TODO: handle exception
			              e.printStackTrace();
			          }
			          if(rowCount == 0)			   
			          	JOptionPane.showMessageDialog(null, "No Orders Yet", "NO Result",JOptionPane.OK_OPTION);			      	
			          else 
						try {
							System.out.println(rowCount);
							ShowOrder.invoke(userid, sql,rs,mainFrame);
							mainFrame.setVisible(false);
						} catch (SQLException e) {
						}
				}
				else if(event.getSource() == ct)
				{
					String sqlCode=" select brandname,streetaddr,city,state,starttime,endtime from after_sales_service_at natural join servicepoint";
					
					java.sql.ResultSet rs = sql.QueryExchte(sqlCode);
					int rowCount = 0;
			          try {
			              rs.last();
			              rowCount = rs.getRow();
			              rs.first();
			          } catch (Exception e) {
			              // TODO: handle exception
			              e.printStackTrace();
			          }
			          if(rowCount == 0)			   
			          	JOptionPane.showMessageDialog(null, "No Contacts Yet", "NO Result",JOptionPane.OK_OPTION);			      	
			          else 
						try {
							System.out.println(rowCount);
							contact.invoke(userid, sql,rs,mainFrame);
							mainFrame.setVisible(false);
						} catch (SQLException e) {
						}
				}
				else if(event.getSource() == st)
				{
					String sqlCode=" select name,state,city,streetaddr,customergrade,starttime from store natural join manage";
					
					java.sql.ResultSet rs = sql.QueryExchte(sqlCode);
					int rowCount = 0;
			          try {
			              rs.last();
			              rowCount = rs.getRow();
			              rs.first();
			          } catch (Exception e) {
			              // TODO: handle exception
			              e.printStackTrace();
			          }
			          if(rowCount == 0)			   
			          	JOptionPane.showMessageDialog(null, "No Stores Yet", "NO Result",JOptionPane.OK_OPTION);			      	
			          else 
						try {
							System.out.println(rowCount);
							stores.invoke(userid, sql,rs,mainFrame);
							mainFrame.setVisible(false);
						} catch (SQLException e) {
						}
				}
				else if(event.getSource() == QuitButton)
				{
                    //done 
					System.exit(0);
				}
			
					
		}

	}
	    
}
