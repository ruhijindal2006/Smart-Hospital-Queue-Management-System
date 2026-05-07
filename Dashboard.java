import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    Dashboard() {
        setTitle("Smart Hospital Queue Dashboard");
        setSize(1200, 700);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(15, 18, 32));

        // --- Sidebar ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(null);
        sidebar.setBounds(0, 0, 220, 700);
        sidebar.setBackground(new Color(10, 12, 25));
        add(sidebar);

        JLabel logo = new JLabel("MediQueue");
        logo.setForeground(Color.GREEN);
        logo.setFont(new Font("Arial", Font.BOLD, 22));
        logo.setBounds(40, 30, 150, 30);
        sidebar.add(logo);

        String[] menuItems = {
            "Dashboard",
            "Register Patient",
            "Live Queue",
            "Doctors",
            "AI Triage",
            "Admin Panel"
        };

        int y = 100;
        for (String menuItem : menuItems) {
            JButton btn = new JButton(menuItem);
            btn.setBounds(20, y, 180, 35);
            btn.setBackground(new Color(25, 28, 45));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);

            if (menuItem.equals("Register Patient")) {
                btn.addActionListener(e -> new RegisterPatient());
            }
            if (menuItem.equals("Live Queue")) {
                btn.addActionListener(e -> new LiveQueue());
            }

            sidebar.add(btn);
            y += 50;
        }

        // --- Main content ---
        JLabel titleLabel = new JLabel("Dashboard Overview");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(260, 20, 300, 40);
        add(titleLabel);

        // Stat cards
        addCard("Patients Today",  "247",    260,  80);
        addCard("Avg Wait Time",   "18 min", 500,  80);
        addCard("Critical Cases",  "3",      740,  80);
        addCard("Doctors Free",    "8",      980,  80);

        // Department load panel
        JPanel deptPanel = new JPanel();
        deptPanel.setBounds(260, 180, 560, 250);
        deptPanel.setBackground(new Color(25, 28, 45));
        deptPanel.setLayout(null);
        add(deptPanel);

        JLabel deptLabel = new JLabel("Department Load");
        deptLabel.setForeground(Color.WHITE);
        deptLabel.setFont(new Font("Arial", Font.BOLD, 14));
        deptLabel.setBounds(20, 10, 200, 30);
        deptPanel.add(deptLabel);

        addBar(deptPanel, "Emergency",   30, 70,  450, Color.RED);
        addBar(deptPanel, "Cardiology",  30, 110, 380, Color.ORANGE);
        addBar(deptPanel, "General OPD", 30, 150, 430, Color.CYAN);
        addBar(deptPanel, "Pediatrics",  30, 190, 300, Color.GREEN);

        // Notifications panel
        JPanel notifPanel = new JPanel();
        notifPanel.setBounds(840, 180, 320, 250);
        notifPanel.setBackground(new Color(25, 28, 45));
        notifPanel.setLayout(null);
        add(notifPanel);

        JLabel notifLabel = new JLabel("Recent Notifications");
        notifLabel.setForeground(Color.WHITE);
        notifLabel.setFont(new Font("Arial", Font.BOLD, 14));
        notifLabel.setBounds(20, 10, 200, 30);
        notifPanel.add(notifLabel);

        JTextArea notifArea = new JTextArea();
        notifArea.setBounds(20, 50, 280, 170);
        notifArea.setBackground(new Color(25, 28, 45));
        notifArea.setForeground(Color.LIGHT_GRAY);
        notifArea.setEditable(false);
        notifArea.setText(
            "CRITICAL: Patient T-089 escalated\n\n" +
            "SMS sent to patient T-102\n\n" +
            "Dr Sharma marked complete\n\n" +
            "AI re-ranked patients\n\n" +
            "Dr Priya available"
        );
        notifPanel.add(notifArea);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void addCard(String title, String value, int x, int y) {
        JPanel card = new JPanel();
        card.setBounds(x, y, 200, 80);
        card.setBackground(new Color(25, 28, 45));
        card.setLayout(null);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.LIGHT_GRAY);
        titleLabel.setBounds(15, 10, 170, 20);
        card.add(titleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.GREEN);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setBounds(15, 35, 170, 30);
        card.add(valueLabel);

        add(card);
    }

    void addBar(JPanel panel, String name, int x, int y, int width, Color color) {
        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(x, y - 20, 120, 20);
        panel.add(nameLabel);

        JPanel bar = new JPanel();
        bar.setBounds(x, y, width, 10);
        bar.setBackground(color);
        panel.add(bar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard());
    }
}
