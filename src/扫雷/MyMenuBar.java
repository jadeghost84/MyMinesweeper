package 扫雷;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.JMenuBar;
import javax.swing.border.MatteBorder;
public class MyMenuBar extends JMenuBar{
	private static final long serialVersionUID = 1L;
	public static Color menuFocusColor = new Color(255,218,185);//菜单选项被停留时颜色
	boolean isDragged = false;
	public MyMenuBar() {
		Insets insets = new Insets(0,0,0,0);
		MatteBorder mb = new MatteBorder(insets,MainInterface.bgcolor);
		this.setBorder(mb);
	
	}
}