package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

public class Battle extends JFrame{
	
	public Battle(Enemy enemy) {
		//Ensures user is unable to close window
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		//Sets the Dungeon Crawler visibility to false so that the user cannot continue to move
		//DungeonCrawler.getInstance().setVisible(false);		FIXME needs to be reimplemented
		
		setSize(800, 600);
		
		//Creates Title in north of JFrame
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(1,3));
		Health health = Health.getInstance();
		northPanel.add(health);
		health.updateHealth();
		JTextField title = new JTextField(25);
		title.setEditable(false);
		title.setText("Battle with " + enemy.name);
		northPanel.add(title);
		EnemyHealth enemyHealth = new EnemyHealth(enemy);
		northPanel.add(enemyHealth);
		enemyHealth.updateHealth();
		add(northPanel, BorderLayout.NORTH);
		
		setVisible(true);
	}
	
	private void destroyWindow() {
		setVisible(false);
		dispose();
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
}
