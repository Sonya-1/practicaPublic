import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MainWindow extends JFrame {
	Table table;
    public MainWindow(String title, int color) {
        super(title);
		table = new Table(color);
        Container c = getContentPane();		
		Menu obj = new Menu();
		JMenu file = new JMenu("File");
		JMenuItem fileItem[] = new JMenuItem[3];
		fileItem[0] = new JMenuItem("Save to file");
		fileItem[1] = new JMenuItem("Load from file");
		fileItem[2] = new JMenuItem("Exit");
		
		fileItem[0].addActionListener ( new ActionListener() {  
            public void actionPerformed( ActionEvent e ) {  
				FileLoader fl = new FileLoader();
				boolean save = fl.saveToFile(table);
				if (save) {
					System.exit(0);	
				}					
			}
        }); 
		file.add(fileItem[0]);
		
		fileItem[1].addActionListener ( new ActionListener() {  
            public void actionPerformed( ActionEvent e ) {
				FileLoader fl = new FileLoader();
				fl.loadFromFile(table);
			}
        }); 
		file.add(fileItem[1]);
		
		fileItem[2].addActionListener ( new ActionListener() {  
            public void actionPerformed( ActionEvent e ) {
				System.exit(0);
			}
        }); 
		file.add(fileItem[2]);
		
		JMenuBar mb = new JMenuBar();
		mb.add(file);
		this.setJMenuBar(mb);
		c.add(table);
        setSize(700, 500);
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setVisible(true);
		
    }
	
    public static void main(String[] args) {
		ColorDialog cd = new ColorDialog();
		int color = cd.getColor();
			
		if (color == 0) {
			System.exit(0);
		}
		MainWindow mw = new MainWindow("Checkers", color); 
    }
}
