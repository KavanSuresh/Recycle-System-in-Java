package controller;

/**
 * @author Preethi Yellappa
 * 
 */

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelGlass extends JLabel{
	
	private static final long serialVersionUID = 1L;
	private Glass glass;
 
	public JLabelGlass(Glass a){
		setIcon(new ImageIcon("C:\\xampp\\htdocs\\Images\\glass.jpg"));
		this.setGlass(a);
		
	}
	
 
	public void setGlass(Glass glass) {
		this.glass = glass;
	}
 
	public Glass getGlass() {
		return glass;
	}
}