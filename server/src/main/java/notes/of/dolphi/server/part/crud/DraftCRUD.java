package notes.of.dolphi.server.part.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Note;

public class DraftCRUD {


	private final String read_sql = "SELECT id, title, note , date_of_add, deleted, synchronized_android, owner FROM draft";
	private final String create_sql = "INSERT INTO draft(title, note, date_of_add, owner) VALUES(?, ?, ?, ?)";
	private final String sql = "DELETE FROM draft WHERE id = ?";
	private final String read_by_id = "SELECT id, title, note , date_of_add, owner FROM draft WHERE id= ?";
	private final String update_by_id = "UPDATE draft SET title=?, note=? WHERE id= ?";
	private final String update_by_id_for_synchronization = "UPDATE draft SET synchronized_android= ? WHERE id= ?";
	private final String read_by_date_of_add = "SELECT id, title, note , date_of_add, deleted, synchronized_android, owner FROM draft WHERE date_of_add = ?";
	private final String update_deleted_by_date_of_add = "UPDATE draft SET deleted=true WHERE date_of_add=?";
	
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
	



	public List<Draft> read_all(String email) {
		List<Draft> list = new ArrayList<Draft>();
		
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        
        	boolean deleted = rs.getInt("deleted") != 0;
        	
			Draft draft = new Draft();
			draft.setId(rs.getInt("id"));
			draft.setDate_of_note(rs.getString("date_of_add"));
			draft.setNote(rs.getString("note"));
			draft.setTitle(rs.getString("title"));
			draft.setDeleted(deleted);
			draft.setOwner(rs.getString("owner"));
			draft.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	list.add(draft);
        	
        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		return list;
	}




	
	
	public List<Draft> read_all_notes() 
	{
	
		List<Draft> list = new ArrayList<Draft>();
		
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        
        	boolean deleted = rs.getInt("deleted") != 0;
        	
        	if(!deleted)
        	{
			Draft draft = new Draft();
			draft.setId(rs.getInt("id"));
			draft.setDate_of_note(rs.getString("date_of_add"));
			draft.setNote(rs.getString("note"));
			draft.setTitle(rs.getString("title"));
			draft.setDeleted(deleted);
			draft.setOwner(rs.getString("owner"));
			draft.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	list.add(draft);
        	}
        	
        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		return list;
	}

	
	public Boolean create(Draft draft, String logged_user) throws SQLException
	{
		 Connection conn = this.connect();
	     PreparedStatement pstmt = conn.prepareStatement(create_sql);
	     
	     pstmt.setString(1, draft.getTitle());
	     pstmt.setString(2, draft.getNote());
	     pstmt.setString(3, draft.getDate_of_note());
	     pstmt.setString(4, logged_user);
	     
	     pstmt.execute();
	     
	     return true;
	}
	

