package controller;

import java.awt.*;
import javax.swing.*;
public class RecordPlayer extends JFrame{
   Dimension screenSize;
   public RecordPlayer() {
   super();
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds(350,200,700,700);
    Screen screen = new Screen();
    add(screen);
    new Thread(screen).start();
    setVisible(true);
  }
}
class Screen extends JPanel implements Runnable{
  private Image cimage;
  
  public void run(){
  int i = 0;
    while(true){
      try{
        cimage = loadImage(i + ".jpg");
        i++;
        repaint();
        Thread.sleep(40);//与录像时每秒帧数一致
      }catch(Exception e){
        e.printStackTrace();
        System.out.println(e);
      }
    }
  }
  public Image loadImage(String name) {
    Toolkit tk = Toolkit.getDefaultToolkit();
    Image image;
    image = tk.getImage("C:/records/" + name);
    MediaTracker mt = new MediaTracker(this);
    mt.addImage(image, 0);
    try {
      mt.waitForID(0);
    }catch (Exception e) {
      e.printStackTrace();
      System.out.println(e);
    }
    return image;
  }
  public Screen() {
    super();
    this.setLayout(null);
  }
  @Override
  public void paint(Graphics g){
    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.drawImage(cimage, 0, 0, null);
  }
}
