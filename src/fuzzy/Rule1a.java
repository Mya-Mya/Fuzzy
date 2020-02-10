package fuzzy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * ファジィ推論法1aに基づく1つのファジィ制御則を表す。
 * 1つ以上の前件部と1つの後件部から成り立つ。
 */
public class Rule1a {
    public class NoAntecedentPartListException extends Exception{}
    double allAntecedentPartGoodness;
    private List<AntecedentPart> antecedentPartList=new ArrayList<>();
    private Membership consequentMembership;

    /**
     *
     * @param consequentMembership 後件部のメンバーシップ関数B(y)
     */
    public Rule1a(Membership consequentMembership){
        this.consequentMembership=consequentMembership;
    }
    public void addAntecedentPart(AntecedentPart antecedentPart){
        antecedentPartList.add(antecedentPart);
    }

    /**
     * このファジィ制御則の前件部全体の適合度ωを更新する。
     */
    public void updateAllAntecedentPartGoodness() throws NoAntecedentPartListException {
        if(antecedentPartList.isEmpty())throw new NoAntecedentPartListException();
        allAntecedentPartGoodness=antecedentPartList.stream()
                .min(Comparator.comparing(AntecedentPart::getGoodness))
                .get()
                .getGoodness();
    }

    /**
     * このファジィ制御則の前件部全体の適合度ωを返す。
     * このメソッドを呼ぶ前に、updateAllAntecedentPartGoodnessが呼ばれている必要がある。
     * @return 適合度ω
     */
    public double getAllAntecedentPartGoodness(){
        return allAntecedentPartGoodness;
    }

    /**
     * このファジィ制御則の推論結果関数 B0_i:Y->[0,1] の値を返す。
     * このメソッドを呼ぶ前に、updateAllAntecedentPartGoodnessが呼ばれている必要がある。
     * @param y∈Y
     * @return 推論結果
     */
    public double getConsequentValue(double y) throws NoAntecedentPartListException {
        return Math.min(consequentMembership.getValue(y),allAntecedentPartGoodness);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("IF");
        Iterator<AntecedentPart> antecedentPartItr=antecedentPartList.iterator();
        while(antecedentPartItr.hasNext()){
            sb.append(" ");
            sb.append(antecedentPartItr.next().toString());
            sb.append(" ");
            if(antecedentPartItr.hasNext()){
                sb.append("and");
            }
        }

        sb.append("THEN ");
        sb.append(consequentMembership.toString());
        return sb.toString();
    }
}
