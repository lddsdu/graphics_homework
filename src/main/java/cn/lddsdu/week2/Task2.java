package cn.lddsdu.week2;

import sun.jvm.hotspot.interpreter.OffsetClosure;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by jack on 18/3/17.
 */

/*
填充算法  graphics没有准确找到点击点的坐标的像素颜色的方法  int[][]记录的方式来记录颜色信息
 */

class Task2Window extends Window{
    public Task2Window(int grid_size, int border_width, int x, int y, MouseAdapter adapter) {
        super(grid_size, border_width, x, y, adapter);
        for (int i = 0 ; i < x; i ++){
            for(int j = 0; j < y; j++){
                back_matrix[i][j] = Color.white;
            }
        }
    }

    @Override
    protected MouseAdapter getAdapter() {
        MouseAdapter superAdapter = super.getAdapter();
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //左键
                if (e.getButton() == MouseEvent.BUTTON1){
                    superAdapter.mouseClicked(e);
                }else{
                    //右键 －－ 染色
                    x = e.getX();
                    y = e.getY();
                    point p = new point(border_width,grid_size,x,y);
                    dye(p.x,p.y, Color.green);
                }
            }
        };
        return adapter;
    }

    @Override
    void paintGrid(int x, int y) {
        super.paintGrid(x, y);
        back_matrix[x][y] = Color.BLACK;
    }

    void paintGridWithColor(int x, int y , Color color){
        Color precolor = graphics.getColor();
        graphics.setColor(color);
        paintGrid(x,y);
        back_matrix[x][y] = color;
        graphics.setColor(precolor);
    }

    void dye(int x,int y,Color color){
        Color currentColor;
        try {
             currentColor = back_matrix[x][y];
        }catch (ArrayIndexOutOfBoundsException e){
            return;
        }

        if(x >= this.x || y >= this.y){
            return ;
        }

        if(!currentColor.equals(color) && !currentColor.equals(Color.black)){
            paintGridWithColor(x,y,color);
            dye(x+1,y,color);
            dye(x-1,y,color);
            dye(x,y+1,color);
            dye(x,y-1,color);
        }
    }
}

public class Task2{
    public static void main(String[] args) {
        Window window = new Task2Window(12,1,40,50,null);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.showWindow();
    }
}

