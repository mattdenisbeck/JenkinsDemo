/* 
	CSC 556 - Chatty Cathy Assignment
	Matthew Beck - 10/25/2015
 */

package edu.nku.csc456.chatclient.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class ChatEndpointConfig extends ServerEndpointConfig.Configurator {

    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
    	super.modifyHandshake(config, request, response);
    	HttpSession httpSession = (HttpSession) request.getHttpSession();

    	config.getUserProperties().put("httpSession", httpSession);
    }
	
}
