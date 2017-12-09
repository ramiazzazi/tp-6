import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
public class LibraryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void patron_added()
	{
		String id;
		Library lib = new Library();
		Patron p = new Patron("d");
		lib.AddPatron(p);
		id = p.getID();
		assertTrue("patron added", lib.GetPatron(id).equals(p));
	}
	
	@Test
	public void invalid_isbn()
	{
		String id;
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		id = c.getISBN();
		assertTrue("not a valid isbn", lib.IsValidISBN(id) == false);
	}
	
	@Test
	public void copy_available()
	{
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		lib.AddCopy(c);
		assertTrue("copy is available", lib.IsBookAvailable(c) == true);
	}
	
	@Test
	public void copy_not_available()
	{
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		Patron p = new Patron("d");
		lib.AddCopy(c);
		lib.CheckoutBook(c, LocalDateTime.now(), p);
		assertTrue("copy is available", lib.IsBookAvailable(c) == false);
	}
	
	@Test
	public void get_copy_successful()
	{
		String id;
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		id = c.getISBN();
		assertTrue("succesfully got copy", lib.getCopy(id).equals(c));
	}

	@Test
	public void get_invalid_patron()
	{
		Library lib = new Library();
		Patron p = new Patron("d");
		lib.AddPatron(p);
		assertTrue("invalid patron retrieved", lib.GetPatron("999") == null);
	}
}
