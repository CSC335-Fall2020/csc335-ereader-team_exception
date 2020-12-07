import java.util.List;

/**
 * 
 * @author 
 *
 */
public class eReaderController {
	private eReaderModel model;

	/**
	 * 
	 * @param model
	 */
	public eReaderController(eReaderModel model){
		this.model = model;
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
	 * Purpose:
	 * @return
	 */
	public List<String> getBook() {
		return this.model.getPages(30, 80);
	}	
	
	/**
	 * Purpose: Calls the model to get a List of 
	 * the file names for all of the books. 
	 * 
	 * @return list that contains all book names.
	 */
//	public List<String> getBookFileNames(){
//		return this.model.getBookFileNames();
//	}
	
	/**
	 * Purpose: Calls the model to get a List of 
	 * the  image file names for all of the books. : 
	 * 
	 * @return list that contains names of image files.
	 */
//	public List<String>  getImageFileNames(){
//		return this.model.getImageFileNames();
//	}
	
	/**
	 * Purpose: Returns the number of books in the directory.
	 * 
	 * @return number of books in library. 
	 */
//	public int getNumBooks() {
//		return this.model.getNumBooks();
//	}
	
}//End class
