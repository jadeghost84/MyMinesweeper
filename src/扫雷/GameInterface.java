package 扫雷;
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
	public static int level = 1;//关卡难度
	public static int minenum = 10;//雷总数量
	public int[] mine = null;
	public static int height = 9;//方阵高度
	public static int width = 9;//方阵宽度
	public boolean issetmine = false;//是否设雷
	public Diamonds[][] dias = null;
	public Queue<Integer> queue = new LinkedList<Integer>();
	public static ImageIcon flag = new ImageIcon(GameInterface.class.getResource("image/小旗.png"));
	public static ImageIcon gameoverico = new ImageIcon(GameInterface.class.getResource("image/lose.png"));
	public static ImageIcon gamewinico = new ImageIcon(GameInterface.class.getResource("image/win.jpg"));
	public static ImageIcon bomb = new ImageIcon(GameInterface.class.getResource("image/165323125.jpg"));
	public static ImageIcon trueflag = new ImageIcon(GameInterface.class.getResource("image/true.png"));
	public static ImageIcon falseflag = new ImageIcon(GameInterface.class.getResource("image/false.png"));
	public boolean gameover = false;//游戏结束标志
	public static int diacolor = -1;//设置方块背景颜色的静态参数
	public int endiacolor = -1;//确定方块背景颜色
	public GameInfo info = null;
	public GameInterface(GameInfo info) {//构造函数传入计时计数板的引用
		creatDiamonds();//实例方块阵
		this.info = info;
		this.setBackground(MainInterface.bgcolor);
		Insets insets = new Insets(15,30,0,30);
		MatteBorder mb = new MatteBorder(insets,MainInterface.bgcolor);
		this.setBorder(mb);
	}
	public void randomMine(int z,Diamonds[][] dia) {//随机雷函数
		Random random = new Random();
		this.mine = new int[minenum];
		int randomint = random.nextInt(height*width);
		for(int i = 0;i<minenum;i++) {
			int j = i;
			while(isContain(mine,i+1,randomint)||z==randomint) {//如果随机重复或与用户第一个点击的重复则重新随机
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
	public void mineCountround(int x,int y,Diamonds[][] dias) {//传入雷位置，给周围非雷加一
			if(x!=0) {//选中有上方块的雷
				if(!dias[x-1][y].ismine) {
					dias[x-1][y].coundRoundMineSum();
				}
			}
			if(x!=height-1) {//选中有下方块的雷
				if(!dias[x+1][y].ismine) {
					dias[x+1][y].coundRoundMineSum();
				}
			}
			if(y!=0) {//选中有左方块的雷
				if(!dias[x][y-1].ismine) {
					dias[x][y-1].coundRoundMineSum();
				}
			}
			if(y!=width-1) {//选中有右方块的雷
				if(!dias[x][y+1].ismine) {
					dias[x][y+1].coundRoundMineSum();
				}
			}
			if(!(x==0||y==0)) {//选中有左上方块的雷
				if(!dias[x-1][y-1].ismine) {
					dias[x-1][y-1].coundRoundMineSum();
				}
			}
			if(!(x==0||y==width-1)) {//选中有右右上方块的雷
				if(!dias[x-1][y+1].ismine) {
					dias[x-1][y+1].coundRoundMineSum();
				}
			}
			if(!(x==height-1||y==0)) {//选中有左下方块的雷
				if(!dias[x+1][y-1].ismine) {
					dias[x+1][y-1].coundRoundMineSum();
				}
			}
			if(!(x==height-1||y==width-1)) {//选中有右下方的雷
				if(!dias[x+1][y+1].ismine) {
					dias[x+1][y+1].coundRoundMineSum();
				}
			}
	}
	public void roundnullmine(int x,int y,Diamonds[][] dias) {//周围雷数为0的触发事件
		if(x!=0&&dias[x-1][y].isEnabled()&&(!dias[x-1][y].isdoclick)) {//判定是否有上方块
			if(!queue.contains(transformXYtoZ(x-1,y))) {
				loopAs(x-1,y,dias);
			}
		}
		if(x!=height-1&&dias[x+1][y].isEnabled()&&(!dias[x+1][y].isdoclick)) {//判定是否有下方块
			if(!queue.contains(transformXYtoZ(x+1,y))) {
				loopAs(x+1,y,dias);
			}
		}
		if(y!=0&&dias[x][y-1].isEnabled()&&(!dias[x][y-1].isdoclick)) {//判定是否有左方块
			if(!queue.contains(transformXYtoZ(x,y-1))) {
				loopAs(x,y-1,dias);
			}
		}
		if(y!=width-1&&dias[x][y+1].isEnabled()&&(!dias[x][y+1].isdoclick)) {//判定是否有右方块
			if(!queue.contains(transformXYtoZ(x,y+1))) {
				loopAs(x,y+1,dias);
			}
		}
		if(x!=0&&y!=0&&dias[x-1][y-1].isEnabled()&&(!dias[x-1][y-1].isdoclick)) {//判定是否有左上方块
			if(!queue.contains(transformXYtoZ(x-1,y-1))) {
				loopAs(x-1,y-1,dias);
			}
		}
		if(x!=height-1&&y!=0&&dias[x+1][y-1].isEnabled()&&(!dias[x+1][y-1].isdoclick)) {//判定是否有左下方块
			if(!queue.contains(transformXYtoZ(x+1,y-1))) {
				loopAs(x+1,y-1,dias);
			}
		}
		if(x!=0&&y!=width-1&&dias[x-1][y+1].isEnabled()&&(!dias[x-1][y+1].isdoclick)) {//判定是否有右上方块
			if(!queue.contains(transformXYtoZ(x-1,y+1))) {
				loopAs(x-1,y+1,dias);
			}
	}
		if(x!=height-1&&y!=width-1&&dias[x+1][y+1].isEnabled()&&(!dias[x+1][y+1].isdoclick)) {//判定是否有右下方块
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
	public void queueDeal(Diamonds[][] dias) {//处理队列对象
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
		Border border = BorderFactory.createLineBorder(new Color(255,0,255),3);//直线边框
		dias[x][y].addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getButton() == MouseEvent.BUTTON1) {
					
				}
			}
			public void mouseEntered(MouseEvent arg0) {//进入时凸显边框
				if(!dias[x][y].isdoclick) {
					dias[x][y].setBorder(border);
				}
			}
			public void mouseExited(MouseEvent arg0) {
				if(!dias[x][y].isdoclick&&!dias[x][y].isbomber) {
					dias[x][y].setBorder(Diamonds.border);
				}else if(dias[x][y].isbomber){
					dias[x][y].setBorder(BorderFactory.createLineBorder(Color.RED,3));//凸显引爆的雷
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
					info.runTimer();//开始计时
					randomMine(transformXYtoZ(x,y),dias);//随机雷函数
					issetmine = true;
				}
				dias[x][y].doclickEvent();
				if(!dias[x][y].ismine) {
					if(dias[x][y].roundminesum==0) {
						roundnullmine(x,y,dias);
						queueDeal(dias);//周围雷数为0队列监听
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
	public void changeColorImmediately() {//立即根据设置改变方块颜色
		endiacolor = ensureDiamondsColor(diacolor);//确定方块背景颜色
		for(int i = 0;i<height;i++) {
			for(int j = 0;j<width;j++) {
				if(!this.dias[i][j].isdoclick) {
					this.dias[i][j].setBackground(MainInterface.getdiaColor(endiacolor));
				}
			}
		}
	}
	public boolean isContain(int[] a,int b,int c) {//测试长度b的数组是否包含c
		for(int i = b-1;i>=0;i--) {
			if(c == a[i]) {
				return true;
			}
		}
		return false;
	}
	public int isMinecount(Diamonds dia) {//是雷返回1，不是为0
		if(dia.ismine) {
			return 1;
		}
		return 0;
	}
	public void iswin() {//判定是否成功
		if(height*width-minenum-Diamonds.doclicknum==0) {
			gamewin();
		}
	}
	public void gamewin() {//游戏成功处理
		gameover = true;//标志游戏结束
		for(int i = 0;i<minenum;i++) {
			int x = transformZtoXY(mine[i])[0];
			int y = transformZtoXY(mine[i])[1];
			dias[x][y].setIcon(GameInterface.trueflag);
			dias[x][y].setEnabled(false);
		}
		info.stopTimer();//暂停计时
		if(GameInterface.level == 3) {
			MainInterface.level4.setEnabled(true);
		}
		new Record(info.gettime(),GameInterface.level);
		//for(;!record;) {}//记录窗口关闭继续
		int jop = JOptionPane.showConfirmDialog(this,"Congratulations on your success！\nDo you want to continue？","you win!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,gamewinico);
		if(jop == JOptionPane.OK_OPTION) {
			replay();
		};
	}
	public void gameover(Diamonds dia) {//游戏失败处理
		gameover = true;//标志游戏结束
		for(int i = 0;i<height;i++) {
			for(int j = 0;j<width;j++) {
				if(dias[i][j].isflag&&dias[i][j].ismine) {//标雷正确
					dias[i][j].setIcon(GameInterface.trueflag);
				}else if(dias[i][j].isflag&&!dias[i][j].ismine) {//标雷错误
					dias[i][j].setIcon(GameInterface.falseflag);
				}else if(!dias[i][j].isflag&&dias[i][j].ismine) {//未标雷的显示
					dias[i][j].setIcon(GameInterface.bomb);
				}
				dias[i][j].setEnabled(false);
			}
		}
		info.stopTimer();//暂停计时
		dia.isbomber = true;
		dia.setBorder(BorderFactory.createLineBorder(Color.RED,3));//凸显引爆的雷
		int jop = JOptionPane.showConfirmDialog(this,"Continue to exert oneself！\nDo you want to continue？","game over!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,gameoverico);
		if(jop == JOptionPane.OK_OPTION) {
			replay();
		};
	}
	public int ensureDiamondsColor(int a) {//设置方块颜色,-1为随机
		Random random = new Random();
		if(a == -1) {
			a = random.nextInt(MainInterface.diacolornum);
		}
		return a;
	}
	public void creatDiamonds() {
		this.removeAll();
		this.setLayout(new GridLayout(height,width));
		this.endiacolor =  ensureDiamondsColor(diacolor);//确定方块背景颜色
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
	public void replay() {//重新开始游戏
		issetmine = false;//更新是否设雷标志
		gameover= false;//更新游戏结束标志
		info.renewMiner();//恢复雷数
		if(info.timer != null) {
			info.stopTimer();//回收计时器
		}
		info.renewTimer();//清零时间
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
			creatDiamonds();//重新创建方块
		}else if(level == 2) {
			GameInterface.level = 2;
			GameInterface.minenum = 40;
			GameInterface.width = 16;
			GameInterface.height = 16;
			creatDiamonds();//重新创建方块
		}else if(level == 3) {
			GameInterface.level = 3;
			GameInterface.minenum = 99;
			GameInterface.width = 30;
			GameInterface.height = 16;
			creatDiamonds();//重新创建方块
		}else if(level == 4) {
			GameInterface.level = 4;
			GameInterface.minenum = 200;
			GameInterface.width = 48;
			GameInterface.height = 22;
			creatDiamonds();//重新创建方块
		}
		info.updateFont();
		replay();
	}
}