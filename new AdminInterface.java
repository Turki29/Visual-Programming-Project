package com.mycompany.main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminInterface extends JFrame {

    JButton btnShowManageDoctors, btnShowManagePatients;
    JPanel mainPanel;
    boolean disableMainWindow = false;
    boolean disableDoctorWindow = false;

    Doctor[] listOfDoctorsDetails = {
        new Doctor("Turki", "Albishri", "0507274090", "Turki@gmail.com", "Cardiology", "8:00", "16:00"),
        new Doctor("Basel", "AlHomiliy", "0507274090", "Basel@gmail.com", "Cardiology", "8:00", "16:00"),
        new Doctor("Nawaf", "Almutairi", "0507274090", "Nawaf@gmail.com", "Cardiology", "8:00", "16:00"),
        new Doctor("Mohammad", "Alorini", "0507274090", "Mohammad@gmail.com", "Cardiology", "8:00", "16:00"),
        new Doctor("Azzam", "Alamil", "0507274090", "Azzam@gmail.com", "Cardiology", "8:00", "16:00")
    };

    public AdminInterface(String title) {
        super(title);
        JPanel panel = (JPanel) this.getContentPane();

        btnShowManageDoctors = new JButton("Manage Doctors");
        btnShowManageDoctors.addActionListener(new ShowDoctors());

       btnShowManagePatients = new JButton("Manage Patients");
btnShowManagePatients.addActionListener(e -> new ShowPatients()); 

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

    // ===================== ShowDoctors =====================
    public class ShowDoctors extends JFrame implements ActionListener {
        JPanel mainPanel;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (disableMainWindow) return;
            else disableMainWindow = true;

            setTitle("Doctor Manager");
            JPanel panel = (JPanel) getContentPane();

            int lengthOfDoctorList = listOfDoctorsDetails.length;
            mainPanel = new JPanel(new GridLayout(lengthOfDoctorList, 3));

            for (int i = 0; i < lengthOfDoctorList; i++) {
                JButton detailsButton = new JButton("Details");
                JButton removeButton = new JButton("Remove");
                final int id = i;

                detailsButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ShowDoctorDetails(id);
                    }
                });

                String doctorName = listOfDoctorsDetails[i].firstname + " " + listOfDoctorsDetails[i].lastname;
                mainPanel.add(new JTextField("Dr. " + doctorName, 20));
                mainPanel.add(detailsButton);
                mainPanel.add(removeButton);
            }

            panel.add(mainPanel);
            this.pack();
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    disableMainWindow = false;
                }
            });
        }
    }

    // ===================== ShowDoctorDetails =====================
    public class ShowDoctorDetails extends JFrame {
        JPanel mainPanel, appointmentsPanel;
        JLabel lblName, lblSpeciality, lblWorkingHours;
        JTextField tfName, tfSpeciality, tfWorkingHours;
        JTable table;
        DefaultTableModel tableModel;

        public ShowDoctorDetails(int id) {
            if (disableDoctorWindow) return;
            else disableDoctorWindow = true;

            setTitle("Doctor Details");
            JPanel panel = (JPanel) getContentPane();

            lblName = new JLabel("Name:");
            lblSpeciality = new JLabel("Speciality:");
            lblWorkingHours = new JLabel("Working Hours:");
            String doctorName = listOfDoctorsDetails[id].firstname + " " + listOfDoctorsDetails[id].lastname;
            String workingHours = listOfDoctorsDetails[id].startWorkingHours + "-"
                    + listOfDoctorsDetails[id].endWorkingHours;

            tfName = new JTextField(doctorName, 20);
            tfSpeciality = new JTextField(listOfDoctorsDetails[id].speciality, 20);
            tfWorkingHours = new JTextField(workingHours, 20);

            mainPanel = new JPanel(new GridLayout(3, 2));
            mainPanel.add(lblName);
            mainPanel.add(tfName);
            mainPanel.add(lblSpeciality);
            mainPanel.add(tfSpeciality);
            mainPanel.add(lblWorkingHours);
            mainPanel.add(tfWorkingHours);

            appointmentsPanel = new JPanel(new BorderLayout());
            String[] columns = { "Patient", "Time", "Clinic" };
            tableModel = new DefaultTableModel(columns, 0);

            for (Appointment appointmentItem : listOfDoctorsDetails[id].appointments) {
                tableModel.addRow(new Object[] {
                        appointmentItem.patient,
                        appointmentItem.time,
                        appointmentItem.clinicName
                });
            }

            table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            appointmentsPanel.add(table.getTableHeader(), BorderLayout.NORTH);
            appointmentsPanel.add(scrollPane, BorderLayout.CENTER);

            panel.setLayout(new GridLayout(2, 1));
            panel.add(mainPanel);
            panel.add(appointmentsPanel, BorderLayout.SOUTH);

            this.pack();
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    disableDoctorWindow = false;
                }
            });
        }
    }

// ===================== ShowPatients =====================
public class ShowPatients extends JFrame implements ActionListener {
    JTextField tfPatientID, tfName, tfEmail, tfPhone;
    JButton btnSearch;
    JTable table;
    DefaultTableModel tableModel;

    public ShowPatients() {
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
public class ShowPatientDetails extends JFrame {
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



}
