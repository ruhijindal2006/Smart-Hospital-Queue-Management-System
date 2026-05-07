import javax.swing.*;
import java.awt.*;

public class HomeLogin extends JFrame {

    public HomeLogin() {

        setTitle("Smart Hospital Login");
        setSize(500, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        getContentPane().setBackground(new Color(25, 25, 40));

        // Title
        JLabel title = new JLabel("SMART HOSPITAL LOGIN");
        title.setBounds(90, 40, 350, 35);
        title.setForeground(Color.CYAN);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title);

        // Admin Button
        JButton adminBtn = new JButton("Admin Login");
        adminBtn.setBounds(150, 130, 180, 40);
        adminBtn.setBackground(new Color(0, 200, 255));
        adminBtn.setFocusPainted(false);
        add(adminBtn);

        // Doctor Button
        JButton doctorBtn = new JButton("Doctor Login");
        doctorBtn.setBounds(150, 190, 180, 40);
        doctorBtn.setBackground(new Color(0, 200, 255));
        doctorBtn.setFocusPainted(false);
        add(doctorBtn);

        // Patient Button
        JButton patientBtn = new JButton("Patient Login");
        patientBtn.setBounds(150, 250, 180, 40);
        patientBtn.setBackground(new Color(0, 200, 255));
        patientBtn.setFocusPainted(false);
        add(patientBtn);

        // Actions
        adminBtn.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        doctorBtn.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        patientBtn.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new HomeLogin();
    }
}