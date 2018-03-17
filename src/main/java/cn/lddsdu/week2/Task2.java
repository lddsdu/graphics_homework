package cn.lddsdu.week2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;

/**
 * Created by jack on 18/3/17.
 */

/*
填充算法
 */

public class Task2{
    static final int WIDTH = 300;
    static final int HEIGHT = 300;
    public static void main(String[] args) throws AWTException {
        JFrame frame = new JFrame("fill");
        frame.setSize(300,300);
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        final Robot robot = new Robot();
        panel.setSize(WIDTH,HEIGHT);
        panel.setBackground(Color.white);
        panel.setVisible(true);
        frame.add(panel);
        frame.setVisible(true);
        final Graphics2D g = (Graphics2D) panel.getGraphics();
        g.setColor(Color.black);
        MouseAdapter ma = new MouseAdapter() {
            boolean beginFill = false;

            void boundaryFill(int x,int y){
                if(x <0 || x >= WIDTH || y < 0 || y >= HEIGHT){
                    System.out.println("fetch the boundary");
                    return ;
                }
                Color color =new Color(2,2,2);//怎么获取到panel中的像素的颜色??????
                //这里的x,y是在屏幕上的绝对坐标
                color = robot.getPixelColor(x,y);
                System.out.println(x+","+y+" "+color.toString());
                if(!color.equals(Color.black)){
                    g.drawLine(x,y,x,y);
                    boundaryFill(x+1,y);
                    boundaryFill(x-1,y);
                    boundaryFill(x,y+1);
                    boundaryFill(x,y-1);
                }

            }

            int x1,y1;
            @Override
            public void mousePressed(MouseEvent e) {
                if( !beginFill) {
                    x1 = e.getX();
                    y1 = e.getY();
                    g.drawLine(x1, y1, x1, y1);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(!beginFill) {
                    System.out.println("release");
                    int x2 = e.getX();
                    int y2 = e.getY();
                    g.drawLine(x1, y1, x2, y2);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                //右键填充颜色
                if (e.getButton() == MouseEvent.BUTTON3){
                    beginFill = true;
                    //开始填充颜色
                    boundaryFill(e.getXOnScreen(),e.getYOnScreen());
                }
            }

            public void mouseDragged(MouseEvent e) {
                System.out.println("dragged");
                int x2 = e.getX();
                int y2 = e.getY();
                g.drawLine(x1,y1,x2,y2);
            }
        };

        panel.addMouseListener(ma);

    }
}

