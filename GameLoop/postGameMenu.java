package GameLoop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

public class postGameMenu extends JLabel {
	private boolean state;
	public postGameMenu(int w, int h, String msg){
		super(msg);
		
		setPreferredSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w,h));
		setMinimumSize(new Dimension(w,h));
		setBackground(Color.DARK_GRAY);
		
		setOpaque(true);
		System.out.println(getBackground());
		
		updateUI();
		Font f1 = new Font("#PilGi", Font.BOLD, 140);
		Font f2 = new Font("#PilGi", Font.BOLD, 20);
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.TOP);
		setFont(f1);

		JButton play = new JButton("Back To Menu");
		play.setFont(f2);
		play.setBackground(Color.white);
		play.addActionListener(e -> {state = true;});
		
		
		play.setSize(100,100);
		play.setBounds(300,300,200,100);
		
		add(play);
		setVisible(true);
		
	}
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
		
	}
	

}
