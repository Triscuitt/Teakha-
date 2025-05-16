import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import salesreportmodule.DBConnection;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.*;

public class MenuManager extends JPanel {
    private final String[] columns = { "ID", "Name", "Description", "Category", "Price", "Available" };
    private JTextField searchField, menuField, catField, priceField;
    private JTextArea descrField;
    private JComboBox<String> filterDrop;
    private JRadioButton availYes, availNo;
    private JLabel displaySelected;

    private JTable table;
    private DefaultTableModel model;

    public MenuManager() {
        userInterface();
        try {
            loadData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void userInterface() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setBackground(UIElements.bgCol);
        gbc.insets = new Insets(25, 10, 0, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        add(sidePanel(), gbc);

        gbc.insets = new Insets(0, 10, 0, 10);
        JPanel gapPanel = new JPanel();
        gapPanel.setBackground(null);
        gbc.gridy = 1;
        gbc.weighty = 3;
        add(gapPanel, gbc);

        gbc.insets = new Insets(0, 10, 25, 10);
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.gridheight = 2;
        add(returnBtn(), gbc);
        gbc.insets = new Insets(25, 10, 25, 10);
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weighty = 3;
        gbc.weightx = 3;
        gbc.gridheight = 4;
        add(recordPanel(), gbc);
    }

    private JPanel sidePanel() {
        // Side Panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        sidePanel.setBackground(UIElements.priCol);
        sidePanel.setBorder(new LineBorder(UIElements.borderCol, 5));

        // Title and Logo
        JLabel logoImg = new UIElements.customLabel(new ImageIcon("C:\\Users\\Admin\\Documents\\Vs Code\\Teakha Project\\resources\\logo.png"), 100, 100);
        JLabel titleLabel = new UIElements.customLabel("Menu Manager", 21);
        JLabel adminLabel = new UIElements.customLabel("(Admin)", 21);
        JPanel separator = new JPanel();
        separator.setBackground(UIElements.borderCol);
        separator.setPreferredSize(new Dimension(0, 2));
        separator.setMinimumSize(new Dimension(0, 2));

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        sidePanel.add(logoImg, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1;
        sidePanel.add(titleLabel, gbc);
        gbc.insets = new Insets(10, 15, 0, 15);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 2;
        sidePanel.add(separator, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.gridy = 3;
        sidePanel.add(adminLabel, gbc);

        // Search/Filter Field
        searchField = new JTextField(15);
        JButton searchBtn = new UIElements.customButton(new ImageIcon("C:\\Users\\Admin\\Documents\\Vs Code\\Teakha Project\\resources\\Taufik_magnifying-glass.png"));

        gbc.insets = new Insets(30, 15, 15, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        sidePanel.add(searchField, gbc);
        gbc.insets = new Insets(30, 0, 15, 15);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        sidePanel.add(searchBtn, gbc);

        filterDrop = new JComboBox<>(columns);
        gbc.insets = new Insets(0, 15, 30, 15);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        sidePanel.add(filterDrop, gbc);

        // Editor
        JLabel menuLabel = new UIElements.customLabel("name:");
        menuLabel.setForeground(UIElements.bgCol);
        menuField = new JTextField(100);

        JLabel descrLabel = new UIElements.customLabel("description:");
        descrLabel.setForeground(UIElements.bgCol);
        descrField = new JTextArea();

        JLabel catLabel = new UIElements.customLabel("category:");
        catLabel.setForeground(UIElements.bgCol);
        catField = new JTextField(50);

        JLabel priceLabel = new UIElements.customLabel("price:");
        priceLabel.setForeground(UIElements.bgCol);
        priceField = new JTextField(10);

        JLabel availLabel = new UIElements.customLabel("available:");
        availLabel.setForeground(UIElements.bgCol);

        availYes = new JRadioButton("yes");
        availYes.setBackground(UIElements.priCol);
        availYes.setForeground(UIElements.secCol);
        availYes.setFocusPainted(false);
        availYes.setSelected(true); // set available by default
        availNo = new JRadioButton("no");
        availNo.setBackground(UIElements.priCol);
        availNo.setForeground(UIElements.secCol);
        availNo.setFocusPainted(false);

        ButtonGroup groupRole = new ButtonGroup();
        groupRole.add(availYes);
        groupRole.add(availNo);

        gbc.insets = new Insets(0, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        sidePanel.add(menuLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        sidePanel.add(menuField, gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.weighty = 5;
        gbc.gridwidth = 3;
        sidePanel.add(descrLabel, gbc);
        gbc.gridy = 8;
        gbc.gridheight = 2;
        sidePanel.add(descrField, gbc);

        gbc.gridy = 10;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        sidePanel.add(catLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        sidePanel.add(catField, gbc);

        gbc.gridy = 11;
        gbc.gridx = 0;
        gbc.weighty = 0;
        sidePanel.add(priceLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        sidePanel.add(priceField, gbc);

        gbc.gridy = 12;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        sidePanel.add(availLabel, gbc);
        gbc.gridx = 1;
        sidePanel.add(availYes, gbc);
        gbc.gridx = 2;
        sidePanel.add(availNo, gbc);

        Dimension buttonSize = new Dimension(65, 30);
        JButton addBtn = new UIElements.customButton("ADD");
        addBtn.setMinimumSize(buttonSize);
        addBtn.setPreferredSize(buttonSize);
        JButton updateBtn = new UIElements.customButton("UPDATE");
        updateBtn.setMinimumSize(buttonSize);
        updateBtn.setPreferredSize(buttonSize);
        JButton removeBtn = new UIElements.customButton("REMOVE");
        removeBtn.setMinimumSize(buttonSize);
        removeBtn.setPreferredSize(buttonSize);

        gbc.insets = new Insets(12, 15, 28, 15);
        gbc.gridy = 13;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        sidePanel.add(addBtn, gbc);
        gbc.insets = new Insets(12, 0, 28, 0);
        gbc.gridx = 1;
        sidePanel.add(updateBtn, gbc);
        gbc.insets = new Insets(12, 15, 28, 15);
        gbc.gridx = 2;
        sidePanel.add(removeBtn, gbc);

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchData();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insertData();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateData();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removeData();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        return sidePanel;
    }

    private JButton returnBtn() {
        // Return Button
        JButton returnBtn = new JButton("Return");
        returnBtn.setBackground(UIElements.priCol);
        returnBtn.setForeground(UIElements.borderCol);
        returnBtn.setBorder(new LineBorder(UIElements.borderCol, 5));
        returnBtn.setFocusPainted(false);

        // TODO: Return to admin settings
        returnBtn.addActionListener(e -> {
            Container parent = SwingUtilities.getAncestorOfClass(AdminSettingsPanel.class, this);
            if (parent instanceof AdminSettingsPanel) {
                ((AdminSettingsPanel) parent).showMainMenu();
            }
        });

        return returnBtn;
    }

    // TODO: add a refresh button
    private JPanel recordPanel() {
        // Record Table
        JPanel recordPanel = new JPanel();
        recordPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        recordPanel.setBackground(UIElements.priCol);
        recordPanel.setBorder(new LineBorder(UIElements.borderCol, 5));
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        JScrollPane scrollOrigin = new JScrollPane(table);

        displaySelected = new UIElements.customLabel("Please select an item from the table", 21);
        gbc.insets = new Insets(30, 25, 15, 25);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 10;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        recordPanel.add(scrollOrigin, gbc);
        gbc.insets = new Insets(15, 25, 20, 0);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weighty = 0;
        gbc.weightx = 10;
        gbc.gridwidth = 1;
        recordPanel.add(displaySelected, gbc);

        JButton exportButton = new UIElements.customButton(new ImageIcon("C:\\Users\\Admin\\Documents\\Vs Code\\Teakha Project\\resources\\ColorCods-pdf.png"), 20, 20);
        JButton reloadButton = new UIElements.customButton(new ImageIcon("C:\\Users\\Admin\\Documents\\Vs Code\\Teakha Project\\resources\\GregorCresnar-reload.png"), 20,
                20);
        gbc.insets = new Insets(15, 0, 20, 12);
        gbc.gridx = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;
        recordPanel.add(exportButton, gbc);
        gbc.insets = new Insets(15, 0, 20, 25);
        gbc.gridx = 2;
        recordPanel.add(reloadButton, gbc);
        //
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                getRowData();
                try {
                    pasteSelection();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                displaySelected();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToPDF();
            }
        });

        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadData();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return recordPanel;
    }

    private void exportToPDF() {
        Document doc = new Document();
        try {
            String fileName = "MenuRecords_Export.pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));
            doc.open();
            doc.add(new Paragraph("MenuRecords"));
            doc.add(new Paragraph(" ")); // Empty line
            PdfPTable pdfTable = new PdfPTable(6);
            pdfTable.addCell("ID");
            pdfTable.addCell("Name");
            pdfTable.addCell("Description");
            pdfTable.addCell("Category");
            pdfTable.addCell("Price");
            pdfTable.addCell("Available");
            for (int i = 0; i < table.getRowCount(); i++) {
                pdfTable.addCell(table.getValueAt(i, 0).toString());
                pdfTable.addCell(table.getValueAt(i, 1).toString());
                pdfTable.addCell(table.getValueAt(i, 2).toString());
                pdfTable.addCell(table.getValueAt(i, 3).toString());
                pdfTable.addCell(table.getValueAt(i, 4).toString());
                pdfTable.addCell(table.getValueAt(i, 5).toString());
            }
            doc.add(pdfTable);
            JOptionPane.showMessageDialog(this, "PDF saved as " + fileName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "PDF Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            doc.close();
        }
    }

    // Functionality
    private void loadData() throws Exception {
        model.setRowCount(0); // Clear table
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM menu_items");
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("item_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getString("price"),
                        rs.getString("available")
                });
            }
            // if (table.getRowCount() > 0) {
            // table.setRowSelectionInterval(0, 0); // Select first row
            // }
        } catch (SQLException e) {
            // JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error",
            // JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchData() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String category = filterDrop.getSelectedItem().toString();
            String search = searchField.getText();
            if (search.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Search Field is Empty!");
            } else {
                model.setRowCount(0); // Clear table
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT * FROM menu_items WHERE " + category + " LIKE ?");
                stmt.setString(1, search + "%");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    model.addRow(new Object[] {
                            rs.getString("item_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getString("price"),
                            rs.getString("available")
                    });
                }
                if (table.getRowCount() != 0) {
                    table.setRowSelectionInterval(0, 0);
                } else {
                    JOptionPane.showMessageDialog(this, "Menu Item not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pasteSelection() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String item_id = getRowData();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM menu_items WHERE item_id = ?");
            stmt.setString(1, item_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                menuField.setText(rs.getString("name"));
                descrField.setText(rs.getString("description"));
                catField.setText(rs.getString("category"));
                priceField.setText(rs.getString("price"));
                boolean available = rs.getBoolean("available");
                // if available, select available
                if (available) {
                    availYes.setSelected(true);
                } else {
                    availNo.setSelected(true);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // TODO: Editor (Add, Update, Remove)
    private void insertData() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String menuName = menuField.getText();
            String description = descrField.getText();
            String category = catField.getText();
            String price = priceField.getText();
            boolean available = getAvailable();
            if (menuName.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Item Name or Price is empty!", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO menu_items(name, description, category, price, available) VALUES (?, ?, ?, ?, ?)");
                stmt.setString(1, menuName);
                stmt.setString(2, description);
                stmt.setString(3, category);
                stmt.setString(4, price);
                stmt.setBoolean(5, available);
                int add = stmt.executeUpdate();
                if (add > 0) {
                    JOptionPane.showMessageDialog(this, "Menu Item is added successfully!");
                    clearFields();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to add Menu Item!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeData() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String item_id = getRowData();
            if (item_id != null) {
                String message = "Do you want to remove this Menu Item?" +
                        "\n >> item_id: " + item_id + " <<" +
                        "\n" +
                        "\n WARNING: This action is irreversible!";
                int delWarning = JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Warning",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                // if pressed yes
                if (delWarning == 0) {
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM menu_items WHERE item_id = ?");
                    stmt.setString(1, item_id);
                    int delete = stmt.executeUpdate();
                    if (delete > 0) {
                        JOptionPane.showMessageDialog(this, "Menu Item is successfully removed!");
                        clearFields();
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Unable to remove the Menu Item!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "item_id not defined, please select a field from the table!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateData() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String item_id = getRowData();
            String menuName = menuField.getText();
            String description = descrField.getText();
            String category = catField.getText();
            String price = priceField.getText();
            boolean available = getAvailable();
            if (menuName.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Item Name or Price is empty.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (item_id != null) {
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE menu_items SET name = ?, description = ?, category = ?, price = ?, available = ? WHERE item_id = ?");
                stmt.setString(1, menuName);
                stmt.setString(2, description);
                stmt.setString(3, category);
                stmt.setString(4, price);
                stmt.setBoolean(5, available);
                stmt.setString(6, item_id);
                int update = stmt.executeUpdate();
                if (update > 0) {
                    JOptionPane.showMessageDialog(this, "Successfully updated the Menu Item!");
                    clearFields();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to update the Menu Item!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "item_id not defined, please select a field from the table!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displaySelected() {
        if (getRowData() != null) {
            displaySelected.setText("selected item_id: " + getRowData());
        } else {
            displaySelected.setText("Please select a menu item from the table");
        }
    }

    private String getRowData() {
        // if the table have rows one or more, return the first column of the selected
        // row(item_id),
        // else, select the first row then redo the former.
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return model.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }

    private boolean getAvailable() {
        if (availYes.isSelected()) {
            return true;
        } else if (availNo.isSelected()) {
            return false;
        }
        return true;
    }

    private void clearFields() {
        menuField.setText("");
        descrField.setText("");
        catField.setText("");
        priceField.setText("");
        // availYes.setSelected(true);
        // availNo.setSelected(false);
    }
}