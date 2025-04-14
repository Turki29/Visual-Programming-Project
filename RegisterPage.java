import java.awt.*;
import javax.swing.*;

public class RegisterPage extends JFrame {
  

    private JLabel lblEmail,lblUsername,lblPassword,lblGender,lblAge,lblPhone;
    private JTextField txtEmail, txtUsername,txtPhone;
    private JPasswordField txtPassword;
    private JRadioButton male, female;
    private JComboBox<String> ageCombo;
    private JButton btnCreate,btnCancel;

    public RegisterPage(String title) {
        super(title);

        setTitle("Create Account");
        setSize(460, 290);
        setLocation(700,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       

    
        JPanel p1 = (JPanel) getContentPane();
        p1.setLayout(new GridLayout(7, 2, 5, 5));

      
        lblEmail = new JLabel("  Email:");
        txtEmail = new JTextField();

        lblUsername = new JLabel("  Username:");
        txtUsername = new JTextField();

        lblPassword = new JLabel("  Password:");
        txtPassword = new JPasswordField();
        
        lblPhone = new JLabel("  Phone number:");
        txtPhone = new JTextField();

        lblGender = new JLabel("  Gender:");
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderPanel.add(male);
        genderPanel.add(female);

        
        
        lblAge = new JLabel("  Birth Date:");

     //days
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
          days[i] = String.valueOf(i + 1);
        }
    JComboBox<String> dayCombo = new JComboBox<>(days);

//months
    String[] months = {
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
};
        JComboBox<String> monthCombo = new JComboBox<>(months);

 //years   
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        String[] years = new String[150];
        for (int i = 0; i < 150; i++) {
        years[i] = String.valueOf(currentYear - i); 
        }
    JComboBox<String> yearCombo = new JComboBox<>(years);

 
    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    datePanel.add(dayCombo);
    datePanel.add(monthCombo);
    datePanel.add(yearCombo);
     
        btnCreate = new JButton("Create");
        btnCancel = new JButton("Cancel");
    
        p1.add(lblEmail);
        p1.add(txtEmail);
        p1.add(lblUsername);
        p1.add(txtUsername);
        p1.add(lblPassword);
        p1.add(txtPassword);
        p1.add(lblPhone);
        p1.add(txtPhone);     
        p1.add(lblGender);
        p1.add(genderPanel);
        p1.add(lblAge);
        p1.add(datePanel);
        p1.add(btnCancel); 
        p1.add(btnCreate);
        
       
        btnCreate.addActionListener(e -> JOptionPane.showMessageDialog (this, "Account Created Successfully!"));
        btnCancel.addActionListener(e -> dispose());
        
        setVisible(true);
    }

}
