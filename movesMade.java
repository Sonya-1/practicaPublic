class movesMade { //movesMade class begins
	
	Board b;
	int player;
    int fromRow;
	int	fromCol; //declares from row and column as public ints
    int toRow; 
	int toCol; //declares to row and column as public ints

    movesMade(Board board, int player, int r1, int c1, int r2, int c2) { //movesMade constructor takes in selected squares and assigns them to public ints
		
		b = board;
		this.player = player;
        fromRow = r1;
        fromCol = c1;
        toRow = r2;
        toCol = c2;
    }
	
    boolean isJump() { //checks if move is a jump
		if ((player == 2 || player == 4) && (fromRow >= toRow + 2 || fromRow >= toRow - 2)) {
			
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
			
			int count = 0;
			if (fromRow < toRow && fromCol < toCol) {
				for (int i = 1; i < 8; i++) {
					if (b.pieceAt(fromRow + i, fromCol + i) == opponent || b.pieceAt(fromRow + i, fromCol + i) == opponentKing) {
						for (int j = 2; j < 8; j++) {
							if (b.pieceAt(fromRow - i - j, fromCol + i + j) != 0) 
								return false;
							if (b.pieceAt(fromRow + i + j, fromCol + i + j) == 0 && (fromRow + i + j == toRow) && (fromCol + i + j == toCol)) {
								System.out.println("movesMade: isJump true");
								return true;
							}
						}					
					}
				}
			}
			
			if (fromRow > toRow && fromCol < toCol) {
				for (int i = 1; i < 8; i++) {
					if (b.pieceAt(fromRow - i, fromCol + i) == opponent || b.pieceAt(fromRow - i, fromCol + i) == opponentKing) {
						for (int j = 2; j < 8; j++) {
							if (b.pieceAt(fromRow - i - j, fromCol + i + j) != 0) 
								return false;
							if (b.pieceAt(fromRow - i - j, fromCol + i + j) == 0 && (fromRow - i - j == toRow) && (fromCol + i + j == toCol)) {
								System.out.println("movesMade: isJump true");
								return true;
							}
						}
					}
				}
			}
			
			if (fromRow < toRow && fromCol > toCol) {
				for (int i = 1; i < 8; i++) {
					if (b.pieceAt(fromRow + i, fromCol - i) == opponent || b.pieceAt(fromRow + i, fromCol - i) == opponentKing) {
						for (int j = 2; j < 8; j++) {
							if (b.pieceAt(fromRow - i - j, fromCol + i + j) != 0) 
								return false;
							if (b.pieceAt(fromRow + i + j, fromCol - i - j) == 0 && (fromRow + i + j == toRow) && (fromCol - i - j == toCol)) {
								System.out.println("movesMade: isJump true");
								return true;
							}
						}
					}
				}
			}
			
			if (fromRow > toRow && fromCol > toCol) {
				for (int i = 1; i < 8; i++) {
					if (b.pieceAt(fromRow - i, fromCol - i) == opponent || b.pieceAt(fromRow - i, fromCol - i) == opponentKing) {
						for (int j = 2; j < 8; j++) {
							if (b.pieceAt(fromRow - i - j, fromCol + i + j) != 0) 
								return false;
							if (b.pieceAt(fromRow -i - j, fromCol - i - j) == 0 && (fromRow - i - j == toRow) && (fromCol - i - j == toCol)) {
								System.out.println("movesMade: isJump true");
								return true;
							}
						}
					}
				}
			}
			
			System.out.println("movesMade: return false in the end");
			return false;
		}
        else {
			return (fromRow - toRow == 2 || fromRow - toRow == -2);
		}
    }

}