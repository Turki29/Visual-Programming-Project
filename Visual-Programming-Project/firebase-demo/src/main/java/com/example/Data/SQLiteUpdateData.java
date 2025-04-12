package com.example.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteUpdateData {
    public static void updatePhoneNumber(int id, String phoneNumber, String tableName) {
        String url = "jdbc:sqlite:hospital.db";
        String sql = "UPDATE "+tableName+" SET phoneNumber = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, phoneNumber);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();
            System.out.println("Updated successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
