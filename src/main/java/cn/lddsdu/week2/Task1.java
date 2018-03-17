package cn.lddsdu.week2;

/**
 * Created by jack on 18/3/17.
 */

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Task1 {
    public static void main(String[] args){
        JFrame frame = new JFrame("drawline ");
        frame.setLayout(new BorderLayout());
        frame.setSize(200,200);
        frame.setVisible(true);
        final JLabel lb = new JLabel("显示当前的鼠标右键点击后的坐标");
        lb.setVisible(true);
        frame.add(lb);
        Panel panel = new Panel();
        panel.setSize(200,200);
        panel.setBackground(Color.white);
        panel.setVisible(true);
        frame.add(panel);
        final Graphics2D g  = (Graphics2D) panel.getGraphics();
        g.setColor(Color.black);
        panel.addMouseListener(new MouseListener() {
            int prepixelx = 0;
            int prepixely = 0;
            boolean doubleclick = true;
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if(doubleclick){
                //首次点击
                    prepixelx = x;
                    prepixely = y;
                    g.drawLine(x,y,x,y);
                    doubleclick = false;
                }else{
                    int dx,dy;
                    dx = prepixelx > x ? (prepixelx -x):(x-prepixelx);
                    dy = prepixely > y ? (prepixely -y):(y - prepixely);
                    if(dx >= dy) {
                        ddaline(g, prepixelx, prepixely, x, y);
                    }else{
                        ddaline2(g, prepixelx, prepixely, x, y);
                    }
                   doubleclick = true;
                }
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }


            //从x轴递增的方式来绘制图像
            void ddaline(Graphics2D g,int x0,int y0, int x1,int y1){
                int temp;
                if(x0 > x1){
                    temp = x0;
                    x0 = x1;
                    x1 = temp;
                    temp = y0;
                    y0 = y1;
                    y1 = temp;
                }
                int x;
                float dx,dy,y,k;

                dx = (float)x1 - x0;
                dy = (float)y1 - y0;
                k = dy / dx;
                y = y0;
                for(x = x0; x <= x1; x++){
                    g.drawLine(x,(int)(y+0.5),x,(int)(y+0.5));
                    y = y + k;
                }
            }

            void ddaline2(Graphics2D g,int x0,int y0,int x1,int y1){
                int temp;
                if(y0 > y1){
                    temp = y0;
                    y0 = y1;
                    y1 = temp;
                    temp = x0;
                    x1 = x0;
                    x0 = temp;
                }
                int y ;
                float dx,dy,x,k2;
                dx = (float)x1 - x0;
                dy = (float)y1 - y0;
                k2 = dx/dy;
                x = x0;
                for(y = y0; y <= y1; y++){
                    g.drawLine((int)(x+0.5),y,(int)(x+0.5),y);
                    x = x + k2;
                }
            }
        });
    }


}
