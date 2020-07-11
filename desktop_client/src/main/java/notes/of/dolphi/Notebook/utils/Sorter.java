package notes.of.dolphi.Notebook.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.notes_of_dolphi.model.Note;

public class Sorter {
	
	public static List<Note> reverse_list(List<Note> notes) {	
		Collections.reverse(notes); 
		return notes;
	}

}
