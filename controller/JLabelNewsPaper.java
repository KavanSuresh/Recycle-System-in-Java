package controller;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Preethi Yellappa
 * 
 */
public class JLabelNewsPaper extends JLabel{

	private static final long serialVersionUID = 1L;
	private NewsPaper newsPaper;
	
	public JLabelNewsPaper(NewsPaper a){
		setIcon(new ImageIcon("C:\\xampp\\htdocs\\Images\\newsPaper.jpg"));
		this.setNewsPaper(a);
	}
	
	public void setNewsPaper(NewsPaper newsPaper) {
		this.newsPaper = newsPaper;
	}
	
	public NewsPaper getNewsPaper() {
		return newsPaper;
	}

}
