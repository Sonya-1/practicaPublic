import java.util.*;

public class Board {
	
	protected int [][] board;
	protected ArrayList jumpedCheckers;
	
	public static final int BLANK = 0;
    public static final int PLAYER_1 = 1;
	public static final int PLAYER_2 = 3;
    public static final int PLAYER_KING_1 = 2;
    public static final int PLAYER_KING_2 = 4;

	Board(int dim) {
		board = new int[dim][dim];
		jumpedCheckers = new ArrayList();
        setUpBoard();
	}
	
	public void setUpBoard() { 
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
				
				if ( row % 2 == col % 2 ) { 
					board[row][col] = BLANK;
				} 
				else {
					if (row < 3) {
						board[row][col] = PLAYER_2; 
					}
					else if (row > 4) {
						board[row][col] = PLAYER_1; 
					}
					else {
						board[row][col] = BLANK;
					}
				}
            }
        }
    }
	
	public void clearJumpedCheckers() {
		jumpedCheckers.clear();
	}
	
	public int pieceAt(int row, int col) {
		if (row < 0 || row > 7 || col < 0 || col > 7) {
			return -1;
		}
        return board[row][col];
    }
	
	public void set(int val, int row, int col) {
		if (row < 0 || row > 7 || col < 0 || col > 7) {
			return;
		}
		board[row][col] = val;
	}
	
	public void clearRoute(int row, int col, int toRow, int toCol) {
		int stepRow = row < toRow ? 1 : -1;
		int stepCol = col < toCol ? 1 : -1;

		while (true) {
			board[row][col] = BLANK;
			if (row == toRow) {
				break;
			}
			row += stepRow;
			col += stepCol;
		}
	}
	
	public void makeMove(Board b, int player, MovesMade move) { 
        makeMove(this, player, move.fromRow, move.fromCol, move.toRow, move.toCol);
    }

    public void makeMove(Board b, int player, int fromRow, int fromCol, int toRow, int toCol) { 
		int val = board[fromRow][fromCol];
		this.clearRoute(fromRow, fromCol, toRow, toCol);

		if (toRow == 0 && val == PLAYER_1) {
			val = PLAYER_KING_1;
		}
		if (toRow == 7 && val == PLAYER_2) {
			val = PLAYER_KING_2;
		}

		board[toRow][toCol] = val;
	}

    public MovesMade[] getLegalMoves(int player) { 
        int playerKing;

        if (player == PLAYER_1){
            playerKing = PLAYER_KING_1;
        } else {
            playerKing = PLAYER_KING_2;
        }

        ArrayList moves = new ArrayList(); 
		
        for (int row = 0; row < 8; row++){ 
            for (int col = 0; col < 8; col++){				
				if (board[row][col] == player){ 
					if (canJump(player, row, col, row+1, col+1, row+2, col+2)) {
						moves.add(new MovesMade(this, player, row, col, row+2, col+2));
					}
					if (canJump(player, row, col, row-1, col+1, row-2, col+2)) {
						moves.add(new MovesMade(this, player, row, col, row-2, col+2));
					}
					if (canJump(player, row, col, row+1, col-1, row+2, col-2)) {
						moves.add(new MovesMade(this, player, row, col, row+2, col-2));
					}
					if (canJump(player, row, col, row-1, col-1, row-2, col-2)) {
						moves.add(new MovesMade(this, player, row, col, row-2, col-2));
					}
				}
				
				if (board[row][col] == playerKing){ 		
					for (int i = 0; i < 7; i++) {
						if (canJumpKing(playerKing, row, col, row + i, col + i)) {
							moves.add(new MovesMade(this, playerKing, row, col, row + i, col + i));
						}
						if (canJumpKing(playerKing, row, col, row - i, col + i)) {
							moves.add(new MovesMade(this, playerKing, row, col, row - i, col + i));
						}
						if (canJumpKing(playerKing, row, col, row + i, col - i)) {
							moves.add(new MovesMade(this, playerKing, row, col, row + i, col - i));
						}
						if (canJumpKing(playerKing, row, col, row - i, col - i)) {
							moves.add(new MovesMade(this, playerKing, row, col, row - i, col - i));
						}
					}
				}
			}
        }


        if (moves.size() == 0){ 
            for (int row = 0; row < 8; row++){ 
				for (int col = 0; col < 8; col++){
					if (board[row][col] == player){ 
						if (canMove(player,row,col,row+1,col+1))
							moves.add(new MovesMade(this, player, row,col,row+1,col+1));
						if (canMove(player,row,col,row-1,col+1))
							moves.add(new MovesMade(this, player, row,col,row-1,col+1));
						if (canMove(player,row,col,row+1,col-1))
							moves.add(new MovesMade(this, player, row,col,row+1,col-1));
						if (canMove(player,row,col,row-1,col-1))
							moves.add(new MovesMade(this, player, row,col,row-1,col-1));
					}
					
					if (board[row][col] == playerKing) {
						for (int i = 0; i < 7; i++) {
							if (canMove(playerKing, row, col, row + i, col + i))
								moves.add(new MovesMade(this, playerKing, row, col, row + i, col + i));										
							if (canMove(playerKing, row, col, row - i, col + i))
								moves.add(new MovesMade(this, playerKing, row, col, row - i, col + i));									
							if (canMove(playerKing, row, col, row + i, col - i))
								moves.add(new MovesMade(this, playerKing, row, col, row + i, col - i));										
							if (canMove(playerKing, row, col, row - i, col - i)) {
								moves.add(new MovesMade(this, playerKing, row, col, row - i, col - i));
							}
						}					
					}
                }
            }
        }

        if (moves.size() == 0){ 
            return null; 
        }else { 
            MovesMade[] moveArray = new MovesMade[moves.size()];
            for (int i = 0; i < moves.size(); i++){
                moveArray[i] = (MovesMade)moves.get(i);
            }
            return moveArray;
        }

    } 

    public MovesMade[] getLegalJumpsFrom(int player, int row, int col){
		int playerKing = 0;
		
		if (player == PLAYER_1){
            playerKing = PLAYER_KING_1;
        } else {
            playerKing = PLAYER_KING_2;
        }

        ArrayList moves = new ArrayList(); 
		
		if (board[row][col] == player){ 
			if (canJump(player, row, col, row+1, col+1, row+2, col+2)) {
				moves.add(new MovesMade(this, player, row, col, row+2, col+2));
			}
			if (canJump(player, row, col, row-1, col+1, row-2, col+2)) {
				moves.add(new MovesMade(this, player, row, col, row-2, col+2));
			}
			if (canJump(player, row, col, row+1, col-1, row+2, col-2)) {
				moves.add(new MovesMade(this, player, row, col, row+2, col-2));
			}
			if (canJump(player, row, col, row-1, col-1, row-2, col-2)) {
				moves.add(new MovesMade(this, player, row, col, row-2, col-2));
			}
		}
		
		if (board[row][col] == playerKing){ 				
			for (int i = 0; i < 7; i++) {
				if (canJumpKing(playerKing, row, col, row + i, col + i)) {
					moves.add(new MovesMade(this, playerKing, row, col, row + i, col + i));
				}
				if (canJumpKing(playerKing, row, col, row - i, col + i)) {
					moves.add(new MovesMade(this, playerKing, row, col, row - i, col + i));
				}
				if (canJumpKing(playerKing, row, col, row + i, col - i)) {
					moves.add(new MovesMade(this, playerKing, row, col, row + i, col - i));
				}
				if (canJumpKing(playerKing, row, col, row - i, col - i)) {
					moves.add(new MovesMade(this, playerKing, row, col, row - i, col - i));
				}
			}
		}
		
		if (moves.size() == 0) { 
			return null; 
		}
		MovesMade[] moveArray = new MovesMade[moves.size()];
		for (int i = 0; i < moves.size(); i++) {
			moveArray[i] = (MovesMade)moves.get(i);
		}
		return moveArray;
	}

    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3){ 

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8 || r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) 
            return false; 
			
		if (board[r1][c1] == BLANK) 
			return false;

        if (board[r3][c3] != BLANK) 
            return false; 
			
		if (board[r2][c2] == BLANK)
			return false;
		
        if (player == PLAYER_1) { 	
			if (board[r2][c2] != PLAYER_2 && board[r2][c2] != PLAYER_KING_2) 
                return false; 
            return true; 
        }
		
		if (player == PLAYER_2) { 
			if (board[r2][c2] != PLAYER_1 && board[r2][c2] != PLAYER_KING_1) 
				return false; 
            return true; 
        }
		return false;
    }
	
	private boolean canJumpKing(int player, int r1, int c1, int r2, int c2) {
		
		if (player == PLAYER_1 || player == PLAYER_2) {
			return false;
		}
		
		if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) 
            return false; 

        if (board[r2][c2] != BLANK) 
            return false; 
		
		int opponent = 0;
		int opponentKing = 0;
		
		if (player == PLAYER_KING_1) {
			opponent = PLAYER_2;
			opponentKing = PLAYER_KING_2;
		}
		else {
			opponent = PLAYER_1;
			opponentKing = PLAYER_KING_1;
		}
		
		int rd = 0;
		int cd = 0;
		if (r1 < r2 && c1 < c2) {
				
			for (int i = 1; i < 8; i++) {
				if (this.pieceAt(r1 + i, c1 + i) == opponent || this.pieceAt(r1 + i, c1 + i) == opponentKing) {
					rd = r1 + i;
					cd = c1 + i;
					break;
				}
			}
				
			if (r2 > rd && c2 > cd && cd != 0 && rd != 0) {
				if (canMove(player, rd, cd, r2, c2)) {
					jumpedCheckers.add(new Point(rd, cd));
					return true;
				}
			}
			else {
				return false;
			}
		}
			
		if (r1 > r2 && c1 < c2) {
			for (int i = 1; i < 8; i++) {
				if (this.pieceAt(r1 - i, c1 + i) == 1 || this.pieceAt(r1 - i, c1 + i) == 2 || 
				this.pieceAt(r1 - i, c1 + i) == 3 || this.pieceAt(r1 - i, c1 + i) == 4) {
					rd = r1 - i;
					cd = c1 + i;
					break;
				}
			}
				
			if ((r2 < rd && c2 > cd) && cd != 0 && rd != 0) {
				if (canMove(player, rd, cd, r2, c2)) {
					jumpedCheckers.add(new Point(rd, cd));
					return true;
				}
			}
			else {
				return false;
			}
		}
	
		if (r1 < r2 && c1 > c2) {
			for (int i = 1; i < 8; i++) {
				if (this.pieceAt(r1 + i, c1 - i) == 1 || this.pieceAt(r1 + i, c1 - i) == 2 || 
				this.pieceAt(r1 + i, c1 - i) == 3 || this.pieceAt(r1 + i, c1 - i) == 4) {
					rd = r1 + i;
					cd = c1 - i;
					break;
				}
			}
				
			if ((r2 > rd && c2 < cd) && cd != 0 && rd != 0) {
				if (canMove(player, rd, cd, r2, c2)) {
					jumpedCheckers.add(new Point(rd, cd));
					return true;
				}
			}
			else {
				return false;
			}
		}
			
		if (r1 > r2 && c1 > c2) {
			for (int i = 1; i < 8; i++) {
				if (this.pieceAt(r1 - i, c1 - i) == 1 || this.pieceAt(r1 - i, c1 - i) == 2 || 
				this.pieceAt(r1 - i, c1 - i) == 3 || this.pieceAt(r1 - i, c1 - i) == 4) {
					rd = r1 - i;
					cd = c1 - i;
					break;
				}
			}
				
			if ((r2 < rd && c2 < cd) && cd != 0 && rd != 0) {
				if (canMove(player, rd, cd, r2, c2)) {
					jumpedCheckers.add(new Point(rd, cd));
					return true;
				}
			}
			else {
				return false;
			}					
		}
		return false;
	}

    private boolean canMove(int player, int r1, int c1, int r2, int c2){ 

        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) 
            return false; 

        if (board[r2][c2] != BLANK) 
            return false; 

        if (player == PLAYER_1) { 
            if (board[r1][c1] == PLAYER_1 && r2 > r1) 
                return false;
            return true; 
        }
		
		else if (player == PLAYER_2) { 
            if (board[r1][c1] == PLAYER_2 && r2 < r1) 
                return false; 
            return true;
        }
		
		else if (player == PLAYER_KING_1 || player == PLAYER_KING_2) {
			int rd = 0;
			int cd = 0;
			if (r1 < r2 && c1 < c2) {
				
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 + i, c1 + i) == 1 || this.pieceAt(r1 + i, c1 + i) == 2 || 
					this.pieceAt(r1 + i, c1 + i) == 3 || this.pieceAt(r1 + i, c1 + i) == 4) {
						rd = r1 + i;
						cd = c1 + i;
						break;
					}
				}
				
				if ((r2 < rd && c2 < cd) || (rd == 0 && cd == 0)) {
					return true;
				}
				else {
					return false;
				}
			}
			

			if (r1 > r2 && c1 < c2) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 - i, c1 + i) == 1 || this.pieceAt(r1 - i, c1 + i) == 2 || 
					this.pieceAt(r1 - i, c1 + i) == 3 || this.pieceAt(r1 - i, c1 + i) == 4) {
						rd = r1 - i;
						cd = c1 + i;
						break;
					}
				}
				
				if ((r2 > rd && c2 < cd) || (rd == 0 && cd == 0)) {
					return true;
				}
				else {
					return false;
				}
			}
			
			if (r1 < r2 && c1 > c2) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 + i, c1 - i) == 1 || this.pieceAt(r1 + i, c1 - i) == 2 || 
					this.pieceAt(r1 + i, c1 - i) == 3 || this.pieceAt(r1 + i, c1 - i) == 4) {
						rd = r1 + i;
						cd = c1 - i;
						break;
					}
				}
				
				if ((r2 < rd && c2 > cd) || (rd == 0 && cd == 0)) {
					return true;
				}
				else {
					return false;
				}
			}
			
			if (r1 > r2 && c1 > c2) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 - i, c1 - i) == 1 || this.pieceAt(r1 - i, c1 - i) == 2 || 
					this.pieceAt(r1 - i, c1 - i) == 3 || this.pieceAt(r1 - i, c1 - i) == 4) {
						rd = r1 - i;
						cd = c1 - i;
						break;
					}
				}
				
				if ((r2 > rd && c2 > cd) || (rd == 0 && cd == 0)) {
					return true;
				}
				else {
					return false;
				}					
			}
		}
		
		return false;
    }
}
