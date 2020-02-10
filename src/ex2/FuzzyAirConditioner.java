package ex2;

import fuzzy.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FuzzyAirConditioner extends JFrame implements ChangeListener {

    private FuzzyInterface1 R;
    private InputValue x1;

    private JLabel tCurrentTemp;
    private JLabel tAirConditionerSetting;
    private JSlider sTemp;

    public FuzzyAirConditioner(){
        super("FuzzyAirConditioner");
        setPreferredSize(new Dimension(500,200));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /**
         * R: 空調の温度設定 y∈[-6,6] を論議するにおいて
         * R1: 今の気温 x1 が A1: 暑かったら B1: 冷房にする
         * R2: 今の気温 x1 が A2: ちょうどよかったら B2: だいたい何も変えない
         * R3: 今の気温 x1 が A3: 寒かったら B3: 暖房にする
         * ただし x1∈(0,40)
         */

        x1=new InputValue(10);
        x1.setDescription("今の気温");

        Membership A1=new TriangleMambership(22,30,43);
        A1.setDescription("暑い");
        Membership B1=new TriangleMambership(-7,-5,-3);
        B1.setDescription("冷房にする");
        Rule1a R1=new Rule1a(B1);
        R1.addAntecedentPart(new AntecedentPart(x1,A1));

        Membership A2=new TrapezoidMembership(12,22,25,35);
        A2.setDescription("ちょうどよい");
        Membership B2=new TriangleMambership(-3,0,3);
        B2.setDescription("だいたい何も変えない");
        Rule1a R2=new Rule1a(B2);
        R2.addAntecedentPart(new AntecedentPart(x1,A2));

        Membership A3=new TrapezoidMembership(0,0,9,18);
        A3.setDescription("寒い");
        Membership B3=new TriangleMambership(4,8,10);
        B3.setDescription("暖房にする");
        Rule1a R3=new Rule1a(B3);
        R3.addAntecedentPart(new AntecedentPart(x1,A3));

        List<Double>outputRange=new ArrayList<>();
        for(double y=-6;y<=6;y+=0.2){
            outputRange.add(y);
        }
        R=new FuzzyInterface1(outputRange);
        R.addRule(R1);
        R.addRule(R2);
        R.addRule(R3);
        System.out.println(R.toString());

        tCurrentTemp=new JLabel();
        add(tCurrentTemp,BorderLayout.NORTH);
        tAirConditionerSetting=new JLabel();
        tAirConditionerSetting.setFont(new Font("メイリオ",Font.BOLD,30));
        add(tAirConditionerSetting,BorderLayout.CENTER);
        sTemp=new JSlider(0,400,10);
        sTemp.addChangeListener(this);
        add(sTemp,BorderLayout.SOUTH);

        setCurrentTemp(sTemp.getValue()/10);
        pack();
        setVisible(true);
    }

    private void setCurrentTemp(double t){
        x1.setValue(t);
        double consequent=0;
        try {
            R.inputValueChanged();
        } catch (Rule1a.NoAntecedentPartListException e) {
            e.printStackTrace();
        }
        try {
            consequent = R.getConsequent();
        } catch (FuzzyInterface1.NoRuleException e) {
            e.printStackTrace();
        } catch (Rule1a.NoAntecedentPartListException e) {
            e.printStackTrace();
        }

        tCurrentTemp.setText("現在の気温: "+Double.toString(t)+" ℃");
        double roundConsequent=Double.parseDouble(String.format("%.2f",consequent));
        StringBuilder consequentText=new StringBuilder();
        consequentText.append("空調設定 : ");
        if(roundConsequent==0){
            tAirConditionerSetting.setForeground(Color.BLACK);
            consequentText.append("OFF");
        }else if(roundConsequent<0){
            tAirConditionerSetting.setForeground(Color.BLUE);
            consequentText.append(roundConsequent);
            consequentText.append(" ℃");
        }else{
            tAirConditionerSetting.setForeground(Color.RED);
            consequentText.append("+");
            consequentText.append(roundConsequent);
            consequentText.append(" ℃");
        }
        tAirConditionerSetting.setText(consequentText.toString());
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        setCurrentTemp((double)sTemp.getValue()/10.0);
    }
}
