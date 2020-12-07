import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author 
 *
 */

public class eReaderModel implements Serializable {
	private String book;
	private List<String> pages ; 
	private int currentPage=0;//jia change
	private String searchBook;//jia change
	private int searchNumber=0;//jia change
	private String searchCurrent;//jia change
	private List<Integer> bookmarks;//winston change
	private String bookName;
	private static final long serialVersionUID = 42L;

	public void testPrint(int index) {
		//System.out.println(book.substring(index, index + 2));
	}
	
	/**
	 *  Will add number of lines per page and characters per line to constructor.
	 * @param filename
	 */
	public eReaderModel(String filename) {
		
		book = "";
		convertFile(filename);
		pages = getPages(30, 80);
		System.out.println(pages.size());
		bookName = filename;
	}
	
	
	public String getName() {
		return bookName;
	}
	
	/**
	 * Purpose: 
	 * @param filename
	 */
	private void convertFile(String filename) {
		BufferedReader fileInput = null;
		try {
            fileInput = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }try {
			while (fileInput.ready()) {
				
			    book += fileInput.readLine() + '\n';
			}
			fileInput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getBook() {
		return book;
	}
	
	/**
	 * Purpose: 
	 * @param pageLength
	 * @param lineLength
	 * @return
	 */
	public List<String> getPages(int pageLength, int lineLength){
		List<String> retval = new ArrayList<String>();
		int lastSpace = 0;
		int start = 0;
		String currLine = "";
		int pageLine = 0;
		for(int i = 0; i < book.length(); i++) {
			if(book.charAt(i) == ' ') {
				lastSpace = i;
			}
			if(i - start >= lineLength) {
				if(lastSpace < start) {
					currLine += book.substring(start, i)+ "-\n";
					pageLine++;
					start = i;
				}else {
				currLine += book.substring(start, lastSpace+1)+ "\n";
				start = lastSpace+1;
				pageLine ++;
				}
			}
			if(book.charAt(i) == '\n') {
				currLine += book.substring(start, i)+ "\n";
				start = i;
				pageLine ++;
			}
			if(pageLine == pageLength) {
				retval.add(currLine);
				pageLine = 0;
				currLine = "";
				
			}
			
		}
		
		currLine +=book.substring(start)+ "\n";
		retval.add(currLine);
		return retval;
	}
	
	/**
	 * Purpose: Accessor that returns the book. 
	 * @return
	 */
	public String getText() {
		return this.book;
	}
	
	
	//jia change
	/** Purpose:A thoughtful little function that displays the 
	 * percentage of the currently viewed pages. print
	 * he can see <9/100>.
	 * @return String with current page / max page
	 */
	public String percent() {
		return "< "+(currentPage+1)+" / "+this.getBookSize()+" >";
	}
	
	
	//jia change
	public String getCurrPage() {
		if(pages==null || pages.size()==0) {
			System.out.println("no book content");
			return null;
		}
		return pages.get(currentPage);
	}
	/**
	 * Purpose: Starts book from first page
	 * @return The first page of the book
	 */
	public String startBook() {
		if(pages == null || pages.size()==0) {
			System.out.println("no book content");
			return null;
		}
		currentPage = 0;
		return pages.get(currentPage);
	}
	
	//jia change
	public String getNext() {
		currentPage++;
		if(pages==null || pages.size()==0 || currentPage>=pages.size()) {
			System.out.println("no book content or no more page");
			currentPage--;
			return null;
		}
		return pages.get(currentPage);
	}
	
	
	
	//jia change
	public String getPrevious() {
		currentPage--;
		if(pages==null || pages.size()==0 || currentPage<0) {
			System.out.println("no book content or first page now");
			currentPage++;
			return null;
		}
		return pages.get(currentPage);
	}
	
	
	//jia change
	/**
	 * Purpose: returns current page number
	 * @return an int of the current page
	 */
	public int getPageNumber() {
		return currentPage;
	}
	/**
	 * Purpose: returns the total number of pages in the current book
	 * @return an int of the total number of pages
	 */
	public int getBookSize() {

		return pages.size();
	}
	
	
	//jia change
	public int search(String input) {
		if(searchBook == null || !searchCurrent.equals(input)) {
			searchNumber = 0;
			searchBook = book;
		}
	
		searchCurrent = input;
		for (int i = 0; i < searchBook.length(); i++) {
			     if (i <= searchBook.length() - input.length()) {
			         if (searchBook.indexOf(input, i) >= 0) {
			             i = searchBook.indexOf(input, i);
			             searchBook=searchBook.substring(i+input.length());
			             searchNumber+=i;
			          //   If the position of the last word of return is found, 
			             searchBook = searchBook.substring(i+input.length());
			             if(searchNumber == 0) {//winston change
			            	 searchNumber += i;
			             } else {//winston change
				             searchNumber += i;
				             searchNumber += input.length();
			             }
			         //If the position of the last word of return is found, 
			         //the position of the first word is searchNumber-input.length();
			             return searchNumber;
			         }
			     }
			 }
		//
	
		searchBook = book.substring(0);
		searchNumber = 0;
		return -1;
	}

	//winston change
	public void addBookmark() {
		int index = search(pages.get(currentPage));
		bookmarks.add(index);
	}
	

	

	/**
	 * Purpose: Retrieves all of the file names in a directory. 
	 * These file names will be used to update book info so 
	 * whenever changes are made the update automatically. This 
	 * function will do this one one of two files: books or book_images.
	 * This allows the function to be flexible and allowed us to avoid
	 * writing another function that does something very similar. 
	 * 
	 * @param bookName string name of the file directory to be read in.
	 * 
	 */
	public List<String> getFileNames(String bookName) {
		File  folder = new File(bookName); // Declare folder variable
		String contents[] = folder.list(); 
		List<String> fileNames  = Arrays.asList(contents);
		return fileNames;
	}
	
	
	
	
	
	
} // End class
