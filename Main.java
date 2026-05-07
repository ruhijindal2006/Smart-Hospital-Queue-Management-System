import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {

    JPanel content;
    String role;

    Color bg = new Color(15,18,35);
    Color side = new Color(28,32,58);
    Color accent = new Color(0,220,255);

    public Main(String role) {

        this.role = cleanRole(role);

        System.out.println("ROLE => " + this.role);

        setTitle("Smart Hospital System - " + this.role.toUpperCase());
        setSize(1150, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(bg);

        add(createTopBar(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);

        content = new JPanel();
        content.setBackground(bg);
        content.setLayout(new GridLayout(2,2,20,20));
        content.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        add(content, BorderLayout.CENTER);

        showDashboard();

        setVisible(true);
    }

    // ================= SAFE ROLE FIX =================
    private String cleanRole(String r) {
        if(r == null) return "";
        return r.toLowerCase().trim()
                .replace("login","")
                .replace("role:","")
                .trim();
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {
        new Main("admin"); // change: admin / doctor / patient
    }

    // ================= TOP BAR =================
    JPanel createTopBar() {

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(side);
        top.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        JLabel title = new JLabel("🏥 SMART HOSPITAL MANAGEMENT");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel roleLabel = new JLabel("ROLE: " + role.toUpperCase());
        roleLabel.setForeground(accent);
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        top.add(title, BorderLayout.WEST);
        top.add(roleLabel, BorderLayout.EAST);

        return top;
    }

    // ================= SIDEBAR =================
    JPanel createSidebar() {

        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(250, 680));
        left.setBackground(side);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        left.add(Box.createVerticalStrut(20));

        JLabel menu = new JLabel("MENU");
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 20));
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);

        left.add(menu);
        left.add(Box.createVerticalStrut(25));

        left.add(menuBtn("Dashboard", e -> showDashboard()));

        String r = role.trim();

        if(r.equals("admin")) {
            left.add(menuBtn("Register Patient", e -> openRegisterPatient()));
            left.add(menuBtn("View Patients", e -> viewPatients()));
        }

        else if(r.equals("doctor")) {
            left.add(menuBtn("Live Queue", e -> showQueue()));
        }

        else if(r.equals("patient")) {
            left.add(menuBtn("Register Patient", e -> openRegisterPatient()));
            left.add(menuBtn("View Queue", e -> showQueue()));
        }

        left.add(Box.createVerticalGlue());

        left.add(menuBtn("Logout", e -> {
            dispose();
            new HomeLogin(); // must exist
        }));

        return left;
    }

    // ================= BUTTON =================
    JButton menuBtn(String text, java.awt.event.ActionListener a) {

        JButton b = new JButton(text);
        b.setMaximumSize(new Dimension(210, 42));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setBackground(new Color(45,50,75));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.addActionListener(a);

        return b;
    }

    // ================= DASHBOARD =================
    void showDashboard() {

        content.removeAll();

        content.add(card("Total Patients", countPatients(), accent));
        content.add(card("Doctors Online", "6", new Color(0,255,120)));
        content.add(card("Emergency Cases", "3", new Color(255,120,80)));
        content.add(card("Queue Status", "ACTIVE", new Color(255,255,80)));

        content.revalidate();
        content.repaint();
    }

    // ================= CARD =================
    JPanel card(String title, String value, Color c) {

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(28,32,58));
        p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel t = new JLabel(title);
        t.setForeground(Color.LIGHT_GRAY);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel v = new JLabel(value);
        v.setForeground(c);
        v.setFont(new Font("Segoe UI", Font.BOLD, 30));
        v.setHorizontalAlignment(SwingConstants.CENTER);

        p.add(t, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);

        return p;
    }

    // ================= REGISTER =================
    void openRegisterPatient() {

        JFrame f = new JFrame("Register Patient");
        f.setSize(450, 300);
        f.setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridLayout(4,2,10,10));

        JTextField name = new JTextField();
        JTextField age = new JTextField();
        JTextField phone = new JTextField();

        JButton save = new JButton("Save");

        p.add(new JLabel("Name"));
        p.add(name);
        p.add(new JLabel("Age"));
        p.add(age);
        p.add(new JLabel("Phone"));
        p.add(phone);
        p.add(new JLabel(""));
        p.add(save);

        save.addActionListener(e -> {

            try {
                Connection con = DBConnection.getConnection();

                String sql = "INSERT INTO patients(name, age, phone) VALUES(?,?,?)";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, name.getText());
                ps.setInt(2, Integer.parseInt(age.getText()));
                ps.setString(3, phone.getText());

                ps.executeUpdate();

                JOptionPane.showMessageDialog(f, "Saved Successfully!");
                f.dispose();

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(f, "DB Error: " + ex.getMessage());
            }
        });

        f.add(p);
        f.setVisible(true);
    }

    // ================= VIEW =================
    void viewPatients() {

        JFrame f = new JFrame("Patients");
        f.setSize(500,400);

        JTextArea area = new JTextArea();

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patients");

            while(rs.next()) {
                area.append(rs.getInt("id")+" | "+rs.getString("name")+"\n");
            }

        } catch(Exception e) {
            area.setText(e.getMessage());
        }

        f.add(new JScrollPane(area));
        f.setVisible(true);
    }

    // ================= QUEUE =================
    void showQueue() {

        content.removeAll();

        JTextArea area = new JTextArea("LIVE QUEUE\n1 A\n2 B\n3 YOU");
        area.setBackground(new Color(28,32,58));
        area.setForeground(Color.WHITE);

        content.setLayout(new BorderLayout());
        content.add(area);

        content.revalidate();
        content.repaint();
    }

    // ================= COUNT =================
    String countPatients() {

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM patients");

            if(rs.next()) return rs.getString(1);

        } catch(Exception e) {
            return "0";
        }

        return "0";
    }
}