import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// connect to SQL Database

public class dbIntegration {

    private static final String url = "jdbc:mysql://localhost:3306/dwaDukaan";
    private static final String user = "root";
    private static final String password = "root";

    public static Connection connectToDB() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
