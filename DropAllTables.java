import java.sql.*;

public class DropAllTables {
    
    public DropAllTables(Connection dbConn, Statement stmt) {
        String dropMenu = "DROP TABLE menu";
        String dropEnt = "DROP TABLE entertainment";
        String dropParty  = "DROP TABLE party";
        String dropVenue = "DROP TABLE venue";
        
        try {
            stmt.executeUpdate(dropParty);
            stmt.executeUpdate(dropMenu);
            stmt.executeUpdate(dropEnt);
            stmt.executeUpdate(dropVenue);
            System.out.println("All tables dropped successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}