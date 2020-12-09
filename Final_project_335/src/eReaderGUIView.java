import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


/**
 * Purpose: Creates the GUI for the E-Reader program. This class represents the View. 
 * It creates a main stage with options that allow the user to choose a book, modify,
 * the font size/type, and search for particular words or phrases.
 * 
 * @author Ztaylor9000, caseywhitmire, jialiangzhao, David (Winston?), and blsmith86
 *
 */

public class eReaderGUIView extends Application{
	
	// STRING CONSTANTS
	public static final String DEFAULT_FONT = "Courier New"    ;
	public static final String FONT_ONE     = "Times New Roman";
	public static final String FONT_TWO     = "Cambria"        ;
	
	// INTEGER CONSTANTS
	public static final int    DEFAULT_SIZE = 12  ;
	public static final int    BUTTON_DIM   = 75  ;
	public static final int    NUM_COLS     = 3   ;
	public static final int    SIZE_TWO     = 11  ;
	public static final int    SIZE_ONE     = 10  ;
	public static final int    HEIGHT       = 1448;
	public static final int    WIDTH        = 1072;
	
	// OBJECTS TO CREATE SCENE
	private eReaderController controller;
	private GridPane          gridPane  ;
	private BorderPane        window    ;
	private List<String>      book      ;
	
	// BUTTONS AND TOOLBAR
	private VBox     toolbarVbox   ;
	private Button   homeButton    ;
	private Button   backButton    ;
	private Button   forwardButton ;
	private Button   searchButton  ;
	private List <Button> buttonList; 
	
	// MENU STUFF AND THINGS
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
	
	// PROGRESS BAR
	GridPane progressPane;
	ProgressBar progressBar;
	
	/**
	 * Purpose: Constructor that instanstiates objects. These can be created
	 * before since the info will be added later. 
	 */
	public eReaderGUIView() {
		this.book           = new ArrayList<String>(     );
		this.buttonList     = new ArrayList<Button>(     );
		this.window         = new BorderPane(            ); 
		this.gridPane       = new GridPane  (            );
		this.progressPane   = new GridPane    (          );
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
		this.progressBar    = new ProgressBar(0          );
		this.fontType       = DEFAULT_FONT;
		this.fontSize       = DEFAULT_SIZE;
		
		// load basic books into eReader
		controller = new eReaderController();
		if(controller.getBookList().size() == 0) {
			System.out.println("list was empty");
			controller.addBook("Alice in Wonderland", "books/aliceInWonderland.txt");
			controller.addBook("War Of The Worlds", "books/warOfTheWorlds.txt");
			controller.addBook("Dracula", "books/dracula.txt");
			controller.addBook("Roswell Report", "books/roswellReport.txt");
			controller.addBook("Wizard of Oz", "books/wizardOfOz.txt");
			controller.addBook("Roswell Report", "books/roswellReport.txt");
		}
		
	}
	
