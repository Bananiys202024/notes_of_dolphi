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
				
		System.out.println("We got connection "+ order);
		
		if(order.equals("synchronize_from_android_table_draft"))
		{
			System.out.println("Start synchronization draft operation..");
			CRUDDraftController.change_column_synchronized_with_android_table_draft(socket, message);
		}
		
		if(order.equals("synchronize_from_android_table_notes"))
		{
			System.out.println("Start read operation..");
			CRUDUsersController.change_column_synchronized_with_android_table_notes(socket, message);
		}
		
		if(order.equals("synchronize_from_android_table_users"))
		{
			System.out.println("Start read operation..");
			CRUDUsersController.change_column_synchronized_with_android_table_users(socket, message);
		}
		
		
		
		if(order.equals("totally_synchronize_from_android_table_draft"))
		{
			System.out.println("Start synchronization draft operation..");
			CRUDDraftController.totally_synchronisation_with_android_table_draft(socket, message);
		}
		
		if(order.equals("totally_synchronize_from_android_table_notes"))
		{
			System.out.println("Start read operation..");
			CRUDUsersController.totally_synchronisation_with_android_table_notes(socket, message);
		}
		
		if(order.equals("totally_synchronize_from_android_table_users"))
		{
			System.out.println("Start read operation..");
			CRUDUsersController.totally_synchronization_with_android_table_users(socket, message);
		}
	
		
		
		
		
		
		
		
		
		
		if(order.equals("synchronize_deleted_records"))
		{
			System.out.println("Start delete_synchronization");
			Synchronization.synchronize_deleted_notes(socket, message);
			System.out.println("Finish delete synchronization");
		}
		
		if(order.equals("totally_synchronization"))
		{
			System.out.println("Start totally_synchronization");
			Synchronization.totally_synchronization(socket, message);
			System.out.println("Finish totaly synchronization");
		}
		
		
		if(order.equals("read_all_records_draft_table"))
		{
			System.out.println("Start create draft operationf..w");
			CRUDDraftController.read(socket, message);
		}
		
		
		if(order.equals("create_draft_note"))
		{
			System.out.println("Start create note operation..e");
			CRUDDraftController.create_note(socket, message);
		}
		
		if(order.equals("read_all_draft_notes"))
		{
			System.out.println("Start read operation..l");
			CRUDDraftController.read(socket, message);
		}
		
		if(order.equals("delete_draft_note"))
		{
			System.out.println("Start delete note operation..p");
			CRUDDraftController.delete_note(socket, message);
		}
		
		if(order.equals("update_draft_note"))
		{
			System.out.println("Start update note operation..[");
			CRUDDraftController.update_note(socket, message);
		}
		
		
		
		
		
		
		
		
		
		if(order.equals("read_absolutely_all_notes"))
		{
			System.out.println("Start read operation..");
			CRUDdiaryController.read_all(socket, message);
		}
		
		if(order.equals("read_all_notes"))
		{
			System.out.println("Start read operation..");
			CRUDdiaryController.read(socket, message);
		}
		
		if(order.equals("read_all_users"))
		{
			System.out.println("Start read operation..");
			CRUDUsersController.read_all_notes(socket, message);
		}
		
		
		if(order.equals("read_by_id"))
		{
			System.out.println("Start read by id operation..");
			CRUDdiaryController.read_by_id(socket, message);
		}
		
		if(order.equals("create"))
		{
			System.out.println("Start create note operation..");
			CRUDdiaryController.create_note(socket, message);
		}
		
		if(order.equals("update"))
		{
			System.out.println("Start update note operation..");
			CRUDdiaryController.update_note(socket, message);
		}
		
		if(order.equals("delete"))
		{
			System.out.println("Start delete note operation..");
			CRUDdiaryController.delete_note(socket, message);
		}
		
		if(order.equals("check_if_user_exist_for_process_login"))
		{
			System.out.println("Start check login operation..");
			CRUDUsersController.check_if_user_exist(socket, message);
		}
		
		if(order.equals("create_user"))
		{
			System.out.println("Start create user operation..");
			CRUDUsersController.create_user(socket, message);
		}
		
	}

}
