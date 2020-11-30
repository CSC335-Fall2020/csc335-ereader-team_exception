import java.io.FileInputStream;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Purpose:
 * 
 * @author 
 *
 */
public class eReaderGUIView  extends Application{
	public static final int WIDTH  = 1072;
	public static final int HEIGHT = 1448;
	
	// OBJECTS
	private eReaderController controller;
	private BorderPane        window;
	private eReaderModel      model; 
	private GridPane          gridpane;
	private VBox toolbarVbox;
	/**
	 * Purpose:
	 */
	public eReaderGUIView() {
		this.window     = new BorderPane  (); 
		this.model      = new eReaderModel();
		this.controller = new eReaderController(model);
		this.gridpane = new GridPane();
	}
	
	/**
	 * Purpose: 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("E-Reader");
		
	
		VBox toolbarVbox = new VBox();
		
		
		Button homeButton     = new Button("Home"); // Get Images for these and add later
		Button backButton     = new Button("Back");
		Button forwardButton  = new Button("Forward");
		Button settingsButton = new Button("Settings");
	
		HBox toolbarHbox = new HBox(homeButton, backButton, forwardButton, settingsButton);
		
		
		toolbarHbox.setSpacing(10);
		
		toolbarVbox.getChildren().add(toolbarHbox);
		this.gridpane.add(toolbarVbox, 0, 0); // Add to gridpane
		
		// Events for buttons
		homeButton.setOnAction    (e-> { System.out.println("Home Button");});
		backButton.setOnAction    (e-> { System.out.println("Back"       );});
		forwardButton.setOnAction (e-> { System.out.println("Forward"    );});
		settingsButton.setOnAction(e-> { System.out.println("Settings"   );});
		
		
		
		
		
		
		
		Scene scene = new Scene(this.gridpane, WIDTH, HEIGHT);
		stage.setScene(scene);

		stage.show();  // Show the stage 
	}

	
	

} // End class
