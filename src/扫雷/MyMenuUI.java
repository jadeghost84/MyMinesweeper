package 扫雷;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ButtonModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicMenuUI;

public class MyMenuUI extends BasicMenuUI{
	protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
        ButtonModel model = menuItem.getModel();
        Color oldColor = g.getColor();
        int menuWidth = menuItem.getWidth();
        int menuHeight = menuItem.getHeight();
        if(menuItem.isOpaque()) {
            if (model.isArmed()|| (menuItem instanceof JMenu && model.isSelected())) {
                g.setColor(Color.WHITE);//改动
                g.fillRect(0,0, menuWidth, menuHeight);
            } else {
            	g.setColor(Color.WHITE);//g.setColor(menuItem.getBackground());改动
                g.fillRect(0,0, menuWidth, menuHeight);
            }
            g.setColor(oldColor);
        }
        else if (model.isArmed() || (menuItem instanceof JMenu && model.isSelected())) {
            g.setColor(MyMenuBar.menuFocusColor);//改动
            g.fillRect(0,0, menuWidth, menuHeight);
            g.setColor(oldColor);
        }
	}
}
