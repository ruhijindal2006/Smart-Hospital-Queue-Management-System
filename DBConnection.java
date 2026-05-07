import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public static void main(String[] args) {
    Connection con = getConnection();
    if (con != null) {
        System.out.println("✅ Connected successfully!");
    } else {
        System.out.println("❌ Connection failed!");
    }
}

    private static final Properties props = new Properties();

    static {
        // Try multiple locations so it works regardless of where you run from
        String[] locations = {
            "db.properties",                                     // current working directory
            "src/db.properties",                                 // if running from project root
            System.getProperty("user.dir") + "/db.properties",  // absolute working directory
            DBConnection.class.getClassLoader()
                .getResource("") != null
                ? DBConnection.class.getClassLoader()
                    .getResource("").getPath() + "db.properties"
                : null                                           // same folder as .class files
        };

        boolean loaded = false;
        for (String path : locations) {
            if (path == null) continue;
            try (FileInputStream fis = new FileInputStream(path)) {
                props.load(fis);
                System.out.println("Loaded db.properties from: " + path);
                loaded = true;
                break;
            } catch (IOException e) {
                // Try next location
            }
        }

        if (!loaded) {
            System.err.println("ERROR: Could not find db.properties in any of these locations:");
            for (String path : locations) {
                if (path != null) System.err.println("  - " + path);
            }
            System.err.println("Current working directory is: " + System.getProperty("user.dir"));
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
           
            );
            return con;
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: MySQL driver not found. Is the .jar in your classpath?");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("ERROR: Could not connect to database. Check db.properties.");
            e.printStackTrace();
            return null;
        }
    }
}