package fuzzy.fuzzyinterface;

import fuzzy.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * 直説法ファジィ推論を提供する。
 */
public class FuzzyInterface1 {
    public static final int RULES_SUM_COMBINATION=0; //ルール同士をブール和算(MAX)で結合する
    public static final int RULES_MULT_COMBINATION=1; //ルール同士をブール積算(MIN)で結合する

    public class NoRuleException extends Exception{}

    private List<Rule> ruleList=new ArrayList<>();
    private List<Double> outputRange;
    private int rulesCombination;

    /**
     * @param yValues 非ファジィ化時に用いる y 座標のリスト
     * @param rulesCombination ルール同士の結合方法
     */
    public FuzzyInterface1(List<Double>yValues, int rulesCombination){
        this.outputRange=yValues;
        this.rulesCombination=rulesCombination;
    }

    public void addRule(Rule rule){
        ruleList.add(rule);
    }

    /**
     * 入力値が変化した時に知らせること。
     * 各ファジィ制御則の前件部の適合度ωを更新する。
     */
    public void inputValueChanged() throws Rule.NoAntecedentPartListException {
        for(Rule r:ruleList){
            r.updateAllAntecedentPartGoodness();
        }
    }
    /**time
     * すべてのファジィ制御則の推論結果関数 μB*:Y->[0,1] を返す。
     * @param y∈Y
     * @return 推論結果
     */
    public double getConsequentValue(double y) throws NoRuleException, Rule.NoAntecedentPartListException {
        if(ruleList.isEmpty())throw new NoRuleException();
        double res=ruleList.get(0).getConsequentValue(y);
        for(int i=1;i<ruleList.size();i++){
            double tmpConsequentValue=ruleList.get(i).getConsequentValue(y);
            if(rulesCombination==RULES_SUM_COMBINATION){// res は consequentValue のうちの最大値を求める
                if(res<tmpConsequentValue)res=tmpConsequentValue;
            }else if(rulesCombination==RULES_MULT_COMBINATION){// res は consequentValue のうちの最小値を求める
                if(res>tmpConsequentValue)res=tmpConsequentValue;
            }
        }
        return res;
    }

    /**
     * 現在の入力値とファジィ制御則に基づいた結論を重心法を用いて返す。
     * このメソッドを呼ぶ前にinputValueChangedを呼ぶ必要がある。
     * @return 結論y
     */
    public double getConsequent() throws NoRuleException, Rule.NoAntecedentPartListException {
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
        for(Rule rule :ruleList)sb.append(rule.toString()).append(System.getProperty("line.separator"));
        return sb.toString();
    }
}
