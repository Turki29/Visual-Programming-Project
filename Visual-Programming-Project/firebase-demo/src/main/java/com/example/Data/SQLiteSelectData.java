package com.example.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteSelectData {

    // طريقة عامة لاختيار البيانات من أي جدول
    public static void selectDataFromTable(String tableName) {
        String url = "jdbc:sqlite:hospital.db";
        String sql = "SELECT id, firstname, lastname, phoneNumber, email FROM " + tableName;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("firstname") + " " + rs.getString("lastname"));
                System.out.println("Phone: " + rs.getString("phoneNumber"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // طريقة لاختيار المرضى
    public static void selectPatients() {
        selectDataFromTable("patients");
    }

    // طريقة لاختيار الأطباء
    public static void selectDoctors() {
        selectDataFromTable("doctors");
    }
}
