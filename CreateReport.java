import java.sql.*;

public class CreateReport {
	
	public CreateReport(String table, String id, Statement stmt) {
		if (table.equals("party")) {
			System.out.println(createPartyReport(id, stmt));
		}
		
		else if (table.equals("menu")) {
			System.out.println(createMenuReport(id, stmt));
		}
	}
	
	public String createPartyReport(String pid, Statement stmt) {
		String venueCostQuery = "SELECT venuecost FROM venue WHERE vid = (SELECT vid FROM party WHERE pid = '" + pid + "')";
		String menuCostQuery = "SELECT costprice FROM menu WHERE mid = (SELECT mid FROM party WHERE pid = '" + pid + "')";
		String entCostQuery = "SELECT costprice FROM entertainment WHERE eid = (SELECT eid FROM party WHERE pid = '" + pid + "')";
		
		venueCostQuery = recordSelect(venueCostQuery, stmt).replaceAll("[^0-9]", "");
		menuCostQuery = recordSelect(menuCostQuery, stmt).replaceAll("[^0-9]", "");
		entCostQuery = recordSelect(entCostQuery, stmt).replaceAll("[^0-9]", "");
		
		int numOfGuests = Integer.parseInt(recordSelect("SELECT numberofguests FROM party WHERE pid = '" + pid + "'", stmt).replaceAll("[^0-9]", ""));
		int priceCharged = Integer.parseInt(recordSelect("SELECT price FROM party WHERE pid = '" + pid + "'", stmt).replaceAll("[^0-9]", ""));
		
		int venueCost = Integer.parseInt(venueCostQuery);
		int menuCost = Integer.parseInt(menuCostQuery);
		int entCost = Integer.parseInt(entCostQuery);
		
		int totalCost = venueCost + (3 * menuCost) + entCost;
		
		int netProfit = (priceCharged * numOfGuests) - totalCost;
		
		String report = "---------- PARTY REPORT ---------- \n";
		
		report += "PARTY ID: " + pid + " \n";
		report += "PARTY NAME: " + recordSelect("SELECT name FROM party WHERE pid='" + pid + "'" , stmt) + " \n";
		report += "VENUE NAME: " + recordSelect("SELECT name FROM venue WHERE vid = (SELECT vid FROM party WHERE pid = '" + pid + "')", stmt) + " \n";
		report += "MENU DESCRIPTION: " + recordSelect("SELECT description FROM menu WHERE mid = (SELECT mid FROM party WHERE pid = '" + pid + "')", stmt) + " \n";
		report += "ENTERTAINMENT DESCRIPTION: " + recordSelect("SELECT description FROM entertainment WHERE eid = (SELECT eid FROM party WHERE pid = '" + pid + "')", stmt) + " \n";
		report += "NO. OF GUESTS: " + recordSelect("SELECT numberofguests FROM party WHERE pid = '" + pid + "'", stmt) + " \n";
		report += "PRICE CHARGED: " + recordSelect("SELECT price FROM party WHERE pid = '" + pid + "'", stmt) + " \n";
		report += "TOTAL COST PRICE: £" + totalCost + " \n";
		report += "NET PROFIT: £" + netProfit + " \n";
		
		return report;
	}
	
	public String createMenuReport(String mid, Statement stmt) {
		int noOfParties = 0;
		int noOfGuests = 0;
		int totalMenuUsed = 0;
		
		try {
			noOfParties = Integer.parseInt(recordSelect("SELECT COUNT(pid) FROM party WHERE mid='" + mid + "'", stmt));
			noOfGuests = Integer.parseInt(recordSelect("SELECT SUM(numberofguests) FROM party WHERE mid='" + mid + "'", stmt));
			totalMenuUsed = (noOfParties * noOfGuests);
		}
		
		catch (Exception e) {
			noOfParties = 0;
			noOfGuests = 0;
			totalMenuUsed = 0;
		}
		
		String report = "---------- MENU REPORT ---------- \n";
		
		report += "MENU ID: " + mid + " \n";
		report += "MENU DESCRIPTION: " + recordSelect("SELECT description FROM menu WHERE mid = '" + mid + "'", stmt) + " \n";
		report += "MENU COST: " + recordSelect("SELECT costprice FROM menu WHERE mid = '" + mid + "'", stmt) + " \n";
		report += "MENU USED: " + totalMenuUsed + " times. \n";

		return report;
	}
	
	public String recordSelect(String query, Statement stmt) {
		String record = "";
		
		try {
			ResultSet resultSet = stmt.executeQuery(query);
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
}