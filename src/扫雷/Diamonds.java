package ɨ��;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
public class Diamonds extends JButton{
	private static final long serialVersionUID = 1L;
	public Font numstyle = new Font("numstyle",Font.BOLD,15);
	public boolean ismine = false;//�Ƿ�Ϊ��
	public boolean isdoclick = false;//�Ƿ񱻵��
	public boolean isflag = false;//�Ƿ������־
	public boolean isbomber = false;//�Ƿ�������
	public int roundminesum = 0;//��Χ����
	public static BevelBorder border = null;
	public static int size = 20;
	public static int doclicknum = 0;
	public Diamonds(int diacolor) {
		super.setUI(new MyButtonUI());
		border = new BevelBorder(BevelBorder.RAISED);
	//	border = BorderFactory.createRaisedBevelBorder();//͹��߿�
		this.setPreferredSize(new Dimension(size,size));
		this.setFont(numstyle);
		this.setBackground(MainInterface.getdiaColor(diacolor));
		this.setBorder(border);
	}
	public void setMine(boolean mine) {//�����Ƿ�Ϊ��
		this.ismine = mine;
	}
	public boolean getMine() {//��֪�Ƿ�Ϊ��
		return this.ismine;
	}
	public void setDoclick(boolean click) {//�����Ƿ��ѵ��
		this.isdoclick = click;
	}
	public boolean getDoclick() {//��֪�Ƿ��ѵ��
		return this.isdoclick;
	}
	public void setRoundMineSum(int num) {//������Χ����
		if(num>=0||num<=8) {
			 this.roundminesum = num;
		}
	}
	public void coundRoundMineSum() {//��Χ�����ۼ�1
		this.roundminesum++;
	}
	public int getRoundMineSum() {//�����Χ����
		return this.roundminesum;
	}
	public void doclickEvent() {//��ť����������¼�
		this.setBackground(new Color(173,216,230));
		Border border = BorderFactory.createLoweredBevelBorder();//���ݱ߿�
		this.setBorder(border);
		this.setDoclick(true);
		this.setEnabled(false);
		Diamonds.doclicknum++;
	}
	public void showText() {//��ť�ı���ʾ
		if(this.roundminesum == 1) {
			this.setForeground(new Color(0,100,0));//����ɫ
		}
		if(this.roundminesum == 2) {
			this.setForeground(new Color(139,0,0));//���ɫ
		}
		if(this.roundminesum == 3) {
			this.setForeground(new Color(0,0,139));//����ɫ
		}
		if(this.roundminesum == 4) {
			this.setForeground(new Color(210,105,30));//�ɿ���
		}
		if(this.roundminesum == 5) {
			this.setForeground(new Color(165,42,42));//��ɫ
		}
		if(this.roundminesum == 6) {
			this.setForeground(new Color(188,143,143));//õ����ɫ
		}
		if(this.roundminesum == 7) {
			this.setForeground(new Color(148,0,211));//��������ɫ
		}
		if(this.roundminesum == 8) {
			this.setForeground(Color.BLACK);//����ɫ
		}
		this.setText(Integer.toString(this.roundminesum));
	}
	public void reNew(int diacolor) {
		this.isdoclick = false;//������÷�
		this.setEnabled(true);//��ť�ع�ɵ��
		this.setMine(false);//�ױ�־�÷�
		this.isflag = false;//�����־�÷�
		this.isbomber = false;//�����߱�־�÷�
		Diamonds.doclicknum = 0;//��0�������
		this.setBorder(border);
		this.setRoundMineSum(0);
		this.setBackground(MainInterface.getdiaColor(diacolor));
		this.setText(null);
		this.setIcon(null);
	}
}
