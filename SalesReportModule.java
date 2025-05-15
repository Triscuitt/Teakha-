package salesreportmodule;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class SalesReportModule {
    private static DefaultTableModel model;
    private static JFrame frame;
    private static JList<String> sales_reportList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Sales Report");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setLayout(new BorderLayout());
            
           
            frame.getContentPane().setBackground(Color.WHITE);
            
            String[] sales_reportItems = {
                "Daily Sales", "Weekly Sales", "Monthly Sales",
                "Sales by Menu Item", "Sales by Time of day",
                "Best Sellers", "Peak Hours", "Transaction History"
            };

            sales_reportList = new JList<>(sales_reportItems);
            sales_reportList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            sales_reportList.setBackground(Color.decode("#D9C6BF")); 
            
            frame.add(sales_reportList, BorderLayout.WEST);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(Color.WHITE); 
           

            String[] columnNames = {"DATE", "Sales Total"};
            model = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(model);
            table.setBackground(Color.WHITE); 
            table.getTableHeader().setBackground(Color.WHITE); 
            
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBackground(Color.decode("#D9C6BF")); 
            scrollPane.getViewport().setBackground(Color.WHITE);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
           
            JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
            searchPanel.setBackground(Color.WHITE); 
            searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
            JTextField searchField = new JTextField(); 
            searchField.setBackground(Color.WHITE);
            JButton searchButton = new JButton("SEARCH");
            searchButton.setBackground(Color.decode("#BF9D8A")); 
            searchPanel.add(searchField, BorderLayout.CENTER);
            searchPanel.add(searchButton, BorderLayout.EAST);
            searchButton.addActionListener(e -> {
                try {
                    performSearch(table, searchField.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                        "Error performing search: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            mainPanel.add(searchPanel, BorderLayout.NORTH);
            
           JButton exportpdfButton = new JButton("Export to PDF");
            exportpdfButton.setBackground(Color.decode("#BF9D8A")); 
            exportpdfButton.addActionListener(e -> exportToPDF(table));
            mainPanel.add(exportpdfButton, BorderLayout.SOUTH);

            sales_reportList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        String selectedValue = sales_reportList.getSelectedValue();
                        if (selectedValue != null) {
                            try {
                                loadSelectedReport(table, selectedValue);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frame,
                                    "Error loading report: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            });

            frame.add(mainPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
    
    private static void loadSelectedReport(JTable table, String reportType) throws Exception {
        switch (reportType) {
            case "Daily Sales":
                SalesMethods.DailySales(table);
                break;
            case "Weekly Sales":
                SalesMethods.WeeklySales(table);
                break;
            case "Monthly Sales":
                SalesMethods.MonthlySales(table);
                break;
            case "Sales by Menu Item":
                SalesMethods.SalesByItem(table);
                break;
            case "Sales by Time of day":
                SalesMethods.SalesByTimeDay(table);
                break;
            case "Best Sellers":
                SalesMethods.BestSellers(table);
                break;
            case "Peak Hours":
                SalesMethods.PeakHours(table);
                break;
            case "Transaction History":
                SalesMethods.TransactionHistory(table);
                break;
                
            default:
                model.setRowCount(0); // Clear table
        }
    }
    
   private static void exportToPDF(JTable table) {
    try {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        
        // Check if there's data to export
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(frame, 
                "No data to export. Please select a report type first.",
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Report.pdf"));
        document.open();
        
        // Add report header based on type
        String selectedValue = sales_reportList.getSelectedValue();
        if (selectedValue == null) {
            throw new IllegalStateException("Please select a report type");
        }
        
        // Add title and date
        com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font
        (com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD);
        Paragraph title = new Paragraph(getReportTitle(selectedValue), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        
        com.itextpdf.text.Font dateFont = new com.itextpdf.text.Font
        (com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL);
        Paragraph date = new Paragraph("Generated on: " + new java.util.Date().toString(), dateFont);
        date.setAlignment(Element.ALIGN_CENTER);
        document.add(date);
        
        document.add(Chunk.NEWLINE);
        
        // Create table
        PdfPTable pdfTable = new PdfPTable(tableModel.getColumnCount());
        pdfTable.setWidthPercentage(100);
        
        try {
            // Set column widths
            float[] columnWidths = new float[tableModel.getColumnCount()];
            for (int i = 0; i < columnWidths.length; i++) {
                columnWidths[i] = 1f; // Equal width for all columns
            }
            pdfTable.setWidths(columnWidths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        
        // Add headers
        BaseColor headerColor = new BaseColor(191, 157, 138); // #BF9D8A
        com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD, 
            new com.itextpdf.text.BaseColor(255, 255, 255));
        
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            PdfPCell headerCell = new PdfPCell(new Phrase(tableModel.getColumnName(i), headerFont));
            headerCell.setBackgroundColor(headerColor);
            headerCell.setBorderWidth(1);
            headerCell.setPadding(5);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(headerCell);
        }
        
        // Add data rows - using tableModel directly
        com.itextpdf.text.Font cellFont = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.NORMAL);
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                Object value = tableModel.getValueAt(row, col);
                String cellValue = (value != null) ? value.toString() : "";
                PdfPCell cell = new PdfPCell(new Phrase(cellValue, cellFont));
                cell.setPadding(4);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell);
            }
        }
        
        document.add(pdfTable);
        document.close();
        JOptionPane.showMessageDialog(frame, "PDF Report generated: Report.pdf");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frame, 
            "Error generating PDF: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    private static String getReportTitle(String reportType) {
        switch (reportType) {
            case "Daily Sales":
                return "Daily Sales Report";
            case "Weekly Sales":
                return "Weekly Sales Report";
            case "Monthly Sales":
                return "Monthly Sales Report";
            case "Sales by Menu Item":
                return "Menu Item Sales Report";
            case "Sales by Time of day":
                return "Time of Day Sales Report";
            case "Best Sellers":
                return "Best Sellers Report";
            case "Peak Hours":
                return "Peak Hours Report";
            case "Transaction History":
                return "Transaction History Report";
            default:
                return "Sales Report";
        }
    }

    private static PdfPTable createReportTable(String reportType) {
        PdfPTable table = new PdfPTable(model.getColumnCount());
        table.setWidthPercentage(100);
        
        try {
            float[] columnWidths = new float[model.getColumnCount()];
            for (int i = 0; i < columnWidths.length; i++) {
                columnWidths[i] = 1f; // Equal width for all columns
            }
            table.setWidths(columnWidths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        
        for (int i = 0; i < model.getColumnCount(); i++) {
            PdfPCell headerCell = new PdfPCell(new Phrase(model.getColumnName(i)));
            headerCell.setBackgroundColor(new BaseColor(191, 157, 138)); 
            headerCell.setBorderWidth(1);
            headerCell.setPadding(5);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }
        
        return table;
    }

    private static void addDataToTable(PdfPTable table) {
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                Object value = model.getValueAt(i, j);
                PdfPCell cell = new PdfPCell(new Phrase(value != null ? value.toString() : ""));
                cell.setBorderWidth(1);
                cell.setPadding(4);
                table.addCell(cell);
            }
        }
    }
private static void performSearch(JTable table, String searchText) throws Exception {
    if (searchText == null || searchText.trim().isEmpty()) {
        JOptionPane.showMessageDialog(frame, 
            "Please enter a search term", 
            "Search Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String selectedReport = sales_reportList.getSelectedValue();
    if (selectedReport == null) {
        JOptionPane.showMessageDialog(frame, 
            "Please select a report type first", 
            "Search Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Get the table model
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    
    // First, load the full data
    loadSelectedReport(table, selectedReport);
    
    // Get the current row count (for results message)
    int totalRows = model.getRowCount();
    
    // Create a temporary model to store matching rows
    DefaultTableModel tempModel = new DefaultTableModel();
    for (int i = 0; i < model.getColumnCount(); i++) {
        tempModel.addColumn(model.getColumnName(i));
    }
    
    // Search criteria based on report type
    int columnIndex = 0;
    String formattedSearch = searchText.trim();
    
    switch (selectedReport) {
        case "Daily Sales":
            // Search by date in column 0
            columnIndex = 0;
            break;
        case "Weekly Sales":
            // Search by week number in column 0
            columnIndex = 0;
            break;
        case "Monthly Sales":
            // Search by month in column 0
            columnIndex = 0;
            break;
        case "Sales by Menu Item":
            // Search by menu item name in column 0
            columnIndex = 0;
            break;
        case "Sales by Time of day":
            // Search by date in column 0
            columnIndex = 0;
            break;
        case "Best Sellers":
            // Search by menu item name in column 0
            columnIndex = 0;
            break;
        case "Peak Hours":
            // Search by date in column 0
            columnIndex = 0;
            break;
        case "Transaction History":
            // Search by transaction ID in column 0
            columnIndex = 0;
            break;
        default:
            columnIndex = 0;
    }
    
    // Find matching rows and add to temporary model
    int matchCount = 0;
    for (int row = 0; row < model.getRowCount(); row++) {
        Object value = model.getValueAt(row, columnIndex);
        if (value != null && value.toString().toLowerCase().contains(formattedSearch.toLowerCase())) {
            // Create a row array with all the column values
            Object[] rowData = new Object[model.getColumnCount()];
            for (int col = 0; col < model.getColumnCount(); col++) {
                rowData[col] = model.getValueAt(row, col);
            }
            // Add the matching row to the temp model
            tempModel.addRow(rowData);
            matchCount++;
        }
    }
    
    // Replace the model with the filtered results
    table.setModel(tempModel);
    
    // Show results message
    if (matchCount == 0) {
        JOptionPane.showMessageDialog(frame,
            "No results found for '" + formattedSearch + "'",
            "Search Results", JOptionPane.INFORMATION_MESSAGE);
        // Reload the full data
        loadSelectedReport(table, selectedReport);
    } else {
        JOptionPane.showMessageDialog(frame,
            "Found " + matchCount + " matches out of " + totalRows + " records",
            "Search Results", JOptionPane.INFORMATION_MESSAGE);
    }
}
}