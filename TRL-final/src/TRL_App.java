import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;

public class TRL_App 
{
	static Scanner scanner = new Scanner(System.in);
	static Patron patronWithHolds;
	static Patron patronWithoutHolds;
	static Library library;
	static ArrayList<String> isbnList = new ArrayList<String>();
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		library =  new Library();
		
		InitializeLibrary();
		InitializePatrons();
		
		newSession(patronWithoutHolds, LocalDateTime.now());
		//newSession(patronWithHolds, LocalDateTime.now());
	}

	public static void newSession(Patron patron, LocalDateTime time)
	{
		Event logger = new Event();
		LocalDateTime sessionTime = LocalDateTime.now();
		Patron currentPatron;
		String upc = "";
		int bookCount = 0;
		String patronID = "";
		ArrayList<Copy> checkoutList = new ArrayList<Copy>();
		Copy book;
		
		//mimics "scanning" Patron ID card
		System.out.print("Enter Patron ID: ");
		
		patronID = scanner.nextLine();
		
		// normally we would read the entered value, but for this use case assume any entered value is good
		currentPatron = library.GetPatron("1");
		logger.logEvent("Patron ID entered.");
		
		if(currentPatron.equals(null))
		{
			System.out.print("Invalid ID. Please sign up for a new ID card.");
			logger.logEvent("Invalid ID.");
			return;
		}
		
		System.out.println(currentPatron.getRecord(sessionTime));
		logger.logEvent("Patron Record Printed.");
		
		if(currentPatron.hasHolds(sessionTime))
		{
			System.out.print("Please return overdue books before checking out new ones.");
			logger.logEvent("Patron has hold.");
			return;
		}
		
		while(!(upc.equals("n")))
		{
			//mimics "scanning" book, 'n' mimics pressing the checkout button
			System.out.print("Enter Book UPC ('n' to complete checkout): ");
			
			// normally this would be the 'scan' result
			upc = scanner.nextLine();
			
			if(upc.equals("n"))
				continue;
			
			//  but i can't expect the user right now to know the 13 digit isbn so just get it
			if(bookCount == isbnList.size())
			{
				System.out.println("That is literally all the books in the library. Proceeding to checkout.");
				logger.logEvent("Library out of books.");
				upc = "n";
			}
			else
			{
				upc = isbnList.get(bookCount);		
				bookCount++;
				
				book = library.getCopy(upc);
				
				if(!book.isAvailable())
				{
					System.out.print("Book is checked out. Type 'y' to manually check it in: ");
					logger.logEvent(book.getTitle() + " is already checked out.");
					if(scanner.nextLine().equals("y"))
					{
						book.Checkin();
						logger.logEvent(book.getTitle() + " manual checkin.");
					}
					else
						continue;
				}
				
				if(book.equals(null))
				{
					System.out.println("Book does not exist in database.");
					logger.logEvent(book.getTitle() + " doesn't exist in the database.");
				}
				else
				{
					checkoutList.add(book);
					System.out.println(book.getInfo() + "\n");
					library.CheckoutBook(book, sessionTime);
					patron.CheckoutBook(book);
					logger.logEvent(book.getTitle() + " checked out to: " + patron.getName());
				}	
			}
		}
		
		System.out.println("\nCheckout complete. Books checked out:");
		logger.logEvent("Checkout Complete.");
		for(int i = 0; i < checkoutList.size(); i++)
		{
			book = checkoutList.get(i);
			System.out.println(book.getTitle() + ". Due: " + book.getDueDate());
		}
		System.out.println("Failure to return clause: We WILL hunt you down.");
		System.out.println("\nType y to print event log, anything else to quit.");
		
		if(scanner.nextLine().equals("y"))
			logger.printLog();
	}
	
	public static void InitializeLibrary()
	{
		AddBookToLibrary("This book is boring", "Some guy");
		AddBookToLibrary("Don't read this", "Who cares");
		AddBookToLibrary("Who even reads books anymore", "Bitter programmer");
		AddBookToLibrary("Books are outdated", "Dr. McTechie");
		AddBookToLibrary("All knowledge in the world", "God?");
	}
	
	public static void AddBookToLibrary(String title, String author)
	{
		Copy copy;
		
		copy = library.CreateCopy(title, author);
		isbnList.add(copy.getISBN());
		library.AddCopy(copy);
	}
	
	public static void InitializePatrons()
	{	
		patronWithHolds = new Patron("Dave");
		patronWithoutHolds = new Patron("Sarah");
		
		library.AddPatron(patronWithHolds);
		library.AddPatron(patronWithoutHolds);
	}
}
