import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
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
public class eReaderGUIView extends Application implements Observer{
	// STRING CONSTANTS
	public static final String DEFAULT_FONT = "Courier New";
	public static final String FONT_ONE     = "Times New Roman";
	public static final String FONT_TWO     = "Cambria";
	
	// INTEGER CONSTANTS
	public static final int    DEFAULT_SIZE = 12;
	public static final int    WIDTH      = 1072;
	public static final int    HEIGHT     = 1448;
	public static final int    BUTTON_DIM = 75  ;
	
	
	// OBJECTS
	private eReaderController controller;
	
	private GridPane          gridPane  ;
	private BorderPane        window    ;
	private eReaderModel      model     ;
	private List<String>      book      ;
	
	// Buttons and Toolbar
	private VBox     toolbarVbox   ;
	private Button   homeButton    ;
	private Button   backButton    ;
	private Button   forwardButton ;
	private Button   settingsButton;
	private Button   searchButton  ;
	
	// Menu stuff and things
	private MenuItem fontTwo;
	private MenuItem fontOne  ;
	private MenuItem sizeEleven   ;
	private MenuItem sizeTwelve   ;
	private Menu     fontStyle    ;
	private Menu     fontMenu     ;
	private Menu     fontSize     ;
	private MenuItem sizeTen      ;
	private MenuBar  menuBar      ;
	private MenuItem fontThree    ;
	
	/**
	 * Purpose: Constructor that instanstiates objects. These can be created
	 * before since the info will be added later. 
	 */
	public eReaderGUIView() {
		this.book        = new ArrayList<String>(     );
		this.window      = new BorderPane(            ); 
		this.gridPane    = new GridPane  (            );
		this.toolbarVbox = new VBox      (            );
		this.fontMenu    = new Menu      ("Format"    );
		this.fontStyle   = new Menu      ("Font Style");
		this.fontSize    = new Menu      ("Size"      );
		this.menuBar	 = new MenuBar   (            );
		this.sizeTen     = new MenuItem  ("10pt"      );
		this.sizeEleven  = new MenuItem  ("11pt"      );
		this.sizeTwelve  = new MenuItem  ("12pt"      );
		this.fontOne     = new MenuItem  (DEFAULT_FONT);
		this.fontTwo     = new MenuItem  (FONT_ONE    );
		this.fontThree 	 = new MenuItem  (FONT_TWO    );
	}
	
	/**
	 * Purpose: 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("E-Mongoose");
		this.model      = new eReaderModel("warOfTheWorlds.txt");  // String arg needs to be updated to whatever 
		  this.controller = new eReaderController(model    ); //  the user chooses
		  this.book        = this.controller.getBook(      );
		 
		// Add submenu to font styles (i.e. font type)
		this.fontStyle.getItems().add(this.fontOne  );
		this.fontStyle.getItems().add(this.fontTwo  );
		this.fontStyle.getItems().add(this.fontThree);
		
		// Add submenu to fontsize
		this.fontSize.getItems().add(this.sizeTen   );
		this.fontSize.getItems().add(this.sizeEleven);
		this.fontSize.getItems().add(this.sizeTwelve);
		
		// Add font style and size to the font menu
		this.fontMenu.getItems().add(this.fontStyle);
		this.fontMenu.getItems().add(this.fontSize);
		
		// Add font menu to the menubar
		this.menuBar.getMenus().add(this.fontMenu);
		
		// Add menubar to the window
		this.window.setTop(this.menuBar);                         
		
	
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
       
        // Set image width and height for buttons
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
		
		toolbarVbox.getChildren().add(toolbarHbox);   // Add Hbox to Vbox
		this.gridPane.add(toolbarVbox, 0, 0);        // Add Vbox to gridpane
		this.gridPane.setAlignment(Pos.TOP_CENTER); // Center gridpane
		this.window.setCenter(this.gridPane);      // Set gridpane to top
		
		// Events for buttons
		this.homeButton    .setOnAction(e-> { System.out.println("Home Button");});
		this.backButton    .setOnAction(e-> { 
			//resets gridpane with previous page as text
			String page = controller.previousPage();
			Text text = new Text();
			VBox vbox = new VBox();
			text.setFont(Font.font (DEFAULT_FONT, DEFAULT_SIZE));
			text.setText(page);
			vbox.getChildren().add(text);
			GridPane newPage = new GridPane();
			newPage.add(toolbarVbox, 0, 0);        // Add Vbox to gridpane
			newPage.setAlignment(Pos.TOP_CENTER); // Center gridpane
			newPage.add(vbox, 0, 1);
			this.gridPane = newPage;
			this.window.setCenter(this.gridPane);      // Set gridpane to top
			
		});
		this.forwardButton .setOnAction(e-> {
			//resets gridpane with next page as text
			String page = controller.nextPage();
			Text text = new Text();
			VBox vbox = new VBox();
			text.setFont(Font.font (DEFAULT_FONT, DEFAULT_SIZE));
			text.setText(page);
			vbox.getChildren().add(text);
			GridPane newPage = new GridPane();
			newPage.add(toolbarVbox, 0, 0);        // Add Vbox to gridpane
			newPage.setAlignment(Pos.TOP_CENTER); // Center gridpane
			newPage.add(vbox, 0, 1);
			this.gridPane = newPage;
			this.window.setCenter(this.gridPane);      // Set gridpane to top
		});
		this.settingsButton.setOnAction(e-> { System.out.println("Settings"   );}); // Possibly remove later
		
		// Events for font style
		this.fontOne   .setOnAction(e-> { System.out.println("Courier New"    );});
		this.fontTwo   .setOnAction(e-> { System.out.println("Times New Roman");});
		this.fontThree .setOnAction(e-> { System.out.println("Cambria"        );});
		
		// Events for font size
		this.sizeTen    .setOnAction(e-> { System.out.println("10pt");});
		this.sizeEleven .setOnAction(e-> { System.out.println("11pt");});
		this.sizeTwelve .setOnAction(e-> { System.out.println("12pt");});
	
	       
		this.searchButton  .setOnAction(e-> {
											 // Add text to GUI
											 for(String line: this.book) {
												 Text text = new Text();
												 VBox vbox = new VBox();
												 text.setFont(Font.font (DEFAULT_FONT, DEFAULT_SIZE));
												 text.setText(line);
												 vbox.getChildren().add(text);
												 this.gridPane.add(vbox, 0, 1);
											 }
											
		}); // End search button event handler
		
		 
		
		// Set the scene
		Scene scene = new Scene(this.window, WIDTH, HEIGHT);
		stage.setScene(scene);
		stage.show();  // Show the stage 
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	// Function to modify the Gridpane that will display the books
	
	
	// Function to display the actual text from the book
	
	// Function to modify the screen brightness
	

} // End class
