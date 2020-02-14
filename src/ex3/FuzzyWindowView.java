package ex3;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class FuzzyWindowView extends JFrame implements IFuzzyWindowView, ChangeListener {

    private final FuzzyWindowModel model;
    private final Font mSmallFont;
    private final Font mLargeFont;
    private JLabel vCurrentTemperatureTextLabel;
    private JLabel vCurrentTimeTextLabel;
    private JLabel vCurrentWindowStatusTextLabel;
    private JSlider vTemperatureValueSlider;
    private JSlider vTimeValueSlider;

    public FuzzyWindowView(){
        super("Fuzzy Example3 Fuzzy Window");
        setPreferredSize(new Dimension(500,300));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mSmallFont = new Font("メイリオ",Font.PLAIN,20);
        mLargeFont = new Font("メイリオ",Font.BOLD,30);

        vCurrentTemperatureTextLabel=new JLabel();
        vCurrentTemperatureTextLabel.setFont(mSmallFont);
        vCurrentTimeTextLabel=new JLabel();
        vCurrentTimeTextLabel.setFont(mSmallFont);
        JPanel vCurrentEnvPanel=new JPanel();
        vCurrentEnvPanel.setLayout(new GridLayout(2,1));
        vCurrentEnvPanel.add(vCurrentTemperatureTextLabel);
        vCurrentEnvPanel.add(vCurrentTimeTextLabel);
        add(vCurrentEnvPanel,BorderLayout.NORTH);

        vCurrentWindowStatusTextLabel =new JLabel();
        vCurrentWindowStatusTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        vCurrentWindowStatusTextLabel.setFont(mLargeFont);
        add(vCurrentWindowStatusTextLabel,BorderLayout.CENTER);

        JPanel vSlidersPanel=new JPanel();
        GridBagLayout layout=new GridBagLayout();
        vSlidersPanel.setLayout(layout);
        GridBagConstraints constraints=new GridBagConstraints();

        constraints.gridwidth=1;
        constraints.gridx=0;
        constraints.gridy=0;
        JLabel vTemperatureValueSliderHelper=new JLabel("気温",SwingConstants.RIGHT);
        vTemperatureValueSliderHelper.setFont(mSmallFont);
        layout.setConstraints(vTemperatureValueSliderHelper,constraints);
        vSlidersPanel.add(vTemperatureValueSliderHelper);

        constraints.gridwidth=4;
        constraints.gridx=1;
        vTemperatureValueSlider=new JSlider();
        vTemperatureValueSlider.setFont(mSmallFont);
        vTemperatureValueSlider.setMinimum((int) FuzzyWindowModel.TEMP_RANGE.min*100);
        vTemperatureValueSlider.setMaximum((int) FuzzyWindowModel.TEMP_RANGE.max*100);
        vTemperatureValueSlider.addChangeListener(this);
        layout.setConstraints(vTemperatureValueSlider,constraints);
        vSlidersPanel.add(vTemperatureValueSlider);

        constraints.gridwidth=1;
        constraints.gridx=0;
        constraints.gridy=1;
        JLabel vTimeValueSliderHelper=new JLabel("時刻",SwingConstants.RIGHT);
        vTimeValueSliderHelper.setFont(mSmallFont);
        layout.setConstraints(vTimeValueSliderHelper,constraints);
        vSlidersPanel.add(vTimeValueSliderHelper);

        constraints.gridwidth=4;
        constraints.gridx=1;
        vTimeValueSlider=new JSlider();
        vTimeValueSlider.setFont(mSmallFont);
        vTimeValueSlider.setMinimum((int) FuzzyWindowModel.TIME_RANGE.min*100);
        vTimeValueSlider.setMaximum((int) FuzzyWindowModel.TIME_RANGE.max*100);
        vTimeValueSlider.addChangeListener(this);
        layout.setConstraints(vTimeValueSlider,constraints);
        vSlidersPanel.add(vTimeValueSlider);

        add(vSlidersPanel,BorderLayout.SOUTH);
        pack();
        setVisible(true);

        model=new FuzzyWindowModel(this);
    }

    @Override
    public void setCurrentTemperatureText(String t) {
        vCurrentTemperatureTextLabel.setText(t);
    }

    @Override
    public void setCurrentTimeText(String t) {
        vCurrentTimeTextLabel.setText(t);
    }

    @Override
    public void setCurrentWindowStatusText(String t) {
        vCurrentWindowStatusTextLabel.setText(t);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        Object source=changeEvent.getSource();
        if(source==vTemperatureValueSlider){
            model.setTemperature(vTemperatureValueSlider.getValue()*0.01);
        }else if(source==vTimeValueSlider){
            model.setTime(vTimeValueSlider.getValue()*0.01);
        }
    }
}
