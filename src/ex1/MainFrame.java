package ex1;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(){
        super("Fuzzy1 Example1");
        setPreferredSize(new Dimension(600,500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new MainPanel());
        setVisible(true);
        pack();
    }
}
