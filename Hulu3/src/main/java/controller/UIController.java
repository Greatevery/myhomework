package controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import creature.Creature;
import faction.HuluFaction;
import faction.ScorpionFaction;

public class UIController extends JPanel {
	public final static int WIDTH = 700; // 地图的寬
	public final static int HEIGHT = 630; // 地图的高
	public final static int cellLength = 70; // 一個單元的邊長
	
	private static UIController controller = new UIController(); // 単例模式

	private Map map;
	private HuluFaction huluFaction;
	private ScorpionFaction scorpionFaction;
	private ScreenRecorder screenRecorder; //記錄戰鬥過程的一個線程
	ArrayList<Creature> creatures; // 存放所有需要绘制的生物
	
	private boolean isStarted = false; // 设置标志位，表示遊戲是否開始
	private boolean gameOver = false; // 判斷遊戲是否結束
	
	private String[][] content = new String[WIDTH][HEIGHT]; //地圖坐標
	private String winner; // 勝利者
	
	//單例模式，全局全局唯一一個UIController的實例
	public static UIController getInstance() {
		return controller;
	}

	public String[][] getContent() {
		return content;
	}

	public void initWorld() {
		// 成員初始化
		map = new Map();
		huluFaction = new HuluFaction();
		scorpionFaction = new ScorpionFaction();
		// 敌我双方阵型初始化
		huluFaction.initializeFormation(cellLength);
		scorpionFaction.initializeFormation(cellLength);
		// 将所有生物添加到creatures数组里
		creatures = new ArrayList<Creature>();
		creatures.addAll(huluFaction.getCreatures());
		creatures.addAll(scorpionFaction.getCreatures());

		// 添加鍵盤事件监听器
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (!isStarted) {
						isStarted = true;
						huluFaction.start();
						scorpionFaction.start();
						Thread fighting = new Thread(new Fighting());
						fighting.start();
						screenRecorder = new ScreenRecorder();
						screenRecorder.start();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_L) {
					if (isStarted == false || gameOver == true) {
						JFileChooser jfc = new JFileChooser();
						jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						jfc.showDialog(new JLabel(), "选择");
						File file = jfc.getSelectedFile();
						if (file.getName().equals("records")) {
							//播放當前文件夾下所有圖片
							RecordPlayer player = new RecordPlayer();
					}
				}
				}
			}
		});
		this.setFocusable(true);
	}
	
	//兩軍對戰
	public void fight(ArrayList<Creature> creatures1, ArrayList<Creature> creatures2) {
		for (Creature creature1 : creatures1)
			for (Creature creature2 : creatures2) {
				Random random = new Random();
				int x1 = creature1.getLocation().getX();
				int y1 = creature1.getLocation().getY();
				int x2 = creature2.getLocation().getX();
				int y2 = creature2.getLocation().getY();
				if ((Math.abs(x1 - x2) <= cellLength / 2 && y1 == y2) || (Math.abs(y1 - y2) <= cellLength / 2 && x1 == x2)) {
					if (creature1.isAlive() && creature2.isAlive()) {
						int key = random.nextInt(2);
						if (key == 0) {
							creature1.setAlive(false);
							creature1.setExit(true);
							content[creature1.getLocation().getX()][creature2.getLocation().getY()] = null;
						} else {
							creature2.setAlive(false);
							creature2.setExit(true);
							content[creature2.getLocation().getX()][creature2.getLocation().getY()] = null;
						}
					}
				}
			}
	}
	
	//判斷該陣營生物是否全部陣亡
	public boolean isAllDead(ArrayList<Creature> creatures) {
		int i;
		for (i = 0; i < creatures.size(); ++i) {
			Creature creature = creatures.get(i);
			if (creature.isAlive())
				break;
		}
		if (i >= creatures.size()) {
			return true;
		} else
			return false;
	}
	//創建一個控制雙方交戰的線程
	class Fighting implements Runnable {
		
		private volatile boolean exit = false;
		
		public void run() {
			// TODO Auto-generated method stub
			while (!exit) {
				try {
					Thread.sleep(100);
					ArrayList<Creature> creatures1 = huluFaction.getCreatures();
					ArrayList<Creature> creatures2 = scorpionFaction.getCreatures();
					// 兩軍對戰
					fight(creatures1, creatures2);
					// 判斷是否一方生物是否全部死亡
					if (isAllDead(creatures1) || isAllDead(creatures2)) {
						//如果葫蘆娃陣營全部死亡，蝎子精陣營的所有生物也停止移動
						if (isAllDead(creatures1)) {
							for (Creature creature2 : creatures2) {
								creature2.setExit(true);
							}
							winner = scorpionFaction.getName();
						}
						else {
							//如果蝎子精陣營全部死亡，葫蘆娃陣營所有生物停止移動
							for (Creature creature1 : creatures1) {
								creature1.setExit(true);
							}
							winner = huluFaction.getName();
						}
						//遊戲結束，并結束所有線程
						gameOver = true;
						Thread.sleep(1000);
						screenRecorder.setExit(true);
						exit = true;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 繪製地圖
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 9; j++)
				g.drawImage(map.getImage(), i * cellLength, j * cellLength, this);
		// 繪製生物
		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.get(i);
			int x = creature.getLocation().getX();
			int y = creature.getLocation().getY();
			g.drawImage(creature.getImage(), x, y, this);
			if (content[x][y] == null) {
				Graphics2D g2 = (Graphics2D) g; // g是Graphics对象
				g2.setStroke(new BasicStroke(3.0f));
				g2.setColor(Color.red);
				g2.drawLine(x + cellLength / 4, y + cellLength / 4, x + (3 * cellLength) / 4, y + (3 * cellLength) / 4);
				g2.drawLine(x + (3 * cellLength) / 4, y + cellLength / 4, x + cellLength / 4, y + (3 * cellLength) / 4);
			}
		}
		//繪製遊戲結束畫面
		if (gameOver) {
			g.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.PLAIN, 60));
			g.setColor(Color.black);
			g.drawString(winner + "勝利", WIDTH / 4, HEIGHT / 2);
		}
	}
}
