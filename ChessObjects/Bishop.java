package ChessObjects;

import java.io.IOException;
import java.util.LinkedList;

public class Bishop extends Piece {
	
	public Bishop(int x, int y, String color) throws IOException {
		super(x, y, color);
		if(color.equals("black"))
			setImage(images.getBlackBishop());
		else
			setImage(images.getWhitekBishop());
		
	}

	@Override
	//checks if a piece is blocking the bishops path, if no pieces blocking move is valid.
	//If and only is the piece landing on is null or opposing teams piece is there
	public boolean validMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		int changeX = updatedX - oldX;
		int changeY = updatedY - oldY;
		int count = 0;
		if(Math.abs(changeX) == Math.abs(changeY)) {
			if(changeX > 0 && changeY > 0) {	
				int r = oldY;
				for(int i = oldX; i < updatedX; i++) {
					if(box[i][r].getPiece() == null)
						count++;
					r++;
				}
				if(count == changeX - 1) {
					if(box[updatedX][updatedY].getPiece() == null)
						return true;	
					else if(box[updatedX][updatedY].getPiece().getColor() != box[oldX][oldY].getPiece().getColor())
						return true;
				}
			}
			
			else if(changeX > 0 && changeY < 0) {
				int r = oldY;
				for(int i = oldX; i < updatedX; i++) {
					if(box[i][r].getPiece() == null)
						count++;
					r--;
				}
				if(count == changeX - 1) 
					if(box[updatedX][updatedY].getPiece() == null)
						return true;	
					else if(box[updatedX][updatedY].getPiece().getColor() != box[oldX][oldY].getPiece().getColor())
						return true;
				
			}
			else if(changeX < 0 && changeY > 0) {
				int i = oldX;
				for(int r = oldY; r < updatedY; r++) {
					if(box[i][r].getPiece() == null) 
						count++;
					
					i--;
				}
				if(count == changeY - 1) {
					if(box[updatedX][updatedY].getPiece() == null)
						return true;	
					else if(box[updatedX][updatedY].getPiece().getColor() != box[oldX][oldY].getPiece().getColor())
						return true;
				}
			}
			else {
				int r = oldY;
				for(int i = oldX; i > updatedX; i--) {
					if(box[i][r].getPiece() == null) 
						count++;
				r--;
				}
				if(count == Math.abs(changeX) - 1) {
					if(box[updatedX][updatedY].getPiece() == null)
						return true;
					else if(box[updatedX][updatedY].getPiece().getColor() != box[oldX][oldY].getPiece().getColor())
						return true;
				}
					
			}
		}
		return false;
	}


	public LinkedList<Move> getMoves(Square[][] boxes) {
		LinkedList<Move> moves = new LinkedList<Move>();
		int x = getX();
		int y = getY();
		while(validMove(getX(), getY(), x, y, boxes)) {
			moves.add(new Move(x, y));
			x++;
			y++;
		}
		x = getX();
		y = getY();
		while(validMove(getX(), getY(), x, y, boxes)) {
			moves.add(new Move(x, y));
			x++;
			y--;
		}
		x = getX();
		y = getY();
		while(validMove(getX(), getY(), x, y, boxes)) {
			moves.add(new Move(x, y));
			x--;
			y++;
		}
		x = getX();
		y = getY();
		while(validMove(getX(), getY(), x, y, boxes)) {
			moves.add(new Move(x, y));
			x--;
			y--;
		}
		return moves;
	}

	@Override
	public LinkedList<Move> getMoves() {
		LinkedList<Move> moves = new LinkedList<Move>();
		int x = getX() + 1;
		int y = getY() + 1;
		while(x < 8 && y < 8) {
			moves.add(new Move(x, y));
			x++;
			y++;
		}
		x = getX() + 1;
		y = getY() - 1;
		while(x < 8 && y > 0) {
			moves.add(new Move(x, y));
			x++;
			y--;
		}
		x = getX() - 1;
		y = getY() + 1;
		while(x > 0 && y < 8) {
			moves.add(new Move(x, y));
			x--;
			y++;
		}
		x = getX() - 1;
		y = getY() - 1;
		while(x > 0 && y > 0) {
			moves.add(new Move(x, y));
			x--;
			y--;
		}
		return moves;
	
	}
}
