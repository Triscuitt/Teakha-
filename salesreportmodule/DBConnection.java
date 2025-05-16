package salesreportmodule;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static String url = "jdbc:mysql://localhost:3306/teakhaKiosk"; //change this if needed
    private static String user = "root";  //change this if needed
    private static String pass = "password";  //change this if needed                                 

    public static Connection getConnection() throws Exception {
         Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }
}
