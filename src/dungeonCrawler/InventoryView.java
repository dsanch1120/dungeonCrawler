package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class InventoryView extends JFrame{
	private Board board;
	private JTextField title = new JTextField(30);
	private ReturnToGameButton returnButton = new ReturnToGameButton();
	
	public InventoryView() {
		board = Board.getBoard();
		setSize(600, 850);
		title.setEditable(false);
		title.setText("Inventory");
		add(title, BorderLayout.NORTH);
		
		JTextField item;
		JTextArea description;
		JPanel panel = new JPanel(); 
		JPanel row;
		JPanel name; JPanel desc;
		for (Item i : board.getPlayer().getInventory()) {
			//Reallocates variables
			row = new JPanel(); name = new JPanel(); desc = new JPanel();
			item = new JTextField(15); item.setEditable(false);
			description = new JTextArea(); description.setEditable(false);
			name.setBorder(new TitledBorder(new EtchedBorder(), "Name"));
			desc.setBorder(new TitledBorder(new EtchedBorder(), "Description"));
			//Adds text to panels
			item.setText(i.getName());
			description.setText(i.getDescription());
			name.add(item);
			desc.add(description);
			//Adds components to panel
			row.add(name);
			row.add(desc);
			panel.add(row);
		}
		add(panel, BorderLayout.CENTER);
		
		add(returnButton.getButton(), BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private void destroyWindow() {
		setVisible(false);
		dispose();
	}
	
	private class ReturnToGameButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public ReturnToGameButton() {
			this.button = new JButton("Return to Game");
			this.button.addActionListener(listener);
		}

		//Getter method
		public JButton getButton() {
			return button;
		}

		private class ButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				//Returns to game if button is pressed
				destroyWindow();
			}	
		}
	}
}
