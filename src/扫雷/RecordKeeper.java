package ɨ��;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class RecordKeeper extends JFrame{
	private static final long serialVersionUID = 1L;
	public RecordKeeper() {
		this.setUndecorated(true);
		this.add(mainPane());
		this.setLocation(400,200);
		this.setSize(400,500);
		this.setVisible(true);
	}
	public JPanel mainPane() {
		JPanel pan = new JPanel();
		JTabbedPane jtp = new JTabbedPane();
		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();
		JPanel pan3 = new JPanel();
		JPanel pan4 = new JPanel();
		jtp.add(pan1,"����");
		jtp.add(pan2,"�м�");
		jtp.add(pan3,"�߼�");
		jtp.add(pan4,"����");
		
		return pan;
	}
}
