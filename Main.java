

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame{
    
    JPanel panel1;
    JTextField fieldUsername, fieldPassword; 
    JLabel lblHeading, lblUsername, lblPassword ;
    JButton btnRegister, btnLogin;
    public Main(String title )
    {
        super(title);

        JPanel panel = (JPanel)this.getContentPane();

        panel.setLayout(new BorderLayout());

        fieldUsername = new JTextField(10);
        fieldPassword = new JTextField(10);

        lblHeading = new JLabel("Login ");
        lblUsername = new JLabel("Username :");
        lblPassword = new JLabel("Password :");
        
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new UserInterface());
        btnRegister = new JButton("Register");

        panel1 = new JPanel(new GridLayout(3,2));

        panel1.add(lblUsername);
        panel1.add(fieldUsername);

        panel1.add(lblPassword);
        panel1.add(fieldPassword);

        panel1.add(btnLogin);
        panel1.add(btnRegister);

        panel.add(lblHeading,  BorderLayout.NORTH);
        panel.add(panel1); 
        

        
        this.pack();
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String args[])
    {
        Main frame = new Main("title");
    }

    public class UserInterface  implements ActionListener{

        public void actionPerformed(ActionEvent e)
        {
            dispose(); // Close the frame
            AdminInterface adminInterface = new AdminInterface("Admin Interface");
        }
    }
}