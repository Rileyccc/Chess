package GameLoop;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseHandler implements MouseListener{
	private int[] clickCordinates;
	boolean clicked = false;
	
	//Constructor
	public MouseHandler() {
		clickCordinates = new int[2];
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		//index 0 is x cordinate
		clickCordinates[0] = event.getX() / 100;
		//index 1 is y cordinate
		clickCordinates[1] = event.getY() / 100;
		System.out.println("X: "  + clickCordinates[0] + " Y: " + clickCordinates[1]  );
		clicked = true;
		
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	
	public int[] getClickedCoordinates() {
		return clickCordinates;
	}
	public boolean getClicked() {
		return clicked;
	}
	//resets click so we can wait for next event in move() method
	public void resetClick() {
		clicked = false;
	}
	

}
