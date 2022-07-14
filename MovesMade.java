class MovesMade { //movesMade class begins
	
	Board b;
	int player;
    int fromRow;
	int	fromCol; //declares from row and column as public ints
    int toRow; 
	int toCol; //declares to row and column as public ints

    MovesMade(Board board, int player, int r1, int c1, int r2, int c2) { //movesMade constructor takes in selected squares and assigns them to public ints
		
		b = board;
		this.player = player;
        fromRow = r1;
        fromCol = c1;
        toRow = r2;
        toCol = c2;
    }
	
    boolean isJump() { //checks if move is a jump
		if ((player == 2 || player == 4)) {
			System.out.println("movesMade: playerKing. fromRow = " + fromRow + " fromCol = " + fromCol + " toRow = " + toRow + " toCol = " + toCol);
			if (fromRow >= toRow + 2 || fromRow <= toRow - 2) {
				System.out.println("movesMade: move can be jump");
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
						if (b.jumpedCheckers.size() == 0) {
							System.out.println("movesMade: jumpedCheckers.size() == 0. return false");
							return false;
						}
						
						Point[] jc = new Point[b.jumpedCheckers.size()];
						for (int k = 0; k < b.jumpedCheckers.size(); k++) {
							jc[k] = (Point)b.jumpedCheckers.get(k);
						}
						
						for (int j = 0; j < jc.length; j++) {
							if (fromRow + i == jc[j].row && fromCol + i == jc[j].col) {
								if (toRow > fromRow + i && toCol > fromCol + i) {
									System.out.println("movesMade: 1 isJump true");
									return true;
								}	
							}
						}
					}
					/*
					for (int i = 1; i < 8; i++) {
						if (b.pieceAt(fromRow + i, fromCol + i) == opponent || b.pieceAt(fromRow + i, fromCol + i) == opponentKing) {
							for (int j = 1; j < 8; j++) {
								if (b.pieceAt(fromRow + i + j, fromCol + i + j) != 0) {//проверка что стоит впереди 
									System.out.println("movesMade: if 1 return false");
									return false;
								}
								if (b.pieceAt(fromRow + i + j, fromCol + i + j) == 0 && (fromRow + i + j == toRow) && (fromCol + i + j == toCol)) {
									System.out.println("movesMade: isJump true");
									return true;
								}
							}					
						}
					}
					*/
				}
				
				if (fromRow > toRow && fromCol < toCol) {
					
					for (int i = 1; i < 8; i++) {
						if (b.jumpedCheckers.size() == 0) {
							System.out.println("movesMade: jumpedCheckers.size() == 0. return false");
							return false;
						}
						
						Point[] jc = new Point[b.jumpedCheckers.size()];
						for (int k = 0; k < b.jumpedCheckers.size(); k++) {
							jc[k] = (Point)b.jumpedCheckers.get(k);
						}
						
						for (int j = 0; j < jc.length; j++) {
							if (fromRow - i == jc[j].row && fromCol + i == jc[j].col) {
								if (toRow < fromRow - i && toCol > fromCol + i) {
									System.out.println("movesMade: 2 isJump true");		
									return true;
								}	
							}
						}
					}
				}
				
				if (fromRow < toRow && fromCol > toCol) {
					for (int i = 1; i < 8; i++) {
						if (b.jumpedCheckers.size() == 0) {
							System.out.println("movesMade: jumpedCheckers.size() == 0. return false");
							return false;
						}
						
						Point[] jc = new Point[b.jumpedCheckers.size()];
						for (int k = 0; k < b.jumpedCheckers.size(); k++) {
							jc[k] = (Point)b.jumpedCheckers.get(k);
						}
						
						for (int j = 0; j < jc.length; j++) {
							if (fromRow + i == jc[j].row && fromCol - i == jc[j].col) {
								if (toRow > fromRow + i && toCol < fromCol - i) {
									System.out.println("movesMade: 3 isJump true");
										
									return true;
								}	
							}
						}
						
					}
					System.out.println("movesMade: if 3 return false");
				}
				
				if (fromRow > toRow && fromCol > toCol) {
					for (int i = 1; i < 8; i++) {
						if (b.jumpedCheckers.size() == 0) {
							System.out.println("movesMade: jumpedCheckers.size() == 0. return false");
							return false;
						}
						
						Point[] jc = new Point[b.jumpedCheckers.size()];
						for (int k = 0; k < b.jumpedCheckers.size(); k++) {
							jc[k] = (Point)b.jumpedCheckers.get(k);
						}
						
						for (int j = 0; j < jc.length; j++) {
							if (fromRow - i == jc[j].row && fromCol - i == jc[j].col) {
								if (toRow < fromRow - i && toCol < fromCol - i) {
									System.out.println("movesMade: 4 isJump true");		
									return true;
								}	
							}
						}
					}
				}
			}
			System.out.println("movesMade: not a jump");
			return false;
		}
        else {
			return (fromRow - toRow == 2 || fromRow - toRow == -2);
		}
    }
	
	public boolean equals(MovesMade mm) {
		if (this.b == mm.b && this.player == mm.player && this.fromRow == mm.fromRow && 
		this.fromCol == mm.fromCol && this.toRow == mm.toRow && this.toCol == mm.toCol) {
			return true;
		}
		else {
			return false;
		}
	}
}