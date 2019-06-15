package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DBHandler;

/**
 * This class serves as an example for working with Swings JTextField.
 * Currently the user can enter a SQL query for querying the location table and the result will then be printed out on the command line.
 * 
 * @author Marcel Verst
 * @version 15.06.2019
 */
public class TextFieldExample {
	JTextField textField;
	JFrame frame;
	JButton button;
	JLabel label;

	/**
	 * Creates a frame with a textfield, a label and a button. If the button is pressed, the database is queried with the text inserted 
	 * into the textfield of the frame.
	 */
	public void doSomething() {
		frame = new JFrame("Textfield example");
		label = new JLabel("Nothing entered");
		button = new JButton("Execute Query");
		textField = new JTextField(16);

		/* Action handling for the button. Here it is defined what will happen when the button is clicked.
		 * Currently the database is queried with the text given from the textfield.*/
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = e.getActionCommand();
				if(s.equals("Execute Query")) {
					DBHandler handler = new DBHandler();
					String query = textField.getText();
					ResultSet rs = handler.executeQuery(query);
					try {
						while(rs.next()) {
							int cityId = rs.getInt(1);
							double longitude = rs.getDouble(2);
							double latitude = rs.getDouble(3);
							String cityName = rs.getString(4);
							
							System.out.println("ID: " + cityId + ", Longitude: " + longitude + ", Latitude: " + latitude + ", Name: " + cityName);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		button.addActionListener(listener);

		JPanel p = new JPanel();

		p.add(textField);
		p.add(button);
		p.add(label);

		frame.add(p);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
