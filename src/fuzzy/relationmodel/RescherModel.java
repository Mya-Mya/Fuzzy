package fuzzy.relationmodel;

import fuzzy.FuzzySet;
import fuzzy.Rule;

/**
 * Rescher のファジィ関係モデル。
 * μR(x+,y) = 1 if ω ≦ μB(y)
 *            0 if ω ＜μB(y)
 */
public class RescherModel extends RelationModel{
    public RescherModel() {
    }

    @Override
    public double getConsequentValue(double y) throws Rule.NoAntecedentPartListException {
        if(allAntecedentPartGoodness<=consequentFuzzySet.invokeMembershipFunction(y))return 1;
        return 0;
    }
}
