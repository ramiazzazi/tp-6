import java.time.LocalDateTime;

public class Copy 
{
	private String copyISBN;
	private String copyTitle;
	private String copyAuthor;
	private LocalDateTime dueDate;
	private Boolean checkedOut;
	
	public Copy(String title, String author, String upc)
	{
		copyTitle = title;
		copyAuthor = author;
		copyISBN = upc;
		checkedOut = false;
		dueDate = null;
	}
	
	public Boolean isOverdue(LocalDateTime currentTime)
	{
		return currentTime.isAfter(dueDate);
	}
	
	public String getDueDate()
	{
		if(!checkedOut)
			return "";
		
		return dueDate.toString(); 
	}
	
	public String getTitle()
	{
		return copyTitle;
	}
	
	public String getAuthor()
	{
		return copyAuthor;
	}
	
	public String getISBN()
	{
		return copyISBN;
	}
	
	public String getInfo()
	{
		return copyTitle + ", " + copyAuthor +  ". ISBN: " + copyISBN;
	}
	
	public Boolean isAvailable()
	{
		return !checkedOut;
	}
	
	public void Checkout(LocalDateTime currentTime)
	{
		dueDate = currentTime.plusHours(168); // due in one week
		checkedOut = true;
	}
	
	public void Checkin()
	{
		dueDate = null;
		checkedOut = false;
	}
}
