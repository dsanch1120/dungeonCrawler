package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import javax.swing.JTextField;

public class Merchant extends BoardCell{
	private BufferedImage image;
	private ArrayList<Item> stock;

	public Merchant(int X, int Y, BufferedImage oImage) {
		super(oImage);
		this.X = X;
		this.Y = Y;
		
		this.type = CellType.MERCHANT;
		try {
			image = ImageIO.read(new File("data/Dungeon_Tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = image.getSubimage(15, 15, 16, 16);
		this.oImage = oImage.getSubimage(47, 127, 16, 16);

		generateStock();
	}

	public void generateStock() {
		stock = new ArrayList<Item>();
		for (int i = 0; i < Board.getBoard().getPossibleItems().size(); i++) {
			if (Board.getBoard().getPossibleItems().get(i).spawn()) {
				stock.add(Board.getBoard().getPossibleItems().get(i));
				stock.get(stock.size() - 1).updateStats();
				stock.get(stock.size() - 1).modifyPrice(1);
			}
		}
	}

	@Override
	public int behavior() {
		MerchantDisplay display = new MerchantDisplay();
		return -1;
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
	public boolean hasPlayer() {
		return false;
	}

	@Override
	public boolean hasEnemy() {
		return false;
	}

	public class MerchantDisplay extends JFrame {
		//Variables to be used throughout
		private ArrayList<JPanel> panels;
		private PurchaseButton button;
		private SellButton sell;

		public MerchantDisplay() {
			DisplayMerchant();
		}

		private void DisplayMerchant() {
			this.getContentPane().removeAll();
			setSize(850, 600);
			JTextField title = new JTextField(15);
			title.setEditable(false);
			title.setText("Merchant: Buy");
			add(title, BorderLayout.NORTH);

			panels = new ArrayList<JPanel>();
			JTextField item;
			JTextField price;

			JPanel temp;
			if (stock.size() == 0) {
				destroyWindow();
			}
			for (int i = 0; i < stock.size(); i++) {
				item = new JTextField(15); item.setEditable(false);
				price = new JTextField(3); price.setEditable(false);

				item.setText(stock.get(i).getName());
				price.setText(String.valueOf(stock.get(i).getPrice()));

				button = new PurchaseButton(i);

				temp = new JPanel();
				temp.add(item); temp.add(price); temp.add(button.getButton());
				panels.add(temp);
			}

			JPanel centerPanel = new JPanel();
			for (int i = 0; i < panels.size(); i++) {
				centerPanel.add(panels.get(i));
			}

			add(centerPanel, BorderLayout.CENTER);

			JPanel southMenu = new JPanel();
			southMenu.setLayout(new GridLayout(2,1));
			SellInventoryButton sellButton = new SellInventoryButton();
			southMenu.add(sellButton.getButton());
			ReturnToGameButton returnButton = new ReturnToGameButton();
			southMenu.add(returnButton.getButton());
			add(southMenu, BorderLayout.SOUTH);
			
			setVisible(true);
		}
		
		private void DisplaySell() {
			this.getContentPane().removeAll();
			setSize(850, 600);
			JTextField title = new JTextField(15);
			title.setEditable(false);
			title.setText("Merchant: Sell");
			add(title, BorderLayout.NORTH);
			
			panels = new ArrayList<JPanel>();
			JTextField item;
			JTextField price;
			
			JPanel temp;
			Board.getBoard().getPlayer().updateInventory();
			for (int i = 0; i < Board.getBoard().getPlayer().getInventory().size(); i++) {
				if (Board.getBoard().getPlayer().getInventory().get(i).isEquipped()) {
					continue;
				}
				item = new JTextField(15); item.setEditable(false);
				price = new JTextField(3); price.setEditable(false);
				
				item.setText(Board.getPlayer().getInventory().get(i).getName());
				price.setText(String.valueOf(Board.getPlayer().getInventory().get(i).getPrice()));
				
				sell = new SellButton(i);
				
				temp = new JPanel();
				temp.add(item); temp.add(price); temp.add(sell.getButton());
				panels.add(temp);
			}
			
			JPanel centerPanel = new JPanel();
			for (int i = 0; i < panels.size(); i++) {
				centerPanel.add(panels.get(i));
			}

			add(centerPanel, BorderLayout.CENTER);

			JPanel southMenu = new JPanel();
			southMenu.setLayout(new GridLayout(2,1));
			BuyInventoryButton buyButton = new BuyInventoryButton();
			ReturnToGameButton returnButton = new ReturnToGameButton();
			southMenu.add(buyButton.getButton());
			southMenu.add(returnButton.getButton());
			add(southMenu, BorderLayout.SOUTH);

			setVisible(true);
		}

		private void destroyWindow() {
			setVisible(false);
			dispose();
		}

		private boolean purchaseItem(int index) {
			if (Board.getBoard().getPlayer().getGold() < stock.get(index).getPrice()) {
				return false;
			} else {
				Board.getBoard().getPlayer().addItem(stock.get(index));
				Board.getBoard().getPlayer().getPurse().addGold(stock.get(index).price * -1);
				GoldView.getInstance().updateGold();
				stock.remove(index);
				setVisible(false);
				this.getContentPane().removeAll();
				DisplayMerchant();
				return true;
			}
		}
		
		private void sellItem(int index) {
			Board.getBoard().getPlayer().getPurse().addGold(Board.getBoard().getPlayer().getInventory().get(index).price);
			GoldView.getInstance().updateGold();
			Board.getBoard().getPlayer().getInventory().remove(index);
			Board.getBoard().getPlayer().updateCollections();
			setVisible(false);
			this.getContentPane().removeAll();
			DisplaySell();
		}
		

		private class SellButton extends JPanel {
			private JButton button;
			private int index;
			private ButtonListener listener = new ButtonListener();

			public SellButton(int index) {
				this.button = new JButton("Sell");
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
					sellItem(index);
				}

			}
		}
		
		private class BuyInventoryButton extends JPanel {
			private JButton button;
			private ButtonListener listener = new ButtonListener();
			
			public BuyInventoryButton() {
				this.button = new JButton("Buy");
				this.button.addActionListener(listener);
			}
			
			//Getter Method
			public JButton getButton() {
				return button;
			}
			
			private class ButtonListener implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent e) {
					destroyWindow();
					DisplayMerchant();
				}
			}
		}
		
		private class SellInventoryButton extends JPanel {
			private JButton button;
			ButtonListener listener = new ButtonListener();
			
			public SellInventoryButton() {
				this.button = new JButton("Sell");
				this.button.addActionListener(listener);
			}
			
			//Getter Method
			public JButton getButton() {
				return button;
			}
			
			private class ButtonListener implements ActionListener {
				@Override 
				public void actionPerformed(ActionEvent e) {
					destroyWindow();
					DisplaySell();
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

		private class PurchaseButton extends JPanel {
			private JButton button;
			private int index;
			private ButtonListener listener = new ButtonListener();

			public PurchaseButton(int index) {
				this.button = new JButton("Purchase Item");
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
					if (purchaseItem(index)) {
					} else {
						//FIXME
					}
				}

			}
		}
	}
}
