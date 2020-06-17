package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

public class LevelUpView extends JFrame{
	Board board = Board.getBoard();

	public LevelUpView() {
		setSize(600,400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		//Adds title to north of JFrame
		JTextField title = new JTextField(20);
		title.setEditable(false);
		title.setText("Choose Attribute to Upgrade");
		add(title, BorderLayout.NORTH);
		//Adds Buttons
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(7,1));
		AttributeButton button = new AttributeButton("Strength", 1, board.getPlayer().getSTRENGTH());
		centerPanel.add(button.getButton());
		button = new AttributeButton("Endurance", 2, board.getPlayer().getENDURANCE());
		centerPanel.add(button.getButton());
		button = new AttributeButton("Defense", 3, board.getPlayer().getDEFENSE());
		centerPanel.add(button.getButton());
		button = new AttributeButton("Agility", 4, board.getPlayer().getAGILITY());
		centerPanel.add(button.getButton());
		button = new AttributeButton("Perception", 5, board.getPlayer().getPERCEPTION());
		centerPanel.add(button.getButton());
		button = new AttributeButton("Intelligence", 6, board.getPlayer().getINTELLIGENCE());
		centerPanel.add(button.getButton());
		button = new AttributeButton("Charisma", 7, board.getPlayer().getCHARISMA());
		centerPanel.add(button.getButton());
		add(centerPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}

	private void destroyWindow() {
		setVisible(false);
		dispose();
	}
	
	private class AttributeButton extends JPanel {
		private JButton button;
		private ButtonListener listener = new ButtonListener();
		private int aIndex;

		public AttributeButton(String name,int aIndex, int aValue) {
			this.button = new JButton(name + " (Currently at: " + aValue + ")");
			this.button.addActionListener(listener);
			this.aIndex = aIndex;
		}

		public JButton getButton() {
			return button;
		}

		private class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (aIndex) {
				case 1:
					board.getPlayer().updateSTRENGTH(1);
					break;
				case 2:
					board.getPlayer().updateENDURANCE(1);
					break;
				case 3:
					board.getPlayer().updateDEFENSE(1);
					break;
				case 4:
					board.getPlayer().updateAGILITY(1);
					break;
				case 5:
					board.getPlayer().updatePERCEPTION(1);
					break;
				case 6:
					board.getPlayer().updateINTELLIGENCE(1);
					break;
				case 7:
					board.getPlayer().updateCHARISMA(1);
					break;
				default:
					System.out.println("This should not be reached");
				}
				destroyWindow();
			}
		}
	}
}
