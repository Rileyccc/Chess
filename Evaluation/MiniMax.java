package Evaluation;

import java.io.IOException;

import ChessObjects.ChessLogic;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceHolder;
import ChessObjects.Square;

public class MiniMax extends ChessLogic{
	private Move bestMove;
	private final int INFINITY = 10000000;
	private Piece piece;
	private int s = 1;
	
	public Move minimax(PieceHolder<Piece> whitePieces, PieceHolder<Piece> blackPieces, String turn, int depth) {
		setBoxes(initTestBoxes(whitePieces, blackPieces));
		
		bestMove = new Move(0, 0);
	
		if(turn.equals("white"))
			max(depth, whitePieces, blackPieces, 1);
		else
			min(depth, whitePieces, blackPieces, 1);
		
		System.out.println("best move found");
		
		return bestMove;
	}
	
	public int max(int depth, PieceHolder<Piece> whitePieces, PieceHolder<Piece> blackPieces, int iteration) {
		//System.out.println(s++);
		setTurn("white");
		int score = -INFINITY;
		int x = 0;
		int y = 0;
		boolean removed = false;
		Piece temp;
		if(depth == 0) {
			return Evaluator.eval(whitePieces, blackPieces);
		}
		int max = -INFINITY;
		
		for(Piece p: whitePieces) {
			x = p.getX();
			y = p.getY();
			if(!p.getSleeping()) {
				for(Move move:p.getMoves()) {
					try {
						if(testMove(x, y, move.getX(), move.getY())) {
						
							temp = (Piece) blackPieces.getPieceIdx(move.getX(), move.getY());	
						
						
							if(temp != null) {
								temp.setSleeping(true);
								removed = true;
							}
							
							getBoxes()[move.getX()][move.getY()].setPiece(getBoxes()[x][y].getPiece());
							getBoxes()[x][y].setPiece(null);
							
							p.setX(move.getX());
							p.setY(move.getY());
						
							score = min(depth - 1, whitePieces, blackPieces, iteration + 1);
							setTurn("white");
							
							if(score > max) {
								max = score;
									if(iteration == 1) {
											bestMove.setX(move.getX());
											bestMove.setY(move.getY());
											piece = p;
								}
							}
							
						
							getBoxes()[x][y].setPiece(getBoxes()[move.getX()][move.getY()].getPiece());
						
							if(removed) {
								getBoxes()[move.getX()][move.getY()].setPiece(temp);
								temp.setSleeping(false);
								removed = false;
							}else {
								getBoxes()[move.getX()][move.getY()].setPiece(null);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
			p.setX(x);
			p.setY(y);
		}
		return max;
	}
	
	public int min(int depth, PieceHolder<Piece> whitePieces, PieceHolder<Piece> blackPieces, int iteration) {
		//System.out.println(s++);
		setTurn("black");
		int score = +INFINITY;
		int x = 0;
		int y = 0;
		boolean removed = false;
		Piece temp;
		if(depth == 0) {
			return Evaluator.eval(whitePieces, blackPieces);
		}
		int min = +INFINITY;
		
		
		for(Piece p: blackPieces) {
			System.out.println(blackPieces.size());
			x = p.getX();
			y = p.getY();
			
			if(!p.getSleeping()) {
				for(Move move:p.getMoves()) {
						try {
							if(testMove(p.getX(), p.getY(), move.getX(), move.getY())) {

								temp = (Piece) whitePieces.getPieceIdx(move.getX(), move.getY());
								
								if(temp != null) {
									temp.setSleeping(true);
									removed = true;
								}
							
								getBoxes()[move.getX()][move.getY()].setPiece(getBoxes()[x][y].getPiece());
								getBoxes()[x][y].setPiece(null);
							
								p.setX(move.getX());
								p.setY(move.getY());

								score = max(depth - 1, whitePieces, blackPieces, iteration + 1);
								setTurn("black");

								
								if(score < min) {
								
									min = score;
									System.out.println(min);
									if(iteration == 1) {
											bestMove.setX(move.getX());
											bestMove.setY(move.getY());
											piece = p;
									
									}
								}

								getBoxes()[x][y].setPiece(getBoxes()[move.getX()][move.getY()].getPiece());
								if(removed) {
									getBoxes()[move.getX()][move.getY()].setPiece(temp);
									temp.setSleeping(false);
									removed = false;
								}else {
									getBoxes()[move.getX()][move.getY()].setPiece(null);
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
				}
			}
			p.setX(x);
			p.setY(y);
	}
		
		
		return min;
	}
	
	public Square[][] initTestBoxes(PieceHolder<Piece> whitePieces, PieceHolder<Piece> blackPieces) {
		Square[][] testBoxes = new Square[8][8];
		for(int i = 0; i < 8; i++) 
		{
			for(int j = 0; j < 8; j++) 
			{
					testBoxes[j][i] = new Square(j, i);
					testBoxes[j][i].setPiece(null);
			}
		}
		for(Piece p: whitePieces) {
			testBoxes[p.getX()][p.getY()].setPiece(p);
		}
		for(Piece p: blackPieces) {
			testBoxes[p.getX()][p.getY()].setPiece(p);
		}
		return testBoxes;
	}
	
	public Move getMove() {
		return bestMove;
	}
	public Piece getPiece() {
		return piece;
	}
}
