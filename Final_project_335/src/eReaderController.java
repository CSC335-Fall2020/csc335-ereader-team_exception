import java.util.TreeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * AUTHOR: @author ztaylor, @author blsmith86, @author caseywhitmire @author wczeng, @author jialiangzhao
 * FILE: eReaderController.java
 * ASSIGNMENT: Final Project
 * Course: CSC 335; Fall 2020
 * PURPOSE: Represents the Controller in the MVC architecture. Allows the view to 
 * interact with the model to to gather information.
 */

public class eReaderController {
	private eReaderModel model;
	private List<String> bookList;
	private String currBook;

	/**
	 * Purpose: Constructor that instantiates the controller for the e-reader.
	 *  creates the controller by checking for a save state file, if the file 
	 *  exists recreates the state of the controllers booklist
	 */
	public eReaderController() {
		initialDeserialize();
	}

	/**
	 * Purpose: Adds books to the current book list, by first creating a model object
	 * with a given book title and filename. Serializes the model object 
	 * created.
	 * 
	 * @param bookTitle a string representing the title of a book
	 * @param filename a string representing the actual books filename
	 */
	public void addBook(String bookTitle, String filePath) {
		model = new eReaderModel(bookTitle, filePath);
		bookList.add(bookTitle);
		this.serialize(bookTitle);
		initialSerialize();
	}
	
	/**
	 * Purpose: Finds and gets a given book based on the title. If the book does not
	 * exist, the method will print a warning.
	 * 
	 * @param name, a string representing the title of a book.
	 */
	public void openBook(String name) {
		if(bookList.contains(name)) {
			this.model = this.deserialize(name);
			currBook = model.getName();
		} else {
			System.out.println("This book is not in the list.");
		}
	}
	
	/**
	 * Purpose: Serializes the current open book (i.e. closes it).
	 */
	public void closeBook() {
		this.serialize(currBook);
	}
	
	/**
	 * Purpose: This function is only run once, at it is the very first time
	 * this program was run. The booklist is serialized so it never has to
	 * be recreated again. 
	 */
	public void initialSerialize() {
		try {
			FileOutputStream fileOut = new FileOutputStream("Booklist.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.bookList);			
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	/**
	 * Purpose: Each time the program is run, check to see if there already exists a 
	 * save state for the booklist. If the booklist exists, it will be loaded in.
	 * Otherwise a new booklist will be created.
	 */
	@SuppressWarnings("unchecked")
	public void initialDeserialize() {
		try { // read in book list
			FileInputStream fileIn = new FileInputStream("Booklist.ser");
			
			ObjectInputStream in = new ObjectInputStream(fileIn);
			this.bookList = (List<String>) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException f) { // booklist not found
			try {
				this.bookList = new ArrayList<String>(); 
				File bookListFile = new File("Booklist.ser"); // create new booklist
				bookListFile.createNewFile();
				try { // save to output
					FileOutputStream fileOut = new FileOutputStream("Booklist.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(this.bookList);
					out.close();
					fileOut.close();
				} catch (IOException i) {
					i.printStackTrace();
				}
			} catch(IOException g) {
				g.printStackTrace();
			}
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
	}
	
	/**
	 * Purpose: Serializes a book with the specified title.
	 * 
	 * @param title title of book
	 */
	public void serialize(String title) {
		try {
			FileOutputStream fileOut = new FileOutputStream(title + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(model);
			out.close();
			fileOut.close();
		} catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	/**
	 * Purpose: Checks for a serialized file for the specific book title.
	 * if it exists return the eReaderModel otherwise return null.
	 * 
	 * @return the deserialized eReaderModel if it exists
	 */
	public eReaderModel deserialize(String title) {
		try {
			FileInputStream fileIn = new FileInputStream(title + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			eReaderModel e = (eReaderModel) in.readObject();
			in.close();
			fileIn.close();
			return e;
		} catch(IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Purpose: Returns the title of the current book
	 * 
	 * @return returns a string for the title of the book
	 */
	public String currBook() {
		return this.currBook;
	}


	/**
	 * Purpose: Returns current page number
	 * 
	 * @return an int of the models current page number
	 */
	public int pageNumber() {
		return model.getPageNumber();
	}
	
	/**
	 * Purpose: Returns the int value for the number of pages in this model of the book
	 * 
	 * @return returns an int for the number of pages in the book
	 */
	public int bookSize() {
		return model.getBookSize();
	}
	
	/**
	 * Purpose: Adds a bookmark to the model of the book.
	 */
	public void addBookmark() {
		model.addBookmark();
	}
	
	/**
	 * Purpose: Removes a bookmark.
	 */
	public void removeBookmark() {
		model.removeBookmark();
	}
	
	
	/**
	 * Purpose: Returns a sorted list containing all bookmarked pages.
	 * 
	 * @return sorted list of bookmarked page numbers
	 */
	public List<Integer> getBookmarks() {
		return model.getBookmarks();
	}

	/**
	 * Purpose: Returns the next page of the current book
	 * 
	 * @return a string representing the page
	 */
	public String nextPage() {
		String page = model.getNext();
		if(page == null) {
			return("End of Book");
		}
		serialize(currBook);
		return page;
	}
	
	/**
	 * Purpose: Returns the previous page of the current book if it exists
	 * 
	 * @return a string representing the page
	 */
	public String previousPage() {
		String page = model.getPrevious();
		if(page == null) {
			return(model.getCurrPage());
		}
		serialize(currBook);
		return page;
	}
	
	/**
	 * Purpose: Returns the page that the model is currently on
	 * 
	 * @return the string of the current page
	 */
	public String getCurrPage() {
		return model.getCurrPage();
	}
	
	/**
	 * Purpose: Returns the booklist.
	 * 
	 * @return 
	 */
	public List<String> getBookList(){
		Collections.sort(bookList);
		return bookList;
	}
	
	
	/**
	 * Purpose: Calls the search function to get the page number for the
	 * text that user searched for.
	 * 
	 * @param str string the user wants to search for in the book.
	 * @return int value representing the page number of the search string
	 */
	public int search(String str) {
		return this.model.search(str);
	}
	
	
	/**
	 * Purpose: Returns the user's progress.
	 * 
	 * @return decimal between 0 and 1 representing percentage of book read
	 */
	public double getProgress() {
		return this.model.getProgress();
	}
	
	/**
	 * Purpose: Calls Model to flip to the next instance of the page
	 * found by the search function. 
	 * 
	 * @param pageNumber page number determined to have the next
	 * text searched for by user. 
	 */
	public void flipToPage(int pageNumber) {
		this.model.setPage(pageNumber);
	}
	
}//End class
