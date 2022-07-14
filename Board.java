import java.util.*;
import java.io.*;

public class Board {
	
	public int [][] board;
	public ArrayList jumpedCheckers;
	
	public static final int blank = 0;
    public static final int player1 = 1;
	public static final int player2 = 3;
    public static final int playerKing1 = 2;
    public static final int playerKing2 = 4;
	
	//public void 
	Board(int dim) {
		board = new int[dim][dim];
		jumpedCheckers = new ArrayList();
        setUpBoard();
	}
	
	public void setUpBoard() { 
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
				
				if ( row % 2 == col % 2 ) { //white cells
					board[row][col] = blank;
				} 
				else {
					if (row < 3) {
						board[row][col] = player2; 
					}
					else if (row > 4) {
						board[row][col] = player1; 
					}
					else {
						board[row][col] = blank;
					}
				}
            }
        }
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
			board[row][col] = blank;
			System.out.println("clearRoute: row = " + row + " col = " + col);
			if (row == toRow) {
				break;
			}
			row += stepRow;
			col += stepCol;
		}
	}
	
	public void makeMove(Board b, int player, MovesMade move) { //method that takes in MovesMade type and makes a move

        makeMove(this, player, move.fromRow, move.fromCol, move.toRow, move.toCol);

    }

    public void makeMove(Board b, int player, int fromRow, int fromCol, int toRow, int toCol) { //makesMove (in database sense)
		int val = board[fromRow][fromCol];
		System.out.println("makeMove: from " + fromRow + fromCol + " to " + toRow + toCol);
		
		this.clearRoute(fromRow, fromCol, toRow, toCol);
		
		// up status
		if (toRow == 0 && val == player1) {
			val = playerKing1;
		}
		if (toRow == 7 && val == player2) {
			val = playerKing2;
		}

		// write current state
		board[toRow][toCol] = val;
	}

    public MovesMade[] getLegalMoves(int player) { //determines legal moves for player
		//System.out.println("getLegalMoves: start for player " + player);
		
        int playerKing;
		
        //identifies player's kings
        if (player == player1){
            playerKing = playerKing1;
        } else {
            playerKing = playerKing2;
        }

        ArrayList moves = new ArrayList(); //creates a new Array to story legal moves
		
        for (int row = 0; row < 8; row++){ //looks through all the squares of the boards

            for (int col = 0; col < 8; col++){
				
				if (board[row][col] == player){ //if a square belongs to the player
					
					//System.out.println("getLegalMoves: player " + player);
					//check all possible jumps around the piece - if one found the player must jump
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
				
				if (board[row][col] == playerKing){ //if a square belongs to the playerKing
					System.out.println("getLegalMoves: playerKing " + playerKing);		
					for (int i = 0; i < 7; i++) {
						if (canJumpKing(playerKing, row, col, row + i, col + i)) {
							moves.add(new MovesMade(this, playerKing, row, col, row + i, col + i));
							System.out.println("getLegalMoves: create jump for " + playerKing + " from " + row + col + " to " + (row + i) + (col + i));
						}
						if (canJumpKing(playerKing, row, col, row - i, col + i)) {
							moves.add(new MovesMade(this, playerKing, row, col, row - i, col + i));
							System.out.println("getLegalMoves: create jump for " + playerKing + " from " + row + col + " to " + (row + i) + (col + i));
						}
						if (canJumpKing(playerKing, row, col, row + i, col - i)) {
							moves.add(new MovesMade(this, playerKing, row, col, row + i, col - i));
							System.out.println("getLegalMoves: create jump for " + playerKing + " from " + row + col + " to " + (row + i) + (col + i));
						}
						if (canJumpKing(playerKing, row, col, row - i, col - i)) {
							moves.add(new MovesMade(this, playerKing, row, col, row - i, col - i));
							System.out.println("getLegalMoves: create jump for " + playerKing + " from " + row + col + " to " + (row + i) + (col + i));
						}
					}
				}
			}
        }


        if (moves.size() == 0){ //if there are no jumps
			System.out.println("getLegalMoves:  there are no jumps");
            for (int row = 0; row < 8; row++){ //look through all the squares again

                for (int col = 0; col < 8; col++){
					
					if (board[row][col] == player){ //if a square belongs to the player

						//check all possible normal moves around the piece - if one found, add it to the list
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
						System.out.println("getLegalMoves: finding basic moves for King");
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

        if (moves.size() == 0){ //if there are no normal moves
			System.out.println("getLegalMoves: moves.size() == 0");
            return null; //the player cannot move
        }else { //otherwise, an array is created to store all the possible moves
            MovesMade[] moveArray = new MovesMade[moves.size()];
            for (int i = 0; i < moves.size(); i++){
                moveArray[i] = (MovesMade)moves.get(i);
            }
            return moveArray;
        }

    } 

    public MovesMade[] getLegalJumpsFrom(int player, int row, int col){
		//determines legal jumps for player
		System.out.println("getLegalJumpsFrom: start row = " + row + " col = " + col + " board[row][col] = " + board[row][col]);
		int playerKing = 0;
		
		if (player == player1){
            playerKing = playerKing1;
        } else {
            playerKing = playerKing2;
        }

        ArrayList moves = new ArrayList(); //creates a new Array to story legal moves
		
		if (board[row][col] == player){ //if a square belongs to the player
					
			System.out.println("getLegalJumpsFrom: player " + player);
			//check all possible jumps around the piece - if one found the player must jump
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
		
		if (board[row][col] == playerKing){ //if a square belongs to the playerKing
			System.out.println("getLegalJumpsFrom: playerKing " + playerKing);				
			for (int i = 0; i < 7; i++) {
				if (canJumpKing(playerKing, row, col, row + i, col + i)) {
					moves.add(new MovesMade(this, playerKing, row, col, row + i, col + i));
					System.out.println("getLegalJumpsFrom: create jump for " + playerKing + " from " + row + col + " to " + (row + i) + (col + i));
				}
				if (canJumpKing(playerKing, row, col, row - i, col + i)) {
					moves.add(new MovesMade(this, playerKing, row, col, row - i, col + i));
					System.out.println("getLegalJumpsFrom: create jump for " + playerKing + " from " + row + col + " to " + (row + i) + (col + i));
				}
				if (canJumpKing(playerKing, row, col, row + i, col - i)) {
					moves.add(new MovesMade(this, playerKing, row, col, row + i, col - i));
					System.out.println("getLegalJumpsFrom: create jump for " + playerKing + " from " + row + col + " to " + (row + i) + (col + i));
				}
				if (canJumpKing(playerKing, row, col, row - i, col - i)) {
					moves.add(new MovesMade(this, playerKing, row, col, row - i, col - i));
					System.out.println("getLegalJumpsFrom: create jump for " + playerKing + " from " + row + col + " to " + (row + i) + (col + i));
				}
			}
		}
		
		if (moves.size() == 0) { //if there are no possible moves
			return null; //null is returned
		}
		//otherwise, an array is created to store all the possible moves
		MovesMade[] moveArray = new MovesMade[moves.size()];
		for (int i = 0; i < moves.size(); i++) {
			moveArray[i] = (MovesMade)moves.get(i);
		}
		return moveArray;
	}

    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3){ //method checks for possible jumps

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8 || r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) //if destination row or column is off board
            return false; //there is no jump, as the destination doesn't exist
			
		if (board[r1][c1] == blank) 
			return false;

        if (board[r3][c3] != blank) //if the destination isn't blank
            return false; //there is no jump, as the destination is taken
			
		if (board[r2][c2] == blank)
			return false;
		
        if (player == player1) { //in the case of player 1
		
			//System.out.println("canJump: search jumps for player1");
		
			if (board[r2][c2] != player2 && board[r2][c2] != playerKing2) //if the middle piece isn't player 2's
                return false; //there is no jump, as player 1 can't jump his own pieces
				
			System.out.println("canJump: find jump for player1 from " + r1 + c1 + " to " + r3 + c3);
            return true; //otherwise, jump is legal
        }
		
		if (player == player2) { //in the case of player 2
		
			//System.out.println("canJump: search jumps for player2");
		
			if (board[r2][c2] != player1 && board[r2][c2] != playerKing1) //if the middle piece isn't player 1's
				return false; //there is no jump, as player 2 can't jump his own pieces
			
            return true; //otherwise, jump is legal
        }
		return false;
    }
	
	private boolean canJumpKing(int player, int r1, int c1, int r2, int c2) {
		
		if (player == player1 || player == player2) {
			System.out.println("canJumpKing: player not a king");
			return false;
		}
		
		if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) //if destination row or column is off board
            return false; //there is no move, as the destination doesn't exist

        if (board[r2][c2] != blank) //if the destination isn't blank
            return false; //there is no move, as the destination is taken
		
		int opponent = 0;
		int opponentKing = 0;
		
		if (player == playerKing1) {
			opponent = player2;
			opponentKing = playerKing2;
		}
		else {
			opponent = player1;
			opponentKing = playerKing1;
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
				//System.out.println("canJumpKing: r2 > rd && c2 > cd " + r2 + rd + " " + c2 + cd);
				if (canMove(player, rd, cd, r2, c2)) {
					jumpedCheckers.add(new Point(rd, cd));
					//System.out.println("canJumpKing: canMove from " + r1 + c1 + " to " + r2 + c2);
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
					//System.out.println("canJumpKing: canMove from " + r1 + c1 + " to " + r2 + c2);
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
					System.out.println("canJumpKing: jumpedCheckers.add(new Point(rd, cd)); " + rd + cd);
					//System.out.println("canJumpKing: canMove from " + r1 + c1 + " to " + r2 + c2);
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
					//System.out.println("canJumpKing: canMove from " + r1 + c1 + " to " + r2 + c2);
					return true;
				}
			}
			else {
				return false;
			}					
		}
		return false;
	}

    private boolean canMove(int player, int r1, int c1, int r2, int c2){ //method checks for possible normal moves

        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) //if destination row or column is off board
            return false; //there is no move, as the destination doesn't exist

        if (board[r2][c2] != blank) //if the destination isn't blank
            return false; //there is no move, as the destination is taken

        if (player == player1) { //in the case of player 1
            if (board[r1][c1] == player1 && r2 > r1) //if destination row is greater than the original
                return false; //there is no move, as player 1 can only move upwards
            return true; //otherwise, move is legal
        }
		
		else if (player == player2) { //in the case of player 2
            if (board[r1][c1] == player2 && r2 < r1) //if destination row is less than the original
                return false; //there is no move, as player 2 can only move downwards
            return true; //otherwise, move is legal
        }
		
		else if (player == playerKing1 || player == playerKing2) {
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
