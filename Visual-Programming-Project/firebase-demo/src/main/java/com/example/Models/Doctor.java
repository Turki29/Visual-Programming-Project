package com.example.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    public int id;
    public String firstname;
    public String lastname;
    public String phoneNumber;
    public String email;
    public int weight;
    public int height;
    public int bloodPressure;
    public int bloodSugar;
    public String longTermDisease;

    // Static list to store all doctors
    public static List<Doctor> listOfDoctor = new ArrayList<>();

    public Doctor(int id, String firstname, String lastname, String phoneNumber, String email, 
                  int weight, int height, int bloodPressure, int bloodSugar, String longTermDisease) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.weight = weight;
        this.height = height;
        this.bloodPressure = bloodPressure;
        this.bloodSugar = bloodSugar;
        this.longTermDisease = longTermDisease;
    }

    // Method to load doctors from the database
    public static void loadDoctors() {
        Connection connection = null;
        try {
            // Change this line to use the same connection string as in App.java
            connection = DriverManager.getConnection("jdbc:sqlite:hospital.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM doctors");
    
            listOfDoctor.clear(); // Clear previous data
            while (rs.next()) {
                Doctor doctor = new Doctor(
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
                listOfDoctor.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
