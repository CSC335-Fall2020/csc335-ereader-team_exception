import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTestCases {
	
	/**
	 * Purpose: Create Model and Controller objects. This should test a great deal
	 * of the controller code. 
	 */
	@Test
	public void test() {
		eReaderController controller = new eReaderController();
		controller.addBook("testBook", "books/testBook.txt");
		controller.openBook("testBook");
		assertEquals(controller.currBook(), "testBook");
		assertEquals(controller.pageNumber(), 0);
		assertEquals(controller.bookSize(), 8);
//		controller.addBookmark();
		assertEquals(controller.getCurrPage(), controller.startBook());
		assertEquals(controller.getCurrPage(), controller.previousPage());
		System.out.println(controller.nextPage());
		System.out.println(controller.previousPage());
		assertEquals(controller.search("the"), 103);
		System.out.println(controller.getBookList());
		System.out.println(controller.getCurrPage());
		controller.closeBook();
		controller.openBook("abcd");
	}

} // End test-class
