package fuzzy;

import java.util.ArrayList;
import java.util.List;

/**
 * 1つのファジィ推論法1を表す。
 * 後件部がメンバーシップ関数である。
 */
public class FuzzyInterface1 {
    public class NoRuleException extends Exception{}
    private List<Rule1a> ruleList=new ArrayList<>();
    private List<Double> outputRange;

    /**
     *
     * @param outputRange 結論となる値の値域Y
     */
    public FuzzyInterface1(List<Double>outputRange){
        this.outputRange=outputRange;
    }

    public void addRule(Rule1a rule){
        ruleList.add(rule);
    }

    /**
     * 入力値が変化した時に知らせること。
     * 各ファジィ制御則の前件部の適合度ωを更新する。
     */
    public void inputValueChanged() throws Rule1a.NoAntecedentPartListException {
        for(Rule1a r:ruleList){
            r.updateAllAntecedentPartGoodness();
        }
    }
    /**
     * すべてのファジィ制御則の推論結果関数 B0:Y->[0,1] を返す。
     * @param y∈Y
     * @return 推論結果
     */
    public double getConsequentValue(double y) throws NoRuleException, Rule1a.NoAntecedentPartListException {
        if(ruleList.isEmpty())throw new NoRuleException();
        double max=ruleList.get(0).getConsequentValue(y);
        for(int i=1;i<ruleList.size();i++){
            double tmpConsequentValue=ruleList.get(i).getConsequentValue(y);
            if(max<tmpConsequentValue)max=tmpConsequentValue;
        }
        return max;
    }

    /**
     * 現在の入力値とファジィ制御則に基づいた結論を重心法を用いて返す。
     * このメソッドを呼ぶ前にinputValueChangedを呼ぶ必要がある。
     * @return 結論y
     */
    public double getConsequent() throws NoRuleException, Rule1a.NoAntecedentPartListException {
        double denominator=0;//ΣB0(y)y
        double numerator=0;//ΣB0(y)
        for(Double y:outputRange){
            double b0y=getConsequentValue(y);
            denominator+=b0y*y;
            numerator+=b0y;
        }
        if(numerator==0){
            return 0;}
        return denominator/numerator;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        for(Rule1a rule1A :ruleList)sb.append(rule1A.toString()).append(System.getProperty("line.separator"));
        return sb.toString();
    }
}
