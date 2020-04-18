package É¨À×;

import javax.swing.JMenuItem;

public class MyMenuItem extends JMenuItem{
	public MyMenuItem(String name) {
		super.setText(name);
		super.setUI(new MyMenuItemUI());
	}
}
