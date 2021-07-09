package ChessObjects;

import java.io.IOException;
import java.util.LinkedList;

public class Knight extends Piece {
	
	public Knight(int x, int y, String color) throws IOException {
		super(x, y, color);
		if(color.equals("black"))
			setImage(images.getBlackKnight());
		else
			setImage(images.getWhiteKnight());
		
	}

	@Override
	public boolean validMove(int oldX, int oldY, int updatedX, int updatedY, Square[][] box) {
		if(withinBounds(updatedX, updatedY)) {
			if(sameColorPiece(box[oldX][oldY], box[updatedX][updatedY]))
				return false;
			if((oldX + 2 == updatedX || oldX - 2 == updatedX) && (oldY + 1 == updatedY || oldY - 1 == updatedY) ||
					(oldY + 2 == updatedY || oldY - 2 == updatedY) && ( oldX + 1 == updatedX || oldX - 1 == updatedX))
				return true;
		}
		return false;
	}


	public LinkedList<Move> getMoves(Square[][] boxes) {
		LinkedList<Move> moves = new LinkedList<>();
		if(validMove(getX(), getY() , getX() + 2, getY() + 1, boxes))
			moves.add(new Move(getX() + 2, getY() + 1));
		if(validMove(getX(), getY() , getX() + 2, getY() - 1, boxes))
			moves.add(new Move(getX() + 2, getY() - 1));
		if(validMove(getX(), getY() , getX() - 2, getY() + 1, boxes))
			moves.add(new Move(getX() - 2, getY() + 1));
		if(validMove(getX(), getY() , getX() - 2, getY() - 1, boxes))
			moves.add(new Move(getX() - 2, getY() - 1));
		if(validMove(getX(), getY() , getX() + 1, getY() + 2, boxes))
			moves.add(new Move(getX() + 1, getY() + 2));
		if(validMove(getX(), getY() , getX() - 1, getY() + 2, boxes))
			moves.add(new Move(getX() - 1, getY() + 2));
		if(validMove(getX(), getY() , getX() + 1, getY() - 2, boxes))
			moves.add(new Move(getX() + 1, getY() - 2));
		if(validMove(getX(), getY() , getX() - 1, getY() - 2, boxes))
			moves.add(new Move(getX() - 1, getY() - 2));
		
		return moves;
	}

	@Override
	public LinkedList<Move> getMoves() {
		LinkedList<Move> moves = new LinkedList<>();
		moves.add(new Move(getX() + 2, getY() + 1));
		moves.add(new Move(getX() + 2, getY() - 1));
		moves.add(new Move(getX() - 2, getY() + 1));
		moves.add(new Move(getX() - 2, getY() - 1));
		moves.add(new Move(getX() + 1, getY() + 2));
		moves.add(new Move(getX() - 1, getY() + 2));
		moves.add(new Move(getX() + 1, getY() - 2));
		moves.add(new Move(getX() - 1, getY() - 2));
		return moves;
	}

}
