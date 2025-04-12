package com.example.AdminInterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.example.Models.Doctor;

public class AdminShowDoctors extends JFrame implements ActionListener {
    JTextField tfDoctorID, tfName, tfEmail, tfPhone;
    JButton btnSearch;
    JTable table;
    DefaultTableModel tableModel;

    public AdminShowDoctors() {
        setTitle("Doctor Manager");

        // Call to load doctors from database
        Doctor.loadDoctors();

        JLabel lblID = new JLabel("ID:");
        JLabel lblName = new JLabel("Name:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblPhone = new JLabel("Phone:");

        tfDoctorID = new JTextField(10);
        tfName = new JTextField(10);
        tfEmail = new JTextField(10);
        tfPhone = new JTextField(10);

        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(lblID);
        inputPanel.add(tfDoctorID);
        inputPanel.add(lblName);
        inputPanel.add(tfName);
        inputPanel.add(lblEmail);
        inputPanel.add(tfEmail);
        inputPanel.add(lblPhone);
        inputPanel.add(tfPhone);
        inputPanel.add(btnSearch);

        String[] columns = { "ID", "Name", "Email", "Phone" };
        tableModel = new DefaultTableModel(columns, 0);

        // Populate the table with doctor data
        if (Doctor.listOfDoctor != null) {
            for (Doctor doctor : Doctor.listOfDoctor) {
                tableModel.addRow(new Object[] {
                    doctor.id,
                    doctor.firstname + " " + doctor.lastname,
                    doctor.email,
                    doctor.phoneNumber
                });
            }
        }

        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0 && Doctor.listOfDoctor != null) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    for (Doctor doctor : Doctor.listOfDoctor) {
                        if (doctor.id == id) {
                            new ShowDoctorDetails(doctor);
                            break;
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setSize(800, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = tfDoctorID.getText().trim();
        String name = tfName.getText().trim().toLowerCase();
        String email = tfEmail.getText().trim().toLowerCase();
        String phone = tfPhone.getText().trim();

        if (Doctor.listOfDoctor == null) {
            JOptionPane.showMessageDialog(this, "No doctors available.");
            return;
        }

        for (Doctor doctor : Doctor.listOfDoctor) {
            boolean matches = true;

            if (!id.isEmpty()) {
                try {
                    if (doctor.id != Integer.parseInt(id)) {
                        matches = false;
                    }
                } catch (NumberFormatException ex) {
                    matches = false;
                }
            }

            if (!name.isEmpty() && !doctor.firstname.toLowerCase().contains(name) && !doctor.lastname.toLowerCase().contains(name)) {
                matches = false;
            }

            if (!email.isEmpty() && !doctor.email.toLowerCase().contains(email)) {
                matches = false;
            }

            if (!phone.isEmpty() && !doctor.phoneNumber.contains(phone)) {
                matches = false;
            }

            if (matches) {
                new ShowDoctorDetails(doctor);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "No matching doctor found.");
    }
}


class ShowDoctorDetails extends JFrame {
    JTextField tfName, tfPhone, tfEmail;
    JButton btnEdit, btnSave, btnMedicalInfo;  
    private Doctor doctor;

    public ShowDoctorDetails(Doctor doctor) {
        this.doctor = doctor;
        setTitle("Doctor Details");

        JLabel lblID = new JLabel("Doctor ID: " + doctor.id);
        JLabel lblName = new JLabel("Name:");
        JLabel lblPhone = new JLabel("Phone:");
        JLabel lblEmail = new JLabel("Email:");

        tfName = new JTextField(doctor.firstname + " " + doctor.lastname, 20);
        tfPhone = new JTextField(doctor.phoneNumber, 20);
        tfEmail = new JTextField(doctor.email, 20);

        tfName.setEditable(false);
        tfPhone.setEditable(false);
        tfEmail.setEditable(false);

        btnEdit = new JButton("Edit");
        btnSave = new JButton("Save");
        btnSave.setEnabled(false);
        btnMedicalInfo = new JButton("Medical Info");  

        btnEdit.addActionListener(e -> enableEditing());
        btnSave.addActionListener(e -> saveChanges());
        btnMedicalInfo.addActionListener(e -> showMedicalInfo());  

        JPanel detailsPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        detailsPanel.add(lblID);
        detailsPanel.add(new JLabel()); 
        detailsPanel.add(lblName);
        detailsPanel.add(tfName);
        detailsPanel.add(lblPhone);
        detailsPanel.add(tfPhone);
        detailsPanel.add(lblEmail);
        detailsPanel.add(tfEmail);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnMedicalInfo);  

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void enableEditing() {
        tfName.setEditable(true);
        tfPhone.setEditable(true);
        tfEmail.setEditable(true);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(true);
    }

    private void saveChanges() {
        String name = tfName.getText();
        String phone = tfPhone.getText();
        String email = tfEmail.getText();

        String[] names = name.split(" ", 2);
        if (names.length >= 2) {
            doctor.firstname = names[0];
            doctor.lastname = names[1];
        } else {
            doctor.firstname = name;
            doctor.lastname = "";
        }

        doctor.phoneNumber = phone;
        doctor.email = email;

        JOptionPane.showMessageDialog(this, "Changes saved:\nName: " + name + "\nPhone: " + phone + "\nEmail: " + email);

        tfName.setEditable(false);
        tfPhone.setEditable(false);
        tfEmail.setEditable(false);
        btnEdit.setEnabled(true);
        btnSave.setEnabled(false);
    }

    private void showMedicalInfo() {
        JFrame medicalInfoFrame = new JFrame("Medical Info - " + doctor.firstname + " " + doctor.lastname);
        medicalInfoFrame.setLayout(new GridLayout(6, 2, 5, 5));

        medicalInfoFrame.add(new JLabel("Weight:"));
        medicalInfoFrame.add(new JLabel(doctor.weight + " kg"));
        medicalInfoFrame.add(new JLabel("Height:"));
        medicalInfoFrame.add(new JLabel(doctor.height + " cm"));
        medicalInfoFrame.add(new JLabel("Blood Pressure:"));
        medicalInfoFrame.add(new JLabel(String.valueOf(doctor.bloodPressure)));
        medicalInfoFrame.add(new JLabel("Blood Sugar:"));
        medicalInfoFrame.add(new JLabel(String.valueOf(doctor.bloodSugar)));
        medicalInfoFrame.add(new JLabel("Long Term Disease:"));
        medicalInfoFrame.add(new JLabel(doctor.longTermDisease));

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> medicalInfoFrame.dispose());
        medicalInfoFrame.add(btnClose);

        medicalInfoFrame.setSize(300, 250);
        medicalInfoFrame.setLocationRelativeTo(null);
        medicalInfoFrame.setVisible(true);
    }
}
