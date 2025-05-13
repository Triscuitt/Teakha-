import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/*
 * Same lang ito sa adminDashboardModule wala naman pinagka-iba bukod sa mga buttons and panels
 * 
 */
class guestDashboardModule {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public guestDashboardModule() {
        frame = new JFrame();
        frame.setSize(1500, 750);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();

        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(280, 30, 1197, 698);
        frame.add(mainPanel);

        mainPanel.add(dashboardFunction(), "dashboardFunction");
        mainPanel.add(menuFunction(), "menuFunction");
        mainPanel.add(orderFunction(), "orderFunction");
        mainPanel.add(orderHistoryFunction(), "orderHistoryFunction");
        mainPanel.add(reservationFunction(), "reservationFunction");

        sidePanel();
        windowControl();
        panels();

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

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

    private void panels() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 280, 750);
        leftPanel.setBackground(Color.decode("#eee3d1"));
        leftPanel.setOpaque(true);
        frame.add(leftPanel);

        JPanel topPanel = new JPanel();
        topPanel.setBounds(280, 0, 1220, 30);
        topPanel.setBackground(Color.decode("#eee3d1"));
        topPanel.setOpaque(true);
        frame.add(topPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(280, 729, 1220, 23);
        bottomPanel.setBackground(Color.decode("#eee3d1"));
        bottomPanel.setOpaque(true);
        frame.add(bottomPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(1477, 3, 23, 747);
        rightPanel.setBackground(Color.decode("#eee3d1"));
        rightPanel.setOpaque(true);
        frame.add(rightPanel);
    }

    private void sidePanel() {
        Font font = new Font("Telegraf", Font.BOLD, 18);

        JButton dashboardButton = new JButton();
        dashboardButton.setText("DASHBOARD");
        dashboardButton.setBounds(27, 202, 225, 46);
        dashboardButton.setFont(font);
        dashboardButton.setBackground(Color.decode("#9b9996"));
        frame.add(dashboardButton);

        dashboardButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "dashboardFunction");
        });

        JButton menuButton = new JButton();
        menuButton.setText("MENU");
        menuButton.setBounds(27, 275, 225, 46);
        menuButton.setBackground(Color.decode("#9b9996"));
        menuButton.setFont(font);
        frame.add(menuButton);

        menuButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "menuFunction");
        });

        JButton orderButton = new JButton();
        orderButton.setText("ORDER");
        orderButton.setBounds(27, 348, 225, 46);
        orderButton.setBackground(Color.decode("#9b9996"));
        orderButton.setFont(font);
        frame.add(orderButton);

        orderButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "orderFunction");
        });

        JButton orderHistoryButton = new JButton();
        orderHistoryButton.setText("ORDER HISTORY");
        orderHistoryButton.setBounds(27, 421, 225, 46);
        orderHistoryButton.setBackground(Color.decode("#9b9996"));
        orderHistoryButton.setFont(font);
        frame.add(orderHistoryButton);

        orderHistoryButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "orderHistoryFunction");
        });

        JButton reservationButton = new JButton();
        reservationButton.setText("RESERVATION");
        reservationButton.setBounds(27, 494, 225, 46);
        reservationButton.setBackground(Color.decode("#9b9996"));
        reservationButton.setFont(font);
        frame.add(reservationButton);

        reservationButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "reservationFunction");
        });

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

        ImageIcon imageIcon = new ImageIcon(
                "C:/Users/Admin/Documents/Vs Code/Teakha Project/image/teakhaLogo.png");
        Image resizedImage = imageIcon.getImage().getScaledInstance(200, 170, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel teakhaLogo = new JLabel(resizedIcon);
        teakhaLogo.setBounds(41, 12, 197, 190);
        frame.add(teakhaLogo);
    }

    private JPanel dashboardFunction() {
        JPanel dashboardPanel = new JPanel(null);

        String welcomeUsername = userSessionManager.getCurrentRole();
        JLabel welcomeTableLabel = new JLabel();
        welcomeTableLabel.setText("Welcome " + welcomeUsername + " !");
        welcomeTableLabel.setBounds(420, 85, 423, 63);
        welcomeTableLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        dashboardPanel.add(welcomeTableLabel);

        Font font = new Font("Telegraf", Font.BOLD, 20);

        JLabel topDishesLabel = new JLabel();
        topDishesLabel.setBounds(226, 202, 133, 31);
        topDishesLabel.setText("Top Dishes");
        topDishesLabel.setFont(font);
        dashboardPanel.add(topDishesLabel);

        JLabel orderStatusLabel = new JLabel();
        orderStatusLabel.setBounds(800, 202, 158, 31);
        orderStatusLabel.setText("Order Status");
        orderStatusLabel.setFont(font);
        dashboardPanel.add(orderStatusLabel);

        JLabel customerFeedbackLabel = new JLabel();
        customerFeedbackLabel.setBounds(770, 405, 251, 31);
        customerFeedbackLabel.setText("Customer Feedback");
        customerFeedbackLabel.setFont(font);
        dashboardPanel.add(customerFeedbackLabel);

        JPanel topDishesPanel = new JPanel();
        topDishesPanel.setBounds(40, 242, 505, 480);
        topDishesPanel.setBackground(Color.decode("#ddb578"));
        topDishesPanel.setOpaque(true);
        dashboardPanel.add(topDishesPanel);

        JPanel upperTopDishesPanel = new JPanel();
        upperTopDishesPanel.setBounds(40, 186, 505, 56);
        upperTopDishesPanel.setBackground(Color.decode("#b9d191"));
        upperTopDishesPanel.setOpaque(true);
        dashboardPanel.add(upperTopDishesPanel);

        JPanel statusOrderPanel = new JPanel();
        statusOrderPanel.setBounds(568, 241, 622, 133);
        statusOrderPanel.setBackground(Color.decode("#ddb578"));
        statusOrderPanel.setOpaque(true);
        dashboardPanel.add(statusOrderPanel);

        JPanel topStatusOrderPanel = new JPanel();
        topStatusOrderPanel.setBounds(568, 185, 622, 56);
        topStatusOrderPanel.setBackground(Color.decode("#b9d191"));
        topStatusOrderPanel.setOpaque(true);
        dashboardPanel.add(topStatusOrderPanel);

        JPanel customerFeedbackPanel = new JPanel();
        customerFeedbackPanel.setBounds(568, 444, 622, 276);
        customerFeedbackPanel.setBackground(Color.decode("#ddb578"));
        customerFeedbackPanel.setOpaque(true);
        dashboardPanel.add(customerFeedbackPanel);

        JPanel topCustomerFeedbackPanel = new JPanel();
        topCustomerFeedbackPanel.setBounds(568, 391, 622, 56);
        topCustomerFeedbackPanel.setBackground(Color.decode("#b9d191"));
        topCustomerFeedbackPanel.setOpaque(true);
        dashboardPanel.add(topCustomerFeedbackPanel);

        return dashboardPanel;
    }

    private JPanel menuFunction() {
        JPanel menuPanel = new JPanel(null);
        JLabel menuLabel = new JLabel();
        menuLabel.setText("Menu");
        menuLabel.setBounds(597, 63, 423, 63);
        menuLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        menuPanel.add(menuLabel);
        return menuPanel;
    }

    private JPanel orderFunction() {
        JPanel orderPanel = new JPanel(null);
        JLabel orderLabel = new JLabel();
        orderLabel.setText("Order");
        orderLabel.setBounds(597, 63, 423, 63);
        orderLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        orderPanel.add(orderLabel);

        return orderPanel;
    }

    private JPanel orderHistoryFunction() {
        JPanel orderHistoryPanel = new JPanel(null);
        JLabel orderHistoryLabel = new JLabel();
        orderHistoryLabel.setText("Order History");
        orderHistoryLabel.setBounds(530, 63, 423, 63);
        orderHistoryLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        orderHistoryPanel.add(orderHistoryLabel);

        return orderHistoryPanel;
    }

    private JPanel reservationFunction() {
        JPanel reservationPanel = new JPanel(null);
        JLabel reservationLabel = new JLabel();
        reservationLabel.setText("Reservation");
        reservationLabel.setBounds(530, 63, 423, 63);
        reservationLabel.setFont(new Font("Canva Sans", Font.BOLD, 40));
        reservationPanel.add(reservationLabel);

        return reservationPanel;
    }
}
