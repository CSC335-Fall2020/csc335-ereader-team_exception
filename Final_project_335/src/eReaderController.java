
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
	 * Purpose:
	 * @return
	 */
	public List<String> getBook() {
		return this.model.getPages(30, 80);
	}
	
	
	
	
	
	
	
	
	
}//End class
