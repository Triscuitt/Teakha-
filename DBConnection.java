/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesreportmodule;

import java.sql.*;

/**
 *
 * @author ivand
 */
public class DBConnection {
    public static Connection getConnection()throws Exception{
    
        String url = "jdbc:mysql://localhost:3306/teakhaKiosk";
        String user = "root"; // change if needed
        String pass = "#Diverson3008"; // change to your password
        Class.forName("com.mysql.cj.jdbc.Driver");
    return DriverManager.getConnection(url, user, pass);
    }
}
