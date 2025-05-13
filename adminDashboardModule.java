import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Eto yung main admin dashboard class
class adminDashboardModule {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Constructor ng admin dashboard
    // Dito nabubuo yung buong admin window at nilalagay lahat ng panels at
    // functionalities
    public adminDashboardModule() {
        frame = new JFrame();
        frame.setSize(1500, 750);
        frame.setLayout(null);
        frame.setUndecorated(true); // Walang title bar
        frame.setLocationRelativeTo(null); // Center yung window

        cardLayout = new CardLayout();

        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(280, 30, 1197, 698);
        frame.add(mainPanel);

        // Dito nilalagay lahat ng main pages ng admin dashboard
        mainPanel.add(dashboardFunction(), "dashboardFunction");
        mainPanel.add(menuFunction(), "menuFunction");
        mainPanel.add(orderFunction(), "orderFunction");
        mainPanel.add(orderHistoryFunction(), "orderHistoryFunction");
        mainPanel.add(inventoryFunction(), "inventoryFunction");
        mainPanel.add(accountManagerFunction(), "accountManagerFunction");

        sidePanel(); // Tawagin yung side navigation panel
        windowControl(); // Para sa custom minimize at exit
        panels(); // Design panels lang, hindi functional

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        frame.add(exitButton);

        JButton minimizeButton = new JButton();
        minimizeButton.setText("-");
        minimizeButton.setBounds(1380, 0, 45, 30);
        minimizeButton.setFont(new Font("Telegraf", Font.BOLD, 10));
        minimizeButton.addActionListener(event -> frame.setState(Frame.ICONIFIED));
        minimizeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                minimizeButton.setBackground(Color.decode("#d3d3d6"));
            }

