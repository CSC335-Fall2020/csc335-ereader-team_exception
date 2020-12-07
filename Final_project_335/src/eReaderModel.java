import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Observable;

/**
 * 
 * @author 
 *
 */

public class eReaderModel extends Observable{
	private String book;
	private List<String> retval ;// jia change 
	private int currentPage=0;//jia change
	private String searchBook;//jia change
	private int searchNumber=0;//jia change
	private String searchCurrent;//jia change
	private List<Integer> bookmarks;//winston change
	private List<String> bookTitles;
	private List<String> bookImages;
	
	public static void main(String[] args) {
		
//		eReaderModel test = new eReaderModel("testBook.txt");
//		List<String> pages = test.getPages(2, 18); // 2 = page length        18 = line length
//		for(int i = 0; i < pages.size(); i++) {
//			System.out.println(pages.get(i));
//			System.out.println("tester bester");
//		}
//		
//		System.out.println(pages.size());
//		System.out.println(test.search("This"));//jia change
//		System.out.println(test.search("test"));//jia change
//		System.out.println(test.search("book"));//jia change
//		System.out.println(test.search("book"));//jia change
//		System.out.println(test.search("book"));//jia change
//		System.out.println(test.search("is"));//winston change
//		System.out.println(test.search("is"));//winston change
//		System.out.println(test.search("is"));//winston change
//		System.out.println(test.search("is"));//winston change
//		System.out.println(test.search("is"));//winston change
	}
	public void testPrint(int index) {
		//System.out.println(book.substring(index, index + 2));
	}
	
	/**
	 * 
	 * @param filename
	 */
	public eReaderModel(String filename) {
		this.bookImages = new ArrayList<String>();
		this.bookTitles = new ArrayList<String>();
		book = "";
		convertFile(filename);
	}
	
	/**
	 * Purpose: 
	 * @param filename
	 */
	private void convertFile(String filename) {
		BufferedReader fileInput = null;
        int i = 0;
		try {
            fileInput = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }try {
			while (fileInput.ready()) {
				
			    book += fileInput.readLine() + '\n';
			    //System.out.println(i);
			    i++;
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
		retval = new ArrayList<String>();//jia change
		int lastSpace = 0;
		int start = 0;
		String currLine = "";
		int pageLine = 0;
		for(int i = 0; i < book.length(); i++) {
			if(book.charAt(i) == ' ') {
				lastSpace = i;
			}
			if(i - start >= lineLength) {
				currLine += book.substring(start, lastSpace+1)+ "\n";
				start = lastSpace+1;
				pageLine ++;
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
	public String getCurrPage() {
		if(retval==null || retval.size()==0) {
			System.out.println("no book content");
			return null;
		}
		return retval.get(currentPage);
	}
	public String startBook() {
		if(retval==null || retval.size()==0) {
			System.out.println("no book content");
			return null;
		}
		currentPage = 0;
		return retval.get(currentPage);
	}
	
	//jia change
	public String getNext() {
		currentPage++;
		if(retval==null || retval.size()==0 || currentPage>=retval.size()) {
			System.out.println("no book content or no more page");
			currentPage--;
			return null;
		}
		return retval.get(currentPage);
	}
	
	
	
	//jia change
	public String getPrevious() {
		currentPage--;
		if(retval==null || retval.size()==0 || currentPage<0) {
			System.out.println("no book content or first page now");
			currentPage++;
			return null;
		}
		return retval.get(currentPage);
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
		return retval.size();
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
		int index = search(retval.get(currentPage));
		bookmarks.add(index);
	}
	
	/**
	 * Purpose: Retrieves all of the file names in the picture
	 * directory. This will aid in displaying the images to the
	 * user so user input can be gathered.  
	 * 
	 * @param isBookFile boolean variable to determine if the
	 * file to be read will be the book or book images.
	 * 
	 */
//	public List<String> getImageFileNames() {
//		File folder; // Declare folder variable
//		folder = new File("book_images"); 
//		this.bookImages.clear();	 // Clear ArrayList before new pass
//		
//		// Get an array of file names
//		String contents[] = folder.list(); 
//		
//		for (int i = 0; i < contents.length; i++) {
//			this.bookImages.add(contents[i]);
//		}
//		return this.bookImages;
//	}
	
	/**
	 * Puprpose: Retrieves all of the file names in the book names
	 * directory. This will aid in displaying the images to the
	 * user so user input can be gathered. 
	 */
//	public List<String> getBookFileNames() {
//		File folder = new File("books");  // Sets directory to books
//		this.bookTitles.clear();   
//		
//		// Get an array of file names
//		String contents[] = folder.list(); 
//		
//		for (int i = 0; i < contents.length; i++) {
//			this.bookTitles.add(contents[i]);
//		}
//		
//		return this.bookTitles;
//		
//	}
	
	/**
	 * Purpose: Returns the size of the list that holds the book names.
	 * This will represent the number of books in the library. 
	 * 
	 * @return int value representing the number of books in the library.
	 */
	public int getNumBooks() {
		return this.bookTitles.size();
	}
	
	
	
	
	
} // End class
