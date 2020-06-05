package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerView extends JFrame {
	private Board board;
	private JTextField userName = new JTextField(30);
	private ReturnToGameButton returnToGameButton = new ReturnToGameButton();
	
	public PlayerView() {
		board = Board.getBoard();
		setSize(500, 850);
		userName.setEditable(false);
		userName.setText(board.getPlayer().getName());
		add(userName, BorderLayout.NORTH);
		
		JTextField attribute = new JTextField(30);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7,1));
		attribute.setEditable(false);
		attribute.setText("Strength: " + board.getPlayer().getSTRENGTH());
		panel.add(attribute);
		attribute = new JTextField(30); attribute.setEditable(false);
		attribute.setText("Endurance: " + board.getPlayer().getENDURANCE());
		panel.add(attribute);
		attribute = new JTextField(30); attribute.setEditable(false);
		attribute.setText("Defense: " + board.getPlayer().getDEFENSE());
		panel.add(attribute);
		attribute = new JTextField(30); attribute.setEditable(false);
		attribute.setText("Agility: " + board.getPlayer().getAGILITY());
		panel.add(attribute);
		attribute = new JTextField(30); attribute.setEditable(false);
		attribute.setText("Perception: " + board.getPlayer().getPERCEPTION());
		panel.add(attribute);
		attribute = new JTextField(30); attribute.setEditable(false);
		attribute.setText("Intelligence: " + board.getPlayer().getINTELLIGENCE());
		panel.add(attribute);
		attribute = new JTextField(30); attribute.setEditable(false);
		attribute.setText("Charisma: " + board.getPlayer().getCHARISMA());
		panel.add(attribute);
		
		add(panel, BorderLayout.CENTER);
		
		add(returnToGameButton.getButton(), BorderLayout.SOUTH);
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
