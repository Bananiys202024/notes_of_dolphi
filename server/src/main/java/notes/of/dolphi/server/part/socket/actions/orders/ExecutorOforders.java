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
						
		if(order.equals("read_notes_online"))
		{
			CRUDdiaryController.read(socket, message);
		}
		
		
		if(order.equals("synchronize_from_android_table_draft"))
		{
			CRUDDraftController.change_column_synchronized_with_android_table_draft(socket, message);
		}
		
		if(order.equals("synchronize_from_android_table_notes"))
		{
			CRUDUsersController.change_column_synchronized_with_android_table_notes(socket, message);
		}
		
		if(order.equals("synchronize_from_android_table_users"))
		{
			CRUDUsersController.change_column_synchronized_with_android_table_users(socket, message);
		}
		
		
		
		if(order.equals("totally_synchronize_from_android_table_draft"))
		{
			CRUDDraftController.totally_synchronisation_with_android_table_draft(socket, message);
		}
		
		if(order.equals("totally_synchronize_from_android_table_notes"))
		{
			CRUDUsersController.totally_synchronisation_with_android_table_notes(socket, message);
		}
		
		if(order.equals("totally_synchronize_from_android_table_users"))
		{
			CRUDUsersController.totally_synchronization_with_android_table_users(socket, message);
		}
	
		
		
		
		
		
		
		
		
		
		if(order.equals("synchronize_deleted_records"))
		{
			Synchronization.synchronize_deleted_notes(socket, message);
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
			CRUDUsersController.create_user(socket, message);
		}
		
	}

}
