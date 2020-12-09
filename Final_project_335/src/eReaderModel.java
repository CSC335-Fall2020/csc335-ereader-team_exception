import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Purpose: This class represents the Model of the MVC 
 * architecture. It has methods to read in the book from
 * a folder contained within the project. It also contains 
 * methods to get page info and create a save state. 
 * 
 * @author Ztaylor9000, caseywhitmire, jialiangzhao, David (Winston?), and blsmith86
 *
 */

public class eReaderModel implements Serializable {
	private String        book  ;
	private List<String>  pages ; 
	private int           currentPage;
	private String        searchBook;
	private int           searchNumber;
	private String        searchCurrent;
	private List<Integer> bookmarks;
	private String bookName;
	private static final long serialVersionUID = 42L;
	private double progress;

	/**
	 *  creates the model of each book with a title and a file address
	 * @param filename is the file address for the text file of the book
	 * @param bookName is the title for this book
	 */
	public eReaderModel(String bookName, String filename) {
		currentPage = 0;
		book = "";
		currentPage  = 0;
		searchNumber = 0;
		convertFile(filename);
		pages = getPages(30, 80);
		this.bookName = bookName;
	}
	
	/**
	 * Purpose: Getter that returns the name of the book to the controller.
	 * 
	 * @return bookName string that represents the name of the book. 
	 */
	public String getName() {
		return bookName;
	}
	
	/**
	 * Purpose: Opens a book and reads it in. The book will be read
	 * into a string while concatenating newline characters where
	 * appropriate. 
	 * 
	 * @param filename name of file to be read in. 
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
			e.printStackTrace();
		}
	}
	
	/**
	 * Purpose: Gets the specific page in the book. 
	 * 
	 * @param pageLength length of the page to slice out
	 * 
	 * @param lineLength length of the line to slice. 
	 * 
	 * @return retval ArrayList that holds the book spit into
	 * pages. 
	 */
	public List<String> getPages(int pageLength, int lineLength){
		List<String> retval = new ArrayList<String>();
		int lastSpace   = 0;
		int start       = 0;
		String currLine = "";
		int pageLine    = 0;
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
	 * Purpose: Gets the current page of the book.
	 * 
	 * @return current page of the book stored in the
	 * ArrayList.
	 */
	public String getCurrPage() {
		if(pages == null || pages.size()== 0) {
			System.out.println("no book content");
			return null;
		}
		return pages.get(currentPage);
	}
	

	/**
	 * Purpose: Gets the next page of the book. 
	 * 
	 * @return string that represets the next page in the book. 
	 */
	public String getNext() {
		currentPage++;
		if(pages == null || pages.size() == 0 || currentPage >= pages.size()) {
			System.out.println("no book content or no more page");
			currentPage--;
			return null;
		}
		progress = (double)currentPage/pages.size();
		return pages.get(currentPage);
	}
	
	/**
	 * Purpose: Gets the previous page.
	 * 
	 * @return string that represents the previous page in the book. 
	 */
	public String getPrevious() {
		currentPage--;
		if(pages == null || pages.size() == 0 || currentPage <0) {
			System.out.println("no book content or first page now");
			currentPage++;
			return null;
		}
		progress = (double)currentPage/pages.size();
		return pages.get(currentPage);
	}
	
	/**
	 * Purpose: returns current page number.
	 * 
	 * @return an int of the current page
	 */
	public int getPageNumber() {
		return currentPage;
	}
	
	/**
	 * Purpose: Returns the total number of pages in the current book.
	 * 
	 * @return an int of the total number of pages
	 */
	public int getBookSize() {
		return pages.size();
	}
	
	/**
	 * Purpose: Searches for a particular word in the book. It will 
	 * find every instance of the word in the book and go to the
	 * next instance when called. 
	 * 
	 * @param input string to search for 
	 * 
	 * @return index of string if found, -1 if not found. 
	 */
	public int search(String input) {
		if(pages.isEmpty()) {
			return -1;
		}else {

			if(searchBook == null || !searchCurrent.equals(input)) {
				searchNumber = 0;
				searchBook = book;
			}
			
			searchCurrent = input;
			for (int i = 0; i < searchBook.length(); i++) {
				     if (i < searchBook.length() - input.length()) {
				         if (searchBook.indexOf(input, i) >= 0) {
				             i = searchBook.indexOf(input, i);
				          //   If the position of the last word of return is found, 
				             searchBook = searchBook.substring(i + input.length());
				             searchNumber += i;
				         
				             if(searchNumber == 0) {//winston change
				            	 searchNumber += i;
				             } else {//winston change
					             searchNumber += i;
					             searchNumber += input.length();
					             int g = 0;
					             while(searchNumber > 0) {
					            	 if (searchNumber > pages.get(g).length()) {
					            		 searchNumber -=  pages.get(g).length();
					            		 g++;
					            	 }else {
					            		 return g;
					            	 }
					             }
				             }
				         }
				     }
				 }
			searchBook = book.substring(0);
			searchNumber = 0;
			return -1;
		}
	}

	/**
	 * Purpose: Adds a bookmark to the page. 
	 */
	public void addBookmark() {
		if(!bookmarks.contains(currentPage)) {
			bookmarks.add(currentPage);
		}
	}

	/**
	 * Purpose: 
	 * @return
	 */
	
	public void removeBookmark() {
		if(bookmarks.contains(currentPage)) {
			bookmarks.remove(currentPage);
		}
	}
	
	public List<Integer> getBookmarks() {
		Collections.sort(bookmarks);
		return bookmarks;
	}
	
	public double getProgress() {
		return progress;
	}
	
	/**
	 * Purpose: 
	 */
	public void setPage(int pageNumber) {
		
		if(pageNumber < pages.size()) {
			System.out.println("In the model");
			this.currentPage = pageNumber;
		}
	}

} // End class