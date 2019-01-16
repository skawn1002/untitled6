package week11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*-----GUI setting (Don't change this code)-----*/
class MyLabel extends JLabel {
    int barSize = 0; // size of bar
    int maxBarSize;

    MyLabel(int maxBarSize) {
        this.maxBarSize = maxBarSize;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.MAGENTA);
        int width = (int) (((double) (this.getWidth())) / maxBarSize * barSize);
        if (width == 0)
            return;
        g.fillRect(0, 0, width, this.getHeight());
    }

    synchronized void fill() {
        if (barSize == maxBarSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        barSize++;
        repaint(); // refresh the bar
        notify();
    }

    synchronized void consume() {
        if (barSize == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        barSize--;
        repaint(); // refresh the bar
        notify();
    }
}

class ConsumerThread extends Thread {
    MyLabel bar;

    ConsumerThread(MyLabel bar) {
        this.bar = bar;
    }

    public void run() {
        while (true) {
            try {
                sleep(200);
                bar.consume();
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
/*----------------------------------------------------*/

class BarFrame extends JFrame {

    /*-----GUI setting (Don't change this code)-----*/
    MyLabel bar = new MyLabel(100);

    BarFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);
        bar.setBackground(Color.ORANGE);
        bar.setOpaque(true);
        bar.setLocation(20, 50);
        bar.setSize(300, 20);
        c.add(bar);
    /*---------------------------------------------*/
////// 키 입력을 받는 부분
        c.addKeyListener(new KeyAdapter() {
            // 틀린 넘버 == cnum
            int cnum = 0;
            public void keyPressed(KeyEvent e) {
                //키를 누르면 어떤 키가 나오는 지 프린트로 표시해주는 역할이고
                System.out.println("key typed " + e.getKeyChar());
                //만약, 타이머쓰레드 클래스의 겟 타임메소드(카운트에 해당하는 n을 뱉어주는 역할)과 ((Character.getNumericValue(char);char 파라미터가 숫자인 경우, 숫자를 받아오고, char 파라미터가 char인 경우 아스키코드로 뱉어주는 함수))가 같다면, 바가 채워짐
                if(TimerThread.getTime()==Character.getNumericValue(e.getKeyChar())) {
                    bar.fill();//Increase the purple gauge when keyboard input is received.
                }else {
                    //아니면, 넘버가 틀린변수가 올라가.
                    cnum++;
                    //근데 만약에 이 넘버 틀린변수가 100을 나눴을때, 나머지가 0이고 (100, 200번 틀릴 경우가 된다면,)
                    if(cnum%100==0 && cnum > 0) {
                        //타임이 빨라진다는 안내와 함께
                        System.out.println("Time speed x2");
                        //타임쓰레드의 setSpeed()가 불려져온다.
                        TimerThread.setSpeed();
                    }
                    System.out.println("Wrong value!! count: " + cnum);
                }
            }

        });
    /*-----GUI setting (Don't change this code)-----*/
        setSize(350, 200);
        setVisible(true);

        c.requestFocus();
        ConsumerThread th = new ConsumerThread(bar);
        th.start(); // thread start
    }
    /*---------------------------------------------*/


}