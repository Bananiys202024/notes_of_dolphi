package notes.of.dolphi.server.part.socket.actions.orders;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import com.example.notes_of_dolphi.model.Message;

import notes.of.dolphi.server.part.controller.CRUDDraftController;
import notes.of.dolphi.server.part.controller.CRUDUsersController;
import notes.of.dolphi.server.part.controller.CRUDdiaryController;
import notes.of.dolphi.server.part.crud.Synchronization;

public class ExecutorOforders {

	public static void execute_order_from_client(Socket socket, Message message) throws IOException, SQLException {		
		
		String order = message.getMessage();
			
		System.out.println("MARKMARKMARKMARKMARKMARK----"+message.getList_all_drafts());
		if(message.getList_all_drafts() != null)
			System.out.println("SIZESIZESIZESIZE----"+message.getList_all_drafts().size());

			
		System.out.println("Order-------"+order);

		if(order.equals("Check connection"))
		{
			Synchronization.check_connection(socket, message);
		}
		
		if(order.equals("read_notes_online"))
		{
			CRUDdiaryController.read(socket, message);
		}
		
		
		if(order.equals("synchronize_from_android_table_draft"))
		{
			System.out.println("Start process of synchronization for table draft");
			CRUDDraftController.change_column_synchronized_with_android_table_draft(socket, message);
			System.out.println("Finish process of synchronization for table draft");
		}
		
		if(order.equals("synchronize_from_android_table_notes"))
		{
			System.out.println("Start process of synchronization for table notes");
			CRUDUsersController.change_column_synchronized_with_android_table_notes(socket, message);
			System.out.println("Finish process of synchronization for table notes");
		}
		
		if(order.equals("synchronize_from_android_table_users"))
		{
			System.out.println("Start process of synchronization for table users");

			CRUDUsersController.change_column_synchronized_with_android_table_users(socket, message);
			System.out.println("Finish process of synchronization for table users");

		}
		
		
		
		if(order.equals("totally_synchronize_from_android_table_draft"))
		{
			System.out.println("Start totally process of synchronization for table draft");
			CRUDDraftController.totally_synchronisation_with_android_table_draft(socket, message);
			System.out.println("Finish totally process of synchronization for table draft");
		}
		
		if(order.equals("totally_synchronize_from_android_table_notes"))
		{
			System.out.println("Start totally process of synchronization for table draft");
			CRUDUsersController.totally_synchronisation_with_android_table_notes(socket, message);
			System.out.println("Finish totally process of synchronization for table notes");
			
		}
		
		if(order.equals("totally_synchronize_from_android_table_users"))
		{
			System.out.println("Start totally process of synchronization for table draft");
			CRUDUsersController.totally_synchronization_with_android_table_users(socket, message);
			System.out.println("Finish totally process of synchronization for table users");
			
		}
	
		
		if(order.equals("synchronize_deleted_records"))
		{
			Synchronization.synchronize_deleted_notes(socket, message);
		}
		
		if(order.equals("synchronize_all_edited_records"))
		{
			Synchronization.synchronize_all_edited_records(socket, message);
		}

		
		if(order.equals("totally_synchronization"))
		{
			Synchronization.totally_synchronization(socket, message);
		}
		
		
		if(order.equals("read_all_records_draft_table"))
		{
			CRUDDraftController.read(socket, message);
		}
		
		
		if(order.equals("create_draft_note"))
		{
			CRUDDraftController.create_note(socket, message);
		}
		
		if(order.equals("read_all_draft_notes"))
		{
			CRUDDraftController.read(socket, message);
		}
		
		if(order.equals("delete_draft_note"))
		{
			CRUDDraftController.delete_note(socket, message);
		}
		
		if(order.equals("update_draft_note"))
		{
			CRUDDraftController.update_note(socket, message);
		}
		
		
		
		
		
		

		if(order.equals("read_absolutely_all_notes"))
		{
			CRUDdiaryController.read_all(socket, message);
		}
		
		if(order.equals("read_all_notes"))
		{
			CRUDdiaryController.read(socket, message);
		}
		
		if(order.equals("read_all_users"))
		{
			CRUDUsersController.read_all_notes(socket, message);
		}
		
		
		if(order.equals("read_by_id"))
		{
			CRUDdiaryController.read_by_id(socket, message);
		}
		
		if(order.equals("create"))
		{
			CRUDdiaryController.create_note(socket, message);
		}
		
		if(order.equals("update"))
		{
			CRUDdiaryController.update_note(socket, message);
		}
		
		if(order.equals("delete"))
		{
			CRUDdiaryController.delete_note(socket, message);
		}
		
		if(order.equals("check_if_user_exist_for_process_login"))
		{
			CRUDUsersController.check_if_user_exist(socket, message);
		}
		
		if(order.equals("create_user"))
		{
			System.out.println("Starting operation create user...");
			CRUDUsersController.create_user(socket, message);
			System.out.println("Finish operation create user...");
		}
		
	}

}
