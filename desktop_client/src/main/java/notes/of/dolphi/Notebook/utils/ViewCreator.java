package notes.of.dolphi.Notebook.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.notes_of_dolphi.model.Note;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import notes.of.dolphi.Notebook.controller.AllnotesController;

public class ViewCreator {

	public static GridPane create_blocks_for_display_information(VBox vb, JFXScrollPane scrollPane, GridPane delete_it, List<Note> list)  {

		GridPane inside_grid_pane = new GridPane();
		inside_grid_pane.setId("inside_grid_pane");
		
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(10);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(80);
		ColumnConstraints col3 = new ColumnConstraints();
		col3.setPercentWidth(5);
		ColumnConstraints col4 = new ColumnConstraints();
		col4.setPercentWidth(5);

		//remove all from gridPane
		while(inside_grid_pane.getRowConstraints().size()>0)
		{
			inside_grid_pane.getRowConstraints().remove(0);
		}
	
		while(inside_grid_pane.getColumnConstraints().size()>0)
		{
			inside_grid_pane.getColumnConstraints().remove(0);
		}
		//...
		
		inside_grid_pane.getColumnConstraints().addAll(col1, col2, col3, col4);
		inside_grid_pane.setVgap(3);

		//initialize variables
		int count=0;
		DateFormat date_format = new DateFormat();
		//...
		
		for(Note note:list)
		{	
		
		
			
		//creating elements
		VBox v_box_for_title_and_text = new VBox();
		VBox v_box_for_date = new VBox();

		Label label_main_text = new Label(note.getNote());	  
		Label label_title = new Label(note.getTitle());
		Label label_date = new Label(date_format.get_date_in_proper_format(note.getDate_of_note()));
		JFXButton button_delete = new JFXButton("Delete");
		JFXButton button_edit = new JFXButton("Edit");
		//...
		
		
		//set on action


		button_edit.setOnAction((event)-> {
			try {
				System.out.println("ID_______"+note.getId());
				AllnotesController.edit_note(event, note.getId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
		button_delete.setOnAction((ActionEvent event)-> {
		
			try {
				AllnotesController.delete_note(note.getId());
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		//..
		
		//a little bit of css and layout, you can find more css in file css_for_block_of_note
		button_delete.setMaxWidth(Double.MAX_VALUE);
		button_delete.setMaxHeight(Double.MAX_VALUE);
		button_edit.setMaxWidth(Double.MAX_VALUE);
		button_edit.setMaxHeight(Double.MAX_VALUE);
		
		label_date.setWrapText(true);
		label_title.setWrapText(true);
		label_main_text.setWrapText(true);
		
		label_date.setId("label_date");
		button_delete.setId("button_delete");
		button_edit.setId("button_edit");
	    v_box_for_title_and_text.setId("v_box_for_title_and_main_text");
		v_box_for_date.setId("label_v_box_date");
		label_title.setId("label_title");
	    label_main_text.setId("label_main_text");
		//...
		
	    //combining or adding element inside element
		v_box_for_title_and_text.getChildren().add(label_title);
		v_box_for_title_and_text.getChildren().add(label_main_text);
		v_box_for_date.getChildren().add(label_date);
		 
		inside_grid_pane.add(v_box_for_date, 0, count);
		inside_grid_pane.add(v_box_for_title_and_text, 1, count);
		
		inside_grid_pane.add(button_delete, 2, count);
		inside_grid_pane.add(button_edit, 3, count);
		//....
		
		
		count++; //incerement of variable
		}
	
		return inside_grid_pane;
	}
}
