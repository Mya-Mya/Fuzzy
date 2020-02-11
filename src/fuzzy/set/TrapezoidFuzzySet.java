package fuzzy.set;

/**
 * メンバーシップ関数が台形のファジィ集合を表す。
 * rightTop = rightBottom の場合、rightTop = rightBottom -> 1 となる。
 * leftTop = leftBottom の場合、 leftTop = leftBottom -> 1 となる。
 */
public class TrapezoidFuzzySet extends FuzzySet {
    private double leftBottom;
    private double leftTilt=0;
    private double leftPiece=0;
    private double leftTop;
    private double rightTop;
    private double rightTilt=0;
    private double rightPiece=0;
    private double rightBottom;

    public TrapezoidFuzzySet(Range range,double leftBottom, double leftTop, double rightTop, double rightBottom){
        this(range,"",leftBottom,leftTop,rightTop,rightBottom);
    }

    public TrapezoidFuzzySet(Range range,String description,double leftBottom, double leftTop, double rightTop, double rightBottom){
        super(range,description);
        if(rightBottom<rightTop||rightTop<leftTop||leftTop<leftBottom)throw new IllegalArgumentException();
        this.leftBottom=leftBottom;

        double leftLength=leftTop-leftBottom;
        if(leftLength!=0){
            this.leftTilt=1/leftLength;
            this.leftPiece=-leftTop/leftLength+1;
        }

        this.leftTop=leftTop;

        double rightLength=rightBottom-rightTop;
        if(rightLength!=0){
            this.rightTilt=-1/rightLength;
            this.rightPiece=rightTop/rightLength+1;
        }

        this.rightTop=rightTop;
        this.rightBottom=rightBottom;
    }

    @Override
    protected double membershipFunction(double x) {
        if(x <leftBottom||rightBottom< x)return 0;
        if(leftTop<= x && x <=rightTop)return 1;
        if(x <leftTop){
            // value ∈ (leftBottom, leftTop)
            return leftTilt* x +leftPiece;
        }
        // value ∈ (rightTop, rightBottom)
        return rightTilt* x +rightPiece;
    }
}
