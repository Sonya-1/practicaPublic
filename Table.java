import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Table extends JPanel implements MouseListener {
	
	private Board board;
	boolean gameInProgress; 
    int currentPlayer;
    int selectedRow;
	int selectedCol;
	int selectedChecker;
	Point selectedCheckerCoord;
	int userColor;
	movesMade[] legalMoves;
	JLabel message;
	
	Table(int color) {
		board = new Board(8);
		userColor = color;
		System.out.println(userColor);
		if (userColor == 1) {
			
			System.out.println("currentPlayer = Board.player1");
			currentPlayer = Board.player1;
			legalMoves = board.getLegalMoves(Board.player1);
		}
		else {
			currentPlayer = Board.player2;
			legalMoves = board.getLegalMoves(Board.player2);
		}
        //legalMoves = board.getLegalMoves(Board.player1); //searches for legal moves
        selectedRow = -1;
		addMouseListener(this);
		gameInProgress = true;
		
		selectedChecker = 0;
		selectedCheckerCoord = new Point();

		
		message = new JLabel("",JLabel.CENTER);
        message.setFont(new  Font("Serif", Font.BOLD, 14));
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setForeground(Color.darkGray);
	}
	
	void gameOver(String str) { //when game is over
        message.setText(str); //indicates who won
        gameInProgress = false; //sets gameInProgress as false, until new game is initialized
    }
	
	public void mousePressed(MouseEvent evt) { //when the board is clicked
		int col = (evt.getX() - 27) / 40; //calculation of square's column
		int row = (evt.getY() - 27) / 40; //calculation of square's row
        if (col >= 0 && col < 8 && row >= 0 && row < 8) {//if square is on the board
            clickedSquare(row,col); //calls ClickedSquare
        }
    }
	
	void clickedSquare(int row, int col) { //processes legal moves
		if (board.pieceAt(row,col) != 0) {
			selectedChecker = 1;
			selectedCheckerCoord.row = row;
			selectedCheckerCoord.col = col;
			repaint();
		}
		
		//BREAK
        for (int i = 0; i < legalMoves.length; i++){ //runs through all legal moves
            if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) { //if selected piece can be moved
                selectedRow = row; //assigns selected row
                selectedCol = col; //assigns selected column
                //repaint(); //repaints board
                return;
            }
        }

        for (int i = 0; i < legalMoves.length; i++){ //runs through all legal moves
            if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol //if already selected piece can move
                && legalMoves[i].toRow == row && legalMoves[i].toCol == col) { //and the selected piece's destination is legal
					MakeMove(this.board, currentPlayer, legalMoves[i]); //make the move
					selectedChecker = 0;
					return;
            }
        }
    }
	
	void MakeMove(Board board, int player, movesMade move) { //moves the piece
        board.makeMove(board, currentPlayer, move); //calls makeMove method in Board class

        if (move.isJump()) { //checks if player must continue jumping
            legalMoves = board.getLegalJumpsFrom(currentPlayer, move.toRow, move.toCol);
			
            if (legalMoves != null) { //if player must jump again
				System.out.println("player must jump again");
                selectedRow = move.toRow; //assigns selected row to destination row
                selectedCol = move.toCol; //assigns selected column to destination column
                repaint(); //repaints board
                return;
            }
        }

        if (currentPlayer == Board.player1) { //if it was player 1's turn
            currentPlayer = Board.player2; //it's now player 2's
            legalMoves = board.getLegalMoves(currentPlayer); //gets legal moves for player 2
            if (legalMoves == null) //if there aren't any moves, player 1 wins
                gameOver("Game over. You win!");
				
        } else { //otherwise, if it was player 2's turn
            currentPlayer = Board.player1; //it's now player 1's turn
            legalMoves = board.getLegalMoves(currentPlayer); //gets legal moves for player 1
            if (legalMoves == null) //if there aren't any moves, player 2 wins
                gameOver("Game over. You lost");
        }

        selectedRow = -1; //no squares are not selected

        if (legalMoves != null) { //if there are legal moves
            boolean sameFromSquare = true; //declares boolean sameFromSquare
            for (int i = 1; i < legalMoves.length; i++) //runs through all legal moves
                if (legalMoves[i].fromRow != legalMoves[0].fromRow //if there are any legal moves besides the selected row
                        || legalMoves[i].fromCol != legalMoves[0].fromCol) { //and column
                    sameFromSquare = false; //declares sameFromSquare as false
                    break;
                }
            if (sameFromSquare) { //if true, the player's final piece is already selected
                selectedRow = legalMoves[0].fromRow;
                selectedCol = legalMoves[0].fromCol;
            }
        }

        repaint(); //repaints board

    }
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.setColor(new Color(139,119,101)); //black
        //g.fillRect(0, 30, 324, 324);
		g.setColor(Color.white);
		g.fillRect(0, 0, 374, 374);
        g.fillRect(400, 0, 200, 374);
		Font big = new Font ("Courier New", 1, 14);
		g.setColor(Color.black);
		g.setFont(big);
		
		g.drawString("Move history", 450, 20);
		
		int num = 9;
		int num1 = -1;
		String a = "A";
		String b = "B";
		String c = "C";
		String d = "D";
		String e = "E";
		String f = "F";
		String g1 = "G";
		String h = "H";
		
		for (int row = 0; row < 8; row++) {
			num--;
			num1++;
            for (int col = 0; col < 8; col++) {
				g.setColor(Color.black);
				if (col == 0) {
					//g.drawString(String.valueOf(num), col*40 + 10, row*40 + 50);
					g.drawString(String.valueOf(num1), col*40 + 10, row*40 + 50);
				}	

				if (col == 7) {
					//g.drawString(String.valueOf(num), col*40 + 75, row*40 + 50);
					g.drawString(String.valueOf(num1), col*40 + 75, row*40 + 50);

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
					g.setColor(new Color(238,203,173)); //white
				}
                else {
                   g.setColor(new Color(139,119,101));
				}
				
                //g.fillRect(2 + col*40 + 25, 2 + row*40 + 55, 40, 40);
				g.fillRect(2 + col*40 + 25, 2 + row*40 + 25, 40, 40);
				
                switch (board.pieceAt(row,col)) {
                    case Board.player1:
                        paintChecker(g, row, col, userColor, 0);
                        break;
                    case Board.player2:
                        paintChecker(g, row, col, (0 - userColor), 0);
                        break;		
                    case Board.playerKing1:
                        paintChecker(g, row, col, userColor, 1);
                        break; 
                    case Board.playerKing2:
                        paintChecker(g, row, col, (0 - userColor), 1);
                        break;
                }
				
				if (selectedChecker == 1) {
					selectChecker(g, selectedCheckerCoord);
				}
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