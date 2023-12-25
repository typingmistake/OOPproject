package Lotto_Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

//목표 등수
public class Sub3 extends JFrame implements Lotto_function {
    private int target_num = 5;
    private JCheckBox[] cb = new JCheckBox[5]; // 체크박스 배열
    private ButtonGroup bg = new ButtonGroup(); // 버튼 그룹
    private JButton[] jb = new JButton[45]; // 로또 번호
    private JTextArea ta = new JTextArea("선택된 번호 : ");
    private JTextArea ta2 = new JTextArea(""); // 최종 결과 재생
    private int count = 0;
    private Vector<Integer> v = new Vector<>();
    private Vector<Integer> resultSet = new Vector<>();
    private int number = 0; // 현재 회차 번호

    public  Sub3 (){
        super("등수 선택");

        JPanel jp2 = new JPanel(new GridLayout(1,6)); // 등수 1~6
        JLabel jl = new JLabel("목표 등수", SwingConstants.CENTER);
        JPanel jp = new JPanel(new GridLayout(5, 9)); // 번호 선택
        setLayout(null);
        JButton accept = new JButton("Enter");
        JButton reset = new JButton("Reset");
        Color pink = new Color(255, 192, 203);
        Color mint = new Color(152, 255, 152);
        Color blue = new Color(135, 206, 235);

        jp2.add(jl);
        for(int i = 0;i<5;i++){
            cb[i] = new JCheckBox((i+1)+"등");
            cb[i].addItemListener(new MyItemListener());
            cb[i].setBackground(blue);
            bg.add(cb[i]);
            jp2.add(cb[i]);
        }
        cb[4].setSelected(true);
        for(int i=0;i<45;i++){
            jb[i] = new JButton(Integer.toString(i+1));
            jb[i].addActionListener(new MyActionListener());
            jb[i].setBackground(pink);
            jp.add(jb[i]);
        }
        jp2.setBounds(0, 0, 800, 50);
        jp2.setBackground(blue);
        jp.setBounds(0, 50, 800, 250); // 위치와 크기 설정

        ta.setLocation(0,300);
        ta.setSize(650,100);
        ta.setFont(new Font("맑은 고딕",Font.BOLD,40));

        JScrollPane sp = new JScrollPane(ta2);
        sp.setLocation(0,400);
        sp.setSize(650,100);

        accept.setLocation(650,300);
        accept.setSize(150,100);
        accept.setBackground(mint);
        accept.addActionListener(new MyActionListener());

        reset.setLocation(650,400);
        reset.setSize(150,100);
        reset.setBackground(mint);
        reset.addActionListener(new MyActionListener());

        add(jp);
        add(jp2);
        add(ta);
        add(sp);
        add(accept);
        add(reset);

        setLocation(300,130);
        setSize(810,530);
        setVisible(true);
    }
    private class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton jb = (JButton) e.getSource();

            if (jb.getText().equals("Enter")) {
                Lotto();
                ta.setText("선택된 번호 : ");
                resetButtons();
            } else if (jb.getText().equals("Reset")) {
                ta.setText("선택된 번호 : ");
                ta2.setText("");
                resetButtons();
                number = 0;
            } else {
                ta.append(jb.getText() + " ");
                v.add(Integer.parseInt(jb.getText()));
                jb.setEnabled(false);
                if (v.size() == 6) {
                    Lotto();
                    resetButtons();
                    ta.setText("선택된 번호 : ");
                }
            }
        }
    }
    private class MyItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateTargetNum();
            }
        }
    }
    private void updateTargetNum() {
        for (int i = 0; i < 5; i++) {
            if (cb[i].isSelected()) {
                target_num = i + 1;
                ta2.append("목표 등수 : "+target_num+"\n");
                break;
            }
        }
    }

    // 모든 버튼 잠금 해제
    private void resetButtons() {
        v.clear();
        for (JButton button : jb) {
            button.setEnabled(true);
        }
    }
    // 로또 프로그램 실행
    @Override
    public void Lotto() {

        ta2.append("\n선택번호는 : < ");
        for (int i = 0; i < v.size(); i++) {
            ta2.append(Integer.toString(v.get(i)) + " ");
        }
        ta2.append("> 입니다.");

        if (v.size()!=6){
            ta2.append("\n덜 채워진 번호 "+(6-v.size())+"개는 랜덤 번호로 채워집니다 : ");
            while (v.size()!=6){
                int flag = 1;
                int tmp = (int) (Math.random() * 45) + 1;
                for (int i = 0; i< v.size(); i++) {
                    if (tmp == v.get(i)) {
                        flag = 0;
                    }
                }
                if (flag == 1){
                    v.add(tmp);
                    ta2.append(Integer.toString(tmp)+" ");
                }
            }
        }
        int[] Lotto;
        int rank;
        do {
            Lotto = genLotto();
            rank = drawLotto(Lotto);
            count++;
        } while(rank != target_num);
        // 결과 출력
        ta2.append("\n목표 등수를 달성하기까지 총 걸린 회차는 " + count + "회 입니다.\n");
        count = 0;
    }
    // 로또 번호 생성

    @Override
    public int[] genLotto(){
        int tmp;
        int gen_count = 0;
        int ok_generation;
        int[] lotto = new int[7];

        while (gen_count < 7) {
            ok_generation = 1;
            tmp = (int) (Math.random() * 45) + 1;

            for (int i = 0; i< 7; i++) {
                if (tmp == lotto[i]) {
                    ok_generation = 0;
                }
            }
            if (ok_generation == 1) {
                lotto[gen_count] = tmp;
                gen_count += 1;
            }
        }

        return lotto;
    }
    // 로또 시행
    @Override
    public int drawLotto(int[] Lotto) {
        int cnt = 0, i, j, rank;

        for (i = 0; i < 6; i++) {
            for (j = 0; j < 6; j++) {
                if (Lotto[i] == v.get(j)) {
                    cnt++;
                }
            }
        }
        int bonus_count = 0;
        for (i = 0; i < 6; i++) {
            if (Lotto[6] == v.get(i)) {
                bonus_count = 1;
            }
        }
        if (cnt == 6) {
            rank = 1;
        } else if (cnt == 5) {
            if (bonus_count == 1) {
                rank = 2;
            } else {
                rank = 3;
            }
        } else if (cnt == 4) {
            rank = 4;
        } else if (cnt == 3) {
            rank = 5;
        } else {
            rank = 6;
        }
        return rank;
    }

}


