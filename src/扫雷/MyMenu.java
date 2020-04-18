package É¨À×;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JMenu;

public class MyMenu extends JMenu{
	public MyMenu(String name) {
		Font font = new Font(Font.SERIF,Font.BOLD,12);
		this.setFont(font);
		this.setText(name);
		this.setMargin(new Insets(0,0,0,0));
		this.setUI(new MyMenuUI());
	}
}
