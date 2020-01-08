package comp421;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	
	Statement statement;
	
	public SQL() throws Exception
	{
		try
		{
			initialise();
		}
		catch(Exception e)
		{
			System.out.println("Class not found!");
			e.printStackTrace();
		}
	}
	
	public void initialise() {
		try
		{
			 // step1 load the driver class			
	        Class.forName("oracle.jdbc.driver.OracleDriver");			
	        // step2 create the connection object			
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/sagar", "System", "Sagarpai99");			
	        // step3 create the statement object			
	        statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	        	    ResultSet.CONCUR_READ_ONLY);			
	        // step4 execute query
	        System.out.println("connected");
	        
		}
		catch (SQLException ex) {
			
	        System.out.println(ex);

	    }catch (ClassNotFoundException ex) {

	        System.out.println(ex);			
	    }
	}
	
	public void WriteExecute(String sqlCode)
	{
		try
		{
			System.out.println(sqlCode);
			statement.executeUpdate(sqlCode);
			System.out.println("Write execute the code"+ sqlCode);
		}
		catch(SQLException e)
		{
			int errorCode = e.getErrorCode();
			String sqlState = e.getSQLState();
			System.out.println("Code5:"+errorCode+"  sqlState5:"+sqlState);
		}
	}
	
	public java.sql.ResultSet QueryExchte(String sqlCode)
	{ 
		java.sql.ResultSet rs = null;
		try {
			System.out.println(sqlCode);
		    rs = statement.executeQuery (sqlCode);
		    System.out.println(rs);
		    //System.out.println(rs.getString(0));
		   /* while (rs.next())
	        {		
	        	System.out.println(rs.getString(2));
	        }*/
		    System.out.println("Query excute the code "+sqlCode);
		} catch (SQLException e)
		    {
			int errorCode = e.getErrorCode(); // Get SQLCODE
			String sqlState = e.getSQLState(); // Get SQLSTATE     
			System.out.println("Code1: " + errorCode + "  sqlState: " + sqlState);
		    }
		return rs;
	}
	
}

