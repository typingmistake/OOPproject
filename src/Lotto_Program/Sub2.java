package Lotto_Program;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

//번호 선택
public class Sub2 extends JFrame implements Lotto_function {
    private JTextArea ta = new JTextArea("선택된 번호 : ");
    private JTextArea ta2 = new JTextArea("");
    private int count = 0;
    private Vector<Integer> v = new Vector<>();
    private Vector<Integer> resultSet = new Vector<>();
    private JButton[] jb = new JButton[45]; // 로또 번호
    private int number = 0; // 현재 회차 번호
    public Sub2(){
        super("일반 선택");

        setLayout(null);
        JButton accept = new JButton("Enter");
        JButton reset = new JButton("Reset");
        JPanel jp = new JPanel(new GridLayout(5,9));
        Color pink = new Color(255, 192, 203);
        Color mint = new Color(152, 255, 152);

        for(int i=0;i<45;i++){
            jb[i] = new JButton(Integer.toString(i+1));
            jb[i].addActionListener(new MyActionListener());
            jb[i].setBackground(pink);
            jp.add(jb[i]);
        }

        jp.setSize(800,300);

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
        add(ta);
        add(sp);
        add(accept);
        add(reset);

        setLocation(300,130);
        setSize(810,530);
        setVisible(true);
    }
    private class MyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton jb = (JButton)e.getSource();

            if(jb.getText().equals("Enter")){
                Lotto();
                ta.setText("선택된 번호 : ");
                v.clear();
                resetButtons();
            } else if(jb.getText().equals("Reset")){
                v.clear();
                ta.setText("선택된 번호 : ");
                ta2.setText("");
                resetButtons();
                number = 0;
            } else{
                ta.append(jb.getText() + " ");
                v.add(Integer.parseInt(jb.getText()));
                jb.setEnabled(false);
                if (v.size()==6) {
                    Lotto();
                    v.clear();
                    resetButtons();
                    ta.setText("선택된 번호 : ");
                }
            }
        }
    }
    // 모든 버튼 잠금 해제
    private void resetButtons() {
        for (JButton button : jb) {
            button.setEnabled(true);
        }
    }
    // 로또 프로그램 실행
    @Override
    public void Lotto() {
        ta2.append("##############<회차 "+(++number)+">\n");

        int[] Lotto = genLotto();
        int rank = drawLotto(Lotto);
        resultSet.add(rank);
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
            ta2.append("이번주 행운의 번호는 ");
            for (int i = 0; i < 6; i++) {
                ta2.append(lotto[i] + " ");
            }
            ta2.append("+ 보너스 번호"+lotto[6]+"입니다.");

            return lotto;
    }
    // 로또 시행
    @Override
    public int drawLotto(int[] Lotto) {
        int cnt = 0, i, j, rank;
        Vector<Integer> result = new Vector<>();

        ta2.append("\n선택번호는 : < ");
        for (i = 0; i < v.size(); i++) {
            ta2.append(Integer.toString(v.get(i)) + " ");
        }
        ta2.append("> 입니다.");

        if (v.size()!=6){
            ta2.append("\n덜 채워진 번호 "+(6-v.size())+"개는 랜덤 번호로 채워집니다 : ");
            while (v.size()!=6){
                int flag = 1;
                int tmp = (int) (Math.random() * 45) + 1;
                for (i = 0; i< v.size(); i++) {
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
        for (i = 0; i < 6; i++) {
            for (j = 0; j < 6; j++) {
                if (Lotto[i] == v.get(j)) {
                    result.add(Lotto[i]);
                    cnt++;
                }
            }
        }
        ta2.append("\n당첨 번호 개수 : "+cnt);
        if (cnt > 0) {
            ta2.append(", 당첨 번호는 ");
            for (i = 0; i < cnt; i++) {
                ta2.append(result.get(i) + " ");
            }
            ta2.append("입니다.");
        }
        int bonus_count = 0;
        for (i = 0; i < 6; i++) {
            if (Lotto[6] == v.get(i)) {
                bonus_count = 1;
            }
        }
        if (cnt == 6) {
            ta2.append(" 로또 추첨 결과 1등입니다.\n");
            rank = 1;
        } else if (cnt == 5) {
            if (bonus_count == 1) {
                ta2.append(" 로또 추첨 결과 2등입니다.\n");
                rank = 2;
            } else {
                ta2.append(" 로또 추첨 결과 3등입니다.\n");
                rank = 3;
            }
        } else if (cnt == 4) {
            ta2.append(" 로또 추첨 결과 4등입니다.\n");
            rank = 4;
        } else if (cnt == 3) {
            ta2.append(" 로또 추첨 결과 5등입니다.\n");
            rank = 5;
        } else {
            ta2.append(" 로또 추첨 결과 꽝입니다.\n");
            rank = 6;
        }
        return rank;
    }

}
