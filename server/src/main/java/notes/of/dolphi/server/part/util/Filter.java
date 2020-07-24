package notes.of.dolphi.server.part.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.notes_of_dolphi.model.Draft;

import notes.of.dolphi.server.part.crud.DraftCRUD;

public class Filter {

	public static List<Draft> filter_list(List<Draft> list_client)
	{
		
		//1. get date of add of client list("list");
//		2. read_all_notes_from_database
//		3. read date_of_add of server
//		4. If dates remove don't add to new list
//		5. return new list
		//6.check by date of add and email
//		
		//how compare two lists by two columns
	
		DraftCRUD draft_crud = new DraftCRUD();
		List<Draft> list_draft = draft_crud.absolutely_read();
		
		if(list_draft != null)
		{
			System.out.println("KeyPoint---"+list_draft.size());
		}
		else
		{
			System.out.println("LIST NULL");
		}
		
		
		if(list_client != null)
		System.out.println("SIZEEE_____" + list_client.size());
			
		DraftCRUD draft_crud_d = new DraftCRUD();
		List<Draft> server_list = draft_crud_d.absolutely_read();
			
		List<Draft> result = new ArrayList<>();
		
		System.out.println("Checking important check---"+server_list);
		
		if(server_list == null)
		return list_client;
		
		System.out.println("Total loop start--");
		for(Draft draft_1 : list_client)
		{
			for(Draft draft_2 :server_list)
			{
				System.out.println("Pratially start");
				String date_1 = draft_1.getDate_of_note();
				String date_2 = draft_2.getDate_of_note();
				
				String owner_1 = draft_1.getOwner();
				String owner_2 = draft_2.getOwner();
			
				System.out.println("date1---"+date_1);
				System.out.println("date2---"+date_2);
				System.out.println("owner1---"+owner_1);
				System.out.println("owner2---"+owner_2);
				
				if(date_1.equals(date_2) && owner_1.equals(owner_2))
				{
					
				}
				else
				{
					result.add(draft_2);
				}	
				System.out.println("Pratially end");

			}
		}
		System.out.println("End loop");

		return result;
	}

}
