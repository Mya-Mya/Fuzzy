package fuzzy;

/**
 * 三角形のメンバーシップ関数を表す。
 */
public class TriangleMambership extends Membership{
    private double leftBase;
    private double leftToCenterTilt=0;//左端の麓～中央の傾き
    private double leftToCenterPiece=0;//左端の麓～中央の切片
    private double center;
    private double centerToRightTilt=0;//中央～右端の麓の傾き
    private double centerToRightPiece=0;//中央～右端の麓の切片
    private double rightBase;
    /**
     *
     * @param leftBase 左端の麓のx座標
     * @param center 頂上のx座標
     * @param rightBase 右端の麓のx座標
     */
    public TriangleMambership(double leftBase,double center,double rightBase){
        this.leftBase=leftBase;
        this.center=center;
        this.rightBase=rightBase;

        double leftLength=center-leftBase;
        if(leftLength!=0){
            this.leftToCenterTilt=1/leftLength;
            this.leftToCenterPiece=-center/leftLength+1;
        }
        double rightLength=rightBase-center;
        if(rightLength!=0){
            this.centerToRightTilt=-1/rightLength;
            this.centerToRightPiece=center/rightLength+1;
        }
    }
    @Override
    protected double getValue(double value) {
        if(value<leftBase||rightBase<value)return 0;
        if(value<center){
            // value ∈ [左端の麓, 中央)
            return leftToCenterTilt*value+leftToCenterPiece;
        }
        // value ∈ [中央, 右端の麓]
        return centerToRightTilt*value+centerToRightPiece;
    }
}
