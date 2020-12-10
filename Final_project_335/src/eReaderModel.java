import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
<<<<<<< HEAD
 * AUTHOR: @author ztaylor, @author blsmith86, @author caseywhitmire @author wczeng, @author jialiangzhao
 * FILE: eReaderModel.java
 * ASSIGNMENT: Final Project
 * Course: CSC 335; Fall 2020
 * PURPOSE: This class represents the Model of the MVC architecture. It has methods to 
 * read in the book from a folder contained within the project. It also contains 
 * methods to get page info and create a save state.
=======
 * Purpose: This class represents the Model of the MVC 
 * architecture. It has methods to read in the book from
 * a folder contained within the project. It also contains 
 * methods to get page info and create a save state. 
 * 
 * @author Ztaylor9000, caseywhitmire, jialiangzhao, David (Winston?), and blsmith86
>>>>>>> refs/remotes/origin/winston
 */

public class eReaderModel implements Serializable {
	private String        book  ;
	private List<String>  pages ; 
	private int           currentPage;
	private String        searchBook;
	private int           searchNumber;
	private String        searchCurrent;
	private List<Integer> bookmarks = new ArrayList<Integer>();
	private String bookName;
	private static final long serialVersionUID = 42L;
	private double progress;

	/**
	 * Purpose: creates the model of each book with a title and a file address
<<<<<<< HEAD
	 * 
=======
>>>>>>> refs/remotes/origin/winston
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
            try {
				fileInput = new BufferedReader( new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

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
	 * @param lineLength length of the line to slice. 
<<<<<<< HEAD
=======
	 * 
>>>>>>> refs/remotes/origin/winston
	 * @return retval ArrayList that holds the book spit into pages. 
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
	 * @return current page of the book stored in the ArrayList.
	 */
	public String getCurrPage() {
		return pages.get(currentPage);
	}
	
	/**
	 * Purpose: Gets the next page of the book. 
	 * 
	 * @return string that represents the next page in the book. 
	 */
	public String getNext() {
		currentPage++;
		if(currentPage >= pages.size()) {
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
		if(currentPage <0) {
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
				         
				             if(searchNumber == 0) {
				            	 searchNumber += i;
				             } else {
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
		if(!this.bookmarks.contains(currentPage)) {
			this.bookmarks.add(currentPage);
		}
	}

	/**
<<<<<<< HEAD
	 * Purpose: Removes a bookmark from the bookmark list.
=======
	 * Purpose: Removes a bookmark.
>>>>>>> refs/remotes/origin/winston
	 */
	
	public void removeBookmark() {
		if(this.bookmarks.contains(currentPage)) {
			this.bookmarks.remove(currentPage);
		}
	}
	
	/**
<<<<<<< HEAD
	 * Purpose: Returns a list of bookmarks.
	 * 
	 * @return an integer list representing the pages? each bookmark was added
=======
	 * Purpose: Returns a sorted list containing all bookmarked pages.
	 * 
	 * @return this.bookmarks sorted list of bookmarked page numbers
>>>>>>> refs/remotes/origin/winston
	 */
	public List<Integer> getBookmarks() {
		Collections.sort(this.bookmarks);
		return this.bookmarks;
	}
	
	/**
<<<<<<< HEAD
	 * Purpose: Returns the users progress through a book
	 * 
	 * @return a double representing the users progress
=======
	 * Purpose: Returns the user's progress.
	 * 
	 * @return progress decimal value between 0 and 1 representing
	 * how much of the book has been read
>>>>>>> refs/remotes/origin/winston
	 */
	public double getProgress() {
		return progress;
	}
	
	/**
<<<<<<< HEAD
	 * Purpose: Allows the ability to change a given page number.
	 * 
	 * @param pageNumber an integer
=======
	 * Purpose: Jumps to a specified page in the book.
>>>>>>> refs/remotes/origin/winston
	 */
	public void setPage(int pageNumber) {
		if(pageNumber < pages.size()) {
			System.out.println("In the model");
			this.currentPage = pageNumber;
		}
	}

} // End class