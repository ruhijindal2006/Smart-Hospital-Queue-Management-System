import javax.swing.*;
import java.sql.*;

public class LoginWindow extends JFrame {

    JTextField user;
    JPasswordField pass;

    public LoginWindow() {

        setTitle("Login");
        setSize(350,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel u = new JLabel("Username");
        u.setBounds(40,50,100,25);
        add(u);

        user = new JTextField();
        user.setBounds(140,50,150,25);
        add(user);

        JLabel p = new JLabel("Password");
        p.setBounds(40,90,100,25);
        add(p);

        pass = new JPasswordField();
        pass.setBounds(140,90,150,25);
        add(pass);

        JButton btn = new JButton("LOGIN");
        btn.setBounds(140,140,100,30);
        add(btn);

        btn.addActionListener(e -> login());

        setVisible(true);
    }

    void login() {

        try {

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital",
                "root",
                "ruhijindal@12"
            );

            PreparedStatement pst = con.prepareStatement(
                "SELECT role FROM users WHERE username=? AND password=?"
            );

            pst.setString(1, user.getText());
            pst.setString(2, new String(pass.getPassword()));

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                String role = rs.getString("role");

                new Main(role);   // 🔥 ROLE PASS
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}