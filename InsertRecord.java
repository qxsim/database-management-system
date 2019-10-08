import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InsertRecord {
	
	public InsertRecord (Statement stmt, Scanner scanner) {
		
		try {
			System.out.print("Party ID: ");
			String pid = scanner.nextLine().toUpperCase();
			
			System.out.print("Party Name: ");
			String partyName = scanner.nextLine().toUpperCase();
			
			System.out.print("Menu ID: ");
			String mid = scanner.nextLine().toUpperCase();
			
			System.out.print("Venue ID: ");
			String vid = scanner.nextLine().toUpperCase();
			
			System.out.print("Entertainment ID: ");
			String eid = scanner.nextLine().toUpperCase();
			
			System.out.print("Quoted Price: ");
			String quotedPrice = scanner.nextLine();
			quotedPrice = "£" + quotedPrice;
			
			System.out.print("Date (HINT: YYYY-MM-DD): ");
			String dateTime = scanner.nextLine().toUpperCase();
			dateTime += " 21:00:00";
			
			System.out.print("Number Of Guests: ");
			int noOfGuests = scanner.nextInt();
			
			insertParty(stmt, pid, partyName, mid, vid, eid, quotedPrice, dateTime, noOfGuests);
		}
		
		catch(InputMismatchException i) {
			i.printStackTrace();
		}
		
		catch (NumberFormatException n) {
			n.printStackTrace();
		}
		
		catch (IllegalArgumentException time) {
			System.out.println("ERROR: Time and Date has invalid entry.");
		}
		
		finally {
			scanner.close();
		}
		
	}

	public void insertParty (Statement stmt, String pid, String partyName, String mid, String vid ,String eid, String quotedPrice, String dateTime, int noOfGuests) {
		
		try {
			stmt.executeUpdate("INSERT INTO " + 
					"party (pid, name, mid, vid, eid, price, timing, numberofguests) " + 
					"VALUES ('" + pid + "', '" + partyName + "', '" + mid + "', '" + vid + "', '" + eid + "', '" + quotedPrice + "' , '" + dateTime + "', '" + noOfGuests + "')");
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("'party' record successfully entered!");
	}
}