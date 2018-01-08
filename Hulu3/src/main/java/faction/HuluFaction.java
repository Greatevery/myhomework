package faction;

import creature.HuluBrother;
import creature.Location;
import creature.Order;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import creature.Color;
import creature.Creature;
import creature.GrandFather;

public class HuluFaction extends Faction implements InitializeFormation{
	private HuluBrother[] brothers;
	private GrandFather grandFather;
	
	public HuluFaction(){
		super("葫芦娃阵营");
		brothers=new HuluBrother[7];
		URL loc;
        ImageIcon icon;
        
        loc = this.getClass().getClassLoader().getResource("大娃.png");
        icon = new ImageIcon(loc);
		brothers[0] = new HuluBrother("红娃", icon.getImage(), Order.一, Color.红色);
		loc = this.getClass().getClassLoader().getResource("二娃.png");
        icon = new ImageIcon(loc);
		brothers[1] = new HuluBrother("橙娃", icon.getImage(), Order.二,Color.橙色);
		loc = this.getClass().getClassLoader().getResource("三娃.png");
        icon = new ImageIcon(loc);
		brothers[2] = new HuluBrother("黄娃", icon.getImage(), Order.三,Color.黄色);
		loc = this.getClass().getClassLoader().getResource("四娃.png");
        icon = new ImageIcon(loc);
		brothers[3] = new HuluBrother("绿娃", icon.getImage(), Order.四,Color.绿色);
		loc = this.getClass().getClassLoader().getResource("五娃.png");
        icon = new ImageIcon(loc);
		brothers[4] = new HuluBrother("青娃", icon.getImage(), Order.五,Color.青色);
		loc = this.getClass().getClassLoader().getResource("六娃.png");
        icon = new ImageIcon(loc);
		brothers[5] = new HuluBrother("蓝娃", icon.getImage(), Order.六,Color.蓝色);
		loc = this.getClass().getClassLoader().getResource("七娃.png");
        icon = new ImageIcon(loc);
		brothers[6] = new HuluBrother("紫娃", icon.getImage(), Order.七,Color.紫色);
		
		
		grandFather = new GrandFather();
		for(HuluBrother brother:brothers)
			this.creatures.add(brother);
		creatures.add(grandFather);
		//設置每一個生物所屬陣營
		for(Creature creature:creatures)
	    	creature.setFaction(name);
	}

	public HuluBrother[] getBrothers() {
		return brothers;
	}
	public GrandFather getGrandFather() {
		return grandFather;
	}
	public void start() {
		for (HuluBrother brother : brothers)
			new Thread(brother).start();
		new Thread(grandFather).start();
	}
	
	public void initializeFormation(int cellLength) {
		grandFather.setLocation(new Location(0,cellLength));
		for(int i = 0; i < brothers.length; ++i)
			brothers[i].setLocation(new Location(0, (i+2)*cellLength));
	}

}