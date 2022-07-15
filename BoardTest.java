public class BoardTest {
	
    public static void showStat() {
        System.out.println("All tests passed");
    }

	private static void cleanBoard(Board b) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				b.set(0, i, j);
			}
		}
	}
	
	public static void testPlayerStandJump() {
		Board b = new Board(8);
		cleanBoard(b);
		b.set(b.PLAYER_1, 1, 0);
		b.set(b.PLAYER_2, 2, 1);
		b.makeMove(b, b.PLAYER_1, 1, 0, 3, 2);
		
		if (b.pieceAt(3, 2) == b.PLAYER_1 && b.pieceAt(2, 1) == b.BLANK) {
			System.out.println("testPlayerStandJump: Test passed");
		}
		else {
			throw new RuntimeException("testPlayerStandJump: Test failed.");
		}
    }
	
    public static void testPlayerKingStandJump() {
		Board b = new Board(8);
		cleanBoard(b);
		b.set(b.PLAYER_KING_1, 0, 3);
		b.set(b.PLAYER_2, 1, 2);
		b.makeMove(b, b.PLAYER_KING_1, 0, 3, 3, 0);
		
		if (b.pieceAt(3, 0) == b.PLAYER_KING_1 && b.pieceAt(1, 2) == b.BLANK) {
			System.out.println("testPlayerKingStandJump: Test passed");
		}
		else {
			throw new RuntimeException("testPlayerKingStandJump: Test failed.");
		}
    }
	
	public static void testPlayerStepBack() {
		Board b = new Board(8);
		cleanBoard(b);
		b.set(b.PLAYER_2, 1, 2);
		MovesMade mm = new MovesMade(b, b.PLAYER_2, 1, 2, 0, 3);
		
		MovesMade[] legalMoves1 = b.getLegalMoves(b.PLAYER_2);
		for (int i = 0; i < legalMoves1.length; i++){ 
            if (legalMoves1[i].equals(mm)) { 
				b.makeMove(b, b.PLAYER_2, legalMoves1[i]); 
            }
        }
		
		if (b.pieceAt(0, 3) == b.PLAYER_2) {
			throw new RuntimeException("testPlayerStepBack: Test failed.");
		}
		else {
			System.out.println("testPlayerStepBack: Test passed");
		}
	}
	
	public static void testPlayerKingJumpViaFriendChecker() {
		Board board = new Board(8);
		cleanBoard(board);
		board.set(board.PLAYER_KING_1, 4, 1);
		board.set(board.PLAYER_2, 3, 2);
		board.set(board.PLAYER_1, 2, 3);
		
		MovesMade mm = new MovesMade(board, board.PLAYER_KING_1, 4, 1, 1, 4);
		
		MovesMade[] legalMoves1 = board.getLegalMoves(board.PLAYER_1);
		for (int i = 0; i < legalMoves1.length; i++){ 
            if (legalMoves1[i].equals(mm)) { 
				board.makeMove(board, board.PLAYER_KING_1, legalMoves1[i]); 
            }
        }
		
		if (board.pieceAt(1, 4) == board.PLAYER_KING_1 || board.pieceAt(3, 2) == board.BLANK || board.pieceAt(2, 3) == board.BLANK) {
			throw new RuntimeException("testPlayerKingJumpViaFriendChecker: Test failed.");
		}
		else {
			System.out.println("testPlayerKingJumpViaFriendChecker: Test passed");
		}
    }
	
    public static void runTests() {
		testPlayerStandJump();
        testPlayerKingStandJump();
		testPlayerStepBack();
		testPlayerKingJumpViaFriendChecker();
    }

    public static void main(String[] args) {
        BoardTest.runTests();
        BoardTest.showStat();
    }
}