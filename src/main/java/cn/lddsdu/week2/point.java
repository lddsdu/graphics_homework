package cn.lddsdu.week2;

/**
 * Created by jack on 18/3/19.
 */

class point{
    public int x,y;

    private int border_width;
    private int grid_size;
    private int codx,cody;

    public point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public point(int border_width, int grid_size, int codx, int cody) {
        this.border_width = border_width;
        this.grid_size = grid_size;
        this.codx = codx;
        this.cody = cody;

        //根据上面信息计算x,y
        int scale = grid_size + border_width;

        x = codx / scale;
        y = cody / scale;
    }

    @Override
    public String toString() {
        return "point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
