package superpaketet;

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
			cnfE.printStackTrace();
			System.exit(1);
		}
		System.out.println("Driver found");

		try {
			System.out.println("Connecting to database...");
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

	public static ArrayList<String> search(String st){
		Statement s = null;
		ResultSet rs = null; 
		ArrayList<String> arr = new ArrayList<String>(); 
		try {
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery(st);
			while(rs.next()) {
				 
				arr.add("Name: "+rs.getString("givenName"));
				arr.add("ID: "+rs.getString("id"));
				arr.add("Family name: "+rs.getString("familyName"));
				arr.add("Email: "+rs.getString("email"));
				arr.add("Gender: " + rs.getString("gender"));
				arr.add("Birthday: " + rs.getString("birth"));
				arr.add("Member since: " + rs.getString("memberSince"));
				arr.add("Is active: " + rs.getString("active").equals("1"));
				arr.add("_______________");
			} 
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		return arr; 
	}

	public static ArrayList<String> searchTeamInfo(String team){
		Statement s = null;
		ResultSet rs = null; 
		ArrayList<String> arr = new ArrayList<String>(); 
		try {
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery("SELECT * FROM medlem NATURAL JOIN funktion WHERE team = '" + team + "' ORDER BY role DESC, givenName");
			while(rs.next()) {
				int role = rs.getInt("role");
				String roleString = null;
				if (role == 1){
					roleString = "Coach";
				}
				else if (role == 0){
					roleString = "Player";
				}
				else{
					roleString = "Parent";
				}

				arr.add("Name: "+rs.getString("givenName"));
				arr.add("Family name: "+rs.getString("familyName"));
				arr.add("ID: "+rs.getString("id"));
				arr.add("Role: " + roleString);
				arr.add("_______________");
			} 
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		return arr; 
	}

	public void addMember(String id, String givenName, String familyName, 
						  String email, String gender, String birthdate, 
						  String memberSince, String active, String role, 
						  String team){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate("INSERT INTO medlem (ID, givenName, familyName, email, "
							+ "gender, birth, memberSince, active) VALUES ("
							+ id + ", '" + givenName + "', '" + familyName + "', '" + email
							+ "', '" + gender + "', '" + birthdate + "', '" + memberSince 
							+ "', " + active + ")");
			s.executeUpdate("INSERT INTO funktion (id, role, team) VALUES (" + id + ", " 
							+ role + ", '" + team + "')");
		}
		catch (SQLException se) {
			System.out.println(se.getMessage());
		}
	}

	public void deleteMember(String id){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate("DELETE FROM medlem WHERE id = '" + id + "'");
			s.executeUpdate("DELETE FROM funktion WHERE id = '" + id + "'");
		}
		catch (SQLException se) {
			System.out.println(se.getMessage());
		}
	}

	public static ArrayList<String> listNamesByFamilyName () {
		ArrayList<String> er   = search("SELECT * FROM medlem ORDER BY familyName");
		return er;
	}

	public static ArrayList<String> listNamesByID () {
		ArrayList<String> er   = search("SELECT * FROM medlem ORDER BY id");
		return er;
	}	

	public static ArrayList<String> getMember (String name) {
		
		ArrayList<String> er   = search("SELECT * FROM medlem WHERE familyName = '" + name + "'");
		return er;
	}

	public static ArrayList<String> getTeamcoaches (String team) {
		
		ArrayList<String> er   = search("SELECT * FROM medlem NATURAL JOIN funktion WHERE role = 1 AND team = '" + team + "'");
		return er;
	}

	public boolean checkMemberExistance(String ID){
		Statement s = null;
		ResultSet rs = null; 
		try{
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery("SELECT * FROM medlem WHERE id = " + ID);
			return rs.next();
		}
		catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		return true;
	}
}


