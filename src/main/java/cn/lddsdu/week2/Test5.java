package cn.lddsdu.week2;

import sun.tools.tree.DoubleExpression;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by jack on 18/4/8.
 */

class DoublePointer{
    double x;
    double y;

    public DoublePointer(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DoublePointer(coordinatePointer c){
        this(c.getX(),c.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

public class Test5 extends Test4{
    static final double minstand = 0.001;
    static final int width = 500;
    static final int height = 400;
    public static void main(String[] args) {
        Test5  test5 = new Test5();
        JFrame frame = new JFrame("beizer");
        frame.setSize(width,height);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setSize(500,400);
        panel.setBackground(test5.background_color);
        panel.setVisible(true);

        frame.add(panel);
        frame.setVisible(true);

        final Graphics2D g = (Graphics2D) panel.getGraphics();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        g.setColor(Color.GRAY);
        g.drawLine(0,height/2,width,height/2);
        g.setColor(Color.black);

        panel.addMouseListener(new MouseAdapter() {
            boolean isfirst = true;
            java.util.List<coordinatePointer> pointlist = new ArrayList<coordinatePointer>();
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                coordinatePointer p = new coordinatePointer(x,y);
                System.out.println(p);
                pointlist.add(p);
                g.drawString(pre + count++, x + 4, y - 4);
                //右键  MouseEvent.BUTTON3
                if(e.getButton() == MouseEvent.BUTTON3){
                    drawpoint_size(g,x,y,Color.red,4);
                    bezier(g,pointlist);
                    //查找最终的交点
                    List<DoublePointer> dl = new ArrayList<>();
                    for(int i = 0 ; i < pointlist.size(); i ++){
                        dl.add(new DoublePointer(pointlist.get(i)));
                    }
                    new Test5().intersection(g,dl);
                }else{ //左键
                    if(isfirst){
                        drawpoint_size(g,x,y,Color.red,4);
                        isfirst = false;
                    }else{
                        drawpoint_size(g,x ,y,Color.green,4);
                    }
                }
            }
        });
    }

    void intersection(Graphics2D g,java.util.List<DoublePointer> pointerList){
        if(oneside(pointerList)){
            return;
        }else{
            if(smallEnough(pointerList)){
                DoublePointer cp = pointerList.get(0);
                g.setColor(Color.red);
                g.drawOval((int) cp.getX()-3,(int) cp.getY()-3,6,6);
                return;
            }
            List<List<DoublePointer>> result = gettwo(pointerList);
            intersection(g,result.get(0));
            intersection(g,result.get(1));
        }
    }


    double average(double... arr){
        int size = arr.length;
        double sum = 0;
        for(double d : arr){
            sum += d;
        }
        return sum/size;
    }

    double square(double[] xarr,double[] yarr){
        double squaresum = 0;
        double averagex = average(xarr);
        double averagey = average(yarr);
        for(int i = 0; i < xarr.length; i++){
            squaresum+=Math.sqrt(xarr[i] - averagex) + Math.sqrt(yarr[i] - averagey);
        }
        return squaresum;
    }

    /**
     *
     * @return
     */
    boolean smallEnough(List<DoublePointer> list){
        int size = list.size();
        double[] xarr = new double[size];
        double[] yarr = new double[size];
        for(int i = 0; i < size; i ++){
            DoublePointer ci = list.get(i);
            xarr[i] = ci.getX();
            yarr[i] = ci.getY();
        }
        double squaresum = square(xarr,yarr);
        return squaresum < minstand;
    }

    /**
     * 将原来的划分两份
     * @return
     */
    java.util.List<List<DoublePointer>> gettwo(List<DoublePointer> origin){
        List<DoublePointer> first = new ArrayList<>();
        List<DoublePointer> second = new ArrayList<>();
        addelement(origin,first,second);
        List<List<DoublePointer>> result = new ArrayList<>();
        result.add(first);
        Collections.reverse(second);
        result.add(second);
        return result;
    }

    void addelement(List<DoublePointer> origin,List<DoublePointer> first,List<DoublePointer> second){
        if(origin.size() == 1){
            first.add(origin.get(0));
            second.add(origin.get(0));
            return;
        }
        List<DoublePointer> newOrigin = new ArrayList<>();
        int index = 1;
        while(index < origin.size()){
            DoublePointer c1 = origin.get(index - 1);
            DoublePointer c2 = origin.get(index);
            double x = c1.getX() + c2.getX();
            double y = c1.getY() + c2.getY();
            newOrigin.add(new DoublePointer(x/2,y/2));
            index ++;
        }
        first.add(origin.get(0));
        second.add(origin.get(origin.size() - 1));
        addelement(newOrigin,first,second);
    }


    boolean oneside(java.util.List<DoublePointer> pointerList){
        if(pointerList == null || pointerList.size() < 1){
            throw new RuntimeException("错误的坐标点个数");
        }
        boolean bottom = true;
        DoublePointer firstone = pointerList.get(0);
        //设置height/2以及以上的区域为上部分
        if(firstone.getY() <= (height/2)){
            bottom = false;
        }
        for(DoublePointer p : pointerList){
            if((p.getY() <= (height/2)) ^ bottom){
                continue;
            }
            return false;
        }
        return true;
    }
}
