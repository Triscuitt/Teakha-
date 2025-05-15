package salesreportmodule;

import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class SalesMethods {
    public static void DailySales(JTable table) throws Exception {
        String[] columnNames = {"Date", "Total Sales"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);

        String sql = """
            SELECT DATE(transaction_time) AS sale_date, 
                   SUM(total_amount) AS total 
            FROM transaction_history 
            GROUP BY DATE(transaction_time)
            ORDER BY sale_date DESC
            """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String date = rs.getString("sale_date");
                double total = rs.getDouble("total");
                model.addRow(new Object[]{date, total});
            }
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage());
        }
    }

public static void WeeklySales(JTable table) throws Exception {
    String[] columnNames = {"Week", "Total Sales"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    table.setModel(model);

    String sql = """
        SELECT YEARWEEK(DATE(transaction_time)) AS week_num,
               SUM(total_amount) AS total 
        FROM transaction_history 
        GROUP BY YEARWEEK(DATE(transaction_time))
        ORDER BY week_num DESC
        """;

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            // Format week number with year
            String weekString = String.format("Week %d/%d", 
                rs.getInt("week_num") % 100,  // Extract week number
                rs.getInt("week_num") / 100   // Extract year
            );
            double total = rs.getDouble("total");
            model.addRow(new Object[]{weekString, total});
        }
    } catch (SQLException e) {
        throw new Exception("Database error: " + e.getMessage());
    }
}
    public static void MonthlySales(JTable table) throws Exception {
        String[] columnNames = {"Month", "Total Sales"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);

        String sql = """
            SELECT DATE_FORMAT(transaction_time, '%Y-%m') AS month,
                   SUM(total_amount) AS total 
            FROM transaction_history 
            GROUP BY DATE_FORMAT(transaction_time, '%Y-%m')
            ORDER BY month DESC
            """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String month = rs.getString("month");
                double total = rs.getDouble("total");
                model.addRow(new Object[]{month, total});
            }
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage());
        }
    }

    public static void SalesByItem(JTable table) throws Exception {
        String[] columnNames = {"Menu Item", "Quantity Sold", "Total Revenue"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);

String sql = "SELECT mi.name, " +
               "COUNT(oi.quantity) as quantity_sold, " +
               "SUM(oi.price * oi.quantity) as revenue " +
               "FROM order_items oi " +
               "JOIN menu_items mi ON oi.item_id = mi.item_id " +
               "GROUP BY mi.name " +
               "ORDER BY revenue DESC";
    
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String item = rs.getString("name");
                int quantity = rs.getInt("quantity_sold");
                double revenue = rs.getDouble("revenue");
                model.addRow(new Object[]{item, quantity, revenue});
            }
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage());
        }
    }
    
    public static void SalesByTimeDay(JTable table) throws Exception {
    String[] columnNames = {"Date", "Time Of Day", "Total Revenue"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    table.setModel(model);
    
    String sql = """
        SELECT 
            DATE(transaction_time) as Date,
            CASE
                WHEN HOUR(transaction_time) BETWEEN 6 AND 11 THEN 'Morning'        
                WHEN HOUR(transaction_time) BETWEEN 12 AND 17 THEN 'Afternoon'     
                WHEN HOUR(transaction_time) BETWEEN 18 AND 21 THEN 'Evening'      
                ELSE 'Night'                                                      
            END AS TimeOfDay,
            COUNT(*) AS Transactions,
            SUM(total_amount) AS TotalRevenue
        FROM transaction_history
        GROUP BY 
            DATE(transaction_time),
            CASE
                WHEN HOUR(transaction_time) BETWEEN 6 AND 11 THEN 'Morning'
                WHEN HOUR(transaction_time) BETWEEN 12 AND 17 THEN 'Afternoon'
                WHEN HOUR(transaction_time) BETWEEN 18 AND 21 THEN 'Evening'
                ELSE 'Night'
            END
        ORDER BY FIELD(TimeOfDay, 'Morning', 'Afternoon', 'Evening', 'Night');
        """;
    
    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            String date = rs.getString("Date");
            String timeOfDay = rs.getString("TimeOfDay");
            double totalRevenue = rs.getDouble("TotalRevenue");
            model.addRow(new Object[]{date, timeOfDay, totalRevenue});
        }
    } catch (SQLException e) {
        throw new Exception("Database error: " + e.getMessage());
    }
}
    public static void BestSellers(JTable table)throws Exception{
        
        String[] columnNames = {"Menu Item", "Total Orders"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);
        
        String sql = """
            SELECT mi.name, SUM(o.quantity) AS total_quantity
            FROM order_items o
            JOIN menu_items mi ON o.item_id = mi.item_id
            GROUP BY o.item_id
            ORDER BY total_quantity DESC
            LIMIT 3;
            """;
        
         try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String item = rs.getString("name");
                int quantity = rs.getInt("total_quantity");
 
                model.addRow(new Object[]{item,quantity });
            }
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage());
        }
    }
        
    
   public static void PeakHours(JTable table) throws Exception {
    String[] columnNames = {"Date", "Peak Hours", "Total Orders"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    table.setModel(model);
    
    String sql = """
        SELECT 
            DATE_FORMAT(transaction_time, '%Y-%m-%d') AS Date,
            CONCAT(LPAD(HOUR(transaction_time), 2, '0'), ':00') AS Hour,
            COUNT(*) AS TotalOrders,
            SUM(total_amount) AS TotalRevenue
        FROM transaction_history
        GROUP BY 
            DATE_FORMAT(transaction_time, '%Y-%m-%d'),
             CONCAT(LPAD(HOUR(transaction_time), 2, '0'), ':00')
        ORDER BY TotalOrders DESC
        LIMIT 5;
        """;
    
    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            String date = rs.getString("Date");
            String hour = rs.getString("Hour");
            int orders = rs.getInt("TotalOrders");
            model.addRow(new Object[]{date, hour, orders});
        }
    } catch (SQLException e) {
        throw new Exception("Database error: " + e.getMessage());
    }
}
   
    public static void TransactionHistory(JTable table)throws Exception{
        
        String[] columnNames = {"Transaction_ID","Session_ID","Total Amount","Transaction Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);
        
            String sql = """
                                SELECT 
                                     transaction_id,
                                     session_id,
                                     total_amount,
                                     transaction_time
                                 FROM transaction_history
                                 ORDER BY transaction_time DESC;                         
                         """;
                    
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                String sessionId = rs.getString("session_id");
                double totalAmount = rs.getDouble("total_amount");
                String transactionTime = rs.getString("transaction_time");
 
                model.addRow(new Object[]{transactionId,sessionId,totalAmount,transactionTime});
            }
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage());
        }
    }
}