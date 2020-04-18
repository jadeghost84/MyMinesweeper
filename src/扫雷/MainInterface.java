package 扫雷;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuShortcut;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.text.html.HTML;
public class MainInterface extends JFrame{
	private static final long serialVersionUID = 1L;
	public static Color bgcolor = new Color(34,139,34);
	public MainInterface mi= this;
	public GameInterface gi = null;
	public GameInfo info = null;
	public static boolean isdisplaykeeper = false;//排行榜显示标志
	public static Color[] diacolor= new Color[20];
	public static int diacolornum = 0;//背景颜色数
	public static MyMenuItem level4 = null;
	public MainInterface() {//构造函数
		super.setUndecorated(true);//去除默认边框
		this.setTitle("扫雷");
		this.setJMenuBar(addMenuBar());//添加菜单栏
		info = new GameInfo();
		gi = new GameInterface(info);
		new Drag(info,this);//信息栏添加拖动
		new Drag(gi,this);//游戏栏边框添加拖动
		this.setLayout(new BorderLayout());
		this.add(gi,BorderLayout.CENTER);
		this.add(info,BorderLayout.SOUTH);
		this.getContentPane().setBackground(Color.GRAY);
		this.pack();
		this.setMinimumSize(super.getSize());
		this.setResizable(false);
		this.setLocation(300,100);
		this.setVisible(true);
		this.addWindowListener((WindowListener) new WindowAdapter() {
			public void windowClosing(WindowEvent a) {
				System.exit(1);
			}
		});
	}
	public JMenuBar addMenuBar() {
		MyMenu game = new MyMenu("游戏(G)");
		MyMenu help = new MyMenu("帮助(H)");
		//JMenu游戏板块
		MyMenuItem newgame = new MyMenuItem("新游戏");
		MyMenu setlevel = new MyMenu("设置难度");
		MyMenu setdiacolor = new MyMenu("设置方块颜色");
		MyMenuItem recordkeeper = new MyMenuItem("英雄榜");
		MyMenuItem exit = new MyMenuItem("退出");
		addLevelSelect("低级",setlevel,1);
		addLevelSelect("中级",setlevel,2);
		addLevelSelect("高级",setlevel,3);
		addLevelSelect("炼狱",setlevel,4);
		addDiaBgColor("随机(默认)",setdiacolor,-1,Color.WHITE);
		addDiaBgColor("橄榄",setdiacolor,0,new Color(85,107,47));//addDiaBgColor(String name,JMenu father,int color_id,Color color)
		addDiaBgColor("黄金",setdiacolor,1,new Color(255,215,0));
		addDiaBgColor("岩石",setdiacolor,2,new Color(72,61,139));
		addDiaBgColor("兰花",setdiacolor,3,new Color(218,112,214));
		addDiaBgColor("卡其布",setdiacolor,4,new Color(240,230,140));
		game.add(newgame);
		game.addSeparator();
		game.add(recordkeeper);
		game.addSeparator();
		game.add(setlevel);
		game.add(setdiacolor);
		game.addSeparator();
		game.add(exit);
		game.setBackground(Color.RED);
		recordkeeper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!MainInterface.isdisplaykeeper) {
					new RecordKeeper();
				}
			}
		});
		newgame.addActionListener(new ActionListener() {//新游戏动作
			public void actionPerformed(ActionEvent arg0) {
				gi.replay();
				mi.pack();
				mi.setVisible(true);
			}	
		});
		exit.addActionListener(new ActionListener() {//退出游戏动作
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}	
		});
		//JMenu帮助板块
		MyMenuItem playhelp = new MyMenuItem("玩法帮助");
		MyMenuItem moregame = new MyMenuItem("更多游戏");
		MyMenuItem productinfo = new MyMenuItem("关于");
		help.add(playhelp);
		help.add(moregame);
		help.add(productinfo);
		playhelp.addActionListener(new ActionListener() {//游戏帮助动作
			public void actionPerformed(ActionEvent arg0) {
				playhelp();
			}	
		});
		moregame.addActionListener(new ActionListener() {//游戏帮助动作
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,"敬请期待","更多游戏",JOptionPane.INFORMATION_MESSAGE);
			}	
		});
		productinfo.addActionListener(new ActionListener() {//关于动作
			public void actionPerformed(ActionEvent arg0) {
				gameAbout();
			}	
		});
		MyMenuBar jmb = new MyMenuBar();
		addFrame(jmb,new MyMenu[] {game,help});//添加图标、退出框架
		new Drag(jmb,this);
		return jmb;
	}
	public void addLevelSelect(String name,MyMenu setlevel,int level_id) {
		MyMenuItem level = new MyMenuItem(name);
		setlevel.add(level);
		if(level_id == 4) {
			this.level4 = level;
			level.setEnabled(false);
			level.setToolTipText("需通关高级关卡，通关有彩蛋");
		}
		level.addActionListener(new ActionListener() {//关卡选择动作监听
			public void actionPerformed(ActionEvent arg0) {
				gi.levelSelect(level_id);
				mi.pack();
			}	
		});
	}
	public void addDiaBgColor(String name,MyMenu diacolorMenu,int diacolor_id,Color diacolor) {//添加颜色选项
		MyMenuItem color = new MyMenuItem(name);
		diacolorMenu.add(color);
		if(diacolor_id != -1) {
			MainInterface.diacolornum++;
			MainInterface.diacolor[diacolor_id] = diacolor;
		}
		color.addActionListener(new ActionListener() {//背景设置随机色动作
			public void actionPerformed(ActionEvent arg0) {
				GameInterface.diacolor = diacolor_id;
				gi.changeColorImmediately();
			}	
		});
	}
	public void addFrame(MyMenuBar jmb,MyMenu[] menu) {//添加图标、退出框架
		jmb.setLayout(new GridLayout(1,2));
		//充当菜单栏的JPanel
		MyMenuBar menubar = new MyMenuBar();
		menubar.setBackground(MainInterface.bgcolor);
		for(int i = 0;i<menu.length;i++) {
			menubar.add(menu[i]);
		}
		//退出、最小化等选项JPanel
		JPanel exit = new JPanel();
		int size = 18;
		exit.setBackground(MainInterface.bgcolor);
		exit.setLayout(new BorderLayout());
		ImageIcon ico = new ImageIcon(MainInterface.class.getResource("image/quit.png"));
		ico.setImage(ico.getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT));
		JLabel exitbutton= new JLabel("",ico,JLabel.LEFT);
		exitbutton.addMouseListener(new MouseAdapter() {
			Border border = BorderFactory.createLineBorder(Color.RED,2);//直线边框
			public void mouseClicked(MouseEvent e){
				System.exit(1);
			}
			public void mouseEntered(MouseEvent e) {
				exitbutton.setBorder(border);
			}
			public void mouseExited(MouseEvent e) {
				exitbutton.setBorder(null);
			}
		});
		exit.add(exitbutton,BorderLayout.EAST);
		jmb.add(menubar);
		jmb.add(exit);
	}
	public static Color getdiaColor(int a) {//根据数字返回颜色
		return MainInterface.diacolor[a];
	}
	public void playhelp() {
		String html = 
				"<html>" + 
				"<body bgcolor=yellow>" + 
				"<p><b style = color:blue>扫雷：规则和基本要求</b></p>" + 
				"<p>&nbsp;&nbsp;<b style = color:blue>游戏目标</b></p>" + 
				"<p>&nbsp;&nbsp;找出空方块，同时避免触雷。清除扫雷区的速度越快，得分就越高。</p>" + 
				"<p>&nbsp;&nbsp;<b style = color:blue>扫雷区</b></p>" + 
				"<p>&nbsp;&nbsp;扫雷有三个标准扫雷区可供选择，各扫雷区的扫雷难度依次递增。</p>" + 
				"<ul>" + 
				"	<li><b style = color:blue>初级：</b>9*9=81 个方块、10 个雷</li>" + 
				"	<li><b style = color:blue>中级：</b>16*16=256 个方块、40 个雷</li>" + 
				"	<li><b style = color:blue>高级：</b>16*30=480 个方块、99 个雷</li>" + 
				"	<li><b style = color:blue>炼狱：</b>30*30=900 个方块、200个雷<b style = color:red>注：完成高级解锁，且通关有彩蛋</b></li>" + 
				"</ul>" + 
				"<p><b style = color:blue>玩法</b></p>" + 
				"<ul>" + 
				"	<li>挖开地雷，游戏即告结束。</li>" + 
				"	<li>挖开空方块，可以继续玩。</li>" + 
				"	<li>挖开数字，则表示在其周围的八个方块中共有多少个雷，可以使用该信息推断能够安全单击附近的哪些方块。</li>" + 
				"</ul>" + 
				"<p><b style = color:blue>提示与技巧</b></p>" + 
				"<ul>" + 
				"	<li><b style = color:blue>标记地雷。</b> 如果您认为某个方块可能藏有地雷，请右键单击它。这会在该方块上做一个旗标。（如果不确定，请再次右键单击标记为问号。）</li>" + 
				"	<li><b style = color:blue>研究图案。</b> 如果一行中有三个方块显示为 2-3-2，您就会知道该行旁边可能排列着三个雷。如果一个方块显示为 8，则它周围的每个方块下面都有一个雷。</li>" + 
				"	<li><b style = color:blue>浏览未探测的。</b>如果不确定下一个单击位置，可以尝试清除某些未探测的区域。在未标记方块的中间单击比在可能有雷的区域单击要好一些。</lI>" + 
				"<ul>" + 
				"</body>" + 
				"</html>";
		JLabel lab = new JLabel(html);
		JButton exit = new JButton("我知道了");
		exit.setForeground(Color.BLACK);
		exit.setBackground(Color.YELLOW);
		JFrame playhelp = new JFrame();
		new Drag(lab,playhelp);
		playhelp.setUndecorated(true);
		playhelp.setLayout(new BorderLayout());
		playhelp.add(lab,BorderLayout.CENTER);
		playhelp.add(exit,BorderLayout.SOUTH);
		playhelp.setLocation(500,20);
		playhelp.setSize(500,510);
		playhelp.setVisible(true);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playhelp.dispose();
			}			
		});
		exit.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				exit.setBackground(Color.RED);
			}
			public void mouseExited(MouseEvent e) {
				exit.setBackground(Color.YELLOW);
			}
		});
	}
	public void gameAbout() {
		String html = "<html>"
				+ "<body bgcolor=yellow>"
				+ "<br/></br>"
				+ "<h3>&nbsp;&nbsp;本程序为兴趣实现，不做任何商业用途。&nbsp;&nbsp;</h3>"
				+ "<h3>&nbsp;&nbsp;若有疑问或问题，可拨打下方联系电话。&nbsp;&nbsp;</h3>"
				+ "<h3 align=right>编程人员:ZZM</h3>"
				+ "<h3 align=right>联系电话:18407838900</h3>"
				+ "</body></html>";
		JLabel lab = new JLabel(html);
		JButton exit = new JButton("我知道了");
		exit.setForeground(Color.BLACK);
		exit.setBackground(Color.YELLOW);
		JFrame about = new JFrame();
		new Drag(lab,about);
		about.setUndecorated(true);
		about.setLayout(new BorderLayout());
		about.add(lab,BorderLayout.CENTER);
		about.add(exit,BorderLayout.SOUTH);
		about.setLocation(500,220);
		about.pack();
		about.setVisible(true);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about.dispose();
			}			
		});
		exit.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				exit.setBackground(Color.RED);
			}
			public void mouseExited(MouseEvent e) {
				exit.setBackground(Color.YELLOW);
			}
		});
	}
}
/*class MyKeyListener implements KeyListener{//键盘事件类
	public JButton but = null;
	public MyMenu menu = null;
	public MyMenuItem item = null;
	public int typeId = 0;
	public int key = 0;
	public MyKeyListener(JButton but,int key) {//构造函数
		this.but = but;
		this.key = key;
		this.typeId = 1;
		but.addKeyListener(this);
	}
	public MyKeyListener(MyMenuItem item,int key) {//构造函数
		this.item = item;
		this.key = key;
		this.typeId = 2;
		item.addKeyListener(this);
	}
	public MyKeyListener(MyMenu menu,int key) {//构造函数
		this.menu = menu;
		this.key = key;
		this.typeId = 3;
		menu.addKeyListener(this);
	}
	public void keyPressed(KeyEvent e) {//某键按下时
		System.out.println("sdasd");
		if(e.getKeyCode() == key&&typeId == 1) {
			but.doClick();
		}else if(e.getKeyCode() == key&&typeId == 2) {
			item.doClick();
		}else if(e.getKeyCode() == key&&typeId == 3) {
			menu.setFocusable(true);
		}
	}
	public void keyReleased(KeyEvent e) {//某键释放时
		System.out.println("sdasd");
	}
	public void keyTyped(KeyEvent e) {//键入某键时
		System.out.println("sdasd");
	}
}*/	
