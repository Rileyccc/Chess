package ChessObjects;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import ImagesAndProcessing.PieceImages;



public abstract class Piece implements Cloneable {
	public  PieceImages images; 
	private String color;
	private BufferedImage image;
	private int numOfMoves;
	private int x, y;
	private boolean sleep;
	
	public Piece(int x, int y, String color) throws IOException {
		this.x = x;
		this.y = y;
		this.color = color;
		images = new PieceImages();	
		sleep = false;
		
	}
	
	
	public void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return y;
	}
	public void setColor(String color){
		this.color = color;
	}
	public void setNumOfMoves(int numOfMoves) {
		this.numOfMoves = numOfMoves;
	}
	public int getNumOfMoves() {
		return numOfMoves;
	}
	public String getColor() {
		return color;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public BufferedImage getImage() {
		return image;
	}
	public boolean getSleeping() {
		return sleep;
	}
	public void setSleeping(boolean sleep ) {
		this.sleep = sleep;
	}
	public abstract LinkedList<Move> getMoves(Square[][] boxes);
	public abstract LinkedList<Move> getMoves();
	
	
	

	
	
	
	public void display() {};
	public abstract boolean validMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box);
	// checks if there is a piece of the same team in the space you are trying to move to 
	public boolean sameColorPiece(Square oldbox, Square newBox) {
		try {
			return oldbox.getPiece().getColor().equals(newBox.getPiece().getColor());
		}catch(NullPointerException e) {
			return false;
		}
	}
	public boolean withinBounds(int updatedX, int updatedY) {
		return updatedX >= 0 && updatedX <= 7 && updatedY >= 0 && updatedY <= 7; 
	}
	// finish.
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(getImage(), x * 100 + 25 + 2, y *100 + 25 + 2, null);
	}
	
	
//	public float getVelX()
//	{
//		return velX; 
//	}
//	public float getVelY()
//	{
//		return velY; 
//	}
//	
//	public void setVelX(float velX)
//	{
//		this.velX = velX;
//	}
//	public void setVelY(float velY)
//	{
//		this.velY = velY; 
//	}
//	
	public Piece clone() throws CloneNotSupportedException {
		return (Piece) super.clone();
	}
	

}
