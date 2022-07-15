import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Table extends JPanel implements MouseListener {
	
	Board board;
	boolean gameInProgress; 
	int winner;
    int currentPlayer;
    int selectedRow;
	int selectedCol;
	int selectedChecker;
	Point selectedCheckerCoord;
	int userColor;
	MovesMade[] legalMoves;
	Font big = new Font ("Courier New", 1, 14);
	String a = "A";
	String b = "B";
	String c = "C";
	String d = "D";
	String e = "E";
	String f = "F";
	String g1 = "G";
	String h = "H";
	
	Table(int color) {
		board = new Board(8);
		userColor = color;
		if (userColor == 1) {
			currentPlayer = Board.PLAYER_1;
			legalMoves = board.getLegalMoves(Board.PLAYER_1);
		}
		else {
			currentPlayer = Board.PLAYER_2;
			legalMoves = board.getLegalMoves(Board.PLAYER_2);
		}
        selectedRow = -1;
		addMouseListener(this);
		gameInProgress = true;
		selectedChecker = 0;
		selectedCheckerCoord = new Point();
		winner = 0;
	}
	
	void gameOver() {
        gameInProgress = false;	
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.pieceAt(i, j) == Board.PLAYER_1 || board.pieceAt(i, j) == Board.PLAYER_KING_1) {
					winner = Board.PLAYER_1;
				}
			}
		}
		if (winner == 0) {
			winner = Board.PLAYER_2;
		}
		repaint();
		return;
    }
	
	public void mousePressed(MouseEvent evt) { 
		int col = (evt.getX() - 27) / 40; 
		int row = (evt.getY() - 27) / 40; 
        if (col >= 0 && col < 8 && row >= 0 && row < 8) {
            clickedSquare(row,col);
        }
    }
	
	void clickedSquare(int row, int col) { 
		if (board.pieceAt(row,col) != 0) {
			selectedChecker = 1;
			selectedCheckerCoord.row = row;
			selectedCheckerCoord.col = col;
			repaint();
		}
		
		if (legalMoves == null) {
			gameOver();
			return;
		}
		
        for (int i = 0; i < legalMoves.length; i++){ 
            if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) { 
                selectedRow = row; 
                selectedCol = col; 
                return;
            }
        }

        for (int i = 0; i < legalMoves.length; i++){ 
            if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol 
                && legalMoves[i].toRow == row && legalMoves[i].toCol == col) { 
					makeMove(this.board, currentPlayer, legalMoves[i]); 
					selectedChecker = 0;
					return;
            }
        }
    }
	
	void makeMove(Board board, int player, MovesMade move) { 
        board.makeMove(board, currentPlayer, move); 
		
		if (move.isJump() == false) {
			board.clearJumpedCheckers();
		}
		
        if (move.isJump()) { 
            legalMoves = board.getLegalJumpsFrom(currentPlayer, move.toRow, move.toCol);
			
            if (legalMoves != null) { 
                selectedRow = move.toRow; 
                selectedCol = move.toCol; 
                repaint(); 
                return;
            }
        }

        if (currentPlayer == Board.PLAYER_1) { 
            currentPlayer = Board.PLAYER_2;
            legalMoves = board.getLegalMoves(currentPlayer); 
			/*
            if (legalMoves == null) { //if there aren't any moves, player 1 wins
                gameOver();
			}
			*/
        } else { 
            currentPlayer = Board.PLAYER_1; 
            legalMoves = board.getLegalMoves(currentPlayer); 
			/*
            if (legalMoves == null) //if there aren't any moves, player 2 wins
                gameOver();
			*/
        }

        selectedRow = -1; 

        if (legalMoves != null) { 
            boolean sameFromSquare = true; 
            for (int i = 1; i < legalMoves.length; i++) 
                if (legalMoves[i].fromRow != legalMoves[0].fromRow 
                        || legalMoves[i].fromCol != legalMoves[0].fromCol) { 
                    sameFromSquare = false; 
                    break;
                }
            if (sameFromSquare) { 
                selectedRow = legalMoves[0].fromRow;
                selectedCol = legalMoves[0].fromCol;
            }
        }
        repaint(); 
    }
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, 374, 374);	
		g.setColor(Color.black);
		g.setFont(big);		
		int num = 9;
		for (int row = 0; row < 8; row++) {
			num--;
            for (int col = 0; col < 8; col++) {
				g.setColor(Color.black);
				if (col == 0) {
					g.drawString(String.valueOf(num), col*40 + 10, row*40 + 50);
				}	

				if (col == 7) {
					g.drawString(String.valueOf(num), col*40 + 75, row*40 + 50);
				}	
				
				if ((col == 0 && row == 0)) {
					g.drawString(a, col*40 + 40, 20);
				}
				
				if ((col == 1 && row == 0)) {
					g.drawString(b, col*40 + 40, 20);
				}
				
				if ((col == 2 && row == 0)) {
					g.drawString(c, col*40 + 40, 20);
				}
				
				if ((col == 3 && row == 0)) {
					g.drawString(d, col*40 + 40, 20);
				}
				
				if ((col == 4 && row == 0)) {
					g.drawString(e, col*40 + 40, 20);
				}
				
				if ((col == 5 && row == 0)) {
					g.drawString(f, col*40 + 40, 20);
				}
				
				if ((col == 6 && row == 0)) {
					g.drawString(g1, col*40 + 40, 20);
				}
				
				if ((col == 7 && row == 0)) {
					g.drawString(h, col*40 + 40, 20);
				}
				
				if ((col == 7 && row == 7)) {
					g.drawString(h, col*40 + 40, 360);
				}
				
				if ((col == 6 && row == 6)) {
					g.drawString(g1, col*40 + 40, 360);
				}
				
				if ((col == 5 && row == 5)) {
					g.drawString(f, col*40 + 40, 360);
				}
				
				if ((col == 4 && row == 4)) {
					g.drawString(e, col*40 + 40, 360);
				}
				
				if ((col == 3 && row == 3)) {
					g.drawString(d, col*40 + 40, 360);
				}
				
				if ((col == 2 && row == 2)) {
					g.drawString(c, col*40 + 40, 360);
				}
				
				if ((col == 1 && row == 1)) {
					g.drawString(b, col*40 + 40, 360);
				}
				
				if ((col == 0 && row == 0)) {
					g.drawString(a, col*40 + 40, 360);
				}
				
                if ( row % 2 == col % 2 ) {
					g.setColor(new Color(238,203,173)); 
				}
                else {
                   g.setColor(new Color(139,119,101));
				}

				g.fillRect(col*40 + 27, row*40 + 27, 40, 40);
				
                switch (board.pieceAt(row,col)) {
                    case Board.PLAYER_1:
                        paintChecker(g, row, col, userColor, 0);
                        break;
                    case Board.PLAYER_2:
                        paintChecker(g, row, col, (0 - userColor), 0);
                        break;		
                    case Board.PLAYER_KING_1:
                        paintChecker(g, row, col, userColor, 1);
                        break; 
                    case Board.PLAYER_KING_2:
                        paintChecker(g, row, col, (0 - userColor), 1);
                        break;
                }
				
				if (selectedChecker == 1) {
					selectChecker(g, selectedCheckerCoord);
				}
            }
        }
		
		if (gameInProgress == false) {
			g.setColor(Color.white);
			g.fillRect(0, 0, 374, 374);
			g.setColor(Color.black);
			g.setFont(big);
			if (winner == Board.PLAYER_1) {
				g.drawString("Game over. You win!", 84, 187);
			}
			else {
				g.drawString("Game over. You lost", 84, 187);
			}
			
		}
		
    }
	
	public void paintChecker(Graphics g, int row, int column, int color, int king) {
		if (color == 1) {
			g.setColor(Color.white);
		}
		else {
			g.setColor(Color.black);
		}
		g.fillOval(4 + column*40 + 25, 4 + row*40 + 25, 36, 36);
		
		if (king == 1) {
			if (color == 1) {
				g.setColor(Color.black);
			}
			else {
				g.setColor(Color.white);
			}
			g.fillOval(20 + column*40 + 25, 20 + row*40 + 25, 5, 5);
		}
	}
	
	public void selectChecker(Graphics g, Point p) {
		g.setColor(Color.red);
		g.drawOval(4 + p.col*40 + 25, 4 + p.row*40 + 25, 36, 36);
	}
	
	public void mouseEntered(MouseEvent evt) { }
    public void mouseClicked(MouseEvent evt) { }
    public void mouseReleased(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
}