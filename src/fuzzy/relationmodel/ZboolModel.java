package fuzzy.relationmodel;

import fuzzy.FuzzySet;
import fuzzy.Rule;

/**
 * ブール演算を拡張したファジィ関係モデル。
 * μR(x+,y) = (ω ∧ μB(y)) ∨ (1-ω)
 */
public class ZboolModel extends RelationModel{
    public ZboolModel(double allAntecedentPartGoodness, FuzzySet consequentFuzzySet) {
    }

    private double oneMinusAllAntecedentPartGoodness;

    @Override
    public void setAllAntecedentPartGoodness(double allAntecedentPartGoodness) {
        super.setAllAntecedentPartGoodness(allAntecedentPartGoodness);
        oneMinusAllAntecedentPartGoodness=1-allAntecedentPartGoodness;
    }

    @Override
    public double getConsequentValue(double y) throws Rule.NoAntecedentPartListException, FuzzySet.MembershipFunctionIllegalOutputException {
        return Math.max(Math.min(allAntecedentPartGoodness,consequentFuzzySet.invokeMembershipFunction(y)),oneMinusAllAntecedentPartGoodness);
    }
}
