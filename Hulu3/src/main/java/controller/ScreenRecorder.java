package controller;

import java.awt.*;
import java.awt.image.*;
import com.sun.image.codec.jpeg.*;
import java.io.*;

public class ScreenRecorder extends Thread{
	
	  private Dimension screenSize;
	  private Rectangle rectangle;
	  private Robot robot;
	  private long i = 0;
	  private JPEGImageEncoder encoder;
	  public volatile boolean exit = false;
	 
	  public ScreenRecorder() {
//	    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize = new Dimension(700,700);
	    rectangle = new Rectangle(335,10,700,700);//可以指定捕获屏幕区域
	    try{
	      robot = new Robot();
	    }catch(Exception e){
	      e.printStackTrace();
	      System.out.println(e);
	    }
	  }
	 
	  public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public void run(){
	    FileOutputStream fos = null;
	    while (!exit){
	      try{
	        BufferedImage image = robot.createScreenCapture(rectangle);//捕获制定屏幕矩形区域
	        fos = new FileOutputStream("records//" + i + ".jpg");
	        JPEGCodec.createJPEGEncoder(fos).encode(image);//图像编码成JPEG
	        fos.close();
	        i = i + 1;
	        Thread.sleep(40);//每秒25帧
	      }catch(Exception e){
	        e.printStackTrace();
	        System.out.println(e);
	        try{
	          if (fos != null)fos.close();
	        }catch(Exception e1){}
	      }
	    }
	  }
	}