package controller;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Preethi Yellappa
 * 
 * @class JLabelCoke
 * This contains the image for coke object
 * 
 */
public class JLabelCoke extends JLabel{
	
	private static final long serialVersionUID = 1L;
	private Coke coke;
 
	public JLabelCoke(Coke a){
		setIcon(new ImageIcon("C:\\xampp\\htdocs\\Images\\coke.jpg"));
		this.setCoke(a);
		
	}
	
 
	public void setCoke(Coke coke) {
		this.coke = coke;
	}
 
	public Coke getCoke() {
		return coke;
	}
}