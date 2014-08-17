package controller;

import javax.swing.JPanel;


import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JTextArea;

/**
 * @author Preethi Yellappa
 * This contains the skeleton for coupon
 */

public class Coupon extends JPanel{
	public Coupon(String printMoney) {
		
		setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 128, 0)));
		setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel header = new JPanel();
		header.setBackground(new Color(255, 255, 255));
		add(header);
		header.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon("C:\\xampp\\htdocs\\Images\\couponImage.png"));
		header.add(imageLabel);
		
		JTextArea couponTextArea = new JTextArea();
		add(couponTextArea);
		couponTextArea.setText(printMoney);
	}

}
