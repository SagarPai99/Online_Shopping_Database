package comp421;

//sql code to be implemented in button  --done 
//test if the address is empty 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame{
	JTextField userid = new JTextField ("User id");
	JTextField phonenumber = new JTextField("Phone number");
	JButton login = new JButton("LOG IN");
	JButton back = new JButton("BACK");
	
	SQL loginsql;
	ResultSet rs;
	String sqlcode;
	String uid;
	String pnum;
	MainFrame mainFrame = null;
	Login frame = this;
	public Login(SQL sqlo,MainFrame mainFrame){
		loginsql=sqlo;
		this.mainFrame = mainFrame;
		JPanel panel1 = new JPanel();
		panel1.setForeground(Color.WHITE);
		panel1.setBackground(new Color(254, 187, 56));
		panel1.setLayout(null);
		JLabel label = new JLabel("User id: ");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(49, 11, 105, 76);
		panel1.add(label);
 userid.setBounds(217, 29, 194, 45);
 panel1.add (userid);
		JLabel label_1 = new JLabel("Phone Number: ");
		label_1.setHorizontalAlignment(SwingConstants.TRAILING);
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_1.setBounds(34, 98, 120, 76);
		panel1.add(label_1);
phonenumber.setAlignmentY(Component.BOTTOM_ALIGNMENT);
phonenumber.setBounds(217, 119, 194, 45);
panel1.add(phonenumber);
		getContentPane().add(panel1, BorderLayout.CENTER);
		login.setBorderPainted(false);
		login.setForeground(Color.WHITE);
		login.setFont(new Font("Tahoma", Font.PLAIN, 12));
		login.setBackground(new Color(0, 0, 51));
		login.setBounds(10, 185, 187, 23);
		panel1.add(login);
		back.setBorderPainted(false);
		back.setForeground(Color.WHITE);
		back.setFont(new Font("Tahoma", Font.PLAIN, 12));
		back.setBackground(new Color(0, 0, 51));
		back.setBounds(217, 185, 194, 23);
		panel1.add(back);
		back.addActionListener(new loginListener());
		login.addActionListener(new loginListener());
		JPanel buttonpanel=new JPanel();
		buttonpanel.setBackground(new Color(0, 0, 102));
		buttonpanel.setLayout(new GridLayout(1,2,25,25));
		getContentPane().add(buttonpanel,BorderLayout.SOUTH);
	}
	public static void invoke(SQL sqlo,MainFrame mainFrame){
		JFrame login = new Login(sqlo,mainFrame);
		login.setTitle("User log in");
		login.setLocationRelativeTo(null);
		login.setSize(450,250);
		login.setVisible(true);
		login.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	class loginListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==login){
				uid = userid.getText();
				pnum = phonenumber.getText();
				if(uid.trim().isEmpty() & pnum.trim().isEmpty())
					JOptionPane.showMessageDialog(null,"User id and phone number cannot leave blank ","Error", JOptionPane.ERROR_MESSAGE);
				else if(uid.trim().isEmpty())
					JOptionPane.showMessageDialog(null, "You must input your user id","Error", JOptionPane.ERROR_MESSAGE);
				else if(pnum.trim().isEmpty() || pnum.toLowerCase()=="phone number")
					JOptionPane.showMessageDialog(null, "You must input your phone number","Error", JOptionPane.ERROR_MESSAGE);
				else if(!isbuyer(uid) || uid.toLowerCase()=="user id")
					JOptionPane.showMessageDialog(null, "You have not registered. You must register before login", "Error",JOptionPane.ERROR_MESSAGE);
				else
					try {
						// The information is correct
						if(pnum.equals(getresult())){
							int userid= Integer.parseInt(uid);
							Image img = new ImageIcon(this.getClass().getResource("/success.png")).getImage();
							JOptionPane.showMessageDialog(null, "You have logged in successfully", "Log in successfully", JOptionPane.INFORMATION_MESSAGE,new ImageIcon(img));
						
							mainFrame.setUserid(userid);
							mainFrame.setAddAddressButtonEnable(true);
							mainFrame.setVisible(true);
							frame.dispose();
						}
						else
							JOptionPane.showMessageDialog(null, "The user id or Phone number is not correct", "Log in failed",JOptionPane.ERROR_MESSAGE);
					} catch (NumberFormatException | HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
						}
			}
			else if(e.getSource()==back){
				mainFrame.setVisible(true);
				frame.dispose();
			}
		}
	}
	public String getresult() throws SQLException{
		
		sqlcode="select phoneNumber from users where userid = "+uid;// Check the pnum based on the uid
		rs=loginsql.QueryExchte(sqlcode);
		rs.next();
		String pnumindb = rs.getString(1);
		System.out.println(rs.getString(1));
		System.out.println("for userid "+uid+" the phonenumber should be "+ pnumindb);
		return pnumindb;
	}
	
	public boolean isbuyer(String uid){
		sqlcode="select userid from buyer where userid = "+uid;
		rs=loginsql.QueryExchte(sqlcode);
		int rowCount = 0;
      try {
    	  System.out.println("efdgffd");
    	  System.out.println(rs);
          //rs.last();
          System.out.println("efdgffd");
          //rowCount = rs.getRow();
          System.out.println("efdgffd");
          //System.out.println(rowCount);
          while (rs.next())
	        {		
	        	System.out.println(rs.getString(1));
	        	if(Integer.parseInt(rs.getString(1)) == Integer.parseInt(uid)){			   
	              	return true;
	             }
	        	//System.out.println("1");
	        }
      } catch (Exception e) {
          // TODO: handle exception
    	  System.out.print("Sgar");
          e.printStackTrace();
      }
      return false;
	}
	
}