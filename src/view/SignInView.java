package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInView extends JFrame {
    public SignInView() {
        JFrame frame = new JFrame("LUXE UP - Sign In");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true); 

        frame.setLayout(new GridLayout(1, 2)); 

        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setBackground(Color.WHITE);

        JLabel signInLabel = new JLabel("Login");
        signInLabel.setFont(new Font("Poppins", Font.BOLD, 36));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(signInLabel, gbc);

        JLabel descriptionLabel = new JLabel("Please enter your Login and your Password");
        descriptionLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 20, 0);
        mainPanel.add(descriptionLabel, gbc);

        JLabel nameLabel = new JLabel("Username or Email");
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        mainPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Poppins", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15),
            BorderFactory.createEmptyBorder(0, 0, 0, 0) 
        ));
        nameField.setPreferredSize(new Dimension(400, 50));
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(nameField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 5, 0);
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15),
            BorderFactory.createEmptyBorder(0, 0, 0, 0) 
        ));
        passwordField.setPreferredSize(new Dimension(400, 50));
        gbc.gridy = 5;
        mainPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.GREEN);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Poppins", Font.BOLD, 20));
        loginButton.setPreferredSize(new Dimension(400, 50));
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        JLabel signUpLabel = new JLabel("Not a member yet? Register!");
        signUpLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
        signUpLabel.setForeground(Color.BLUE);
        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new SignUpView();
            }
        });
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(signUpLabel, gbc);

        loginButton.addActionListener((var e) -> {
            String usernameOrEmail = nameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (usernameOrEmail.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/luxeup", "root", "")) {
                String query = "SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, usernameOrEmail);
                ps.setString(2, usernameOrEmail);
                ps.setString(3, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();

                    
                    if (usernameOrEmail.equalsIgnoreCase("admin") || usernameOrEmail.equalsIgnoreCase("admin@gmail.com")) {
                        new view.ProductAdminView(new controller.ProductAdminController());
                    } else {
                        new view.ProductCustomerView(new controller.ProductCustomerController());
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Database Connection Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        JPanel brandingPanel = new JPanel();
        brandingPanel.setBackground(new Color(0x3A3845));
        brandingPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcBranding = new GridBagConstraints();

        JLabel logoLabel = new JLabel("LUXE UP");
        logoLabel.setFont(new Font("Serif", Font.BOLD, 48));
        logoLabel.setForeground(Color.WHITE);
        gbcBranding.gridx = 0;
        gbcBranding.gridy = 0;
        gbcBranding.insets = new Insets(0, 0, 10, 0);
        brandingPanel.add(logoLabel, gbcBranding);

        JLabel welcomeLabel = new JLabel("Selamat Datang di Aplikasi Luxe Up");
        welcomeLabel.setFont(new Font("Poppins", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.WHITE);
        gbcBranding.gridy = 1;
        brandingPanel.add(welcomeLabel, gbcBranding);

        frame.add(mainPanel);
        frame.add(brandingPanel);

        frame.setVisible(true);
    }
}
