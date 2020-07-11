package notes.of.dolphi.server.part.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;

import notes.of.dolphi.server.part.crud.CRUDdiary;
import notes.of.dolphi.server.part.crud.JDBC_initializing;
import notes.of.dolphi.server.part.crud.UsersCRUD;

public class CRUDUsersController {

	public static void check_if_user_exist(Socket socket,
			Message message) throws IOException, SQLException 
	{
		//init database
		System.out.println("Initialize database...");
		JDBC_initializing jdbc_init = new JDBC_initializing();
		jdbc_init.create_databases_if_not_exist();
		System.out.println("Initialize database finish.");
		//...
		
		//get request from client 
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute process of method
		UsersCRUD crud_login = new UsersCRUD();
		Boolean result = crud_login.check_user(message.getUser().getEmail(), message.getUser().getPassword());
		//...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(result);
		System.out.println("Finish process...");
		//...
	}

	public static void create_user(Socket socket,
			Message message) throws SQLException, IOException {
				
				//init database
				System.out.println("Initialize database...");
				JDBC_initializing jdbc_init = new JDBC_initializing();
				jdbc_init.create_databases_if_not_exist();
				System.out.println("Initialize database finish.");
				//...
				
				//get request from client 
				OutputStream outputStream = socket.getOutputStream();
				ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
				//...
				
				//execute process of method
				UsersCRUD users_crud = new UsersCRUD();
				Boolean result = users_crud.create_user(message.getUser());
				//...
				
				//send request from server
				System.out.println("sending resources...");
				object_output_stream.writeObject(result);
				System.out.println("Finish process...");
				//...
	}

	public static void change_column_synchronized_with_android_table_users(Socket socket, Message message) throws IOException, SQLException {
		
		//init database
		System.out.println("Initialize database...");
		JDBC_initializing jdbc_init = new JDBC_initializing();
		jdbc_init.create_databases_if_not_exist();
		System.out.println("Initialize database finish.");
		//...
		
		//get request from client 
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute process of method
		UsersCRUD users_crud = new UsersCRUD();
		
		List<User> list = message.getList_all_users();
		if(list != null)
		{
		for(User user : list)
		{
			users_crud.create_user_for_synchronization_process(user);
		}
		}
		//...
		System.out.println("key point");
		
		//get all notes from server
		List<User> result = users_crud.read_not_synchronized_records();
		users_crud.change_column_synchronized_with_android_to_true(result);
		//...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(result);
		System.out.println("Finish process...");
		//...
		
	}
	
	public static void change_column_synchronized_with_android_table_notes(Socket socket, Message message) throws IOException, SQLException {
		//init database
		System.out.println("Initialize database...");
		JDBC_initializing jdbc_init = new JDBC_initializing();
		jdbc_init.create_databases_if_not_exist();
		System.out.println("Initialize database finish.");
		//...
		
		//get request from client 
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute process of method
		CRUDdiary diary_crud = new CRUDdiary();
		List<Note> list = message.getList_all_notes();
		String logged_user = message.getUser().getEmail();
		
		if(list != null)
		{
		for(Note note : list)
		{
			diary_crud.create_synchronized_list(note, logged_user);
		}
		}
		
		//...
		System.out.println("key point");
		
		//get all notes from server
		List<Note> result = diary_crud.read_not_synchronized_and_not_deleted_records(logged_user);
		diary_crud.change_column_synchronized_with_android_to_true(result);
			
		//We can delete this part
		System.out.println("Result from server--That one mark---");
		for(Note note: result)
		{
			System.out.println("Notes-----"+note.getDate_of_note());;
		}
		System.out.println("Finish result from server---That one mark---");
		//...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(result);
		System.out.println("Finish process...");
		//...
	}

	public static void read_all_notes(Socket socket, Message message) throws IOException {
				
				//init database
				System.out.println("Initialize database...");
				JDBC_initializing jdbc_init = new JDBC_initializing();
				jdbc_init.create_databases_if_not_exist();
				System.out.println("Initialize database finish.");
				//...
				
				//get request from client 
				OutputStream outputStream = socket.getOutputStream();
				ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
				//...
				
				//execute process of method
				String logged_user = message.getUser().getEmail();		
				UsersCRUD crud_users = new UsersCRUD();
				List<User> notes = crud_users.read();
				//...
				
				//send request from server
				System.out.println("sending resources...");
				object_output_stream.writeObject(notes);
				System.out.println("Finish process...");
				//...
	}

	public static void totally_synchronisation_with_android_table_notes(Socket socket, Message message) throws IOException {
		

				//init database
				System.out.println("Initialize database...");
				JDBC_initializing jdbc_init = new JDBC_initializing();
				jdbc_init.create_databases_if_not_exist();
				System.out.println("Initialize database finish.");
				//...
				
				//get request from client 
				OutputStream outputStream = socket.getOutputStream();
				ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
				//...
				
				String logged_user =  message.getUser().getEmail();
				//get all notes from server
				CRUDdiary diary_crud = new CRUDdiary();
				List<Note> result = diary_crud.read_all(logged_user);
				diary_crud.change_column_synchronized_with_android_to_true(result);

				//...
				
				//send request from server
				System.out.println("sending resources...");
				object_output_stream.writeObject(result);
				System.out.println("Finish process...");
				//...
				
	}

	public static void totally_synchronization_with_android_table_users(Socket socket, Message message) throws IOException {
	


		//init database
		System.out.println("Initialize database...");
		JDBC_initializing jdbc_init = new JDBC_initializing();
		jdbc_init.create_databases_if_not_exist();
		System.out.println("Initialize database finish.");
		//...
		
		//get request from client 
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute process of method
		UsersCRUD users_crud = new UsersCRUD();
		
		//get all notes from server
		List<User> result = users_crud.read_all();
		//...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(result);
		System.out.println("Finish process...");
		//...
		

		
	}


}
