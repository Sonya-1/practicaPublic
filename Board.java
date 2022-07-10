import java.util.ArrayList;

public class Board {
	
	private int [][] board;
	
	public static final int blank = 0;
    public static final int player1 = 1;
	public static final int player2 = 3;
    public static final int playerKing1 = 2;
    public static final int playerKing2 = 4;
	
	//public void 
	Board(int dim) {
		board = new int[dim][dim];
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
	
	public void makeMove(Board b, int player, movesMade move) { //method that takes in movesMade type and makes a move

        makeMove(this, player, move.fromRow, move.fromCol, move.toRow, move.toCol);

    }

    public void makeMove(Board b, int player, int fromRow, int fromCol, int toRow, int toCol) { //makesMove (in database sense)

        board[toRow][toCol] = board[fromRow][fromCol]; //piece that was in original square is now in new square
        board[fromRow][fromCol] = blank; //the original square is now blank
		
		if (player == player1 || player == player2) {
			if (fromRow - toRow == 2 || fromRow - toRow == -2){ //if a move is a jump

				//the player jumps
				int jumpRow = (fromRow + toRow) / 2;
				int jumpCol = (fromCol + toCol) / 2;
				board[jumpRow][jumpCol] = blank; //the original square is not blank

			}
		}
        
		if (player == playerKing1 || player == playerKing2) {
			System.out.println("makeMove: move King");
			if (fromRow >= toRow + 2 || fromRow >= toRow - 2) {
				System.out.println("makeMove: King jump");
				int count1 = 0;
				int count2 = 0;
				int count3 = 0;
				int count4 = 0;
				int opponent = 0;
				int opponentKing = 0;
				
				if (player == 2) {
					opponent = 3;
					opponentKing = 4;
				}
				else {
					opponent = 1;
					opponentKing = 2;
				}

				if (fromRow < toRow && fromCol < toCol) {
					for (int i = 1; i < 8; i++) {
						if (this.pieceAt(fromRow + i, fromCol + i) == opponent || this.pieceAt(fromRow + i, fromCol + i) == opponentKing) {
							count1++;
						}
					}
				}
				
				if (fromRow > toRow && fromCol < toCol) {
					for (int i = 1; i < 8; i++) {
						if (this.pieceAt(fromRow - i, fromCol + i) == opponent || this.pieceAt(fromRow - i, fromCol + i) == opponentKing) {
							count2++;
						}
					}
				}
				
				if (fromRow < toRow && fromCol > toCol) {
					for (int i = 1; i < 8; i++) {
						if (this.pieceAt(fromRow + i, fromCol - i) == opponent || this.pieceAt(fromRow + i, fromCol - i) == opponentKing) {
							count3++;
						}
					}
				}
				
				if (fromRow > toRow && fromCol > toCol) {
					for (int i = 1; i < 8; i++) {
						if (this.pieceAt(fromRow - i, fromCol - i) == opponent || this.pieceAt(fromRow - i, fromCol - i) == opponentKing) {
							count4++;
						}
					}
				}
				
				Point jumpChecker = new Point();
				System.out.println("makeMove: make jumpChecker");
				
				if (count1 == 1) {
					for (int i = 1; i < 8; i++) {
						if (this.pieceAt(fromRow + i, fromCol + i) == opponent || this.pieceAt(fromRow + i, fromCol + i) == opponentKing) {
							System.out.println("makeMove: jumpChecker.row - " + (fromRow + i) + " jumpChecker.col - " + (fromCol + i));
							jumpChecker.row = fromRow + i;
							jumpChecker.col = fromCol + i;
							break;
						}
					} 
					board[jumpChecker.row][jumpChecker.col] = blank;
				}
				
				if (count2 == 1) {
					for (int i = 1; i < 8; i++) {
						if (this.pieceAt(fromRow - i, fromCol + i) == opponent || this.pieceAt(fromRow - i, fromCol + i) == opponentKing) {
							System.out.println("makeMove: jumpChecker.row - " + (fromRow - i) + " jumpChecker.col - " + (fromCol + i));
							jumpChecker.row = fromRow - i;
							jumpChecker.col = fromCol + i;
							break;
						}
					} 
					board[jumpChecker.row][jumpChecker.col] = blank;
				}
				
				if (count3 == 1) {
					for (int i = 1; i < 8; i++) {
						if (this.pieceAt(fromRow + i, fromCol - i) == opponent || this.pieceAt(fromRow + i, fromCol - i) == opponentKing) {
							System.out.println("makeMove: jumpChecker.row - " + (fromRow + i) + " jumpChecker.col - " + (fromCol - i));
							jumpChecker.row = fromRow + i;
							jumpChecker.col = fromCol - i;
							break;
						}
					} 
					board[jumpChecker.row][jumpChecker.col] = blank;
				}
				
				if (count4 == 1) {
					for (int i = 1; i < 8; i++) {
						if (this.pieceAt(fromRow - i, fromCol - i) == opponent || this.pieceAt(fromRow - i, fromCol - i) == opponentKing) {
							System.out.println("makeMove: jumpChecker.row - " + (fromRow - i) + " jumpChecker.col - " + (fromCol - i));
							jumpChecker.row = fromRow - i;
							jumpChecker.col = fromCol - i;
							break;
						}
					} 
					board[jumpChecker.row][jumpChecker.col] = blank;
				}
			}
		}

        if (toRow == 0 && board[toRow][toCol] == player1){ //if a player 1 piece reaches top row
            board[toRow][toCol] = playerKing1;
        }

        if (toRow == 7 && board[toRow][toCol] == player2){ //if a player 2 piece reaches bottom row
            board[toRow][toCol] = playerKing2; //it becomes a king
        }
    }

    public movesMade[] getLegalMoves(int player) { //determines legal moves for player

        if (player != player1 && player != player2) //if method is not called with a player
            return null; //null is returned

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

                    //check all possible jumps around the piece - if one found the player must jump
                    if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                        moves.add(new movesMade(this, player, row, col, row+2, col+2));
                    if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                        moves.add(new movesMade(this, player, row, col, row-2, col+2));
                    if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                        moves.add(new movesMade(this, player, row, col, row+2, col-2));
                    if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                        moves.add(new movesMade(this, player, row, col, row-2, col-2));

                }
				
				if (board[row][col] == playerKing) {
					System.out.println("getLegalMoves: finding jumps for King");
					for (int i = 1; i < 7; i++) {
						for (int j = 2; j < 7; j++) {
							if (canJump(player, row, col, row + i, col + i, row + j, col + j))
								moves.add(new movesMade(this, playerKing, row, col, row + j, col + j));
							
							if (canJump(player, row, col, row - i, col + i, row - j, col + j))
								moves.add(new movesMade(this, playerKing, row, col, row - j, col + j));
							
							if (canJump(player, row, col, row + i, col - i, row + j, col - j))
								moves.add(new movesMade(this, playerKing, row, col, row + j, col - j));
							
							if (canJump(player, row, col, row - i, col - i, row - j, col - j))
								moves.add(new movesMade(this, playerKing, row, col, row - j, col - j));
						}
					}
					
				}
				

            }

        }

        if (moves.size() == 0){ //if there are no jumps

            for (int row = 0; row < 8; row++){ //look through all the squares again

                for (int col = 0; col < 8; col++){

                    if (board[row][col] == player){ //if a square belongs to the player

                        //check all possible normal moves around the piece - if one found, add it to the list
                        if (canMove(player,row,col,row+1,col+1))
                            moves.add(new movesMade(this, player, row,col,row+1,col+1));
                        if (canMove(player,row,col,row-1,col+1))
                            moves.add(new movesMade(this, player, row,col,row-1,col+1));
                        if (canMove(player,row,col,row+1,col-1))
                            moves.add(new movesMade(this, player, row,col,row+1,col-1));
                        if (canMove(player,row,col,row-1,col-1))
                            moves.add(new movesMade(this, player, row,col,row-1,col-1));

                    }
					
					if (board[row][col] == playerKing) {
						System.out.println("getLegalMoves: finding basic moves for King");
						for (int i = 0; i < 7; i++) {
							for (int j = 0; j < 7; j++) {
								if (canMove(player, row, col, row + i, col + i))
									moves.add(new movesMade(this, playerKing, row, col, row + i, col + i));
									
								if (canMove(player, row, col, row - i, col + i))
									moves.add(new movesMade(this, playerKing, row, col, row - i, col + i));
									
								if (canMove(player, row, col, row + i, col - i))
									moves.add(new movesMade(this, playerKing, row, col, row + i, col - i));
									
								if (canMove(player, row, col, row - i, col - i))
									moves.add(new movesMade(this, playerKing, row, col, row - i, col - i));
							}
						}					
					}
					
                }
            }
        }

        if (moves.size() == 0){ //if there are no normal moves
            return null; //the player cannot move
        }else { //otherwise, an array is created to store all the possible moves
            movesMade[] moveArray = new movesMade[moves.size()];
            for (int i = 0; i < moves.size(); i++){
                moveArray[i] = (movesMade)moves.get(i);
            }
            return moveArray;
        }

    } 

    public movesMade[] getLegalJumpsFrom(int player, int row, int col){ //determines legal jumps for player

        if (player != player1 && player != player2) //if method is not called with a player
            return null; //null is returned

        int playerKing;

        //identifies player's kings
        if (player == player1){
            playerKing = playerKing1;
        }else {
            playerKing = playerKing2;
        }

        ArrayList moves = new ArrayList(); //creates a new Array to story legal moves

        if (board[row][col] == player){

            //if there is a possible jump, add it to list
            if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                moves.add(new movesMade(this, player, row, col, row+2, col+2));
            if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                moves.add(new movesMade(this, player, row, col, row-2, col+2));
            if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                moves.add(new movesMade(this, player, row, col, row+2, col-2));
            if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                moves.add(new movesMade(this, player, row, col, row-2, col-2));

        }
		if (board[row][col] == playerKing) {
			System.out.println("getLegalJumpsFrom: finding jumps for King");
			for (int i = 1; i < 7; i++) {
				for (int j = 2; j < 7; j++) {
					if (canJump(player, row, col, row + i, col + i, row + j, col + j))
						moves.add(new movesMade(this, playerKing, row, col, row + j, col + j));
							
					if (canJump(player, row, col, row - i, col + i, row - j, col + j))
						moves.add(new movesMade(this, playerKing, row, col, row - j, col + j));
							
					if (canJump(player, row, col, row + i, col - i, row + j, col - j))
						moves.add(new movesMade(this, playerKing, row, col, row + j, col - j));
							
					if (canJump(player, row, col, row - i, col - i, row - j, col - j))
						moves.add(new movesMade(this, playerKing, row, col, row - j, col - j));
				}
			}
					
		}
		
        if (moves.size() == 0){ //if there are no possible moves
            return null; //null is returned
        }else { //otherwise, an array is created to store all the possible moves
            movesMade[] moveArray = new movesMade[moves.size()];
            for (int i = 0; i < moves.size(); i++){
                moveArray[i] = (movesMade)moves.get(i);
            }
            return moveArray;
        }
    }
		

    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3){ //method checks for possible jumps

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8 || r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) //if destination row or column is off board
            return false; //there is no jump, as the destination doesn't exist

        if (board[r3][c3] != blank) //if the destination isn't blank
            return false; //there is no jump, as the destination is taken
			
		if (board[r2][c2] == blank)
			return false;
		
        if (player == player1) { //in the case of player 1
		
			if (board[r2][c2] != player2 && board[r2][c2] != playerKing2) //if the middle piece isn't player 2's
                return false; //there is no jump, as player 1 can't jump his own pieces

            return true; //otherwise, jump is legal
        }
		
		else if (player == player2) { //in the case of player 2
		
			if (board[r2][c2] != player1 && board[r2][c2] != playerKing1) //if the middle piece isn't player 1's
				return false; //there is no jump, as player 2 can't jump his own pieces
			
            return true; //otherwise, jump is legal
        }
		
		else if (player == playerKing1) {
			
			System.out.println("canJump: search jumps for playerKing1");
			
			if (board[r2][c2] != player2 && board[r2][c2] != playerKing2) //if the middle piece isn't player 2's
                return false; //there is no jump, as player 1 can't jump his own pieces
			
			int count = 0;
			if (r1 < r3 && c1 < c3) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 + i, c1 + i) == player2 || this.pieceAt(r1 + i, c1 + i) == playerKing2) {
						for (int j = 2; j < 8; j++) {
							if (this.pieceAt(r1 + i + j, c1 + i + j) != 0) 
								return false;
							if (this.pieceAt(r1 + i + j, c1 + i + j) == 0 && (r1 + i + j == r3) && (c1 + i + j == c3)) {
								System.out.println("canJump: true");
								return true;
							}
						}
					}
				}
			}
			
			if (r1 > r3 && c1 < c3) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 - i, c1 + i) == player2 || this.pieceAt(r1 - i, c1 + i) == playerKing2) {
						for (int j = 2; j < 8; j++) {
							if (this.pieceAt(r1 - i - j, c1 + i + j) != 0) 
								return false;
							if (this.pieceAt(r1 - i - j, c1 + i + j) == 0 && (r1 - i - j == r3) && (c1 + i + j == c3)) {
								System.out.println("canJump: true");
								return true;
							}
						}
					}
				}
			}
			
			if (r1 < r3 && c1 > c3) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 + i, c1 - i) == player2 || this.pieceAt(r1 + i, c1 - i) == playerKing2) {
						for (int j = 2; j < 8; j++) {
							if (this.pieceAt(r1 + i + j, c1 - i - j) != 0) 
								return false;
							if (this.pieceAt(r1 + i + j, c1 - i - j) == 0 && (r1 + i + j == r3) && (c1 - i - j == c3)) {
								System.out.println("canJump: true");
								return true;
							}
						}
					}
				}
			}
			
			if (r1 > r3 && c1 > c3) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 - i, c1 - i) == player2 || this.pieceAt(r1 - i, c1 - i) == playerKing2) {
						for (int j = 2; j < 8; j++) {
							if (this.pieceAt(r1 - i - j, c1 - i - j) != 0) 
								return false;
							if (this.pieceAt(r1 - i - j, c1 - i - j) == 0 && (r1 - i - j == r3) && (c1 - i - j == c3)) {
								System.out.println("canJump: true");
								return true;
							}
						}
					}
				}
			}
			/*
			int count = 0;
			if (r3 > r1) {
				for (int i = r3; i < r1; i--) {
					if (this.pieceAt(r3 + i, c3 + i) == 3 || this.pieceAt(r3 + i, c3 + i) == 4) {
						count++;
					}
				}
				if (count > 1) {
					return false;
				}
			}
			
			if (r3 < r1) {
				for (int i = r3; i < r1; i++) {
					if (this.pieceAt(r3 + i, c3 + i) == 3 || this.pieceAt(r3 + i, c3 + i) == 4) {
						count++;
					}
				}
				if (count > 1) {
					return false;
				}
			}
			*/
			System.out.println("playerKing1 canJump to " + r3 + " " + c3);
			return true;
		}
		
		else if (player == playerKing2) {
			
			System.out.println("canJump: search jumps for playerKing2");
			
			if (board[r2][c2] != player1 && board[r2][c2] != playerKing1) //if the middle piece isn't player 1's
                return false; //there is no jump, as player 2 can't jump his own pieces
				
			int count = 0;
			if (r1 < r3 && c1 < c3) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 + i, c1 + i) == player1 || this.pieceAt(r1 + i, c1 + i) == playerKing1) {
						for (int j = 2; j < 8; j++) {
							if (this.pieceAt(r1 + i + j, c1 + i + j) != 0) 
								return false;
							if (this.pieceAt(r1 + i + j, c1 + i + j) == 0 && (r1 + i + j == r3) && (c1 + i + j == c3)) {
								System.out.println("canJump: true");
								return true;
							}
						}
					}
				}
			}
			
			if (r1 > r3 && c1 < c3) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 - i, c1 + i) == player1 || this.pieceAt(r1 - i, c1 + i) == playerKing1) {
						for (int j = 2; j < 8; j++) {
							if (this.pieceAt(r1 - i - j, c1 + i + j) != 0) 
								return false;
							if (this.pieceAt(r1 - i - j, c1 + i + j) == 0 && (r1 - i - j == r3) && (c1 + i + j == c3)) {
								System.out.println("canJump: true");
								return true;
							}
						}
					}
				}
			}
			
			if (r1 < r3 && c1 > c3) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 + i, c1 - i) == player1 || this.pieceAt(r1 + i, c1 - i) == playerKing1) {
						for (int j = 2; j < 8; j++) {
							if (this.pieceAt(r1 + i + j, c1 - i - j) != 0) 
								return false;
							if (this.pieceAt(r1 + i + j, c1 - i - j) == 0 && (r1 + i + j == r3) && (c1 - i - j == c3)) {
								System.out.println("canJump: true");
								return true;
							}
						}
					}
				}
			}
			
			if (r1 > r3 && c1 > c3) {
				for (int i = 1; i < 8; i++) {
					if (this.pieceAt(r1 - i, c1 - i) == player1 || this.pieceAt(r1 - i, c1 - i) == playerKing1) {
						for (int j = 2; j < 8; j++) {
							if (this.pieceAt(r1 - i - j, c1 - i - j) != 0) 
								return false;
							if (this.pieceAt(r1 - i - j, c1 - i - j) == 0 && (r1 - i - j == r3) && (c1 - i - j == c3)) {
								System.out.println("canJump: true");
								return true;
							}
						}
					}
				}
			}	
			
			/*
			int count = 0;
			if (r3 > r1) {
				for (int i = r3; i < r1; i--) {
					if (this.pieceAt(r3 + i, c3 + i) == 1 || this.pieceAt(r3 + i, c3 + i) == 2) {
						count++;
					}
				}
				if (count > 1) {
					return false;
				}
			}
			
			if (r3 < r1) {
				for (int i = r3; i < r1; i++) {
					if (this.pieceAt(r3 + i, c3 + i) == 1 || this.pieceAt(r3 + i, c3 + i) == 2) {
						count++;
					}
				}
				if (count > 1) {
					return false;
				}
			}
			*/
			System.out.println("playerKing2 canJump to " + r3 + " " + c3);			
			return true;
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
		
		return true;
    }
}
