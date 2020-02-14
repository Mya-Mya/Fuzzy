package ex3;

import fuzzy.AntecedentThesis;
import fuzzy.fuzzyinterface.FuzzyInterface1;
import fuzzy.InputValue;
import fuzzy.Rule;
import fuzzy.relationmodel.RelationModelFactory;
import fuzzy.set.FuzzySet;
import fuzzy.set.TrapezoidFuzzySet;
import fuzzy.set.TriangleFuzzySet;

import java.util.ArrayList;
import java.util.List;

public class FuzzyWindowModel {
    public static final FuzzySet.Range TEMP_RANGE=new FuzzySet.Range(0,40);
    public static final FuzzySet.Range TIME_RANGE=new FuzzySet.Range(0,24);
    public static final FuzzySet.Range WINDOW_RANGE =new FuzzySet.Range(-10,10);

    private FuzzyInterface1 fuzzyInterface;
    private InputValue temp;
    private InputValue time;

    private IFuzzyWindowView view;

    public FuzzyWindowModel(IFuzzyWindowView view){
        this.view=view;
        view.setCurrentTemperatureText("気温未設定");
        view.setCurrentTimeText("時刻未設定");
        view.setCurrentWindowStatusText("窓未設定");

        /*
        現在の気温 temp ∈ TEMP_RANGE =[0,40] と現在の時刻 time ∈ TIME_RANGE =[0,24] から
        窓の開閉状況 window ∈ WINDOW_RANGE =[-10,10]をファジィ推論によって求める
        */
        temp=new InputValue("現在の気温",0);
        time=new InputValue("現在の時刻",0);
        /*
        温度に関するファジィ集合 {tempFuzzy j} j=1,..,5
        */
        FuzzySet isSoCold=new TriangleFuzzySet(TEMP_RANGE,"とても寒い",0,0,10);
        FuzzySet isCold=new TriangleFuzzySet(TEMP_RANGE,"寒い",0,10,20);
        FuzzySet isMidium=new TriangleFuzzySet(TEMP_RANGE,"丁度良い",10,20,30);
        FuzzySet isHot=new TriangleFuzzySet(TEMP_RANGE,"暑い",20,30,40);
        FuzzySet isSoHot=new TriangleFuzzySet(TEMP_RANGE,"とても暑い",30,40,40);
        /*
        時刻に関するファジィ集合
        */
        FuzzySet isAtNight1=new TrapezoidFuzzySet(TIME_RANGE,"夜中1",0,0,5,8);
        FuzzySet isAtNoon=new TrapezoidFuzzySet(TIME_RANGE,"日中",5,8,16,19);
        FuzzySet isAtNight2=new TrapezoidFuzzySet(TIME_RANGE,"夜中2",16,19,24,24);
        /*
        窓の開閉に関するファジィ集合
        */
        FuzzySet wannaCloseVeryMuch=new TriangleFuzzySet(WINDOW_RANGE,"とても閉めたい",-10,-10,0);
        FuzzySet wannaClose=new TriangleFuzzySet(WINDOW_RANGE,"閉めたい",-10,-5,0);
        FuzzySet wannaNothing=new TriangleFuzzySet(WINDOW_RANGE,"そのままでよい",-5,0,5);
        FuzzySet wannaOpen=new TriangleFuzzySet(WINDOW_RANGE,"開けたい",0,5,10);
        FuzzySet wannaOpenVeryMuch=new TriangleFuzzySet(WINDOW_RANGE,"とても開けたい",0,10,10);
        /*
        ファジィ制御則 {R i} i=1,..,8
        */
        //使用するファジィ関係モデル
        RelationModelFactory relationModel=new RelationModelFactory(RelationModelFactory.MAMDAMI_MODEL);
        //ファジィ制御則
        Rule R1=new Rule(new AntecedentThesis(temp,isSoCold),wannaCloseVeryMuch,relationModel.create());
        Rule R2=new Rule(new AntecedentThesis(temp,isCold),wannaClose,relationModel.create());
        Rule R3=new Rule(new AntecedentThesis(temp,isMidium),wannaNothing,relationModel.create());
        Rule R4=new Rule(new AntecedentThesis(temp,isHot),wannaOpen,relationModel.create());
        Rule R5=new Rule(new AntecedentThesis(temp,isSoHot),wannaOpenVeryMuch,relationModel.create());
        Rule R6=new Rule(new AntecedentThesis(time,isAtNight1),wannaClose,relationModel.create());
        Rule R7=new Rule(new AntecedentThesis(time,isAtNoon),wannaNothing,relationModel.create());
        Rule R8=new Rule(new AntecedentThesis(time,isAtNight2),wannaClose,relationModel.create());
        /*
        ファジィ推論
        */
        //推論された関数 μB*(y) の積分の用途で用いる
        List<Double>yValues=new ArrayList<>();
        for(double y=WINDOW_RANGE.min;y<=WINDOW_RANGE.max;y+=0.2)yValues.add(y);
        //ファジィ推論
        fuzzyInterface=new FuzzyInterface1(yValues,relationModel.getPreferredCombinationModeBetweenRules());
        fuzzyInterface.addRule(R1);
        fuzzyInterface.addRule(R2);
        fuzzyInterface.addRule(R3);
        fuzzyInterface.addRule(R4);
        fuzzyInterface.addRule(R5);
        fuzzyInterface.addRule(R6);
        fuzzyInterface.addRule(R7);
        fuzzyInterface.addRule(R8);
    }

    public void setTemperature(double t){
        view.setCurrentTemperatureText(String.format("%.1f",t));
        temp.setValue(t);
        updateWindowState();
    }
    public void setTime(double t){
        view.setCurrentTimeText(String.format("%.1f",t));
        time.setValue(t);
        updateWindowState();
    }

    private void updateWindowState(){
        double windowState=0;
        try {
            fuzzyInterface.inputValueChanged();
        } catch (Rule.NoAntecedentPartListException e) {
            e.printStackTrace();
        }
        try {
            windowState=fuzzyInterface.getConsequent();
        } catch (FuzzyInterface1.NoRuleException e) {
            e.printStackTrace();
            view.setCurrentWindowStatusText("エラー発生");
        } catch (Rule.NoAntecedentPartListException e) {
            e.printStackTrace();
        }
        view.setCurrentWindowStatusText(String.format("%.2f",windowState));
    }
}
