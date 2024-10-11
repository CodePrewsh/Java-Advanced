package Chapter18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

// A simple example demonstrating JDBC (Java Database Connectivity) for accessing a database
public class SimpleJDBCExample {

    public static void main(String[] args) {
        // Create the JDBC URL
        // Assuming the database server is running on localhost with port 1527
        String url = "jdbc:derby://localhost:1527/EmployeeDB";
        String username = "tiger"; // Database username
        String password = "scott";  // Database password

        // Create a SQL query to select all records from the Employee table
        String query = "SELECT * FROM Employee";

        // A try-with-resources statement to ensure proper resource management
        // Connection and Statement implement java.lang.AutoCloseable
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            // Create a Statement object to execute the query
            Statement stmt = con.createStatement();
            // Execute the query and obtain a ResultSet containing the results
            ResultSet rs = stmt.executeQuery(query);

            // Iterate through the ResultSet to process each row of results
            while (rs.next()) {
                // Retrieve data from the current row of the ResultSet
                int empID = rs.getInt("ID"); // Get the employee ID
                String first = rs.getString("FIRSTNAME"); // Get the employee's first name
                String last = rs.getString("LASTNAME"); // Get the employee's last name
                Date birth_date = rs.getDate("BIRTHDATE"); // Get the employee's birth date
                float salary = rs.getFloat("SALARY"); // Get the employee's salary

                // Print employee details to the console
                System.out.println("Employee ID:   " + empID + "\n"
                        + "Employee Name: " + first.trim() + " " + last.trim() + "\n"
                        + "Birth Date:    " + birth_date + "\n"
                        + "Salary:        " + salary + "\n");
            }

            // SQL query to add a new employee record
            query = "INSERT INTO Employee VALUES (400, 'Bill', 'Murray','1950-09-21', 150000)";
            // Execute the update and check if one row was affected
            if (stmt.executeUpdate(query) != 1) {
                System.out.println("Failed to add a new employee record"); // Print an error message if the insert fails
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions that occur during connection or query execution
            System.out.println("Exception creating connection: " + e);
            System.exit(0); // Exit the program if an exception occurs
        }
        // No need to explicitly close the Connection and Statement objects;
        // they will be automatically closed at the end of the try block
    }
}
