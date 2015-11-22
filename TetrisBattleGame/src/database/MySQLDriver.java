package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.mysql.jdbc.Driver;

public class MySQLDriver {
	private Connection con;
	private final static String selectUserName = "SELECT * FROM USERS WHERE USERNAME=?";
	private final static String addUser = "INSERT INTO USERS(USERNAME, PASSWORD) VALUES (?,?)";
	private final static String checkPassword = "SELECT USERNAME, PASSWORD FROM USERS";
	private final static String readScores = "SELECT USER FROM SCORES";
	private final static String readScores2 = "SELECT SCORE FROM SCORES";
	private final static String addScore = "INSERT INTO SCORES(USER, SCORE) VALUES (?,?)";
	private final static String updateScore = "UPDATE SCORES SET SCORE = ? WHERE USER = ?";
	private final static String selectUserScores = "SELECT * FROM SCORES WHERE USER = ?";
	
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
	
	public ArrayList<String> readScores() {
		ArrayList<String> users = new ArrayList<String>();
		try {
			PreparedStatement ps = con.prepareStatement(readScores);
			ResultSet result = ps.executeQuery();
			System.out.println(result.getFetchSize());
			while(result.next()) {
				users.add(result.getString(1));
			} 
		} catch (SQLException e) {
				System.out.println("sql exception in readScores: " + e.getMessage());
				e.printStackTrace();
		}
		return users;
	}
	
	public ArrayList<Integer> readScores2() {
		ArrayList<Integer> scores = new ArrayList<Integer>();
		try {
			PreparedStatement ps = con.prepareStatement(readScores2);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				scores.add(result.getInt(1));
			} 
		} catch (SQLException e) {
				System.out.println("sql exception in readScores: " + e.getMessage());
				e.printStackTrace();
		}
		return scores;
	}
	
	public void addScore(String userName, int score) {
		try {
			PreparedStatement ps = con.prepareStatement(selectUserScores);
			ps.setString(1, userName);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				if(result.getInt(2) < score) {
					System.out.println(result.getString(1) + " already exists in the database with a lower score. Must update, instead of add score.");
					PreparedStatement ps2 = con.prepareStatement(updateScore);
					ps2.setInt(1, score);
					ps2.setString(2, userName);
					ps2.executeUpdate();
					System.out.println("updated score for " + userName);
					return;
				}
				else
					return;
			} 
			PreparedStatement ps3 = con.prepareStatement(addScore);
			ps3.setString(1, userName);
			ps3.setInt(2, score);
			ps3.executeUpdate();
			System.out.println("Adding username: " + userName + " with score: " + score + " to the database.");
		} catch (SQLException e) {
			System.out.println("sql exception in add score: " + e.getMessage());	
			e.printStackTrace();
		}
	}
	
	public void add(String userName, char[] password) { //adds a user to the database
		try {
			PreparedStatement ps = con.prepareStatement(addUser);
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			String spassword = passwordEncryptor.encryptPassword(new String(password));
			ps.setString(1, userName);
			ps.setString(2, spassword);
			ps.executeUpdate();
			System.out.println("Adding username: " + userName + "to the database with password " + spassword);
		} catch (SQLException e) {
			System.out.println("sql exception in add: " + e.getMessage());	
			e.printStackTrace();
		}
	}
	
	public boolean passwordMatches(String userName, char[] password) { //checks if the given username and password match
		try {
			PreparedStatement ps = con.prepareStatement(checkPassword);
			ResultSet result = ps.executeQuery();
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			String spassword = (new String(password));
			while(result.next()) {
				if(result.getString("USERNAME").equals(userName) && passwordEncryptor.checkPassword(spassword,result.getString("PASSWORD")))
					return true;
			}
			System.out.println("password i am checking " + spassword);
		} catch (SQLException e) {
				System.out.println("sql exception in doesExist: " + e.getMessage());
				e.printStackTrace();
			}
		System.out.println("unable to find a username and password that match.");
		return false;
	}
}
