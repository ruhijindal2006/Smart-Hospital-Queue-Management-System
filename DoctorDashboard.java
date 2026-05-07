import javax.swing.*;
import java.awt.*;

public class DoctorDashboard extends JFrame {

    public DoctorDashboard() {

        setTitle("Smart Hospital Dashboard (DOCTOR)");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(15, 18, 28));
        setLayout(null);

        JLabel title = new JLabel("Smart Hospital Dashboard (DOCTOR)");
        title.setBounds(20, 20, 420, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title);

        JLabel logo = new JLabel("HOSPITAL");
        logo.setBounds(40, 80, 180, 30);
        logo.setForeground(Color.CYAN);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(logo);

        JButton dashboard = new JButton("Dashboard");
        dashboard.setBounds(30, 140, 160, 35);
        add(dashboard);

        JButton patients = new JButton("View Patients");
        patients.setBounds(30, 180, 160, 35);
        add(patients);

        JButton appointments = new JButton("Appointments");
        appointments.setBounds(30, 220, 160, 35);
        add(appointments);

        JButton queueBtn = new JButton("Live Queue");
        queueBtn.setBounds(30, 260, 160, 35);
        queueBtn.addActionListener(e -> new LiveQueue());
        add(queueBtn);

        JButton exit = new JButton("Exit");
        exit.setBounds(30, 500, 160, 35);
        exit.addActionListener(e -> dispose());
        add(exit);

        JPanel card1 = createCard("Total Patients", "1");
        card1.setBounds(250, 120, 250, 180);
        add(card1);

        JPanel card2 = createCard("Doctors Available", "4");
        card2.setBounds(530, 120, 250, 180);
        add(card2);

        JPanel card3 = createCard("Live Queue", "1");
        card3.setBounds(250, 330, 250, 180);
        add(card3);

        JPanel card4 = createCard("Appointments", "1");
        card4.setBounds(530, 330, 250, 180);
        add(card4);

        setVisible(true);
    }

    JPanel createCard(String title, String value) {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(20, 30, 50));

        JLabel t = new JLabel(title);
        t.setBounds(15, 10, 200, 25);
        t.setForeground(Color.WHITE);
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel v = new JLabel(value);
        v.setBounds(110, 70, 60, 40);
        v.setForeground(Color.GREEN);
        v.setFont(new Font("Segoe UI", Font.BOLD, 32));

        panel.add(t);
        panel.add(v);

        return panel;
    }
}