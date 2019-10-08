import java.sql.*;

public class CreateTables {

	public CreateTables(Connection dbConn, Statement stmt) {
		String partyCreate = "CREATE TABLE party ( " + 
				"	pid				CHAR (8)	NOT NULL UNIQUE PRIMARY KEY, " + 
				"	name			CHAR (30)	NOT NULL, " + 
				"	mid				CHAR (7)	NOT NULL, " + 
				"	vid				CHAR (7)	NOT NULL, " + 
				"	eid				CHAR (7)	NOT NULL, " + 
				"	price			MONEY		NOT NULL, " + 
				"	timing			TIMESTAMP	NOT NULL, " + 
				"	numberofguests	INTEGER		NOT NULL, " + 
				"	FOREIGN KEY (mid) REFERENCES menu(mid) " +
				"		ON DELETE CASCADE " +
				"		ON UPDATE CASCADE, " +
				"	FOREIGN KEY (vid) REFERENCES venue(vid) " +
				"		ON DELETE CASCADE " +
				"		ON UPDATE CASCADE, " +
				"	FOREIGN KEY (eid) REFERENCES entertainment(eid) " +
				"		ON DELETE CASCADE " +
				"		ON UPDATE CASCADE " +
				")";
		
		String venueCreate = "CREATE TABLE venue ( " + 
				"	vid				CHAR (7)	NOT NULL PRIMARY KEY, " + 
				"	name			CHAR (30)	NOT NULL, " + 
				"	venuecost		MONEY		NOT NULL " + 
				")";
		
		String menuCreate = "CREATE TABLE menu ( " + 
				"	mid				CHAR (7)	NOT NULL PRIMARY KEY, " + 
				"	description		CHAR (30)	NOT NULL, " + 
				"	costprice		MONEY		NOT NULL " + 
				")";
		
		String entertainmentCreate = "CREATE TABLE entertainment ( " + 
				"	eid				CHAR (7)	NOT NULL PRIMARY KEY, " + 
				"	description		CHAR (30)	NOT NULL, " + 
				"	costprice		MONEY		NOT NULL " + 
				")";
		
		String[] entryArray = {venueCreate, menuCreate, entertainmentCreate, partyCreate};
		
		for (int i=0; i<4; i++) {
			try {
				stmt.executeUpdate(entryArray[i]);
				System.out.println("Created '" + entryArray[i].split(" ")[2] + "' successfully");
			} 
		
			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("'" + entryArray[i].split(" ")[2] + "' already exists");
			}
		}
	}
}