package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
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
		
		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/exposure";
			String username = "root";
			String password= "12345";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url,username,password);
			System.out.println("Connection created");
			
			
			String query = "SELECT COUNT(*) num FROM users";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			int newID = 0;
			while(rs.next())
				newID = rs.getInt("num");
			
			String newName = request.getParameter("fullname");
			String newUsername = request.getParameter("username");
			String newPassword = request.getParameter("password");
			String newDescription = request.getParameter("description");
			
			if(newName.equals("") || newUsername.equals("") || newPassword.equals(""))
			{
				response.sendRedirect("signup.html");
			}
			
			else{
				//PreparedStatement preparedStmt = con.prepareStatement(query2);
				//String query2 = "INSERT into users(id, name, username, password, description)"
						//+ " values(1,2,3,4,5)";
				Statement st2 = con.createStatement();
				st2.executeUpdate("INSERT into users(id, name, username, password, description) " +
				"values(" + newID + ",\'" + newName + "\',\'" + newUsername + "\',\'" + newPassword + "\',\'" + newDescription + "\')");
				
				request.getSession().setAttribute("username", newUsername);
				request.getSession().setAttribute("name", newName);
				request.getSession().setAttribute("description", newDescription);
				request.getSession().setAttribute("id", newID);
				
				Cookie userCookie = new Cookie("Username", newUsername);
				userCookie.setMaxAge(60*60*24*21);
				response.addCookie(userCookie);
				request.getRequestDispatcher("feed").forward(request, response);
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
