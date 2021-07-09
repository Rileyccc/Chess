package ChessObjects;

import java.io.IOException;
import java.util.LinkedList;

public class Queen extends Piece {
	
	public Queen(int x, int y, String color) throws IOException {
		super(x, y, color);
		if(color.equals("black"))
			setImage(images.getBlackQueen());
		else 
			setImage(images.getWhiteQueen());
			
		
	}

	@Override
	public boolean validMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		if(oldX != updatedX && oldY == updatedY || oldX == updatedX && oldY != updatedY)
			return rookTypeMove(oldX, oldY, updatedX, updatedY, box);
		else
			return bishopTypeMove(oldX, oldY, updatedX, updatedY, box);
	}
	public boolean rookTypeMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
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
				if(box[updatedX][updatedY].getPiece() == null)
					return true;	
				else if(box[updatedX][updatedY].getPiece().getColor() != box[oldX][oldY].getPiece().getColor())
					return true;
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
				if(box[updatedX][updatedY].getPiece() == null)
					return true;	
				else if(box[updatedX][updatedY].getPiece().getColor() != box[oldX][oldY].getPiece().getColor())
					return true;
			}
		}
		return false;
	}
	public boolean bishopTypeMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box){
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
		while(validMove(getX(), getY(), x, getY(), boxes)) {
			moves.add(new Move(x, getY()));
			x++;
		}
		x = getX();
		while(validMove(getX(), getY(), x, getY(), boxes)) {
			moves.add(new Move(x, getY()));
			x--;
		}
		int y = getY();
		while(validMove(getX(), getY(), getX(), y, boxes)) {
			moves.add(new Move(getX(), y));
			y++;
		}
		y = getY();
		while(validMove(getX(), getY(), getX(), y, boxes)) {
			moves.add(new Move(getX(), y));
			y--;
		}
		x = getX();
		y = getY();
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
		x = getX() + 1;
		while(x < 8) {
			moves.add(new Move(x, getY()));
			x++;
		}
		x = getX() - 1;
		while(x > 0) {
			moves.add(new Move(x, getY()));
			x--;
		}
	    y = getY() + 1;
		while(y < 8) {
			moves.add(new Move(getX(), y));
			y++;
		}
		y = getY() - 1;
		while(y > 0) {
			moves.add(new Move(getX(), y));
			y--;
		}
		return moves;
		
		
	}
}

