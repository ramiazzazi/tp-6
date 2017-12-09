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
	static Patron patronWithHolds;
	static Patron patronWithoutHolds;
	static Library library;
	static ArrayList<String> isbnList = new ArrayList<String>();
	
	
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
		int num;
		isbn = createUniqueISBN(isbn);
		
		newCopy = new Copy(title, author, isbn);
		
		copiesList.add(newCopy);
		
		return newCopy;
	}
	
//Refactor #2: Extracted a method, which ensures ISBN is not a duplicate
	private String createUniqueISBN(String isbn) {
		Boolean validISBN = false;
		
		while(!validISBN)	
		{
			isbn = createNewISBN(isbn);
			
			validISBN = IsValidISBN(isbn);
		}
		return isbn;
	}
	
//refactor #1: Extracted a method, which generates a string of ISBNLength, currently set at 13 characters
	private String createNewISBN(String isbn) {
		int num;
		for(int i = 0; i < ISBNLength; i++)
		{
			num = rand.nextInt(10);
			isbn += Integer.toString(num);
		}
		return isbn;
	}
	
	public void AddCopy(Copy copy)
	{
		copiesList.add(copy);
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
	
	public void CheckoutBook(Copy book, LocalDateTime time)
	{		
		book.Checkout(time);
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
	
	public void InitializeLibrary()
	{
		AddBookToLibrary("This book is boring", "Some guy");
		AddBookToLibrary("Don't read this", "Who cares");
		AddBookToLibrary("Who even reads books anymore", "Bitter programmer");
		AddBookToLibrary("Books are outdated", "Dr. McTechie");
		AddBookToLibrary("All knowledge in the world", "God?");
	}
	
	public void AddBookToLibrary(String title, String author)
	{
		Copy copy;
		
		copy = CreateCopy(title, author);
		isbnList.add(copy.getISBN());
		AddCopy(copy);
	}
	
	public void InitializePatrons()
	{	
		patronWithHolds = new Patron("Dave");
		patronWithoutHolds = new Patron("Sarah");
		
		this.AddPatron(patronWithHolds);
		this.AddPatron(patronWithoutHolds);
	}
}
