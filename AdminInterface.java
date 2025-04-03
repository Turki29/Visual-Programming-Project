import javax.swing.JFrame;

public class AdminInterface extends JFrame {
    public AdminInterface(String title) {
        super(title);

        // Set size of the window
        setSize(800, 600); // Width: 800, Height: 600

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Make the window visible
        setVisible(true);

        // Set default close operation
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

   
}