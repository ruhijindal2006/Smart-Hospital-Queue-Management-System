import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {

    String role;
    JTextField userField;
    JPasswordField passField;

    public Login(String role) {
        this.role = role;

        setTitle(role + " Login");
        setSize(350, 220);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(30, 40, 60));
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);

        userField = new JTextField();
        passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);

        loginBtn.addActionListener(e -> login());

        add(userLabel); add(userField);
        add(passLabel); add(passField);
        add(new JLabel("")); add(loginBtn);

        setVisible(true);
    }

    private void login() {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "password"
            );

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=? AND role=?"
            );

            ps.setString(1, userField.getText());
            ps.setString(2, new String(passField.getPassword()));
            ps.setString(3, role);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");

                if (role.equals("DOCTOR")) {
                    new Main(role); // doctor dashboard
                } else {
                    new LiveQueue(); // patient → directly queue
                }

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login!");
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}