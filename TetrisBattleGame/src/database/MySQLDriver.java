package database;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

import com.mysql.jdbc.Driver;

public class MySQLDriver {
	private Connection con;
	private final static String selectUserName = "SELECT * FROM USERS WHERE USERNAME=?";
	private final static String addUser = "INSERT INTO USERS(USERNAME, PASSWORD) VALUES (?,?)";
	private final static String checkPassword = "SELECT USERNAME, PASSWORD FROM USERS";
	
	public MySQLDriver() {
		 try {
			 new Driver();
		 } catch (SQLException e) {
			 System.out.println("sql exception in constructor: " + e.getMessage());
		 }
	}
	
	public void connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tetris?user=root&password=");
		} catch (SQLException e) {
			 System.out.println("sql exception in connect: " + e.getMessage());
		 }
	}
	
	public void stop() {
		try {
			con.close();
		} catch (SQLException e) {
			 System.out.println("sql exception in stop: " + e.getMessage());
			 e.printStackTrace();
		 }
	}
	
	public boolean doesExist(String userName) { //checks if a username exists
		try {
			PreparedStatement ps = con.prepareStatement(selectUserName);
			ps.setString(1, userName);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				System.out.println(result.getString(1) + " already exists in the database.");
				return true;
			} 
		} catch (SQLException e) {
				System.out.println("sql exception in doesExist: " + e.getMessage());
				e.printStackTrace();
			}
		System.out.println("unable to find a user with name: " + userName);
		return false;
	}
	
	public void add(String userName, char[] password) { //adds a user to the database
		try {
			PreparedStatement ps = con.prepareStatement(addUser);
			String spassword = new String(password);
			ps.setString(1, userName);
			ps.setString(2, spassword);
			ps.executeUpdate();
			System.out.println("Adding username: " + userName + "to the database.");
		} catch (SQLException e) {
			System.out.println("sql exception in add: " + e.getMessage());	
			e.printStackTrace();
		}
	}
	
	public boolean passwordMatches(String userName, char[] password) { //checks if the given username and password match
		try {
			PreparedStatement ps = con.prepareStatement(checkPassword);
			ResultSet result = ps.executeQuery();
			String spassword = new String(password);
			while(result.next()) {
				if(result.getString("USERNAME").equals(userName) && result.getString("PASSWORD").equals(spassword))
					return true;
			} 
		} catch (SQLException e) {
				System.out.println("sql exception in doesExist: " + e.getMessage());
				e.printStackTrace();
			}
		System.out.println("unable to find a username and password that match");
		return false;
	}
}
