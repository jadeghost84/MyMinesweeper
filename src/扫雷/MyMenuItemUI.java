package 扫雷;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ButtonModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.plaf.basic.BasicMenuItemUI;

public class MyMenuItemUI extends BasicMenuItemUI{
	protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
        ButtonModel model = menuItem.getModel();
        Color oldColor = g.getColor();
        int menuWidth = menuItem.getWidth();
        int menuHeight = menuItem.getHeight();
        if(menuItem.isOpaque()) {
            if (model.isArmed()|| (menuItem instanceof JMenu && model.isSelected())) {
                g.setColor(MyMenuBar.menuFocusColor);//改动
                g.fillRect(0,0, menuWidth, menuHeight);
            } else {
            	g.setColor(Color.WHITE);//g.setColor(menuItem.getBackground());改动
                g.fillRect(0,0, menuWidth, menuHeight);
            }
            g.setColor(oldColor);
        }
        else if (model.isArmed() || (menuItem instanceof JMenu && model.isSelected())) {
            g.setColor(MainInterface.bgcolor);//改动
            g.fillRect(0,0, menuWidth, menuHeight);
            g.setColor(oldColor);
        }
	}
}
