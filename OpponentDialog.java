import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*; 
public class OpponentDialog {  
    private static JDialog d;  
	private int opponent;
	
    OpponentDialog() {  
        JFrame f= new JFrame();  
        d = new JDialog(f , "Dialog Example", true);  
        d.setLayout( new FlowLayout() );  
        JButton person = new JButton ("Person");  
		JButton computer = new JButton ("Computer");  
        person.addActionListener ( new ActionListener()  
        {  
            public void actionPerformed( ActionEvent e ) {  
				opponent = 0;
                OpponentDialog.d.setVisible(false);  
            }
        }); 
		computer.addActionListener ( new ActionListener()  
        {  
            public void actionPerformed( ActionEvent e ) {  
				opponent = 1;
                OpponentDialog.d.setVisible(false);  
            }
        }); 		
        d.add( new JLabel ("Chosee an opponent"));  
        d.add(person);   
		d.add(computer);
        d.setSize(400,300);       
        d.setVisible(true);  
    }  
	
	public int getOpponent() {
		return opponent;
	}
}