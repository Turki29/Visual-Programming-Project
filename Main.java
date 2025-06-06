import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame{
    
    private JPanel p0,p1,p2,p3;
    private JTextField fieldUsername, fieldPassword; 
    private JLabel lblHeading, lblUsername, lblPassword ;
    private JButton btnRegister, btnLogin;
    public Main(String title )
    {
        super(title);
        
        this.setLocation(1040,540);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = (JPanel)this.getContentPane();

        panel.setLayout(new GridLayout(5,1));

        fieldUsername = new JTextField(15);
        fieldPassword = new JTextField(15);

        lblHeading = new JLabel("Login ",JLabel.CENTER);
        lblUsername = new JLabel("Username :");
        lblPassword = new JLabel("Password :");
        
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new UserInterface());
        btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                new RegisterPage("Register Page");
            }
        });
        p0 = new JPanel(new FlowLayout());
        p1 = new JPanel(new FlowLayout());
        p2  = new JPanel(new FlowLayout());
        p3  = new JPanel(new FlowLayout());

        p0.add(lblHeading);

        p1.add(lblUsername);
        p1.add(fieldUsername);

        p2.add(lblPassword);
        p2.add(fieldPassword);

        p3.add(btnLogin);
        p3.add(btnRegister);

        
        panel.add(p0);
        panel.add(p1); 
        panel.add(p2);
        panel.add(p3);
        
        
        this.pack();

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String args[])
    {
        Main frame = new Main("Hospital");
    }


    public class UserInterface  implements ActionListener{

        public void actionPerformed(ActionEvent e)
        {
            dispose(); // Close the frame
            String username = fieldUsername.getText();
            
            if(username.toLowerCase().equals("admin"))
            {
                 AdminInterface adminInterface = new AdminInterface("Admin Interface");
            }
           else if (username.toLowerCase().equals("patient"))
           {
            PatientsInterface patientsInterface = new PatientsInterface("Patients Interface");
           }
           else if (username.toLowerCase().equals("doctor"))
           {
            DoctorInterface doctorInterface = new DoctorInterface("Doctor Interface");
           }
            
        }
    }
}
