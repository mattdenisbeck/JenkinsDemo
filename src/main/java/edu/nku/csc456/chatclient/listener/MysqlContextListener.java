/* 
	CSC 556 - Chatty Cathy Assignment
	Matthew Beck - 10/25/2015
 */

package edu.nku.csc456.chatclient.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import edu.nku.csc456.chatclient.dao.MessagesDAO;
import edu.nku.csc456.chatclient.dao.UsersDAO;

@WebListener
public class MysqlContextListener implements ServletContextListener {
	
	public static final String DATABASE_CONN_KEY = "dbconnection";
	public static final String USERS_DAO_KEY = "usersDAO";
	public static final String MESSAGES_DAO_KEY = "messagesDAO";
	
	Connection conn;
	
	public void contextInitialized (ServletContextEvent sce){
		try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				System.out.println("*****Error loading the driver*******");
				e.printStackTrace();
			}
			
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/chat?user=root&password=");
			sce.getServletContext().setAttribute(DATABASE_CONN_KEY, conn);
			sce.getServletContext().setAttribute(USERS_DAO_KEY, new UsersDAO(conn));
			sce.getServletContext().setAttribute(MESSAGES_DAO_KEY, new MessagesDAO(conn));
			
		} catch (SQLException e) {
			// handle any errors
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		
	}
	
	public void contextDestroyed (ServletContextEvent event){
		if(conn != null){
			try{
				conn.close();
				conn = null;
			}catch (SQLException e) {
				// handle any errors
				System.out.println("SQLException: " + e.getMessage());
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("VendorError: " + e.getErrorCode());
				e.printStackTrace();
			}
		}
	}
}
