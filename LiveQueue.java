import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class LiveQueue extends JFrame {

    DefaultTableModel model;
    JTable table;

    public LiveQueue() {
        setTitle("Live Queue");
        setSize(650, 350);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(30, 40, 60));
        setLayout(new BorderLayout());

        // Columns
        String[] columns = {"Token", "Name", "Department", "Doctor"};
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

        // 🔘 Buttons panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 40, 60));

        JButton refreshBtn = new JButton("Refresh");
        JButton nextBtn = new JButton("Next Patient");

        styleButton(refreshBtn);
        styleButton(nextBtn);

        refreshBtn.addActionListener(e -> refreshQueue());
        nextBtn.addActionListener(e -> serveNext());

        panel.add(refreshBtn);
        panel.add(nextBtn);

        add(panel, BorderLayout.SOUTH);

        loadQueue();

        // 🔄 Auto refresh every 3 sec
        new Timer(3000, e -> refreshQueue()).start();

        setVisible(true);
    }

    // 🎨 Button style
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    // 📥 Load queue
    private void loadQueue() {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "ruhijindal@12"
            );

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT token, name, department, doctor FROM patients ORDER BY token ASC"
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("token"),
                        rs.getString("name"),
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
    private void refreshQueue() {
        model.setRowCount(0);
        loadQueue();
    }

    // ▶️ Next patient (FIFO remove)
    private void serveNext() {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "ruhijindal@12"
            );

            Statement st = con.createStatement();

            st.executeUpdate(
                "DELETE FROM patients ORDER BY token ASC LIMIT 1"
            );

            con.close();

            refreshQueue();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}