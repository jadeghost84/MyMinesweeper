package ɨ��;
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
	public Record own= null;//��������
	public String user_name = null;//��¼��
	public int record = 0;//����ʱ���¼
	public int level = -1;//ͨ���Ѷȣ�1Ϊ������2Ϊ�м���3Ϊ�߼�,4 Ϊ������0Ϊ�Զ���
	public boolean user_defined = false;//�Ƿ��Ѷ��û��Զ���
	public Record(int record,int level){//���캯��
		super.setModal(true);
		this.record = record;
		this.level = level;
		this.own = this;
		setInterface();
		this.setUndecorated(true);//ȥ��Ĭ�ϱ߿�
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
			str += user_name+Record.InfoSeparator+record;//д�����ֺͳɼ�
			byte b[] = new byte[100];//100���ֽ�
			b = str.getBytes();
			OutputStream output = new FileOutputStream(f,true);
			output.write(b);
			output.close();
		}else {
			System.out.println("��¼�ļ�δ�ҵ���QAQ");
		}
	}
	public void setInterface() {
		Container cont = this.getContentPane();//�õ���������
		JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);//���������л�ѡ�
		new Drag(jtp,this);
		JPanel pan1 = panel(1);
		JPanel pan2 = panel(2);
		jtp.addTab("����",pan1);
		jtp.addTab("English",pan2);
		cont.add(jtp);
	}
	public JPanel panel(int language) {//ʵ���������
		String labelStr = null;
		String sureStr = null;
		String cancelStr = null;
		if(language == 2) {//Ӣ�ı�ǩ
			labelStr = "<html>"
					+ "<body style = font-size:18px >"
					+ "<p margin = 10px align = 'center' color=red style = font-family:'��������'>You Are So Great!</p>"
					+ "<br/>"
					+ "<p align = 'center' color=blue style = font-family:'Stencil'>Can You</p>"
					+ "<p align = 'center' color=blue style = font-family:'Stencil'>Tell Me</p>"
					+ "<p align = 'center' color=blue style = font-family:'Stencil'>Your Name?</p>"
					+ "</body>"
					+ "</html>";
			sureStr = "OK";
			cancelStr = "NO";
		}else if(language == 1) {//���ı�ǩ
			labelStr = "<html>"
					+ "<body style = font-size:18px >"
					+ "<p margin = 10px align = 'center' color=red style = font-family:'��������'>��ð���!</p>"
					+ "<br/>"
					+ "<p align = 'center' color=blue style = font-family:'�����п�'>�ҿ���֪��</p>"
					+ "<p align = 'center' color=blue style = font-family:'�����п�'>���������?</p>"
					+ "</body>"
					+ "</html>";
			sureStr = "�õ�";
			cancelStr = "��Ҫ";
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
						name.setText("����Ӣ��");
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
