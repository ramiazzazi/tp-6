import java.util.ArrayList;
import java.time.LocalDateTime;

public class Patron 
{
	private String patronName;
	private String patronID;
	private ArrayList<Copy> copiesOut;
	
	public Patron(String name)
	{
		patronName = name;
		copiesOut = new ArrayList<Copy>();
		patronID = "";
	}
	
	public void AssignID(int id)
	{
		patronID = Integer.toString(id);
	}
	
	public String getID()
	{
		return patronID;
	}
	
	public String getName()
	{
		return patronName;
	}
	
	public Boolean hasHolds(LocalDateTime time)
	{
		for(int i = 0; i < copiesOut.size(); i++)
		{
			if(copiesOut.get(i).isOverdue(time))
				return true;
		}
		
		return false;
	}
	
	public String getRecord(LocalDateTime time)
	{
		String record = "Patron Record. Copies out:\n";
		Copy copy;
		
		for(int i = 0; i < copiesOut.size(); i++)
		{
			copy = copiesOut.get(i);
			record += copy.getTitle() + ", ";
			record += copy.getAuthor() + ". Overdue: ";
			
			if(copy.isOverdue(time))
				record += "YES";
			else
				record += "NO";
			
			record += "\n";
		}
		
		return record;
	}
	
	public void CheckoutBook(Copy book)
	{
		copiesOut.add(book);
	}
	
	public void CheckinBook(Copy book)
	{
		copiesOut.remove(book);
	}
	
	public int getCheckedOutCount()
	{
		return copiesOut.size();
	}
	
	public Copy getCheckedOutBook(String isbn)
	{
		for(int i = 0; i < copiesOut.size(); i++)
		{
			if(copiesOut.get(i).getISBN().equals(isbn))
				return copiesOut.get(i);
		}
		
		return null;
	}
	
	public Copy getCheckedOutBook(int index)
	{
		return copiesOut.get(index);
	}
	
	public boolean isBookCheckedOut(Copy book)
	{
		for(int i = 0; i < copiesOut.size(); i++)
		{
			if(copiesOut.get(i).equals(book))
				return true;
		}
		
		return false;
	}
}
