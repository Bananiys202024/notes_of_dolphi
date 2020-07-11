package notes.of.dolphi.Notebook.utils;


public class Checker {

	public boolean is_values_empty(String text, String text2, String text3) {
		
		if(text.isEmpty() || text2.isEmpty() || text3.isEmpty())
		return true;
		
		if(text.equals("") || text2.equals("") || text3.equals(""))
		return true;
			
		if(text.replaceAll(" ", "").equals("") || text2.replaceAll(" ", "").equals("") || text3.replaceAll(" ", "").equals(""))
		return true;
	
		return false;
	}


}
