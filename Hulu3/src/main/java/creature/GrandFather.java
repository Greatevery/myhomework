package creature;

import java.net.URL;
import javax.swing.ImageIcon;

public class GrandFather extends Creature{
	public GrandFather() {
		super("爷爷");
		URL loc = this.getClass().getClassLoader().getResource("爷爷.png");
	    ImageIcon icon = new ImageIcon(loc);
	    this.setImage(icon.getImage());
	    speedX = STEP;
	    speedY = 0;
	}
}
