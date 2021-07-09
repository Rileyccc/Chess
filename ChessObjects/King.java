package ChessObjects;

import java.io.IOException;
import java.util.LinkedList;

import GameLoop.GameChecks;

public class King extends Piece {
	
	public King(int x, int y, String color) throws IOException {
		super(x, y, color);
		if(color.equals("black"))
			setImage(images.getBlackKing());
		else
			setImage(images.getWhiteKing());
		
	}
	


	@Override
	public  boolean validMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		if(withinBounds(updatedX, updatedY)) {
			if(((oldY + 1 == updatedY || oldY - 1 == updatedY || updatedY == oldY) && (oldX + 1 == updatedX || oldX - 1 == updatedX)) &&
				 (box[updatedX][updatedY].getPiece() == null || !(box[oldX][oldY].getPiece().getColor().equals(box[updatedX][updatedY].getPiece().getColor())))) {
				setNumOfMoves(getNumOfMoves() + 1);
				return true;
			}else if((oldY + 1 == updatedY || oldY - 1 == updatedY) && (oldX + 1 == updatedX || oldX - 1 == updatedX || oldX == updatedX) &&
				(box[updatedX][updatedY].getPiece() == null || !(box[oldX][oldY].getPiece().getColor().equals(box[updatedX][updatedY].getPiece().getColor())))) { 
				setNumOfMoves(getNumOfMoves() + 1);
				return true;
				
			}
			else if(getNumOfMoves() == 0 && oldY == updatedY && oldX - 2 == updatedX && box[0][oldY].getPiece() != null &&
			box[0][oldY].getPiece() instanceof Rook && ((Rook) box[0][oldY].getPiece()).getNumOfMoves() == 0 && box[1][oldY].getPiece() == null
			&& box[2][oldY].getPiece() == null && box[3][oldY].getPiece() == null){
				GameChecks.QUEENSIDECASTLE = true;
				setNumOfMoves(getNumOfMoves() + 1);
				System.out.println("valid Queenside castle");
	    		return true;
			}else if(getNumOfMoves() == 0 && oldY == updatedY && oldX + 2 == updatedX && box[7][oldY].getPiece() != null &&
			box[7][oldY].getPiece() instanceof Rook && ((Rook) box[0][oldY].getPiece()).getNumOfMoves() == 0 && box[6][oldY].getPiece() == null
			&& box[5][oldY].getPiece() == null){
				GameChecks.KINGSIDECASTLE = true;
				setNumOfMoves(getNumOfMoves() + 1);
				System.out.println("valid king side castle");
				return true;
			}
		}
    	return false;
		}
	
	
	public boolean checkMateTest(int oldX, int oldY, int updatedX, int updatedY, Square[][] box){
		if(((oldY + 1 == updatedY || oldY - 1 == updatedY || updatedY == oldY) && (oldX + 1 == updatedX || oldX - 1 == updatedX)) &&
					 (box[updatedX][updatedY].getPiece() == null || !(box[oldX][oldY].getPiece().getColor().equals(box[updatedX][updatedY].getPiece().getColor())))) {
			return true;
		}else if((oldY + 1 == updatedY || oldY - 1 == updatedY) && (oldX + 1 == updatedX || oldX - 1 == updatedX || oldX == updatedX) &&
			(box[updatedX][updatedY].getPiece() == null || !(box[oldX][oldY].getPiece().getColor().equals(box[updatedX][updatedY].getPiece().getColor())))) { 
			return true;
		}
		
		return false;
	
	}



	
	public LinkedList<Move> getMoves(Square[][] boxes) {
		LinkedList<Move> moves = new LinkedList<>();
		if(validMove(getX(), getY(), getX() + 1, getY(), boxes)){
			setNumOfMoves(getNumOfMoves() - 1);
			moves.add(new Move(getX() + 1, getY()));
		}
		if(validMove(getX(), getY(), getX() + 1, getY() + 1, boxes)){
			setNumOfMoves(getNumOfMoves() - 1);
			moves.add(new Move(getX() + 1, getY() + 1));
		}
		if(validMove(getX(), getY(), getX() - 1, getY() - 1, boxes)){
			setNumOfMoves(getNumOfMoves() - 1);
			moves.add(new Move(getX() + 1, getY() - 1));
		}
		if(validMove(getX(), getY(), getX() - 1, getY(), boxes)){
			setNumOfMoves(getNumOfMoves() - 1);
			moves.add(new Move(getX() - 1, getY()));
		}
		if(validMove(getX(), getY(), getX() - 1, getY() + 1, boxes)){
			setNumOfMoves(getNumOfMoves() - 1);
			moves.add(new Move(getX() - 1, getY() + 1));
		}
		if(validMove(getX(), getY(), getX() - 1, getY() - 1, boxes)){
			setNumOfMoves(getNumOfMoves() - 1);
			moves.add(new Move(getX() - 1, getY() - 1));
		}
		if(validMove(getX(), getY(), getX() - 1, getY() - 1, boxes)){
			setNumOfMoves(getNumOfMoves() - 1);
			moves.add(new Move(getX() - 1, getY() - 1));
		}
		if(validMove(getX(), getY(), getX() + 1, getY() + 1, boxes)){
			setNumOfMoves(getNumOfMoves() - 1);
			moves.add(new Move(getX() + 1, getY() + 1));
		}
		
		return moves;
				
	}



	@Override
	public LinkedList<Move> getMoves() {
		LinkedList<Move> moves = new LinkedList<>();
		moves.add(new Move(getX() + 1, getY()));
		moves.add(new Move(getX() - 1, getY()));
		moves.add(new Move(getX(), getY() + 1));
		moves.add(new Move(getX(), getY() - 1));
		moves.add(new Move(getX() + 1, getY() + 1));
		moves.add(new Move(getX() + 1, getY() - 1));
		moves.add(new Move(getX() - 1, getY() + 1));
		moves.add(new Move(getX() - 1, getY() - 1));
	
		return moves;
		
	}
}
