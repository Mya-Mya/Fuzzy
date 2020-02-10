package fuzzy;

/**
 * メンバーシップ関数のスーパークラス。
 * メンバーシップ関数 g は g:X->[0,1] の連続した関数でなければならない。
 */
abstract public class Membership {
    public class MembershipIllegalOutputException extends Exception{
    }
    private String description;
    public Membership(){

    }
    public double invokeGetValue(double value) throws MembershipIllegalOutputException {
        double res=getValue(value);
        if(value<0||1<value)throw new MembershipIllegalOutputException();
        return res;
    }
    abstract protected double getValue(double value);

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
