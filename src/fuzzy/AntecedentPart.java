package fuzzy;

/**
 * 1つの前件部を表す。1つの入力値への参照 x と1つのファジィ集合 A から成り立つ。
 */
public class AntecedentPart {
    private InputValue inputValue;
    private FuzzySet fuzzySet;
    public AntecedentPart(InputValue inputValue, FuzzySet fuzzySet){
        this.inputValue=inputValue;
        this.fuzzySet = fuzzySet;
    }

    /**
     * 現在の入力値に応じた適合度を返す。
     * @return 適合度 μ(x+)
     */
    public double getGoodness(){
        return fuzzySet.invokeMembershipFunction(inputValue.getValue());
    }

    @Override
    public String toString() {
        return inputValue.toString()+" is "+ fuzzySet.toString();
    }
}
