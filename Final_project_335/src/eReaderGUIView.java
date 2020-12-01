import java.io.FileInputStream;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Purpose: Creates the GUI for the E-Reader program. This class represents the View. 
 * 
 * @author 
 *
 */
public class eReaderGUIView  extends Application{
	public static final int WIDTH      = 1072;
	public static final int HEIGHT     = 1448;
	public static final int BUTTON_DIM = 75  ;
	
	// OBJECTS
	private eReaderController controller;
	private BorderPane        window    ;
	private GridPane          gridPane  ;
	private eReaderModel      model     ;
	private GridPane          deviceName;
	private GridPane   		  text      ;
	private VBox			  textBox   ;
	
	// Buttons and Toolbar
	private VBox     toolbarVbox   ;
	private Button   homeButton    ;
	private Button   backButton    ;
	private Button   forwardButton ;
	private Button   settingsButton;
	private Button   searchButton  ;
	
	// Menu stuff
	private Menu     fontSizeMenu;
	private MenuItem font        ;
	private MenuItem fontSize	 ;
	private MenuBar  menuBar ;

	/**
	 * Purpose: Constructor that instanstiates objects. These can be created
	 * before since the info will be added later. 
	 */
	public eReaderGUIView() {
		this.window       = new BorderPane  (); 
		this.model        = new eReaderModel(null);
		this.controller   = new eReaderController(model);
		this.gridPane     = new GridPane();
		this.toolbarVbox  = new VBox    ();
		this.text         = new GridPane();   
		this.deviceName   = new GridPane(); 
		this.textBox      = new VBox    ();
		this.fontSizeMenu = new Menu("Format");
		this.font         = new MenuItem("Font Style");
		this.fontSize     = new MenuItem("Size");
		this.menuBar	  = new MenuBar();
	}
	
	/**
	 * Purpose: 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("E-Mongoose");

		
		// Add font menuItems to the menu
		this.fontSizeMenu.getItems().add(this.font);
		this.fontSizeMenu.getItems().add(this.fontSize);
		
		
		// Add font menu to the menubar
		this.menuBar.getMenus().add(this.fontSizeMenu);
		window.setTop(this.menuBar);
		
	
		// Read the image data from the files
		FileInputStream input1 = new FileInputStream("button_images/homeButton.png"    );
		FileInputStream input2 = new FileInputStream("button_images/forwardButton.png" );
		FileInputStream input3 = new FileInputStream("button_images/backButton.png"    );
		FileInputStream input4 = new FileInputStream("button_images/settingsButton.png");
		FileInputStream input5 = new FileInputStream("button_images/searchButton.png"  );
		
		Image image1 = new Image(input1);
		Image image2 = new Image(input2);
		Image image3 = new Image(input3);
		Image image4 = new Image(input4);
		Image image5 = new Image(input5);
		
        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);
        ImageView imageView3 = new ImageView(image3);
        ImageView imageView4 = new ImageView(image4);
        ImageView imageView5 = new ImageView(image5);
       
        // Set image width and height 
        imageView1.setFitWidth (BUTTON_DIM);
        imageView1.setFitHeight(BUTTON_DIM);
        
        imageView2.setFitWidth (BUTTON_DIM);
        imageView2.setFitHeight(BUTTON_DIM);
        
        imageView3.setFitWidth (BUTTON_DIM);
        imageView3.setFitHeight(BUTTON_DIM);
        
        imageView4.setFitWidth (BUTTON_DIM);
        imageView4.setFitHeight(BUTTON_DIM);
        
        imageView5.setFitWidth (BUTTON_DIM);
        imageView5.setFitHeight(BUTTON_DIM);
        
        // Create the buttons with images
        this.homeButton     = new Button("", imageView1);
        this.forwardButton  = new Button("", imageView2);
        this.backButton     = new Button("", imageView3);
		this.settingsButton = new Button("", imageView4);
		this.searchButton   = new Button("", imageView5);
		
		// Add buttons to the toolbar Hbox
		HBox toolbarHbox = new HBox(this.homeButton, this.forwardButton, this.backButton, 
				                    this.settingsButton, this.searchButton);
		
		// Button Spacing
		toolbarHbox.setSpacing(50);
		
		toolbarVbox.getChildren().add(toolbarHbox);   // Add the Hbox to Vbox
		this.gridPane.add(toolbarVbox, 0, 0);        // Add Vbox to gridpane
		this.gridPane.setAlignment(Pos.TOP_CENTER); // Center gridpane
		this.window.setCenter(this.gridPane);      // Set gridpane to top
		
		
		
		
		// Events for buttons
		this.homeButton    .setOnAction(e-> { System.out.println("Home Button");});
		this.backButton    .setOnAction(e-> { System.out.println("Back"       );});
		this.forwardButton .setOnAction(e-> { System.out.println("Forward"    );});
		this.settingsButton.setOnAction(e-> { System.out.println("Settings"   );});
		this.searchButton  .setOnAction(e-> { System.out.println("Search"     );});
		
		// Set the scene
		Scene scene = new Scene(this.window, WIDTH, HEIGHT);
		
		stage.setScene(scene);

		stage.show();  // Show the stage 
	}

	// Function to modify the Gridpane that will display the books
	
	
	// Function to display the actual text from the book
	
	// Function to modify the screen brightness
	

} // End class
