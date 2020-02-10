package ex2;

import fuzzy.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FuzzyAirConditioner {
    public FuzzyAirConditioner(){
        /**
         * R: 空調の温度設定 y∈[-6,6] を論議するにおいて
         * R1: 今の気温 x1 が A1: 暑かったら B1: 冷房にする
         * R2: 今の気温 x1 が A2: ちょうどよかったら B2: だいたい何も変えない
         * R3: 今の気温 x1 が A3: 寒かったら B3: 暖房にする
         * ただし x1∈(0,40)
         */

        InputValue x1=new InputValue(10);
        x1.setDescription("今の気温");

        Membership A1=new TriangleMambership(22,30,40);
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
        FuzzyInterface1 R=new FuzzyInterface1(outputRange);
        R.addRule(R1);
        R.addRule(R2);
        R.addRule(R3);
        System.out.println(R.toString());

        double t=10;
        while(t!=-10){
            System.out.println("温度は？");
            String input=new Scanner(System.in).nextLine();
            t=Double.parseDouble(input);
            x1.setValue(t);

            try {
                R.inputValueChanged();
            } catch (Rule1a.NoAntecedentPartListException e) {
                e.printStackTrace();
            }
            try {
                double consequent = R.getConsequent();
                System.out.println(consequent);
            } catch (FuzzyInterface1.NoRuleException e) {
                e.printStackTrace();
            } catch (Rule1a.NoAntecedentPartListException e) {
                e.printStackTrace();
            }
        }
    }
}
