package notes.of.dolphi.server.part.crud;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_initializing {

	public void create_databases_if_not_exist() {	
		//drop_all_tables();
		create_table_notes_if_exist();
		create_table_users_if_exist();
		create_table_draft_if_exist();
	}

	private void drop_database() {
		  
		  String url = "jdbc:sqlite:notebook.db";

		
		  String sql_query_1 = "DROP TABLE draft";
		  String sql_query_2 = "DROP TABLE notes";
		  String sql_query_3 = "DROP TABLE users";
		  
		  try (Connection conn = DriverManager.getConnection(url);
	               Statement stmt = conn.createStatement()) {
	           // create a new table
			  stmt.executeUpdate(sql_query_1);
			  stmt.executeUpdate(sql_query_2);
			  stmt.executeUpdate(sql_query_3);
	       } catch (SQLException e) {
	           System.out.println(e.getMessage());
	       }	
		  
		
	}

	private void create_table_notes_if_exist() {
		
		 // SQLite connection string
		String url = "jdbc:sqlite:notebook.db";
       
       // SQL statement for creating a new table
       String sql = "CREATE TABLE IF NOT EXISTS notes (\n"
               + "    id INTEGER PRIMARY KEY,\n"
               + "    title text NOT NULL,\n"
               + "    note text NOT NULL, \n"
               + "    date_of_add text NOT NULL, \n"
               + "    owner text NOT NULL, \n"
               + "    synchronized_client boolean DEFAULT 0 NOT NULL, \n"
               + "    synchronized_android boolean DEFAULT 0 NOT NULL, \n"
               + "    deleted boolean DEFAULT 0 NOT NULL, \n"
			   + "    synchronized_deleted boolean DEFAULT 0 NOT NULL \n"
               + ");";
       
       try (Connection conn = DriverManager.getConnection(url);
               Statement stmt = conn.createStatement()) {
           // create a new table
           stmt.execute(sql);
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }	
		
	}

	private void create_table_users_if_exist() {
		
		 // SQLite connection string
		String url = "jdbc:sqlite:notebook.db";
       
       // SQL statement for creating a new table
       String sql = "CREATE TABLE IF NOT EXISTS users (\n"
               + "    id INTEGER PRIMARY KEY,\n"
               + "    username text NOT NULL,\n"
               + "    password text NOT NULL, \n"
               + "    email text NOT NULL, \n"
               + "    permission text NOT NULL, \n"
               + "    synchronized_client boolean DEFAULT 0 NOT NULL, \n"
               + "    synchronized_android boolean DEFAULT 0 NOT NULL, \n"
               + "    deleted boolean DEFAULT 0 NOT NULL, \n"
			   + "    synchronized_deleted boolean DEFAULT 0 NOT NULL \n"
               + ");";

       try (Connection conn = DriverManager.getConnection(url);
               Statement stmt = conn.createStatement()) {
           // create a new table
           stmt.execute(sql);

       } catch (SQLException e) {

           System.out.println(e.getMessage());
       }	
		
	}
	
	private void create_table_draft_if_exist() {
		
		 // SQLite connection string
		String url = "jdbc:sqlite:notebook.db";
      
      // SQL statement for creating a new table
      String sql = "CREATE TABLE IF NOT EXISTS draft (\n"
              + "    id INTEGER PRIMARY KEY,\n"
              + "    title text NOT NULL,\n"
              + "    note text NOT NULL, \n"
              + "    date_of_add text NOT NULL, \n"
              + "    owner text NOT NULL, \n"
              + "    synchronized_client boolean DEFAULT 0 NOT NULL, \n"
              + "    synchronized_android boolean DEFAULT 0 NOT NULL, \n"
              + "    deleted boolean DEFAULT 0 NOT NULL, \n"
			  + "    synchronized_deleted boolean DEFAULT 0 NOT NULL \n"
              + ");";
      
      try (Connection conn = DriverManager.getConnection(url);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
          System.out.println(e.getMessage());
      }	
		
	}



}
