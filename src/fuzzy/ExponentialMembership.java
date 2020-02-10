package fuzzy;

/**
 * 指数形のメンバーシップ関数を表す。
 */
public class ExponentialMembership extends Membership {
    private double center;
    private double minus_a;

    /**
     *
     * @param center 中心となるx座標
     * @param a>0 グラフの鋭さ
     */
    public ExponentialMembership(double center,double a) {
        this.center=center;
        if(a<=0)throw new IllegalArgumentException();
        this.minus_a=-a;
    }

    @Override
    protected double getValue(double value) {
        return Math.exp(minus_a*Math.abs(value-center));
    }
}
