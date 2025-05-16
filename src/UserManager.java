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

public class UserManager extends JPanel {
    private JTextField searchField, userField, passField;
    private JComboBox<String> filterDrop;
    private JRadioButton guestRole, adminRole;
    private JLabel displaySelected;

    private JTable table;
    private DefaultTableModel model;

    public UserManager() {
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
        // TODO: create a checkbox toggle that hides/unhides the user passwords.
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        sidePanel.setBackground(UIElements.priCol);
        sidePanel.setBorder(new LineBorder(UIElements.borderCol, 5));

        // Title and Logo
        JLabel logoImg = new UIElements.customLabel(new ImageIcon("C:\\Users\\Admin\\Documents\\Vs Code\\Teakha Project\\resources\\logo.png"), 100, 100);
        JLabel titleLabel = new UIElements.customLabel("User Manager", 21);
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

        String[] options = { "user_id", "username", "password", "role" };
        filterDrop = new JComboBox<>(options);
        gbc.insets = new Insets(0, 15, 30, 15);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        sidePanel.add(filterDrop, gbc);

        // Editor
        JLabel userLabel = new UIElements.customLabel("username:");
        userLabel.setForeground(UIElements.bgCol);
        userField = new JTextField(15);

        JLabel passLabel = new UIElements.customLabel("password:");
        passLabel.setForeground(UIElements.bgCol);
        passField = new JTextField(15);

        JLabel roleLabel = new UIElements.customLabel("role:");
        roleLabel.setForeground(UIElements.bgCol);

        guestRole = new JRadioButton("guest");
        guestRole.setBackground(UIElements.priCol);
        guestRole.setForeground(UIElements.secCol);
        guestRole.setFocusPainted(false);
        guestRole.setSelected(true); // set guest by default
        adminRole = new JRadioButton("admin");
        adminRole.setBackground(UIElements.priCol);
        adminRole.setForeground(UIElements.secCol);
        adminRole.setFocusPainted(false);

        ButtonGroup groupRole = new ButtonGroup();
        groupRole.add(guestRole);
        groupRole.add(adminRole);

        gbc.insets = new Insets(0, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        sidePanel.add(userLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        sidePanel.add(userField, gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        sidePanel.add(passLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        sidePanel.add(passField, gbc);

        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        sidePanel.add(roleLabel, gbc);
        gbc.gridx = 1;
        sidePanel.add(guestRole, gbc);
        gbc.gridx = 2;
        sidePanel.add(adminRole, gbc);

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
        gbc.gridy = 10;
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
        model = new DefaultTableModel(new String[] { "ID", "Username", "Password", "Role" }, 0);
        table = new JTable(model);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        JScrollPane scrollOrigin = new JScrollPane(table);

        displaySelected = new UIElements.customLabel("Please select a user from the table", 21);
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
            String fileName = "UserRecords_Export.pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));
            doc.open();
            doc.add(new Paragraph("UserRecords"));
            doc.add(new Paragraph(" ")); // Empty line
            PdfPTable pdfTable = new PdfPTable(4);
            pdfTable.addCell("ID");
            pdfTable.addCell("Username");
            pdfTable.addCell("Password");
            pdfTable.addCell("Role");
            for (int i = 0; i < table.getRowCount(); i++) {
                pdfTable.addCell(table.getValueAt(i, 0).toString());
                pdfTable.addCell(table.getValueAt(i, 1).toString());
                pdfTable.addCell(table.getValueAt(i, 2).toString());
                pdfTable.addCell(table.getValueAt(i, 3).toString());
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                });
            }
            // if (table.getRowCount() > 0) {
            // table.setRowSelectionInterval(0, 0); // Select first row
            // }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE " + category + " LIKE ?");
                stmt.setString(1, search + "%");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    model.addRow(new Object[] {
                            rs.getString("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    });
                }
                if (table.getRowCount() != 0) {
                    table.setRowSelectionInterval(0, 0);
                } else {
                    JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pasteSelection() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String guest = guestRole.getText();
            String admin = adminRole.getText();
            String user_id = getRowData();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            stmt.setString(1, user_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userField.setText(rs.getString("username"));
                passField.setText(rs.getString("password"));
                String role = rs.getString("role");
                if (role.equals(guest)) {
                    guestRole.setSelected(true);
                } else if (role.equals(admin)) {
                    adminRole.setSelected(true);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // TODO: Editor (Add, Update, Remove)
    private void insertData() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String username = userField.getText();
            String password = passField.getText();
            String role = getRole();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                PreparedStatement stmt = conn
                        .prepareStatement("INSERT INTO users(username, password, role) VALUES (?, ?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                int add = stmt.executeUpdate();
                if (add > 0) {
                    // instead of doing a complicated getter setter(grabbing the frame from
                    // Main.java to here with something like 'class UserManagerUI(JFrame frame)' and
                    // 'new UserManagerUI(frame)')
                    // using "this" just does that directly
                    JOptionPane.showMessageDialog(this, "Successfully added the User!");
                    clearFields();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to add User!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeData() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String user_id = getRowData();
            if (user_id != null) {
                String message = "Do you want to remove this user?" +
                        "\n >> user_id: " + user_id + " <<" +
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
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE user_id = ?");
                    stmt.setString(1, user_id);
                    int delete = stmt.executeUpdate();
                    if (delete > 0) {
                        // instead of doing a complicated getter setter(grabbing the frame from
                        // Main.java to here with something like 'class UserManagerUI(JFrame frame)' and
                        // 'new UserManagerUI(frame)')
                        // using "this" just does that directly
                        JOptionPane.showMessageDialog(this, "User is successfully removed! ");
                        clearFields();
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Unable to remove the User!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "user_id is not defined, please select a field from the table!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateData() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String user_id = getRowData();
            String username = userField.getText();
            String password = passField.getText();
            String role = getRole();
            if (username.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username or Role is empty.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (user_id == null) {
                JOptionPane.showMessageDialog(this, "user_id is not defined, please select a field from the table!");
            } else {
                PreparedStatement stmt = conn
                        .prepareStatement("UPDATE users SET username = ?, password = ?, role = ? WHERE user_id = ?");
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.setString(4, user_id);
                int update = stmt.executeUpdate();
                if (update > 0) {
                    JOptionPane.showMessageDialog(this, "Successfully updated the User's info!");
                    clearFields();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to update the User's info!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displaySelected() {
        if (getRowData() != null) {
            displaySelected.setText("selected user_id: " + getRowData());
        } else {
            displaySelected.setText("Please select a user from the table");
        }
    }

    private String getRowData() {
        // if the table have rows one or more, return the first column of the selected
        // row(user_id),
        // else, select the first row then redo the former.
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return model.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }

    private String getRole() {
        if (guestRole.isSelected()) {
            return guestRole.getText();
        } else if (adminRole.isSelected()) {
            return adminRole.getText();
        }
        return null;
    }

    private void clearFields() {
        userField.setText("");
        passField.setText("");
        // guestRole.setSelected(true);
        // adminRole.setSelected(false);
    }
}
