import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AdminSettingsPanel extends JPanel {
    private JLabel logo, label;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JButton userBtn, menuBtn;

    public void showMainMenu() {
        cardLayout.show(mainPanel, "main");
    }

    public AdminSettingsPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(panel(), "main");
        mainPanel.add(new UserManager(), "UserManager");
        mainPanel.add(new MenuManager(), "MenuManager");

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "main");
    }

    private JPanel panel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbcPanel = new GridBagConstraints();
        panel.setBackground(UIElements.priCol);
        panel.setBorder(new LineBorder(UIElements.borderCol, 5));

        logo = new UIElements.customLabel(new ImageIcon("C:\\Users\\Admin\\Documents\\Vs Code\\Teakha Project\\resources\\logo.png"), 240, 240);
        label = new UIElements.customLabel("Admin Settings | Records", 21);

        int btnFontSize = 14;
        userBtn = new UIElements.customButton("User Manager", Font.BOLD, btnFontSize);
        menuBtn = new UIElements.customButton("Menu Manager", Font.BOLD, btnFontSize);

        gbcPanel.gridy = 0;
        gbcPanel.gridwidth = 2;
        panel.add(logo, gbcPanel);
        gbcPanel.gridy = 1;
        panel.add(label, gbcPanel);

        gbcPanel.insets = new Insets(55, 184, 21, 184);
        gbcPanel.fill = GridBagConstraints.NONE;
        gbcPanel.anchor = GridBagConstraints.CENTER;
        gbcPanel.gridy = 2;
        gbcPanel.gridx = 0;
        gbcPanel.weighty = 0;
        gbcPanel.weightx = 0;
        gbcPanel.gridwidth = 1;
        panel.add(userBtn, gbcPanel);
        gbcPanel.insets = new Insets(21, 184, 90, 184);
        gbcPanel.gridy = 3;
        gbcPanel.gridx = 0;
        panel.add(menuBtn, gbcPanel);

        Dimension btnSize = new Dimension(200, 48); 
        userBtn.setPreferredSize(btnSize);
        menuBtn.setPreferredSize(btnSize);

        userBtn.addActionListener(e -> cardLayout.show(mainPanel, "UserManager"));
        menuBtn.addActionListener(e -> cardLayout.show(mainPanel, "MenuManager"));

        return panel;
    }
}
