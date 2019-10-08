import java.sql.*;
import java.util.Random;

public class PopulateTables {
	
	Random generateRandom = new Random();
	
	public PopulateTables(Connection dbConn, Statement stmt) {
		String[] venueTypes = {"THE BALLROOM", "THE DANCEHOUSE", "DANCEFLOOR", "PRYZM", "ROSIES", 
				"BASSHOUSE", "PLAYERS", "PLAYAS", "BASSDROP", "THE O2"};
		
		String[] menuTypes = {"CHICKEN", "BEEF", "VEGETARIAN", "VEGAN", "CHINESE", "INDIAN", "PAKISTANI",
				"ARAB", "ENGLISH", "THAI", "SPANISH"};
		
		String[] entTypes = {"50 CENT", "MAGIC SHOW", "DANCE", "ADELE", "ACAPPELA", "DRAKE", "STAND-UP COMEDY",
				"BEATBOXING", "PLAY", "OPERA"};
		
		String[] partyNames = {"WINTER BALL", "WINTER FAIRYTALE", "WINTER IS COMING", "CHRISTMAS PARTY", "CHRISTMAS BALL",
				"MASQUERADE BALL", "MASQUERADE PARTY", "SWEATER PARTY", "XMAS BASH", "WINTER WONDERLAND"};
		
		
		populateVenue(venueTypes, stmt);
		populateMenu(menuTypes, stmt);
		populateEntertainment(entTypes, stmt);
		populateParty(partyNames, stmt);
	}
	
	public void populateParty(String[] partyNames, Statement stmt) {
		String insertParty = "";
		String format = "";
		int low = 30;
		int high = 1000;
		int min = 50;
		int max = 1000;
		
		
		for (int i=1; i<=100; i++) {
			format = String.format("%05d", i);
			
			insertParty = "INSERT INTO " + 
					"party (pid, name, mid, vid, eid, price, timing, numberofguests) " +
					"VALUES (" + "'PAR" + format + "', '" + partyNames[generateRandom.nextInt(partyNames.length)] + "', '" +
					recordSelect("mid", "menu", stmt) + "', '" + recordSelect("vid", "venue", stmt) + "', '" + recordSelect("eid", "entertainment", stmt) + "', '" +
					generateCost(low, high) + "', '" + timeGenerate(stmt) + "', '" + (generateRandom.nextInt(max-min) + min) + "')";
			
			
			try {
				stmt.executeUpdate(insertParty);
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				break;
			}
		} 
		
		System.out.println("'party' entries sucessfully added!");	
	}
	
	public void populateVenue(String[] venueTypes, Statement stmt) {
		String insertVenue = "";
		String format = "";
		int low = 400;
		int high = 50000;
		
		
		for (int i=1; i<=100; i++) {
			format = String.format("%04d", i);
			
			insertVenue = "INSERT INTO " + 
					"venue (vid, name, venuecost) " +
					"VALUES (" + "'VEN" + format + "', '" + venueTypes[generateRandom.nextInt(venueTypes.length)] + "', '" + 
					generateCost(low, high) + "')";
			
			
			try {
				stmt.executeUpdate(insertVenue);
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("'venue' entries sucessfully added!");
	}
	
	public void populateMenu(String[] menuTypes, Statement stmt) {
		String insertMenu = "";
		String format = "";
		int low = 10;
		int high = 500;
		
		
		for (int i=1; i<=100; i++) {
			format = String.format("%04d", i);
			
			insertMenu = "INSERT INTO " + 
					"menu (mid, description, costprice) " +
					"VALUES (" + "'MEN" + format + "', '" + menuTypes[generateRandom.nextInt(menuTypes.length)] + "', '" + 
					generateCost(low, high) + "')";
			
			
			try {
				stmt.executeUpdate(insertMenu);
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("'menu' entries sucessfully added!");
	}
	
	public void populateEntertainment(String[] entTypes, Statement stmt) {
		String insertEnt = "";
		String format = "";
		int low = 100;
		int high = 50000;
		
		
		for (int i=1; i<=100; i++) {
			format = String.format("%04d", i);
			
			insertEnt = "INSERT INTO " + 
					"entertainment (eid, description, costprice) " +
					"VALUES (" + "'ENT" + format + "', '" + entTypes[generateRandom.nextInt(entTypes.length)] + "', '" + 
					generateCost(low, high) + "')";
			
			
			try {
				stmt.executeUpdate(insertEnt);
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("'entertainment' entries sucessfully added!");
	}
	
	public String timeGenerate(Statement stmt) {
		String timedate = " ";
		
		try {
			ResultSet resultSet = stmt.executeQuery("SELECT TIMESTAMP WITHOUT TIME ZONE '2018-12-01' + RANDOM() * (TIMESTAMP WITHOUT TIME ZONE '2018-12-31' - TIMESTAMP WITHOUT TIME ZONE '2018-12-01')");
			while (resultSet.next()) {
				String columnValue = resultSet.getString(1);
				timedate = columnValue.split(" ")[0];
				timedate += " 21:00:00";
				return timedate;
			}
		} 
		
		catch (SQLException e) {
			System.out.println("ERROR: Time couldn't be retrieved");
			e.printStackTrace();
		}
		
		return timedate;	
	}
	
	public String recordSelect(String value, String table, Statement stmt) {
		String record = "";
		
		try {
			ResultSet resultSet = stmt.executeQuery("SELECT " + value + " FROM " + table + " ORDER BY RANDOM() LIMIT 1");
			while (resultSet.next()) {
				String columnValue = resultSet.getString(1);
				record = columnValue;
				return record;
			}
		} 
		
		catch (SQLException e) {
			System.out.println("ERROR: Record couldn't be retrieved");
			e.printStackTrace();
		}
		
		return record;
	}
	
	public String generateCost(int low, int high) {
		Random genVal = new Random();
		
		int firstHalf = genVal.nextInt(high-low) + low;
		int secondHalf = genVal.nextInt(99);
		
		String cost = firstHalf + "." + secondHalf; 
		
		return cost;	
	}
}