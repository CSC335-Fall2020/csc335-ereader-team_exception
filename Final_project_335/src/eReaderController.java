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
	

	public List<String> getPages(int pageLength, int lineLength) {
		return model.getPages(pageLength, lineLength);
	}
	
	public void addBookmark() {
		model.addBookmark();
	}
	
	public String nextPage() {
		return model.getNext();
	}
	
	public String previousPage() {
		return model.getPrevious();
	}

	/**
	 * Purpose:
	 * @return
	 */
	public List<String> getBook() {
		return this.model.getPages(30, 80);
	}	
}//End class
