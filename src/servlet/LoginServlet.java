package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String un = request.getParameter("username");
		String pword = request.getParameter("password");
		
		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/exposure";
			String username = "root";
			String password= "12345";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url,username,password);
			System.out.println("Connection created");
			
			
			String query = "SELECT * FROM users";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			boolean found = false;
			while(rs.next()){
				String currUsername = rs.getString("username");
				String currPassword = rs.getString("password");
				
				if(un.equals(currUsername) && pword.equals(currPassword)){
					found = true;
					String currName = rs.getString("name");
					String currDesc = rs.getString("description");
					int currId = rs.getInt("id");
					request.getSession().setAttribute("username", currUsername);
					request.getSession().setAttribute("name", currName);
					request.getSession().setAttribute("description", currDesc);
					request.getSession().setAttribute("id", currId);
					
					if(request.getParameterValues("remember") != null){
						Cookie userCookie = new Cookie("Username", currUsername);
						userCookie.setMaxAge(60*60*24*21);
						response.addCookie(userCookie);
					}
					request.getRequestDispatcher("feed").forward(request, response);
					
					break;
				}
			}
			
			if(found == false)
			{
				response.sendRedirect("relog");
				//response.sendRedirect("login.html");
			}
			
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		catch (SQLException e){
			
			e.printStackTrace();
		}
	}

}
