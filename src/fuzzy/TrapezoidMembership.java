package fuzzy;

/**
 * 台形のメンバーシップ関数を表す。
 * rightTop = rightBottom の場合、rightTop = rightBottom -> 1 となる。
 * leftTop = leftBottom の場合、 leftTop = leftBottom -> 1 となる。
 */
public class TrapezoidMembership extends Membership{
    private double leftBottom;
    private double leftTilt=0;
    private double leftPiece=0;
    private double leftTop;
    private double rightTop;
    private double rightTilt=0;
    private double rightPiece=0;
    private double rightBottom;

    public TrapezoidMembership(double leftBottom,double leftTop,double rightTop,double rightBottom){
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
    protected double getValue(double value) {
        if(value<leftBottom||rightBottom<value)return 0;
        if(leftTop<=value&&value<=rightTop)return 1;
        if(value<leftTop){
            // value ∈ (leftBottom, leftTop)
            return leftTilt*value+leftPiece;
        }
        // value ∈ (rightTop, rightBottom)
        return rightTilt*value+rightPiece;
    }
}
