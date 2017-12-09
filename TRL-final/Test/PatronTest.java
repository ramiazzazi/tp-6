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
		
		assertTrue("nothing checked out initially", d.getCheckedOutCount() == 0);
	}
	
	@org.junit.Test
	public void checkout_success()
	{
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		d.CheckoutBook(c);
		
		assertTrue("checkout success", d.getCheckedOutCount() > 0);
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
	public void check_in_success()
	{
		Patron d = new Patron("name");
		Copy c = new Copy("a", "b", "c");
		d.CheckoutBook(c);
		d.CheckinBook(c);
		assertTrue("checkin success", d.getCheckedOutCount() == 0);

	}
	
}
