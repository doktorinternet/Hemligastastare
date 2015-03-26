package superpaketet;

import java.sql.*;
import java.util.*;
import java.lang.*;

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
					  String memberSince, String active, boolean isCoach, 
					  boolean isPlayer, boolean isParent, String team){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate("INSERT INTO medlem (ID, givenName, familyName, email, "
							+ "gender, birth, memberSince, active) VALUES ("
							+ id + ", '" + givenName + "', '" + familyName + "', '" + email
							+ "', '" + gender + "', '" + birthdate + "', '" + memberSince 
							+ "', " + active + ")");
			
			if(isPlayer){
				s.executeUpdate("INSERT INTO funktion (id, role, team) VALUES (" 
								+ id + ", 0, '" + team + "')");
			}
			if(isCoach){
				s.executeUpdate("INSERT INTO funktion (id, role, team) VALUES (" 
								+ id + ", 1, '" + team + "')");
			}
			if(isParent){
				s.executeUpdate("INSERT INTO funktion (id, role, team) VALUES (" 
								+ id + ", 2, '" + team + "')");
			}
		
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

	public void updateMemberMedlem(String id, String field, String value){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate("UPDATE medlem SET " + value + " = '" + field + "' WHERE id = " + id);
		}
		catch (SQLException se) {
			System.out.println(se.getMessage());
		}
	}

	public void updateMemberFunktion(String id, String field, String value){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate("UPDATE funktion SET " + field + " = " + value + " WHERE id = " + id);
		}
		catch (SQLException se) {
			System.out.println(se.getMessage());
		}
	}

	public void updateMemberRole(String id, boolean isCoach, boolean isParent, boolean isPlayer){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			if(isCoach){
				s.executeUpdate("UPDATE funktion SET role = '1' WHERE ID = '" + id + "'");
			}else{
				s.executeUpdate("DELETE FROM funktion WHERE role = '1' AND id = '" + id +"'");
			}
			if(isParent){
				s.executeUpdate("UPDATE funktion SET role = '2' WHERE ID = '" + id + "'");
			}else{
				s.executeUpdate("DELETE FROM funktion WHERE role = '2' AND id = '" + id + "'");
			}	
			if(isPlayer){
				s.executeUpdate("UPDATE funktion SET role = '0' WHERE ID = '" + id + "'");
			}else{
				s.executeUpdate("DELETE FROM funktion WHERE role = '0' AND id = '" + id + "'");
			}
		}
		catch(SQLException se){
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
			rs = s.executeQuery("SELECT * FROM medlem WHERE id = '" + ID + "'");
			return rs.next();
		}
		catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		return true;
	}
	
	public ArrayList<String> checkMemberRoles(String id){
		Statement s = null;
		ResultSet rs = null;
		//String role = null;
		//String[] roles = new String[2];
		ArrayList<String> roles = new ArrayList();
		try{
			s = DatabaseHandler.conn.createStatement();
/* 			rs = s.executeQuery("SELECT role FROM funktion NATURAL JOIN "
								+ "medlem WHERE id = '" + id + "'"); */
			roles = search("SELECT role FROM funktion NATURAL JOIN "
											+ "medlem WHERE id = '" + id + "'");
			System.out.println("Instans array skapas");
			//Array a = rs.getArray(1);
			System.out.println("Array skapad ");
			//roles = (String[])a.getArray();
			System.out.println("Array skapad ");
			/* while(rs.next()){
				int i = 1;
				int a = 0;
				if(rs.getString(i).equals("0")){
					role = "Player";
					roles[a] = role;					
				}
				else if(rs.getString(i).equals("1")){
					role = "Coach";
					roles[a] = role;
				}
				else if(rs.getString(i).equals("2")){
					role = "Parent";
					roles[a] = role;				
				}
				else if(rs.getString(i) == null){
					role = "zilch";
					roles[a] = role;
				}
				i++;
				a++;
			} */
		
		for (String r : roles){
			System.out.println(r);
		}
		
		}
		catch (SQLException se){
			System.out.println("Error message in catch box");
			System.out.println(se.getMessage());
		}
		System.out.println("return roles");
		return roles;
	}

	public String checkMemberActive(String id){
		Statement s = null;
		ResultSet rs = null;
		String active = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery("SELECT active FROM medlem WHERE id = '" + id + "'");
			if(rs.getString(1).equals("1")){
				active = "is active";
			}
			else{
				active = "is inactive";
			}
		}
		catch (SQLException se){
			System.out.println(se.getMessage());
		}
		return active;
	}

	public void setMemberActive(String id, String active){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate("UPDATE medlem SET active = " + active + " WHERE id = " + id);
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
	}
}


