package creature;

import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;


public class Scorpion extends Creature{
	
	public Scorpion() {
		super("蝎子精");
		URL loc = this.getClass().getClassLoader().getResource("蝎子精.png");
	    ImageIcon icon = new ImageIcon(loc);
	    this.setImage(icon.getImage());
	    speedX = -STEP;
	    speedY = 0;
		// TODO Auto-generated constructor stub
	}
}
