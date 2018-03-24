package cn.lddsdu.week2;

/**
 * Created by jack on 18/3/24.
 */

        import java.awt.BasicStroke;
        import java.awt.Color;
        import java.awt.EventQueue;
        import java.awt.Graphics;
        import java.awt.Graphics2D;
        import java.awt.Point;
        import java.awt.event.MouseEvent;
        import java.awt.event.MouseListener;
        import java.util.ArrayList;

        import javax.swing.JFrame;
        import javax.swing.JPanel;
        import javax.swing.border.EmptyBorder;

public class Bezier extends JFrame {

    private static final long serialVersionUID = 2L;
    private JPanel contentPane;
    private static int pointnum = 0;
    private boolean flag = true,end = false;
    static ArrayList<Point> point = new ArrayList<Point>();
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Bezier frame = new Bezier();
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
    public Bezier() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

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
                ((Graphics2D) graphics).setStroke(new BasicStroke(2));
                graphics.setColor(Color.BLACK);
                if(e.getButton() == MouseEvent.BUTTON1) {  //左键画折线
                    if(flag) {  //第一次鼠标点击
                        point.add(new Point(e.getX(),e.getY()));
                        pointnum++;
                        System.out.println("点"+(pointnum)+":("+e.getX()+","+e.getY()+")");
                        flag = false;
                    }else{ //第一次之后鼠标点击
                        point.add(new Point(e.getX(),e.getY()));
                        pointnum++;
                        System.out.println("点"+(pointnum)+":("+e.getX()+","+e.getY()+")");
                        //绘制折线
                        graphics.drawLine((int)point.get(pointnum-2).x,(int)point.get(pointnum-2).y,
                                e.getX(),e.getY());

                    }
                    if(end) {
                        repaint();
                        flag = true;
                        end = false;
                        pointnum = 0;
                        point.clear();
                    }
                }
                if(e.getButton() == MouseEvent.BUTTON3) {      //右键绘制曲线
                    graphics.setColor(Color.RED);
                    drawCurve(graphics); //绘制曲线

                    System.out.println("PointNum"+point.size());
                    System.out.println("绘制完成");
                    end=true;
                }
            }
        });
    }

    public static void drawCurve(Graphics graphics){
        double sx=point.get(0).getX(),sy=point.get(0).getY(),ex=0,ey=0;
        double b=0;
        int n=pointnum-1;
        for(double t=0;t<=1;t+=0.01){ //控制求得曲线上点的数量
            for(int i=0;i<pointnum;i++){
                b=Factorial(n)*Math.pow(t, i)*
                        Math.pow(1-t, n-i)/Factorial(i)/Factorial(n-i);
                ex+=point.get(i).getX()*b;
                ey+=point.get(i).getY()*b;
            }
            ((Graphics2D) graphics).setStroke( new BasicStroke( 2 ) );
            graphics.drawLine((int)sx, (int)sy, (int)ex, (int)ey);

            sx=ex;sy=ey;
            ex=0;ey=0;
        }
    }

    public static int Factorial(int num){
        int sum=1;
        if(num<0){//判断传入数是否为负数
            throw new IllegalArgumentException("必须为正整数!");//抛出不合理参数异常
        }
        for(int i=1;i<=num;i++){//循环num
            sum *= i;//每循环一次进行乘法运算
        }
        return sum;//返回阶乘的值
    }
}
