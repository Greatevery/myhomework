package creature;

import java.net.URL;
import javax.swing.ImageIcon;

public class Snake extends Creature{
	public Snake() {
		super("蛇精");
		URL loc = this.getClass().getClassLoader().getResource("蛇精.png");
	    ImageIcon icon = new ImageIcon(loc);
	    this.setImage(icon.getImage());
	    this.speedX = -STEP;
	    this.speedY = 0;
	}
	
}
