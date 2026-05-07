import javax.swing.*;
import java.awt.*;

public class PatientDashboad extends JFrame {

    public PatientDashboad() {
        setTitle("Patient Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(30, 40, 60));
        setLayout(new FlowLayout());

        JLabel label = new JLabel("PATIENT DASHBOARD");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton queueBtn = new JButton("View Live Queue");
        queueBtn.addActionListener(e -> new LiveQueue());

        add(label);
        add(queueBtn);

        setVisible(true);
    }
}