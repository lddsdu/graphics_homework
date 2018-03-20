package cn.lddsdu.week2;

import com.sun.prism.shader.DrawPgram_LinearGradient_PAD_AlphaTest_Loader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Task {
    public static void main(String[] args) throws InterruptedException {
        Window window = new Window(10,2,40,40,null);
        Thread.sleep(100);
        window.showWindow();
    }
}
