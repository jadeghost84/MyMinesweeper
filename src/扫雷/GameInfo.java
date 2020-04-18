package 扫雷;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameInfo extends JPanel{
	private static final long serialVersionUID = 1L;
	public int t_value = 0;
	public int m_value = GameInterface.minenum;
	JLabel time = null;
	JLabel mine = null;
	Timer timer = null;
	String a1 = "      时间:";
	String a2= "秒";
	String b1 = "雷数:";
	String b2 = "个      ";
	public GameInfo() {
		time = new JLabel(a1+t_value+a2);
		mine = new JLabel(b1+m_value+b2);
		updateFont();
		this.setLayout(new BorderLayout());
//		time.setBounds(80,0,150,30);
//		mine.setBounds(430,0,150,30);
		this.add(time,BorderLayout.WEST);
		this.add(mine,BorderLayout.EAST);
		this.setBackground(MainInterface.bgcolor);
	}
	public void runTimer() {
		Timer timer = new Timer();
		this.timer = timer;
		timer.schedule(new TimerTask() {
			public void run() {
				t_value++;
				time.setText(a1+t_value+a2);
			}
		},0,1000);
	}
	public int gettime() {//返回所用时间
		return t_value;
	}
	public void stopTimer() {
		this.timer.cancel();
	}
	public void renewTimer() {
		t_value = 0;
		time.setText(a1+t_value+a2);
	}
	public void subMiner() {//小旗标志雷数器减一
		m_value--;
		updateMiner();
	}
	public void addMiner() {//取消小旗标志雷数器加一
		m_value++;
		updateMiner();
	}
	public void renewMiner() {
		m_value = GameInterface.minenum;
		updateMiner();
	}
	public void updateMiner() {
		mine.setText(b1+m_value+b2);
	}
	public void updateFont() {
		Font font = new Font(Font.DIALOG,Font.BOLD,15+2*GameInterface.level);
		time.setFont(font);
		mine.setFont(font);
	}
}
