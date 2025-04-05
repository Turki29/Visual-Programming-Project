import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.table.*;  // Add this import for TableCellRenderer

public class patient_page extends JFrame {
    // Main panels
    private JPanel mainPanel, headerPanel, navigationPanel, contentPanel;
    
    // Cards for content panel
    private JPanel dashboardPanel, doctorSearchPanel, appointmentsPanel, profilePanel;
    private CardLayout cardLayout;
    
    // Components
    private JLabel welcomeLabel, titleLabel;
    private JButton dashboardBtn, searchDoctorBtn, appointmentsBtn, profileBtn, logoutBtn;
    
    // Doctor search components
    private JComboBox<String> specialtyCombo, departmentCombo;
    private JTextField searchField;
    private JButton searchBtn;
    private JTable doctorTable;
    private JScrollPane doctorScrollPane;
    
    // Appointments components
    private JTable appointmentTable;
    private JScrollPane appointmentScrollPane;
    private JButton bookAppointmentBtn, cancelAppointmentBtn;
    
    // Profile components
    private JTextField nameField, emailField, phoneField;
    private JButton updateProfileBtn;
    
    // Sample data - in real implementation, this would come from a database
    private String patientName = "John Doe";
    
    public patient_page(String title) {
        super(title);
        
        // Set up the frame
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Initialize main panels
        initializePanels();
        
        // Set up layout
        setupLayout();
        
        // Add event listeners
        addEventListeners();
        
        // Show the frame
        setVisible(true);
    }
    
    private void initializePanels() {
        // Main panel with border layout
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        
        // Header panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(800, 80));
        
        // Navigation panel
        navigationPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        navigationPanel.setBackground(new Color(52, 73, 94));
        navigationPanel.setPreferredSize(new Dimension(200, 520));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        // Content panel with card layout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        
        // Initialize content panels
        initializeDashboardPanel();
        initializeDoctorSearchPanel();
        initializeAppointmentsPanel();
        initializeProfilePanel();
        
        // Add content panels to card layout
        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(doctorSearchPanel, "doctorSearch");
        contentPanel.add(appointmentsPanel, "appointments");
        contentPanel.add(profilePanel, "profile");
    }
    
