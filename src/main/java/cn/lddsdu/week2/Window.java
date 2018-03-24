package cn.lddsdu.week2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jack on 18/3/19.
 */

public class Window{
    protected JFrame jFrame = new JFrame("作业一");
    protected JPanel panel;
    protected Graphics2D graphics;
    protected Color background_color =  Color.white;
    protected Color border_color = Color.black;
    protected int grid_size,border_width,x,y,width,height;
    protected Color[][] back_matrix;
    public Lock lock = new ReentrantLock();
    Condition window_ready = lock.newCondition();
    protected MouseAdapter adapter = null;

    int click_num = 0;
    point prepoint = null;

    public Window(int grid_size,int border_width,int x,int y,MouseAdapter adapter) {

        this.grid_size = grid_size;
        this.border_width = border_width;
        this.x = x;
        this.y = y;
        back_matrix = new Color[x][y];
        width = x * (grid_size+border_width)+border_width;
        height = y* (grid_size + border_width)+border_width;
        jFrame.setSize(width,height);
        jFrame.setLayout(new BorderLayout());
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        panel = new JPanel();
        panel.setSize(width,height);
        panel.setVisible(true);
        jFrame.add(panel);
        graphics = (Graphics2D) panel.getGraphics();
        graphics.setColor(border_color);
//        graphics.setBackground(background_color);
        adapter = getAdapter();
        panel.addMouseListener(adapter);
    }


    //这里的获取到MouseAdapter的方法可以定制
    protected MouseAdapter getAdapter() {
        if(adapter == null) {
            adapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX(), y = e.getY();
                    point p = new point(border_width, grid_size, x, y);
                    System.out.println("click " + p);
                    paintGrid(p.x, p.y);
                    click_num++;
                    if (click_num % 2 == 0) {
                        drawline(prepoint, p);
                    }
                    prepoint = p;
                }
            };
        }
        return adapter;
    }

    /**
     * 填充单元格
     * @param x
     * @param y
     */
    void paintGrid(int x,int y){
        int codx,cody;
        int scale = this.grid_size + this.border_width;
        codx = scale * x;
        cody = scale * y;
        for(int xx = codx; xx <= codx + grid_size + border_width; xx++){
            graphics.drawLine(xx,cody,xx,cody+grid_size+border_width);
        }
    }

    /**
     * 绘制端点之间的线段
     * @param point1
     * @param point2
     */
    void drawline(point point1,point point2){
        int width = point1.x - point2.x;
        int height = point1.y - point2.y;
        boolean useXaxis = Math.abs(width) > Math.abs(height);
        if(useXaxis){
            ddaline(point1.x,point1.y,point2.x,point2.y);
        }else{
            ddaline2(point1.x,point1.y,point2.x,point2.y);
        }
    }

    //从x轴递增的方式来绘制图像
    void ddaline(int x0,int y0, int x1,int y1){
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
            paintGrid(x,(int)(y+0.5));
            y = y + k;
        }
    }

    //从y轴开始扫描
    void ddaline2(int x0,int y0,int x1,int y1){
        int temp;
        if(y0 > y1){
            temp = y0;
            y0 = y1;
            y1 = temp;
            temp = x0;
            x0 = x1;
            x1 = temp;
        }
        int y ;
        float dx,dy,x,k2;
        dx = (float)x1 - x0;
        dy = (float)y1 - y0;
        k2 = dx/dy;
        x = x0;
        for(y = y0; y <= y1; y++){
            paintGrid((int)(x+0.5),y);
            x = x + k2;
        }
    }



    //绘制窗口
    public void showWindow(){
        //首先画纵向
        for(int x = 0; x < width ; x+= (grid_size+border_width)){
            for(int i = 0 ; i < border_width; i++) {
                graphics.drawLine(x,0,x,height);
            }
        }
        //绘制横向的线段
        for(int y = 0; y < height ; y +=(grid_size + border_width)){
            for(int i = 0 ; i < border_width; i++){
                graphics.drawLine(0,y,width,y);
            }
        }
    }

}
