package notes.of.dolphi.Notebook.jdbc.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.notes_of_dolphi.model.Note;

public class UsersCRUD {

	private final String create_sql = "INSERT INTO users(email, username, password, permission) VALUES(?, ?, ?, ?)";
	private final String read_sql = "SELECT id, email, username , password, permission FROM users";

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
	
	public void create_user(String email, String username, String password) throws SQLException {
	
		 Connection conn = this.connect();
	     PreparedStatement pstmt = conn.prepareStatement(create_sql);
	 
	     pstmt.setString(1, email);
	     pstmt.setString(2, username);
	     pstmt.setString(3, password);
	     pstmt.setString(4, "PEASANT");

	     pstmt.execute();
		
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


}
