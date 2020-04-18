package 扫雷;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JComponent;

class Drag{//拖动类
	Point loc = null;
	Point tmp = null;
	boolean isDragged = false;
	JComponent com = null;
	Window window = null;
	public Drag(JComponent com,Window frame) {//构造函数
		this.com = com;
		this.window = frame;
		setDragable();
	}
	public void setDragable() {//JMenu添加拖动
		com.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				isDragged = false;
				window.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			public void mousePressed(java.awt.event.MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				window.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});
		com.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				if(isDragged) {
					loc = new Point(window.getLocation().x + e.getX() - tmp.x,window.getLocation().y + e.getY() - tmp.y);
					window.setLocation(loc);
				}
			}
		});
	}
}
