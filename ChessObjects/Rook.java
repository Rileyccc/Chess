package ChessObjects;

import java.io.IOException;
import java.util.LinkedList;

public class Rook extends Piece {
	
	
	public Rook(int x, int y, String color) throws IOException {
		super(x, y, color);
		if(color.equals("black"))
			setImage(images.getBlackRook());
		else
			setImage(images.getWhiteRook());
	}
	

	@Override
	public boolean validMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		int count = 0;
		
		if(oldX != updatedX && oldY == updatedY) {
			int changeX = updatedX - oldX;
			
			if(changeX > 0) {
				for(int i = oldX; i < updatedX; i++) {
					if(box[i][updatedY].getPiece() == null)
						count++;
				}
			}else {
			
				for(int i = oldX; i > updatedX; i--)
					if(box[i][updatedY].getPiece() == null) 
						count++;
			}
			
			if(count == Math.abs(changeX) - 1) { 
				if(box[updatedX][updatedY].getPiece() == null) {
					setNumOfMoves(getNumOfMoves() + 1);
					return true;	
				}
				else if(box[updatedX][updatedY].getPiece().getColor() != box[oldX][oldY].getPiece().getColor()) {
					setNumOfMoves(getNumOfMoves() + 1);
					return true;
				}
			}
		
		}else if(oldX == updatedX && oldY != updatedY) {
			int changeY = updatedY - oldY;
			
			if(changeY > 0) {
				for(int i = oldY; i < updatedY; i++) {
					if(box[updatedX][i].getPiece() == null)
						count++;
				}
			}else {
				for(int i = oldY; i > updatedY; i--)
					if(box[updatedX][i].getPiece() == null) 
						count++;
			}
			
			if(count == Math.abs(changeY) - 1) {
				if(box[updatedX][updatedY].getPiece() == null) {
					setNumOfMoves(getNumOfMoves() + 1);					
					return true;	
				}
				else if(box[updatedX][updatedY].getPiece().getColor() != box[oldX][oldY].getPiece().getColor()) {
					setNumOfMoves(getNumOfMoves() + 1);
					return true;
				}
			}
		}
		return false;
	}


	public LinkedList<Move> getMoves(Square[][] boxes) {
		LinkedList<Move> moves = new LinkedList<Move>();
		int x = getX() + 1;
		while(validMove(getX(), getY(), x, getY(), boxes)) {
			moves.add(new Move(x, getY()));
			x++;
		}
		x = getX() - 1;
		while(validMove(getX(), getY(), x, getY(), boxes)) {
			moves.add(new Move(x, getY()));
			x--;
		}
		int y = getY() + 1;
		while(validMove(getX(), getY(), getX(), y, boxes)) {
			moves.add(new Move(getX(), y));
			y++;
		}
		y = getY() - 1;
		while(validMove(getX(), getY(), getX(), y, boxes)) {
			moves.add(new Move(getX(), y));
			y--;
		}
		return moves;
	}


	@Override
	public LinkedList<Move> getMoves() {
		LinkedList<Move> moves = new LinkedList<Move>();
		int x = getX();
		while(x < 8) {
			moves.add(new Move(x, getY()));
			x++;
		}
		x = getX();
		while(x > 0) {
			moves.add(new Move(x, getY()));
			x--;
		}
		int y = getY();
		while(y < 8) {
			moves.add(new Move(getX(), y));
			y++;
		}
		y = getY();
		while(y > 0) {
			moves.add(new Move(getX(), y));
			y--;
		}
		return moves;
		
	}

}
