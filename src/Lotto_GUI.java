import Lotto_Program.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 프로그램 진입점

public class Lotto_GUI extends JFrame {
    JButton jb2 = new JButton("일반 선택");
    JButton jb3 = new JButton("등수 선택");
    Lotto_GUI(){
        super("로또 추첨 프로그램 메인화면");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,3));

        Color blue = new Color(135, 206, 235);

        jb2.addActionListener(new MyActionListner());
        jb2.setBackground(blue);
        jb3.addActionListener(new MyActionListner());
        jb3.setBackground(blue);

        add(jb2);
        add(jb3);

        setLocation(300,150);
        setSize(300,200);
        setVisible(true);
    }
    class MyActionListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jb2){
                new Sub2();
            } else{
                new Sub3();
            }
        }
    }
    public static void main(String[] args) {
        new Lotto_GUI();
    }
}