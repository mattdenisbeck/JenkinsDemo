/* 
	CSC 556 - Chatty Cathy Assignment
	Matthew Beck - 10/25/2015
 */

package edu.nku.csc456.chatclient.model;

import java.time.LocalDateTime;

public class Message {
	
	private String text, user1, user2;
	private LocalDateTime timeStamp;
	private Boolean user1_read, user2_read;
	
	public Message(String msg, String user1, String user2, LocalDateTime timeStamp, Boolean user1_read, Boolean user2_read){
		this.setText(msg);
		this.user1 = user1;
		this.user2 = user2;
		this.timeStamp = timeStamp;
		this.user1_read = user1_read;
		this.user2_read = user2_read;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUser1() {
		return user1;
	}
	public String getUser2() {
		return user2;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public Boolean getUser1_read() {
		return user1_read;
	}
	public Boolean getUser2_read() {
		return user2_read;
	}

	public void setUser1_read(boolean user1_read) {
		this.user1_read = user1_read;
	}

	public void setUser2_read(boolean user2_read) {
		this.user2_read = user2_read;
	}
}
