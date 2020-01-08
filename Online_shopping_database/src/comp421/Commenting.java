package comp421;

//sql code to be implemented in button  --done 
//test if the address is empty 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Commenting extends JFrame{
	JTextField cmnt = new JTextField ("Comments");
	JTextField grade =new JTextField("Grade");
	//JTextField phonenumber = new JTextField("Phone number");
	JButton submit = new JButton("SUBMIT");
	//JButton back = new JButton("BACK");
	
	SQL cmtsql;
	ResultSet rs;
	String sqlcode;
	int uid;
	int pid;
	//MainFrame mainFrame = null;
	Commenting frame = this;
	public Commenting(int pid,int userid,SQL sqlo){
		getContentPane().setBackground(new Color(255, 153, 0));
		cmtsql=sqlo;
		//this.mainFrame = mainFrame;
		this.pid=pid;
		this.uid=userid;
		getContentPane().setLayout(null);
		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 0, 0);
		getContentPane().add(panel1);
		JPanel buttonpanel=new JPanel();
		buttonpanel.setBounds(0, 0, 0, 0);
		getContentPane().add(buttonpanel);
		submit.setBackground(new Color(0, 0, 51));
		submit.setFont(new Font("Tahoma", Font.BOLD, 11));
		submit.setForeground(Color.WHITE);
		submit.setBounds(150, 208, 80, 29);
		getContentPane().add(submit);
		JLabel label = new JLabel("Comments");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBounds(35, 88, 80, 16);
		getContentPane().add(label);
		grade.setBounds(166, 161, 185, 29);
		getContentPane().add(grade);
		cmnt.setBounds(166, 76, 185, 77);
		getContentPane().add(cmnt);
		submit.addActionListener(new loginListener());
	}
	public static void invoke(int pid,int userid,SQL sqlo){
		JFrame ct = new Commenting(pid,userid,sqlo);
		ct.setTitle("Commenting");
		ct.setLocationRelativeTo(null);
		ct.setSize(450,450);
		ct.setVisible(true);
		ct.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	class loginListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==submit){
				String k = cmnt.getText();
				try{int p = Integer.parseInt(grade.getText());
				//pnum = phonenumber.getText();
				if(k.length()!=0)
				{
					String sq="insert into Comments values(";
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
					sq +="\'"+ sdf.format(d)+"\'"+ ", ";
					sq +=uid+", ";
					sq +=pid+", ";
					sq +=p+", ";
					sq+="\'"+k+"\')";
					cmtsql.WriteExecute(sq);
				}}
				catch(Exception f)
				{frame.dispose();}
				//mainFrame.setVisible(true);
				frame.dispose();
			}
		}
	}	
}