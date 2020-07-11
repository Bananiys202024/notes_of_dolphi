package notes.of.dolphi.Notebook.jdbc.initializing;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_initializing {


	public void create_databases_if_not_exist() {
		create_table_notes_if_note_exist();
		create_table_users_if_note_exist();
	}

	private void create_table_notes_if_note_exist() {
		
		 // SQLite connection string
		String url = "jdbc:sqlite:notebook.db";
       
       // SQL statement for creating a new table
       String sql = "CREATE TABLE IF NOT EXISTS notes (\n"
               + "    id INTEGER PRIMARY KEY,\n"
               + "    title text NOT NULL,\n"
               + "    note text NOT NULL, \n"
               + "    date_of_add text NOT NULL, \n"
               + "    user text NOT NULL \n"
               + ");";
       
       try (Connection conn = DriverManager.getConnection(url);
               Statement stmt = conn.createStatement()) {
           // create a new table
           stmt.execute(sql);
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }	
		
	}

	private void create_table_users_if_note_exist() {
		
		 // SQLite connection string
		String url = "jdbc:sqlite:notebook.db";
       
       // SQL statement for creating a new table
       String sql = "CREATE TABLE IF NOT EXISTS users (\n"
               + "    id INTEGER PRIMARY KEY,\n"
               + "    username text NOT NULL,\n"
               + "    password text NOT NULL, \n"
               + "    email text NOT NULL, \n"
               + "    permission text NOT NULL \n"
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
