class MovesMade { 
	
	protected Board b;
	protected int player;
    protected int fromRow;
	protected int fromCol; 
    protected int toRow; 
	protected int toCol;

    MovesMade(Board board, int player, int r1, int c1, int r2, int c2) { 
		
		b = board;
		this.player = player;
        fromRow = r1;
        fromCol = c1;
        toRow = r2;
        toCol = c2;
    }
	
    boolean isJump() { 
		if ((player == b.PLAYER_KING_1 || player == b.PLAYER_KING_2)) {
			if (fromRow >= toRow + 2 || fromRow <= toRow - 2) {
				int opponent = 0;
				int opponentKing = 0;
				
				if (player == b.PLAYER_KING_1) {
					opponent = b.PLAYER_2;
					opponentKing = b.PLAYER_KING_2;
				}
				else {
					opponent = b.PLAYER_1;
					opponentKing = b.PLAYER_KING_1;
				}
				
				int count = 0;
				if (fromRow < toRow && fromCol < toCol) {
					for (int i = 1; i < 8; i++) {
						if (b.jumpedCheckers.size() == 0) {
							return false;
						}
						
						Point[] jc = new Point[b.jumpedCheckers.size()];
						for (int k = 0; k < b.jumpedCheckers.size(); k++) {
							jc[k] = (Point)b.jumpedCheckers.get(k);
						}
						
						for (int j = 0; j < jc.length; j++) {
							if (fromRow + i == jc[j].row && fromCol + i == jc[j].col) {
								if (toRow > fromRow + i && toCol > fromCol + i) {
									return true;
								}	
							}
						}
					}
				}
				
				if (fromRow > toRow && fromCol < toCol) {
					
					for (int i = 1; i < 8; i++) {
						if (b.jumpedCheckers.size() == 0) {
							return false;
						}
						
						Point[] jc = new Point[b.jumpedCheckers.size()];
						for (int k = 0; k < b.jumpedCheckers.size(); k++) {
							jc[k] = (Point)b.jumpedCheckers.get(k);
						}
						
						for (int j = 0; j < jc.length; j++) {
							if (fromRow - i == jc[j].row && fromCol + i == jc[j].col) {
								if (toRow < fromRow - i && toCol > fromCol + i) {	
									return true;
								}	
							}
						}
					}
				}
				
				if (fromRow < toRow && fromCol > toCol) {
					for (int i = 1; i < 8; i++) {
						if (b.jumpedCheckers.size() == 0) {
							return false;
						}
						
						Point[] jc = new Point[b.jumpedCheckers.size()];
						for (int k = 0; k < b.jumpedCheckers.size(); k++) {
							jc[k] = (Point)b.jumpedCheckers.get(k);
						}
						
						for (int j = 0; j < jc.length; j++) {
							if (fromRow + i == jc[j].row && fromCol - i == jc[j].col) {
								if (toRow > fromRow + i && toCol < fromCol - i) {										
									return true;
								}	
							}
						}
						
					}
				}
				
				if (fromRow > toRow && fromCol > toCol) {
					for (int i = 1; i < 8; i++) {
						if (b.jumpedCheckers.size() == 0) {
							return false;
						}
						
						Point[] jc = new Point[b.jumpedCheckers.size()];
						for (int k = 0; k < b.jumpedCheckers.size(); k++) {
							jc[k] = (Point)b.jumpedCheckers.get(k);
						}
						
						for (int j = 0; j < jc.length; j++) {
							if (fromRow - i == jc[j].row && fromCol - i == jc[j].col) {
								if (toRow < fromRow - i && toCol < fromCol - i) {
									return true;
								}	
							}
						}
					}
				}
			}
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