import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterPatient extends JFrame {

    JTextField nameField, ageField, symptomField;
    JComboBox<String> deptBox;
    JLabel aiResult;

    public RegisterPatient() {

        setTitle("AI Register Patient");
        setSize(520, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color bg = new Color(18, 22, 40);
        Color card = new Color(28, 34, 58);
        Color cyan = new Color(0, 220, 255);

        getContentPane().setBackground(bg);

        // ===== TOP TITLE =====
        JLabel title = new JLabel("AI SMART PATIENT REGISTRATION", JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(card);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setPreferredSize(new Dimension(500, 55));
        add(title, BorderLayout.NORTH);

        // ===== FORM PANEL =====
        JPanel form = new JPanel(new GridLayout(6,2,12,12));
        form.setBackground(bg);
        form.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        Font f = new Font("Segoe UI", Font.BOLD, 15);

        JLabel l1 = label("Patient Name:", f);
        JLabel l2 = label("Age:", f);
        JLabel l3 = label("Symptoms:", f);
        JLabel l4 = label("Department:", f);

        nameField = new JTextField();
        ageField = new JTextField();
        symptomField = new JTextField();

        deptBox = new JComboBox<>(new String[]{
                "Auto Detect",
                "General",
                "Cardiology",
                "Orthopedic",
                "Neurology"
        });

        styleField(nameField);
        styleField(ageField);
        styleField(symptomField);
        deptBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        aiResult = new JLabel("AI Result: Waiting...");
        aiResult.setForeground(cyan);
        aiResult.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton save = new JButton("Save Patient");
        save.setBackground(cyan);
        save.setForeground(Color.BLACK);
        save.setFont(new Font("Segoe UI", Font.BOLD, 15));
        save.setFocusPainted(false);

        JButton close = new JButton("Close");
        close.setBackground(new Color(255,80,80));
        close.setForeground(Color.WHITE);
        close.setFocusPainted(false);

        save.addActionListener(e -> savePatient());
        close.addActionListener(e -> dispose());

        form.add(l1); form.add(nameField);
        form.add(l2); form.add(ageField);
        form.add(l3); form.add(symptomField);
        form.add(l4); form.add(deptBox);
        form.add(save); form.add(close);
        form.add(aiResult); form.add(new JLabel(""));

        add(form, BorderLayout.CENTER);

        setVisible(true);
    }

    JLabel label(String text, Font f) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(f);
        return l;
    }

    void styleField(JTextField t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    void savePatient() {

        try {

            String symptom = symptomField.getText().toLowerCase();

            String priority = "Normal";
            String dept = "General";

            // ===== AI DECISION SYSTEM =====
            if (symptom.contains("fracture") ||
                symptom.contains("bone") ||
                symptom.contains("accident") ||
                symptom.contains("injury") ||
                symptom.contains("leg pain")) {

                priority = "Emergency";
                dept = "Orthopedic";
            }

            else if (symptom.contains("chest") ||
                     symptom.contains("heart") ||
                     symptom.contains("breathing") ||
                     symptom.contains("bp high")) {

                priority = "Emergency";
                dept = "Cardiology";
            }

            else if (symptom.contains("headache") ||
                     symptom.contains("migraine") ||
                     symptom.contains("brain") ||
                     symptom.contains("dizziness")) {

                priority = "Senior";
                dept = "Neurology";
            }

            else if (symptom.contains("fever") ||
                     symptom.contains("cold") ||
                     symptom.contains("cough") ||
                     symptom.contains("weakness")) {

                priority = "Normal";
                dept = "General";
            }

            if (!deptBox.getSelectedItem().toString().equals("Auto Detect")) {
                dept = deptBox.getSelectedItem().toString();
            }

            aiResult.setText("AI Result: " + priority + " | " + dept);

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hospital",
                    "root",
                    "ruhijindal@12"
            );

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                    "SELECT IFNULL(MAX(token),0)+1 FROM patients"
            );

            int token = 1;
            if (rs.next()) token = rs.getInt(1);

            String doctor = "Not Assigned";

            PreparedStatement psDoc = con.prepareStatement(
                    "SELECT name FROM doctors WHERE department=? LIMIT 1"
            );

            psDoc.setString(1, dept);

            ResultSet dr = psDoc.executeQuery();

            if (dr.next()) {
                doctor = dr.getString("name");
            }

            PreparedStatement ps = con.prepareStatement(

            "INSERT INTO patients(name,age,department,token,doctor,priority) VALUES(?,?,?,?,?,?)"
            );

            ps.setString(1, nameField.getText());
            ps.setInt(2, Integer.parseInt(ageField.getText()));
            ps.setString(3, dept);
            ps.setInt(4, token);
            ps.setString(5, doctor);
            ps.setString(6, priority);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Patient Registered Successfully!\n\n" +
                    "Token No: " + token +
                    "\nPriority: " + priority +
                    "\nDepartment: " + dept +
                    "\nDoctor: " + doctor
            );

            con.close();

            nameField.setText("");
            ageField.setText("");
            symptomField.setText("");
            deptBox.setSelectedIndex(0);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}