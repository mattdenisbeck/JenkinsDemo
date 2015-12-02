package edu.nku.csc456.chatclient.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.nku.csc456.chatclient.model.Message;

public class MessagesDAO {
	
	private Connection conn;
	private static final String SELECT_CONVERSATION_SQL = "select * from messages where (user1= ? and user2= ? ) or "
			+ "(user1= ? and user2= ? ) order by timeStamp ASC;";
	private static final String INSERT_MSG_SQL = "insert into messages values (?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_READ_SQL = "update messages set user2_read=1 where user2=?;";
	
	public MessagesDAO(Connection conn ){
		this.conn = conn;
	}
	
	//user1 is understood to be the sender, user2 is understood to be the receiver
	public void saveMessage(Message msg) {
		
		String text = msg.getText();
		String user1 = msg.getUser1(); 
		String user2 = msg.getUser2();
		Timestamp timeStamp = Timestamp.valueOf(msg.getTimeStamp()); 
		Boolean user1_read = msg.getUser1_read(); 
		Boolean user2_read = msg.getUser2_read();
		
		//insert message into database 
		try (PreparedStatement statement = conn.prepareStatement(INSERT_MSG_SQL)) {
			statement.setString(1, text);
			statement.setString(2, user1);
			statement.setString(3, user2);
			statement.setTimestamp(4, timeStamp);
			statement.setBoolean(5, user1_read);
			statement.setBoolean(6, user2_read);
			
			statement.executeUpdate();  
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public ArrayList<Message> loadConversation(String user1, String user2) {
		
		ArrayList<Message> conversation = new ArrayList<Message>();
		
		try (PreparedStatement statement = conn.prepareStatement(SELECT_CONVERSATION_SQL)) {
            statement.setString(1, user1);
            statement.setString(2, user2);
            statement.setString(3, user2);
            statement.setString(4, user1);
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
				String text = rs.getString(1);
				String usr1 = rs.getString(2);
				String usr2 = rs.getString(3);
				Timestamp ts = (Timestamp) rs.getObject(4);
				LocalDateTime timeStamp = ts.toLocalDateTime();
				Boolean user1_read = rs.getBoolean(5);
				Boolean user2_read = rs.getBoolean(6);
				conversation.add(new Message(text, usr1, usr2, timeStamp, user1_read, user2_read));
			}
            statement.close(); 

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return conversation;
		
	}

	public void persistConversation(ArrayList<Message> conversation) {
		conversation.stream().forEach(msg -> saveMessage(msg));		
	}

	public void updateReadStatus(String username) {
		try (PreparedStatement statement = conn.prepareStatement(UPDATE_READ_SQL)) {
			statement.setString(1, username);
			
			statement.executeUpdate();  
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
