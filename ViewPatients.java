import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class ViewPatients extends JFrame {

    DefaultTableModel model;
    JTable table;

    public ViewPatients() {
        setTitle("View Patients");
        setSize(700, 350);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(30, 40, 60));
        setLayout(new BorderLayout());

        // Columns (with doctor)
        String[] columns = {"ID", "Name", "Age", "Department", "Doctor"};
        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        // 🎨 Table UI
        table.setBackground(new Color(50, 60, 80));
        table.setForeground(Color.WHITE);
        table.setRowHeight(25);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 🔘 Bottom Panel (Buttons)
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 40, 60));

        JButton refreshBtn = new JButton("Refresh");
        JButton deleteBtn = new JButton("Delete Selected");

        styleButton(refreshBtn);
        styleButton(deleteBtn);

        refreshBtn.addActionListener(e -> refreshData());
        deleteBtn.addActionListener(e -> deletePatient());

        panel.add(refreshBtn);
        panel.add(deleteBtn);

        add(panel, BorderLayout.SOUTH);

        loadData();

        setVisible(true);
    }

    // 🎨 Button style
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    // 📥 Load data
    private void loadData() {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "ruhijindal@12"
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patients");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getString("doctor")
                });
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // 🔄 Refresh
    private void refreshData() {
        model.setRowCount(0);
        loadData();
    }

    // ❌ Delete selected patient
    private void deletePatient() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "ruhijindal@12"
            );

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM patients WHERE id=?"
            );
            ps.setInt(1, id);

            ps.executeUpdate();

            con.close();

            JOptionPane.showMessageDialog(this, "✅ Patient Deleted");

            refreshData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
