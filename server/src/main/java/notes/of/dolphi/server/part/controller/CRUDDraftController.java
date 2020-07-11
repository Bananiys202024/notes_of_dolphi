package notes.of.dolphi.server.part.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;

import notes.of.dolphi.server.part.crud.CRUDdiary;
import notes.of.dolphi.server.part.crud.DraftCRUD;
import notes.of.dolphi.server.part.crud.JDBC_initializing;
import notes.of.dolphi.server.part.crud.UsersCRUD;

public class CRUDDraftController {

	public static void create_draft(Socket socket,
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
				
				String logged_user = message.getUser().getEmail();
				String username = message.getUser().getUsername();
				String password = message.getUser().getPassword();
				
				List<Draft> draft_list = message.getList_all_drafts();
				//execute process of method
				DraftCRUD draft_crud = new DraftCRUD();
				for(Draft draft : draft_list)
				{
				draft_crud.create(draft, logged_user);
				}
				//...
				
				//send request from server
				System.out.println("sending resources...");
				object_output_stream.writeObject(true);
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
		UsersCRUD user_crud = new UsersCRUD();
		user_crud.change_column_synchronized_with_android_to_true(message.getList_all_users());
		//...
		System.out.println("key point");
		
		//get all notes from server
		List<User> result = user_crud.read_not_synchronized_records();
		//...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(result);
		System.out.println("Finish process...");
		//...
		
	}
	
public static void change_column_synchronized_with_android_table_draft(Socket socket, Message message) throws IOException, SQLException {
		
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
		
		
		//save drafts in database
		List<Draft> list = message.getList_all_drafts();
		String logged_user = message.getUser().getEmail();
		DraftCRUD draft_crud = new DraftCRUD();

		if(list != null)
		for(Draft draft : list)
		{
		draft_crud.create(draft, logged_user);
		draft_crud.change_column_synchronized_with_android_to_true(message.getList_all_drafts());
		}
		//
		
		System.out.println("key point draft synchronization");
		List<Draft> result = draft_crud.read_not_synchronized_and_not_deleted_records(message.getUser().getEmail());
		
		System.out.println("result--draft----KEY---POINT---"+result.size());
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(result);
		System.out.println("Finish process...");
		//...
		
	}
	
	public static void change_column_synchronized_with_android_table_notes(Socket socket, Message message) throws IOException {
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
		diary_crud.change_column_synchronized_with_android_to_true(message.getList_all_notes());
		//...
		System.out.println("key point");
		
		//get all notes from server
		String logged_user = message.getUser().getEmail();
		List<Note> result = diary_crud.read_not_synchronized_records(logged_user);
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

	public static void create_note(Socket socket, Message message) throws IOException, SQLException {
		

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
				Draft draft = message.getDraft();
				draft.setDate_of_note(draft.getDate_of_note());
				DraftCRUD crud_draft = new DraftCRUD();
				Boolean result = crud_draft.create(draft, logged_user);
				//...
				
				//send request from server
				System.out.println("sending resources...");
				object_output_stream.writeObject(result);
				System.out.println("Finish process...");
				//...
				
	}

	public static void read(Socket socket, Message message) throws IOException {
		
		//init database
		System.out.println("Initialize database...draft");
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
		DraftCRUD draft_crud = new DraftCRUD();
		List<Draft> drafts = draft_crud.read(logged_user);
		//...
		
		//send request from server
		System.out.println("sending resources...draft");
		object_output_stream.writeObject(drafts);
		System.out.println("Finish process...");
		//...
	}

	public static void delete_note(Socket socket, Message message) throws IOException {
		


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
		int id = message.getNote().getId();
		DraftCRUD crud_draft = new DraftCRUD();
		String result = crud_draft.delete(id);
		//...
		
		//send request from server
		System.out.println("sending resources....");
		object_output_stream.writeObject(result);
		System.out.println("Finish process");
		//...
		
	}

	public static void update_note(Socket socket, Message message) throws IOException {
	


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
		Draft draft = message.getDraft();
		DraftCRUD crud_draft = new DraftCRUD();
		String result = crud_draft.update(draft);
		//...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(result);
		System.out.println("Finish process...");
		//...
		
	}

	public static void totally_synchronisation_with_android_table_draft(Socket socket, Message message) throws IOException, SQLException {

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
		
		DraftCRUD draft_crud = new DraftCRUD();
		
		List<Draft> result = draft_crud.read_all(message.getUser().getEmail());
		
		System.out.println("result--draft----KEY---POINT---"+result.size());
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(result);
		System.out.println("Finish process...");
		//...
		
		
	}



}
