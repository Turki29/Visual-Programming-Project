package com.example;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.example.AdminInterface.AdminInterface;
import com.example.DoctorInterface.DoctorInterface;
import com.example.Models.Doctor;
import com.example.PatientInterface.PatientsInterface;


public class App extends JFrame {

    private JPanel p0, p1, p2, p3;
    private JTextField fieldUsername, fieldPassword;
    private JLabel lblHeading, lblUsername, lblPassword;
    private JButton btnRegister, btnLogin;
    private Connection connection;

    public App(String title) {
        super(title);

        this.setLocation(1040, 540);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = (JPanel) this.getContentPane();
        panel.setLayout(new GridLayout(5, 1));

        fieldUsername = new JTextField(15);
        fieldPassword = new JTextField(15);

        lblHeading = new JLabel("Login", JLabel.CENTER);
        lblUsername = new JLabel("Username :");
        lblPassword = new JLabel("Password :");

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new UserInterface());
        btnRegister = new JButton("Register");
        btnRegister.addActionListener(e -> new RegisterPage("Register Page"));

        p0 = new JPanel(new FlowLayout());
        p1 = new JPanel(new FlowLayout());
        p2 = new JPanel(new FlowLayout());
        p3 = new JPanel(new FlowLayout());

        p0.add(lblHeading);
        p1.add(lblUsername);
        p1.add(fieldUsername);
        p2.add(lblPassword);
        p2.add(fieldPassword);
        p3.add(btnLogin);
        p3.add(btnRegister);

        panel.add(p0);
        panel.add(p1);
        panel.add(p2);
        panel.add(p3);

        this.pack();
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialize SQLite connection
        connect();
        createTables();
    }

    // Connect to SQLite Database
    private void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:hospital.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create necessary tables in the database
    private void createTables() {
        String usersTable = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, password TEXT NOT NULL, role TEXT NOT NULL)";
        String patientsTable = "CREATE TABLE IF NOT EXISTS patients (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, firstname TEXT, lastname TEXT, phoneNumber TEXT, email TEXT, diagnosis TEXT, FOREIGN KEY(user_id) REFERENCES users(id))";
        String doctorsTable = "CREATE TABLE IF NOT EXISTS doctors (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, firstname TEXT, lastname TEXT, phoneNumber TEXT, email TEXT, specialization TEXT, weight INTEGER, height INTEGER, bloodPressure INTEGER, bloodSugar INTEGER, longTermDisease TEXT, FOREIGN KEY(user_id) REFERENCES users(id))";
        String adminsTable = "CREATE TABLE IF NOT EXISTS admins (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, firstname TEXT, lastname TEXT, phoneNumber TEXT, email TEXT, FOREIGN KEY(user_id) REFERENCES users(id))";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(patientsTable);
            stmt.execute(doctorsTable);
            stmt.execute(adminsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ActionListener for login button
    public class UserInterface implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = fieldUsername.getText().trim();
            String password = fieldPassword.getText().trim();

            try {
                String query = "SELECT id, role FROM users WHERE username=? AND password=?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String role = rs.getString("role");
                    
                    dispose();  // Close login window

                    switch (role.toLowerCase()) {
                        case "admin":
                            new AdminInterface("Admin Interface");
                            break;
                        case "patient":
                            new PatientsInterface("Patients Interface");
                            break;
                        case "doctor":
                            Doctor doctor = getDoctorInfo(userId);
                            new DoctorInterface( "Doctor Interface");
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Unknown role.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login.");
                }

                rs.close();
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        // Fetch doctor details from the database
        private Doctor getDoctorInfo(int userId) {
            String query = "SELECT * FROM doctors WHERE user_id=?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new Doctor(
                            rs.getInt("id"),
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("phoneNumber"),
                            rs.getString("email"),
                            rs.getInt("weight"),
                            rs.getInt("height"),
                            rs.getInt("bloodPressure"),
                            rs.getInt("bloodSugar"),
                            rs.getString("longTermDisease")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        App frame = new App("Hospital");
    }
    
}
