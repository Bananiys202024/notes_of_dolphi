package notes.of.dolphi.server.part.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;

public class UsersCRUD {

	private final String create_sql = "INSERT INTO users(email, username, password, permission, synchronized_android) VALUES(?, ?, ?, ?, ?)";
	private final String read_sql = "SELECT id, email, username , password, permission, synchronized_android, deleted FROM users";
	private final String update_by_id = "UPDATE users SET synchronized_android= ? WHERE id= ?";

	private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:notebook.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

	
	public Boolean create_user(User user) throws SQLException {
	
		 String email = user.getEmail();
		 String password = user.getPassword();
		 String username = user.getUsername();
		
		 Connection conn = this.connect();
	     PreparedStatement pstmt = conn.prepareStatement(create_sql);
	 
	     pstmt.setString(1, email);
	     pstmt.setString(2, username);
	     pstmt.setString(3, password);
	     pstmt.setString(4, "PEASANT");
	     pstmt.setBoolean(5, false);
	     
	     pstmt.execute();
		
	     return true;
	}
	
	public Boolean create_user_for_synchronization_process(User user) throws SQLException {
		
		 String email = user.getEmail();
		 String password = user.getPassword();
		 String username = user.getUsername();
		
		 Connection conn = this.connect();
	     PreparedStatement pstmt = conn.prepareStatement(create_sql);
	 
	     pstmt.setString(1, email);
	     pstmt.setString(2, username);
	     pstmt.setString(3, password);
	     pstmt.setString(4, "PEASANT");
	     pstmt.setBoolean(5, true);
	     
	     pstmt.execute();
		
	     return true;
	}


	public boolean check_user(String email, String password) throws SQLException {
				
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        	
        	if(rs.getString("email").equals(email) && rs.getString("password").equals(password))
        	{
        		return true;
        	}

        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);
        }
        return false;
		
		
	}

	public boolean check_if_user_exist(String email) {
		
		  try
	        (Connection conn = this.connect();
	        Statement stmt  = conn.createStatement();
	        ResultSet rs    = stmt.executeQuery(read_sql))
	        {
	        
	        while (rs.next()) {
	        	
	        	if(rs.getString("email").equals(email))
	        	{
	        		return true;
	        	}

	        }
	      
	        }
	        catch(SQLException e )
	        {
	        	System.out.println(e);
	        }
	        return false;
		
	
	}

	public String change_column_synchronized_with_android_to_true(List<User> list_all_users) {
	
		if(list_all_users != null && list_all_users.size()>0)
		{
		for(User user : list_all_users)
		{
			
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(update_by_id)) {
 
            // set the corresponding param
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, user.getId());
            // update 
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		}
		}//if
        return "notes were synchronized";
	}

	public List<User> read() {
		List<User> list = new ArrayList<User>();
		
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {

        	boolean deleted = rs.getInt("deleted") != 0;
        	
        	if(!deleted)
        	{
        	User user = new User();
        	user.setDeleted(deleted);
        	user.setEmail(rs.getString("email"));
        	user.setId(rs.getInt("id"));
        	user.setPassword(rs.getString("password"));
        	user.setPermission(rs.getString("permission"));
        	user.setSynchronized_server(rs.getInt("synchronized_android") != 0);		
        	user.setUsername(rs.getString("username"));
        	
        	list.add(user);
        	}
        	
        }
        
        return list;
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        return null;
	}

	public List<User> read_not_synchronized_records() {
		
		List<User> list = new ArrayList<User>();
		
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {

        	Boolean synchronized_record = rs.getInt("synchronized_android") != 0;
        	
        	if(!synchronized_record)
        	{
        	User user = new User();
        	user.setDeleted(rs.getInt("deleted") != 1);
        	user.setEmail(rs.getString("email"));
        	user.setId(rs.getInt("id"));
        	user.setPassword(rs.getString("password"));
        	user.setPermission(rs.getString("permission"));
        	user.setSynchronized_server(synchronized_record);		
        	user.setUsername(rs.getString("username"));
        	
        	list.add(user);
        	}//..if end
        }
        
        return list;
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        return null;
        
	}

	public List<User> read_deleted_records() {
	List<User> list = new ArrayList<User>();
		
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {

        	boolean deleted = rs.getInt("deleted") != 0;
        	
        	if(deleted)
        	{
        	User user = new User();
        	user.setDeleted(deleted);
        	user.setEmail(rs.getString("email"));
        	user.setId(rs.getInt("id"));
        	user.setPassword(rs.getString("password"));
        	user.setPermission(rs.getString("permission"));
        	user.setSynchronized_server(rs.getInt("synchronized_android") != 0);		
        	user.setUsername(rs.getString("username"));
        	
        	list.add(user);
        	}
        	
        }
        
        return list;
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        return null;
        
	}

	public List<User> read_all() {
			List<User> list = new ArrayList<User>();
		
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {

        	Boolean synchronized_record = rs.getInt("synchronized_android") != 0;
        	
        
        	User user = new User();
        	user.setDeleted(rs.getInt("deleted") != 1);
        	user.setEmail(rs.getString("email"));
        	user.setId(rs.getInt("id"));
        	user.setPassword(rs.getString("password"));
        	user.setPermission(rs.getString("permission"));
        	user.setSynchronized_server(synchronized_record);		
        	user.setUsername(rs.getString("username"));
        	
        	list.add(user);
        	
        }
        
        return list;
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        return null;
        
	}



}
