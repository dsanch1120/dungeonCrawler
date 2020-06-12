/*
 * Created by Daniel Sanchez
 * June 8, 2020
 */
package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Treasure extends BoardCell {
	BufferedImage image;
	
	private static ArrayList<Item> contents = new ArrayList<Item>();

	public Treasure(int X, int Y, BufferedImage oImage) {
		super(oImage);
		this.X = X;
		this.Y = Y;
		this.type = CellType.TREASURE;
		try {
			image = ImageIO.read(new File("data/Dungeon_Tileset.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		image = image.getSubimage(15, 15, 16, 16);
		this.oImage = oImage.getSubimage(63, 127, 16, 16);
		generateContents();
	}

	public void generateContents() {
		board = Board.getBoard();
		for (int i = 0; i < board.getPossibleItems().size(); i++) {
			if (board.getPossibleItems().get(i).spawn()) {
				contents.add(board.getPossibleItems().get(i));
			}
		}
	}
	
	public static ArrayList<Item> getContents() {
		return contents;
	}
	
	@Override
	public void draw(Graphics cell) {
		if (visible) {
			cell.drawImage(image, X*15, Y*15, null);
			cell.drawImage(oImage, X*15, Y*15, null);
		} else {
			cell.setColor(Color.BLACK);
			cell.drawRect(X*15, Y*15, width, height);
			cell.setColor(Color.BLACK);
			cell.fillRect(X*15, Y*15, width, height);
		}
	}
	@Override
	public int behavior() {
		TreasureDisplay display = new TreasureDisplay();
		return -1;
	}
	@Override
	public boolean hasPlayer() {
		return false;
	}
	@Override
	public boolean hasEnemy() {
		return false;
	}
	
	public class TreasureDisplay extends JFrame{
		//Variables to be used throughout
		private Board board = Board.getBoard();
		private ArrayList<JPanel> panels;
		
		public TreasureDisplay() {
			DisplayTreasure();
		}
		
		public void DisplayTreasure() {
			setSize(500, 850);
			JTextField title = new JTextField(15);
			title.setEditable(false);
			title.setText("Treasure Chest");
			add(title, BorderLayout.NORTH);
			
			panels = new ArrayList<JPanel>();
			JTextField item;
			JTextArea description;
			ObtainButton button;
			JPanel temp;
			if (contents.size() == 0) {
				destroyWindow();
			}
			for (int i = 0; i < contents.size(); i++) {
				item = new JTextField(15); item.setEditable(false);
				description = new JTextArea(); description.setEditable(false);
				
				item.setText(contents.get(i).getName());
				description.setText(contents.get(i).getDescription());
				
				button = new ObtainButton(i);
				
				temp = new JPanel();
				temp.add(item); temp.add(description); temp.add(button.getButton());
				panels.add(temp);
			}
			
			JPanel centerPanel = new JPanel();
			for (int i = 0; i < panels.size(); i++) {
				centerPanel.add(panels.get(i));
			}
			
			add(centerPanel, BorderLayout.CENTER);
			
			ReturnToGameButton returnButton = new ReturnToGameButton();
			add(returnButton.getButton(), BorderLayout.SOUTH);
			
			setVisible(true);
		}
		
		private void destroyWindow() {
			setVisible(false);
			dispose();
		}
		
		private void addItem(int index) {
			board.getPlayer().addItem(contents.get(index));
			contents.remove(index);
			setVisible(false);
			this.getContentPane().removeAll();
			DisplayTreasure();
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
		
		private class ObtainButton extends JPanel {
			private JButton button;
			private int index;
			private ButtonListener listener = new ButtonListener();
			
			public ObtainButton(int index) {
				this.button = new JButton("Add to Inventory");
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
					addItem(index);
				}
				
			}
		}
	}
}
