package creature;

import java.awt.Image;
import java.util.Random;

//单个葫芦娃
public class HuluBrother extends Creature{
	
	private Order order;//排行
	private Color color;//颜色

	public HuluBrother(String name,Image image, Order order,Color color){
		super(name, image);
		this.order = order;
		this.color=color;
		this.setSpeedX(STEP);
		this.setSpeedY(0);
	}

	public Order getOrder() {
		return order;
	}
}