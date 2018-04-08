package cn.lddsdu.week2;

/**
 * Created by jack on 18/4/8.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Root extends JFrame {

    private static final long serialVersionUID = 1L;
    private static JPanel contentPane;
    private static int pointnum = 0;
    private boolean flag = true,end = false;
    static ArrayList<Point> point = new ArrayList<Point>();
    static ArrayList<Point> tempPoint = new ArrayList<Point>();
    static ArrayList<Point> tempPoint2 = new ArrayList<Point>();
    static int width=0,height=0;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Root frame = new Root();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Root() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        setContentPane(contentPane);

        addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Graphics graphics=getGraphics();
                if(e.getButton() == MouseEvent.BUTTON1) {  //左键画折线
                    if(flag) {
                        point.add(new Point(e.getX(),e.getY()));
                        pointnum++;
                        System.out.println("点"+(pointnum)+":("+e.getX()+","+e.getY()+")");
                        flag = false;
                    }else{
                        point.add(new Point(e.getX(),e.getY()));
                        pointnum++;
                        System.out.println("点"+(pointnum)+":("+e.getX()+","+e.getY()+")");

                        graphics.drawLine((int)point.get(pointnum-2).x,(int)point.get(pointnum-2).y,
                                e.getX(),e.getY());
                    }
                    if(end) { //初始化，以便重新画线
                        repaint();
                        flag = true;
                        end = false;
                        pointnum = 0;
                        point.clear();
                    }
                }
                if(e.getButton() == MouseEvent.BUTTON3) {      //右键绘制曲线,割角算法
                    drawCurve(graphics); //绘制曲线
                    root(graphics,point); //求根

                    System.out.println("PointNum"+point.size());
                    System.out.println("绘制完成");
                    end=true;
                }
            }
        });
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics2d=(Graphics2D)g;
        width=getWidth();
        height=getHeight();
        graphics2d.drawLine(0, height/2, width, height/2); //绘制中间水平坐标轴
    }

    public static void drawCurve(Graphics graphics){ //绘制曲线
        double sx=point.get(0).getX(),sy=point.get(0).getY(),ex=0,ey=0,tx=0,ty=0;
        int n=pointnum-1;
        for(double t=0;t<=1;t+=0.1){
            tempPoint.clear();
            tempPoint.addAll(point); //把所有数据点赋值给tempPoint

            for(int i=0;i<n;i++){
                for(int j=0;j<tempPoint.size()-1;j++){
                    //计算分点坐标
                    tx=tempPoint.get(j).getX()*(1-t)+tempPoint.get(j+1).getX()*t;
                    ty=tempPoint.get(j).getY()*(1-t)+tempPoint.get(j+1).getY()*t;
                    tempPoint2.add(new Point((int)tx, (int)ty)); //把本次求得的分点，加入tempPoint2
                }
                tempPoint.clear();
                tempPoint.addAll(tempPoint2);
                tempPoint2.clear(); //清空保存求得的分点

            }

            ex=tempPoint.get(0).getX();
            ey=tempPoint.get(0).getY();
            graphics.setColor(Color.RED);
            graphics.drawLine((int)sx, (int)sy, (int)ex, (int)ey);
            sx=ex;sy=ey; //保存本次的点，作为下次画线的起始点
        }
    }

    public static void root(Graphics graphics,ArrayList<Point> p){
        if (!notSameSide(p)) {
            return;
        }else if (smallEnough(p)) {
            double sx=p.get(0).getX(),sy=p.get(0).getY(),
                    ex=p.get(p.size()-1).getX(),ey=p.get(p.size()-1).getY();
            double root_x=(ex-sx)/(ey-sy)*(height/2-sy)+sx;
            graphics.setColor(Color.BLUE);
            graphics.drawOval((int)root_x-4, (int)height/2-4, 8, 8);

            return;
        }

        double tx=0,ty=0;
        int n=p.size()-1;
        double t=0.5;

        ArrayList<Point> leftPoint = new ArrayList<Point>(); //左半部分折线点集
        ArrayList<Point> rightPoint = new ArrayList<Point>(); //右半部分折线点集

        leftPoint.add(p.get(0));
        rightPoint.add(p.get(p.size()-1));

        tempPoint.clear();
        tempPoint.addAll(p); //把所有数据点赋值给tempPoint
        tempPoint2.clear();
        for(int i=0;i<n;i++){
            for(int j=0;j<tempPoint.size()-1;j++){
                //计算分点坐标
                tx=tempPoint.get(j).getX()*(1-t)+tempPoint.get(j+1).getX()*t;
                ty=tempPoint.get(j).getY()*(1-t)+tempPoint.get(j+1).getY()*t;
                tempPoint2.add(new Point((int)tx, (int)ty)); //把本次求得的分点，加入tempPoint2
            }
            tempPoint.clear();
            tempPoint.addAll(tempPoint2);
            tempPoint2.clear(); //清空保存求得的分点
            leftPoint.add(tempPoint.get(0));
            rightPoint.add(tempPoint.get(tempPoint.size()-1));

        }
        root(graphics,leftPoint); //递归调用左右部分
        root(graphics,rightPoint);
    }

    public static boolean notSameSide(ArrayList<Point> p){
        boolean above=false,below=false,s=false;
        for(int i=0;i<p.size();i++){
            if (p.get(i).getY()<=height/2) {
                below=true;
            }else {
                above=true;
            }
        }
        s=below&&above;
        return s;
    }

    public static boolean smallEnough(ArrayList<Point> p){
        //判断折线首尾端点长度是否足够小
        double x2=Math.pow(p.get(0).getX()-p.get(p.size()-1).getX(), 2);
        double y2=Math.pow(p.get(0).getY()-p.get(p.size()-1).getY(), 2);
        if (Math.pow(x2+y2, 0.5)<5) {
            return true;
        }
        return false;
    }
}