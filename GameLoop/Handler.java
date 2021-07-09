package GameLoop;

import java.awt.Graphics;
import java.io.IOException;

import ChessObjects.King;
import ChessObjects.Knight;
import ChessObjects.Move;
import ChessObjects.Pawn;
import ChessObjects.Piece;
import ChessObjects.PieceHolder;
import ChessObjects.Queen;
import ChessObjects.Rook;
import ChessObjects.Square;
import Evaluation.MiniMax;




public class Handler {
	// first dimension in z axis is box objects
	// second dimension in z axis is piece objects.
	private Square[][] boxes = new Square[8][8];
	private String turn = "white";
	private PieceHolder<Piece> whitePieces;
	private PieceHolder<Piece> blackPieces;
	private MiniMax computer;
	
	//private ChessLogic logic;
	//private Box tempObject;
	// make more efficeint remember only one or 2 need to be ticked not all 
	// same for render
	public Handler() {
		
		if(GameChecks.COMPUTER)
			computer = new MiniMax();
	
	}
	public void initPieceHolders() {
		whitePieces = new PieceHolder<>();
		blackPieces = new PieceHolder<>();
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 8; j++) {
				blackPieces.add(boxes[j][i].getPiece());
			}
		}
		for(int i = 6; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				whitePieces.add(boxes[j][i].getPiece());
			}
		}
		
	}
	
	
	
	
	public void tick() {
		for(int i = 0; i < boxes.length; i++) {
			for(int j = 0; j < boxes[i].length; j++) {
				if(boxes[i][j] != null){
				boxes[i][j].tick();	
				}
			}	
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < boxes.length; i++) {
			for(int j = 0; j < boxes[i].length; j++) {
				if(boxes[i][j] != null) {
					boxes[i][j].render(g);
					if(boxes[i][j].getPiece() != null) {
						boxes[i][j].getPiece().render(g);
					}
				}
			}
		}
	}
	public void addSquare (Square box) {
		boxes[box.getX()][box.getY()] = box;
	}
	
	public void removePiece(Square box) {
		
	}
	public void move(int oldX, int oldY, int updatedX, int updatedY) throws InterruptedException, IOException, CloneNotSupportedException {
		Boolean valid = false; 
		try {
			//need to add check if in check or check mate
			if(turn.equals(boxes[oldX][oldY].getPiece().getColor()) && boxes[oldX][oldY].getPiece().validMove(oldX, oldY, updatedX, updatedY, boxes) ) {	
				//if valid move piece will be moved
				Piece emergency = boxes[updatedX][updatedY].getPiece();
				
				boxes[updatedX][updatedY].setPiece(boxes[oldX][oldY].getPiece());
				boxes[oldX][oldY].setPiece(null);
				boxes[updatedX][updatedY].getPiece().setX(updatedX);
				boxes[updatedX][updatedY].getPiece().setY(updatedY);
			
				//if move causes a check piececs will be reverted
				//
				if(!isCheck()) {
					if(GameChecks.KINGSIDECASTLE) {
						boxes[7][oldY].setPiece(null);
						boxes[5][oldY].setPiece(new Rook(5, oldY, boxes[updatedX][updatedY].getPiece().getColor()));
					
						GameChecks.KINGSIDECASTLE = false;
						
					}else if(GameChecks.QUEENSIDECASTLE) {
						boxes[0][oldY].setPiece(null);
						boxes[3][oldY].setPiece(new Rook(3, oldY, boxes[updatedX][updatedY].getPiece().getColor()));
						System.out.println("hello tick");
						GameChecks.QUEENSIDECASTLE = false;
						
					}
					
					if(emergency != null) {
						if(turn.equals("black")) {
							System.out.println(emergency.getX());
							whitePieces.removeIdx(emergency.getX(), emergency.getY());
							
						}else
							blackPieces.removeIdx(emergency.getX(), emergency.getY());	
					}
					
					valid = true;
					boxes[updatedX][updatedY].getPiece().setNumOfMoves(boxes[updatedX][updatedY].getPiece().getNumOfMoves() + 1);
					
				}else {
					
					boxes[oldX][oldY].setPiece(boxes[updatedX][updatedY].getPiece());
					boxes[oldX][oldY].getPiece().setX(oldX);
	 				boxes[oldX][oldY].getPiece().setY(oldY);
	 				boxes[oldX][oldY].getPiece().setNumOfMoves(boxes[oldX][oldY].getPiece().getNumOfMoves() - 1);
	 				boxes[updatedX][updatedY].setPiece(emergency);
	 				Square king = findKing();
	 				errorAnimation(king.getPiece().getX(), king.getPiece().getY());

				}
			}else {
				errorAnimation(updatedX, updatedY);	
				
			}
		}catch(NullPointerException e) {
			errorAnimation(updatedX, updatedY);
		}
		
		if(valid) {
			turn = (turn.equals("white"))? "black" : "white" ;
			GameChecks.CHECKMATE = checkMate();
			GameChecks.STALEMATE = staleMate();
	
			if(GameChecks.CHECKMATE) 
				GameChecks.WINNER = (turn.equals("white"))? true : false;
			
			GameChecks.TURN = (GameChecks.TURN)? false: true;
			pawnPromotion();
		}
	}
	
	public void computerMove() {
		Move m = computer.minimax(whitePieces, blackPieces, "black", 2 );
		Piece p = computer.getPiece();
	    
		System.out.printf("oldX: %d oldY: %d\n", p.getX(), p.getY());
		System.out.printf("updatedX: %d updatedY: %d\n", m.getX(), m.getY());
		try {
			move(p.getX(), p.getY(), m.getX(), m.getY());
		} catch (InterruptedException | IOException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		 
	
	
	}
	
	public void errorAnimation(int x, int y) {
		boxes[x][y].setErrorState(true);
	}
	
	// checks to see if player is in check
	public boolean isCheck() throws InterruptedException {
		Square king = findKing();
		int kingY = king.getPiece().getY();
		int kingX = king.getPiece().getX();
		for(int i = 0; i < boxes.length; i++) {
			for(int j = 0; j < boxes[i].length; j++) {
				Piece check = boxes[j][i].getPiece();
				
				if(check != null && !(check instanceof Pawn)) {
					if(!check.getColor().equals(turn) && check.validMove(j, i, kingX, kingY, boxes)) { 
						return true;
					}
				}else if(check != null && !check.getColor().equals(turn) && ((Pawn) check).validKillmove(j, i, kingX, kingY, boxes)) {
						return true;
				}
			}
		}
			return false;
	}
		// finds king
		public Square findKing() {
			for(int i = 0; i < boxes.length; i++) {
				for(int j =0; j < boxes[i].length; j++) {
					if(boxes[j][i].getPiece() != null && boxes[j][i].getPiece() instanceof King && turn.equals(boxes[j][i].getPiece().getColor()))
						return boxes[j][i];
				}
			}
				return null;
		}
		
		// 3 CASES:
		//KING CANNOT MOVE
		//NO PIECE CAN BLOCK CHECK
		//NO PIECE CAN KILL CHECKER
		public boolean checkMate() throws InterruptedException, IOException {
			Square king = findKing();
			int kingY = king.getPiece().getY();
			int kingX = king.getPiece().getX();
			return kingCannotMove(kingX, kingY) && !pieceCanDie(king) && !pieceCanBeBlocked(king);
		}
		

		//checks all squares to see if the king would be in check if moved there
		public boolean kingCannotMove(int kingX,int kingY) throws InterruptedException, IOException {
			boolean noMoves = true; 
			boolean[] check = new boolean[9];
			//set all boolean values to false
			
				
			if(kingX - 1 < 0 || kingCannotMoveHelper(kingX, kingY, -1, 0))
				check[0] = true;
			if(kingX + 1 > 7 || kingCannotMoveHelper(kingX, kingY, 1, 0))
				check[1] = true;
			if(kingY - 1 < 0 || kingCannotMoveHelper(kingX, kingY, 0, -1))
				check[2] = true;
			if(kingY + 1 > 7 || kingCannotMoveHelper(kingX, kingY, 0, 1))
				check[3] = true;
			if(kingX - 1 < 0 || kingY - 1 < 0 || kingCannotMoveHelper(kingX, kingY, -1, -1))
				check[4] = true;
			if(kingX - 1 < 0 || kingY + 1 > 7 || kingCannotMoveHelper(kingX, kingY, -1, 1))
				check[5] = true;
			if(kingX + 1 > 7 || kingY - 1 < 0 || kingCannotMoveHelper(kingX, kingY, 1, -1))
				check[6] = true;
			if(kingX + 1 > 7 || kingY + 1 > 7 || kingCannotMoveHelper(kingX, kingY, 1, 1))
				check[7] = true;
			if(kingCannotMoveHelper(kingX, kingY, 0, 0))
				check[8] = true;
			
			for(boolean item: check)
				if(!item)
					return false;
			
			return noMoves;
		}
		//will switch check if the king is in check in given coordinateds
		public boolean kingCannotMoveHelper(int kingX, int kingY, int x, int y) throws InterruptedException, IOException {
			boolean check = true;
			if(kingX == kingX + x && kingY == kingY + y) {
				return isCheck();
			}else 
				if(((King) boxes[kingX][kingY].getPiece()).checkMateTest(kingX, kingY, (kingX + x), (kingY + y), boxes)){
					Square flip = boxes[kingX + x][kingY + y];
					Square temp = new Square(kingX, kingY, boxes[kingX][kingY].getColor());
					temp.setPiece(null);
					boxes[kingX + x][kingY + y] = boxes[kingX][kingY];
					boxes[kingX + x][kingY + y].setColor(flip.getColor());
					boxes[kingX + x][kingY + y].getPiece().setX(kingX + x);
					boxes[kingX + x][kingY + y].getPiece().setY(kingY + y);
					boxes[kingX][kingY] = temp;
					check = isCheck();
					boxes[kingX][kingY] = boxes[kingX + x][kingY + y];
					boxes[kingX][kingY].setColor(temp.getColor());
					boxes[kingX + x][kingY + y] = flip;
					boxes[kingX][kingY].getPiece().setX(kingX);
					boxes[kingX][kingY].getPiece().setY(kingY);
			
			}
			return check;	
		}
		//checks to see if the piece checking the king can be killed
		public boolean pieceCanDie(Square king){
			Square checkPiece;
			try {
			checkPiece = checkingPiece(king);
			}catch(NullPointerException e){
				
				return false;
			}
			for(int i = 0; i < boxes.length; i++) {
				for(int j =0; j < boxes[i].length; j++) {
					if(boxes[j][i].getPiece() != null && !boxes[j][i].getPiece().equals(king.getPiece()) && boxes[j][i].getPiece().validMove(j, i, checkPiece.getPiece().getX(), checkPiece.getPiece().getY(), boxes) 
						&& !checkPiece.getPiece().getColor().equals(boxes[i][j].getPiece().getColor()))
						return true;
				}	
			}
			return false;
		}
		//finds the piece which is putting the king in check
		public Square checkingPiece(Square king) {
			
			for(int i = 0; i < boxes.length; i++) {
				for(int j =0; j < boxes[i].length; j++) {
					Piece check = boxes[j][i].getPiece();
					if(check != null && !(check instanceof Pawn)) { 
						if(check.validMove(j, i, king.getPiece().getX(), king.getPiece().getY(), boxes) && !(king.getPiece().getColor().equals(check.getColor())) ) 
							return boxes[j][i];
					}else if(check != null && ((Pawn) check).validKillmove(j, i, king.getPiece().getX(), king.getPiece().getY(), boxes) && !(king.getPiece().getColor().equals(check.getColor()))){
						return boxes[j][i];
					}
				}
			}
			return null;
		}
		// first we find the an array of the path the check piece can take then 
		// we will make a helper method which see's if any pieces on the kings team 
		// can move to that position on the path to block the checking piece
		public boolean pieceCanBeBlocked(Square king) { 
			Square checkPiece;
			try {
			checkPiece = checkingPiece(king);
			}catch(NullPointerException e) {
				return false;
			}
			
			int kingX = king.getPiece().getX();
			int kingY = king.getPiece().getY();
			int checkX = checkPiece.getPiece().getX();
			int checkY = checkPiece.getPiece().getY();
			int[] path;
			
			
			if( ((King)king.getPiece()).checkMateTest(kingX, kingY, checkX, checkY, boxes)) {
				return false;
			}else if(checkPiece.getPiece() instanceof Knight){
				return false;
			}else if(kingX == checkX) {
				if(checkY < kingY) {
					int dist = kingY - checkY;
					path = new int[dist * 2];
					int p = 0; 
					for(int i = 1; i < dist; i++) {
						path[p++] = checkY + i;
						path[p++] = kingX;
					}
				}else {
					int dist = checkY - kingY;
					path = new int[dist * 2];
					int p = 0; 
					for(int i = 1; i < dist; i++) {
						path[p++] = checkY - i;
						path[p++] = kingX;
					}
					for(int e: path)
						System.out.println(e);
					
				}
			}else if(kingY == checkY) {
				if(checkX < kingX) {
					int dist = kingX - checkX;
					path = new int[dist * 2];
					int p = 0; 
					for(int i = 1; i < dist; i++) {
						path[p++] = kingY;
						path[p++] = checkX + i;
					}
				}else {
					int dist = checkX - kingX;
					path = new int[dist * 2];
					int p = 0; 
					for(int i = 1; i < dist; i++) {
						path[p++] = kingY;
						path[p++] = checkX - i;
					}	
				}
			}else if(checkX < kingX) {
				if(checkY < kingY) {
					int dist = kingY - checkY;
					path = new int[dist * 2];
					int p = 0;
					for(int i = 0; i < dist; i++) {
						path[p++] = checkY + i;
						path[p++] =	checkX + i;
					}
				}else {
					int dist = kingY - checkY;
					path = new int[-(dist*2)];
					int p = 0;
					for(int i = 0; i > dist; i--) {
						path[p++] = checkY + i;
						path[p++] = checkX - i;
					}
				}
			}else if(checkX > kingX) {
				if(checkY < kingY) {
					int dist = kingY - checkY;
					path = new int[dist *2];
					int p = 0;
					for(int i = 0; i < dist; i++) {
						path[p++] = checkY + i;
						path[p++] = checkX - i;
					}
				}else {
					int dist = checkY - kingY;
					path = new int[dist*2];
					int p = 0;
					for(int i = 0; i < dist; i++) {
						path[p++] = checkY - i;
						path[p++] = checkX - i;
					}
				}
			}else return false;
			
			System.out.println(path.length);
			for(int i = 0; i < path.length / 2; i++) {
				
				
				if(posibleMove(path[i], path[++i], king))
					return true;
			}
			return false;		
		}
		public boolean posibleMove(int y, int x, Square king) {
			System.out.println("in possible moves");
			String color = king.getPiece().getColor();
			for(int i = 0; i < boxes.length; i++) {
				for(int j = 0; j < boxes[i].length; j++) {
				Piece piece = boxes[j][i].getPiece();
				if(piece != null && piece != king.getPiece() && piece.getColor().equals(color))
					if(piece.validMove(piece.getX(), piece.getY(), x, y, boxes)) {
						return true;
					}
				}
			}
			return false;	
		}
		public void pawnPromotion() throws IOException {
			for(int i = 0; i < boxes.length; i++) {
				for(int j = 0; j < boxes[i].length; j++) {
					Piece piece = boxes[j][i].getPiece();
					if(piece != null && piece instanceof Pawn && piece.getY() == 7) {
						boxes[j][i].setPiece(new Queen(j, i, piece.getColor()));
						
					}
					else if(piece != null && piece instanceof Pawn && piece.getY() == 0) {
						boxes[j][i].setPiece(new Queen(j, i, piece.getColor()));	
					}
				}
			}
		}
		public boolean staleMate() throws InterruptedException, IOException {
			Square king = findKing();
			int kingX = king.getPiece().getX();
			int kingY = king.getPiece().getY();
			System.out.println("StalemateMethod");
			return kingCannotMoveStalemate(kingX, kingY) && noMoves(king);
		}
		public boolean kingCannotMoveStalemate(int kingX,int kingY) throws InterruptedException, IOException {
			boolean noMoves = true; 
			boolean[] check = new boolean[8];
			//set all boolean values to false
			
				
			if(kingX - 1 < 0 || kingCannotMoveHelper(kingX, kingY, -1, 0))
				check[0] = true;
			if(kingX + 1 > 7 || kingCannotMoveHelper(kingX, kingY, 1, 0))
				check[1] = true;
			if(kingY - 1 < 0 || kingCannotMoveHelper(kingX, kingY, 0, -1))
				check[2] = true;
			if(kingY + 1 > 7 || kingCannotMoveHelper(kingX, kingY, 0, 1))
				check[3] = true;
			if(kingX - 1 < 0 || kingY - 1 < 0 || kingCannotMoveHelper(kingX, kingY, -1, -1))
				check[4] = true;
			if(kingX - 1 < 0 || kingY + 1 > 7 || kingCannotMoveHelper(kingX, kingY, -1, 1))
				check[5] = true;
			if(kingX + 1 > 7 || kingY - 1 < 0 || kingCannotMoveHelper(kingX, kingY, 1, -1))
				check[6] = true;
			if(kingX + 1 > 7 || kingY + 1 > 7 || kingCannotMoveHelper(kingX, kingY, 1, 1))
				check[7] = true;
			
			
			for(boolean item: check)
				if(!item)
					return false;
			
			return noMoves;
		}

		public boolean noMoves(Square king) {
			for(int i = 0; i < boxes.length; i++) {
				for(int j = 0; j < boxes[i].length; j++) {
					if(boxes[j][i].getPiece() != null && boxes[j][i].getPiece().getColor().equals(king.getPiece().getColor())
							&& !(boxes[j][i].getPiece() instanceof Knight) && !(boxes[j][i].getPiece() instanceof King)) {
						Piece piece = boxes[j][i].getPiece();
						if(j - 1 >= 0 && piece.validMove(j, i, j-1, i, boxes)) { 
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j + 1 <= 7 && piece.validMove(j, i, j + 1, i, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(i - 1 >= 0 &&  piece.validMove(j, i, j, i - 1, boxes)) {
							return false;
						}if(i + 1 <= 7 &&  piece.validMove(j, i, j, i + 1, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j - 1 >= 0 && i - 1 >= 0 &&  piece.validMove(j, i, j - 1, i -1, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j - 1 >= 0 && i + 1 <= 7 && piece.validMove(j, i, j - 1, i + 1, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j + 1 <= 7 && i - 1 >= 0 && piece.validMove(j, i, j + 1, i - 1, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j + 1 <= 7 && i + 1 <= 7 && piece.validMove(j, i, j + 1, i + 1, boxes )) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;	
						}
					}else if(boxes[j][i].getPiece() != null && boxes[j][i].getPiece().getColor().equals(king.getPiece().getColor()) 
							&& boxes[j][i].getPiece() instanceof Knight && !(boxes[j][i].getPiece() instanceof King)) {
						Piece piece = boxes[j][i].getPiece();
						if(j - 2 >= 0 && i - 1 >= 0 && piece.validMove(j, i, j - 2, i - 1, boxes)) { 
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j - 2 >= 0 && i + 1 <= 7  && piece.validMove(j, i, j - 2, i + 1, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j + 2 <= 7 && i - 1 >= 0 && piece.validMove(j, i, j + 2, i - 1, boxes)) {
							return false;
						}if(j + 2 <= 7 && i + 1 <= 7 && piece.validMove(j, i, j + 2, i + 1, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j - 1 >= 0 && i - 2 >= 0 &&  piece.validMove(j, i, j - 1, i - 2, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j + 1  <= 7 && i - 2 >= 0 && piece.validMove(j, i, j + 1, i - 2, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j + 1 <= 7 && i + 2 <= 7 && piece.validMove(j, i, j + 1, i + 2, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;
						}if(j - 1 >= 0 && i + 2 <= 7 && piece.validMove(j, i, j - 1, i + 2, boxes)) {
							piece.setNumOfMoves(piece.getNumOfMoves() - 1);
							return false;	
						}
					}			
				}
			}
			System.out.println("end of no  moves");
			return true;
		}
		
		

}
