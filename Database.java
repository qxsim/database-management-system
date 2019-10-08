import java.sql.*;

public class Database {
	
	private static String username = "qxn635";
	private static String password = "pl1tz28lzn";
	
	
	public static void main(String[] args) {
		
		System.setProperty("jdbc.drivers", "org.postgresql.Driver");
		String dbName = "jdbc:postgresql://mod-intro-databases.cs.bham.ac.uk/"+username;
		Connection dbConn = null;
		Statement stmt = null;
		CreateTables create;
		PopulateTables populate;
		DropAllTables drop;
		UIInterface ui;
		
		try {
			Class.forName("org.postgresql.Driver");
		} 
		
		catch (ClassNotFoundException ex) {
			System.out.println("ERROR: Driver not found");
		}

		try {
			dbConn = DriverManager.getConnection(dbName, username, password);
			stmt = dbConn.createStatement();
			
			System.out.println("Connected");
			
			drop = new DropAllTables(dbConn, stmt);
			create = new CreateTables(dbConn, stmt);
			populate = new PopulateTables(dbConn, stmt);
			ui = new UIInterface(dbConn, stmt);
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR: Could not connect");
			
		}

		finally {
			try {
				dbConn.close();
				System.out.println("Closed");
			} 
			
			catch (SQLException e) {
				System.out.println("ERROR: Could not close connection");
				e.printStackTrace();
			}
		}
	}
}