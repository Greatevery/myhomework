package creature;

import java.awt.Image;

public class LittleMonster extends Creature{

	public LittleMonster(String name, Image image) {
		super(name,image);
		speedX = -STEP;
		speedY = 0;
		// TODO Auto-generated constructor stub
	}
}
