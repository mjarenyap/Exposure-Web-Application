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

import beans.Photo;

import java.util.ArrayList;

/**
 * Servlet implementation class RelogServlet
 */
@WebServlet("/relog")
public class RelogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		boolean found = false;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			
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
				
				for(int i = 0; i < cookies.length; i++){
					Cookie currCookie = cookies[i];
					if(currCookie.getName().equals("Username")){
						currCookie.setMaxAge(60*60*24*21);
						
						while(rs.next()){
							if(rs.getString("username").equals(currCookie.getValue())){
								request.getSession().setAttribute("name", rs.getString("name"));
								request.getSession().setAttribute("description", rs.getString("description"));
								request.getSession().setAttribute("id", rs.getInt("id"));
								break;
							}
						}
						
						request.getSession().setAttribute("username", currCookie.getValue());
						response.addCookie(currCookie);
						request.getRequestDispatcher("feed").forward(request, response);
						found = true;
						break;
					}
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		if(found == false){
			try{
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://localhost:3306/exposure";
				String username = "root";
				String password= "12345";
				Class.forName(driver);
				
				Connection con = DriverManager.getConnection(url,username,password);
				System.out.println("Connection created");
				
				
				String query = "SELECT p.id, p.title, p.description, u.username, p.url "
						+ "FROM photos p, users u "
						+ "WHERE p.userID = u.id AND p.private = 0";
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				
				ArrayList<Photo> photoList = new ArrayList<Photo>();
				ArrayList<String> usernameList = new ArrayList<String>();
				while(rs.next())
				{
					Photo currPhoto = new Photo();
					currPhoto.setId(rs.getInt("id"));
					currPhoto.setTitle(rs.getString("title"));
					currPhoto.setDescription(rs.getString("description"));
					currPhoto.setUrl(rs.getString("url"));
					
					photoList.add(currPhoto);
					usernameList.add(rs.getString("username"));
				}
				
				for(int i = 0; i < photoList.size(); i++){
					ArrayList<String> addingTags = new ArrayList<String>();
					String photoTagQuery = "SELECT p.title, t.name, pt.photoID " +
					"FROM photos p, tags t, phototags pt " + 
					"WHERE p.id = pt.photoID AND t.id = pt.tagID";
					Statement photoTagStatement = con.createStatement();
					ResultSet photoTagRS = photoTagStatement.executeQuery(photoTagQuery);
					while(photoTagRS.next()){
						if(photoList.get(i).getTitle().equals(photoTagRS.getString("title")) &&
						photoList.get(i).getId() == photoTagRS.getInt("photoID"))
							addingTags.add(photoTagRS.getString("name"));
					}
					
					photoList.get(i).setTags(addingTags);
				}
				
				request.setAttribute("photoList", photoList);
				request.setAttribute("usernameList", usernameList);
				request.getRequestDispatcher("index.jsp").forward(request, response);
				con.close();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			catch (SQLException e){
				
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
