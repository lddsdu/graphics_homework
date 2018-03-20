package cn.lddsdu.week2;

import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Created by jack on 18/3/17.
 */

class Task1Window extends Window {

    private point frame_point1;
    private point frame_point2;


    public Task1Window(int grid_size, int border_width, int x, int y, MouseAdapter adapter) {
        super(grid_size, border_width, x, y, adapter);
    }


    boolean innerframe(point p){
        if(p.x <= frame_point1.x || p.x >= frame_point2.x ){
            return false;
        }
        if(p.y <= frame_point1.y || p.y >= frame_point2.y ){
            return false;
        }
        return true;
    }


    @Override
    public void showWindow() {
        super.showWindow();
        int binx = this.x / 2;
        int biny = this.y / 2;
        int quax = binx / 2;
        int quay = biny / 2;
        point point1 = new point(quax,quay);
        point point2 = new point(quax + binx,quay + biny);

        frame_point1 = point1;
        frame_point2 = point2;

        System.out.println(">>>");
        drawReact(point1,point2);
    }

    @Override
    protected MouseAdapter getAdapter() {
        return super.getAdapter();
    }

    protected void drawReact(point point1,point point2){
        int x1= point1.x,y1 = point1.y,x2 = point2.x,y2 = point2.y;
        point point3 = new point(x1,y2);
        point point4 = new point(x2,y1);
        graphics.setColor(Color.GRAY);
        drawline(point1,point3);
        drawline(point1,point4);
        drawline(point2,point3);
        drawline(point2,point4);
        graphics.setColor(Color.BLACK);
    }

    @Override
    void paintGrid(int x, int y) {
        point p = new point(x,y);
        if(! innerframe(p)) {
            graphics.setColor(Color.GRAY);
        }
        super.paintGrid(x, y);
        graphics.setColor(Color.black);
    }
}

public class Task1 {
    public static void main(String[] args) throws InterruptedException {
        Window window = new Task1Window(10,2,40,40,null);
        Thread.sleep(100);
        window.showWindow();
    }
}
