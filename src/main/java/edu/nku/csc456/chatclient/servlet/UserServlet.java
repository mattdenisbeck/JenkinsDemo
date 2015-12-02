/* 
	CSC 556 - Chatty Cathy Assignment
	Matthew Beck - 10/25/2015
 */

package edu.nku.csc456.chatclient.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.nku.csc456.chatclient.listener.MysqlContextListener;
import edu.nku.csc456.chatclient.model.User;
import edu.nku.csc456.chatclient.dao.UsersDAO;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String firstName, lastName, username;
	UsersDAO usersDao;
	
	@Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        usersDao = (UsersDAO) config.getServletContext().getAttribute(MysqlContextListener.USERS_DAO_KEY);
    }
	
	@Override 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
		
		firstName = (String) req.getParameter("firstname");
		lastName = (String) req.getParameter("lastname");
		username = (String) req.getParameter("username");
		
		HttpSession session = req.getSession();
		session.setAttribute("username", username);
		
		usersDao.insertNewUser(firstName, lastName, username);
		
		List<User> users = new ArrayList<User>();
		users = usersDao.getAllUsers();
		
		req.setAttribute("users", users);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/users.jsp");
		dispatcher.forward(req, resp);
	}
}
