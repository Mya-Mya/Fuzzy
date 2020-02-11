package fuzzy;

/**
 * メンバーシップ関数が三角形のファジィ集合を表す。
 */
public class TriangleFuzzySet extends FuzzySet {
    private double leftBase;
    private double leftToCenterTilt=0;//左端の麓～中央の傾き
    private double leftToCenterPiece=0;//左端の麓～中央の切片
    private double center;
    private double centerToRightTilt=0;//中央～右端の麓の傾き
    private double centerToRightPiece=0;//中央～右端の麓の切片
    private double rightBase;
    /**
     * @param range 値域
     * @param leftBase 左端の麓のx座標
     * @param center 頂上のx座標
     * @param rightBase 右端の麓のx座標
     */
    public TriangleFuzzySet(Range range,double leftBase,double center,double rightBase){
        this(range,"",leftBase,center,rightBase);
    }
    /**
     * @param range 値域
     * @param description 説明
     * @param leftBase 左端の麓のx座標
     * @param center 頂上のx座標
     * @param rightBase 右端の麓のx座標
     */
    public TriangleFuzzySet(Range range,String description,double leftBase, double center, double rightBase){
        super(range,description);

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
    protected double membershipFunction(double x) {
        if(x <leftBase||rightBase< x)return 0;
        if(x <center){
            // value ∈ [左端の麓, 中央)
            return leftToCenterTilt* x +leftToCenterPiece;
        }
        // value ∈ [中央, 右端の麓]
        return centerToRightTilt* x +centerToRightPiece;
    }
}
