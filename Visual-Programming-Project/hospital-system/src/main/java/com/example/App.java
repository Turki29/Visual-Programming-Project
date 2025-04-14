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
import com.example.Data.*;
import com.example.DoctorInterface.DoctorInterface;
import com.example.Models.Doctor;
import com.example.Models.Person;
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

    }

    public class UserInterface implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = fieldUsername.getText().trim();
            String password = fieldPassword.getText().trim();

           
             

             

            switch (username.toLowerCase()) {
                case "admin":
                    new AdminInterface("Admin Interface");
                    break;
                case "patient":
                    new PatientsInterface("Patients Interface");
                    break;
                case "doctor":
                    
                    new DoctorInterface( "Doctor Interface");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unknown role.");
            }
               

            
        }

       
    }

    public static void main(String[] args) {
        
        UsersDbCommands db = new UsersDbCommands();
        db.DeleteUser(1);
        App frame = new App("Hospital");

    }
    
}
