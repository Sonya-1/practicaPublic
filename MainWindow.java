import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MainWindow extends JFrame /*implements ActionListener*/ {
	Table table;
    public MainWindow(String title, int color) {
        super(title);
		table = new Table(color);
		//System.out.println("create table");
        Container c = getContentPane();
		
		Menu obj = new Menu();
		//Create a Menu
		JMenu file = new JMenu("File");
		JMenu game = new JMenu("Game");
		//Create Menu Items
		JMenuItem fileItem[] = new JMenuItem[2];
		JMenuItem gameItem = new JMenuItem();
		fileItem[0] = new JMenuItem("Save to file");
		fileItem[1] = new JMenuItem("Load from file");
		//fileItem[2] = new JMenuItem("Exit");
		
		fileItem[0].addActionListener ( new ActionListener() {  
            public void actionPerformed( ActionEvent e ) {  
				FileLoader fl = new FileLoader();
				fl.saveToFile("game.txt", table);
				System.exit(0);
				/*
				try {
					table.saveToFile("game.txt");
					System.exit(0);
				}
				catch (IOException ex) {} 
				*/				
			}
        }); 
		file.add(fileItem[0]);
		
		fileItem[1].addActionListener ( new ActionListener() {  
            public void actionPerformed( ActionEvent e ) {
				FileLoader fl = new FileLoader();
				fl.loadFromFile("game.txt", table);
				//table = new Table("game.txt");	
				//System.out.println("actionPerformed: create new table");
			}
        }); 
		file.add(fileItem[1]);
		
		/*
		for(int i = 0; i < 1; i++) {
			fileItem[i].addActionListener(this);
			file.add(fileItem[i]);
		}
		*/
		gameItem =  new JMenuItem("Step back");
		game.add(gameItem);
		//Create a menu bar
		JMenuBar mb = new JMenuBar();
		mb.add(file);
		mb.add(game);
		this.setJMenuBar(mb);
		c.add(table);
        setSize(700, 500);
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setVisible(true);
		
    }
	/*
	public void actionPerformed(ActionEvent e) {
		//text.setText("Menu Item Selected : " + e.getActionCommand());
		try {
			table.saveToFile("game.txt");
			System.exit(0);
		}
		catch (IOException ex) {}
    }
	*/
    public static void main(String[] args) {
		ColorDialog cd = new ColorDialog();
		int color = cd.getColor();
			
		if (color == 0) {
			System.exit(0);
		}
		MainWindow mw = new MainWindow("Checkers", color); 
    }
}
