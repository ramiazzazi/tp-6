import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

// copies database
public class Library 
{
	private ArrayList<Copy> copiesList;
	private ArrayList<Patron> patronList;
	private int nextAvailableID = 1;
	private int ISBNLength = 13;
	private Random rand;
	ArrayList<String> isbnList = new ArrayList<String>();
	
	
	public Library()
	{
		copiesList = new ArrayList<Copy>();
		rand = new Random();
		patronList = new ArrayList<Patron>();
		InitializeLibrary();
		InitializePatrons();
	}
	
	public Copy CreateCopy(String title, String author)
	{
		Copy newCopy;
		String isbn = "";
		isbn = createUniqueISBN(isbn);
		
		newCopy = new Copy(title, author, isbn);
		
		copiesList.add(newCopy);
		
		return newCopy;
	}
	
//Refactor #2: Extracted a method, which ensures ISBN is not a duplicate
	private String createUniqueISBN(String isbn) 
	{
		Boolean validISBN = false;
		
		while(!validISBN)	
		{
			isbn = createNewISBN(isbn);
			
			validISBN = IsValidISBN(isbn);
		}
		return isbn;
	}
	
//refactor #1: Extracted a method, which generates a string of ISBNLength, currently set at 13 characters
	private String createNewISBN(String isbn) 
	{
		int num;
		for(int i = 0; i < ISBNLength; i++)
		{
			num = rand.nextInt(10);
			isbn += Integer.toString(num);
		}
		return isbn;
	}
	
	public Boolean IsBookAvailable(Copy copy)
	{
		return copy.isAvailable();
	}
	
	public Copy getCopy(String isbn)
	{
		Copy copy = null;
		
		for(int i = 0; i < copiesList.size(); i++)
		{
			if(copiesList.get(i).getISBN().equals(isbn))
				copy = copiesList.get(i);
		}
		
		return copy;
	}
	
	public Copy getCopy(int index)
	{
		if(index < copiesList.size())
			return copiesList.get(index);
		else
			return null;
	}
	
	public void CheckoutBook(Copy book, LocalDateTime time, Patron patron)
	{		
		book.Checkout(time);
		patron.CheckoutBook(book);
	}
	
	public void CheckinBook(Copy book, Patron patron)
	{
		book.Checkin();
		patron.CheckinBook(book);
	}
	
	public void ManualCheckinBook(Copy book)
	{
		book.Checkin();
		// need to find out which patron, if any, has it checked it and remove it from their list
		for(int i = 0; i < patronList.size(); i++)
		{
			if(patronList.get(i).isBookCheckedOut(book))
				patronList.get(i).CheckinBook(book);
		}
	}
	
	public Boolean IsValidISBN(String isbn)
	{
		Boolean valid = true;
		
		for(int i = 0; i < copiesList.size(); i++)
		{
			if(copiesList.get(i).getISBN().equals(isbn))
				valid = false;
		}
		
		return valid;
	}
	
	public void AddPatron(Patron p)
	{
		p.AssignID(nextAvailableID);
		patronList.add(p);
		nextAvailableID++;
	}
	
	public Patron GetPatron(String id)
	{
		Patron returnPatron = null;
		for(int i = 0; i < patronList.size(); i++)
		{
			if(patronList.get(i).getID().compareTo(id) == 0)
				return patronList.get(i);
		}
		
		return returnPatron;
	}
	
	public void ExtendCheckout(Copy book, LocalDateTime time)
	{
		book.Checkin();
		book.Checkout(time);
	}
	
	public void InitializeLibrary()
	{
		AddBookToLibrary("This book is boring", "Some guy");
		AddBookToLibrary("Don't read this", "Who cares");
		AddBookToLibrary("Who even reads books anymore", "Bitter programmer");
		AddBookToLibrary("Books are outdated", "Dr. McTechie");
		AddBookToLibrary("All knowledge in the world", "God?");
		AddBookToLibrary("The shortest book in the world", "Steve");
		AddBookToLibrary("Book #391", "Stephen King");
		AddBookToLibrary("Please by this book, I need money", "McBeggar");
		AddBookToLibrary("Theoretical physics for kids", "Smarty Marty");
		AddBookToLibrary("The last book in the library", "Librarian");
	}
	
	public void AddBookToLibrary(String title, String author)
	{
		Copy copy;
		
		copy = CreateCopy(title, author);
		isbnList.add(copy.getISBN());
	}
	
	public void InitializePatrons()
	{	
		LocalDateTime notOverdue = LocalDateTime.now().minusHours(48);
		LocalDateTime overdue = LocalDateTime.now().minusHours(500);
		Patron patron = new Patron("Dave");		
		this.AddPatron(patron);
		
		patron = new Patron("Sarah");
		CheckoutBook(getCopy(7), notOverdue, patron);
		this.AddPatron(patron);
		
		patron = new Patron("Gary");
		CheckoutBook(getCopy(8), notOverdue, patron);
		CheckoutBook(getCopy(9), overdue, patron);
		this.AddPatron(patron);
	}
}
