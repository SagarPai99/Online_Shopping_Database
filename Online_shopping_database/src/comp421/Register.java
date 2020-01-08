package comp421;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// need to be modified 
// card valid data should follow format strictly
// card number should be made up with only numbers and space ,but no character
// 


public class Register extends JFrame{
	JTextField name = new JTextField();
	JTextField phonenum = new JTextField();
	JTextField cardnum = new JTextField();
	JTextField expirydate = new JTextField();
	JTextField bank = new JTextField();
	JTextField organization = new JTextField();
	JButton submit = new JButton("SUBMIT");
	JButton back = new JButton("BACK");
	MainFrame mainFrame = null;
	Register frame = this;
	
	SQL adduser;
	ResultSet rs;
	int userid;
	String sqlcode;
	
	public Register(SQL sqlo,MainFrame mainFrame){
		getContentPane().setForeground(new Color(0, 0, 51));
		getContentPane().setBackground(new Color(254, 187, 56));
		this.mainFrame = mainFrame;
		adduser=sqlo;
		getContentPane().setLayout(null);
		JPanel jpanel = new JPanel();
		jpanel.setBackground(new Color(254, 187, 56));
		jpanel.setBounds(0, 0, 434, 210);
		jpanel.setLayout(null);
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(10, 2, 217, 24);
		jpanel.add(lblName);
name.setBounds(207, 2, 217, 24);
jpanel.add(name);
		JLabel label_1 = new JLabel("Phone Number:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(10, 37, 217, 24);
		jpanel.add(label_1);
	phonenum.setBounds(207, 37, 217, 24);
	jpanel.add(phonenum);
		JLabel label_2 = new JLabel("Card Number:");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(10, 72, 162, 24);
		jpanel.add(label_2);
 	cardnum.setBounds(207, 72, 217, 24);
 	jpanel.add(cardnum);
		JLabel label_3 = new JLabel("Expiry Date: (dd-mmm-yyyy");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_3.setForeground(Color.WHITE);
		label_3.setBounds(10, 107, 162, 24);
		jpanel.add(label_3);
 	expirydate.setBounds(207, 107, 217, 24);
 	jpanel.add(expirydate);
		JLabel label_4 = new JLabel("Bank:");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_4.setForeground(Color.WHITE);
		label_4.setBounds(10, 140, 162, 24);
		jpanel.add(label_4);
			bank.setBounds(207, 140, 217, 24);
			jpanel.add(bank);
		JLabel lblCardIssueOrganisation = new JLabel("Card Issue Organisation:");
		lblCardIssueOrganisation.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCardIssueOrganisation.setForeground(Color.WHITE);
		lblCardIssueOrganisation.setBounds(10, 175, 149, 24);
		jpanel.add(lblCardIssueOrganisation);
 organization.setBounds(207, 175, 217, 24);
 jpanel.add(organization);
		getContentPane().add(jpanel);
		submit.setBackground(new Color(0, 0, 51));
		submit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		submit.setForeground(Color.WHITE);
		submit.setBounds(234, 221, 168, 23);
		submit.addActionListener(new registerlistener());
		submit.setPreferredSize(new Dimension(10,10));
		getContentPane().add(submit);
		back.setBackground(new Color(0, 0, 51));
		back.setFont(new Font("Tahoma", Font.PLAIN, 12));
		back.setForeground(Color.WHITE);
		back.setBounds(20, 221, 162, 23);
		back.addActionListener(new backlistener());
		back.setPreferredSize(new Dimension(10,10));
		getContentPane().add(back);
		//this.add(back, BorderLayout.SOUTH);
	}
	public static void invoke (SQL sqlo,MainFrame mainFrame){
		JFrame register = new Register(sqlo,mainFrame);
		register.setVisible(true);
		register.setSize(475, 300);
		register.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		register.setLocationRelativeTo(null);
		register.setTitle("Register a new User");
	}
	class backlistener implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			mainFrame.setVisible(true);
			mainFrame.setAddAddressButtonEnable(true);
			frame.dispose();
		}
	}
	class registerlistener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			sqlcode ="select max(userid) from users";// get the max userid from user table
			rs = adduser.QueryExchte(sqlcode);
			try {
				rs.next();
				userid = rs.getInt(1)+1;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			String Name = name.getText();
			String Pnum = phonenum.getText();
			String Cardnum = cardnum.getText();
			String Expirydate = expirydate.getText();
			String Bank = bank.getText();
			String Org = organization.getText();
			if(Name.trim().isEmpty()||Pnum.trim().isEmpty()||Cardnum.trim().isEmpty()||Expirydate.trim().isEmpty()||Bank.trim().isEmpty()||Org.trim().isEmpty())
				JOptionPane.showMessageDialog(null, "It is required to fill in every blank","Error",JOptionPane.ERROR_MESSAGE);
			else if(!isValidCard(Cardnum))
				JOptionPane.showMessageDialog(null, "The card number is not correct","Error",JOptionPane.ERROR_MESSAGE);
			//else if(!isValidDate(Expirydate))
				//JOptionPane.showMessageDialog(null, "The format of expiry date is not correct","Error",JOptionPane.ERROR_MESSAGE);
			else{
				sqlcode = "insert into users values ("+userid+", \'"+Name+"\', \'"+Pnum+"\')"; // Insert a new buyer to table user and buyer.
				adduser.WriteExecute(sqlcode);
				sqlcode = "insert into bankcard values ("+userid+",\'"+Cardnum+"\', \'"+Expirydate+"\', \'"+Bank+"\', \'"+Org+"\')";
				System.out.println(sqlcode);
				adduser.WriteExecute(sqlcode);
				System.out.println(sqlcode);
				//sqlcode = "insert into creditcard values (\'"+Cardnum+"\',"+userid+", \'"+Org+"\');";
				//adduser.WriteExecute(sqlcode);
				sqlcode = "insert into Buyer values("+userid+")";
				adduser.WriteExecute(sqlcode);
				Image img = new ImageIcon(this.getClass().getResource("/success.png")).getImage();
				JOptionPane.showMessageDialog(null, "You have successfully registed, your unique userid is "+ userid+", please keep it for login next time","Register Successfully",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(img));
				//Return back the current user id
				
					
				
				try {
					mainFrame.setUserid(userid);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.setVisible(true);
				mainFrame.setAddAddressButtonEnable(true);
				frame.dispose();
			}
		}
		
	}
	public boolean isValidCard(String cardnum){
		String cardPattern="\\d{4} \\d{4} \\d{4} \\d{4}";
		Pattern pattern=Pattern.compile(cardPattern);
		Matcher match=pattern.matcher(cardnum);
		if(match.matches())
			return true;
		else 
			return false;
	}
	public boolean isValidDate(String date){
		
		String datePattern = "\\d{2}-\\d{3}-\\d{1,2}"; 
		
		Pattern pattern= Pattern.compile(datePattern);
		Matcher match= pattern.matcher(date);
		if(match.matches()){
			if(date.length()==8){
				if(date.charAt(5) =='0'|| date.charAt(7)=='0')
					return false;
			}
			if(date.length()==10){
				if(date.charAt(5) =='0'&&date.charAt(6) =='0')
					return false;
				if(date.charAt(8) =='0'&&date.charAt(9) =='0')
					return false;
			}
			return true;
		}
		else
			return false;
	}
}