    private void initializeDashboardPanel() {
        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Welcome message
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(Color.WHITE);
        welcomeLabel = new JLabel("Welcome, " + patientName + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
        
        // Dashboard content
        JPanel dashboardContent = new JPanel(new GridLayout(2, 2, 20, 20));
        dashboardContent.setBackground(Color.WHITE);
        
        // Dashboard cards
        JPanel upcomingAppointments = createDashboardCard("Upcoming Appointments", "2");
        JPanel pendingAppointments = createDashboardCard("Pending Appointments", "0");
        JPanel completedAppointments = createDashboardCard("Completed Appointments", "5");
        JPanel notifications = createDashboardCard("Notifications", "3");
        
        dashboardContent.add(upcomingAppointments);
        dashboardContent.add(pendingAppointments);
        dashboardContent.add(completedAppointments);
        dashboardContent.add(notifications);
        
        welcomePanel.add(dashboardContent, BorderLayout.CENTER);
        dashboardPanel.add(welcomePanel, BorderLayout.CENTER);
    }
    
    private JPanel createDashboardCard(String title, String count) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(240, 240, 240));
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        JLabel countLabel = new JLabel(count);
        countLabel.setFont(new Font("Arial", Font.BOLD, 24));
        countLabel.setHorizontalAlignment(JLabel.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(countLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void initializeDoctorSearchPanel() {
        doctorSearchPanel = new JPanel(new BorderLayout());
        doctorSearchPanel.setBackground(Color.WHITE);
        doctorSearchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        
        JLabel specialtyLabel = new JLabel("Specialty:");
        specialtyCombo = new JComboBox<>(new String[]{"All", "Cardiology", "Neurology", "Orthopedics", "Pediatrics", "General"});
        
        JLabel departmentLabel = new JLabel("Department:");
        departmentCombo = new JComboBox<>(new String[]{"All", "Emergency", "Outpatient", "Inpatient", "ICU"});
        
        searchField = new JTextField(15);
        searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(41, 128, 185));
        searchBtn.setForeground(Color.WHITE);
        
        searchPanel.add(specialtyLabel);
        searchPanel.add(specialtyCombo);
        searchPanel.add(departmentLabel);
        searchPanel.add(departmentCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        
        // Doctor table
        String[] columns = {"Doctor ID", "Name", "Specialty", "Department", "Available Slots", "Actions"};
        Object[][] data = {
            {"D001", "Dr. Smith", "Cardiology", "Outpatient", "10", "Book"},
            {"D002", "Dr. Johnson", "Neurology", "Emergency", "5", "Book"},
            {"D003", "Dr. Williams", "Orthopedics", "Inpatient", "8", "Book"},
            {"D004", "Dr. Brown", "Pediatrics", "Outpatient", "12", "Book"},
            {"D005", "Dr. Davis", "General", "Emergency", "7", "Book"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the Actions column is editable
            }
        };
        
        doctorTable = new JTable(model);
        doctorTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        doctorTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        doctorScrollPane = new JScrollPane(doctorTable);
        
        doctorSearchPanel.add(searchPanel, BorderLayout.NORTH);
        doctorSearchPanel.add(doctorScrollPane, BorderLayout.CENTER);
    }
    
    private void initializeAppointmentsPanel() {
        appointmentsPanel = new JPanel(new BorderLayout());
        appointmentsPanel.setBackground(Color.WHITE);
        appointmentsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Appointment table
        String[] columns = {"Appointment ID", "Doctor", "Date", "Time", "Status", "Actions"};
        Object[][] data = {
            {"A001", "Dr. Smith", "2025-04-05", "10:00 AM", "Confirmed", "Cancel"},
            {"A002", "Dr. Johnson", "2025-04-10", "02:30 PM", "Confirmed", "Cancel"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the Actions column is editable
            }
        };
        
        appointmentTable = new JTable(model);
        appointmentTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        appointmentTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        appointmentScrollPane = new JScrollPane(appointmentTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        bookAppointmentBtn = new JButton("Book New Appointment");
        bookAppointmentBtn.setBackground(new Color(41, 128, 185));
        bookAppointmentBtn.setForeground(Color.WHITE);
        
        buttonPanel.add(bookAppointmentBtn);
        
        appointmentsPanel.add(appointmentScrollPane, BorderLayout.CENTER);
        appointmentsPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void initializeProfilePanel() {
        profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Profile form
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 20));
        formPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel("Full Name:");
        nameField = new JTextField("John Doe");
        
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField("john.doe@example.com");
        
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField("123-456-7890");
        
        updateProfileBtn = new JButton("Update Profile");
        updateProfileBtn.setBackground(new Color(41, 128, 185));
        updateProfileBtn.setForeground(Color.WHITE);
        
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(new JLabel(""));
        formPanel.add(updateProfileBtn);
        
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.WHITE);
        containerPanel.add(formPanel, BorderLayout.NORTH);
        
        profilePanel.add(containerPanel, BorderLayout.CENTER);
    }
    
    private void setupLayout() {
        // Set up header
        titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Set up navigation buttons
        dashboardBtn = createNavButton("Dashboard");
        searchDoctorBtn = createNavButton("Search Doctors");
        appointmentsBtn = createNavButton("My Appointments");
        profileBtn = createNavButton("My Profile");
        logoutBtn = createNavButton("Logout");
        
        navigationPanel.add(dashboardBtn);
        navigationPanel.add(searchDoctorBtn);
        navigationPanel.add(appointmentsBtn);
        navigationPanel.add(profileBtn);
        navigationPanel.add(logoutBtn);
        
        // Add panels to main layout
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(navigationPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Show dashboard by default
        cardLayout.show(contentPanel, "dashboard");
    }
    
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(44, 62, 80));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private void addEventListeners() {
        // Navigation buttons
        dashboardBtn.addActionListener(e -> cardLayout.show(contentPanel, "dashboard"));
        searchDoctorBtn.addActionListener(e -> cardLayout.show(contentPanel, "doctorSearch"));
        appointmentsBtn.addActionListener(e -> cardLayout.show(contentPanel, "appointments"));
        profileBtn.addActionListener(e -> cardLayout.show(contentPanel, "profile"));
        logoutBtn.addActionListener(e -> {
            dispose();
            new Main("Hospital");
        });
        
        // Doctor search functionality
        searchBtn.addActionListener(e -> searchDoctors());
        
        // Book appointment button
        bookAppointmentBtn.addActionListener(e -> cardLayout.show(contentPanel, "doctorSearch"));
        
        // Update profile button
        updateProfileBtn.addActionListener(e -> updateProfile());
    }
    
    private void searchDoctors() {
        String specialty = (String) specialtyCombo.getSelectedItem();
        String department = (String) departmentCombo.getSelectedItem();
        String searchTerm = searchField.getText().trim();
        
        // In a real implementation, this would search the database
        // For now, just display a message
        JOptionPane.showMessageDialog(this, 
            "Searching for doctors with:\nSpecialty: " + specialty + 
            "\nDepartment: " + department + 
            "\nSearch term: " + searchTerm,
            "Search Results",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateProfile() {
        // In a real implementation, this would update the database
        // For now, just display a message
        JOptionPane.showMessageDialog(this, 
            "Profile updated successfully!",
            "Profile Update",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Custom renderer for the button column
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    
    // Custom editor for the button column
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                if (label.equals("Book")) {
                    // Get the doctor information
                    String doctorId = doctorTable.getValueAt(doctorTable.getSelectedRow(), 0).toString();
                    String doctorName = doctorTable.getValueAt(doctorTable.getSelectedRow(), 1).toString();
                    bookAppointment(doctorId, doctorName);
                } else if (label.equals("Cancel")) {
                    // Get the appointment information
                    String appointmentId = appointmentTable.getValueAt(appointmentTable.getSelectedRow(), 0).toString();
                    cancelAppointment(appointmentId);
                }
            }
            isPushed = false;
            return label;
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
    
    private void bookAppointment(String doctorId, String doctorName) {
        // In a real implementation, this would show a dialog for selecting date and time
        // For now, just display a message
        JOptionPane.showMessageDialog(this, 
            "Booking appointment with " + doctorName + " (ID: " + doctorId + ")",
            "Book Appointment",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Add the appointment to the appointments table (in a real app, this would come from a database)
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        
        // Generate a new appointment ID (in a real app, this would come from a database)
        String appointmentId = "A00" + (model.getRowCount() + 1);
        
        // Get current date + 7 days
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setTime(date.getTime() + 7 * 24 * 60 * 60 * 1000);
        String appointmentDate = sdf.format(date);
        
        // Add to appointments table
        model.addRow(new Object[]{appointmentId, doctorName, appointmentDate, "10:00 AM", "Confirmed", "Cancel"});
        
        // Switch to appointments view
        cardLayout.show(contentPanel, "appointments");
    }
    
    private void cancelAppointment(String appointmentId) {
        // In a real implementation, this would cancel the appointment in the database
        // For now, just display a message and remove from table
        int choice = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel appointment " + appointmentId + "?",
            "Cancel Appointment",
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
            int selectedRow = appointmentTable.getSelectedRow();
            model.removeRow(selectedRow);
            
            JOptionPane.showMessageDialog(this, 
                "Appointment " + appointmentId + " canceled successfully!",
                "Appointment Canceled",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
