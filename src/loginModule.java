import javax.swing.*;

import salesreportmodule.DBConnection;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class loginModule extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    Font font = new Font("Telegraf", Font.BOLD, 15);

    // Eto yung constructor, dito nabubuo yung buong login window natin.
    public loginModule() {
        new JFrame();
        setSize(1197, 696);
        setLayout(null);
        setUndecorated(true); // Para mawala yung Title bar
        setLocationRelativeTo(null); // Para ma center yung frame

        windowControl(); // Tawagin yung custom minimize at exit
        panels(); // Tawagin yung top at bottom panel

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(0, 30, 1197, 636);
        add(mainPanel);

        // Dito nilalagay yung iba't ibang pages (main, login, signup)
        mainPanel.add(mainPage(), "mainPage");
        mainPanel.add(loginPage(), "loginPage");
        mainPanel.add(signUpPage(), "signUpPage");

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Eto yung method na nagche-check kung tama yung username at password mo sa
    private String authenticatedUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Pag may nahanap, ibabalik yung username
                return rs.getString("username");
            } else {
                // Pag wala, null ang balik
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Eto naman yung method na nagreregister ng bagong user sa database.
    private String registerUser(String username, String password) {
        String query = "INSERT INTO users (username, password, role) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, "guest");

            stmt.executeUpdate();
            return "Registration Successful! You can now Log in.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Registration Failed due to error.";
        }
    }

    // Dito ginagawa yung sariling minimize at exit button kasi walang default na
    // title bar.
    private void windowControl() {
        JButton exitButton = new JButton();
        exitButton.setText("X");
        exitButton.setBounds(1152, 0, 45, 30);
        exitButton.setFont(font);
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
        minimizeButton.setBounds(1082, 0, 45, 30);
        minimizeButton.setFont(font);
        minimizeButton.addActionListener(event -> setState(Frame.ICONIFIED));
        minimizeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                minimizeButton.setBackground(Color.RED);
            }

            public void mouseExited(MouseEvent e) {
                minimizeButton.setBackground(Color.WHITE);
            }
        });
        add(minimizeButton);
    }

    // Para lang to sa design ng taas at baba ng window, aesthetic purposes lang.
    private void panels() {
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 1197, 30);
        topPanel.setBackground(Color.decode("#eee3d1"));
        add(topPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 666, 1197, 30);
        bottomPanel.setBackground(Color.decode("#eee3d1"));
        add(bottomPanel);
    }

    // Dito yung welcome page, may login at create account na buttons.
    private JPanel mainPage() {
        JPanel mainPage = new JPanel(null);

        mainPage.setBackground(Color.decode("#faf8f7"));

        JButton loginNow = new JButton();
        loginNow.setBounds(459, 495, 278, 39);
        loginNow.setText("Login Now");
        loginNow.setBackground(Color.decode("#eee3d1"));
        loginNow.setFont(font);
        mainPage.add(loginNow);

        JButton createAccount = new JButton();
        createAccount.setBounds(459, 558, 278, 40);
        createAccount.setText("Create Account");
        createAccount.setBackground(Color.decode("#eee3d1"));
        createAccount.setFont(font);
        mainPage.add(createAccount);

        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setBounds(470, 61, 300, 54);
        welcomeLabel.setText("Welcome!");
        welcomeLabel.setFont(new Font("Telegraf", Font.BOLD, 60));
        welcomeLabel.setForeground(Color.decode("#dba245"));
        mainPage.add(welcomeLabel);

        String paragraph = "<html>Our dishes are a mix of the traditional and the modern - a balanced combination of your well-loved Chinese comfort food. Taste the fresh and authentic at Teakha.<br><br>"
                + "View our menu and conveniently order your Teakha favorites in just a few clicks.</html>";

        JLabel text = new JLabel();
        text.setBounds(310, 144, 597, 116);
        text.setText(paragraph);
        text.setFont(new Font("Telegraf", Font.PLAIN, 15));
        text.setForeground(Color.decode("#dba245"));
        mainPage.add(text);

        ImageIcon imageIcon = new ImageIcon(
                "C:/Users/Admin/Documents/Vs Code/Teakha Project/image/teakhaLogo.png");
        Image resizedImage = imageIcon.getImage().getScaledInstance(204, 204, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel teakhaLogo = new JLabel(resizedIcon);
        teakhaLogo.setBounds(493, 275, 204, 204);
        mainPage.add(teakhaLogo);

        // Pag pinindot yung login, lilipat sa loginPage
        loginNow.addActionListener(e -> {
            cardLayout.show(mainPanel, "loginPage");
        });

        // Pag pinindot yung create account, lilipat sa signUpPage
        createAccount.addActionListener(e -> {
            cardLayout.show(mainPanel, "signUpPage");
        });

        return mainPage;

    }

    // Dito ka maglalagay ng username at password para mag-login.
    private JPanel loginPage() {
        JPanel loginPage = new JPanel(null);

        loginPage.setBackground(Color.decode("#faf8f7"));

        JLabel loginLabel = new JLabel();
        loginLabel.setBounds(545, 52, 132, 59);
        loginLabel.setText("Login");
        loginLabel.setFont(new Font("Telegraf", Font.BOLD, 44));
        loginLabel.setForeground(Color.decode("#dba245"));
        loginPage.add(loginLabel);

        ImageIcon imageIcon = new ImageIcon(
                "C:/Users/Admin/Documents/Vs Code/Teakha Project/image/teakhaLogo.png");
        Image resizedImage = imageIcon.getImage().getScaledInstance(204, 204, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel teakhaLogo = new JLabel(resizedIcon);
        teakhaLogo.setBounds(496, 142, 204, 204);
        loginPage.add(teakhaLogo);

        String paragraph = "<html>Place to Login to use the app.</html>";

        JLabel text = new JLabel();
        text.setBounds(491, 111, 235, 23);
        text.setText(paragraph);
        text.setFont(font);
        loginPage.add(text);

        JLabel userNameLabel = new JLabel();
        userNameLabel.setBounds(459, 360, 135, 23);
        userNameLabel.setText("Username");
        userNameLabel.setFont(font);
        loginPage.add(userNameLabel);

        JTextField userNameField = new JTextField(20);
        userNameField.setBounds(459, 384, 278, 38);
        userNameField.setBackground(Color.decode("#eee3d1"));
        userNameField.setFont(font);
        loginPage.add(userNameField);

        JLabel passwordLabel = new JLabel();
        passwordLabel.setBounds(459, 435, 93, 23);
        passwordLabel.setText("Password");
        passwordLabel.setFont(font);
        loginPage.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(459, 459, 278, 38);
        passwordField.setBackground(Color.decode("#eee3d1"));
        loginPage.add(passwordField);

        JButton loginButton = new JButton();
        loginButton.setBounds(506, 530, 194, 38);
        loginButton.setText("Login");
        loginButton.setBackground(Color.decode("#eee3d1"));
        loginButton.setFont(font);
        loginPage.add(loginButton);

        // Pag pinindot yung login, iche-check kung tama credentials mo
        loginButton.addActionListener(e -> {
            String username = userNameField.getText();
            String password = new String(passwordField.getPassword());
            String role = authenticatedUser(username, password);
            if (role != null) {
                userRoleManager.setCurrentUser(username, role);
                dispose();
                new dashBoardModule();

            } else {
                JOptionPane.showMessageDialog(this,"Invalid username or password.");
            }
        });

        JButton backButton = new JButton();
        backButton.setBounds(506, 580, 194, 38);
        backButton.setText("Home");
        backButton.setBackground(Color.decode("#eee3d1"));
        backButton.setFont(font);
        loginPage.add(backButton);

        // Pag gusto mong bumalik sa main page, pindutin lang to
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "mainPage");
        });

        return loginPage;
    }

    // Dito ka magreregister ng bagong account.
    private JPanel signUpPage() {
        JPanel signUpPage = new JPanel(null);

        signUpPage.setBackground(Color.decode("#faf8f7"));

        JLabel signUpLabel = new JLabel();
        signUpLabel.setBounds(535, 52, 132, 59);
        signUpLabel.setText("SignUp");
        signUpLabel.setFont(new Font("Telegraf", Font.BOLD, 38));
        signUpLabel.setForeground(Color.decode("#dba245"));
        signUpPage.add(signUpLabel);

        ImageIcon imageIcon = new ImageIcon(
                "C:/Users/Admin/Documents/Vs Code/Teakha Project/image/teakhaLogo.png");
        Image resizedImage = imageIcon.getImage().getScaledInstance(204, 204, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel teakhaLogo = new JLabel(resizedIcon);
        teakhaLogo.setBounds(496, 142, 204, 204);
        signUpPage.add(teakhaLogo);

        String paragraph = "<html>Place to enter your details.</html>";

        JLabel text = new JLabel();
        text.setBounds(510, 111, 235, 23);
        text.setText(paragraph);
        text.setFont(font);
        signUpPage.add(text);

        JLabel userNameLabel = new JLabel();
        userNameLabel.setBounds(459, 360, 135, 23);
        userNameLabel.setText("Username");
        userNameLabel.setFont(font);
        signUpPage.add(userNameLabel);

        JTextField userNameField = new JTextField(20);
        userNameField.setBounds(459, 384, 278, 38);
        userNameField.setBackground(Color.decode("#eee3d1"));
        userNameField.setFont(font);
        signUpPage.add(userNameField);

        JLabel passwordLabel = new JLabel();
        passwordLabel.setBounds(459, 435, 93, 23);
        passwordLabel.setText("Password");
        passwordLabel.setFont(font);
        signUpPage.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(459, 459, 278, 38);
        passwordField.setBackground(Color.decode("#eee3d1"));
        signUpPage.add(passwordField);

        JButton signUpButton = new JButton();
        signUpButton.setBounds(506, 530, 194, 38);
        signUpButton.setText("SignUp");
        signUpButton.setBackground(Color.decode("#eee3d1"));
        signUpButton.setFont(font);
        signUpPage.add(signUpButton);

        // Pag pinindot yung signup, magtatry magregister ng bagong user
        signUpButton.addActionListener(e -> {
            String username = userNameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password");
                return;
            }
            String result = registerUser(username, password);
            if (result.startsWith("Registration Successful")) {
                JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.");
                cardLayout.show(mainPanel, "loginPage");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists! registration failed.");
            }
        });

        JButton backButton = new JButton();
        backButton.setBounds(506, 580, 194, 38);
        backButton.setText("Home");
        backButton.setBackground(Color.decode("#eee3d1"));
        backButton.setFont(font);
        signUpPage.add(backButton);

        // Pindutin to para bumalik sa main page
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "mainPage");
        });

        return signUpPage;
    }

    // Entry point ng program, dito nagsisimula lahat
    public static void main(String[] args) {
        SwingUtilities.invokeLater(loginModule::new);
    }
}
