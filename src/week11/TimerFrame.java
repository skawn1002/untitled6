package week11;

import java.awt.*;
import javax.swing.*;

class TimerThread extends Thread {
    JLabel timerLabel;

    public TimerThread(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    static int n = -1;
    static int time = 1000;
    public void run() {
        while(true) {
            n = -1;
            while (n < 9) {
                n++;
                timerLabel.setText(Integer.toString(n));
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }}
        //기존 n을 받아올 수 있도록, public static으로 getTime이라는 변수에 n을 리턴시켜주는 새로운 함수를 만든다.
    public static int getTime() {
        return n;
    }
    public static void setSpeed() {
        //기존의 타임을 미리 변수로 지정해 놓았다. , getSpeed()함수가 불러져 올 경우에, time을 2배 빨라지게 만든다. 1000을 2로 나누면 2배 빨라진다.
        time = time/2;
        System.out.println("time: "+time);
    }
}

class TimerFrame extends JFrame {


    public TimerFrame(String title) {
        /*-----GUI setting (Don't change this code)-----*/
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        JLabel timerLabel = new JLabel();
        timerLabel.setFont(new Font("Gothic", Font.ITALIC, 80));
        c.add(timerLabel);

        TimerThread th = new TimerThread(timerLabel);
        setSize(250, 150);
        setVisible(true);
        th.start();
        /*---------------------------------------------*/
    }
}