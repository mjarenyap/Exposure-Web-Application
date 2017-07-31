package servlet;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		//copy the local photo to the beans/uploads directory
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
			
			//add the photos information (i.e. id, title, description, uploader)
			String query = "SELECT COUNT(*) num FROM photos";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			int newID = 0;
			while(rs.next())
				newID = rs.getInt("num");
			
			String newURL = "beans/uploads/photo-" + (newID + 1) + ".png";
			String newTitle = request.getParameter("title");
			String newDescription = request.getParameter("description");
			String oldURL = request.getParameter("imageURL");
			
			System.out.println(newTitle);
			System.out.println(newDescription);
			System.out.println(oldURL);
			/*
			Base64 base = new Base64();
			String encodingPrefix = "base64,";
			int contentStartIndex = oldURL.indexOf(encodingPrefix) + encodingPrefix.length();
			byte[] imageData = base.decodeBase64(oldURL.substring(contentStartIndex));
			*/
			
			//InputStream is = null;
	        OutputStream os = null;
	        byte[] data = Base64.decodeBase64(oldURL);
	        try {
	            //is = new FileInputStream(new File(oldURL));
	            os = new FileOutputStream("/Users/mikejarenbyap/Documents/Term 3 AY1617/WEBAPDE/MP2/MachineProject2/WebContent/"+ newURL);
	            os.write(data);
	            //is.close();
	            os.close();
	        }catch(IOException e){}
			
			String[] addingTags = request.getParameterValues("addedTag");
			if(addingTags != null){
				for(int i = 0; i < addingTags.length; i++){
					boolean exists = false;
					String tagQuery = "SELECT * from tags";
					Statement tagStatement = con.createStatement();
					ResultSet tagRS = tagStatement.executeQuery(tagQuery);
					
					while(tagRS.next()){
						if(tagRS.getString("name").equals(addingTags[i])){
							Statement photoTagInsert = con.createStatement();
							photoTagInsert.executeUpdate("INSERT into phototags(photoid, tagid) " +
							"values(" + newID + ", " + tagRS.getInt("id") + ")");
							exists = true;
							break;
						}
					}
					
					if(!exists){
						String newTagIdQuery = "SELECT COUNT(*) num from tags";
						Statement newTagIdStatement = con.createStatement();
						ResultSet newTagIdSet = newTagIdStatement.executeQuery(newTagIdQuery);
						
						int newTagId = 0;
						while(newTagIdSet.next())
							newTagId = newTagIdSet.getInt("num");
						
						Statement newTagQuery = con.createStatement();
						newTagQuery.executeUpdate("INSERT into tags(id, name) " +
								"values(" + newTagId + ", \'" + addingTags[i] + "\')");
						
						Statement photoTagInsert = con.createStatement();
						photoTagInsert.executeUpdate("INSERT into phototags(photoid, tagid) " +
						"values(" + newID + ", " + newTagId + ")");
					}
					
					exists = false;
				}
			}
			
			int privacy = 0;
			if(request.getParameterValues("privacy") != null){
				privacy = 1;
				
				String[] privilegedUsers = request.getParameterValues("addedUser");
				if(privilegedUsers != null){
					ArrayList<Integer> privilegeIDs = new ArrayList<Integer>();
					
					for(int i = 0; i < privilegedUsers.length; i++){
						String userQuery = "SELECT * FROM users";
						Statement userStatement = con.createStatement();
						ResultSet userRS = userStatement.executeQuery(userQuery);
						
						while(userRS.next()){
							if(privilegedUsers[i].equals(userRS.getString("username")))
								privilegeIDs.add(userRS.getInt("id"));
						}
					}
					
					for(int i = 0; i < privilegeIDs.size(); i++){
						Statement privilegeStatement = con.createStatement();
						privilegeStatement.executeUpdate("INSERT into privilege(photoID, userID) " + 
						"values(" + newID + ", " + privilegeIDs.get(i) +")");
					}
				}
			}
			
			Statement st2 = con.createStatement();
			st2.executeUpdate("INSERT into photos(id, title, description, userID, url, private) " +
			"values(" + newID + ",\'" + newTitle + "\',\'" + newDescription + "\'," + 
					(int)request.getSession().getAttribute("id") + ", \'" + newURL + "\', " + privacy + ")");
			
			con.close();
			request.getRequestDispatcher("feed").forward(request, response);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		catch (SQLException e){
			
			e.printStackTrace();
		}
	}

}
