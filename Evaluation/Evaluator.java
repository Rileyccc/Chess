package Evaluation;

import ChessObjects.PieceHolder;
import ChessObjects.Queen;
import ChessObjects.Rook;
import GameLoop.GameChecks;
import ChessObjects.Bishop;
import ChessObjects.Knight;
import ChessObjects.Pawn;
import ChessObjects.Piece;

public final class Evaluator {
	
	private static final int PAWN = 100; 
	private static final int KNIGHT = 320;
	private static final int BISHOP = 330;
	private static final int ROOK = 500;
	private static final int QUEEN = 900;
	private static final int KING = 20000;
	
	
	
			
	// 90% sure need to flip black and white
	private static int[][] whitePawnPos = {
		{0,  0,  0,  0,  0,  0,  0,  0},
		{50, 50, 50, 50, 50, 50, 50, 50},
		{10, 10, 20, 30, 30, 20, 10, 10},
		{5,  5, 10, 25, 25, 10,  5,  5},
		{0,  0,  0, 20, 20,  0,  0,  0},
		{5, -5,-10,  0,  0,-10, -5,  5},
		{5, 10, 10,-20,-20, 10, 10,  5},
		{0,  0,  0,  0,  0,  0,  0,  0}
	};
	
	private static int[][] blackPawnPos = {
		{0,  0,  0,  0,  0,  0,  0,  0},
		{5, 10, 10,-20,-20, 10, 10,  5},
		{5, -5,-10,  0,  0,-10, -5,  5},
		{0,  0,  0, 20, 20,  0,  0,  0},
		{5,  5, 10, 25, 25, 10,  5,  5},
		{10, 10, 20, 30, 30, 20, 10, 10},
		{50, 50, 50, 50, 50, 50, 50, 50},
		{0,  0,  0,  0,  0,  0,  0,  0}
	};
	
	private static int [][] knightPos = {
		{-50,-40,-30,-30,-30,-30,-40,-50},
		{-40,-20,  0,  0,  0,  0,-20,-40},
		{-30,  0, 10, 15, 15, 10,  0,-30},
		{-30,  5, 15, 20, 20, 15,  5,-30},
		{-30,  0, 15, 20, 20, 15,  0,-30},
		{-30,  5, 10, 15, 15, 10,  5,-30},
		{-40,-20,  0,  5,  5,  0,-20,-40},
		{-50,-40,-30,-30,-30,-30,-40,-50}
	};
	
