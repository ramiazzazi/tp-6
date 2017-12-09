import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

public class CopyTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void new_copy_not_due()
	{
		Copy c = new Copy("name", "author", "isbn");
		assertTrue("new copy is not due", c.getDueDate().equals(""));
	}
	
	@Test
	public void copy_checked_out()
	{
		Copy c = new Copy("name", "author", "isbn");
		c.Checkout(LocalDateTime.now());
		assertTrue("copy is checked out", c.isAvailable() == false);
	}
	
	@Test
	public void copy_is_overdue()
	{
		Copy c = new Copy("name", "author", "isbn");
		c.Checkout(LocalDateTime.now().minusHours(500));
		assertTrue("copy is checked out", c.isOverdue(LocalDateTime.now()) == true);
	}
	
	@Test
	public void copy_correct_name()
	{
		Copy c = new Copy("name", "author", "isbn");
		assertTrue("name is correct", c.getTitle().equals("name"));
	}
	
	@Test
	public void copy_correct_author()
	{
		Copy c = new Copy("name", "author", "isbn");
		assertTrue("author is correct", c.getAuthor().equals("author"));
	}
	
	@Test
	public void copy_correct_isbn()
	{
		Copy c = new Copy("name", "author", "isbn");
		assertTrue("isbn is correct", c.getISBN().equals("isbn"));
	}

}
