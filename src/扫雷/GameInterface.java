package ɨ��;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
public class GameInterface extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int level = 1;//�ؿ��Ѷ�
	public static int minenum = 10;//��������
	public int[] mine = null;
	public static int height = 9;//����߶�
	public static int width = 9;//������
	public boolean issetmine = false;//�Ƿ�����
	public Diamonds[][] dias = null;
	public Queue<Integer> queue = new LinkedList<Integer>();
	public static ImageIcon flag = new ImageIcon(GameInterface.class.getResource("image/С��.png"));
	public static ImageIcon gameoverico = new ImageIcon(GameInterface.class.getResource("image/lose.png"));
	public static ImageIcon gamewinico = new ImageIcon(GameInterface.class.getResource("image/win.jpg"));
	public static ImageIcon bomb = new ImageIcon(GameInterface.class.getResource("image/165323125.jpg"));
	public static ImageIcon trueflag = new ImageIcon(GameInterface.class.getResource("image/true.png"));
	public static ImageIcon falseflag = new ImageIcon(GameInterface.class.getResource("image/false.png"));
	public boolean gameover = false;//��Ϸ������־
	public static int diacolor = -1;//���÷��鱳����ɫ�ľ�̬����
	public int endiacolor = -1;//ȷ�����鱳����ɫ
	public GameInfo info = null;
	public GameInterface(GameInfo info) {//���캯�������ʱ�����������
		creatDiamonds();//ʵ��������
		this.info = info;
		this.setBackground(MainInterface.bgcolor);
		Insets insets = new Insets(15,30,0,30);
		MatteBorder mb = new MatteBorder(insets,MainInterface.bgcolor);
		this.setBorder(mb);
	}
	public void randomMine(int z,Diamonds[][] dia) {//����׺���
		Random random = new Random();
		this.mine = new int[minenum];
		int randomint = random.nextInt(height*width);
		for(int i = 0;i<minenum;i++) {
			int j = i;
			while(isContain(mine,i+1,randomint)||z==randomint) {//�������ظ������û���һ��������ظ����������
				randomint = random.nextInt(height*width);
			}
			mine[i] = randomint;
			int x = transformZtoXY(randomint)[0];
			int y = transformZtoXY(randomint)[1];
			dia[x][y].setMine(true);
			mineCountround(x,y,dia);
		}
	}
	public int transformXYtoZ(int x,int y) {
		int z;
		return z = x*width+y;
	}
	public int[] transformZtoXY(int z) {
		int x = z/width;
		int y = z%width;
		int[] s= {x,y}; 
		return s;
	}
	public void mineCountround(int x,int y,Diamonds[][] dias) {//������λ�ã�����Χ���׼�һ
			if(x!=0) {//ѡ�����Ϸ������
				if(!dias[x-1][y].ismine) {
					dias[x-1][y].coundRoundMineSum();
				}
			}
			if(x!=height-1) {//ѡ�����·������
				if(!dias[x+1][y].ismine) {
					dias[x+1][y].coundRoundMineSum();
				}
			}
			if(y!=0) {//ѡ�����󷽿����
				if(!dias[x][y-1].ismine) {
					dias[x][y-1].coundRoundMineSum();
				}
			}
			if(y!=width-1) {//ѡ�����ҷ������
				if(!dias[x][y+1].ismine) {
					dias[x][y+1].coundRoundMineSum();
				}
			}
			if(!(x==0||y==0)) {//ѡ�������Ϸ������
				if(!dias[x-1][y-1].ismine) {
					dias[x-1][y-1].coundRoundMineSum();
				}
			}
			if(!(x==0||y==width-1)) {//ѡ���������Ϸ������
				if(!dias[x-1][y+1].ismine) {
					dias[x-1][y+1].coundRoundMineSum();
				}
			}
			if(!(x==height-1||y==0)) {//ѡ�������·������
				if(!dias[x+1][y-1].ismine) {
					dias[x+1][y-1].coundRoundMineSum();
				}
			}
			if(!(x==height-1||y==width-1)) {//ѡ�������·�����
				if(!dias[x+1][y+1].ismine) {
					dias[x+1][y+1].coundRoundMineSum();
				}
			}
	}
	public void roundnullmine(int x,int y,Diamonds[][] dias) {//��Χ����Ϊ0�Ĵ����¼�
		if(x!=0&&dias[x-1][y].isEnabled()&&(!dias[x-1][y].isdoclick)) {//�ж��Ƿ����Ϸ���
			if(!queue.contains(transformXYtoZ(x-1,y))) {
				loopAs(x-1,y,dias);
			}
		}
		if(x!=height-1&&dias[x+1][y].isEnabled()&&(!dias[x+1][y].isdoclick)) {//�ж��Ƿ����·���
			if(!queue.contains(transformXYtoZ(x+1,y))) {
				loopAs(x+1,y,dias);
			}
		}
		if(y!=0&&dias[x][y-1].isEnabled()&&(!dias[x][y-1].isdoclick)) {//�ж��Ƿ����󷽿�
			if(!queue.contains(transformXYtoZ(x,y-1))) {
				loopAs(x,y-1,dias);
			}
		}
		if(y!=width-1&&dias[x][y+1].isEnabled()&&(!dias[x][y+1].isdoclick)) {//�ж��Ƿ����ҷ���
			if(!queue.contains(transformXYtoZ(x,y+1))) {
				loopAs(x,y+1,dias);
			}
		}
		if(x!=0&&y!=0&&dias[x-1][y-1].isEnabled()&&(!dias[x-1][y-1].isdoclick)) {//�ж��Ƿ������Ϸ���
			if(!queue.contains(transformXYtoZ(x-1,y-1))) {
				loopAs(x-1,y-1,dias);
			}
		}
		if(x!=height-1&&y!=0&&dias[x+1][y-1].isEnabled()&&(!dias[x+1][y-1].isdoclick)) {//�ж��Ƿ������·���
			if(!queue.contains(transformXYtoZ(x+1,y-1))) {
				loopAs(x+1,y-1,dias);
			}
		}
		if(x!=0&&y!=width-1&&dias[x-1][y+1].isEnabled()&&(!dias[x-1][y+1].isdoclick)) {//�ж��Ƿ������Ϸ���
			if(!queue.contains(transformXYtoZ(x-1,y+1))) {
				loopAs(x-1,y+1,dias);
			}
	}
		if(x!=height-1&&y!=width-1&&dias[x+1][y+1].isEnabled()&&(!dias[x+1][y+1].isdoclick)) {//�ж��Ƿ������·���
			if(!queue.contains(transformXYtoZ(x+1,y+1))) {
				loopAs(x+1,y+1,dias);
			}
		}
	}
	public void loopAs(int x,int y,Diamonds[][] dias) {//
		queue.offer(transformXYtoZ(x,y));
		if(dias[x][y].roundminesum == 0) {		
			roundnullmine(x,y,dias);
		}else {		
			dias[x][y].showText();
		}
	}
	public void queueDeal(Diamonds[][] dias) {//������ж���
			while(!queue.isEmpty()) {
				int z = 0;
				try {
					z = queue.poll();
				}catch(NullPointerException e) {
					return;
				}
				int x = transformZtoXY(z)[0];
				int y = transformZtoXY(z)[1];
				Diamonds dia = dias[x][y];
				dia.doclickEvent();
			}
	}
	public void addMouseListener(int x,int y ,Diamonds[][] dias) {
		Border border = BorderFactory.createLineBorder(new Color(255,0,255),3);//ֱ�߱߿�
		dias[x][y].addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getButton() == MouseEvent.BUTTON1) {
					
				}
			}
			public void mouseEntered(MouseEvent arg0) {//����ʱ͹�Ա߿�
				if(!dias[x][y].isdoclick) {
					dias[x][y].setBorder(border);
				}
			}
			public void mouseExited(MouseEvent arg0) {
				if(!dias[x][y].isdoclick&&!dias[x][y].isbomber) {
					dias[x][y].setBorder(Diamonds.border);
				}else if(dias[x][y].isbomber){
					dias[x][y].setBorder(BorderFactory.createLineBorder(Color.RED,3));//͹����������
				}
			}
			public void mousePressed(MouseEvent arg0) {
				if(arg0.getButton() == MouseEvent.BUTTON3) {
					if(dias[x][y].isEnabled()&&!dias[x][y].isflag) {
						dias[x][y].setIcon(GameInterface.flag);
						dias[x][y].isflag = true;
						dias[x][y].setEnabled(false);
						info.subMiner();
					}else if(!gameover&&dias[x][y].isflag){
						dias[x][y].setIcon(null);
						dias[x][y].isflag = false;
						dias[x][y].setEnabled(true);	
						info.addMiner();
					}
				}
			}
			public void mouseReleased(MouseEvent arg0) {
				
			}
		});
	}
	public void addActionListener(int x,int y,Diamonds[][] dias) {
		dias[x][y].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!issetmine) {
					info.runTimer();//��ʼ��ʱ
					randomMine(transformXYtoZ(x,y),dias);//����׺���
					issetmine = true;
				}
				dias[x][y].doclickEvent();
				if(!dias[x][y].ismine) {
					if(dias[x][y].roundminesum==0) {
						roundnullmine(x,y,dias);
						queueDeal(dias);//��Χ����Ϊ0���м���
					}else {
						dias[x][y].showText();
					}
					iswin();
				}else {
					gameover(dias[x][y]);
				}
			}
		});
	}
	public void changeColorImmediately() {//�����������øı䷽����ɫ
		endiacolor = ensureDiamondsColor(diacolor);//ȷ�����鱳����ɫ
		for(int i = 0;i<height;i++) {
			for(int j = 0;j<width;j++) {
				if(!this.dias[i][j].isdoclick) {
					this.dias[i][j].setBackground(MainInterface.getdiaColor(endiacolor));
				}
			}
		}
	}
	public boolean isContain(int[] a,int b,int c) {//���Գ���b�������Ƿ����c
		for(int i = b-1;i>=0;i--) {
			if(c == a[i]) {
				return true;
			}
		}
		return false;
	}
	public int isMinecount(Diamonds dia) {//���׷���1������Ϊ0
		if(dia.ismine) {
			return 1;
		}
		return 0;
	}
	public void iswin() {//�ж��Ƿ�ɹ�
		if(height*width-minenum-Diamonds.doclicknum==0) {
			gamewin();
		}
	}
	public void gamewin() {//��Ϸ�ɹ�����
		gameover = true;//��־��Ϸ����
		for(int i = 0;i<minenum;i++) {
			int x = transformZtoXY(mine[i])[0];
			int y = transformZtoXY(mine[i])[1];
			dias[x][y].setIcon(GameInterface.trueflag);
			dias[x][y].setEnabled(false);
		}
		info.stopTimer();//��ͣ��ʱ
		if(GameInterface.level == 3) {
			MainInterface.level4.setEnabled(true);
		}
		new Record(info.gettime(),GameInterface.level);
		//for(;!record;) {}//��¼���ڹرռ���
		int jop = JOptionPane.showConfirmDialog(this,"Congratulations on your success��\nDo you want to continue��","you win!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,gamewinico);
		if(jop == JOptionPane.OK_OPTION) {
			replay();
		};
	}
	public void gameover(Diamonds dia) {//��Ϸʧ�ܴ���
		gameover = true;//��־��Ϸ����
		for(int i = 0;i<height;i++) {
			for(int j = 0;j<width;j++) {
				if(dias[i][j].isflag&&dias[i][j].ismine) {//������ȷ
					dias[i][j].setIcon(GameInterface.trueflag);
				}else if(dias[i][j].isflag&&!dias[i][j].ismine) {//���״���
					dias[i][j].setIcon(GameInterface.falseflag);
				}else if(!dias[i][j].isflag&&dias[i][j].ismine) {//δ���׵���ʾ
					dias[i][j].setIcon(GameInterface.bomb);
				}
				dias[i][j].setEnabled(false);
			}
		}
		info.stopTimer();//��ͣ��ʱ
		dia.isbomber = true;
		dia.setBorder(BorderFactory.createLineBorder(Color.RED,3));//͹����������
		int jop = JOptionPane.showConfirmDialog(this,"Continue to exert oneself��\nDo you want to continue��","game over!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,gameoverico);
		if(jop == JOptionPane.OK_OPTION) {
			replay();
		};
	}
	public int ensureDiamondsColor(int a) {//���÷�����ɫ,-1Ϊ���
		Random random = new Random();
		if(a == -1) {
			a = random.nextInt(MainInterface.diacolornum);
		}
		return a;
	}
	public void creatDiamonds() {
		this.removeAll();
		this.setLayout(new GridLayout(height,width));
		this.endiacolor =  ensureDiamondsColor(diacolor);//ȷ�����鱳����ɫ
		Diamonds[][] dias = new Diamonds[height][width];
		this.dias = dias;
		for(int i = 0;i<height;i++) {
			for(int j = 0;j<width;j++) {
				dias[i][j] = new Diamonds(this.endiacolor);
				addMouseListener(i,j,dias);
				addActionListener(i,j,dias);
				super.add(dias[i][j]);
			}
		}
		this.updateUI();
	}
	public void replay() {//���¿�ʼ��Ϸ
		issetmine = false;//�����Ƿ����ױ�־
		gameover= false;//������Ϸ������־
		info.renewMiner();//�ָ�����
		if(info.timer != null) {
			info.stopTimer();//���ռ�ʱ��
		}
		info.renewTimer();//����ʱ��
		this.endiacolor = ensureDiamondsColor(GameInterface.diacolor);
		for(int i = 0;i<height;i++) {
			for(int j = 0;j<width;j++) {
				Diamonds dia = this.dias[i][j];
				dia.reNew(this.endiacolor);
			}
		}
	}
	public void levelSelect(int level) {
		if(level == 1) {
			GameInterface.level = 1;
			GameInterface.minenum = 10;
			GameInterface.width = 9;
			GameInterface.height = 9;
			creatDiamonds();//���´�������
		}else if(level == 2) {
			GameInterface.level = 2;
			GameInterface.minenum = 40;
			GameInterface.width = 16;
			GameInterface.height = 16;
			creatDiamonds();//���´�������
		}else if(level == 3) {
			GameInterface.level = 3;
			GameInterface.minenum = 99;
			GameInterface.width = 30;
			GameInterface.height = 16;
			creatDiamonds();//���´�������
		}else if(level == 4) {
			GameInterface.level = 4;
			GameInterface.minenum = 200;
			GameInterface.width = 48;
			GameInterface.height = 22;
			creatDiamonds();//���´�������
		}
		info.updateFont();
		replay();
	}
}