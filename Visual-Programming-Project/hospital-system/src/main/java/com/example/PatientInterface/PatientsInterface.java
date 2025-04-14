package com.example.PatientInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class PatientsInterface extends JFrame {
    private JLabel lbDashboard, lbPatientName;
    private JButton btnBookAppointment, btnViewAppointments, btnExit;
    // Store appointments in a central list so all classes can access them
    private static List<String[]> appointmentsList = new ArrayList<>();
    
    // Initialize with some sample appointments
    static {
        appointmentsList.add(new String[]{"Cardiology", "Dr. A", "Saturday", "8:00 AM"});
        appointmentsList.add(new String[]{"Dermatology", "Dr. B", "Monday", "9:00 AM"});
        appointmentsList.add(new String[]{"Neurology", "Dr. C", "Tuesday", "10:00 AM"});
    }

    public PatientsInterface(String title) {
        super(title);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = (JPanel) getContentPane();
        panel.setLayout(new GridLayout(5,1)); 
        
        lbPatientName = new JLabel("Patient Name: default", JLabel.LEFT);
        lbDashboard = new JLabel("Patient Dashboard", JLabel.CENTER);
        
        JPanel buttonPanel1 = new JPanel(new FlowLayout());
        JPanel buttonPanel2 = new JPanel(new FlowLayout());
        JPanel buttonPanel3 = new JPanel(new FlowLayout()); 
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
        
        buttonPanel1.add(btnBookAppointment);
        buttonPanel2.add(btnViewAppointments);
        buttonPanel3.add(btnExit);
        panel.add(lbPatientName, BorderLayout.NORTH);
        panel.add(lbDashboard, BorderLayout.CENTER);
        panel.add(buttonPanel1, BorderLayout.CENTER);
        panel.add(buttonPanel2, BorderLayout.CENTER);
        panel.add(buttonPanel3, BorderLayout.CENTER);
       
        setVisible(true);
    }
    
    public class Book extends JFrame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setTitle("Book Appointment");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
            String[] department = {"Cardiology", "Dermatology", "Neurology"};
            String[] doctors = {"Dr. A", "Dr. B", "Dr. C"};
            String[] schedulesDays = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            String[] schedulesTime = {"8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM"};
    
            JPanel bookingPanel = new JPanel(new GridLayout(6, 2));
    
            JComboBox<String> departmentCombo = new JComboBox<>(department);
            JComboBox<String> doctorCombo = new JComboBox<>(doctors);
            JComboBox<String> scheduleCombo = new JComboBox<>(schedulesDays);
            JComboBox<String> scheduleComboT = new JComboBox<>(schedulesTime);
    
            JButton btnBook = new JButton("Book Appointment");
            JButton btnCancel = new JButton("Cancel");
    
            btnBook.addActionListener(event -> {
                String selectedDepartment = (String) departmentCombo.getSelectedItem();
                String selectedDoctor = (String) doctorCombo.getSelectedItem();
                String selectedDay = (String) scheduleCombo.getSelectedItem();
                String selectedTime = (String) scheduleComboT.getSelectedItem();

                if (selectedDay == null || selectedTime == null) {
                    JOptionPane.showMessageDialog(this, "Please select a day and time.");
                } else {
                    // Add new appointment to the list
                    String[] newAppointment = {selectedDepartment, selectedDoctor, selectedDay, selectedTime};
                    appointmentsList.add(newAppointment);
                    
                    JOptionPane.showMessageDialog(this, "Appointment booked with " + selectedDoctor +
                            " in " + selectedDepartment + " on " + selectedDay + " at " + selectedTime);
                    dispose();
                }
            });
    
            btnCancel.addActionListener(event -> dispose());
    
            bookingPanel.add(new JLabel("Select Department:"));
            bookingPanel.add(departmentCombo);
            bookingPanel.add(new JLabel("Select Doctor:"));
            bookingPanel.add(doctorCombo);
            bookingPanel.add(new JLabel("Select Day:"));
            bookingPanel.add(scheduleCombo);
            bookingPanel.add(new JLabel("Select Time:"));
            bookingPanel.add(scheduleComboT);
            bookingPanel.add(btnBook);
            bookingPanel.add(btnCancel);
    
            add(bookingPanel);
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
            
            // Create column names
            String[] columns = {"Department", "Doctor", "Day", "Time", "Actions"};
            
            // Create table model with column names but no data yet
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make only the last column editable (for buttons)
                    return column == 4;
                }
            };
            
            // Add all appointments to the table model
            for (String[] appointment : appointmentsList) {
                tableModel.addRow(appointment);
            }
            
            // Create table with custom renderer for the button column
            appointmentsTable = new JTable(tableModel);
            appointmentsTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
            appointmentsTable.getColumnModel().getColumn(4).setCellEditor(
                new ButtonEditor(new JCheckBox(), this));
            
            JScrollPane scrollPane = new JScrollPane(appointmentsTable);
            
            JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton btnClose = new JButton("Close");
            btnClose.addActionListener(event -> dispose());
            
            JButton btnAddAppointment = new JButton("Add New Appointment");
            btnAddAppointment.addActionListener(event -> {
                new Book().actionPerformed(null);
                dispose(); // Close current window
            });
            
            controlPanel.add(btnAddAppointment);
            controlPanel.add(btnClose);
            
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(new JLabel("Your Appointments:", JLabel.CENTER), BorderLayout.NORTH);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(controlPanel, BorderLayout.SOUTH);
            
            add(mainPanel);
            setVisible(true);
        }
        
        // Method to update the table when appointments change
        public void refreshTable() {
            tableModel.setRowCount(0); // Clear the table
            
            // Re-add all appointments
            for (String[] appointment : appointmentsList) {
                tableModel.addRow(appointment);
            }
        }
        
        // Method to cancel an appointment by index
        public void cancelAppointment(int rowIndex) {
            if (rowIndex >= 0 && rowIndex < appointmentsList.size()) {
                String[] appointment = appointmentsList.get(rowIndex);
                int response = JOptionPane.showConfirmDialog(
                    this,
                    "Cancel appointment with " + appointment[1] + " in " + appointment[0] + 
                    " on " + appointment[2] + " at " + appointment[3] + "?",
                    "Cancel Appointment",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (response == JOptionPane.YES_OPTION) {
                    appointmentsList.remove(rowIndex);
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Appointment successfully cancelled.");
                }
            }
        }
    }
    
    // Custom renderer for the button column
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Cancel");
            return this;
        }
    }
    
    // Custom editor for the button column
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isPushed;
        private int currentRow;
        private ViewAppointments parent;
        
        public ButtonEditor(JCheckBox checkBox, ViewAppointments parent) {
            super(checkBox);
            this.parent = parent;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            button.setText("Cancel");
            isPushed = true;
            currentRow = row;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                parent.cancelAppointment(currentRow);
            }
            isPushed = false;
            return "Cancel";
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
    
   
}