package ChessObjects;

import java.util.ArrayList;

public class PieceHolder<E> extends ArrayList<E> implements Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PieceHolder(){
		super(16);
	} 
	// remove Piece from list based on x and y cord 
	public Piece removeIdx(int x, int y) {
	    Object[] pieces  = (Object[]) toArray();
		for(int i = 0; i < size(); i++) {
			Piece p = (Piece) pieces[i];
			if(((Piece)p).getX() == x && ((Piece) p).getY() == y) {
				return (Piece) remove(i);
			}	
		}
		return null;
		
	}
	
	public Piece getPieceIdx(int x, int y) {
		  Object[] pieces  = (Object[]) toArray();
			for(int i = 0; i < size() - 1; i++) {
				Piece p = (Piece) pieces[i];
				if(((Piece)p).getX() == x && ((Piece) p).getY() == y) {
					return (Piece) p;
				}	
			}
			return null;	
	}
	

}
