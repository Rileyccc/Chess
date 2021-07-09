package ChessObjects;

import java.io.IOException;
import java.util.LinkedList;

public class Pawn extends Piece {
	
	public Pawn(int x, int y, String color) throws IOException {
		super(x, y, color);
		if(color.equals("black"))
			setImage(images.getBlackPawn());
		else 
			setImage(images.getWhitePawn());
		
	}


	public boolean validKillmove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		if(box[oldX][oldY].getPiece().getColor().equals("white"))
			return validWhitePawnKill(oldX,  oldY,  updatedX,  updatedY, box);
		else
			return validBlackPawnKill(oldX, oldY, updatedX, updatedY, box);
	}


	@Override
	public boolean validMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		if(withinBounds(updatedX, updatedY)) {
			if( box[oldX][oldY].getPiece() == null) {
				return false;
			}
			String color = box[oldX][oldY].getPiece().getColor();
			
			if(getNumOfMoves() == 0 && color.equals("white"))
				return whitePawnFirstMove(oldX, oldY, updatedX, updatedY, box);
			else if(getNumOfMoves() == 0 && color.equals("black"))
				return blackPawnFirstMove(oldX, oldY, updatedX, updatedY, box);
			else if(color.equals("white"))
				return normalCaseWhiteMove(oldX, oldY, updatedX, updatedY, box);
			else
				return normalCaseBlackMove(oldX, oldY, updatedX, updatedY, box);
		
		}
		return false;
			
			
		
		
	}
	
	public boolean whitePawnFirstMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		// black pawn: and hasn't moved yet y-- or y - 2
		return normalCaseWhiteMove(oldX, oldY, updatedX, updatedY, box) || (oldY - 2 == updatedY && oldX == updatedX && box[updatedX][oldY-1].getPiece() == null && box[updatedX][updatedY].getPiece() == null) ;
		
	}
	public boolean blackPawnFirstMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		
		// white pawn: pawn can only go down, y++, or y + 2
		return normalCaseBlackMove(oldX, oldY, updatedX, updatedY, box) || (oldY + 2 == updatedY && oldX == updatedX && box[updatedX][oldY+1].getPiece() == null && box[updatedX][updatedY].getPiece() == null);
	}
	public boolean normalCaseWhiteMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		boolean test = (( oldY - 1 == updatedY && oldX == updatedX && box[updatedX][updatedY].getPiece() == null) 
				|| oldY - 1 == updatedY && box[updatedX][updatedY].getPiece() != null && ((oldX == updatedX + 1 && box[updatedX][updatedY].getPiece().getColor().equals("black"))
				|| oldX == updatedX-1 && box[updatedX][updatedY].getPiece() != null && box[updatedX][updatedY].getPiece().getColor().equals("black"))
				|| blackEnPassant(oldX, oldY, updatedX, updatedY, box));
		return test;
	}
	public boolean normalCaseBlackMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		boolean test =  ((oldY + 1 == updatedY && oldX == updatedX && box[updatedX][updatedY].getPiece() == null) 
				||  oldY + 1== updatedY && box[updatedX][updatedY].getPiece() != null && ((oldX == updatedX + 1 && box[updatedX][updatedY].getPiece().getColor().equals("white"))
				|| oldX == updatedX-1 && box[updatedX][updatedY].getPiece() != null && box[updatedX][updatedY].getPiece().getColor().equals("white"))
				|| whiteEnPassant(oldX, oldY, updatedX, updatedY, box));
		return test;
	}
	public boolean  validWhitePawnKill(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		return oldY - 1 == updatedY && ((oldX == updatedX + 1 && box[updatedX][updatedY].getPiece().getColor().equals("black"))
				|| oldX == updatedX-1 && box[updatedX][updatedY].getPiece().getColor().equals("black"));
	}
	public boolean validBlackPawnKill(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		return   oldY + 1== updatedY && ((oldX == updatedX + 1 && box[updatedX][updatedY].getPiece().getColor().equals("white"))
				|| oldX == updatedX-1 && box[updatedX][updatedY].getPiece().getColor().equals("white"));
		
	}
	
	public boolean blackEnPassant(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		
		boolean test1 = oldY == 3 && withinBounds(oldX + 1, oldY) &&(box[oldX + 1][oldY].getPiece() != null && box[oldX + 1][oldY].getPiece() instanceof Pawn && box[oldX + 1][oldY].getPiece().getColor().equals("black") 
				&& ((Pawn) box[oldX + 1][oldY].getPiece()).getNumOfMoves() == 0 && updatedX == oldX + 1 && updatedY == oldY - 1 && box[updatedX][updatedY].getPiece() == null
				);
		boolean test2 = oldY == 3 && withinBounds(oldX-1, oldY ) && (box[oldX - 1][oldY].getPiece() != null && box[oldX - 1][oldY].getPiece() instanceof Pawn && box[oldX - 1][oldY].getPiece().getColor().equals("black") 
				&& ((Pawn) box[oldX - 1][oldY].getPiece()).getNumOfMoves() == 0 && updatedX == oldX - 1 && updatedY == oldY - 1 && box[updatedX][updatedY].getPiece() == null
				);
		if(test1) {
			box[oldX + 1][oldY].setPiece(null);
			return test1;
		}
		
		if(test2) {
			box[oldX - 1][oldY].setPiece(null);
			return test2;	
		}
		return false;
	}
	public boolean whiteEnPassant(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		boolean test1 = oldY == 4 && withinBounds(oldX + 1, oldY) && (oldX + 1 >= 0 && box[oldX + 1][oldY].getPiece() != null && box[oldX + 1][oldY].getPiece() instanceof Pawn && box[oldX + 1][oldY].getPiece().getColor().equals("white") 
				&& ((Pawn) box[oldX + 1][oldY].getPiece()).getNumOfMoves() == 0 && updatedX == oldX + 1 && updatedY == oldY + 1 && box[updatedX][updatedY].getPiece() == null
				);
		boolean test2 = oldY == 4 && withinBounds(oldX - 1, oldY)&&(oldX-1 >= 0 && box[oldX - 1][oldY].getPiece() != null && box[oldX - 1][oldY].getPiece() instanceof Pawn && box[oldX - 1][oldY].getPiece().getColor().equals("white") 
				&& ((Pawn) box[oldX - 1][oldY].getPiece()).getNumOfMoves() == 0 && updatedX == oldX - 1 && updatedY == oldY + 1 && box[updatedX][updatedY].getPiece() == null
				);
		if(test1) {
			box[oldX + 1][oldY].setPiece(null);
			return test1;
		}
		
		if(test2) {
			box[oldX - 1][oldY].setPiece(null);
			return test2;	
		}
		return false;
	}

	public LinkedList<Move> getMoves(Square[][] boxes){
		LinkedList<Move> moves = new LinkedList<Move>();
		
		if(getColor().equals("white")) {
			if((validMove(getX(), getY(), getX(), getY() - 1, boxes))) {
				moves.add(new Move(getX(), (getY() -1)));
			}
			
			if((validMove(getX(), getY(), getX() - 1, getY() - 1, boxes))) {
				moves.add(new Move((getX() - 1), (getY() -1)));
			}
			
			if((validMove(getX(), getY(), getX() + 1, getY() - 1, boxes))) {
				moves.add(new Move((getX() + 1), (getY() -1)));
			}
			if(getNumOfMoves() == 0 && validMove(getX(), getY(), getX(), getY() - 2, boxes)) {
				moves.add(new Move(getX(), (getY() -2)));
			}
		}else if(getColor().equals("black")) {
			if((validMove(getX(), getY(), getX(), getY() + 1, boxes))) {
				moves.add(new Move(getX(), (getY() + 1)));
			}
			
			if((validMove(getX(), getY(), getX() - 1, getY() + 1, boxes))) {
				moves.add(new Move((getX() - 1), (getY() + 1)));
			}
			
			if((validMove(getX(), getY(), getX() + 1, getY() + 1, boxes))) {
				moves.add(new Move((getX() + 1), (getY() + 1)));
			}
			if(getNumOfMoves() == 0 && validMove(getX(), getY(), getX(), getY() + 2, boxes)) {
				moves.add(new Move(getX(), (getY() + 2)));
			}
		}
		return moves;
	}

	@Override
	public LinkedList<Move> getMoves() {
		LinkedList<Move> moves = new LinkedList<Move>();
		
		if(getColor().equals("white")) {
			
			moves.add(new Move(getX(), (getY() -1)));
			moves.add(new Move((getX() - 1), (getY() -1)));
			moves.add(new Move((getX() + 1), (getY() -1)));
			if(getNumOfMoves() == 0) {
				moves.add(new Move(getX(), (getY() -2)));
			}
		}else if(getColor().equals("black")) {
			moves.add(new Move(getX(), (getY() + 1)));
			moves.add(new Move((getX() - 1), (getY() + 1)));
			moves.add(new Move((getX() + 1), (getY() + 1)));
			if(getNumOfMoves() == 0) {
				moves.add(new Move(getX(), (getY() + 2)));
			}
		}
		return moves;
	}
}
