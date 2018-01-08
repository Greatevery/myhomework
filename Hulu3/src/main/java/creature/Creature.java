package creature;

import java.awt.Image;
import java.util.Random;

import controller.UIController;

public class Creature implements Runnable {

	private UIController controller = UIController.getInstance();
	private boolean isAlive = true; // 判斷該生物是否活著
	private String[][] content = controller.getContent();
	private volatile boolean exit = false; //同步關鍵字，控制是否結束線程
	
	protected String name;
	protected Image image;
	protected Location location; //生物當前所在位置
	protected final int STEP = UIController.cellLength / 5; // 生物每次移动的距离
	protected int speedX; // 生物前行的速度的x轴分量
	protected int speedY; // 生物前行的速度的y轴分量
	protected String faction; // 当前生物所属阵营
	
	public Creature(String name) {
		this.name = name;
	}

	public Creature(String name, Image image) {
		this.name = name;
		this.image = image;
	}

	public Creature(String name, Image image, Location location) {
		this.name = name;
		this.image = image;
		this.location = location;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
		content[location.getX()][location.getY()] = faction;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}
	
	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	// 改变生物体的前进方向
	public void changeDirection() {
		Random random = new Random();
		int r = random.nextInt(3);
		switch (r) {
		case 0:
			speedX = -speedX;
			speedY = -speedY;
			break;
		case 1:
			int temp1 = speedX;
			speedX = speedY;
			speedY = temp1;
			break;
		case 2:
			int temp2 = speedX;
			speedX = -speedY;
			speedY = -temp2;
			break;
		}
	}

	// 判断当前生物能否移动
	public boolean canMove() {
		int tempX = location.getX() + 3 * speedX;
		int tempY = location.getY() + 3 * speedY;
		// 判断下一个位置是否撞到边界
		if (!(tempX >= 0 && tempX <= UIController.WIDTH - UIController.cellLength && tempY >= 0
				&& tempY <= UIController.HEIGHT - UIController.cellLength))
			return false;
		// 判断下一个位置是否有属于同一阵营的活著的生物，如果有则不能移动
		if (content[tempX][tempY] == faction)
			return false;
		return true;
	}

	public synchronized void move() {
		int tempX = location.getX() + speedX;
		int tempY = location.getY() + speedY;
		// 将原来位置清空
		content[location.getX()][location.getY()] = null;
		// 将生物移动到新的位置
		location.setX(tempX);
		location.setY(tempY);
		content[tempX][tempY] = faction;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while (!exit) {
			if (canMove())
				move();
			else {
				changeDirection();
				continue;
			}
			try {

				Thread.sleep(100);
				this.controller.repaint();

			} catch (Exception e) {

			}
		}
	}

}