            public void mouseExited(MouseEvent e) {
                minimizeButton.setBackground(Color.WHITE);
            }
        });
        frame.add(minimizeButton);
    }

    // Design panels lang to (left, top, bottom, right) - pang aesthetic lang
    private void panels() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 280, 750);
        leftPanel.setBackground(Color.decode("#eee3d1"));
        frame.add(leftPanel);

        JPanel topPanel = new JPanel();
        topPanel.setBounds(280, 0, 1220, 30);
        topPanel.setBackground(Color.decode("#eee3d1"));
        frame.add(topPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(280, 729, 1220, 23);
        bottomPanel.setBackground(Color.decode("#eee3d1"));
        frame.add(bottomPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(1477, 3, 23, 747);
        rightPanel.setBackground(Color.decode("#eee3d1"));
        frame.add(rightPanel);
    }

    // Side navigation panel, dito lahat ng buttons para magpalit ng page
    private void sidePanel() {
        Font font = new Font("Telegraf", Font.BOLD, 18);

        // Dashboard button - para makita yung dashboard page
        JButton dashboardButton = new JButton();
        dashboardButton.setText("DASHBOARD");
        dashboardButton.setBounds(27, 202, 225, 46);
        dashboardButton.setFont(font);
        dashboardButton.setBackground(Color.decode("#9b9996"));
        frame.add(dashboardButton);
        dashboardButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "dashboardFunction");
        });

        // Menu button - para makita yung menu page
        JButton menuButton = new JButton();
        menuButton.setText("MENU");
        menuButton.setBounds(27, 275, 225, 46);
        menuButton.setBackground(Color.decode("#9b9996"));
        menuButton.setFont(font);
        frame.add(menuButton);
        menuButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "menuFunction");
        });

        // Order button - para makita yung order page
        JButton orderButton = new JButton();
        orderButton.setText("ORDER");
        orderButton.setBounds(27, 348, 225, 46);
        orderButton.setBackground(Color.decode("#9b9996"));
        orderButton.setFont(font);
        frame.add(orderButton);
        orderButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "orderFunction");
        });

        // Order History button - para makita yung order history page
        JButton orderHistoryButton = new JButton();
        orderHistoryButton.setText("ORDER HISTORY");
        orderHistoryButton.setBounds(27, 421, 225, 46);
        orderHistoryButton.setBackground(Color.decode("#9b9996"));
        orderHistoryButton.setFont(font);
        frame.add(orderHistoryButton);
        orderHistoryButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "orderHistoryFunction");
        });

        // Inventory button - para makita yung inventory page
        JButton inventoryButton = new JButton();
        inventoryButton.setText("INVENTORY");
        inventoryButton.setBounds(27, 494, 225, 46);
        inventoryButton.setBackground(Color.decode("#9b9996"));
        inventoryButton.setFont(font);
        frame.add(inventoryButton);
        inventoryButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "inventoryFunction");
        });

        // Account Manager button - para makita yung account manager page
        JButton accountManagerButton = new JButton();
        accountManagerButton.setText("ACCOUNT MANAGER");
        accountManagerButton.setBounds(27, 567, 225, 46);
        accountManagerButton.setBackground(Color.decode("#9b9996"));
        accountManagerButton.setFont(font);
        frame.add(accountManagerButton);
        accountManagerButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "accountManagerFunction");
        });

        // Logout button - para makalabas ka sa admin dashboard
        JButton logoutButton = new JButton();
        logoutButton.setText("LOG OUT");
        logoutButton.setBounds(11, 690, 110, 46);
        logoutButton.setBackground(Color.decode("#9b9996"));
        logoutButton.setFont(new Font("Telegraf", Font.BOLD, 16));
        frame.add(logoutButton);
        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to logout?", "Confirm Logout",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                frame.dispose();
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
        frame.add(teakhaLogo);
    }

    // Eto yung laman ng dashboard page (may stats at panels)
    private JPanel dashboardFunction() {
        JPanel dashboardPanel = new JPanel(null);

        // Welcome label para kay admin
        String welcomeUsername = userSessionManager.getCurrentUser();
        JLabel welcomeAdminLabel = new JLabel();
        welcomeAdminLabel.setText("Welcome " + welcomeUsername + " !");
        welcomeAdminLabel.setBounds(478, 63, 423, 63);
        welcomeAdminLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        dashboardPanel.add(welcomeAdminLabel);

        Font font = new Font("Telegraf", Font.BOLD, 18);

        // Labels para sa mga stats
        JLabel todaysOrderLabel = new JLabel();
        todaysOrderLabel.setBounds(132, 310, 143, 24);
        todaysOrderLabel.setText("Today's Order");
        todaysOrderLabel.setFont(font);
        dashboardPanel.add(todaysOrderLabel);

        JLabel todaysCustomerLabel = new JLabel();
        todaysCustomerLabel.setBounds(502, 310, 198, 24);
        todaysCustomerLabel.setText("Today's Customer");
        todaysCustomerLabel.setFont(font);
        dashboardPanel.add(todaysCustomerLabel);

        JLabel todaysRevenueLabel = new JLabel();
        todaysRevenueLabel.setBounds(912, 310, 172, 24);
        todaysRevenueLabel.setText("Today's Revenue");
        todaysRevenueLabel.setFont(font);
        dashboardPanel.add(todaysRevenueLabel);

        JLabel recentOrdersLabel = new JLabel();
        recentOrdersLabel.setBounds(325, 395, 178, 31);
        recentOrdersLabel.setText("Recent Orders");
        recentOrdersLabel.setFont(font);
        dashboardPanel.add(recentOrdersLabel);

        JLabel topDishLabel = new JLabel();
        topDishLabel.setBounds(936, 395, 104, 31);
        topDishLabel.setText("Top Dish");
        topDishLabel.setFont(font);
        dashboardPanel.add(topDishLabel);

        // Panels para sa stats at charts (design lang)
        JPanel todaysOrderPanel = new JPanel();
        todaysOrderPanel.setBounds(52, 200, 315, 150);
        todaysOrderPanel.setBackground(Color.decode("#ddb578"));
        dashboardPanel.add(todaysOrderPanel);

        JPanel todaysCustomerPanel = new JPanel();
        todaysCustomerPanel.setBounds(434, 200, 315, 150);
        todaysCustomerPanel.setBackground(Color.decode("#ddb578"));
        dashboardPanel.add(todaysCustomerPanel);

        JPanel todaysRevenuePanel = new JPanel();
        todaysRevenuePanel.setBounds(829, 200, 315, 150);
        todaysRevenuePanel.setBackground(Color.decode("#ddb578"));
        dashboardPanel.add(todaysRevenuePanel);

        JPanel recentOrdersPanel = new JPanel();
        recentOrdersPanel.setBackground(Color.decode("#ddb578"));
        recentOrdersPanel.setBounds(51, 430, 728, 283);
        dashboardPanel.add(recentOrdersPanel);

        JPanel topRecentOrdersPanel = new JPanel();
        topRecentOrdersPanel.setBounds(51, 380, 728, 55);
        topRecentOrdersPanel.setBackground(Color.decode("#b9d191"));
        dashboardPanel.add(topRecentOrdersPanel);

        JPanel topDishesPanel = new JPanel();
        topDishesPanel.setBounds(803, 431, 371, 283);
        topDishesPanel.setBackground(Color.decode("#ddb578"));
        dashboardPanel.add(topDishesPanel);

        JPanel upperTopDishesPanel = new JPanel();
        upperTopDishesPanel.setBounds(803, 380, 371, 55);
        upperTopDishesPanel.setBackground(Color.decode("#b9d191"));
        dashboardPanel.add(upperTopDishesPanel);

        return dashboardPanel;
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

    // Eto yung laman ng Order page (label lang for now)
    private JPanel orderFunction() {
        JPanel orderPanel = new JPanel(null);
        JLabel orderLabel = new JLabel();
        orderLabel.setText("Order");
        orderLabel.setBounds(597, 63, 423, 63);
        orderLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        orderPanel.add(orderLabel);

        return orderPanel;
    }

    // Eto yung laman ng Order History page (label lang for now)
    private JPanel orderHistoryFunction() {
        JPanel orderHistoryPanel = new JPanel(null);
        JLabel orderHistoryLabel = new JLabel();
        orderHistoryLabel.setText("Order History");
        orderHistoryLabel.setBounds(530, 63, 423, 63);
        orderHistoryLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        orderHistoryPanel.add(orderHistoryLabel);

        return orderHistoryPanel;
    }

    // Eto yung laman ng Inventory page (label lang for now)
    private JPanel inventoryFunction() {
        JPanel inventoryPanel = new JPanel(null);
        JLabel inventoryLabel = new JLabel();
        inventoryLabel.setText("Inventory");
        inventoryLabel.setBounds(597, 63, 423, 63);
        inventoryLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        inventoryPanel.add(inventoryLabel);

        return inventoryPanel;
    }

    // Eto yung laman ng Account Manager page (label lang for now)
    private JPanel accountManagerFunction() {
        JPanel accountManagerPanel = new JPanel(null);
        JLabel accountManagerLabel = new JLabel();
        accountManagerLabel.setText("Account Manager");
        accountManagerLabel.setBounds(490, 63, 423, 63);
        accountManagerLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        accountManagerPanel.add(accountManagerLabel);

        return accountManagerPanel;
    }
}
