package gui;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * This class serves as an example and training class to learn JavaX Swing features.
 * 
 * @author Marcel Verst
 * @version 15.06.2019
 */
public class SwingGUIExample {
	public void drawWindow() {
		JFrame frame = new JFrame("My Window");
		frame.setSize(1000, 400);
		
		JButton myButton = new JButton("My Button");
		myButton.setName("My Button");
		JButton abbrechen = new JButton("Abbrechen");
		abbrechen.setName("Abbrechen");
		JButton speichern = new JButton("Speichern");
		speichern.setName("Speichern");
		
		MouseListener mouseListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(e.getComponent().getName() + " Clicked");
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		};
		myButton.addMouseListener(mouseListener);
		abbrechen.addMouseListener(mouseListener);
		speichern.addMouseListener(mouseListener);
		
		frame.setLayout(new GridLayout(2, 3));
		
		frame.add(myButton);
		frame.add(speichern);
		frame.add(abbrechen);
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
