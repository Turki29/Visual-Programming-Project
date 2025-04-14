// 
package com.example.AdminInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class AdminInterface extends JFrame {

    JButton btnShowManageDoctors, btnShowManagePatients;
    JPanel mainPanel;
    boolean disableMainWindow = false;
    boolean disableDoctorWindow = false;

   

    public AdminInterface(String title) {
        super(title);
        JPanel panel = (JPanel) this.getContentPane();

        btnShowManageDoctors = new JButton("Manage Doctors");
        btnShowManageDoctors.addActionListener(e-> new AdminShowDoctors());

       btnShowManagePatients = new JButton("Manage Patients");
       btnShowManagePatients.addActionListener(e -> new AdminShowPatients()); 

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        btnShowManageDoctors.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnShowManagePatients.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonsPanel.add(btnShowManageDoctors);
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(btnShowManagePatients);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(buttonsPanel);

        panel.add(centerPanel, BorderLayout.CENTER);

        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}