	public List<Draft> read_not_synchronized_records(String logged_user) {
		
		List<Draft> list = new ArrayList<Draft>();
				
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        	
        	if(rs.getString("owner").equals(logged_user))
        	{
        	boolean synchronized_record = rs.getInt("synchronized_android") != 0;	
        		
        	if(!synchronized_record)
        	{
			Draft draft = new Draft();
			draft.setId(rs.getInt("id"));
			draft.setDate_of_note(rs.getString("date_of_add"));
			draft.setNote(rs.getString("note"));
			draft.setTitle(rs.getString("title"));
			draft.setDeleted(rs.getInt("deleted") != 0);
			draft.setOwner(rs.getString("owner"));
			draft.setSynchronized_server(synchronized_record);
        	
        	list.add(draft);
        	}//end if
        	
        	}	
        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		return list;
		
	}
	
	
public List<Draft> read_not_synchronized_and_not_deleted_records(String logged_user) {
		
		List<Draft> list = new ArrayList<Draft>();
				
        try
        (Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(read_sql))
        {
        
        while (rs.next()) {
        	
        	if(rs.getString("owner").equals(logged_user))
        	{
        		
        	boolean synchronized_record = rs.getInt("synchronized_android") != 0;	
        	boolean deleted = rs.getInt("deleted") != 0;
        	
        	if(!synchronized_record)
        	{
        		if(!deleted)
        		{
			Draft draft = new Draft();
			draft.setId(rs.getInt("id"));
			draft.setDate_of_note(rs.getString("date_of_add"));
			draft.setNote(rs.getString("note"));
			draft.setTitle(rs.getString("title"));
			draft.setDeleted(deleted);
			draft.setOwner(rs.getString("owner"));
			draft.setSynchronized_server(synchronized_record);
        	
        	list.add(draft);
        		}
        	}//end if
        	
        	}	
        }
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		return list;
		
	}

	public List<Draft> read_deleted_records(String logged_user) {
		List<Draft> list = new ArrayList<Draft>();
		
		
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
			Draft draft = new Draft();
			draft.setId(rs.getInt("id"));
			draft.setDate_of_note(rs.getString("date_of_add"));
			draft.setNote(rs.getString("note"));
			draft.setTitle(rs.getString("title"));
			draft.setDeleted(deleted);
			draft.setOwner(rs.getString("owner"));
			draft.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	list.add(draft);
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

	
	
	public List<Draft> read(String logged_user) 
	{
	
		List<Draft> list = new ArrayList<Draft>();
		
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
			Draft draft = new Draft();
			draft.setId(rs.getInt("id"));
			draft.setDate_of_note(rs.getString("date_of_add"));
			draft.setNote(rs.getString("note"));
			draft.setTitle(rs.getString("title"));
			draft.setDeleted(deleted);
			draft.setOwner(rs.getString("owner"));
			draft.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	list.add(draft);
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

	public Draft read_by_id(String logged_user, int id) {
		
		Draft draft = new Draft();
		
        try
        (Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(read_by_id))
        {
        
        	 pstmt.setInt(1, id);
        	
        ResultSet rs = pstmt.executeQuery();	 
        	 
        while (rs.next()) {
        	
        	draft.setId(rs.getInt("id"));
        	draft.setDeleted(rs.getInt("deleted") != 0);
        	draft.setTitle(rs.getString("title"));
        	draft.setDate_of_note(rs.getString("date_of_add"));
        	draft.setNote(rs.getString("note"));
        	draft.setOwner(rs.getString("owner"));
        	draft.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	}	
        
      
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		
		return draft;
	}

	public String update(Draft draft) {
		
	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(update_by_id)) {
	 
	            // set the corresponding param
	            pstmt.setString(1, draft.getTitle());
	            pstmt.setString(2, draft.getNote());
	            pstmt.setInt(3, draft.getId());
	            // update 
	            
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        
	        return "success";
		
	}

	public String change_column_synchronized_with_android_to_true(List<Draft> list_all_users) 
	{

		if(list_all_users != null && list_all_users.size()>0)
		{
		for(Draft draft : list_all_users)
		{
			
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(update_by_id_for_synchronization)) {
 
            // set the corresponding param
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, draft.getId());
            // update 
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		}
		}//if
        
		return "notes were synchronized";
	}


	public void put_mark_on_deleted_records(List<Draft> list_all_drafts) {
		
		System.out.println("Starting checking----");
		if(list_all_drafts != null)
		for(Draft model : list_all_drafts)
		{
			
		Draft draft = read_by_date_of_add(model.getDate_of_note());
		System.out.println("Tut mir leid---"+ model.getDate_of_note());
     
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(update_deleted_by_date_of_add)) {

            // set the corresponding param
            pstmt.setString(1, draft.getDate_of_note());
            // update 
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
		}//end for loop
        

		
	}


	private Draft read_by_date_of_add(String date_of_note) {
		


		Draft draft = new Draft();
		
        try
        (Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(read_by_date_of_add))
        {
        
        pstmt.setString(1, date_of_note);
        	
        ResultSet rs = pstmt.executeQuery();	 
        	 
        while (rs.next()) {
        	
        	draft.setId(rs.getInt("id"));
        	draft.setDeleted(true);
        	draft.setTitle(rs.getString("title"));
        	draft.setDate_of_note(rs.getString("date_of_add"));
        	draft.setNote(rs.getString("note"));
        	draft.setOwner(rs.getString("owner"));
        	draft.setSynchronized_server(rs.getInt("synchronized_android") != 0);
        	
        	}	
        
        }
        catch(SQLException e )
        {
        	System.out.println(e);       
        }
        
		
		return draft;

		
	}

}
