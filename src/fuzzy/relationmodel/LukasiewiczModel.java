package fuzzy.relationmodel;

import fuzzy.Rule;

/**
 * Lukasiewicz のファジィ関係モデル
 * μR(x+,y) = 1 ∧ (1 - ω + μB(y))
 */
public class LukasiewiczModel extends RelationModel {
    private double oneMinusAllAntecedentPartGoodness;

    @Override
    public void setAllAntecedentPartGoodness(double allAntecedentPartGoodness) {
        super.setAllAntecedentPartGoodness(allAntecedentPartGoodness);
        oneMinusAllAntecedentPartGoodness=1-allAntecedentPartGoodness;
    }
    @Override
    public double getConsequentValue(double y) throws Rule.NoAntecedentPartListException {
        return Math.min(1,oneMinusAllAntecedentPartGoodness+consequentFuzzySet.invokeMembershipFunction(y));
    }
}
