package fuzzy.relationmodel;

import fuzzy.FuzzySet;
import fuzzy.Rule;

/**
 * ∧ 結合された前件部のファジィ集合から後件部のファジィ集合を説明するファジィ関係モデル。
 * このクラスでは実測値による前件部からの適合度ω と後件部のファジィ集合から結論となるファジィ集合を予測する。
 */
abstract public class RelationModel {
    protected double allAntecedentPartGoodness;
    protected FuzzySet consequentFuzzySet;

    public void setConsequentFuzzySet(FuzzySet consequentFuzzySet) {
        this.consequentFuzzySet=consequentFuzzySet;
    }

    public void setAllAntecedentPartGoodness(double allAntecedentPartGoodness) {
        this.allAntecedentPartGoodness = allAntecedentPartGoodness;
    }

    public abstract double getConsequentValue(double y) throws Rule.NoAntecedentPartListException, FuzzySet.MembershipFunctionIllegalOutputException;
}
