import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
public class ColorDialog {  
    private static JDialog d;  
	private int color;
	
    ColorDialog() {  
        JFrame f= new JFrame();  
        d = new JDialog(f , "ColorDialog", true);  
        d.setLayout( new FlowLayout() );  
        JButton black = new JButton ("Black");  
		JButton white = new JButton ("White");  
        black.addActionListener ( new ActionListener()  
        {  
            public void actionPerformed( ActionEvent e ) {  
				color = -1;
                ColorDialog.d.setVisible(false);  
            }
        }); 
		white.addActionListener ( new ActionListener()  
        {  
            public void actionPerformed( ActionEvent e ) {  
				color = 1;
                ColorDialog.d.setVisible(false);  
            }
        }); 		
        d.add( new JLabel ("Chosee the color of the checkers"));  
        d.add(black);   
		d.add(white);
        d.setSize(400,300);   
		d.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        d.setVisible(true);  
    }  
	
	public int getColor() {
		return color;
	}
}