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
	
	public static void main(String[] args) {
		eReaderModel test = new eReaderModel("testBook.txt");
		List<String> pages = test.getPages(2, 18);
		for(int i = 0; i < pages.size(); i++) {
			System.out.println(pages.get(i));
			System.out.println("tester bester");
		}
		System.out.println(pages.size());
	}
	public eReaderModel(String filename) {
		book = "";
		getBook(filename);
	}
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
} // End class
