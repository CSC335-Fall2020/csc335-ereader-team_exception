import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author 
 *
 */
public class eReaderController {
	private eReaderModel model;
	private HashMap<String,eReaderModel> bookList;
	private String currBook;
	
	/**
	 * 
	 * @param model
	 */
	public eReaderController(eReaderModel model){
		currBook = model.getName();
		this.model = model;
		bookList=new HashMap<String,eReaderModel>();
		bookList.put(currBook, model);
	}
	

	public void addBook(String filename) {
		eReaderModel newBook=new eReaderModel(filename);
		bookList.put(newBook.getName(),newBook);
	}
	
	
	public void openBook(String name) {
		if(bookList.containsKey(name)) {
			this.model=bookList.get(name);
			currBook=model.getName();
		} else {
			System.out.println("This book is not in the list.");
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
	 */
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
	
	
}//End class
