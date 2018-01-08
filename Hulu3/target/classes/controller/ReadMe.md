#面向葫芦娃编程（三）
#主要类

1、Main类作为整个程序的入口。

2、Ground类表示的是阵地，并且实现放置各方阵营的方法.

3、Formation类作为阵型的基类，设置基本的属性和方法，其它的阵型均由该类派生而来。

4、Location类是位置类，抽象了坐标。

5、TooCrowdedException类是处理出界异常的类。

6、HuluFaction类是葫芦娃阵营，ScorpionFaction类是蝎子精阵营。

7、Creature类是生物体的基类，SheJing类和GrandFather类、HuluBrother类继承Creature类

8、HuluPosition类表示七个葫芦娃的七个位置

##设计理念
根据实际情况下两方对阵的情形进行抽象，Ground类作为阵地类，相当于一个战场。战场上敌我双方分别是蝎子精率领的小喽啰和葫芦娃，而蛇精和爷爷分别为双方助威，双方有不同的阵型，所以可以根据阵型的共同点抽象出不同阵型的共同基类Formation。


另外在Ground类要实现layOut方法，将不同阵型的Formation放置在战场上。

最后在Main类里实例化一个对象g作为战场，在该战场上变换阵型打印出来