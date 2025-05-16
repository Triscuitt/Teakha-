import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import salesreportmodule.DBConnection;
import java.sql.Connection;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Eto yung main dashboard class
class dashBoardModule extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JScrollPane scrollPane, scrollPane2;
    private JTable recentOrdersTable, topDishesTable;
    private DefaultTableModel recentOrdersTableModel;
    private DefaultTableModel topDishesTableModel;

    // Dito nabubuo yung buong admin window at nilalagay lahat ng panels at
    public dashBoardModule() {
        new JFrame();
        setSize(1500, 750);
        setLayout(null);
        setUndecorated(true); 
        setLocationRelativeTo(null); 

        cardLayout = new CardLayout();

        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(280, 30, 1197, 698);
        add(mainPanel);

        // Dito nilalagay lahat ng main pages ng dashboard
        mainPanel.add(DashboardFunction(), "dashboardFunction");
        mainPanel.add(menuFunction(), "menuFunction");
        mainPanel.add(new salesreportmodule.SalesReportModule(), "salesFunction");
        mainPanel.add(new AdminSettingsPanel(), "adminSettingsFunction");

        sidePanel(); // Tawagin yung side navigation panel
        windowControl(); // Para sa custom minimize at exit
        panels(); // Design panels lang, hindi functional

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Para sa custom minimize at exit button kasi walang title bar
    private void windowControl() {
        JButton exitButton = new JButton();
        exitButton.setText("X");
        exitButton.setBounds(1455, 0, 45, 30);
        exitButton.setFont(new Font("Telegraf", Font.BOLD, 15));
        exitButton.addActionListener(event -> System.exit(0));
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(Color.RED);
            }

            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(Color.WHITE);
            }
        });
        add(exitButton);

        JButton minimizeButton = new JButton();
        minimizeButton.setText("-");
        minimizeButton.setBounds(1380, 0, 45, 30);
        minimizeButton.setFont(new Font("Telegraf", Font.BOLD, 15));
        minimizeButton.addActionListener(event -> setState(Frame.ICONIFIED));
        minimizeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                minimizeButton.setBackground(Color.decode("#d3d3d6"));
            }

            public void mouseExited(MouseEvent e) {
                minimizeButton.setBackground(Color.WHITE);
            }
        });
        add(minimizeButton);
    }

    // Design panels lang to (left, top, bottom, right) - pang aesthetic lang
    private void panels() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 280, 750);
        leftPanel.setBackground(Color.decode("#eee3d1"));
        add(leftPanel);

        JPanel topPanel = new JPanel();
        topPanel.setBounds(280, 0, 1220, 30);
        topPanel.setBackground(Color.decode("#eee3d1"));
        add(topPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(280, 729, 1220, 23);
        bottomPanel.setBackground(Color.decode("#eee3d1"));
        add(bottomPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(1477, 3, 23, 747);
        rightPanel.setBackground(Color.decode("#eee3d1"));
        add(rightPanel);
    }

    // Side navigation panel, dito lahat ng buttons para magpalit ng page
    private void sidePanel() {
        Font font = new Font("Telegraf", Font.BOLD, 18);

        // Dashboard button - para makita yung dashboard page
        JButton dashboardButton = new JButton();
        dashboardButton.setText("Dashboard");
        dashboardButton.setBounds(27, 202, 225, 46);
        dashboardButton.setFont(font);
        dashboardButton.setBackground(Color.decode("#9b9996"));
        add(dashboardButton);
        dashboardButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "dashboardFunction");
        });

        // Menu button - para makita yung menu page
        JButton menuButton = new JButton();
        menuButton.setText("Menu");
        menuButton.setBounds(27, 275, 225, 46);
        menuButton.setBackground(Color.decode("#9b9996"));
        menuButton.setFont(font);
        add(menuButton);
        menuButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "menuFunction");
        });
        String userRole = userRoleManager.getCurrentRole();

        if ("admin".equalsIgnoreCase(userRole)) {
            // Sales Report button
            JButton salesButton = new JButton();
            salesButton.setText("Sales Report");
            salesButton.setBounds(27, 348, 225, 46);
            salesButton.setBackground(Color.decode("#9b9996"));
            salesButton.setFont(font);
            salesButton.addActionListener(e -> {
                cardLayout.show(mainPanel, "salesFunction");
            });
            add(salesButton);

            // Admin Settings button
            JButton adminSettingsButton = new JButton();
            adminSettingsButton.setText("Admin Settings");
            adminSettingsButton.setBounds(27, 419, 225, 46);
            adminSettingsButton.setBackground(Color.decode("#9b9996"));
            adminSettingsButton.setFont(font);
            adminSettingsButton.addActionListener(e -> {
                cardLayout.show(mainPanel, "adminSettingsFunction");
            });
            add(adminSettingsButton);
        }

        // Logout button - para makalabas ka sa admin dashboard
        JButton logoutButton = new JButton();
        logoutButton.setText("Log Out");
        logoutButton.setBounds(11, 690, 110, 46);
        logoutButton.setBackground(Color.decode("#9b9996"));
        logoutButton.setFont(new Font("Telegraf", Font.BOLD, 16));
        add(logoutButton);
        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                dispose();
                new loginModule();
            }
        });

        // Teakha Logo sa taas ng side panel
        ImageIcon imageIcon = new ImageIcon(
                "C:/Users/Admin/Documents/Vs Code/Teakha Project/image/teakhaLogo.png");
        Image resizedImage = imageIcon.getImage().getScaledInstance(200, 170, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel teakhaLogo = new JLabel(resizedIcon);
        teakhaLogo.setBounds(41, 12, 197, 190);
        add(teakhaLogo);
    }

    // Eto yung laman ng dashboard page (may stats at panels)
    private JPanel DashboardFunction() {
        JPanel dashboardPanel = new JPanel(null);

        // Welcome label
        String welcomeUsername = userRoleManager.getCurrentUser();
        String welcome = "WELCOME!";

        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setBounds(500, 34, 500, 75);
        welcomeLabel.setText(welcome);
        welcomeLabel.setFont(new Font("Canva Sans", Font.BOLD, 50));
        dashboardPanel.add(welcomeLabel);

        JLabel user = new JLabel();
        user.setText(welcomeUsername);
        user.setFont(new Font("Telegraf", Font.BOLD, 40));
        user.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBounds(479, 109, 300, 62);
        welcomePanel.add(user, BorderLayout.CENTER);
        dashboardPanel.add(welcomePanel);

        Font font = new Font("Telegraf", Font.BOLD, 25);

        // Labels para sa mga stats
        JLabel todaysOrderLabel = new JLabel();
        todaysOrderLabel.setText("Today's Order");
        todaysOrderLabel.setFont(font);
        todaysOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel todaysCustomerLabel = new JLabel();
        todaysCustomerLabel.setText("Today's Customer");
        todaysCustomerLabel.setFont(font);
        todaysCustomerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel todaysRevenueLabel = new JLabel();
        todaysRevenueLabel.setText("Today's Revenue");
        todaysRevenueLabel.setFont(font);
        todaysRevenueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel totalOrder = new JLabel();
        totalOrder.setFont(new Font("Telegraf", Font.BOLD, 30));
        totalOrder.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel totalCustomer = new JLabel();
        totalCustomer.setFont(new Font("Telegraf", Font.BOLD, 30));
        totalCustomer.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel totalRevenue = new JLabel();
        totalRevenue.setFont(new Font("Telegraf", Font.BOLD, 30));
        totalRevenue.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel recentOrdersLabel = new JLabel();
        recentOrdersLabel.setText("Recent Orders");
        recentOrdersLabel.setFont(font);
        recentOrdersLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel topDishLabel = new JLabel();
        topDishLabel.setText("Top Dish");
        topDishLabel.setFont(font);
        topDishLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panels para sa stats at charts (design pa lang)
        JPanel todaysOrderPanel = new JPanel(new BorderLayout());
        todaysOrderPanel.setBounds(52, 200, 315, 150);
        todaysOrderPanel.setBackground(Color.decode("#ddb578"));
        todaysOrderPanel.add(todaysOrderLabel, BorderLayout.SOUTH);
        todaysOrderPanel.add(totalOrder, BorderLayout.CENTER);
        totalOrder.setHorizontalAlignment(SwingConstants.CENTER);
        dashboardPanel.add(todaysOrderPanel);

        JPanel todaysCustomerPanel = new JPanel(new BorderLayout());
        todaysCustomerPanel.setBounds(434, 200, 315, 150);
        todaysCustomerPanel.setBackground(Color.decode("#ddb578"));
        todaysCustomerPanel.add(todaysCustomerLabel, BorderLayout.SOUTH);
        todaysCustomerPanel.add(totalCustomer, BorderLayout.CENTER);
        dashboardPanel.add(todaysCustomerPanel);

        JPanel todaysRevenuePanel = new JPanel(new BorderLayout());
        todaysRevenuePanel.setBounds(829, 200, 315, 150);
        todaysRevenuePanel.setBackground(Color.decode("#ddb578"));
        todaysRevenuePanel.add(todaysRevenueLabel, BorderLayout.SOUTH);
        todaysRevenuePanel.add(totalRevenue, BorderLayout.CENTER);
        dashboardPanel.add(todaysRevenuePanel);

        JPanel recentOrdersPanel = new JPanel(new BorderLayout());
        recentOrdersPanel.setBackground(Color.decode("#ddb578"));
        recentOrdersPanel.setBounds(51, 430, 728, 283);
        dashboardPanel.add(recentOrdersPanel);

        JPanel topRecentOrdersPanel = new JPanel();
        topRecentOrdersPanel.setBounds(51, 395, 728, 40);
        topRecentOrdersPanel.setBackground(Color.decode("#b9d191"));
        topRecentOrdersPanel.add(recentOrdersLabel, BorderLayout.CENTER);
        topRecentOrdersPanel.add(recentOrdersLabel, BorderLayout.CENTER);
        dashboardPanel.add(topRecentOrdersPanel);

        String[] orderColumnNames = { "Order ID", "User ID", "Order Time", "Total Amount" };

        recentOrdersTableModel = new DefaultTableModel(orderColumnNames, 0);

        recentOrdersTable = new JTable(recentOrdersTableModel);
        recentOrdersTable.setFillsViewportHeight(true);
        recentOrdersTable.setRowHeight(37);
        recentOrdersTable.setBackground(Color.decode("#ddb578"));

        JScrollPane scrollPane = new JScrollPane(recentOrdersTable);
        recentOrdersPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel topDishesPanel = new JPanel(new BorderLayout());
        topDishesPanel.setBounds(803, 431, 371, 283);
        topDishesPanel.setBackground(Color.decode("#ddb578"));
        dashboardPanel.add(topDishesPanel);

        JPanel upperTopDishesPanel = new JPanel(new BorderLayout());
        upperTopDishesPanel.setBounds(803, 395, 371, 40);
        upperTopDishesPanel.setBackground(Color.decode("#b9d191"));
        upperTopDishesPanel.add(topDishLabel, BorderLayout.CENTER);
        dashboardPanel.add(upperTopDishesPanel);

        String[] columnNames = { "Dish Name", "Category", "Total Sold" };

        topDishesTableModel = new DefaultTableModel(columnNames, 0);

        topDishesTable = new JTable(topDishesTableModel);
        topDishesTable.setFillsViewportHeight(true);
        topDishesTable.setRowHeight(30);
        topDishesTable.setBackground(Color.decode("#ddb578"));

        JScrollPane scrollPane2 = new JScrollPane(topDishesTable);
        topDishesPanel.add(scrollPane2, BorderLayout.CENTER);

        new javax.swing.Timer(5000, e -> getTableDashBoardData(totalOrder, totalCustomer, totalRevenue,
                recentOrdersTableModel, topDishesTableModel)).start();

        getTableDashBoardData(totalOrder, totalCustomer, totalRevenue, recentOrdersTableModel, topDishesTableModel);

        return dashboardPanel;
    }

    private void getTableDashBoardData(JLabel totalOrder, JLabel totalCustomer, JLabel totalRevenue,
            DefaultTableModel recentOrdersTableModel,
            DefaultTableModel topDishesTableModel) {

        new Thread(() -> {
            try (Connection conn = DBConnection.getConnection()) {
                // Today's orders
                try (PreparedStatement ps = conn
                        .prepareStatement("SELECT COUNT(*) FROM orders WHERE DATE(order_time) = CURDATE()")) {
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        int count = rs.getInt(1);
                        SwingUtilities.invokeLater(() -> totalOrder.setText(String.valueOf(count)));
                    }
                }

                // Today's customers
                try (PreparedStatement ps = conn.prepareStatement(
                        "SELECT COUNT(DISTINCT user_id) FROM orders WHERE DATE(order_time) = CURDATE()")) {
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        SwingUtilities.invokeLater(() -> totalCustomer.setText(String.valueOf(count)));
                    }
                }

                // Today's revenue
                try (PreparedStatement ps = conn.prepareStatement(
                        "SELECT IFNULL(SUM(total_amount), 0) FROM transaction_history WHERE DATE(transaction_time) = CURDATE()")) {
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        double revenue = rs.getDouble(1);
                        SwingUtilities.invokeLater(() -> totalRevenue.setText(String.format("₱%.2f", revenue)));
                    }
                }

                // Recent Orders (last 20)
                try (PreparedStatement ps = conn.prepareStatement(
                        "SELECT order_id, user_id, order_time, total_amount FROM orders ORDER BY order_time DESC LIMIT 20 OFFSET 20")) {
                    ResultSet rs = ps.executeQuery();
                    // Clear existing rows
                    SwingUtilities.invokeLater(() -> recentOrdersTableModel.setRowCount(0));
                    while (rs.next()) {
                        Object[] row = {
                                rs.getInt("order_id"),
                                rs.getInt("user_id"),
                                rs.getTimestamp("order_time"),
                                String.format("₱%.2f", rs.getDouble("total_amount"))
                        };
                        SwingUtilities.invokeLater(() -> recentOrdersTableModel.addRow(row));

                    }
                }

                // Top Dishes (top 10 by quantity sold)
                try (PreparedStatement ps = conn.prepareStatement(
                        "SELECT m.name, m.category, SUM(oi.quantity) AS total_sold " +
                                "FROM order_items oi " +
                                "JOIN menu_items m ON oi.item_id = m.item_id " +
                                "GROUP BY m.name, m.category " +
                                "ORDER BY total_sold DESC " +
                                "LIMIT 10")) {
                    ResultSet rs = ps.executeQuery();
                    // Clear existing rows
                    SwingUtilities.invokeLater(() -> topDishesTableModel.setRowCount(0));
                    while (rs.next()) {
                        Object[] row = {
                                rs.getString("name"),
                                rs.getString("category"),
                                rs.getInt("total_sold")
                        };
                        SwingUtilities.invokeLater(() -> topDishesTableModel.addRow(row));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Eto yung laman ng Menu page (label lang for now)
    private JPanel menuFunction() {
        JPanel menuPanel = new JPanel(null);
        JLabel menuLabel = new JLabel();
        menuLabel.setText("Menu");
        menuLabel.setBounds(597, 63, 423, 63);
        menuLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        menuPanel.add(menuLabel);
        return menuPanel;
    }

    // Eto yung laman ng Account Manager page (label lang for now)
    private JPanel adminSettingsFunction() {
        JPanel adminSettingsPanel = new JPanel(null);
        JLabel adminSettingsLabel = new JLabel();
        adminSettingsLabel.setText("Admin Settings");
        adminSettingsLabel.setBounds(490, 63, 423, 63);
        adminSettingsLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        adminSettingsPanel.add(adminSettingsLabel);

        return adminSettingsPanel;
    }
}
