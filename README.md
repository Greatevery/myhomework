# 葫芦娃大战妖精

------




## 问题分析


##### 1.  背景是在一个N*M的二维空间上，该空间上任意一个位置坐标上仅可站立一个生物体。所以考虑设置一个N*M的数组，来表示该二维空间，通过数组每个元素的取值判断该位置上是否有生物。



##### 2. 葫芦娃、老爷爷和妖精（蝎子精、蛇精、小喽啰）阵营分别在左右两侧站队。考虑设计一个阵营类，分别容纳葫芦娃、老爷爷和妖精（蝎子精、蛇精、小喽啰），并且设计一个接口用于两个阵营的阵型的初始化。

##### 3. 各个生物体实现一个线程，当按空格键时，执行start()，向敌方前进。考虑从所有生物体中抽象出一个父类Creature，所有生物由该父类派生，并且由该父类实现Runnable接口。

##### 4. 当某个生物体与敌方相遇时，选取一个概率决定双方生死，死者留下尸体，某一方全部死亡时，游戏结束。考虑设计一个控制类UIController,控制整个战斗过程

## 具体实现
[共设计了三个包：creature、faction、controller]
### 1. creature包：共有9个类。
#####其中最重要的是Creature类，该类实现了Runnable接口：
```java
public void run() {
     // TODO Auto-generated method stub
	while (!exit) {
		if (canMove()) //判断当前生物能否移动
			move();
		else {
			changeDirection(); //改变前进方向
			continue;
		}
		try {
			Thread.sleep(100);
			this.controller.repaint();
		} catch (Exception e) {

		}
	}
}
```
另外有5个派生的生物类，一个Location类，一个Order枚举类和一个Color枚举类。
###2.faction包：共有三个类和一个接口。
#####Faction类作为父类，派生出huluFaction类和scorpionFaction类，两个子类又分别实现了InitializeFormation接口，用于初始化阵型。另外子类中生成了各个生物体的线程，供UIController类调用。
###3.controller包：共有五个类。
#####（1）Main类：作为程序的入口，该类继承了JFrame类，用于生成程序主体框架；
#####（2）ScreenRecorder类：记录战斗过程的类，该类继承了Thread类；
#####（3）RecordPlayer类：回放战斗过程的类；
#####（4）Map类：封装了一张图片，用于生成战斗背景图；
#####（5）UIController类：整个战斗过程控制类，实现不同对象之间的交互通信，采用单例模式，全局唯一。添加了一个键盘响应事件接受键盘输入。实现了一个内部类：Fighting类，该类实现了Runnable接口，用于模拟双方交战的场景，并时刻判断是否有一方生物全部死亡，即游戏是否结束。
##面向对象机制
###1.继承和多态。
#####所有的生物均由Creature类继承而来，葫芦娃阵营和蝎子精阵营则由Faction类继承而来。
###2.集合和泛型。
#####在Faction类中采用```ArrayList<Creature> creatures;```存储所有生物。
```java
grandFather = new GrandFather();
for(HuluBrother brother:brothers)
    this.creatures.add(brother);
creatures.add(grandFather);
```
##设计原则、模式
###1.LSP
#####HuluFaction类和scorpionFaction类在继承Faction类时为了避免修改父类放法，将initializeFormation方法抽象为一个接口，降低了父类和子类之间的耦合性。
###2.SRP
#####在进行类的设计的时候，尽可能地使每一个类只负责一项功能，避免当一个类被修改的时候，另一个类不会发生故障。
###3.单例模式
#####对UIController类采用单例模式，确保该类只有一个实例，而且自行实例化并向整个系统提供这个实例。

```java
private static UIController controller = new UIController(); 
public static UIController getInstance() {
		return controller;
	}
```
##多线程
#####共实现了17个线程，其中15个是生物体线程，一个是控制判断双方战斗的线程，一个是用于记录战斗过程的线程，还有一个是用于回放战斗经过的线程。
所有线程通过退出标志位exit终止线程，exit被定义为valitle类型，成为原子级别的操作。

记录战斗过程的线程是通过按一定频率对屏幕截屏的方式实现的，回放战斗经过的线程则通过对所截的图按原来的频率快速播放得到。

程序接受键盘响应，当按下空格键的时候，所有生物体线程，控制双方战斗的线程，以及记录战斗过程的线程同时启动。在战斗前或战斗结束后按L键，然后可以选择文件回放战斗经过，该文件和src在同一目录下，目录名称是records。

##注解
对于覆盖父类的方法，统一采用@Override进行标注，可供编译器检查。

