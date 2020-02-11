package fuzzy;

/**
 * ファジィ集合のスーパークラス。
 */
abstract public class FuzzySet {
    /**
     * メンバーシップ関数の値域 X を表す。X = [min,max] の開区間である。
     */
    public static class Range{
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

    protected Range range;
    private String description;
    public FuzzySet(Range range){
        this(range,"");
    }
    public FuzzySet(Range range,String description){
        this.range=range;
        setDescription(description);
    }

    public double invokeMembershipFunction(double x){
        if(!range.isIn(x))throw new IllegalArgumentException();
        double res=membershipFunction(x);
        if(res<0){
            System.err.println("FuzzySet "+description+" : membershipFunction("+x+")の値が不正です。");
            return 0;
        }
        if(1<res){
            System.err.println("FuzzySet "+description+" : membershipFunction("+x+")の値が不正です。");
            return 1;
        }
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
