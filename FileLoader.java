import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

class FileLoader extends JFrame {
	
	public boolean saveToFile(Table t) {
		FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.SAVE);
		fd.setDirectory("C:\\");
		fd.setFile("game2.txt");
		fd.setVisible(true);
		String filename = fd.getFile();
		if (filename == null) {
			return false;
		}
			
		else {
			saveBoard(filename, t);	
		}
		
		return true;
	}
	
	public void saveBoard(String filename, Table t) {
		try {
			OutputStream outputStream = new FileOutputStream(filename);
			DataOutputStream output = new DataOutputStream (outputStream);
			output.writeUTF(String.valueOf(t.currentPlayer));
			output.writeChar(' ');
			output.writeUTF(String.valueOf(t.userColor));
			output.writeChar(' ');
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					int val = t.board.pieceAt(i, j);
					output.writeUTF(String.valueOf(val));
					output.writeChar(' ');
				}
			}
		}
		catch (IOException ex) {} 
	}
	
	public void loadFromFile(Table t) {
		FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setFile("game2.txt");
		fd.setVisible(true);
		String filename = fd.getFile();
		if (filename != null) {
			loadBoard(filename, t);
		}
	}
	
	public void loadBoard(String filename, Table t) {
		try {
			System.out.println("getFromFile");
			InputStream inputstream = new FileInputStream(filename);
			DataInputStream input = new DataInputStream(inputstream);
			t.board = new Board(8);
			t.currentPlayer = Integer.parseInt(input.readUTF());
			char del;
			int val;
			del = input.readChar();
			t.userColor = Integer.parseInt(input.readUTF());
			del = input.readChar();
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					val = Integer.parseInt(input.readUTF());
					t.board.set(val, i, j);
					del = input.readChar();
				}
			}
			t.legalMoves = t.board.getLegalMoves(t.currentPlayer);
			t.selectedRow = -1;
			t.addMouseListener(t);
			t.gameInProgress = true;
			
			t.selectedChecker = 0;
			t.selectedCheckerCoord = new Point();
			t.winner = 0;
			t.repaint();
			
		}
		catch (IOException ex) {}
	}
}
