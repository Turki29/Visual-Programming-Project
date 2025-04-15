package com.example.PatientInterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.example.Data.AppointmentsDbCommands;
import com.example.Data.UsersDbCommands;
import com.example.Models.Appointment;
import com.example.Models.Person;
public class PatientsInterface extends JFrame {
    private JLabel lbDashboard, lbPatientName;
    private JButton btnBookAppointment, btnViewAppointments, btnExit;
    private Person patient;

    public PatientsInterface(String title, Person patient) {
        super(title);
        this.patient = patient;

        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        lbPatientName = new JLabel("Patient Name: " + patient.getName(), JLabel.LEFT);
        lbDashboard = new JLabel("Patient Dashboard", JLabel.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        btnBookAppointment = new JButton("Book Appointment");
        btnViewAppointments = new JButton("View Appointments");
        btnExit = new JButton("Exit");

        btnBookAppointment.addActionListener(new Book());
        btnViewAppointments.addActionListener(new ViewAppointments());
        btnExit.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        buttonsPanel.add(btnBookAppointment);
        buttonsPanel.add(btnViewAppointments);
        buttonsPanel.add(btnExit);

        mainPanel.add(lbPatientName, BorderLayout.NORTH);
        mainPanel.add(lbDashboard, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);
    }

    public class Book extends JFrame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setTitle("Book Appointment");
            setSize(600, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            AppointmentsDbCommands db = new AppointmentsDbCommands();
            UsersDbCommands userDb = new UsersDbCommands();

            List<String> departmentsList = db.getDepartments();
            JComboBox<String> departmentCombo = new JComboBox<>(departmentsList.toArray(new String[0]));
            JComboBox<String> doctorCombo = new JComboBox<>();
            JComboBox<String> dayCombo = new JComboBox<>(new String[] {
                "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
            });
            JComboBox<String> timeCombo = new JComboBox<>(new String[] {
                "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM"
            });

            JTextField searchField = new JTextField(10);
            JButton btnSearch = new JButton("Search");

            btnSearch.addActionListener(ev -> {
                String keyword = searchField.getText().trim();
                List<String> results = db.searchDoctorsByName(keyword);
                doctorCombo.removeAllItems();
                for (String doc : results) {
                    doctorCombo.addItem(doc);
                }
            });

            departmentCombo.addActionListener(ev -> {
                String selectedDepartment = (String) departmentCombo.getSelectedItem();
                List<String> doctors = db.getDoctorsByDepartment(selectedDepartment);
                doctorCombo.removeAllItems();
                for (String doctor : doctors) {
                    doctorCombo.addItem(doctor);
                }
            });

            JButton btnBook = new JButton("Book Appointment");
            JButton btnCancel = new JButton("Cancel");

            btnBook.addActionListener(ev -> {
                String doctorName = (String) doctorCombo.getSelectedItem();
                String department = (String) departmentCombo.getSelectedItem();
                String day = (String) dayCombo.getSelectedItem();
                String time = (String) timeCombo.getSelectedItem();

                if (doctorName == null || day == null || time == null) {
                    JOptionPane.showMessageDialog(this, "Please select all fields.");
                    return;
                }

                boolean isTaken = db.isAppointmentSlotTaken(doctorName, day, time);

                if (isTaken) {
                    JOptionPane.showMessageDialog(this, "❌ This appointment slot is already booked.");
                } else {
                    Appointment a = new Appointment(0, patient.getId(), department, doctorName, day, time);
                    boolean success = db.insertAppointment(a);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "✅ Appointment booked successfully.");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "❌ Booking failed.");
                    }
                }
            });

            btnCancel.addActionListener(ev -> dispose());

            JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
            panel.add(new JLabel("Search Doctor:"));
            panel.add(searchField);
            panel.add(new JLabel(""));
            panel.add(btnSearch);
            panel.add(new JLabel("Department:"));
            panel.add(departmentCombo);
            panel.add(new JLabel("Doctor:"));
            panel.add(doctorCombo);
            panel.add(new JLabel("Day:"));
            panel.add(dayCombo);
            panel.add(new JLabel("Time:"));
            panel.add(timeCombo);
            panel.add(btnBook);
            panel.add(btnCancel);

            add(panel);
            setVisible(true);
        }
    }

    public class ViewAppointments extends JFrame implements ActionListener {
        private JTable appointmentsTable;
        private DefaultTableModel tableModel;

        @Override
        public void actionPerformed(ActionEvent e) {
            setTitle("View Appointments");
            setSize(600, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            String[] columns = {"Department", "Doctor", "Day", "Time"};
            tableModel = new DefaultTableModel(columns, 0);

            AppointmentsDbCommands db = new AppointmentsDbCommands();
            List<Appointment> appointments = db.getAppointmentsForPatient(patient.getId());

            for (Appointment a : appointments) {
                tableModel.addRow(new String[]{
                    a.getDepartment(), a.getDoctor(), a.getDay(), a.getTime()
                });
            }

            appointmentsTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(appointmentsTable);

            JPanel controlPanel = new JPanel(new FlowLayout());
            JButton btnClose = new JButton("Close");
            btnClose.addActionListener(event -> dispose());

            controlPanel.add(btnClose);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(new JLabel("Your Appointments:", JLabel.CENTER), BorderLayout.NORTH);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(controlPanel, BorderLayout.SOUTH);

            add(mainPanel);
            setVisible(true);
        }
    }
}
