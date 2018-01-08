package faction;

import java.net.URL;

import javax.swing.ImageIcon;

import controller.UIController;
import creature.Creature;
import creature.LittleMonster;
import creature.Location;
import creature.Scorpion;
import creature.Snake;

public class ScorpionFaction extends Faction implements InitializeFormation{
	private Scorpion scorpion;
	private Snake snake;
	private LittleMonster[] monsters;
	
	public ScorpionFaction() {
		super("蝎子精阵营");
		scorpion = new Scorpion();
		snake = new Snake();
		monsters = new LittleMonster[5];
		URL loc;
		ImageIcon icon;
		loc = this.getClass().getClassLoader().getResource("小喽啰1.png");
	    icon = new ImageIcon(loc);
	    monsters[0] = new LittleMonster("小喽啰1", icon.getImage());
	    loc = this.getClass().getClassLoader().getResource("小喽啰2.png");
	    icon = new ImageIcon(loc);
	    monsters[1] = new LittleMonster("小喽啰2", icon.getImage());
	    loc = this.getClass().getClassLoader().getResource("小喽啰3.png");
	    icon = new ImageIcon(loc);
	    monsters[2] = new LittleMonster("小喽啰3", icon.getImage());
	    loc = this.getClass().getClassLoader().getResource("小喽啰4.png");
	    icon = new ImageIcon(loc);
	    monsters[3] = new LittleMonster("小喽啰4", icon.getImage());
	    loc = this.getClass().getClassLoader().getResource("小喽啰5.png");
	    icon = new ImageIcon(loc);
	    monsters[4] = new LittleMonster("小喽啰5", icon.getImage());
		
	    this.creatures.add(scorpion);
	    this.creatures.add(snake);
	    for(LittleMonster monster:monsters)
	    	this.creatures.add(monster);
	    for(Creature creature:creatures)
	    	creature.setFaction(name);
	}
	
	public LittleMonster[] getLittleMonster() {
		return monsters;
	}

	public Snake getSnake() {
		return snake;
	}

	public Scorpion getScorpion() {
		return scorpion;
	}
	
	public void start() {
		new Thread(scorpion).start();
		new Thread(snake).start();
		for (LittleMonster monster : monsters)
			new Thread(monster).start();
	}

	public void initializeFormation(int cellLength) {
		scorpion.setLocation(new Location(6*cellLength, 4*cellLength));
		snake.setLocation(new Location(7*cellLength, 3*cellLength));
		for(int i = 0;i < 3; i++) {
			monsters[i].setLocation(new Location((7+i)*cellLength,(5 + i)*cellLength));
		}
		for(int i = 3; i < monsters.length; i++)
			monsters[i].setLocation(new Location((i+5)*cellLength, (5-i)*cellLength));
	}
}
