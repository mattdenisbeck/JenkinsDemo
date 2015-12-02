/* 
	CSC 556 - Chatty Cathy Assignment
	Matthew Beck - 10/25/2015
 */

package edu.nku.csc456.chatclient.model;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

public class SessionHandler {
	
	private static SessionHandler instance;
	Map<String, Session> chatSessions;
	
	static {
		new SessionHandler();
	}
	
	private SessionHandler() {
		chatSessions = new HashMap<>();
		instance = this;
	}
	
	public static SessionHandler getInstance() {
		return instance;
	}
	
	public void addSession(String user, Session session) {
		System.out.println("Registering user: " + user + " for session " + session.getId());
		chatSessions.put(user, session);
	}
	
	public Session getSession(String user) {
		return chatSessions.get(user);
	}
	
	public void removeSession(String user) {
		chatSessions.remove(user);
	}
	
}
