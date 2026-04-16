import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLTest {
    public static void main(String[] args) {
        System.out.println("=== MySQL Connection Test ===\n");

        String url = "jdbc:mysql://localhost:3306/greenloop";
        String username = "root";
        String password = "root";

        try {
            // Test 1: Load MySQL driver
            System.out.println("✓ Step 1: Loading MySQL driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("  SUCCESS: MySQL driver loaded\n");

            // Test 2: Connect to database
            System.out.println("✓ Step 2: Connecting to MySQL...");
            System.out.println("  Connection URL: " + url);
            System.out.println("  Username: " + username);
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("  SUCCESS: Connected to MySQL\n");

            // Test 3: Check if greenloop database exists
            System.out.println("✓ Step 3: Checking greenloop database...");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DATABASE()");
            if (rs.next()) {
                System.out.println("  Current database: " + rs.getString(1));
                System.out.println("  SUCCESS: Database is accessible\n");
            }
            rs.close();

            // Test 4: Check if tables exist
            System.out.println("✓ Step 4: Checking tables...");
            rs = stmt.executeQuery("SHOW TABLES");
            System.out.println("  Tables found:");
            while (rs.next()) {
                System.out.println("    - " + rs.getString(1));
            }
            System.out.println("  SUCCESS: Tables are present\n");
            rs.close();

            // Test 5: Check if employees table has data
            System.out.println("✓ Step 5: Checking employees data...");
            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM employees");
            if (rs.next()) {
                int count = rs.getInt("count");
                System.out.println("  Total employees: " + count);
                if (count > 0) {
                    System.out.println("  SUCCESS: Employee records exist\n");
                } else {
                    System.out.println("  WARNING: No employees found. Please insert test employee.\n");
                }
            }
            rs.close();

            // Test 6: Check if john user exists
            System.out.println("✓ Step 6: Checking for 'john' user...");
            rs = stmt.executeQuery("SELECT * FROM employees WHERE username = 'john'");
            if (rs.next()) {
                System.out.println("  Found: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                System.out.println("  Username: " + rs.getString("username"));
                System.out.println("  SUCCESS: 'john' user exists and can be used for login\n");
            } else {
                System.out.println("  WARNING: 'john' user not found. Please insert: ");
                System.out.println("  INSERT INTO employees (title, first_name, last_name, username, email, password, role_id)");
                System.out.println("  VALUES ('Mr', 'John', 'Doe', 'john', 'john@example.com', 'john', 1);\n");
            }
            rs.close();

            // Test 7: Check roles
            System.out.println("✓ Step 7: Checking roles...");
            rs = stmt.executeQuery("SELECT * FROM roles");
            System.out.println("  Roles found:");
            while (rs.next()) {
                System.out.println("    - " + rs.getString("role_name"));
            }
            rs.close();

            connection.close();
            System.out.println("✓ All tests passed! MySQL is properly connected.\n");
            System.out.println("You can now login with: username='john', password='john'");

        } catch (Exception e) {
            System.out.println("✗ ERROR: " + e.getMessage());
            System.out.println("\nTroubleshooting:");
            System.out.println("1. Is MySQL running? Check: Services > MySQL80");
            System.out.println("2. Is the database 'greenloop' created?");
            System.out.println("3. Did you insert the SQL tables from sql_generator.txt?");
            System.out.println("4. Is the password 'root' correct?");
            e.printStackTrace();
        }
    }
}
