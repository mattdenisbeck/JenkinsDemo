/* 
	CSC 556 - Chatty Cathy Assignment
	Matthew Beck - 10/25/2015
 */

package edu.nku.csc456.chatclient.websocket;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import edu.nku.csc456.chatclient.dao.MessagesDAO;
import edu.nku.csc456.chatclient.model.Message;
import edu.nku.csc456.chatclient.model.SessionHandler;

@ServerEndpoint(value = "/ChatServerEndpoint",
				configurator = ChatEndpointConfig.class)
public class ChatEndpoint {

	MessagesDAO messagesDao;
	ArrayList<Message> conversation;
	
	private Session wsSession;
	private HttpSession httpSession;
	
	@SuppressWarnings("unchecked")
	@OnOpen
	public void onOpen(Session userSession) throws IOException {
		System.out.println("onOpen called");
		this.wsSession = userSession;
		this.httpSession = (HttpSession) wsSession.getUserProperties().get("httpSession");
		
		messagesDao = (MessagesDAO) httpSession.getAttribute("messagesDao");
		
		//register HttpSession username with web socket session username
        String username = (String) httpSession.getAttribute("username");
		wsSession.getUserProperties().put("username", username);
		
		SessionHandler.getInstance().addSession(username, wsSession);
		
		//register HttpSession partner with web socket session partner
		String partner = (String) httpSession.getAttribute("partner");
		wsSession.getUserProperties().put("partner", partner);
		
		//register HttpSession conversation with web socket session conversation
		conversation = (ArrayList<Message>) httpSession.getAttribute("conversation");
		
		wsSession.getUserProperties().put("conversation", conversation);
		
		wsSession.getBasicRemote().sendText(buildJsonData("System", "you are connected as " + username));
		wsSession.getBasicRemote().sendText(buildJsonData("System", "you are chatting with " + partner));
		
		//send each message of conversation to users
		sendPreviousConversation(username);
		messagesDao.updateReadStatus(username);
	}
	
	private void sendPreviousConversation(String username) throws IOException {
		
		//send read messages
		conversation.stream().filter(msg -> (msg.getUser1().equals(username) && msg.getUser1_read() == true) 
									|| (msg.getUser2().equals(username) && msg.getUser2_read() == true) )
							.forEach(msg -> sendOldMessage(msg));
		//send unread messages
		if(conversation.stream().filter(msg -> (msg.getUser2().equals(username) && msg.getUser2_read() == false)).count() == 0){
			wsSession.getBasicRemote().sendText(buildJsonData("System", "No unread messages"));
		}
		else{
			wsSession.getBasicRemote().sendText(buildJsonData("System", "Unread messages below"));
			conversation.stream().filter(msg -> (msg.getUser2().equals(username) && msg.getUser2_read() == false))
								.forEach(msg -> sendOldMessage(msg));
		}
	}

	private void sendOldMessage(Message msg) {
		try {
			wsSession.getBasicRemote().sendText(buildJsonData(msg.getUser1(), msg.getText()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@OnMessage
	public String onMessage(String message) throws IOException {
	
		Session session;
		
		String user1 = (String) wsSession.getUserProperties().get("username");
		String user2 = (String) wsSession.getUserProperties().get("partner");
		
		//create message object
		Message msg = new Message(message, user1, user2, LocalDateTime.now(), false, false);
		
		//echo message back to sender
		session = SessionHandler.getInstance().getSession(user1);
		session.getBasicRemote().sendText(buildJsonData(user1, message));
		msg.setUser1_read(true);
		
		//echo message to receiver
		if(SessionHandler.getInstance().getSession(user2) != null) {
			session = SessionHandler.getInstance().getSession(user2);
			session.getBasicRemote().sendText(buildJsonData(user1, message));
			msg.setUser2_read(true);
		}
		
		//add message to conversation list & persist to database
		conversation = (ArrayList<Message>) wsSession.getUserProperties().get("conversation");
		conversation.add(msg);
		messagesDao.saveMessage(msg);
		
		return message;
	}

   private String buildJsonData(String username, String message) {
	   JsonObject jsonObject = Json.createObjectBuilder()
			   .add("message", message)
			   .add("username", username)
			   .build();
	   StringWriter stringwriter = new StringWriter();
	   try (JsonWriter jsonWriter = Json.createWriter(stringwriter)) {
		   jsonWriter.write(jsonObject);
	   }
	   return stringwriter.toString();
   }
   
   @OnClose
   public void onClose(Session userSession) {
       SessionHandler.getInstance().removeSession((String) wsSession.getUserProperties().get("username"));
   }
}