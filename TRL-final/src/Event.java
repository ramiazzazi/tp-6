import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event 
{
	private ArrayList<String> log;
	
	public Event()
	{
		log = new ArrayList<String>();
	}
	
	public void newLog()
	{
		log.clear();
	}
	
	public void newLog(String firstLine)
	{
		log.clear();
		log.add(firstLine + ". " + LocalDateTime.now()); 
	}
	
	public void logEvent(String line)
	{
		log.add(line + ". " + LocalDateTime.now());
	}
	
	public void printLog()
	{
		for(int i = 0; i < log.size(); i++)
			System.out.println(log.get(i));
	}
	
	public int getLogSize()
	{
		return log.size();
	}
}
