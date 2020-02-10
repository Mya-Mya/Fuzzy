package fuzzy;

/**
 * 1つの前件部を表す。1つの入力値への参照と1つのメンバーシップ関数から成り立つ。
 */
public class AntecedentPart {
    private InputValue inputValue;
    private Membership membership;
    public AntecedentPart(InputValue inputValue, Membership membership){
        this.inputValue=inputValue;
        this.membership=membership;
    }

    /**
     * 現在の入力値に応じた適合度 A:X->[0,1] を返す。
     * @return 適合度A(x)
     */
    public double getGoodness(){
        return membership.getValue(inputValue.getValue());
    }

    @Override
    public String toString() {
        return inputValue.toString()+" is "+membership.toString();
    }
}
