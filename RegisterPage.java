import java.awt.*;
import javax.swing.*;

public class RegisterPage extends JFrame {
  

    private JLabel lblEmail,lblUsername,lblPassword,lblGender,lblAge;
    private JTextField txtEmail, txtUsername;
    private JPasswordField txtPassword;
    private JRadioButton male, female;
    private JComboBox<String> ageCombo;
    private JButton btnCreate,btnCancel;

    public RegisterPage(String title) {
        super(title);

        setTitle("Create Account");
        setSize(350, 250);
        setLocation(700,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       

    
        JPanel p1 = (JPanel) getContentPane();
        p1.setLayout(new GridLayout(6, 2, 5, 5));

      
        lblEmail = new JLabel("  Email:");
        txtEmail = new JTextField();

        lblUsername = new JLabel("  Username:");
        txtUsername = new JTextField();

        lblPassword = new JLabel("  Password:");
        txtPassword = new JPasswordField();

        lblGender = new JLabel("  Gender:");
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderPanel.add(male);
        genderPanel.add(female);

        lblAge = new JLabel("  Age:");
        
        
        String[] ages = new String[130];
        for (int i = 0; i < 130; i++) {
            ages[i] = String.valueOf(i + 1); // Convert numbers to String
        }
        ageCombo = new JComboBox<>(ages);

        btnCancel = new JButton("Cancel");
        btnCreate = new JButton("Create");
        
    
        p1.add(lblEmail);
        p1.add(txtEmail);
        p1.add(lblUsername);
        p1.add(txtUsername);
        p1.add(lblPassword);
        p1.add(txtPassword);
        p1.add(lblGender);
        p1.add(genderPanel);
        p1.add(lblAge);
        p1.add(ageCombo);
        p1.add(btnCreate);
        p1.add(btnCancel); 
        
        

       
        btnCreate.addActionListener(e -> JOptionPane.showMessageDialog (this, "Account Created Successfully!"));
        btnCancel.addActionListener(e -> dispose());
        
        setVisible(true);
    }

}