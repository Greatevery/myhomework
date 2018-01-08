package controller;
import javax.swing.JFrame;


public class Main extends JFrame{
	public Main() {
		super("葫芦娃大战妖精");
		UIController controller = UIController.getInstance();
		controller.initWorld();
		add(controller);
		setBounds(350,200,700,700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

    public static void main(String[] args) {
    	new Main();
      }
}
