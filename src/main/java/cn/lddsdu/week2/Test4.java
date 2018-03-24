package cn.lddsdu.week2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * Created by jack on 18/3/24.
 *
 * 绘制bezier曲线
 *
 * 目前绘制出来的曲线存在锯齿
 */

class coordinatePointer{
    private int x;
    private int y;

    public coordinatePointer(int x, int y) {
        if(x < 0 || x > 500 || y < 0 || y > 500){
            throw new RuntimeException("illegal coordinate");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "coordinatePointer{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

public class Test4 {
    static Color background_color = Color.white;
    static Color line_color = Color.black;
    static String pre = "p";
    static int count = 0;
    /**
     * 带绘制的点大小参数的绘制选项
     * @param g
     * @param x
     * @param y
     * @param color
     * @param size
     */
    static void drawpoint_size(Graphics2D g,int x,int y,Color color,int size){
        Color c = g.getColor();
        g.setColor(color);
        g.drawOval(x,y,size,size);
        g.setColor(c);
    }

    static void drawpoint(Graphics2D g,int x,int y,Color color){
        Color c = g.getColor();
        if(c == color){
            g.drawLine(x,y,x,y);
            return ;
        }
        g.setColor(color);
        g.drawLine(x,y,x,y);
        g.setColor(c);
    }

    static void drawpoint(Graphics2D g,int x,int y){
        g.drawLine(x,y,x,y);
    }

    static  void drawpoint(Graphics2D g,coordinatePointer p){
        int x = p.getX();
        int y = p.getY();
        g.drawLine(x,y,x,y);
    }

    public static void main(String[] args) {
        Test4 t = new Test4();
        JFrame frame = new JFrame("beizer曲线");
        frame.setSize(500,500);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setSize(500,500);
        panel.setBackground(t.background_color);
        panel.setVisible(true);

        frame.add(panel);
        frame.setVisible(true);

        final Graphics2D g = (Graphics2D) panel.getGraphics();

//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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

    private static void drawline(Graphics2D g,coordinatePointer s,coordinatePointer e,Color color){
        Color c = g.getColor();
        if(color == c){
            g.drawLine(s.getX(),s.getY(),e.getX(),e.getY());
            return;
        }
        g.setColor(color);
        g.drawLine(s.getX(),s.getY(),e.getX(),e.getY());
        g.setColor(c);
    }

    private static void bezier(Graphics2D g, java.util.List<coordinatePointer> pointlist) {
        java.util.List<coordinatePointer> lists = new ArrayList<>();
        for(int i = 0 ; i < 100 ; i++){
            lists.add(getpoint(pointlist,i * 0.01));
        }
        lists.add(pointlist.get(pointlist.size()-1));
        for(int i = 0; i< 100; i++){
            g.setStroke(new BasicStroke(2));
            drawline(g,lists.get(i),lists.get(i+1),Color.BLACK);
        }
    }

    private static coordinatePointer getpoint(java.util.List<coordinatePointer> pointerList,double t){
        if(pointerList.size() <= 0){
            throw new RuntimeException("pointerlist size cannot be <= 0");
        }
        if(pointerList.size() == 1){
            return pointerList.get(0);
        }
        java.util.List<coordinatePointer> newpointerList = new ArrayList<>();
        for(int i = 0; i < pointerList.size() - 1; i++){
            newpointerList.add(gettpoint(pointerList.get(i),pointerList.get(i+1),t));
        }
        return getpoint(newpointerList,t);
    }

    /**
     * 获取到比例为t的点
     * @param s
     * @param e
     * @param t
     * @return
     */
    private static coordinatePointer gettpoint(coordinatePointer s,coordinatePointer e,double t){
        int sx = s.getX(),sy = s.getY(),ex = e.getX(),ey = e.getY();
        int nx = sx + (int)((ex - sx)*t + 0.5);
        int ny = sy + (int)((ey - sy)*t + 0.5);
        coordinatePointer cp = new coordinatePointer(nx,ny);
        System.out.println(cp);
        return cp;
    }
}
