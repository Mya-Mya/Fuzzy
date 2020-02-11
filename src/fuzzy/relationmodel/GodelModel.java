package fuzzy.relationmodel;

import fuzzy.Rule;

/**
 * Gödel のファジィ関係モデル。
 * μR(x+,y) = 1     if ω ≦ μB(y)
 *            μB(y) if ω > μB(y)
 */
public class GodelModel extends RelationModel{
    @Override
    public double getConsequentValue(double y) throws Rule.NoAntecedentPartListException {
        double myuBy=consequentFuzzySet.invokeMembershipFunction(y);
        if(allAntecedentPartGoodness<=myuBy)return 1;
        return myuBy;
    }
}
