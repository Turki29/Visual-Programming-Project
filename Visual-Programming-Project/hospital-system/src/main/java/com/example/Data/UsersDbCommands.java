package com.example.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.example.Models.Person;

public class UsersDbCommands extends DbConnection {

    public Person getUser(int givenId) {
        String query = "SELECT * FROM users where id = " + givenId + ";";
        Person resultPerson = null;

        try (
                Statement stmt = db.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                int clinicId = rs.getInt("clinic_id");

                resultPerson = new Person(id, name, password, role, phone, email, clinicId);
                return resultPerson;
            } else {
                JOptionPane.showMessageDialog(null,
                        "User with id: '" + givenId + "' not found",
                        "NotFound",
                        JOptionPane.ERROR_MESSAGE);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching users", "Exception", JOptionPane.ERROR_MESSAGE);
        }
        return new Person(-1, "", "", "", "", "", -1);
    }

    // getUsers("id =" + id)
    public List<Person> getUsers(String condition) {
        String query;
        if (condition.isEmpty())
            query = "SELECT * FROM users;";
        else
            query = "SELECT * FROM users where " + condition + ";";

        List<Person> resultPersons = new ArrayList<>();

        try (
                Statement stmt = db.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                int clinicId = rs.getInt("clinic_id");

                Person person = new Person( id,  name,  email,  phone,
                 password,  role,  clinicId);
                resultPersons.add(person);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching users", "Exception", JOptionPane.ERROR_MESSAGE);
        }
        return resultPersons;

    }

    public boolean DeleteUser(int id) {
        String query = "DELETE FROM users WHERE Id = " + id + ";";

        try {
            Statement stmt = db.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);

        }
        return false;
    }

    public boolean InsertUser(Person person) {
        String query = "INSERT INTO users (name, password, role, phone, email, clinic_id)" +
                "VALUES ('" +
                person.getName() + "','" +
                person.getPassword() + "','" +
                person.getRole() + "','" +
                person.getPhoneNumber() + "','" +
                person.getEmail() + "'," +
                person.getClinicId() + ")";

        try {
            Statement stmt = db.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);

        }
        return false;
    }

    public boolean UpdateUser(Person person)
    {
        String query = "UPDATE users SET name = '" + person.getName() +
                "', password = '" + person.getPassword() + 
                "', role = '" + person.getRole() + 
                "', phone = '" + person.getPhoneNumber() + 
                "', email = '" + person.getEmail() + 
                "', clinic_id = " + person.getClinicId() + 
                " WHERE id = " + person.getId() + ";";

try {
    Statement stmt = db.createStatement();
    stmt.executeUpdate(query);
    return true;
} catch (SQLException e) {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);

}
return false; 
    }
 
    public List<Person> getDoctors() {
        return getUsers("role = 'doctor'");
    }
    
    public List<Person> searchDoctorsByName(String keyword) {
        return getUsers("role = 'doctor' AND name LIKE '%" + keyword + "%'");
    }
    
}

