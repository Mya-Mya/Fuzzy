package fuzzy;

import fuzzy.relationmodel.RelationModel;
import fuzzy.set.FuzzySet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 1つ以上の前件部と1つの後件部から成り立つファジィ制御則 R_i を表す。
 * 前件部同士は ∧ 結合される。
 */
public class Rule {
    public class NoAntecedentPartListException extends Exception{}

    double allAntecedentPartGoodness;
    private List<AntecedentThesis> antecedentThesisList =new ArrayList<>();
    private FuzzySet consequentFuzzySet;
    private RelationModel relationModel;

    /**
     * @param consequentFuzzySet 後件部のファジィ集合 B
     * @param relationModel 前件部と後件部とのファジィ関係モデル R
     */
    public Rule(FuzzySet consequentFuzzySet, RelationModel relationModel){
        this(null,consequentFuzzySet,relationModel);
    }
    /**
     * @param antecedentThesis 1つの前件部のファジィ命題 P_ij
     * @param consequentFuzzySet 後件部のファジィ集合 B
     * @param relationModel 前件部と後件部とのファジィ関係モデル R
     */
    public Rule(AntecedentThesis antecedentThesis, FuzzySet consequentFuzzySet, RelationModel relationModel){
        this.consequentFuzzySet = consequentFuzzySet;
        this.relationModel=relationModel;
        relationModel.setConsequentFuzzySet(consequentFuzzySet);
        addAntecedentPart(antecedentThesis);
    }
    public void addAntecedentPart(AntecedentThesis antecedentThesis){
        antecedentThesisList.add(antecedentThesis);
    }

    /**
     * このファジィ制御則の前件部全体の適合度 ω を更新する。
     */
    public void updateAllAntecedentPartGoodness() throws NoAntecedentPartListException {
        if(antecedentThesisList.isEmpty())throw new NoAntecedentPartListException();
        allAntecedentPartGoodness= antecedentThesisList.stream()
                .min(Comparator.comparing(AntecedentThesis::getGoodness))
                .get()
                .getGoodness();
        relationModel.setAllAntecedentPartGoodness(allAntecedentPartGoodness);
    }

    /**
     * このファジィ制御則の前件部全体の適合度 ω を返す。
     * @return 適合度 ω
     */
    public double getAllAntecedentPartGoodness(){
        return allAntecedentPartGoodness;
    }

    /**
     * このファジィ制御則の結論関数 B_i*:Y->[0,1] の値を返す。
     * このメソッドを呼ぶ前に、updateAllAntecedentPartGoodnessが呼ばれている必要がある。
     * @param y∈Y
     * @return 結論
     */
    public double getConsequentValue(double y) throws NoAntecedentPartListException {
        return relationModel.getConsequentValue(y);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("IF");
        Iterator<AntecedentThesis> antecedentPartItr= antecedentThesisList.iterator();
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
