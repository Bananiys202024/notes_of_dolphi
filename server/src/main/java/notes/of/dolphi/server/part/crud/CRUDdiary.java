package notes.of.dolphi.server.part.crud;


import java.io.IOException;
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


public class CRUDdiary {

	private final String read_sql = "SELECT id, title, note , date_of_add, deleted, synchronized_android, owner FROM notes";
	private final String create_sql = "INSERT INTO notes(title, note, date_of_add, owner) VALUES(?, ?, ?, ?)";
	private final String create_synchronization_sql = "INSERT INTO notes(title, note, date_of_add, owner, synchronized_android) VALUES(?, ?, ?, ?, ?)";
	private final String synchronized_create_sql = "INSERT INTO notes(title, note, date_of_add, owner, synchronized_android) VALUES(?,?,?,?,?)";
	private final String sql = "DELETE FROM notes WHERE id = ?";
	private final String read_by_id = "SELECT id, title, note , date_of_add, owner FROM notes WHERE id= ?";
	private final String read_by_date_of_add = "SELECT id, title, note , date_of_add, owner FROM notes WHERE date_of_add= ?";
	private final String update_by_id = "UPDATE notes SET title=?, note=? WHERE id= ?";
	private final String update_by_id_for_synchronization = "UPDATE notes SET synchronized_android= ? WHERE id= ?";
	private final String update_deleted_by_id = "UPDATE notes SET deleted=? WHERE id= ?";

	
	
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
	
	public String create(Note note, String logged_user) throws SQLException
	{
		 Connection conn = this.connect();
	     PreparedStatement pstmt = conn.prepareStatement(create_sql);
	     
	     pstmt.setString(1, note.getTitle());
	     pstmt.setString(2, note.getNote());
	     pstmt.setString(3, note.getDate_of_note());
	     pstmt.setString(4, logged_user);
	     
	     pstmt.execute();
	     
	     return "success";
	}
	
	public List<Note> read_all_notes() {
		
		List<Note> list = new ArrayList<Note>();
		
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        
			Note note = new Note();
        	note.setId(rs.getInt("id"));
        	note.setDate_of_note(rs.getString("date_of_add"));
        	note.setNote(rs.getString("note"));
        	note.setTitle(rs.getString("title"));
        	note.setDeleted(rs.getInt("deleted") != 0);
        	note.setOwner(rs.getString("owner"));
        	note.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	list.add(note); 		
        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		return list;
	
	}

	public List<Note> read_not_synchronized_records(String logged_user) {
		
		List<Note> list = new ArrayList<Note>();
				
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        	
        	if(rs.getString("owner").equals(logged_user))
        	{
        
        	Boolean synchronized_record = rs.getInt("synchronized_android") != 0;	
        	
        	if(!synchronized_record)
        	{
			Note note = new Note();
        	note.setId(rs.getInt("id"));
        	note.setDate_of_note(rs.getString("date_of_add"));
        	note.setNote(rs.getString("note"));
        	note.setTitle(rs.getString("title"));
        	note.setDeleted(rs.getInt("deleted") != 0);
        	note.setOwner(rs.getString("owner"));
        	note.setSynchronized_server(synchronized_record);
        	
        	list.add(note);
        	}//if end...
        	}	
        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		return list;
		
	}
	
public List<Note> read_not_synchronized_and_not_deleted_records(String logged_user) {
		
		List<Note> list = new ArrayList<Note>();
				
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        	
        	if(rs.getString("owner").equals(logged_user))
        	{
        
        	Boolean synchronized_record = rs.getInt("synchronized_android") != 0;	
        	Boolean deleted = rs.getInt("deleted") != 0;
        	
        	if(!synchronized_record)
        	{
        		if(!deleted)
        		{
			Note note = new Note();
        	note.setId(rs.getInt("id"));
        	note.setDate_of_note(rs.getString("date_of_add"));
        	note.setNote(rs.getString("note"));
        	note.setTitle(rs.getString("title"));
        	note.setDeleted(deleted);
        	note.setOwner(rs.getString("owner"));
        	note.setSynchronized_server(synchronized_record);
        	
        	list.add(note);
        		}//end if deleted
        	}//if end...
        	}	
        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		return list;
		
	}

	
	public List<Note> read(String logged_user) 
	{
	
		List<Note> list = new ArrayList<Note>();
		
		System.out.println("logged_user--"+ logged_user);
		
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        	
        	if(rs.getString("owner").equals(logged_user))
        	{
        
        	boolean deleted = rs.getInt("deleted") != 0;
        	
        	if(!deleted)
        	{
			Note note = new Note();
        	note.setId(rs.getInt("id"));
        	note.setDate_of_note(rs.getString("date_of_add"));
        	note.setNote(rs.getString("note"));
        	note.setTitle(rs.getString("title"));
        	note.setDeleted(deleted);
        	note.setOwner(rs.getString("owner"));
        	note.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	list.add(note);
        	}
        	
        	}	
        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		return list;
	}
	
