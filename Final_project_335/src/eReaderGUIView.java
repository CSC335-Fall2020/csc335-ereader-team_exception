import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


/**
 * Purpose: Creates the GUI for the E-Reader program. This class represents the View. 
 * 
 * @author 
 *
 */
public class eReaderGUIView extends Application implements Observer{
	
	// numRows/numCols based on amount of books; should be updated when new books are added
	private int numRows  = 2; 
	private int numCols  = 3;
	private int numBooks = 5; // probably a temp variable, until we have the control worked out.
	
	// STRING CONSTANTS
	public static final String DEFAULT_FONT = "Courier New"    ;
	public static final String FONT_ONE     = "Times New Roman";
	public static final String FONT_TWO     = "Cambria"        ;

	// INTEGER CONSTANTS
	public static final int    DEFAULT_SIZE = 12;
	public static final int    SIZE_TWO   = 11;
	public static final int    SIZE_ONE   = 10;
	public static final int    WIDTH      = 1072;
	public static final int    HEIGHT     = 1448;
	public static final int    BUTTON_DIM = 75  ;
	
	// OBJECTS TO CREATE SCENE
	private eReaderController controller;
	private GridPane          gridPane  ;
	private BorderPane        window    ;
	private eReaderModel      model     ;
	private List<String>      book      ;
	
	// DATA STRUCTURES TO HOLD THE FILE NAMES
	private List<String>	  bookTitlePath ;
	private List<String>      bookImagePath;
	
	// Buttons and Toolbar
	private VBox     toolbarVbox   ;
	private Button   homeButton    ;
	private Button   backButton    ;
	private Button   forwardButton ;
	private Button   searchButton  ;
	private List <Button> buttonList; 
	
	// Menu stuff and things
	private MenuItem fontTwo;
	private MenuItem fontOne  ;
	private MenuItem sizeEleven   ;
	private MenuItem sizeTwelve   ;
	private Menu     fontStyle    ;
	private Menu     fontMenu     ;
	private Menu     fontSizeMenu ;
	private MenuItem sizeTen      ;
	private MenuBar  menuBar      ;
	private MenuItem fontThree    ;
	private GridPane newPage;
	private String   fontType;
	private int 	 fontSize;
	
	
	
	/**
	 * Purpose: Constructor that instanstiates objects. These can be created
	 * before since the info will be added later. 
	 */
	public eReaderGUIView() {
		this.book           = new ArrayList<String>(     );
		this.bookTitlePath  = new ArrayList<String>(     );
		this.bookImagePath  = new ArrayList<String>(     );
		this.buttonList     = new ArrayList<Button>(     );
		this.window         = new BorderPane(            ); 
		this.gridPane       = new GridPane  (            );
		this.toolbarVbox    = new VBox      (            );
		this.fontMenu       = new Menu      ("Format"    );
		this.fontStyle      = new Menu      ("Font Style");
		this.fontSizeMenu   = new Menu      ("Size"      );
		this.menuBar	    = new MenuBar   (            );
		this.sizeTen        = new MenuItem  ("10pt"      );
		this.sizeEleven     = new MenuItem  ("11pt"      );
		this.sizeTwelve     = new MenuItem  ("12pt"      );
		this.fontOne        = new MenuItem  (DEFAULT_FONT);
		this.fontTwo        = new MenuItem  (FONT_ONE    );
		this.fontThree 	    = new MenuItem  (FONT_TWO    );
		this.newPage        = new GridPane  (            );
		this.fontType       = DEFAULT_FONT;
		this.fontSize       = DEFAULT_SIZE;
		
		
	}
	
