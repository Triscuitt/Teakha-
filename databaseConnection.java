import java.sql.Connection;
import java.sql.DriverManager;

public class databaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/teakhaKiosk"; //change this if needed
    private static final String user = "root";  //change this if needed
    private static final String pass = "password";  //change this if needed                                 

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, pass);
    }
}
