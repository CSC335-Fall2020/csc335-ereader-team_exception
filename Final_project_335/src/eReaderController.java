import java.util.TreeMap;
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
 * 
 * @author 
 *
 */
public class eReaderController {
	private eReaderModel model;
	private TreeMap<String, eReaderModel> bookList;
	private String currBook;
	private String title;
	
	/**
	 * 
	 * @param model
	 */
	public eReaderController(eReaderModel model){
		currBook = model.getName();
		this.model = model;
		bookList = new TreeMap<String,eReaderModel>();
		bookList.put(currBook, model);
	}
	


	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addBook(String filename) {
		eReaderModel newBook = new eReaderModel(filename);
		bookList.put(title,newBook);
	}
	
	public void openBook(String name) {
		if(bookList.containsKey(name)) {
			this.model = bookList.get(name);
			currBook = model.getName();
		} else {
			System.out.println("This book is not in the list.");
		}
	}
	
	/**
	 * when the stage first launches, serialize each book
	 * 
	 */
	public void initialSerialize() {
		Set<String> unsorted = bookList.keySet();
		List<String> sorted = new ArrayList<String>();
		for(String s : unsorted) {
			sorted.add(s);
		}
		Collections.sort(sorted);
		try {
			FileOutputStream fileOut = new FileOutputStream("/tmp/Booklist.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.bookList);
			
			for(String title : sorted) {
				fileOut = new FileOutputStream("/tmp/" + title + ".ser");
				out = new ObjectOutputStream(fileOut);
				out.writeObject(bookList.get(title));
			}
			
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
			FileInputStream fileIn = new FileInputStream("/tmp/Booklist.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			this.bookList = (TreeMap<String, eReaderModel>) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException f) {
			initialSerialize();
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
			FileOutputStream fileOut = new FileOutputStream("/tmp/" + title + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(bookList.get(title));
			out.close();
			fileOut.close();
		} catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	/**
	 * when they open, deserialize
	 */
	public void deserialize(String title) {
		try {
			FileInputStream fileIn = new FileInputStream("/tmp/" + title + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			eReaderModel e = (eReaderModel) in.readObject();
			// have to do something with e
			in.close();
			fileIn.close();
		} catch(IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
	}
	
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
	
	/**
	 * Purpose: Gets the file names for a particular file path.
	 * It will store them in an ArrayList.
	 * 
	 * @return list that contains the file names for a particular directory.
	 */
	public List<String> getFilePath(String fileName) {
		return this.model.getFileNames(fileName);
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
