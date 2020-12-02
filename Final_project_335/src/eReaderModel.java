import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author 
 *
 */
public class eReaderModel {

	private String book;
	private List<String> retval ;// jia change 
	private int currentPage=0;//jia change
	private String searchBook;//jia change
	private int searchNumber=0;//jia change
	private String searchCurrent;//jia change
	
	
	
	public static void main(String[] args) {
		
		eReaderModel test = new eReaderModel("testBook.txt");
		List<String> pages = test.getPages(2, 18); // 2 = page length        18 = line length
		for(int i = 0; i < pages.size(); i++) {
			System.out.println(pages.get(i));
			System.out.println("tester bester");
		}
		
		System.out.println(pages.size());
		System.out.println(test.search("book"));//jia change
		System.out.println(test.search("test"));//jia change
		System.out.println(test.search("book"));//jia change
		System.out.println(test.search("book"));//jia change
		System.out.println(test.search("book"));//jia change
		System.out.println(test.search("book"));//jia change
	
	}
	
	/**
	 * 
	 * @param filename
	 */
	public eReaderModel(String filename) {
		book = "";
		getBook(filename);
	}
	
	/**
	 * Purpose: 
	 * @param filename
	 */
	private void getBook(String filename){
		Scanner fileInput = null;
        
        try {
            fileInput = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }while (fileInput.hasNext()) {
        	
            book += fileInput.nextLine() + '\n';
            
        }
        fileInput.close();
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
			if( i - start >= lineLength) {
				
				currLine +=book.substring(start, lastSpace+1)+ "\n";
				start = lastSpace+1;
				pageLine ++;
			}if( book.charAt(i) == '\n') {
				currLine +=book.substring(start, i)+ "\n";
				start = i+1;
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
	public String startBook() {
		if(retval==null || retval.size()==0) {
			System.out.println("no book content");
			return null;
		}
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
	public int getCurrent() {
		return currentPage;
	}
	
	
	//jia change
	public int search(String input) {
		if(searchBook==null || !searchCurrent.equals(input)) {
			searchNumber=0;
			searchBook=book.substring(0);
		}
	
		searchCurrent=input.substring(0);
		for (int i = 0; i <searchBook.length(); i++) {
			     if (i <= searchBook.length() - input.length()) {
			         if (searchBook.indexOf(input, i) > 0) {
			             i = searchBook.indexOf(input, i);
			             searchBook=searchBook.substring(i+input.length());
			             searchNumber+=i;
			          //   If the position of the last word of return is found, 
			         //the position of the first word is searchNumber-input.length();
			             return searchNumber;
			         }
			     }
			 }
		//
	
		searchBook=book.substring(0);
		searchNumber=0;
		return -1;
	}
} // End class
