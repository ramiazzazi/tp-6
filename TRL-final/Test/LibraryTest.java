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
		assertTrue("not a valid isbn", lib.IsValidISBN(id).equals(false));
	}
	
	@Test
	public void copy_available()
	{
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		assertTrue("copy is available", lib.IsBookAvailable(c) == true);
	}
	
	@Test
	public void copy_not_available()
	{
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		Patron p = new Patron("d");
		lib.CheckoutBook(c, LocalDateTime.now(), p);
		assertTrue("copy is available", lib.IsBookAvailable(c).equals(false));
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
		assertEquals("invalid patron retrieved", lib.GetPatron("999"), null);
	}
	
	@Test
	public void checkout_extended()
	{
		LocalDateTime now = LocalDateTime.now();
		String beforeDue;
		String afterDue;
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		c.Checkout(now.minusHours(168));
		beforeDue = c.getDueDate();
		lib.ExtendCheckout(c, now);
		afterDue = c.getDueDate();
		assertFalse("checkout book extended", beforeDue.equals(afterDue));
	}
	
	@Test
	public void book_checked_in_is_vailable()
	{
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		Patron p = new Patron("d");
		lib.CheckoutBook(c, LocalDateTime.now(), p);
		lib.CheckinBook(c, p);
		assertTrue("checked in book is available", c.isAvailable() == true);
	}
	
	@Test
	public void book_checked_in_is_not_on_patron_list()
	{
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		Patron p = new Patron("d");
		lib.CheckoutBook(c, LocalDateTime.now(), p);
		lib.CheckinBook(c, p);
		Integer count = p.getCheckedOutCount();
		assertTrue("checked in book not on patron list", count.equals(0)); 
	}
	
	@Test
	public void invalid_book_is_null()
	{
		Library lib = new Library();
		assertEquals("checked in book not on patron list", lib.getCopy(10000), null);
	}
	
	@Test
	public void manual_checkin_available()
	{
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		c.Checkout(LocalDateTime.now());
		lib.ManualCheckinBook(c);
		assertTrue("book manually checked in is available", c.isAvailable() == true);
	}
	
	@Test
	public void manual_checkin_removed_from_patron_list()
	{
		Library lib = new Library();
		Copy c = lib.CreateCopy("a", "b");
		Patron p = new Patron("d");
		lib.AddPatron(p);
		lib.CheckoutBook(c,  LocalDateTime.now(), p);
		lib.ManualCheckinBook(c);
		Integer count = p.getCheckedOutCount();
		assertTrue("book manually checked in removed from patron list", count.equals(0));
	}
}
