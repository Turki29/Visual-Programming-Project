package com.example.Data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.Models.Appointment;

public class AppointmentsDbCommands extends DbConnection {

    public boolean insertAppointment(Appointment a) {
        String sql = "INSERT INTO appointment (patient_id, doctor_name, department, day, time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = db.prepareStatement(sql)) {
            pstmt.setInt(1, a.getPatientId());
            pstmt.setString(2, a.getDoctor());       
            pstmt.setString(3, a.getDepartment());   
            pstmt.setString(4, a.getDay());
            pstmt.setString(5, a.getTime());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
            return false;
        }
    }
    

    public List<Appointment> getAppointmentsForPatient(int patientId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointment WHERE patient_id = " + patientId;
        try (Statement stmt = db.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Appointment(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getString("department"),
                    rs.getString("doctor_name"),
                    rs.getString("day"),
                    rs.getString("time")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching appointment: " + e.getMessage());
        }
        return list;
    }
    public boolean isAppointmentSlotTaken(String doctorName, String day, String time) {
        String query = "SELECT COUNT(*) AS count FROM appointment " +
                       "WHERE doctor_name = ? AND day = ? AND time = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setString(1, doctorName);
            stmt.setString(2, day);
            stmt.setString(3, time);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt("count") > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error checking appointment slot: " + e.getMessage());
            return true; // احتياطاً، نعتبره محجوز إذا حدث خطأ
        }
    }
    public List<String> getDepartments() {
        List<String> departments = new ArrayList<>();
        String sql = "SELECT name FROM clinics";
        try (Statement stmt = db.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                departments.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching departments: " + e.getMessage());
        }
        return departments;
    }
    public List<String> getDoctorsByDepartment(String department) {
        List<String> doctors = new ArrayList<>();
        String sql = "SELECT p.name FROM users p " +
                     "JOIN clinics c ON p.clinic_id = c.id " +
                     "WHERE c.name = ? AND p.role = 'doctor'";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                doctors.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching doctors by department: " + e.getMessage());
        }
        return doctors;
    }
    public List<String> searchDoctorsByName(String keyword) {
        List<String> doctors = new ArrayList<>();
        String sql = "SELECT name FROM users WHERE role = 'doctor' AND name LIKE ?";
        
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");  // استخدام LIKE للبحث عن الأطباء الذين تحتوي أسماؤهم على الكلمة المفتاحية
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                doctors.add(rs.getString("name"));  // إضافة اسم الطبيب إلى القائمة
            }
        } catch (SQLException e) {
            System.out.println("❌ Error searching doctors: " + e.getMessage());
        }
        
        return doctors;  // إرجاع قائمة الأطباء
    }
}
