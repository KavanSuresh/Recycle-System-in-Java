package controller;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Preethi Yellappa
 * 
 */
public class JLabelPlastic extends JLabel{
	
	private static final long serialVersionUID = 1L;
	private Plastic plastic;
 
	public JLabelPlastic(Plastic a){
		setIcon(new ImageIcon("C:\\xampp\\htdocs\\Images\\plastic.jpg"));
		this.setPlastic(a);
		
	}
	
 
	public void setPlastic(Plastic plastic) {
		this.plastic = plastic;
	}
 
	public Plastic getPlastic() {
		return plastic;
	}
}