	private static int[][] whiteBishopPos = {
			{-20,-10,-10,-10,-10,-10,-10,-20}, 
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5, 10, 10,  5,  0,-10},
			{-10,  5,  5, 10, 10,  5,  5,-10},
			{-10,  0, 10, 10, 10, 10,  0,-10}, 
			{-10, 10, 10, 10, 10, 10, 10,-10},
			{-10,  5,  0,  0,  0,  0,  5,-10},
			{20,-10,-10,-10,-10,-10,-10,-20}	
	};
	
	private static int[][] blackBishopPos = {
			{20, -10,-10,-10,-10,-10,-10,-20}, 
			{-10,  5,  0,  0,  0,  0,  5,-10},
			{-10, 10, 10, 10, 10, 10, 10,-10},
			{-10,  0, 10, 10, 10, 10,  0,-10}, 
			{-10,  5,  5, 10, 10,  5,  5,-10},
			{-10,  0,  5, 10, 10,  5,  0,-10},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-20,-10,-10,-10,-10,-10,-10,-20}
	}; 
	
	private static int[][] whiteRookPos = {
			{ 0,  0,  0,  0,  0,  0,  0,  0}, 
			{ 5, 10, 10, 10, 10, 10, 10,  5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5}, 
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{ 0,  0,  0,  5,  5,  0,  0,  0}	
	}; 
	
	private static int[][] blackRookPos = {
			{ 0,  0,  0,  5,  5,  0,  0,  0}, 
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5}, 
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{ 5, 10, 10, 10, 10, 10, 10,  5},
			{ 0,  0,  0,  0,  0,  0,  0,  0}
	}; 
	private static int[][] whiteQueenPos = {
			{-20,-10,-10, -5, -5,-10,-10,-20}, 
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5,  5,  5,  5,  0,-10},
			{ -5,  0,  5,  5,  5,  5,  0, -5},
			{ 0,  0,  5,  5,  5,  5,  0, -5}, 
			{-10,  5,  5,  5,  5,  5,  0,-10},
			{-10,  0,  5,  0,  0,  0,  0,-10},
			{-20,-10,-10, -5, -5,-10,-10,-20}	
	}; 
	
	private static int[][] blackQueenPos = {
			{-20,-10,-10, -5, -5,-10,-10,-20}, 
			{-10,  0,  5,  0,  0,  0,  0,-10},
			{-10,  5,  5,  5,  5,  5,  0,-10},
			{ 0,  0,  5,  5,  5,  5,  0, -5},
			{ -5,  0,  5,  5,  5,  5,  0, -5},
			{-10,  0,  5,  5,  5,  5,  0,-10},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-20,-10,-10, -5, -5,-10,-10,-20}	
	}; 
	
	private static int[][] whiteKingMidGamePos = {
			{-30,-40,-40,-50,-50,-40,-40,-30}, 
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-20,-30,-30,-40,-40,-30,-30,-20}, 
			{-10,-20,-20,-20,-20,-20,-20,-10},
			{ 20, 20,  0,  0,  0,  0, 20, 20},
			{ 20, 30, 10,  0,  0, 10, 30, 20}	
	}; 
	
	private static int[][] blackKingMidGamePos = {
			{ 20, 30, 10,  0,  0, 10, 30, 20},
			{ 20, 20,  0,  0,  0,  0, 20, 20},
			{-10,-20,-20,-20,-20,-20,-20,-10},
			{-20,-30,-30,-40,-40,-30,-30,-20}, 
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30}, 
	};
	
	private static int[][] whiteKingEndGamePos = {
			{-50,-40,-30,-20,-20,-30,-40,-50},
			{-30,-20,-10,  0,  0,-10,-20,-30},
			{-30,-10, 20, 30, 30, 20,-10,-30},
			{-30,-10, 30, 40, 40, 30,-10,-30},
			{-30,-10, 30, 40, 40, 30,-10,-30},
			{-30,-10, 20, 30, 30, 20,-10,-30},
			{-30,-30,  0,  0,  0,  0,-30,-30},
			{-50,-30,-30,-30,-30,-30,-30,-50}
	};
	
	private static int[][] blackKingEndGamePos = {
			{-50,-30,-30,-30,-30,-30,-30,-50},
			{-30,-30,  0,  0,  0,  0,-30,-30},
			{-30,-10, 20, 30, 30, 20,-10,-30},
			{-30,-10, 30, 40, 40, 30,-10,-30},
			{-30,-10, 30, 40, 40, 30,-10,-30},
			{-30,-10, 20, 30, 30, 20,-10,-30},
			{-30,-20,-10,  0,  0,-10,-20,-30},
			{-50,-40,-30,-20,-20,-30,-40,-50},
	};
	
	public static int eval( PieceHolder<Piece> whitePieces, PieceHolder<Piece> blackPieces) {
		return eval(whitePieces) - eval(blackPieces);
	}
	
	public static int eval(PieceHolder<Piece> pieces) {
		int value = 0;
		if(pieces.get(0).getColor().equals("white")) {
			for(Piece p: pieces) {
				if(!p.getSleeping()) {
					if(p instanceof Pawn)
						value += PAWN + whitePawnPos[p.getX()][p.getY()];
					else if(p instanceof Knight)
						value += KNIGHT + knightPos[p.getX()][p.getY()];
					else if(p instanceof Bishop)
						value += BISHOP + whiteBishopPos[p.getX()][p.getY()];
					else if(p instanceof Rook)
						value += ROOK + whiteRookPos[p.getX()][p.getY()];
					else if(p instanceof Queen)
						value += QUEEN + whiteQueenPos[p.getX()][p.getY()];
					else {
						if(!GameChecks.ENDGAME) 
							value += KING + whiteKingMidGamePos[p.getX()][p.getY()];
						else
							value += KING + whiteKingEndGamePos[p.getX()][p.getY()];
					}		
				}
			}
		}else {
			for(Piece p: pieces) {
				if(!p.getSleeping()) {
					if(p instanceof Pawn)
						value += PAWN + blackPawnPos[p.getX()][p.getY()];
					else if(p instanceof Knight)
						value += KNIGHT + knightPos[p.getX()][p.getY()];
					else if(p instanceof Bishop)
						value += BISHOP + blackBishopPos[p.getX()][p.getY()];
					else if(p instanceof Rook)
						value += ROOK + blackRookPos[p.getX()][p.getY()];
					else if(p instanceof Queen)
						value += QUEEN + blackQueenPos[p.getX()][p.getY()];
					else {
						if(!GameChecks.ENDGAME) 
							value += KING + blackKingMidGamePos[p.getX()][p.getY()];
						else
							value += KING + blackKingEndGamePos[p.getX()][p.getY()];
					}	
				}
			}
		}
		
		return value;
	}
	
	
}
