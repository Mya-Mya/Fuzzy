package fuzzy;

/**
 * ファジィ集合のスーパークラス。
 */
abstract public class FuzzySet {
    /**
     * メンバーシップ関数の値域 X を表す。X = [min,max] の開区間である。
     */
    public class Range{
        public final double min;
        public final double max;
        public Range(double min,double max){
            this.min=min;
            this.max=max;
        }
        public boolean isIn(double x){
            return min<=x&&x<=max;
        }
    }
    public class MembershipFunctionIllegalOutputException extends Exception{}

    protected Range range;
    private String description;
    public FuzzySet(Range range){
        this(range,"");
    }
    public FuzzySet(Range range,String description){
        this.range=range;
        setDescription(description);
    }

    public double invokeMembershipFunction(double x) throws MembershipFunctionIllegalOutputException {
        if(!range.isIn(x))throw new IllegalArgumentException();
        double res=membershipFunction(x);
        if(0<res||1<res)throw new MembershipFunctionIllegalOutputException();
        return res;
    }

    /**
     * メンバーシップ関数 μ は
     * μ : X -> [0,1] を要求する。
     * @param x∈X
     * @return ファジィ数
     */
    abstract protected double membershipFunction(double x);

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
