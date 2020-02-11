package fuzzy;

/**
 * メンバーシップ関数が指数形のファジィ集合を表す。
 */
public class ExponentialFuzzySet extends FuzzySet {
    private double center;
    private double minus_a;
    /**
     * @param range 値域
     * @param center 中心となるx座標
     * @param a>0 グラフの鋭さ
     */
    public ExponentialFuzzySet(Range range,double center,double a){
        this(range,"",center,a);
    }
    /**
     * @param range 値域
     * @param description 説明
     * @param center 中心となるx座標
     * @param a>0 グラフの鋭さ
     */
    public ExponentialFuzzySet(Range range,String description,double center, double a) {
        super(range,description);
        this.center=center;
        if(a<=0)throw new IllegalArgumentException();
        this.minus_a=-a;
    }

    @Override
    protected double membershipFunction(double x) {
        return Math.exp(minus_a*Math.abs(x -center));
    }
}
