import java.sql.*;
import java.util.Scanner;

public class UIInterface {
	
	public CreateReport create;
	public InsertRecord insert;

	public UIInterface(Connection dbConn, Statement stmt) {
		startInterface(stmt);
	}
	
	public void startInterface(Statement stmt) {
		System.out.println("Interface Started.");
		System.out.print("Enter a command: ");
		System.out.println("(HINT: Use 'report', 'insert', or 'exit')");
		Scanner scanner = new Scanner(System.in);
		
		String command = scanner.nextLine().toLowerCase(); 
		
		if (command.equals("report") || command.equals("insert") || command.equals("exit")) {
			if (command.equals("report")) {
				System.out.println("HINT: Use 'party' or 'menu'");
	    		String reportType = scanner.nextLine().toLowerCase();
	    		
	    		if (reportType.equals("party") || reportType.equals("menu")) {
	    			System.out.println("HINT: Enter table ID - 'PARXXXXX | MENXXXX'");
	    			String id = scanner.nextLine().toUpperCase(); 
	    			if (reportType.equals("party")) {
	    				if (id.split("(?<=\\D)(?=\\d)")[0].equals("PAR") && id.split("(?<=\\D)(?=\\d)")[1].matches("[-+]?\\d*\\.?\\d+")) {
	    					create = new CreateReport(reportType, id, stmt);
		    				startInterface(stmt);
	    				}
	    				
	    				else {
	    					System.out.println("ERROR: Invalid PartyID");
	    					startInterface(stmt);
	    				}
	    			}
	    		
	    			else if (reportType.equals("menu")) {
	    				if (id.split("(?<=\\D)(?=\\d)")[0].equals("MEN") && id.split("(?<=\\D)(?=\\d)")[1].matches("[-+]?\\d*\\.?\\d+")) {
	    					create = new CreateReport(reportType, id, stmt);
		    				startInterface(stmt);
	    				}
	    				
	    				else {
	    					System.out.println("ERROR: Invalid MenuID");
	    					startInterface(stmt);
	    				}
	    			}
	    			
	    			else {
	    				
	    			}
	    		}
	    		
	    		else {
	    			System.out.println("ERROR: Enter a valid table.");
	    		}

	    	}
	    	
	    	else if (command.equals("insert")) {
	    		insert = new InsertRecord(stmt, scanner);
	    	}
	    	
	    	else {
	    		//QUITS
	    		scanner.close();
	    	}
	    }
	    
	    else {
	    	System.out.println("Usage: Enter 'report' to produce a report, 'insert' to insert a new entry, or 'exit' to close.");
	    	startInterface(stmt);
	    }
	}
}