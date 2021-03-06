package fuzzy.relationmodel;

import fuzzy.set.FuzzySet;
import fuzzy.Rule;

/**
 * ∧ 結合された前件部のファジィ集合から後件部のファジィ集合を説明するファジィ関係モデル R 。
 * このクラスでは実測値による前件部からの適合度 ω と後件部のファジィ集合 B から結論となるファジィ集合 B* を予測する。
 * 注意！このインスタンスは複数のファジィ制御則で使いまわしてはいけない！
 *
 * メモ：使うファジィ関係モデルによって、推奨されるファジィ制御則の結合方法が異なる。
 * Mamdaniモデルのみブール和結合(FuzzyInterface1.RULES_SUM_COMBINATION)が推奨され、
 * それ以外のモデルはブール積結合(FuzzyInterface1.RULES_MULT_COMBINATION)が推奨される。
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

    public abstract double getConsequentValue(double y) throws Rule.NoAntecedentPartListException;
}
