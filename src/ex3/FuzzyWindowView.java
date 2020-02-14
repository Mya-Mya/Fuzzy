package ex3;

import javax.swing.*;
import java.awt.*;

public class FuzzyWindow extends JFrame {

    private JSlider vTemparetureValueSlider;
    private JSlider vTimeValueSlider;

    public FuzzyWindow(){
        super("Fuzzy Example3 Fuzzy Window");
        setPreferredSize(new Dimension(400,200));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