	/**
	 * Purpose: 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("E-Mongoose");
		  

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
		//this.window.setBottom();
		////////////////////////////////////////////////////////////////////////////////
		// EVENTS																	  //
		////////////////////////////////////////////////////////////////////////////////

		// Events for buttons


		this.homeButton    .setOnAction(e-> {  controller.closeBook();menuStart();
		
				
		
		});
				
		this.backButton    .setOnAction(e-> { controller.previousPage();setText(this.fontType, this.fontSize, true);});  
		this.forwardButton .setOnAction(e-> { controller.nextPage();setText(this.fontType, this.fontSize, true);});

		this.searchButton  .setOnAction(e-> { searchMenu();});
		
		// Events for font style. Sets each fontstyle to a particular type.
		this.fontOne   .setOnAction(e-> { setText(DEFAULT_FONT, this.fontSize, false);});
		this.fontTwo   .setOnAction(e-> { setText(FONT_ONE    , this.fontSize, false);});
		this.fontThree .setOnAction(e-> { setText(FONT_TWO    , this.fontSize, false);});
		
		// Events for font size
		this.sizeTen    .setOnAction(e-> { setText(this.fontType, SIZE_ONE    , false);});
		this.sizeEleven .setOnAction(e-> { setText(this.fontType, SIZE_TWO    , false);});
		this.sizeTwelve .setOnAction(e-> { setText(this.fontType, DEFAULT_SIZE, false);});
	
	
		
		
		// Add the progress bar 
		HBox hbox = new HBox(this.progressBar);
		
		
		this.progressPane.setAlignment(Pos.CENTER);
		this.progressPane.add(hbox,1,2);
		this.window.setBottom(progressPane);
		
		// Set the progress  // NEEDS TO MOVE TO THE APPROPRIATE PLACE TO UPDATE
		progressBar.setProgress(0.5);
		
		// Set size 
		this.progressBar.setPrefSize(400, 30);
		
		// Set the scene
		Scene scene = new Scene(this.window, WIDTH, HEIGHT);
		
		// load current page of book before showing reader screen.

		stage.setScene(scene);
		stage.show();  // Show the stage 
	}


	
	/**
	 * Purpose: Starts up the stage for the book selection menu. Closes upon
	 * book selection. Adds a scroll functionality as well.
	 * 
	 */
	public void menuStart() {
		
		Stage stage = new Stage();
		stage.setTitle("Main menu");
		Group root  = new Group();
		BorderPane border = new BorderPane();
		ScrollBar sc = new ScrollBar();
		
		try {
			border.setCenter(addGridPane(stage)); // Calls addGridPane() here
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		border.setTop(addComboBox(stage));
		
		Scene scene = new Scene(root, 485, 500);
		stage.setScene(scene);
		root.getChildren().addAll(border,sc);
		
		sc.setLayoutX(scene.getWidth()-sc.getWidth());
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(500);
        sc.setMax(1000);
		
        // gives the scroll bar the ability to actually scroll
        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    border.setLayoutY(-new_val.doubleValue());
            }
        });
		
		stage.show();;
	}
	
	/**
	 * Purpose: Creates the gridpane with the books for the stage. It will
	 * also initialize the event handler to allow the user to click on a 
	 * book to make a choice. That will then open the book.
	 * 
	 * @return returns the gridpane to menuStart();
	 * @throws FileNotFoundException throws exception if the file to be
	 * read in for the pictures or books does not exist. 
	 */
	public GridPane addGridPane(Stage stage) throws FileNotFoundException {
		GridPane grid = new GridPane();
		grid.setHgap(30);
		grid.setVgap(30);
		grid.setPadding(new Insets(20, 10, 20, 10));
		FileInputStream input;
		int numRows = 0;
		

		List<String> bookNames  = controller.getBookList();
		//List<String> bookImages = getFileNames("book_images");

		
		// Calculate number of rows for the grid.
		if(bookNames.size() % NUM_COLS == 0) {
			numRows = bookNames.size() / NUM_COLS;
		}else {
			numRows = (bookNames.size() / NUM_COLS) + (bookNames.size() % NUM_COLS); // Add remainder 
		}

		int numBooks = bookNames.size();

		// keep track of how many times we loop.
		int count = 0;
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				
				if (count == numBooks) { // stop if count is equal to the amount of books we have
					break;
				}
				
				
				String str = bookNames.get(count);
				
				VBox vbox = new VBox(); // to contain the cover and the title
				Label title = new Label(str); // sets titles as the label for each button
				title.setMaxWidth(Double.MAX_VALUE);
		        title.setAlignment(Pos.CENTER);
		        title.setTextAlignment(TextAlignment.CENTER);
		        title.setWrapText(true);
		        title.setMaxWidth(100);
		        title.setMaxHeight(50);
				
				input = new FileInputStream("default_book_image/cover.png");
				
				ImageView imageView   = new ImageView(new Image(input, 110, 150, false, false));
				vbox.getChildren().addAll(imageView, title);
			       
				// create new button for each cover/title (vbox)
				Button button = new Button("", vbox);  
				

				button.setId(bookNames.get(count));
				this.buttonList.add(button); 

				// add buttons to grid
				grid.add(button, j, i);
				
				count++;
			}
		}
		getBookId(stage); // Event handler to get the button id
		return grid;
	}
	
	/**
	 * Creates a drop down bar to be added to the top of the menu.
	 * <p>
	 * Adds an input option to a combobox (drop down menu). 
	 * Opens the input window and closes the menu upon clicking on the option.
	 * 
	 * @param menuStage the menu stage; gives the ability to close it when the input window opens
	 * @return a ComboBox; to be added to the menu
	 */
	private ComboBox addComboBox(Stage menuStage) {
		final ComboBox<String> combo = new ComboBox();
		
		combo.getItems().add("Input");
		combo.setOnAction((e) -> {
            inputBookWindow(); // open input window
            menuStage.close(); // close menu
		});
		return combo;
	}
	
	/**
	 * Creates the input window.
	 * <p>
	 * Creates a new stage, with two text fields that allow the user to input the
	 * title of the book they want to add, as well as the books path.
	 */
	private void inputBookWindow() {
		
		Stage stage = new Stage();
		Group root  = new Group();
		BorderPane border = new BorderPane();
		
		Scene scene = new Scene(root, 485, 200);
		stage.setScene(scene);
		
		Label title = new Label("Book title:");
		TextField strTitle = new TextField ();
		HBox getTitle = new HBox(); // contains the text field and label
		getTitle.getChildren().addAll(title, strTitle);
		getTitle.setSpacing(10);
		
		Label path = new Label("File path:");
		TextField strPath = new TextField (); // MUST type exact path
		HBox getPath = new HBox(); // contains the text field and label
		getPath.getChildren().addAll(path, strPath);
		getPath.setSpacing(15);
		
		GridPane grid = new GridPane();
		grid.setVgap(30);
		grid.setPadding(new Insets(20, 10, 20, 10));
		
		// add HBox's to grid
		grid.add(getTitle, 0, 0);
		grid.add(getPath, 0, 1);
		
		Button submit = new Button("Submit");
		submit.setOnAction(e-> { 
			if (strTitle.getText() != null && strPath.getText() != null) {
				
				// add book to controller
				controller.addBook(strTitle.getText(), strPath.getText());
				
				stage.close(); //close current stage
				menuStart(); // reopen menu
			}
			
			;});
		
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e-> { 
			stage.close(); // close current stage
			menuStart(); // reopen menu
			;}); 
		
		HBox buttons = new HBox(); // Add buttons to HBox
		buttons.getChildren().addAll(submit, cancel);
		buttons.setSpacing(20);
		buttons.setPadding(new Insets(20, 10, 20, 10));
		
		border.setCenter(grid);
		border.setBottom(buttons); // add buttons to grid
		border.setAlignment(buttons, Pos.CENTER);
		
		root.getChildren().addAll(border);
		
		
		stage.show();
	}
	
	/**
	 * Purpose: Searches for a match in the book images file. For instance if
	 * everything before the period in the file name matches then the picture
	 * exists in the directory. It will then return the index of that particular
	 * image within the directory. If not, then -1 will be returned. 
	 * 
	 * @param bookImages list of strings that represent the file path to the
	 * book images. 
	 * 
	 * @param searchVal string that matches the filepath in the book.
	 * 
	 * @return index of filename if found. Otherwise -1 will be returned. 
	 */
	private int getBookImageIndex(List<String> bookImages, String searchVal) {
		searchVal =  searchVal +".png";
		for(int i =0; i < bookImages.size(); i++) {
			if(searchVal.equals(bookImages.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * Purpose: Event handler that gathers the book name whenever the 
	 * user clicks on a book image. It will gather the id associated 
	 * with that button so that the an instance of the model can be
	 * created later. 
	 * 
	 * @param stage stage object that will be used to close the menu
	 * when the user chooses a book (i.e. click on a book image).
	 * 
	 */
	private void getBookId(Stage stage) {
		for(int i = 0; i < this.buttonList.size(); i++) {
			final int val = i;
				this.buttonList.get(i).setOnAction(e-> { 
					String bookChoice = this.buttonList.get(val).getId();
					
					getBook(bookChoice);
					stage.close(); // Close the book menu after user makes choice
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

		controller.openBook(bookChoice); 
		setText(DEFAULT_FONT, DEFAULT_SIZE, false              );
	}

	
	/**
	 * Purpose: Creates the stage for the search menu. It will
	 * send a search for a string. 
	 *  
	 */
	private void searchMenu() {
		GridPane textContainer  = new GridPane(    );
		Stage stage             = new Stage(       );
		TextField textField     = new TextField(   ); 
		BorderPane searchWindow = new BorderPane(  );
		Button nextButton       = new Button("Next");
		VBox vbox               = new VBox(        );
		VBox vbox1 				= new VBox(        );
		VBox vbox2              = new VBox(        );
		
		Label label = new Label("Find: "     );
		label.setFont(new Font("Crimson", 18));
		HBox hbox = new HBox(label, textField);
		
		nextButton.setTranslateX(15           );
		vbox.getChildren().add(hbox           );
		vbox1.getChildren().add(nextButton    );
		vbox2.setSpacing(15                   );
		vbox2.getChildren().addAll(vbox, vbox1);
		vbox1.setAlignment(Pos.BOTTOM_CENTER  );
		textContainer.setAlignment(Pos.CENTER );
		textContainer.add(vbox2,0,0           );
		searchWindow.setCenter(textContainer  );
		
		// Use next button to handle the event
		nextButton.setOnAction(e-> {displaySearchResults(textField.getText());});
		
		Scene scene = new Scene(searchWindow, 500, 125);
		stage.setScene(scene);
		stage.show();  // Show the stage 
	}
	
	/**
	 * Purpose: Calls controller to get the search string index.
	 * It will use that to display the page number for the 
	 * desired search string. 
	 * 
	 * @param string to pass to the Controller -> Model to search
	 * for. 
	 */
	private void displaySearchResults(String str) {
		
		int page = this.controller.search(str);
		
		// Print alert to user that search returned no results
		if(page == -1) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Message");
			alert.setHeaderText("Message");
			alert.setContentText("Text not Found.");
			alert.showAndWait();
			alert.close();
		}
	}
	
	
	/**
	 * Purpose: Sets the text field to the new desired text type. The parameter
	 * newFont will be the new text type. It will then display the page of text
	 * on the GUI. If a change is made to the same page (i.e. font style or size
	 * is modified while viewing a page) then only that value that needs to be changed
	 * will change. Otherwise, a new page will be retreived from the controller and
	 * the page will be updated. 
	 * 
	 * @param newFont new font style to be applied (if applicable).
	 * 
	 * @param newSize new font size
	 * 
	 * @param isNewPage boolean variable that determines if a new page is needed
	 * or the same page. 
	 */
	private void setText(String newFont, int newSize, boolean isNewPage) {
		String page = "";
		GridPane newPage = new GridPane();
		Text text        = new Text(    );
		VBox vbox        = new VBox(    );
		this.fontType = newFont;
		this.fontSize = newSize;
		
		page = controller.getCurrPage(); // Otherwise get the current page	
		text.setFont(Font.font (this.fontType, this.fontSize));   
		text.setText(page);
		vbox.getChildren().add(text);
		newPage.add(toolbarVbox, 0, 0);        
		newPage.setAlignment(Pos.TOP_CENTER); 
		newPage.add(vbox, 0, 1);
		this.gridPane = newPage;
		this.window.setCenter(this.gridPane); 
	}
	
	/**
	 * Purpose: Retrieves all of the file names in a directory. 
	 * These file names will be used to update book info so 
	 * whenever changes are made the update automatically. This 
	 * function will do this one one of two files: books or book_images.
	 * This allows the function to be flexible and allowed us to avoid
	 * writing another function that does something very similar. A version
	 * of this method is stored in the View because these methods cannot
	 * be accessed through the controller due to the controller not being
	 * instantiated. The controller is instantiated after the layout for
	 * the book options for the home button is created. 
	 * 
	 * @param bookName string name of the file directory to be read in.
	 * 
	 */
	private List<String> getFileNames(String bookName) {
		File  folder = new File(bookName); // Declare folder variable
		String contents[] = folder.list(); 
		List<String> fileNames  = Arrays.asList(contents);
		return fileNames;
	}
} // End class
