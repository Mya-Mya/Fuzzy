package ex1;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class ChasingAgent extends JFrame {
    public ChasingAgent(){
        super("Fuzzy1 Example1");
        setPreferredSize(new Dimension(600,500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new MainPanel());
        setVisible(true);
        pack();
    }
}
