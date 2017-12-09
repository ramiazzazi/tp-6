import static org.junit.Assert.*;

import org.junit.Before;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class PatronTest {

	@Before
	public void setUp() throws Exception {
	}

	@org.junit.Test
	public void test() 
	{
		//fail("Not yet implemented");
	}

	@org.junit.Test
	public void patron_has_name()
	{
		Patron d = new Patron("name");
		assertTrue("patron does not have name", d.getName().equals("name"));
	}
	
	@org.junit.Test
	public void nothing_checked_out_initially()
	{
		Patron d = new Patron("name");
		Integer count = d.getCheckedOutCount();
		assertTrue("nothing checked out initially", count.equals(0));
	}
	
	@org.junit.Test
	public void checkout_success()
	{
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		d.CheckoutBook(c);
		Integer count = d.getCheckedOutCount();
		assertTrue("checkout success", count.equals(1));
	}
	
	@org.junit.Test
	public void patron_assign_ID()
	{
		Patron d = new Patron("name");
		d.AssignID(77);
		assertTrue("patron has correct id", d.getID().equals("77"));
	}

	@org.junit.Test
	public void patron_has_holds()
	{
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		c.Checkout(LocalDateTime.now().minusHours(500));
		d.CheckoutBook(c);
		assertTrue("patron has hold", d.hasHolds(LocalDateTime.now()));
	}
	
	@org.junit.Test
	public void patron_has_no_holds()
	{
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		c.Checkout(LocalDateTime.now());
		d.CheckoutBook(c);
		assertFalse("patron has no hold", d.hasHolds(LocalDateTime.now()));
	}
	
	@org.junit.Test
	public void check_in_success()
	{
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		d.CheckoutBook(c);
		d.CheckinBook(c);
		Integer count = d.getCheckedOutCount();
		assertTrue("checkin success", count.equals(0));

	}
	
	@org.junit.Test
	public void patron_has_record()
	{
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		c.Checkout(LocalDateTime.now());
		d.CheckoutBook(c);
		assertFalse("patron has no hold", d.getRecord(LocalDateTime.now()).equals(""));
	}
	
	@org.junit.Test
	public void book_checked_out_to_patron()
	{
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		c.Checkout(LocalDateTime.now());
		d.CheckoutBook(c);
		Copy c2 = d.getCheckedOutBook(c.getISBN());
		assertTrue("book checked out to patron", c.equals(c2));
	}
	
	@org.junit.Test
	public void book_checked_out_to_patron_bad_isbn()
	{
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		c.Checkout(LocalDateTime.now());
		d.CheckoutBook(c);
		Copy c2 = d.getCheckedOutBook("1234");
		assertEquals("bad book is null", c2, null);
	}
	
	@org.junit.Test
	public void book_checked_out_by_index()
	{
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		c.Checkout(LocalDateTime.now());
		d.CheckoutBook(c);
		Copy c2 = d.getCheckedOutBook(0);
		assertTrue("bad book is null", c.equals(c2));
	}
	
	@org.junit.Test
	public void overdue_and_notoverdue_records_not_same()
	{
		LocalDateTime now = LocalDateTime.now();
		String recordOverdue = "";
		String recordNotOverdue = "";
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		c.Checkout(now);
		d.CheckoutBook(c);
		recordNotOverdue = d.getRecord(now);
		d.CheckinBook(c);
		c.Checkin();
		c.Checkout(now.minusHours(500));
		d.CheckoutBook(c);
		recordOverdue = d.getRecord(now);
		assertFalse("overude and not over due records not same", recordOverdue.equals(recordNotOverdue));
	}
}
//git test
