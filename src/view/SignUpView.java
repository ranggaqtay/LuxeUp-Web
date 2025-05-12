package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpView extends JFrame {

    public SignUpView() {
        JFrame frame = new JFrame("LUXE UP - Sign Up");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true); 

        frame.setLayout(new GridLayout(1, 2)); 

        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setBackground(Color.WHITE);

        JLabel signUpLabel = new JLabel("Register");
        signUpLabel.setFont(new Font("Poppins", Font.BOLD, 36));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(signUpLabel, gbc);

        JLabel descriptionLabel = new JLabel("Please enter your details to create an account");
        descriptionLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 20, 0);
        mainPanel.add(descriptionLabel, gbc);

        JLabel nameLabel = new JLabel("Username");
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

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 5, 0);
        mainPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Poppins", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(15),
                BorderFactory.createEmptyBorder(0, 0, 0, 0) 
        ));
        emailField.setPreferredSize(new Dimension(400, 50));
        gbc.gridy = 5;
        mainPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 5, 0);
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(15),
                BorderFactory.createEmptyBorder(0, 0, 0, 0) 
        ));
        passwordField.setPreferredSize(new Dimension(400, 50));
        gbc.gridy = 7;
        mainPanel.add(passwordField, gbc);

        JButton signUpButton = new JButton("Register") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.GREEN);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("Poppins", Font.BOLD, 20));
        signUpButton.setPreferredSize(new Dimension(400, 50));
        signUpButton.setBorderPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setFocusPainted(false);
        gbc.gridy = 8;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(signUpButton, gbc);

        JLabel loginLabel = new JLabel("Already have an account? Login");
        loginLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new SignInView();
            }
        });
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(loginLabel, gbc);

        signUpButton.addActionListener(e -> {
            String username = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/luxeup", "root", "")) {

                
                String checkEmailQuery = "SELECT * FROM users WHERE email = ?";
                PreparedStatement psCheck = conn.prepareStatement(checkEmailQuery);
                psCheck.setString(1, email);
                var resultSet = psCheck.executeQuery();

                if (resultSet.next()) {
                    
                    JOptionPane.showMessageDialog(frame, "Email udah terdaftar bosku, ganti pake akun lain ye", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.executeUpdate();

        
                JOptionPane.showMessageDialog(frame, "Sign Up Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new SignInView();

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
