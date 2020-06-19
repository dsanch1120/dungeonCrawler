package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Battle extends JFrame {
	private WeaponButton weaponButton = new WeaponButton();
	private PotionButton potionButton = new PotionButton();
	private ArmorButton armorButton = new ArmorButton();
	private PlayerButton playerButton = new PlayerButton();
	private AttackButton attackButton = new AttackButton();
	private AbilityButton abilityButton = new AbilityButton();
	private Enemy enemy;
	private Health health;
	private EnemyHealth enemyHealth;

	public Battle(Enemy enemy) {
		//Ensures user is unable to close window
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		//Sets the Dungeon Crawler visibility to false so that the user cannot continue to move
		DungeonCrawler.getInstance().setVisible(false);

		this.enemy = enemy;
		setSize(800, 600);

		//Creates Title in north of JFrame
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(1,3));
		health = new Health();
		northPanel.add(health);
		health.updateHealth();
		JTextField title = new JTextField(25);
		title.setEditable(false);
		title.setText("Battle with " + enemy.name);
		northPanel.add(title);
		enemyHealth = new EnemyHealth(enemy);
		northPanel.add(enemyHealth);
		enemyHealth.updateHealth();
		add(northPanel, BorderLayout.NORTH);



		//Creates menu in the south of the JFrame
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2,3));
		southPanel.add(attackButton.getButton());
		southPanel.add(playerButton.getButton());
		southPanel.add(abilityButton.getButton());
		southPanel.add(weaponButton.getButton());
		southPanel.add(potionButton.getButton());
		southPanel.add(armorButton.getButton());

		add(southPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void destroyWindow() {
		setVisible(false);
		dispose();
	}

	private class AbilityButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public AbilityButton() {
			this.button = new JButton("Use Ability");
			this.button.addActionListener(listener);
		}

		//Getter method
		public JButton getButton() {
			return button;
		}

		private class ButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {				
				AbilityView abilityView = new AbilityView();
			}	
		}
	}

	private class PlayerButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public PlayerButton() {
			this.button = new JButton("Player");
			this.button.addActionListener(listener);
		}

		//Getter method
		public JButton getButton() {
			return button;
		}

		private class ButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerView playerView = new PlayerView();
			}	
		}
	}

	private class AttackButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public AttackButton() {
			this.button = new JButton("Attack");
			this.button.addActionListener(listener);
		}

		//Getter method
		public JButton getButton() {
			return button;
		}

		private class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Lets user Attack the enemy
				if (Board.getPlayer().agilityRoll() >= enemy.agilityRoll()) {
					enemy.takeDamage(Board.getPlayer().attack());
				}
				else {
					//FIXME needs to be further implemented
					//Runs if the player misses, probably a pop-up window
				}
				enemyHealth.updateHealth();
				//health.updateHealth();
				if (enemy.HP <= 0) {
					Board.getPlayer().updateXP(enemy.XP);
					XP.getInstance().updateXP();
					Board.getBoard().getBoardArray()[Board.getPlayer().getxCoordinate()][Board.getPlayer().getyCoordinate()].enemy = null;
					Board.getBoard().repaint();
					destroyWindow();
					DungeonCrawler.getInstance().setVisible(true);
					DungeonCrawler.getInstance().updateHealth();
					return;
				}
				if (enemy.agilityRoll() >= Board.getPlayer().agilityRoll()) {
					Board.getPlayer().updateHealth((enemy.attack()) * -1);
				}
				health.updateHealth();
				if (Board.getPlayer().getHP() <= 0) {
					destroyWindow();
				}
			}
		}
	}

	private class PotionButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public PotionButton() {
			this.button = new JButton("Potions");
			this.button.addActionListener(listener);
		}

		//Getter method
		public JButton getButton() {
			return button;
		}

		private class ButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				//Lets user drink a potion
				PotionView potionView = new PotionView();
			}	
		}
	}

	private class ArmorButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public ArmorButton() {
			this.button = new JButton("Change Armor");
			this.button.addActionListener(listener);
		}

		//Getter method
		public JButton getButton() {
			return button;
		}

		private class ButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				//Changes player's armor
				ArmorView armorView = new ArmorView();
			}	
		}
	}

	private class WeaponButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public WeaponButton() {
			this.button = new JButton("Change Weapon");
			this.button.addActionListener(listener);
		}

		//Getter method
		public JButton getButton() {
			return button;
		}

		private class ButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				//Changes Player's Weapon
				WeaponView weaponView = new WeaponView();
			}	
		}
	}

	private static class EnemyHealth extends JPanel {
		private JTextField health = new JTextField();
		private Enemy enemy;

		public EnemyHealth(Enemy enemy) {
			this.enemy = enemy;
			health.setEditable(false);
			add(health);
		}

		public void updateHealth() {
			setVisible(false);
			health.setText(enemy.name + " health: " + enemy.HP);
			setVisible(true);
		}
	}
	
	private class AbilityView extends JFrame {
		private ArrayList<JPanel> panels;
		
		public AbilityView() {
			//Variables to be used throughout
			JTextField ability = new JTextField(15);
			JTextArea description = new JTextArea();
			UseButton useButton;
			JPanel temp;
			panels = new ArrayList<JPanel>();
			setSize(850, 600);
			
			if (Board.getPlayer().getAbilities().size() == 0) {
				destroyWindow();
				return;
			} else {
				for (Ability i : Board.getPlayer().getAbilities()) {
					ability = new JTextField(15); ability.setEditable(false);
					description = new JTextArea(); description.setEditable(false);
					
					ability.setText(i.getName());
					description.setText(i.getDescription());
					
					useButton = new UseButton(i);
					
					temp = new JPanel();
					temp.add(ability); temp.add(description); temp.add(useButton.getButton());
					panels.add(temp);
				}
				
				JPanel centerPanel = new JPanel();
				for (int i = 0; i < panels.size(); i++) {
					centerPanel.add(panels.get(i));
				}
				
				add(centerPanel, BorderLayout.CENTER);
				
				setVisible(true);
			}
		}
		
		private void destroyFrame() {
			setVisible(false);
			dispose();
		}
		
		private class UseButton extends JPanel {
			private JButton button;
			private Ability ability;
			private ButtonListener listener = new ButtonListener();
			
			public UseButton(Ability ability) {
				this.ability = ability;
				this.button = new JButton("Use");
				this.button.addActionListener(listener);
			}
			
			//Getter method
			public JButton getButton() {
				return button;
			}
			
			private class ButtonListener implements ActionListener {
				
				@Override 
				public void actionPerformed(ActionEvent e) {
					if (ability.behavior() != null) {
						enemy.takeDamage(ability.behavior());
						enemyHealth.updateHealth();
					} else {
						ability.behavior();
					}
					
					if (enemy.HP <= 0) {
						Board.getPlayer().updateXP(enemy.XP);
						XP.getInstance().updateXP();
						Board.getBoard().getBoardArray()[Board.getPlayer().getxCoordinate()][Board.getPlayer().getyCoordinate()].enemy = null;
						Board.getBoard().repaint();
						destroyFrame();
						destroyWindow();
						DungeonCrawler.getInstance().setVisible(true);
						DungeonCrawler.getInstance().updateHealth();
						return;
					} else {
						if (enemy.agilityRoll() >= Board.getPlayer().agilityRoll()) {
							Board.getPlayer().updateHealth((enemy.attack()) * -1);
						}
						health.updateHealth();
						if (Board.getPlayer().getHP() <= 0) {
							destroyWindow();
						}
					}
					destroyFrame();
				}
			}
		}
	}
}
