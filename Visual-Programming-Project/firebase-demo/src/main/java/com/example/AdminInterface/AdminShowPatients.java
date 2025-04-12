package com.example.AdminInterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import com.example.Models.Patient;

public class AdminShowPatients extends JFrame implements ActionListener {
    JTextField tfPatientID, tfName, tfEmail, tfPhone;
    JButton btnSearch;
    JTable table;
    DefaultTableModel tableModel;

    public AdminShowPatients() {
        setTitle("Patient Manager");

        JLabel lblID = new JLabel("ID:");
        JLabel lblName = new JLabel("Name:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblPhone = new JLabel("Phone:");

        tfPatientID = new JTextField(10);
        tfName = new JTextField(10);
        tfEmail = new JTextField(10);
        tfPhone = new JTextField(10);

        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(lblID);
        inputPanel.add(tfPatientID);
        inputPanel.add(lblName);
        inputPanel.add(tfName);
        inputPanel.add(lblEmail);
        inputPanel.add(tfEmail);
        inputPanel.add(lblPhone);
        inputPanel.add(tfPhone);
        inputPanel.add(btnSearch);

        String[] columns = { "ID", "Name", "Email", "Phone" };
        tableModel = new DefaultTableModel(columns, 0);

        for (Patient patient : Patient.listOfPatients) {
            tableModel.addRow(new Object[] {
                patient.id,
                patient.firstname + " " + patient.lastname,
                patient.email,
                patient.phoneNumber
            });
        }

        table = new JTable(tableModel);
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
        String id = tfPatientID.getText().trim();
        String name = tfName.getText().trim().toLowerCase();
        String email = tfEmail.getText().trim().toLowerCase();
        String phone = tfPhone.getText().trim();

        for (Patient patient : Patient.listOfPatients) {
            boolean matches = true;

            if (!id.isEmpty() && patient.id != Integer.parseInt(id)) {
                matches = false;
            }

            if (!name.isEmpty() && !patient.firstname.toLowerCase().contains(name) && !patient.lastname.toLowerCase().contains(name)) {
                matches = false;
            }

            if (!email.isEmpty() && !patient.email.toLowerCase().contains(email)) {
                matches = false;
            }

            if (!phone.isEmpty() && !patient.phoneNumber.contains(phone)) {
                matches = false;
            }

            if (matches) {
                new ShowPatientDetails(patient); 
                return;  
            }
        }

        JOptionPane.showMessageDialog(this, "No matching patient found.");
    }
}





// ===================== ShowPatientDetails =====================
class ShowPatientDetails extends JFrame {
    JTextField tfName, tfPhone, tfEmail;
    JButton btnEdit, btnSave, btnMedicalInfo;  

    public ShowPatientDetails(Patient patient) {
        setTitle("Patient Details");

        JLabel lblID = new JLabel("Patient ID: " + patient.id);
        JLabel lblName = new JLabel("Name:");
        JLabel lblPhone = new JLabel("Phone:");
        JLabel lblEmail = new JLabel("Email:");

        tfName = new JTextField(patient.firstname + " " + patient.lastname, 20);
        tfPhone = new JTextField(patient.phoneNumber, 20);
        tfEmail = new JTextField(patient.email, 20);

        tfName.setEditable(false);
        tfPhone.setEditable(false);
        tfEmail.setEditable(false);

        btnEdit = new JButton("Edit");
        btnSave = new JButton("Save");
        btnSave.setEnabled(false);
        btnMedicalInfo = new JButton("Medical Info");  

        btnEdit.addActionListener(e -> enableEditing());
        btnSave.addActionListener(e -> saveChanges(patient));
        btnMedicalInfo.addActionListener(e -> showMedicalInfo(patient));  

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

    private void saveChanges(Patient patient) {
        String name = tfName.getText();
        String phone = tfPhone.getText();
        String email = tfEmail.getText();

        JOptionPane.showMessageDialog(this, "Changes saved:\nName: " + name + "\nPhone: " + phone + "\nEmail: " + email);

        patient.firstname = name.split(" ")[0]; 
        patient.lastname = name.split(" ")[1];
        patient.phoneNumber = phone;
        patient.email = email;

        tfName.setEditable(false);
        tfPhone.setEditable(false);
        tfEmail.setEditable(false);
        btnEdit.setEnabled(true);
        btnSave.setEnabled(false);
    }

    private void showMedicalInfo(Patient patient) {

      JFrame medicalInfoFrame = new JFrame("Medical Info - " + patient.firstname + " " + patient.lastname);
        medicalInfoFrame.setLayout(new GridLayout(6, 2, 5, 5));

        medicalInfoFrame.add(new JLabel("Weight:"));
        medicalInfoFrame.add(new JLabel(String.valueOf(patient.weight) + " kg"));
        medicalInfoFrame.add(new JLabel("Height:"));
        medicalInfoFrame.add(new JLabel(String.valueOf(patient.height) + " cm"));
        medicalInfoFrame.add(new JLabel("Blood Pressure:"));
        medicalInfoFrame.add(new JLabel(String.valueOf(patient.bloodPressure)));
        medicalInfoFrame.add(new JLabel("Blood Sugar:"));
        medicalInfoFrame.add(new JLabel(String.valueOf(patient.bloodSugar)));
        medicalInfoFrame.add(new JLabel("Long Term Disease:"));
        medicalInfoFrame.add(new JLabel(patient.longTermDisease));

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> medicalInfoFrame.dispose());
        medicalInfoFrame.add(btnClose);

        medicalInfoFrame.setSize(300, 250);
        medicalInfoFrame.setLocationRelativeTo(null);
        medicalInfoFrame.setVisible(true);
    }
}



          