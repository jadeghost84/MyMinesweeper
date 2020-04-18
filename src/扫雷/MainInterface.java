package ɨ��;
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
	public static boolean isdisplaykeeper = false;//���а���ʾ��־
	public static Color[] diacolor= new Color[20];
	public static int diacolornum = 0;//������ɫ��
	public static MyMenuItem level4 = null;
	public MainInterface() {//���캯��
		super.setUndecorated(true);//ȥ��Ĭ�ϱ߿�
		this.setTitle("ɨ��");
		this.setJMenuBar(addMenuBar());//��Ӳ˵���
		info = new GameInfo();
		gi = new GameInterface(info);
		new Drag(info,this);//��Ϣ������϶�
		new Drag(gi,this);//��Ϸ���߿�����϶�
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
		MyMenu game = new MyMenu("��Ϸ(G)");
		MyMenu help = new MyMenu("����(H)");
		//JMenu��Ϸ���
		MyMenuItem newgame = new MyMenuItem("����Ϸ");
		MyMenu setlevel = new MyMenu("�����Ѷ�");
		MyMenu setdiacolor = new MyMenu("���÷�����ɫ");
		MyMenuItem recordkeeper = new MyMenuItem("Ӣ�۰�");
		MyMenuItem exit = new MyMenuItem("�˳�");
		addLevelSelect("�ͼ�",setlevel,1);
		addLevelSelect("�м�",setlevel,2);
		addLevelSelect("�߼�",setlevel,3);
		addLevelSelect("����",setlevel,4);
		addDiaBgColor("���(Ĭ��)",setdiacolor,-1,Color.WHITE);
		addDiaBgColor("���",setdiacolor,0,new Color(85,107,47));//addDiaBgColor(String name,JMenu father,int color_id,Color color)
		addDiaBgColor("�ƽ�",setdiacolor,1,new Color(255,215,0));
		addDiaBgColor("��ʯ",setdiacolor,2,new Color(72,61,139));
		addDiaBgColor("����",setdiacolor,3,new Color(218,112,214));
		addDiaBgColor("���䲼",setdiacolor,4,new Color(240,230,140));
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
		newgame.addActionListener(new ActionListener() {//����Ϸ����
			public void actionPerformed(ActionEvent arg0) {
				gi.replay();
				mi.pack();
				mi.setVisible(true);
			}	
		});
		exit.addActionListener(new ActionListener() {//�˳���Ϸ����
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}	
		});
		//JMenu�������
		MyMenuItem playhelp = new MyMenuItem("�淨����");
		MyMenuItem moregame = new MyMenuItem("������Ϸ");
		MyMenuItem productinfo = new MyMenuItem("����");
		help.add(playhelp);
		help.add(moregame);
		help.add(productinfo);
		playhelp.addActionListener(new ActionListener() {//��Ϸ��������
			public void actionPerformed(ActionEvent arg0) {
				playhelp();
			}	
		});
		moregame.addActionListener(new ActionListener() {//��Ϸ��������
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,"�����ڴ�","������Ϸ",JOptionPane.INFORMATION_MESSAGE);
			}	
		});
		productinfo.addActionListener(new ActionListener() {//���ڶ���
			public void actionPerformed(ActionEvent arg0) {
				gameAbout();
			}	
		});
		MyMenuBar jmb = new MyMenuBar();
		addFrame(jmb,new MyMenu[] {game,help});//���ͼ�ꡢ�˳����
		new Drag(jmb,this);
		return jmb;
	}
	public void addLevelSelect(String name,MyMenu setlevel,int level_id) {
		MyMenuItem level = new MyMenuItem(name);
		setlevel.add(level);
		if(level_id == 4) {
			this.level4 = level;
			level.setEnabled(false);
			level.setToolTipText("��ͨ�ظ߼��ؿ���ͨ���вʵ�");
		}
		level.addActionListener(new ActionListener() {//�ؿ�ѡ��������
			public void actionPerformed(ActionEvent arg0) {
				gi.levelSelect(level_id);
				mi.pack();
			}	
		});
	}
	public void addDiaBgColor(String name,MyMenu diacolorMenu,int diacolor_id,Color diacolor) {//�����ɫѡ��
		MyMenuItem color = new MyMenuItem(name);
		diacolorMenu.add(color);
		if(diacolor_id != -1) {
			MainInterface.diacolornum++;
			MainInterface.diacolor[diacolor_id] = diacolor;
		}
		color.addActionListener(new ActionListener() {//�����������ɫ����
			public void actionPerformed(ActionEvent arg0) {
				GameInterface.diacolor = diacolor_id;
				gi.changeColorImmediately();
			}	
		});
	}
	public void addFrame(MyMenuBar jmb,MyMenu[] menu) {//���ͼ�ꡢ�˳����
		jmb.setLayout(new GridLayout(1,2));
		//�䵱�˵�����JPanel
		MyMenuBar menubar = new MyMenuBar();
		menubar.setBackground(MainInterface.bgcolor);
		for(int i = 0;i<menu.length;i++) {
			menubar.add(menu[i]);
		}
		//�˳�����С����ѡ��JPanel
		JPanel exit = new JPanel();
		int size = 18;
		exit.setBackground(MainInterface.bgcolor);
		exit.setLayout(new BorderLayout());
		ImageIcon ico = new ImageIcon(MainInterface.class.getResource("image/quit.png"));
		ico.setImage(ico.getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT));
		JLabel exitbutton= new JLabel("",ico,JLabel.LEFT);
		exitbutton.addMouseListener(new MouseAdapter() {
			Border border = BorderFactory.createLineBorder(Color.RED,2);//ֱ�߱߿�
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
	public static Color getdiaColor(int a) {//�������ַ�����ɫ
		return MainInterface.diacolor[a];
	}
	public void playhelp() {
		String html = 
				"<html>" + 
				"<body bgcolor=yellow>" + 
				"<p><b style = color:blue>ɨ�ף�����ͻ���Ҫ��</b></p>" + 
				"<p>&nbsp;&nbsp;<b style = color:blue>��ϷĿ��</b></p>" + 
				"<p>&nbsp;&nbsp;�ҳ��շ��飬ͬʱ���ⴥ�ס����ɨ�������ٶ�Խ�죬�÷־�Խ�ߡ�</p>" + 
				"<p>&nbsp;&nbsp;<b style = color:blue>ɨ����</b></p>" + 
				"<p>&nbsp;&nbsp;ɨ����������׼ɨ�����ɹ�ѡ�񣬸�ɨ������ɨ���Ѷ����ε�����</p>" + 
				"<ul>" + 
				"	<li><b style = color:blue>������</b>9*9=81 �����顢10 ����</li>" + 
				"	<li><b style = color:blue>�м���</b>16*16=256 �����顢40 ����</li>" + 
				"	<li><b style = color:blue>�߼���</b>16*30=480 �����顢99 ����</li>" + 
				"	<li><b style = color:blue>������</b>30*30=900 �����顢200����<b style = color:red>ע����ɸ߼���������ͨ���вʵ�</b></li>" + 
				"</ul>" + 
				"<p><b style = color:blue>�淨</b></p>" + 
				"<ul>" + 
				"	<li>�ڿ����ף���Ϸ���������</li>" + 
				"	<li>�ڿ��շ��飬���Լ����档</li>" + 
				"	<li>�ڿ����֣����ʾ������Χ�İ˸������й��ж��ٸ��ף�����ʹ�ø���Ϣ�ƶ��ܹ���ȫ������������Щ���顣</li>" + 
				"</ul>" + 
				"<p><b style = color:blue>��ʾ�뼼��</b></p>" + 
				"<ul>" + 
				"	<li><b style = color:blue>��ǵ��ס�</b> �������Ϊĳ��������ܲ��е��ף����Ҽ�������������ڸ÷�������һ����ꡣ�������ȷ�������ٴ��Ҽ��������Ϊ�ʺš���</li>" + 
				"	<li><b style = color:blue>�о�ͼ����</b> ���һ����������������ʾΪ 2-3-2�����ͻ�֪�������Ա߿��������������ס����һ��������ʾΪ 8��������Χ��ÿ���������涼��һ���ס�</li>" + 
				"	<li><b style = color:blue>���δ̽��ġ�</b>�����ȷ����һ������λ�ã����Գ������ĳЩδ̽���������δ��Ƿ�����м䵥�����ڿ������׵����򵥻�Ҫ��һЩ��</lI>" + 
				"<ul>" + 
				"</body>" + 
				"</html>";
		JLabel lab = new JLabel(html);
		JButton exit = new JButton("��֪����");
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
				+ "<h3>&nbsp;&nbsp;������Ϊ��Ȥʵ�֣������κ���ҵ��;��&nbsp;&nbsp;</h3>"
				+ "<h3>&nbsp;&nbsp;�������ʻ����⣬�ɲ����·���ϵ�绰��&nbsp;&nbsp;</h3>"
				+ "<h3 align=right>�����Ա:ZZM</h3>"
				+ "<h3 align=right>��ϵ�绰:18407838900</h3>"
				+ "</body></html>";
		JLabel lab = new JLabel(html);
		JButton exit = new JButton("��֪����");
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
/*class MyKeyListener implements KeyListener{//�����¼���
	public JButton but = null;
	public MyMenu menu = null;
	public MyMenuItem item = null;
	public int typeId = 0;
	public int key = 0;
	public MyKeyListener(JButton but,int key) {//���캯��
		this.but = but;
		this.key = key;
		this.typeId = 1;
		but.addKeyListener(this);
	}
	public MyKeyListener(MyMenuItem item,int key) {//���캯��
		this.item = item;
		this.key = key;
		this.typeId = 2;
		item.addKeyListener(this);
	}
	public MyKeyListener(MyMenu menu,int key) {//���캯��
		this.menu = menu;
		this.key = key;
		this.typeId = 3;
		menu.addKeyListener(this);
	}
	public void keyPressed(KeyEvent e) {//ĳ������ʱ
		System.out.println("sdasd");
		if(e.getKeyCode() == key&&typeId == 1) {
			but.doClick();
		}else if(e.getKeyCode() == key&&typeId == 2) {
			item.doClick();
		}else if(e.getKeyCode() == key&&typeId == 3) {
			menu.setFocusable(true);
		}
	}
	public void keyReleased(KeyEvent e) {//ĳ���ͷ�ʱ
		System.out.println("sdasd");
	}
	public void keyTyped(KeyEvent e) {//����ĳ��ʱ
		System.out.println("sdasd");
	}
}*/	
