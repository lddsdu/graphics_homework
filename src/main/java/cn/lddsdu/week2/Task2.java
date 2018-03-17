package cn.lddsdu.week2;
/**
 * Created by jack on 18/3/17.
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Task2{
    public static void main(String[] args) throws IOException {
        BufferedImage src = ImageIO.read(new File("my.jpg"));
        int height = src.getHeight();
        int width = src.getWidth();
        int [] rgb = new int[4];

        int pixel = src.getRGB(660,0);
        System.out.println(pixel);

        rgb[0] = (pixel & 0xff000000)>> 24;
        rgb[1] = (pixel & 0x00ff0000)>> 16;
        rgb[2] = (pixel & 0x0000ff00)>> 8;
        rgb[3] = (pixel & 0x000000ff);

        System.out.println(rgb[0] + " " + rgb[1] + " "+ rgb[2] + " "+ rgb[3]);


    }
}

