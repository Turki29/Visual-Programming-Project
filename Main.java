import javax.swing.JFrame;
import java.awt.*;
import javax.swing.JPanel;

public class Main extends JFrame{
    
    public Main(String title )
    {
        super(title);

        JPanel panel = (JPanel)this.getContentPane();

        panel.setLayout(new BorderLayout());
        this.pack();
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String args[])
    {
        Main frame = new Main("title");
    }
}