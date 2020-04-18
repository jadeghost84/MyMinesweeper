package 扫雷;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
public class Record extends JDialog{
	private static final long serialVersionUID = 1L;
	public static String UserSeparator = "<Z@Z@M>";
	public static String InfoSeparator = "<z@z@m>";
	public Record own= null;//自身引用
	public String user_name = null;//记录者
	public int record = 0;//所用时间记录
	public int level = -1;//通关难度，1为初级，2为中级，3为高级,4 为炼狱，0为自定义
	public boolean user_defined = false;//是否难度用户自定义
	public Record(int record,int level){//构造函数
		super.setModal(true);
		this.record = record;
		this.level = level;
		this.own = this;
		setInterface();
		this.setUndecorated(true);//去除默认边框
		this.setSize(200,270);
		this.setLocation(400,200);
		this.setVisible(true);
	}
	public void write() throws Exception{
		File f = null;
		f = new File(Record.class.getResource("document//record"+level+".txt").toURI());
		String str = "";
		if(f.exists()) {
			if(f.length()>0) {
				str+= Record.UserSeparator;
			}
			str += user_name+Record.InfoSeparator+record;//写入名字和成绩
			byte b[] = new byte[100];//100个字节
			b = str.getBytes();
			OutputStream output = new FileOutputStream(f,true);
			output.write(b);
			output.close();
		}else {
			System.out.println("记录文件未找到！QAQ");
		}
	}
	public void setInterface() {
		Container cont = this.getContentPane();//得到窗体容器
		JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);//设置语言切换选项卡
		new Drag(jtp,this);
		JPanel pan1 = panel(1);
		JPanel pan2 = panel(2);
		jtp.addTab("中文",pan1);
		jtp.addTab("English",pan2);
		cont.add(jtp);
	}
	public JPanel panel(int language) {//实现主体界面
		String labelStr = null;
		String sureStr = null;
		String cancelStr = null;
		if(language == 2) {//英文标签
			labelStr = "<html>"
					+ "<body style = font-size:18px >"
					+ "<p margin = 10px align = 'center' color=red style = font-family:'方正舒体'>You Are So Great!</p>"
					+ "<br/>"
					+ "<p align = 'center' color=blue style = font-family:'Stencil'>Can You</p>"
					+ "<p align = 'center' color=blue style = font-family:'Stencil'>Tell Me</p>"
					+ "<p align = 'center' color=blue style = font-family:'Stencil'>Your Name?</p>"
					+ "</body>"
					+ "</html>";
			sureStr = "OK";
			cancelStr = "NO";
		}else if(language == 1) {//中文标签
			labelStr = "<html>"
					+ "<body style = font-size:18px >"
					+ "<p margin = 10px align = 'center' color=red style = font-family:'方正舒体'>你好棒啊!</p>"
					+ "<br/>"
					+ "<p align = 'center' color=blue style = font-family:'华文行楷'>我可以知道</p>"
					+ "<p align = 'center' color=blue style = font-family:'华文行楷'>你的名字吗?</p>"
					+ "</body>"
					+ "</html>";
			sureStr = "好的";
			cancelStr = "不要";
		}
		JPanel pan = new JPanel();
		JLabel lab = new JLabel(labelStr);
		pan.setBackground(Color.PINK);
		JTextField name = new JTextField();
		JButton sure = new JButton(sureStr);
		JButton cancel = new JButton(cancelStr);
		pan.setLayout(null);
		lab.setBounds(0,0,200,150);
		name.setBounds(10,150,180,30);
		sure.setBounds(10,200,80,30);
		cancel.setBounds(110,200,80,30);
		pan.add(lab);
		pan.add(name);
		pan.add(sure);
		pan.add(cancel);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				own.dispose();
			}
		});
		sure.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					if("".equals(name.getText())) {
						name.setText("无名英雄");
					}
					user_name = name.getText();
					write();
					own.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		return pan;
	}
}
