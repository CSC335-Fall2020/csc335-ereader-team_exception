import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ControllerTestCases {
	
	/**
	 * Purpose: General test case
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		eReaderController controller = new eReaderController();
		controller.addBook("testBook", "books/testBook.txt");
		controller.openBook("testBook");
		assertEquals(controller.currBook(), "testBook");
		assertEquals(controller.pageNumber(), 0);
		assertEquals(controller.bookSize(), 8);
		assertEquals(controller.getCurrPage(), controller.previousPage());
		System.out.println(controller.nextPage());
		double prog1 = 0.125;
		junit.framework.Assert.assertEquals(controller.getProgress(), prog1);
		System.out.println(controller.previousPage());
		assertEquals(controller.search("the"), 0);
		System.out.println(controller.search("the"));
		assertEquals(controller.search("asdf"), -1);
		System.out.println(controller.getBookList());
		System.out.println(controller.getCurrPage());
		controller.addBookmark();
		controller.addBookmark();
		controller.removeBookmark();
		controller.removeBookmark();
		assertEquals(controller.getBookmarks(), new ArrayList<Integer>());
		controller.flipToPage(7);
		controller.flipToPage(10);
		double prog2 = 0.0;
		junit.framework.Assert.assertEquals(controller.getProgress(), prog2);
		assertEquals(controller.nextPage(), "End of Book");
		controller.closeBook();
		controller.openBook("abcd");
	}

	/**
	 * Purpose: Empty text file test case.
	 */
	@Test
	public void testEmpty() {
		eReaderController controller = new eReaderController();
		controller.addBook("empty", "books/empty.txt");
		controller.openBook("empty");
		assertEquals(controller.search("the"), -1);
		System.out.println(controller.getCurrPage());
	}
} // End test-class