	/**
	 * Purpose: 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("E-Mongoose");
		
		// this will get moved into the main menu button for selecting a new book
//		this.model      = new eReaderModel("books/warOfTheWorlds.txt");  // Add to home button and allow the user to click to choose a book.
//		this.controller = new eReaderController(model    );      
//		this.book        = this.controller.getBook(      );
//		
		
		
		
		// Add submenu to font styles (i.e. font type)
		this.fontStyle.getItems().add(this.fontOne  );
		this.fontStyle.getItems().add(this.fontTwo  );
		this.fontStyle.getItems().add(this.fontThree);
		
		// Add submenu to font size
		this.fontSizeMenu.getItems().add(this.sizeTen   );
		this.fontSizeMenu.getItems().add(this.sizeEleven);
		this.fontSizeMenu.getItems().add(this.sizeTwelve);
		
		// Add font style and size to the font menu
		this.fontMenu.getItems().add(this.fontStyle   );
		this.fontMenu.getItems().add(this.fontSizeMenu);
		
		// Add font menu to the menubar
		this.menuBar.getMenus().add(this.fontMenu);
		
		// Add menubar to the window
		this.window.setTop(this.menuBar);                         
		
	
		// Read the image data from the files
		FileInputStream input1 = new FileInputStream("button_images/homeButton.png"   );
		FileInputStream input2 = new FileInputStream("button_images/forwardButton.png");
		FileInputStream input3 = new FileInputStream("button_images/backButton.png"   );
		FileInputStream input4 = new FileInputStream("button_images/searchButton.png" );
		
		Image image1 = new Image(input1);
		Image image2 = new Image(input2);
		Image image3 = new Image(input3);
		Image image4 = new Image(input4);
		
        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);
        ImageView imageView3 = new ImageView(image3);
        ImageView imageView4 = new ImageView(image4);
       
        // Set image width and height for buttons
        imageView1.setFitWidth (BUTTON_DIM);
        imageView1.setFitHeight(BUTTON_DIM);
        
        imageView2.setFitWidth (BUTTON_DIM);
        imageView2.setFitHeight(BUTTON_DIM);
        
        imageView3.setFitWidth (BUTTON_DIM);
        imageView3.setFitHeight(BUTTON_DIM);
        
        imageView4.setFitWidth (BUTTON_DIM);
        imageView4.setFitHeight(BUTTON_DIM);
        
        // Create the buttons with images
        this.homeButton     = new Button("", imageView1);
        this.forwardButton  = new Button("", imageView2);
        this.backButton     = new Button("", imageView3);
        this.searchButton   = new Button("", imageView4);
		
		// Add buttons to the toolbar Hbox
		HBox toolbarHbox = new HBox(this.homeButton, this.forwardButton, this.backButton, 
				                    this.searchButton);
		
		// Button Spacing
		toolbarHbox.setSpacing(50);
		
		this.toolbarVbox.getChildren().add(toolbarHbox); // Add Hbox to Vbox
		this.gridPane.add(toolbarVbox, 0, 0);           // Add Vbox to gridpane
		this.gridPane.setAlignment(Pos.TOP_CENTER);    // Center gridpane
		this.window.setCenter(this.gridPane);         // Set gridpane to top
		
		////////////////////////////////////////////////////////////////////////////////
		// EVENTS																	  //
		////////////////////////////////////////////////////////////////////////////////
		
		this.homeButton    .setOnAction(e-> {  menuStart();
		
				
		
		});
							
		
		
		this.backButton    .setOnAction(e-> { setText(this.fontType, this.fontSize, true);});  
		this.forwardButton .setOnAction(e-> { setText(this.fontType, this.fontSize, true);});
		
		// Events for font style. Sets each fontstyle to a particular type.
		this.fontOne   .setOnAction(e-> { setText(DEFAULT_FONT, this.fontSize, false);});
		this.fontTwo   .setOnAction(e-> { setText(FONT_ONE    , this.fontSize, false);});
		this.fontThree .setOnAction(e-> { setText(FONT_TWO    , this.fontSize, false);});
		
		// Events for font size
		this.sizeTen    .setOnAction(e-> { setText(this.fontType, SIZE_ONE    , false);});
		this.sizeEleven .setOnAction(e-> { setText(this.fontType, SIZE_TWO    , false);});
		this.sizeTwelve .setOnAction(e-> { setText(this.fontType, DEFAULT_SIZE, false);});
	
		// COLOR STUFF
		ColorAdjust colorAdjust = new ColorAdjust();
	    colorAdjust.setBrightness(0);
	    this.window.setEffect(colorAdjust);  // Set the color adjust here
	    
	    // Set font to white to simulate night mode.
	    // Figure out what settings the images need and menu items
	    
		 // Set the scene
		Scene scene = new Scene(this.window, WIDTH, HEIGHT);
		
		
		
		
		
		
		stage.setScene(scene);
		stage.show();  // Show the stage 
		
		
	}
	
	/**
	 * Purpose: 
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Purpose:
	 */
	public void menuStart() {
		
		Stage stage = new Stage();
		Group root = new Group();
		BorderPane border = new BorderPane();
		ScrollBar sc = new ScrollBar();
		
		try {
			border.setCenter(addGridPane()); // Calls addGridPane() here
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		Scene scene = new Scene(root, 485, 500);
		stage.setScene(scene);
		root.getChildren().addAll(border,sc);
		
		sc.setLayoutX(scene.getWidth()-sc.getWidth());
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(500);
        sc.setMax(200);
		
        // gives the scroll bar the ability to actually scroll
        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    border.setLayoutY(-new_val.doubleValue());
            }
        });
		
		stage.show();
	}
	
	/**
	 * Purpose:
	 * @return
	 * @throws FileNotFoundException
	 */
	public GridPane addGridPane() throws FileNotFoundException {
		GridPane grid = new GridPane();
		grid.setHgap(30);
		grid.setVgap(30);
		grid.setPadding(new Insets(20, 10, 20, 10));
		
		getFileNames(false); // Get book image file names
		getFileNames(true);  // Get rid of these when I get the stuff from the controller.
		
		// keep track of how many times we loop.
		int count = 0;
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				
				if (count == numBooks) { // stop if count is equal to the amount of books we have
					break;
				}
				
				// Add new picture from book_images folder
				FileInputStream input = new FileInputStream("book_images/"+ this.bookImagePath.get(count)); // Get this from the controller later
				ImageView imageView   = new ImageView(new Image(input));
			       
		        // Set image width and height for buttons
		        imageView.setFitWidth (110);
		        imageView.setFitHeight(150);
		        
		        // create new button for each cover/title (vbox)
				Button button = new Button("", imageView);  
				
				button.setId(this.bookTitlePath.get(count));
				this.buttonList.add(button); // Might be able to remove the data structure. 
				
				// add buttons to grid
				grid.add(button, j, i);
				
				count++;
			}
		}
		getBookId(); // Event handler to get the button id
		return grid;
	}

	/**
	 * Purpose: Event handler that gathers the book name whenever the 
	 * user clicks on a book image. It will gather the id associated 
	 * with that button so that the an instance of the model can be
	 * created later. 
	 * 
	 */
	private void getBookId() {
		for(int i =0; i < this.buttonList.size(); i++) {
			final int val = i;
				this.buttonList.get(i).setOnAction(e-> { 
					String bookChoice = this.buttonList.get(val).getId();
					
					getBook(bookChoice);
					
					
				}); // End lambda event
		}
	}
	
	/**
	 * Purpose: Opens the book up. This has to be called from the lambda function in
	 * order for all information to open the book. 
	 * 
	 * @param bookChoice string representing the user's choice of a book to read. 
	 */
	private void getBook(String bookChoice) {
		// Create an instance of the model and controller
		this.model      = new eReaderModel("books/"+ bookChoice); 
		this.controller = new eReaderController(this.model     ); 
		this.book       = this.controller.getBook(             );
		setText( DEFAULT_FONT, DEFAULT_SIZE, false);
	}

	/**
	 * Purpose: Sets the text field to the new desired text type. The parameter
	 * newFont will be the new text type. It will then display the page of text
	 * on the GUI. If a change is made to the same page (i.e. font style or size
	 * is modified while viewing a page) then only that value that needs to be changed
	 * will change. Otherwise, a new page will be retreived from the controller and
	 * the page will be udpated. 
	 * 
	 * @param newFont new font style to be applied (if applicable).
	 * 
	 * @param newSize new font size
	 * 
	 * @param isNewPage boolean variable that determines if a new page is needed
	 * or the same page. 
	 */
	private void setText(String newFont, int newSize, boolean isNewPage) {
		System.out.println("NewFont "+ newFont+ " New size "+newSize+ " Is new page "+ isNewPage);
		String page = "";
		GridPane newPage = new GridPane();
		Text text        = new Text(    );
		VBox vbox        = new VBox(    );
		this.fontType = newFont;
		this.fontSize = newSize;
		
		if(isNewPage) {
			 page = controller.nextPage();     // Set new page if fwd/back button called this method
		}else {
			 page = controller.getCurrPage(); // Otherwise just get the old page to update style or size
		}
	
		text.setFont(Font.font (this.fontType, this.fontSize));   // Changed font type 
		text.setText(page);
		vbox.getChildren().add(text);
		newPage.add(toolbarVbox, 0, 0);        // Add Vbox to gridpane
		newPage.setAlignment(Pos.TOP_CENTER); // Center gridpane
		newPage.add(vbox, 0, 1);
		this.gridPane = newPage;
		this.window.setCenter(this.gridPane); // Set gridpane to top

	}
	
	/**
	 * Purpose: Retrieves all of the file names in a directory. 
	 * These file names will be used to update book info so 
	 * whenever changes are made the update automatically. This 
	 * function will do this one one of two files: books or book_images.
	 * This allows the function to be flexible and allowed us to avoid
	 * writing another function that does something very similar. 
	 * 
	 * @param isBookFile boolean variable to determine if the
	 * file to be read will be the book or book images.
	 * 
	 */
	private void getFileNames(boolean isBookFile) {
		File folder; // Declare folder variable
		
		if(isBookFile) {
			folder = new File("books");  // Sets directory to books
			this.bookTitlePath.clear();     // Clear ArrayList before new pass
		}else {
			folder = new File("book_images"); // Sets directory to book images
			this.bookImagePath.clear();	 // Clear ArrayList before new pass
		}
		
		// Get an array of file names
		String contents[] = folder.list(); 
		
		for (int i = 0; i < contents.length; i++) {
			if(isBookFile) {
				this.bookTitlePath.add(contents[i]);
			}else {
				this.bookImagePath.add(contents[i]);
			}
		}
	}
	
	
	

} // End class
