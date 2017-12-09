import java.time.LocalDateTime;
import java.util.Scanner;

public class TRL_App 
{
	static Scanner scanner = new Scanner(System.in); 
	static Library library;
	static Event logger;
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		logger = new Event();
		library =  new Library();
		newSession(LocalDateTime.now());
	}

	public static void newSession(LocalDateTime sessionTime)
	{
		Patron currentPatron;
		String response = "";
		
		logger.newLog();
		
		//mimics "scanning" Patron ID card
		System.out.print("Enter Patron ID (1 for Dave, 2 for Sarah, 3 for Gary): ");	
		currentPatron = library.GetPatron(scanner.nextLine());
		logger.logEvent("Patron ID entered.");
		
		if(currentPatron.equals(null))
		{
			System.out.print("Invalid ID. Please sign up for a new ID card.");
			logger.logEvent("Invalid ID.");
			return;
		}
		
		System.out.println(currentPatron.getRecord(sessionTime));
		logger.logEvent("Patron Record Printed.");
		
		System.out.println("What would you like to do? (i for book checkin, o for book checkout, e for extend checkout)");
		response = scanner.nextLine();
		while((!response.equals("i")) && (!response.equals("o")) && (!response.equals("e")))
		{
			System.out.println("Invalid option. Try again.");
			response = scanner.nextLine();
		}
		
		if(response.equals("i"))
			checkinSession(sessionTime, currentPatron);
		else if(response.equals("o"))
			checkoutSession(sessionTime, currentPatron);
		else
			extendCheckoutSession(sessionTime, currentPatron);
		
		boolean printClause = false;
		for(int i = 0; i < currentPatron.getCheckedOutCount(); i++)
		{
			printClause = true;
			System.out.println(currentPatron.getCheckedOutBook(i).getTitle() + ". Due: " + currentPatron.getCheckedOutBook(i).getDueDate());
		}
		if(printClause)
			System.out.println("Failure to return clause: We WILL hunt you down.");
		
		System.out.println("\nType y to print event log, anything else to quit.");
		
		if(scanner.nextLine().equals("y"))
			logger.printLog();
	}
	
	public static void checkoutSession(LocalDateTime sessionTime, Patron currentPatron)
	{
		Copy book;
		String upc = "";
		int bookCount = 0;
		
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
			if(bookCount == library.isbnList.size())
			{
				System.out.println("That is literally all the books in the library. Proceeding to checkout.");
				logger.logEvent("Library out of books.");
				upc = "n";
			}
			else
			{
				upc = library.isbnList.get(bookCount);		
				bookCount++;
				
				book = library.getCopy(upc);
				
				if(!book.isAvailable())
				{
					System.out.print("Book is checked out. Type 'y' to manually check it in: ");
					logger.logEvent(book.getTitle() + " is already checked out.");
					if(scanner.nextLine().equals("y"))
					{
						library.ManualCheckinBook(book);
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
					System.out.println(book.getInfo() + "\n");
					library.CheckoutBook(book, sessionTime, currentPatron);
					logger.logEvent(book.getTitle() + " checked out to: " + currentPatron.getName());
				}	
			}
		}
		
		System.out.println("\nCheckout complete. Books checked out:");
		logger.logEvent("Checkout Complete.");
	}
	
	public static void checkinSession(LocalDateTime sessionTime, Patron currentPatron)
	{
		String response = "";
		int checkinNumber = 0;
		Copy book;
		
		if(currentPatron.getCheckedOutCount() < 1)
		{
			System.out.print("You do not have any books checked out.");
			logger.logEvent("Patron has no books to check in.");
			return;
		}
		
		System.out.println("How many books would you like to check in?");
		while (checkinNumber < 1)
		{
			response = scanner.nextLine();
			try
			{
				checkinNumber = Integer.parseInt(response);
			}
			catch(NumberFormatException e)
			{
				checkinNumber = 0;
				System.out.println("Invalid number entered. Try again.");
			}
			
			if(checkinNumber < 1)
				System.out.println("Invalid number entered. Try again.");
		}
		
		if(checkinNumber > currentPatron.getCheckedOutCount())
			checkinNumber = currentPatron.getCheckedOutCount();
		
		for(int i = 0; i < checkinNumber; i++)
		{
			book = currentPatron.getCheckedOutBook(i);
			library.CheckinBook(book, currentPatron);
			logger.logEvent(book.getTitle() + " checked in from: " + currentPatron.getName());
		}
		
		System.out.println("\nCheckin complete. Books still checked out:");
		logger.logEvent("Checkin Complete.");
	}
	
	public static void extendCheckoutSession(LocalDateTime sessionTime, Patron currentPatron)
	{
		String response;
		Copy book;
		
		if(currentPatron.getCheckedOutCount() < 1)
		{
			System.out.print("You do not have any books checked out to extend.");
			logger.logEvent("Patron has no books to to extend checkout.");
			return;
		}
		
		System.out.println("Would you like to extend checkout on all books or just overdue ones? (a for all, o for overdue)");
		response = scanner.nextLine();
		while((!response.equals("a")) && (!response.equals("o")))
		{
			System.out.println("Invalid option. Try again.");
			response = scanner.nextLine();
		}
		
		for(int i = 0; i < currentPatron.getCheckedOutCount(); i++)
		{
			book = currentPatron.getCheckedOutBook(i);
			if(book.isOverdue(sessionTime) || response.equals("a"))
			{
				library.ExtendCheckout(book, sessionTime);
				logger.logEvent(book.getTitle() + " due date extended to: " + sessionTime);
			}
		}
		
		System.out.println("\nCheckout extension complete. Books still checked out:");
		logger.logEvent("Checkout Extension Complete.");
	}
}
