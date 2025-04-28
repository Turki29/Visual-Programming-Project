package com.example.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DbConnection {

    protected Connection db;

    public DbConnection() {
        String url = "jdbc:sqlite:hospital.db"; // اسم قاعدة البيانات

        try {
            this.db = DriverManager.getConnection(url);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initializeDatabase() {
        try (Statement stmt = db.createStatement()) {
    
            // Create 'clinics' table if it doesn't exist
            String createClinicsTable = "CREATE TABLE IF NOT EXISTS clinics (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        "name TEXT NOT NULL UNIQUE" +  // Ensure clinic names are unique
                                        ");";
            stmt.execute(createClinicsTable);
    
            // Insert data into 'clinics' table only if it doesn't already exist
            String insertClinics = "INSERT OR IGNORE INTO clinics (name) VALUES " +
                                   "('Cardiology'), " +
                                   "('Dermatology'), " +
                                   "('Neurology');";
            stmt.execute(insertClinics);
    
            System.out.println("✅ Tables created or already exist, and data inserted successfully.");
    
        } catch (SQLException e) {
            System.out.println("❌ Error initializing database: " + e.getMessage());
        }
    }
    
}
