package com.example.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlLiteConnection {
    public SqlLiteConnection() {
        String url = "jdbc:sqlite:hospital.db"; // اسم قاعدة البيانات

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("تم الاتصال بقاعدة البيانات بنجاح");
            }
        } catch (SQLException e) {
            System.out.println("خطأ: " + e.getMessage());
        }
    }
}
