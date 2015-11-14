package game;

public class User {

	private String username;
	private long password;
	
	public User(String username, long password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public long getPassword() {
		return password;
	}
	
}
