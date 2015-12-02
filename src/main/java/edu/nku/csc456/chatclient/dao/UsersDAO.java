/* 
	CSC 556 - Chatty Cathy Assignment
	Matthew Beck - 10/25/2015
 */

package edu.nku.csc456.chatclient.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import edu.nku.csc456.chatclient.model.User;

public class UsersDAO {
	
	private Connection conn;
    
    private static final String SELECT_USER_SQL = "select username from users where username = ";
	
	public UsersDAO(Connection conn ){
		this.conn = conn;
	}
	
	public void insertNewUser(String firstName, String lastName, String username) {
		//insert user into database and/or login
		Statement stmt;
		try {
			stmt = conn.createStatement();
			//don't allow inserts with duplicate keys (username)
			String query = "insert into users (firstname,lastname,username)"
					+ " values ('" + firstName + "' , '" + lastName + "' , '" + username + "')"
					+ " on duplicate key update username = username;";  
			stmt.executeUpdate(query);  
			stmt.close(); 
			stmt = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Optional<User> findUser(String username) {
        try(Statement statement = conn.createStatement()) {
            //this is an example of SQL injection vulnerability
            statement.execute(SELECT_USER_SQL + "'" + username + "'");
            ResultSet rs = statement.getResultSet();
            while( rs.next() ) {
            	return Optional.ofNullable(new User(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
	
	public ArrayList<User> getAllUsers() {
		
		ArrayList<User> users = new ArrayList<User>();
		Statement stmt;
		try {
			
			//query database for all users
			stmt = conn.createStatement();
			String query = "select * from users;"; 
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				String fn = rs.getString(1);
				String ln = rs.getString(2);
				String un = rs.getString(3);
				users.add(new User(fn,ln,un));
			}
			
			stmt.close(); 
			stmt = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		
		return users;
		
	}
	
	

}
