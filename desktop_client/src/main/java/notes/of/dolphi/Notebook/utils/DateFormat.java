package notes.of.dolphi.Notebook.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormat {

     public String get_date_in_proper_format(String date_of_note){
		
		if(date_of_note != null)
		{
		try
		{
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
		
		Date date = sdf.parse(date_of_note);
		
		System.out.println("HERE: date" + date);

		SimpleDateFormat sdf_new = new SimpleDateFormat("dd-MMM yyyy");
		
		return sdf_new.format(date).replace("-", " ");
		}
		catch(ParseException e)
		{
			System.out.println(e);
		}
		}//end if
		return "";
		
	}

}
