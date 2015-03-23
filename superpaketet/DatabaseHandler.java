import java.sql.*;
import java.util.*;

public class DatabaseHandler{

	static final String DB_URL = "jdbc:sqlite:minDatabas.db";
	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	public static Connection conn = null;
	
	public void initDatabase(){

		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException cnfE) {
			System.err.println("Could not find JDBC driver!!!111");
			//cnfE.printStackTrace();
			System.exit(1);
		}
		System.out.println("Driver found");

		try {
			System.out.println("Connecting to database...");
			//String path = this.getClass().getResource("MinDatabas.db").getPath();
			conn = DriverManager.getConnection("jdbc:sqlite:minDatabas.db"); 
		} catch (SQLException sqlE) {
			System.out.println("Couldn't connect:");
			sqlE.printStackTrace();
			System.out.println("Exiting...");
			System.exit(1);
		}
		if (conn != null) {
			System.out.println("Connection to database established");
		} else {
			System.out.println("Connection failed");
		}
	}

	public static ArrayList<String> search(String st, String getInput, boolean returnInput){
		Statement s = null;
		ResultSet rs = null; 
		int ri; 
		ArrayList<String> arr = new ArrayList<String>(); 
		try {
			s = DatabaseHandler.conn.createStatement();
		} catch (SQLException se) {
			System.out.println("Connection");
			System.out.println(se.getMessage());
			System.exit(1);
		} 
		try {
			if (returnInput == true){
				rs = s.executeQuery(st);
				while(rs.next()) {
					
					
					ResultSetMetaData rsmd = rs.getMetaData();
					 
					String memberID = rsmd.getColumnName(1);
					String firstName = rsmd.getColumnName(2);
					String op3 = rsmd.getColumnName(3);
					String op4 = rsmd.getColumnName(4);
					String op5 = rsmd.getColumnName(5);
					String op6 = rsmd.getColumnName(6);
					String op7 = rsmd.getColumnName(7);
					String op8 = rsmd.getColumnName(8);
					String memberIDStr = rs.getString(memberID);
					String firstNameStr = rs.getString(firstName);
					String finalOp3 = rs.getString(op3);
					String finalOp4 = rs.getString(op4);
					String finalOp5 = rs.getString(op5);
					String finalOp6 = rs.getString(op6);
					String finalOp7 = rs.getString(op7);
					String finalOp8 = rs.getString(op8);
					 
						arr.add("Name: "+firstNameStr);
						arr.add("ID: "+memberIDStr);
						arr.add("Family name: "+finalOp3);
						arr.add("Email: "+finalOp4);
						arr.add("Gender: " + finalOp5);
						arr.add("Birthday: " + finalOp6);
						arr.add("Member since: " + finalOp7);
						arr.add("Is active: " + finalOp8.equals("1"));
						arr.add("_______________");
				} 
			} else {
				ri = s.executeUpdate(st); 
			}
		} catch (SQLException se) {
			System.out.println("ResultSet");
			System.out.println(se.getMessage());
			//System.exit(1);
		}
		return arr; 
	}

	public static ArrayList<String> listNames () {
		ArrayList<String> er   = search("SELECT * FROM medlem","givenName",true);
		//String ere = Arrays.asList(er).toString();
		//String [] result = er.toArray();
		//System.out.println(Arrays.asList(er));
		//showList.setText("<html>"+ere+"</html>");
		return er;
	}

	public static ArrayList<String> getMember (String name) {
		
		ArrayList<String> er   = search("SELECT * FROM medlem WHERE familyName = '" + name + "'" ,"givenName",true);
		//String ere = Arrays.asList(er).toString();
		//String [] result = er.toArray();
		//System.out.println(Arrays.asList(er));
		//showList.setText("<html>"+ere+"</html>");
		return er;
	}
}


