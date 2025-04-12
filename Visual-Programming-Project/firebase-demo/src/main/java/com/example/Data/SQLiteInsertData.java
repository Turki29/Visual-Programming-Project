package com.example.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteInsertData {
    public static void insertDoctor(int id, String firstname, String lastname, String phoneNumber, String email, int weight, int height, int bloodPressure, int bloodSugar, String longTermDisease) {
        String url = "jdbc:sqlite:hospital.db";
        String sql = "INSERT INTO doctors(id, firstname, lastname, phoneNumber, email, weight, height, bloodPressure, bloodSugar, longTermDisease) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, firstname);
            pstmt.setString(3, lastname);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, email);
            pstmt.setInt(6, weight);
            pstmt.setInt(7, height);
            pstmt.setInt(8, bloodPressure);
            pstmt.setInt(9, bloodSugar);
            pstmt.setString(10, longTermDisease);

            pstmt.executeUpdate();
            System.out.println("Doctor inserted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
