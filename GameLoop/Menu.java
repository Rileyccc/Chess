package GameLoop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;





public class Menu extends JLabel { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean state;
	
	public Menu(int w, int h){
		super("CHESS");
	
		setIcon(new ImageIcon(Menu.class.getClassLoader()
			      .getResource("ImagesAndProcessing/ChessMenuPicture.jpg")));
		
		setPreferredSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w,h));
		setMinimumSize(new Dimension(w,h));
		
		
		
		updateUI();
		Font f1 = new Font("#PilGi", Font.BOLD, 150);
		
		Font f2 = new Font("#PilGi", Font.BOLD, 20 );
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.TOP);
		setFont(f1);
		
		

		JButton play = new JButton("2 Player");
		play.setFont(f2);
		play.setBackground(Color.white);
		play.addActionListener(e -> {state = true;});
		play.setBorderPainted(false);
		
		
		JButton playComputer = new JButton("Player V Computer");
		playComputer.setFont(f2);
		playComputer.setBackground(Color.white);
		playComputer.addActionListener(e -> {
			state = true;
			GameChecks.COMPUTER = true;
		});
		playComputer.setBorderPainted(false);
		
		play.setSize(100,100);
		play.setBounds(200,300,400,75);
		
		
		playComputer.setSize(100,100);
		playComputer.setBounds(200, 380, 400, 75);
		
		add(playComputer);
		add(play);
	
	
		
		
	}
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
		
	}
	
	

}
