package ChessObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;




public class Square implements Cloneable{
	private Piece piece; 
	private int x, y;
	private BufferedImage image;
	private Color color;
	//private int width = 100;
	//private int height = 100;
	private boolean errorState;
	private int count;
	//constructor
	public Square(int x, int y, Color color)  throws IOException{
		this.x = x;
		this.y = y;
		this.color = color;
		errorState = false;
		count = 0;
		
		initalizePiece(x, y);	
	}
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	public BufferedImage getImage() {
		if(image == null)
			return piece.getImage();
		else return image;
	}
	
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
		
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public int getY() {
		return y;
	}
	public int getX() {
		return x;
	}
	public void setErrorState(boolean errorState) 
	{
		this.errorState = errorState;
	}
	public Color getColor() {
		return color; 
	}
	public void setColor(Color color) {
		this.color = color;
	}


	
	//initialize pieces 
	public void initalizePiece(int x, int y) throws IOException {
		// white Knights
		if(y == 7 && (x == 1 || x == 6)) 
		{
			piece = new Knight(x,y,"white");
		}
		// white pawn
		else if(y == 6) 
		{
			piece = new Pawn(x,y, "white");
		}
		// white Rook
		else if(y == 7 && (x == 0 || x==7) ) 
		{
			piece = new Rook(x,y, "white");
		}
		// white Bishop
		else if(y == 7 && (x == 2 || x== 5))
		{
			piece = new Bishop(x, y, "white");
		}
		// White King
		else if(y == 7 && x == 4)
		{
			
			piece = new King(x, y, "white");
		}
		// white Queen
		else if(y == 7 && x == 3) 
		{
			piece = new Queen(x, y, "white");
		}
		// black pawn
		else if(y == 1)
		{
			piece = new Pawn(x,y, "black");
		}
		// black rook
		else if(y == 0 && (x == 0 || x == 7))
		{
			piece = new Rook(x, y, "black");	
		}
		// black Knights 
		else if (y == 0 && (x == 1 || x == 6)) 
		{
			piece = new Knight(x, y, "black");	
		}
		// black Bishop 
		else if(y == 0 && (x == 2 || x == 5)) 
		{
			piece = new Bishop(x, y, "black");
		}
		// black King
		else if(y == 0 && x == 4) 
		{
			piece = new King(x, y, "black");
		}
		// black Queen
		else if(y == 0 && x == 3) 
		{
			piece = new Queen(x, y, "black");
		}
	}


	public void tick() {
		
	}

	public void render(Graphics g){
			if(errorState == false){
				g.setColor(color);
				g.fillRect(x * 100 + 2, y * 100 + 2, 100, 100);
			}
			else {
				if(count < 60 || (count >=120 && count < 180 ) || (count >= 240 && count < 300) ) {
					g.setColor(Color.red);
					g.fillRect(x * 100 + 2, y * 100 + 2, 100, 100);
				}else if( (count >= 60 &&  count < 120) || (count >= 180 && count < 240) || count == 300) { 
					g.setColor(color);
					g.fillRect(x * 100 + 2, y * 100 + 2, 100, 100);
					if(count == 300) {
						errorState = false;
						count = 0;
					}
						
				}
				count++;
			}
	}
	public Square clone() throws CloneNotSupportedException{  
		Square s = (Square)super.clone();
		
		s.piece  = (Piece)piece.clone(); 
		return s;
	}  
}
