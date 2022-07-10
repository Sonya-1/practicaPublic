import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame implements ActionListener {
	static JLabel text;
    public MainWindow(String title, int color) {
       
        super(title);
        Table table = new Table(color);
		System.out.println("create table");
        Container c = getContentPane();
		
		Menu obj = new Menu();
		//Create a Menu
		JMenu file = new JMenu("File");
		JMenu game = new JMenu("Game");
		//Create Menu Items
		JMenuItem fileItem[] = new JMenuItem[3];
		JMenuItem gameItem = new JMenuItem();
		fileItem[0] = new JMenuItem("Save");
		fileItem[1] = new JMenuItem("New game");
		fileItem[2] = new JMenuItem("Exit");
		for(int i = 0; i < 3; i++) {
			fileItem[i].addActionListener(this);
			file.add(fileItem[i]);
		}
		
		gameItem =  new JMenuItem("Step back");
		game.add(gameItem);
		//Create a menu bar
		JMenuBar mb = new JMenuBar();
		mb.add(file);
		mb.add(game);
		this.setJMenuBar(mb);
		
		/*
		JButton back = new JButton("Back");  
		back.setBounds(0,0,95,30); 
		JButton save = new JButton("Save");  
		save.setBounds(279,0,95,30); 
		c.add(back);
		c.add(save);
		*/
		
		c.add(table);
        setSize(700, 500);
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setVisible(true);
		
    }
	
	public void actionPerformed(ActionEvent e) {
		text.setText("Menu Item Selected : " + e.getActionCommand());
    }
	
    public static void main(String[] args) {
		ColorDialog cd = new ColorDialog();
		int color = cd.getColor();
		System.out.println(color);
		//OpponentDialog od = new OpponentDialog(); 
        MainWindow mw = new MainWindow("Checkers", color);
    }
}