	public String delete(int id) {
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
 
            System.out.println("Remove---"+id);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
		return "success";
		
	}

	public Note read_by_id(String logged_user, int id) {
		
		Note note = new Note();
		
        try
        (Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(read_by_id))
        {
        
        	 pstmt.setInt(1, id);
        	
        ResultSet rs = pstmt.executeQuery();	 
        	 
        while (rs.next()) {
        	
        	note.setId(rs.getInt("id"));
        	note.setDeleted(rs.getInt("deleted") != 0);
            note.setTitle(rs.getString("title"));
        	note.setDate_of_note(rs.getString("date_of_add"));
        	note.setNote(rs.getString("note"));
        	note.setOwner(rs.getString("owner"));
        	note.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	}	
        
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		
		return note;
	}

	public String update(Note note) {
		
	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(update_by_id)) {
	 
	            // set the corresponding param
	            pstmt.setString(1, note.getTitle());
	            pstmt.setString(2, note.getNote());
	            pstmt.setInt(3, note.getId());
	            // update 
	            
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        
	        return "success";
		
	}

	public String change_column_synchronized_with_android_to_true(List<Note> list_all_users) 
	{

		if(list_all_users != null && list_all_users.size()>0)
		{
		for(Note note : list_all_users)
		{
			
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(update_by_id_for_synchronization)) {
 
            // set the corresponding param
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, note.getId());
            // update 
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		}
		}//if
        
		return "notes were synchronized";
	}

	public void create_synchronized_list(Note note, String logged_user) throws SQLException {
		
		 Connection conn = this.connect();
	     PreparedStatement pstmt = conn.prepareStatement(create_synchronization_sql);
	     
	     pstmt.setString(1, note.getTitle());
	     pstmt.setString(2, note.getNote());
	     pstmt.setString(3, note.getDate_of_note());
	     System.out.println("Checking time of creation----"+note.getDate_of_note());
	     pstmt.setString(4, logged_user);
	     pstmt.setBoolean(5, true);
	     pstmt.execute();
	}

	public void put_mark_on_deleted_records(List<Note> list) 
	{

		if(list != null)
		for(Note not : list)
		{
			
		Note note = read_by_date_of_add(not.getDate_of_note());
		
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(update_deleted_by_id)) {
        	
            // set the corresponding param
            pstmt.setBoolean(1,true);
            pstmt.setInt(2
            		, note.getId());
            // update 
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
		}//end for loop
        
	}

	private Note read_by_date_of_add(String date_of_note) {
		
		Note note = new Note();
		
        try
        (Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(read_by_date_of_add))
        {
        
        	 pstmt.setString(1, date_of_note);
        	
        ResultSet rs = pstmt.executeQuery();	 
        	 
        while (rs.next()) {
        	
        	note.setId(rs.getInt("id"));
        	note.setDeleted(rs.getInt("deleted") != 0);
            note.setTitle(rs.getString("title"));
        	note.setDate_of_note(rs.getString("date_of_add"));
        	note.setNote(rs.getString("note"));
        	note.setOwner(rs.getString("owner"));
        	note.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	}	
        
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		
		return note;
		
	}

	public List<Note> read_deleted_records(String logged_user) {

		List<Note> list = new ArrayList<Note>();
		
		System.out.println("logged_user--"+ logged_user);
		
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        	
        	if(rs.getString("owner").equals(logged_user))
        	{
        
        	boolean deleted = rs.getInt("deleted") != 0;
        	
        	if(deleted)
        	{
			Note note = new Note();
        	note.setId(rs.getInt("id"));
        	note.setDate_of_note(rs.getString("date_of_add"));
        	note.setNote(rs.getString("note"));
        	note.setTitle(rs.getString("title"));
        	note.setDeleted(deleted);
        	note.setOwner(rs.getString("owner"));
        	note.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	list.add(note);
        	}
        	
        	}	
        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		return list;
	}

	public List<Note> read_all(String logged_user) {

		List<Note> list = new ArrayList<Note>();
				
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        	
        	if(rs.getString("owner").equals(logged_user))
        	{

        	Boolean synchronized_record = rs.getInt("synchronized_android") != 0;	
        
			Note note = new Note();
        	note.setId(rs.getInt("id"));
        	note.setDate_of_note(rs.getString("date_of_add"));
        	note.setNote(rs.getString("note"));
        	note.setTitle(rs.getString("title"));
        	note.setDeleted(rs.getInt("deleted") != 0);
        	note.setOwner(rs.getString("owner"));
        	note.setSynchronized_server(synchronized_record);
        	
        	list.add(note);
        	}	
        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		return list;
		
	}


	

}
