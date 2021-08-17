package de.klyman.increment.model;

public class User {
	
	private String username;
	private String token;
	
	public User(String username, String token) { 
       this.username = username;
       this.token = token;
    }
	
	public String getUsername() {
		return this.username;
	}
	
	public String getToken() {
		return this.token;
	}

}
