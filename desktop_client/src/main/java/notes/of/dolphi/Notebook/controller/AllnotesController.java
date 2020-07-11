package notes.of.dolphi.Notebook.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Note;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import notes.of.dolphi.Notebook.jdbc.initializing.JDBC_initializing;
import notes.of.dolphi.Notebook.server.ServerDiary;
import notes.of.dolphi.Notebook.utils.Sorter;
import notes.of.dolphi.Notebook.utils.ViewCreator;

public class AllnotesController implements Initializable {

	@FXML private JFXScrollPane scrollPane;	
	@FXML private VBox vb;
	@FXML private GridPane grid_pane;

	
	 public void initialize(URL arg0, ResourceBundle arg1) 
	{
		 	ServerDiary server = new ServerDiary();
		 	List<Note> list = null;
			try {
				list = server.read();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	 	
		 	list = Sorter.reverse_list(list);
		 	
		 	GridPane vb_generated = ViewCreator.create_blocks_for_display_information(vb, scrollPane, grid_pane, list);
			scrollPane.setContent(vb_generated);
	}
	 
	public void go_to_page_add_note(ActionEvent event) throws IOException
	{
		RouterController router = new RouterController();
		router.go_to_page_add_note(event);
	}
	
	public void logout_and_exit() throws IOException
	{
		RouterController router = new RouterController();
		router.logout_and_exit();
	}

	public static void delete_note(int id) throws ClassNotFoundException, IOException 
	{
		ServerDiary server = new ServerDiary();
		server.delete(id);	
	}

	public static void edit_note(ActionEvent event, int id) throws IOException {
		
		RouterController router = new RouterController();
		router.go_to_edit_page(event, id);	
	}

	
}
