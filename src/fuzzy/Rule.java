package fuzzy;

import fuzzy.relationmodel.RelationModel;
import fuzzy.set.FuzzySet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 1つ以上の前件部と1つの後件部から成り立つファジィ制御則を表す。
 * 前件部同士は ∧ 結合される。
 */
public class Rule {
    public class NoAntecedentPartListException extends Exception{}

    double allAntecedentPartGoodness;
    private List<AntecedentPart> antecedentPartList=new ArrayList<>();
    private FuzzySet consequentFuzzySet;
    private RelationModel relationModel;

    /**
     * @param consequentFuzzySet 後件部のファジィ集合B
     * @param relationModel 前件部と後件部とのファジィ関係モデル
     */
    public Rule(FuzzySet consequentFuzzySet, RelationModel relationModel){
        this.consequentFuzzySet = consequentFuzzySet;
        this.relationModel=relationModel;
        relationModel.setConsequentFuzzySet(consequentFuzzySet);
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
        relationModel.setAllAntecedentPartGoodness(allAntecedentPartGoodness);
    }

    /**
     * このファジィ制御則の前件部全体の適合度ωを返す。
     * @return 適合度ω
     */
    public double getAllAntecedentPartGoodness(){
        return allAntecedentPartGoodness;
    }

    /**
     * このファジィ制御則の推論結果関数 B+:Y->[0,1] の値を返す。
     * このメソッドを呼ぶ前に、updateAllAntecedentPartGoodnessが呼ばれている必要がある。
     * @param y∈Y
     * @return 推論結果
     */
    public double getConsequentValue(double y) throws NoAntecedentPartListException {
        return relationModel.getConsequentValue(y);
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
        sb.append(consequentFuzzySet.toString());
        return sb.toString();
    }
}
