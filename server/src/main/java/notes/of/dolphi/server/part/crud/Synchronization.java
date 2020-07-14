package notes.of.dolphi.server.part.crud;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;

public class Synchronization {

	public static void totally_synchronization(Socket socket, Message message) throws IOException {
	
		
		//send to socket
		
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
		
		//main process
		Message message_model = new Message();
		CRUDdiary crud_diary = new CRUDdiary(); 
		DraftCRUD crud_draft = new DraftCRUD();
		UsersCRUD crud_users = new UsersCRUD();
		
		List<Draft> list_all_draft = crud_draft.read_all_notes();
		List<Note> list_all_notes = crud_diary.read_all_notes();
		List<User> list_all_users = crud_users.read();
		
		message_model.setList_all_users(list_all_users);
		message_model.setList_all_notes(list_all_notes);
		message_model.setList_all_drafts(list_all_draft);
		////...
		

		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(message_model);
		System.out.println("Finish process...");
		//...
		//...
	}

	public static void synchronize_deleted_notes(Socket socket, Message message) throws IOException
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
		
		String logged_user = message.getUser().getEmail();
		
		//main process
		Message message_model = new Message();
		CRUDdiary crud_diary = new CRUDdiary(); 
		DraftCRUD crud_draft = new DraftCRUD();
		UsersCRUD crud_users = new UsersCRUD();
		
		List<Draft> list_all_draft = crud_draft.read_deleted_records(logged_user);
		List<Note> list_all_notes = crud_diary.read_deleted_records(logged_user);
		List<User> list_all_users = crud_users.read_deleted_records();
		
		message_model.setList_all_users(list_all_users);
		message_model.setList_all_notes(list_all_notes);
		message_model.setList_all_drafts(list_all_draft);
		
		crud_diary.put_mark_on_deleted_records(message.getList_all_notes());
		crud_draft.put_mark_on_deleted_records(message.getList_all_drafts());
		////...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(message_model);
		System.out.println("Finish process...");
		//...
		//...
	}

}
