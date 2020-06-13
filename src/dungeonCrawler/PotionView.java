package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PotionView extends JFrame {
	//Variables to be used throughout
		private Board board = Board.getBoard();
		private ArrayList<JPanel> panels;
		private ReturnToGameButton returnButton = new ReturnToGameButton();

		public PotionView() {
			displayPotions();
		}

		public void displayPotions() {
			setVisible(false);
			this.getContentPane().removeAll();
			//Variables to be used throughout
			JTextField item = new JTextField(15);
			JTextArea description = new JTextArea();
			DrinkPotionButton drinkPotion;
			JPanel temp;
			panels = new ArrayList<JPanel>();
			item.setEditable(false);
			description.setEditable(false);
			
			setSize(850, 600);
			
			for (int i = 0; i < board.getPlayer().getPotions().size(); i++) {
				item = new JTextField(15); item.setEditable(false);
				description = new JTextArea(); description.setEditable(false);
				
				item.setText(board.getPlayer().getPotions().get(i).getName());
				description.setText(board.getPlayer().getPotions().get(i).getDescription());
				
				drinkPotion = new DrinkPotionButton(i);
				
				temp = new JPanel();
				temp.add(item); temp.add(description); temp.add(drinkPotion.getButton());
				panels.add(temp);
			}
			
			JPanel centerPanel = new JPanel();
			for (int i = 0; i < panels.size(); i++) {
				centerPanel.add(panels.get(i));
			}
			
			add(centerPanel, BorderLayout.CENTER);
			
			add(returnButton.getButton(), BorderLayout.SOUTH);
			setVisible(true);
		}
		
		private void destroyWindow() {
			setVisible(false);
			dispose();
		}
		
		private class DrinkPotionButton extends JPanel {
			private JButton button;
			private int index;
			private ButtonListener listener = new ButtonListener();
			
			public DrinkPotionButton(int index) {
				this.button = new JButton("Drink");
				this.index = index;
				this.button.addActionListener(listener);
			}
			
			//Getter method
			public JButton getButton() {
				return button;
			}
			
			private class ButtonListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					board.getPlayer().getPotions().get(index).effect();
					board.getPlayer().getPotions().remove(index);
					displayPotions();
				}
				
			}
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
