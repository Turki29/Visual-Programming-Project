import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdminInterface extends JFrame {

    JButton btnShowManageDoctors, btnShowManagePatients;
    JPanel mainPanel;
    boolean disableMainWindow = false;
    boolean disableDoctorWindow = false;
    String[] listOfDoctors = {"Turki", "Basel", "Nawaf", "Mohammad", "Azzam"};

    public AdminInterface(String title) {
        super(title);
        JPanel panel = (JPanel)this.getContentPane();
        
        
        btnShowManageDoctors = new JButton("Manage Doctors");
        btnShowManageDoctors.addActionListener(new ShowDoctors());
        btnShowManagePatients = new JButton("Manage Patients");
        
         // بانل فرعية لترتيب الأزرار فوق بعض
    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

    // تأكد من عدم التمدد
    btnShowManageDoctors.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnShowManagePatients.setAlignmentX(Component.CENTER_ALIGNMENT);

    buttonsPanel.add(btnShowManageDoctors);
    buttonsPanel.add(Box.createVerticalStrut(10)); // مسافة بين الأزرار
    buttonsPanel.add(btnShowManagePatients);

    // بانل لتوسيط كل شيء في منتصف النافذة
    JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    centerPanel.add(buttonsPanel);

    panel.add(centerPanel, BorderLayout.CENTER);

        // Set size of the window
        setSize(400,400);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Make the window visible
        setVisible(true);

        // Set default close operation
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

   

    public class ShowDoctors extends JFrame implements ActionListener {

        JPanel mainPanel;
        
        
        @Override
        public void actionPerformed(ActionEvent e)
        {

            if(disableMainWindow) return;
            else disableMainWindow = true;

            setTitle("Doctor Manager");

            JPanel panel = (JPanel)getContentPane();

            
            int lengthOfDoctorList = listOfDoctors.length;
            mainPanel = new JPanel(new GridLayout(lengthOfDoctorList, 3));
            for(int i = 0; i <lengthOfDoctorList; i++ )
            {
                JButton detailsButton = (new JButton("Details"));
                JButton removeButton = (new JButton("Remove"));

                final int id = i;

                detailsButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        new ShowDoctorDetails(id);
                    }
                });
                
                
                

                mainPanel.add(new JTextField("Dr. " + listOfDoctors[i],20));
                mainPanel.add(detailsButton);
                mainPanel.add(removeButton);
            }

            panel.add(mainPanel);

            this.pack();
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    disableMainWindow = false;
                }
            });
        }

        
    }

    public class ShowDoctorDetails extends JFrame {
        
        JPanel mainPanel;
        JLabel lblName, lblSpeciality, lblWorkingHours;
        JTextField tfName, tfSpeciality, tfWorkingHours;

        public ShowDoctorDetails(int id)
        {
            
            if(disableDoctorWindow) return;
            else disableDoctorWindow = true;

            setTitle("Doctor Details");

            JPanel panel = (JPanel)getContentPane();

            lblName = new JLabel("Name:");
            lblSpeciality = new JLabel("Speciality:");
            lblWorkingHours = new JLabel("Working Hours:");

            tfName = new JTextField(listOfDoctors[id], 20);
            tfSpeciality = new JTextField("Cardiology", 20);
            tfWorkingHours = new JTextField("8:00AM-3:00PM", 20);

            mainPanel = new JPanel(new GridLayout(3, 2));


            mainPanel.add(lblName);
            mainPanel.add(tfName);
            mainPanel.add(lblSpeciality);
            mainPanel.add(tfSpeciality);
            mainPanel.add(lblWorkingHours);
            mainPanel.add(tfWorkingHours);

            panel.add(mainPanel);

            this.pack();
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    disableDoctorWindow = false;
                }
            });
        }
    }
}
