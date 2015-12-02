/* 
	CSC 556 - Chatty Cathy Assignment
	Matthew Beck - 10/25/2015
 */

package edu.nku.csc456.chatclient.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nku.csc456.chatclient.dao.MessagesDAO;
import edu.nku.csc456.chatclient.listener.MysqlContextListener;
import edu.nku.csc456.chatclient.model.Message;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	MessagesDAO messagesDao;
	
	@Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        messagesDao = (MessagesDAO) config.getServletContext().getAttribute(MysqlContextListener.MESSAGES_DAO_KEY);
    }
	
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String user1 = (String) req.getSession().getAttribute("username");
		String user2 = (String) req.getParameter("partner");
		ArrayList<Message> conversation = messagesDao.loadConversation(user1, user2);
		
		//register partner with HttpSession attribute
		req.getSession().setAttribute("partner", user2);
		req.getSession().setAttribute("conversation", conversation);
		req.getSession().setAttribute("messagesDao", messagesDao);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/chat.jsp");
		dispatcher.forward(req, resp);
	}
}
