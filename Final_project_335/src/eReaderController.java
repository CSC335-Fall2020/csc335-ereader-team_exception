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
 * Purpose: Represents the Controller in the MVC architecture. Allows the view to 
 * interact with the model to to gather information. 
 * 
 * @author Ztaylor9000, caseywhitmire, jialiangzhao, David (Winston?), and blsmith86
 *
 */
public class eReaderController {
	private eReaderModel model;
	private List<String> bookList;
	private String currBook;

	/**
	 * Purpose: Controller that instantiates the controller for the e-reader.
	 * 
	 * 
	 * 
	 */


	public eReaderController(){
		initialDeserialize();
	}

	/**
	 * Purpose: Call
	 * @param filename
	 */
	public void addBook(String bookTitle, String filename) {
		model = new eReaderModel(bookTitle, filename);
		bookList.add(bookTitle);
		this.serialize(bookTitle);
		initialSerialize();
	}
	
	public void openBook(String name) {
		if(bookList.contains(name)) {
			this.model = this.deserialize(name);
			System.out.println(model.getName());
			currBook = model.getName();
		} else {
			System.out.println("This book is not in the list.");
		}
	}
	
	public void closeBook() {
		System.out.println("closes book");
		this.serialize(currBook);
	}
	
	/**
	 * When a book is added to the book list serialize the book list
	 * 
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
	 * each time the program is run, check to see if there already exists a save state for the booklist
	 */
	@SuppressWarnings("unchecked")
	public void initialDeserialize() {
		try {
			FileInputStream fileIn = new FileInputStream("Booklist.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			this.bookList = (List<String>) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException f) {
			try {
				this.bookList = new ArrayList<String>();
				File bookListFile = new File("Booklist.ser");
				bookListFile.createNewFile();
				try {
					FileOutputStream fileOut = new FileOutputStream("Booklist.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(this.bookList);
					out.close();
					fileOut.close();
				} catch (IOException i) {
					i.printStackTrace();
				}
			}catch(IOException g) {
				g.printStackTrace();
			}
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
	}
	
	/**
	 * when they close or when they add a book, serialize
	 * BE SURE TO RUN THIS AFTER THE BOOK AND TITLE ARE ADDED TO BOOKLIST
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
	 * when they open a book, deserialize that book
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
	 * Purpose:
	 * @return
	 */
	public String currBook() {
		return this.currBook;
	}

	/**
	 * 
	 * @param pageLength
	 * @param lineLength
	 * @return
	**/
	public List<String> getPages(int pageLength, int lineLength) {
		return model.getPages(pageLength, lineLength);
	}
	
	/**
	 * Purpose: returns current page number
	 * @return an int of the models current page number
	 */
	public int pageNumber() {
		return model.getPageNumber();
	}
	
	/**
	 * Purpose:
	 * @return
	 */
	public int bookSize() {
		return model.getBookSize();
	}
	
	/**
	 * Purpose:
	 */
	public void addBookmark() {
		model.addBookmark();
	}
	
	/**
	 * Purpose: returns the next page of the current book
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
	 * Purpose: returns the previous page of the current book if it exists
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
	 * Purpose: returns the page that the model is currently on
	 * @return
	 */
	public String getCurrPage() {
		return model.getCurrPage();
	}
	
	/**
	 * Purpose: returns the first page of the current book
	 * @return a string representing the page
	 */
	public String startBook() {
		return model.startBook();
	}
	
	/**
	 * Purpose: tester
	 * @return
	 */
	public List<String> getBook() {
		return this.model.getPages(30, 80);
	}	

	/*
	 * 
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
	 * 
	 * @return int value representing the page number of the search string
	 */
	public int search(String str) {
		return this.model.search(str);
	}
	
}//End class
