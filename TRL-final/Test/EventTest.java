import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EventTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void log_clear() 
	{
		Event e = new Event();
		e.newLog();
		assertTrue("new log is empty", e.getLogSize() == 0);
	}

	@Test
	public void log_add() 
	{
		Event e = new Event();
		e.logEvent("test");
		assertTrue("log event added", e.getLogSize() == 1);
	}
	
	@Test
	public void new_log()
	{
		Event e = new Event();
		assertTrue("log object starts empty", e.getLogSize() == 0);
	}
	
	@Test
	public void new_log2()
	{
		Event e = new Event();
		e.newLog("this is a test string");
		assertFalse("log object starts empty", e.getLogSize() == 0);
	}
	
	public void print_log() {
		
	}
	
	
}
