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

/**
 * Servlet implementation class ResultServlet
 */
@WebServlet("/result")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultServlet() {
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
			
			String searchQuery = request.getParameter("tagSearch");
			System.out.println(searchQuery);
			
			String query = "SELECT p.id, p.title, p.description, u.username, p.url, t.name "
					+ "FROM photos p, users u, tags t, phototags pt "
					+ "WHERE p.userID = u.id AND p.private = 0 AND pt.photoID = p.id AND pt.tagID = t.id AND t.name = \'" + 
					searchQuery + "\'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			ArrayList<Photo> photoList = new ArrayList<Photo>();
			ArrayList<String> usernameList = new ArrayList<String>();
			ArrayList<String> tagList = new ArrayList<String>();
			tagList.add(searchQuery);
			
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
			
			for(int i = 0; i < photoList.size(); i++)
				photoList.get(i).setTags(tagList);
			
			request.setAttribute("query", searchQuery);
			request.setAttribute("photoList", photoList);
			request.setAttribute("usernameList", usernameList);
			
			Cookie[] cookies = request.getCookies();
			
			if(cookies.length == 2 || request.getSession().getAttribute("id") != null){
				String sharedQuery = "SELECT p.id, p.title, p.description, u.username, p.url, t.name "
						+ "FROM photos p, users u, tags t, phototags pt, privilege pr "
						+ "WHERE p.userID = u.id AND p.private = 1 AND pt.photoID = p.id AND pt.tagID = t.id AND t.name = \'" + 
						searchQuery + "\' AND pr.photoID = p.id AND pr.userID = " + (int)request.getSession().getAttribute("id");
				Statement sharedStatement = con.createStatement();
				ResultSet sharedRS = sharedStatement.executeQuery(sharedQuery);
				
				while(sharedRS.next()){
					Photo currPhoto = new Photo();
					currPhoto.setId(rs.getInt("id"));
					currPhoto.setTitle(rs.getString("title"));
					currPhoto.setDescription(rs.getString("description"));
					currPhoto.setUrl(rs.getString("url"));
					
					photoList.add(currPhoto);
					usernameList.add(rs.getString("username"));
				}
				
				request.setAttribute("photoList", photoList);
				request.setAttribute("usernameList", usernameList);
				request.getRequestDispatcher("results.jsp").forward(request, response);
			}
			
			else request.getRequestDispatcher("search.jsp").forward(request, response);
			
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
		//doGet(request, response);
		
		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/exposure";
			String username = "root";
			String password= "12345";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url,username,password);
			System.out.println("Connection created");
			
			String searchQuery = request.getParameter("search");
			
			String query = "SELECT p.id, p.title, p.description, u.username, p.url, t.name "
					+ "FROM photos p, users u, tags t, phototags pt "
					+ "WHERE p.userID = u.id AND p.private = 0 AND pt.photoID = p.id AND pt.tagID = t.id AND t.name = \'" + 
					searchQuery + "\'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			ArrayList<Photo> photoList = new ArrayList<Photo>();
			ArrayList<String> usernameList = new ArrayList<String>();
			ArrayList<String> tagList = new ArrayList<String>();
			tagList.add(searchQuery);
			
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
			
			for(int i = 0; i < photoList.size(); i++)
				photoList.get(i).setTags(tagList);
			
			request.setAttribute("query", searchQuery);
			request.setAttribute("photoList", photoList);
			request.setAttribute("usernameList", usernameList);
			
			Cookie[] cookies = request.getCookies();
			
			if(cookies.length == 2 || request.getSession().getAttribute("id") != null){
				String sharedQuery = "SELECT p.id, p.title, p.description, u.username, p.url, t.name "
						+ "FROM photos p, users u, tags t, phototags pt, privilege pr "
						+ "WHERE p.userID = u.id AND p.private = 1 AND pt.photoID = p.id AND pt.tagID = t.id AND t.name = \'" + 
						searchQuery + "\' AND pr.photoID = p.id AND pr.userID = " + (int)request.getSession().getAttribute("id");
				Statement sharedStatement = con.createStatement();
				ResultSet sharedRS = sharedStatement.executeQuery(sharedQuery);
				
				while(sharedRS.next()){
					Photo currPhoto = new Photo();
					currPhoto.setId(rs.getInt("id"));
					currPhoto.setTitle(rs.getString("title"));
					currPhoto.setDescription(rs.getString("description"));
					currPhoto.setUrl(rs.getString("url"));
					
					photoList.add(currPhoto);
					usernameList.add(rs.getString("username"));
				}
				
				request.setAttribute("photoList", photoList);
				request.setAttribute("usernameList", usernameList);
				request.getRequestDispatcher("results.jsp").forward(request, response);
			}
			
			else request.getRequestDispatcher("search.jsp").forward(request, response);
			
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		catch (SQLException e){
			
			e.printStackTrace();
		}
	}

}
