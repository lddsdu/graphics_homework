package cn.lddsdu.week2;

import com.sun.xml.internal.bind.v2.TODO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by jack on 18/3/20.
 */

class Task3Window extends Window{

    public Task3Window(int grid_size, int border_width, int x, int y, MouseAdapter adapter) {
        super(grid_size, border_width, x, y, adapter);
    }

    private Color polygoncolor = Color.GRAY;
    private Color border_color = Color.BLACK;
    private Color pitchon_color = Color.RED;

    private boolean rightClicked = false;
    private point firstpoint = null;
    point prepoint = null;
    private point lastpoint = null;
    private Queue<point> pointqueue = new ArrayBlockingQueue<point>(100);
    private point p1 = null;
    private point p2 = null;
    private point p3 = null,p4 = null;

    enum site{
        TOP,BOTTOM,LEFT,RIGHT;
    }

    void drawlinewithColor(point point1, point point2,Color color) {
        Color precolor = graphics.getColor();
        graphics.setColor(color);
        super.drawline(point1, point2);
        graphics.setColor(precolor);
    }

    void drawReact(point point1,point point2){
        int p1x,p2x,p1y,p2y;
        p1x = point1.x;p1y = point1.y;p2x = point2.x; p2y = point2.y;
        point point3 = new point(p1x,p2y);
        point point4 = new point(p2x,p1y);

        drawlinewithColor(point1,point4,border_color);
        drawlinewithColor(point1,point3,border_color);
        drawlinewithColor(point2,point4,border_color);
        drawlinewithColor(point2,point3,border_color);

    }

    //重新绘制截取到的形状
    void pitchon(){
        batch(site.TOP);
        batch(site.BOTTOM);
        batch(site.LEFT);
        batch(site.RIGHT);

        //最后重新绘制
        point pre = null;
        point first = null;
        int size = pointqueue.size();

        for (int i = 0 ; i < size; i++){
            point p = pointqueue.poll();
            System.out.println("final point "+p);
            if( i == 0){
                first = p;
                pre = p;
                continue;
            }
            drawlinewithColor(pre,p,pitchon_color);
            pre = p;
        }

        drawlinewithColor(first,pre,pitchon_color);

    }

    void batch(site site){
        int size = pointqueue.size();
        System.out.println(size);
        point ps = pointqueue.poll();
        System.out.println("poll "+ps);
        size --;
        point s = ps;
        point f = ps;
        point p = null;
        while(size > 0){
            p = pointqueue.poll();
            System.out.println("poll "+p);
            size --;
            dealwith(site,s,p);
            s = p;
        }
        p = f;
        dealwith(site,s,p);
        System.out.println("site = "+site+" end");
    }

    void dealwith(site site,point start,point end){
        if(site == Task3Window.site.TOP){
            //四种情况
            int y = p1.y;
            if(start.y > y){
                if(end.y > y){
                    //情况（1）
                    pointqueue.add(end);
                    System.out.println("<<< "+end);
                }else{
                    //情况（3）
                    point i = intersectionPoint_y(y,start,end);
                    pointqueue.add(i);
                    System.out.println("<<< "+i);
                }
            }else{
                if(end.y < y){

                }else {
                    point i = intersectionPoint_y(y,start,end);
                    pointqueue.add(i);
                    System.out.println("<<< "+i);
                    pointqueue.add(end);
                    System.out.println("<<< "+end);
                }
            }
        }else if (site == Task3Window.site.BOTTOM){
            int y =  p2.y;
            if(start.y < y){
                if(end.y < y) {
                    pointqueue.add(end);
                    System.out.println("<<< "+end);
                }else{
                    point i = intersectionPoint_y(y,start,end);
                    pointqueue.add(i);
                    System.out.println("<<< "+i);
                }
            }else{
                if(end.y > y){

                }else{
                    point i = intersectionPoint_y(y,start,end);
                    pointqueue.add(i);
                    System.out.println("<<< "+i);
                    pointqueue.add(end);
                    System.out.println("<<< "+end);
                }
            }
        }else if (site == Task3Window.site.LEFT){
            int x = p1.x;
            if(start.x > x){
                if(end.x > x){
                    pointqueue.add(end);
                    System.out.println("<<< "+end);
                }else{
                    point i = intersectionPoint_x(x,start,end);
                    pointqueue.add(i);
                    System.out.println("<<< "+i);
                }
            }else{
                if(end.x < x){

                }else{
                    point i = intersectionPoint_x(x,start,end);
                    pointqueue.add(i);
                    System.out.println("<<< "+i);
                    pointqueue.add(end);
                    System.out.println("<<< "+end);
                }
            }
        }else{//RIGTH
            int x = p2.x;
            if(start.x < x){
                if(end.x < x){
                    pointqueue.add(end);
                    System.out.println("<<< "+end);
                }else{
                    point i = intersectionPoint_x(x,start,end);
                    pointqueue.add(i);
                    System.out.println("<<< "+i);
                }
            }else{
                if(end.x > x){

                }else{
                    point i = intersectionPoint_x(x,start,end);
                    pointqueue.add(i);
                    System.out.println("<<< "+i);
                    pointqueue.add(end);
                    System.out.println("<<< "+end);
                }

            }
        }
    }


    //TODO:还没有添加判断从x轴扫描，y轴扫描的判断
    point intersectionPoint_y(int y,point start,point end){
        int x1 = start.x,x2 = end.x,y1 = start.y,y2 = end.y;
        double px = (y - y1) * (double)(x2 - x1) / (y2 - y1) + x1;
        return new point((int)(px+0.5),y);
    }

    point intersectionPoint_x(int x,point start,point end){
        int x1 = start.x,x2 = end.x,y1 = start.y,y2 = end.y;
        double py = (double)(y2-y1) / (x2 -x1) * ( x - x1) + y1;
        return new point(x,(int)(py+0.5));
    }

    @Override
    protected MouseAdapter getAdapter() {

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x=e.getX(),y = e.getY();
                point p = new point(border_width,grid_size,x,y);
                if(e.getButton() == MouseEvent.BUTTON3){
                    lastpoint = p;
                    rightClicked = true;
                    //连接两条线段
                    drawlinewithColor(prepoint,p,polygoncolor);
                    drawlinewithColor(p,firstpoint,polygoncolor);
                }else{
                    if(firstpoint == null){
                        firstpoint = p;
                    }else{
                        //连接线段
                        drawlinewithColor(p,prepoint,polygoncolor);
                    }
                }
                prepoint = p;
                System.out.println("click "+p);
                pointqueue.add(p);
            }

            //绘制矩形框的监听事件
            @Override
            public void mousePressed(MouseEvent e) {
                if(rightClicked) {
                    int x= e.getX(),y = e.getY();
                    point p = new point(border_width,grid_size,x,y);
                    p1 = p;
                    System.out.println("press");

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(rightClicked) {
                    rightClicked = false;
                    int x= e.getX(),y = e.getY();
                    point p = new point(border_width,grid_size,x,y);
                    p2 = p;
                    System.out.println("release");
                    drawReact(p1,p2);
                    pitchon();
                }
            }

        };
        return adapter;
    }
}

public class Task3 {
    public static void main(String[] args) throws InterruptedException {
        Window window;
        try {
            window = new Task3Window(5, 1, 80, 80, null);
            Thread.sleep(100);
            window.showWindow();
        }catch (Exception e){
            System.out.println("error");
        }
    }
}
