package controller;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Map {
	private Image image;
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Map() {
		URL loc = this.getClass().getClassLoader().getResource("背景.png");
	    ImageIcon icon = new ImageIcon(loc);
	    image = icon.getImage();
	}
}
