package 扫雷;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
public class Diamonds extends JButton{
	private static final long serialVersionUID = 1L;
	public Font numstyle = new Font("numstyle",Font.BOLD,15);
	public boolean ismine = false;//是否为雷
	public boolean isdoclick = false;//是否被点击
	public boolean isflag = false;//是否设旗标志
	public boolean isbomber = false;//是否引爆者
	public int roundminesum = 0;//周围雷数
	public static BevelBorder border = null;
	public static int size = 20;
	public static int doclicknum = 0;
	public Diamonds(int diacolor) {
		super.setUI(new MyButtonUI());
		border = new BevelBorder(BevelBorder.RAISED);
	//	border = BorderFactory.createRaisedBevelBorder();//凸起边框
		this.setPreferredSize(new Dimension(size,size));
		this.setFont(numstyle);
		this.setBackground(MainInterface.getdiaColor(diacolor));
		this.setBorder(border);
	}
	public void setMine(boolean mine) {//设置是否为雷
		this.ismine = mine;
	}
	public boolean getMine() {//获知是否为雷
		return this.ismine;
	}
	public void setDoclick(boolean click) {//设置是否已点击
		this.isdoclick = click;
	}
	public boolean getDoclick() {//获知是否已点击
		return this.isdoclick;
	}
	public void setRoundMineSum(int num) {//设置周围雷数
		if(num>=0||num<=8) {
			 this.roundminesum = num;
		}
	}
	public void coundRoundMineSum() {//周围雷数累加1
		this.roundminesum++;
	}
	public int getRoundMineSum() {//获得周围雷数
		return this.roundminesum;
	}
	public void doclickEvent() {//按钮被点击触发事件
		this.setBackground(new Color(173,216,230));
		Border border = BorderFactory.createLoweredBevelBorder();//凹陷边框
		this.setBorder(border);
		this.setDoclick(true);
		this.setEnabled(false);
		Diamonds.doclicknum++;
	}
	public void showText() {//按钮文本显示
		if(this.roundminesum == 1) {
			this.setForeground(new Color(0,100,0));//深绿色
		}
		if(this.roundminesum == 2) {
			this.setForeground(new Color(139,0,0));//深红色
		}
		if(this.roundminesum == 3) {
			this.setForeground(new Color(0,0,139));//深蓝色
		}
		if(this.roundminesum == 4) {
			this.setForeground(new Color(210,105,30));//巧克力
		}
		if(this.roundminesum == 5) {
			this.setForeground(new Color(165,42,42));//棕色
		}
		if(this.roundminesum == 6) {
			this.setForeground(new Color(188,143,143));//玫瑰棕色
		}
		if(this.roundminesum == 7) {
			this.setForeground(new Color(148,0,211));//深紫罗兰色
		}
		if(this.roundminesum == 8) {
			this.setForeground(Color.BLACK);//纯黑色
		}
		this.setText(Integer.toString(this.roundminesum));
	}
	public void reNew(int diacolor) {
		this.isdoclick = false;//被点击置否
		this.setEnabled(true);//按钮回归可点击
		this.setMine(false);//雷标志置否
		this.isflag = false;//红旗标志置否
		this.isbomber = false;//引爆者标志置否
		Diamonds.doclicknum = 0;//置0被点击数
		this.setBorder(border);
		this.setRoundMineSum(0);
		this.setBackground(MainInterface.getdiaColor(diacolor));
		this.setText(null);
		this.setIcon(null);
	}
}
