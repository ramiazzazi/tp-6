import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
		Integer size = e.getLogSize();
		assertTrue("new log is empty", size.equals(0));
	}

	@Test
	public void log_add() 
	{
		Event e = new Event();
		e.logEvent("test");
		Integer size = e.getLogSize();
		assertTrue("log event added", size.equals(1));
	}
	
	@Test
	public void new_log()
	{
		Event e = new Event();
		Integer size = e.getLogSize();
		assertTrue("log object starts empty", size.equals(0));
	}
	
	@Test
	public void new_log2()
	{
		Event e = new Event();
		e.newLog("this is a test string");
		Integer size = e.getLogSize();
		assertFalse("log object starts empty", size.equals(0));
	}
	
	@Test
	public void print_log()
	{
		String testString = "this is a test string";
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Event e = new Event();
		e.newLog(testString);
		e.printLog();
		String outString = outContent.toString().substring(0, 21);
		assertTrue("log printed correctly", outString.equals(testString));
	}
	
}
