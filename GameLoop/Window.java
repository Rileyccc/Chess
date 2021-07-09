package GameLoop;

import java.awt.Dimension;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

public class Window {
	Menu menu;
	postGameMenu postGame;
	Game game;
	boolean back = false;
	//CONSTRUCTOR
	public Window(int w, int h, String title) {
		menu = new Menu(w,h);
		
		JFrame frame = new JFrame(title);
		
		frame.add(menu);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	
		while(true) {	
			game = new Game();
			game.setPreferredSize(new Dimension(w, h));
			game.setMaximumSize(new Dimension(w,h));
			game.setMinimumSize(new Dimension(w,h));
			
			frame.setVisible(true);
			while(!menu.getState()) {
				try {
					TimeUnit.MILLISECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("vs computer: " + GameChecks.COMPUTER);
			
			frame.remove(menu);	
			menu.setState(false);
				
			frame.add(game);
			frame.pack();
			frame.setVisible(true);
		
			game.start();
			
			while(!GameChecks.GAMEOVER) {
				try {
					TimeUnit.MILLISECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
			if(GameChecks.STALEMATE) {
				postGame = new postGameMenu(w, h, "StaleMate");
			}else if(GameChecks.CHECKMATE) {
				if(!GameChecks.WINNER) {
					postGame = new postGameMenu(w, h, "White Wins");
				}else
					postGame = new postGameMenu(w, h, "Black Wins");
			}
			
			frame.remove(game);
			frame.add(postGame);
			frame.pack();
			frame.setVisible(true);
			
			GameChecks.STALEMATE = false;
			GameChecks.CHECKMATE = false; 
			GameChecks.GAMEOVER = false;
			GameChecks.COMPUTER = false;
			GameChecks.TURN = false;
			
			while(!postGame.getState()) {
				try {
					TimeUnit.MILLISECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			frame.remove(postGame);
			postGame = null;
			game = null;
			frame.add(menu);
			
		}
	}

}
