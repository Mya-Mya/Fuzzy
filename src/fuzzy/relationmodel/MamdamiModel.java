package fuzzy.relationmodel;

import fuzzy.FuzzySet;
import fuzzy.Rule;

/**
 * Mamdami のファジィ関係モデル。
 * μR(x+,y) = ω ∧ μB(y)
 */
public class MamdamiModel extends RelationModel{

    public MamdamiModel(double allAntecedentPartGoodness, FuzzySet consequentFuzzySet) {
    }

    @Override
    public double getConsequentValue(double y) throws Rule.NoAntecedentPartListException, FuzzySet.MembershipFunctionIllegalOutputException {
        return Math.min(allAntecedentPartGoodness,consequentFuzzySet.invokeMembershipFunction(y));
    }
}
