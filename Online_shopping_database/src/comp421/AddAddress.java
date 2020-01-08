package comp421;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddAddress extends JFrame {
	JTextField name= new JTextField("Name");
	JTextField phonenum=new JTextField("Phone Number");
	JTextField streetaddr = new JTextField("Street Address");
	JTextField city = new JTextField("City");
	JTextField state = new JTextField("State");
	JTextField postalcode = new JTextField("Postal Code");
	JButton add = new JButton("ADD");
	JButton back = new JButton("BACK");
	MainFrame mainFrame = null;
	AddAddress frame = this;
	
	ResultSet rs;
	String sqlcode;
	SQL newaddr;
	int userid;
	int addrid;
	
	public AddAddress(int uid,SQL sqlo,MainFrame mainFrame){
		getContentPane().setBackground(new Color(254, 187,56));
		this.mainFrame = mainFrame;
		userid = uid;
		System.out.println("userid = "+ userid);
		newaddr=sqlo;
		getContentPane().setLayout(null);
		JPanel jpanel = new JPanel();
		jpanel.setBorder(null);
		jpanel.setBackground(new Color(254, 187,56));
		jpanel.setBounds(10, 0, 579, 323);
		jpanel.setLayout(null);
		JLabel label = new JLabel("Name:");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setForeground(Color.WHITE);
		
		label.setBounds(21, 10, 217, 36);
		jpanel.add(label); name.setBounds(332, 10, 217, 36);
 jpanel.add(name);
		JLabel label_1 = new JLabel("Phone Number:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(21, 57, 217, 36);
		jpanel.add(label_1); phonenum.setBounds(332, 57, 217, 36);
 jpanel.add(phonenum);
		JLabel label_2 = new JLabel("Street Address: ");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(21, 104, 217, 36);
		jpanel.add(label_2); streetaddr.setBounds(332, 104, 217, 67);
 jpanel.add(streetaddr);
		JLabel label_3 = new JLabel("City: ");
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_3.setBounds(21, 182, 217, 36);
		jpanel.add(label_3); city.setBounds(332, 182, 217, 36);
 jpanel.add(city);
		JLabel label_4 = new JLabel("State:");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_4.setForeground(Color.WHITE);
		label_4.setBounds(21, 229, 217, 36);
		jpanel.add(label_4); state.setBounds(332, 229, 217, 36);
 jpanel.add(state);
		JLabel label_5 = new JLabel("Postal Code:");
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_5.setBounds(21, 276, 217, 36);
		jpanel.add(label_5); postalcode.setBounds(332, 276, 217, 36);
 jpanel.add(postalcode);
		getContentPane().add(jpanel);
		add.setBackground(new Color(0, 0, 51));
		add.setForeground(Color.WHITE);
		add.setBounds(342, 334, 185, 40);
		add.addActionListener(new addrlistener());
		add.setPreferredSize(new Dimension(20,40));
		back.setForeground(Color.WHITE);
		back.setBackground(new Color(0, 0, 51));
		back.setBounds(56, 334, 170, 40);
		back.addActionListener(new BckListener());
		getContentPane().add(add);
		getContentPane().add(back);
	}
	public static void invoke (int uid, SQL sqlo,MainFrame  mainFrame){
		JFrame address = new AddAddress(uid,sqlo,mainFrame);
		address.setTitle("Add a new address");
		address.setVisible(true);
		address.setLocationRelativeTo(null);
		address.setSize(600,450);
		address.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	class BckListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			mainFrame.setVisible(true);
			mainFrame.setAddAddressButtonEnable(true);
			frame.dispose();
		}
	}
	
	class addrlistener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			sqlcode="select max(addrid) from address";
			rs=newaddr.QueryExchte(sqlcode);
			try {	
				rs.next();
				addrid= rs.getInt(1)+1;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			String Name= name.getText();
			String Pnum= phonenum.getText();
			String Streetaddr= streetaddr.getText();
			String City = city.getText();
			String Statet = state.getText();
			String Postalcode = postalcode.getText();
			
			if(Name.trim().isEmpty()||Pnum.trim().isEmpty()||Streetaddr.trim().isEmpty()||City.trim().isEmpty()||Statet.trim().isEmpty()||Postalcode.trim().isEmpty())
				JOptionPane.showMessageDialog(null, "It is required to fill in every blank","Error",JOptionPane.ERROR_MESSAGE);
			else
			{
				sqlcode="insert into address values ("+addrid+","+userid+", \'"+Name+"\', \'"+Pnum+"\', \'"+Statet+"\', \'"+City+"\', \'"+Streetaddr+"\', \'"+Postalcode+"\')"; // Insert a new address of this user to the table address
				newaddr.WriteExecute(sqlcode);
				Image img = new ImageIcon(this.getClass().getResource("/success.png")).getImage();
				JOptionPane.showMessageDialog(null, "You have successfully added a new address", "Success", JOptionPane.INFORMATION_MESSAGE,new ImageIcon(img));
				
				
				mainFrame.setVisible(true);
				mainFrame.setSearchAndBuyButtonEnable(true);
				frame.dispose();
			}   
		}
	}
}