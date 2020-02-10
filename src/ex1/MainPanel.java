package ex1;

import fuzzy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Member;
import java.util.ArrayList;

@Deprecated
public class MainPanel extends JPanel implements ActionListener, MouseListener {
    private Timer timer;
    private Point agentPosition=new Point(100,100);
    private Point poisonPosition=new Point(300,300);

    private FuzzyInterface1 Rx;
    private FuzzyInterface1 Ry;
    private InputValue dx1=new InputValue(0);
    private InputValue dx2=new InputValue(0);
    private InputValue dy1=new InputValue(0);
    private InputValue dy2=new InputValue(0);

    public MainPanel(){
        super();
        addMouseListener(this);

        /**
         * Rx: エージェントのx方向の行動 X ∈ [-20,20] について
         * Rx1: エージェントの Ax1: すぐ右に毒があったら(dx1) Bx1: 左に急いで移動する
         * Rx2: エージェントの Ax2: すぐ左に毒があったら(dx1) Bx2: 右に急いで移動する
         * Rx3: エージェントの Ax3: 右にマウスがあったら(dx2) Bx3: 右に移動する
         * Rx4: エージェントの Ax4: 左にマウスがあったら(dx2) Bx4: 左に移動する
         */
        dx1.setDescription("エージェントと毒とのx方向の位置関係");
        dx2.setDescription("エージェントとマウスとのx方向の位置関係");
        Membership Ax1=new TriangleMambership(0,30,60);
        Ax1.setDescription("すぐ右に毒がある");
        Membership Bx1=new TriangleMambership(-20,-15,-9);
        Bx1.setDescription("左に急いで移動する");
        Rule1a Rx1=new Rule1a(Bx1);
        Rx1.addAntecedentPart(new AntecedentPart(dx1,Ax1));

        Membership Ax2=new TriangleMambership(-60,30,0);
        Ax2.setDescription("すぐ左に毒がある");
        Membership Bx2=new TriangleMambership(9,15,20);
        Bx2.setDescription("右に急いで移動する");
        Rule1a Rx2=new Rule1a(Bx2);
        Rx2.addAntecedentPart(new AntecedentPart(dx1,Ax2));

        Membership Ax3=new TriangleMambership(-1,150,300);
        Ax3.setDescription("右にマウスがある");
        Membership Bx3=new TriangleMambership(4,10,14);
        Bx3.setDescription("右に移動する");
        Rule1a Rx3=new Rule1a(Bx3);
        Rx3.addAntecedentPart(new AntecedentPart(dx2,Ax3));

        Membership Ax4=new TriangleMambership(-300,-150,1);
        Ax4.setDescription("左にマウスがある");
        Membership Bx4=new TriangleMambership(-14,-10,4);
        Bx4.setDescription("左に移動する");
        Rule1a Rx4=new Rule1a(Bx4);
        Rx4.addAntecedentPart(new AntecedentPart(dx2,Ax4));

        java.util.List<Double>X=new ArrayList<>();
        for(double x=-20;x<=20;x+=0.5)X.add(x);
        Rx=new FuzzyInterface1(X);
        Rx.addRule(Rx1);
        Rx.addRule(Rx2);
        Rx.addRule(Rx3);
        Rx.addRule(Rx4);
        System.out.println(Rx.toString());

        /**
         * Ry: エージェントのy方向の行動 Y ∈ [-3,3] について
         * Ry1: エージェントの Ay1: すぐ下に毒があったら(dy1) By1: 上に急いで移動する
         * Ry2: エージェントの Ay2: すぐ上に毒があったら(dy1) By2: 下に急いで移動する
         * Ry3: エージェントの Ay3: 下にマウスがあったら(dy2) By3: 下に移動する
         * Ry4: エージェントの Ay4: 上にマウスがあったら(dy2) By4: 上に移動する
         */
        dy1.setDescription("エージェントと毒とのy方向の位置関係");
        dy2.setDescription("エージェントとマウスとのy方向の位置関係");

        Membership Ay1=new TriangleMambership(0,30,60);
        Ay1.setDescription("すぐ下に毒がある");
        Membership By1=new TriangleMambership(-20,-15,-9);
        By1.setDescription("上に急いで移動する");
        Rule1a Ry1=new Rule1a(By1);
        Ry1.addAntecedentPart(new AntecedentPart(dy1,Ay1));

        Membership Ay2=new TriangleMambership(-60,30,0);
        Ay2.setDescription("すぐ上に毒がある");
        Membership By2=new TriangleMambership(9,15,20);
        By2.setDescription("下に急いで移動する");
        Rule1a Ry2=new Rule1a(By2);
        Ry2.addAntecedentPart(new AntecedentPart(dy1,Ay2));

        Membership Ay3=new TriangleMambership(-1,150,300);
        Ay3.setDescription("下にマウスがある");
        Membership By3=new TriangleMambership(4,10,14);
        By3.setDescription("下に移動する");
        Rule1a Ry3=new Rule1a(By3);
        Ry3.addAntecedentPart(new AntecedentPart(dy2,Ay3));

        Membership Ay4=new TriangleMambership(-300,-150,1);
        Ay4.setDescription("上にマウスがある");
        Membership By4=new TriangleMambership(-14,-10,4);
        By4.setDescription("上に移動する");
        Rule1a Ry4=new Rule1a(By4);
        Ry4.addAntecedentPart(new AntecedentPart(dy2,Ay4));

        java.util.List<Double>Y=new ArrayList<>();
        for(double y=-20;y<=20;y+=0.5)Y.add(y);
        Ry=new FuzzyInterface1(Y);
        //Ry.addRule(Ry1);
        //Ry.addRule(Ry2);
        Ry.addRule(Ry3);
        Ry.addRule(Ry4);
        System.out.println(Ry.toString());

        timer=new Timer(100,this);
        timer.setActionCommand("TIMER");
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.white);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.black);
        g.fillOval(agentPosition.x-5,agentPosition.y-5,10,10);
        g.setColor(Color.red);
        g.fillRect(poisonPosition.x-7,poisonPosition.y-7,14,14);
        if(pointerInfo==null)return;
        g.setColor(Color.green);
        g.fillRect(pointerInfo.getLocation().x-7,pointerInfo.getLocation().y-7,14,14);
    }

    private PointerInfo pointerInfo;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String acco=actionEvent.getActionCommand();
        if(acco.equals(timer.getActionCommand())){
            pointerInfo=MouseInfo.getPointerInfo();

            //System.out.println(pointerInfo.getLocation().x);

            //入力値の更新
            dx1.setValue(poisonPosition.x-agentPosition.x);
            dx2.setValue(pointerInfo.getLocation().x-agentPosition.x);
            dy1.setValue(poisonPosition.y-agentPosition.y);
            dy2.setValue(pointerInfo.getLocation().y-agentPosition.y);
            try {
                Rx.inputValueChanged();
                Ry.inputValueChanged();
            } catch (Rule1a.NoAntecedentPartListException e) {
                e.printStackTrace();
            }

            //エージェントの位置更新
            try {
                agentPosition.x+=Math.ceil(Rx.getConsequent());
                agentPosition.y+=Math.ceil(Ry.getConsequent());
            } catch (FuzzyInterface1.NoRuleException e) {
                e.printStackTrace();
            } catch (Rule1a.NoAntecedentPartListException e) {
                e.printStackTrace();
            }

            //エージェントの位置の修正
            if(agentPosition.x<0)agentPosition.x=0;
            if(getWidth()<agentPosition.x)agentPosition.x=getWidth();
            if(agentPosition.y<0)agentPosition.y=0;
            if(getHeight()<agentPosition.y)agentPosition.y=getHeight();

            repaint();
        }
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        poisonPosition=pointerInfo.getLocation();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
