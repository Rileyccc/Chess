package ImagesAndProcessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class PieceImages {
	
	private BufferdImageLoader[] pieces; 
	
	// guide to pieces numbers 
	public PieceImages() throws IOException {
		pieces = new BufferdImageLoader[16];
		for(int i = 0; i < pieces.length; i++)
			pieces[i] = new BufferdImageLoader();
		pieces[0].setImage("blackPawn.png");
		pieces[1].setImage("blackRook.png");
		pieces[2].setImage("blackKnight.png");
		pieces[3].setImage("blackBishop.png");
		pieces[4].setImage("blackQueen.png");
		pieces[5].setImage("blackKing.png");
		pieces[6].setImage("whitePawn.png");
		pieces[7].setImage("whiteRook.png");
		pieces[8].setImage("whiteKnight.png");
		pieces[9].setImage("whiteBishop.png");
		pieces[10].setImage("whiteQueen.png");
		pieces[11].setImage("whiteKing.png");	
	}
	//getters for all the PiecesImages
	public BufferedImage getBlackPawn() {return pieces[0].getImage();}
	public BufferedImage getBlackRook() {return pieces[1].getImage();}
	public BufferedImage getBlackKnight() {return pieces[2].getImage();}
	public BufferedImage getBlackBishop() {return pieces[3].getImage();}
	public BufferedImage getBlackQueen() {return pieces[4].getImage();}
	public BufferedImage getBlackKing() {return pieces[5].getImage();}
	public BufferedImage getWhitePawn() {return pieces[6].getImage();}
	public BufferedImage getWhiteRook() {return pieces[7].getImage();}
	public BufferedImage getWhiteKnight() {return pieces[8].getImage();}
	public BufferedImage getWhitekBishop() {return pieces[9].getImage();}
	public BufferedImage getWhiteQueen() {return pieces[10].getImage();}
	public BufferedImage getWhiteKing() {return pieces[11].getImage();}
}
