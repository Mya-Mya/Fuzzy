package fuzzy.relationmodel;

import fuzzy.fuzzyinterface.FuzzyInterface1;

/**
 * 複数のファジィ制御則に対して同じファジィ関係モデルを使いたい場合、ファジィ関係モデルを比較したい時に便利なクラス。
 */
public class RelationModelFactory {
    public static final int GODEL_MODEL=0;
    public static final int LUKASIEWICZ_MODEL=1;
    public static final int MAMDAMI_MODEL=2;
    public static final int RESCHER_MODEL=3;
    public static final int ZBOOL_MODEL=4;

    private int usingModel;
    public RelationModelFactory(int usingModel){
        setUsingModel(usingModel);
    }
    public void setUsingModel(int usingModel){
        if(usingModel<0||4<usingModel)throw new IllegalArgumentException();
        this.usingModel = usingModel;
    }
    public RelationModel create(){
        switch (usingModel){
            case GODEL_MODEL:
                return new GodelModel();
            case LUKASIEWICZ_MODEL:
                return new LukasiewiczModel();
            case MAMDAMI_MODEL:
                return new MamdamiModel();
            case RESCHER_MODEL:
                return new RescherModel();
            case ZBOOL_MODEL:
                return new ZboolModel();
        }
        return null;
    }
    public int getPreferredCombinationModeBetweenRules(){
        if(usingModel==MAMDAMI_MODEL)return FuzzyInterface1.RULES_SUM_COMBINATION;
        return FuzzyInterface1.RULES_MULT_COMBINATION;
    }
}
