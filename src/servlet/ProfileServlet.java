package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Photo;
import beans.User;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/exposure";
			String username = "root";
			String password= "12345";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url,username,password);
			System.out.println("Connection created");
			
			User currUser = new User();
			currUser.setId((int)request.getSession().getAttribute("id"));
			currUser.setName(String.valueOf(request.getSession().getAttribute("name")));
			currUser.setUsername(String.valueOf(request.getSession().getAttribute("username")));
			currUser.setDescription(String.valueOf(request.getSession().getAttribute("description")));
			
			String query = "SELECT p.id, p.title, p.description, p.url "
					+ "FROM photos p "
					+ "WHERE p.userID = " + (int)request.getSession().getAttribute("id");
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			ArrayList<Photo> photoList = new ArrayList<Photo>();
			while(rs.next())
			{
				Photo currPhoto = new Photo();
				currPhoto.setId(rs.getInt("id"));
				currPhoto.setTitle(rs.getString("title"));
				currPhoto.setDescription(rs.getString("description"));
				currPhoto.setUrl(rs.getString("url"));
				
				photoList.add(currPhoto);
			}
			
			for(int i = 0; i < photoList.size(); i++){
				ArrayList<String> addingTags = new ArrayList<String>();
				String photoTagQuery = "SELECT p.title, t.name " +
				"FROM photos p, tags t, phototags pt " + 
				"WHERE p.id = pt.photoID AND t.id = pt.tagID";
				Statement photoTagStatement = con.createStatement();
				ResultSet photoTagRS = photoTagStatement.executeQuery(photoTagQuery);
				while(photoTagRS.next()){
					if(photoList.get(i).getTitle().equals(photoTagRS.getString("title")))
						addingTags.add(photoTagRS.getString("name"));
				}
				
				photoList.get(i).setTags(addingTags);
			}
			
			String sharedQuery = "SELECT p.id, p.title, p.description, u.username, p.url "
					+ "FROM photos p, users u, privilege pr "
					+ "WHERE p.userID = u.id AND p.private = 1 AND pr.photoID = p.id AND pr.userID = " + (int)request.getSession().getAttribute("id");
			Statement sharedStatement = con.createStatement();
			ResultSet sharedRS = sharedStatement.executeQuery(sharedQuery);
			
			ArrayList<Photo> sharedList = new ArrayList<Photo>();
			ArrayList<String> sharedUsernames = new ArrayList<String>();
			while(sharedRS.next()){
				Photo currPhoto = new Photo();
				currPhoto.setId(sharedRS.getInt("id"));
				currPhoto.setTitle(sharedRS.getString("title"));
				currPhoto.setDescription(sharedRS.getString("description"));
				currPhoto.setUrl(sharedRS.getString("url"));
				
				sharedList.add(currPhoto);
				sharedUsernames.add(sharedRS.getString("username"));
			}
			
			for(int i = 0; i < sharedList.size(); i++){
				ArrayList<String> addingTags = new ArrayList<String>();
				String photoTagQuery = "SELECT p.title, t.name, pt.photoID " +
				"FROM photos p, tags t, phototags pt " + 
				"WHERE p.id = pt.photoID AND t.id = pt.tagID";
				Statement photoTagStatement = con.createStatement();
				ResultSet photoTagRS = photoTagStatement.executeQuery(photoTagQuery);
				while(photoTagRS.next()){
					if(sharedList.get(i).getTitle().equals(photoTagRS.getString("title")) &&
						sharedList.get(i).getId() == photoTagRS.getInt("photoID"))
						addingTags.add(photoTagRS.getString("name"));
				}
				
				sharedList.get(i).setTags(addingTags);
			}
			
			request.setAttribute("sharedList", sharedList);
			request.setAttribute("photoList", photoList);
			request.setAttribute("sharedUsernames", sharedUsernames);
			request.setAttribute("user", currUser);
			request.getRequestDispatcher("profile.jsp").forward(request, response);
			
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		catch (SQLException e){
			
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/exposure";
			String username = "root";
			String password= "12345";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url,username,password);
			System.out.println("Connection created");
			
			String query;
			String uname = request.getParameter("theusername");
			String filteredName = "";
			
			if(uname != null)
				for(int i = 1; i < uname.length(); i++)
					filteredName += uname.charAt(i);
			
			User currUser = new User();
			
			String userQuery = "SELECT * FROM users";
			Statement userStatement = con.createStatement();
			ResultSet userRS = userStatement.executeQuery(userQuery);
			
			while(userRS.next()){
				if(userRS.getString("username").equals(filteredName)){
					currUser.setId(userRS.getInt("id"));
					currUser.setName(userRS.getString("name"));
					currUser.setUsername(userRS.getString("username"));
					currUser.setDescription(userRS.getString("description"));
					break;
				}
			}
			
			query = "SELECT p.id, p.title, p.description, p.url "
					+ "FROM photos p "
					+ "WHERE p.userID = " + currUser.getId();
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			ArrayList<Photo> photoList = new ArrayList<Photo>();
			while(rs.next())
			{
				Photo currPhoto = new Photo();
				currPhoto.setId(rs.getInt("id"));
				currPhoto.setTitle(rs.getString("title"));
				currPhoto.setDescription(rs.getString("description"));
				currPhoto.setUrl(rs.getString("url"));
				
				photoList.add(currPhoto);
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
			request.setAttribute("user", currUser);
			
			Cookie[] cookies = request.getCookies();
			if(cookies.length > 1 || request.getSession().getAttribute("id") != null)
				request.getRequestDispatcher("profile.jsp").forward(request, response);
				
			else request.getRequestDispatcher("user-profile.jsp").forward(request, response);
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		catch (SQLException e){
			
			e.printStackTrace();
		}
	}

}
