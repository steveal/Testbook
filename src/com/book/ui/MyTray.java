package com.book.ui;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.TimerTask; 
import javax.swing.*; 
public class MyTray 
{
TrayIcon trayIcon; // 托盘图标
 SystemTray tray; // 本操作系统托盘的实例
 public MyTray()
 { 
  final JFrame frame=new JFrame("系统托盘");
  frame.setSize(300,200); 
  frame.setVisible(true); 
  tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例
  ImageIcon icon = new ImageIcon("./file/image/title.png"); // 将要显示到托盘中的图标
  
  PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
  final MenuItem show = new MenuItem("打开程序");
  final MenuItem exit = new MenuItem("退出程序");
  pop.add(show);
  pop.add(exit);
  trayIcon = new TrayIcon(icon.getImage(),"系统托盘", pop);//实例化托盘图标
  trayIcon.setImageAutoSize(true);
  //为托盘图标监听点击事件
  trayIcon.addMouseListener(new MouseAdapter() 
  {
      public void mouseClicked(MouseEvent e)
      {
         if(e.getClickCount()== 2)//鼠标双击图标
         { 
        	// trayIcon.displayMessage("警告", "这是一个警告提示!", TrayIcon.MessageType.WARNING); 
        	// trayIcon.displayMessage("错误", "这是一个错误提示!", TrayIcon.MessageType.ERROR); 
        	 //trayIcon.displayMessage("信息", "这是一个信息提示!", TrayIcon.MessageType.INFO); 
        	 //tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标    
           frame.setExtendedState(JFrame.NORMAL);//设置状态为正常
           frame.setVisible(true);//显示主窗体
          }
      }
   });
  
  //选项注册事件
  ActionListener al2=new ActionListener()
     {
       public void actionPerformed(ActionEvent e)
       {
        //退出程序
        if(e.getSource()==exit)
        {
         System.exit(0);//退出程序
        }
        //打开程序
        if(e.getSource()==show)
        {
         frame.setExtendedState(JFrame.NORMAL);//设置状态为正常
      frame.setVisible(true);
        }
       }
      };
      exit.addActionListener(al2);
      show.addActionListener(al2);
        
      try 
      {
           tray.add(trayIcon); // 将托盘图标添加到系统的托盘实例中
       } 
      catch(AWTException ex)
      {
           ex.printStackTrace();
       }
   
   //为主窗体注册窗体事件
   frame.addWindowListener(new WindowAdapter()
   {
    //窗体最小化事件
    public void windowIconified(WindowEvent e)
        {    
            frame.setVisible(false);//使窗口不可视
            frame.dispose();//释放当前窗体资源
         }   
   });
   
   
   //加个定时监听,每到什么时候就提醒
   try
	{
    java.util.Timer timer = new java.util.Timer();
    timer.schedule(new TimerTask() {
        public void run() {  
   		 String eet="2012-08-28 17:39:30";
   		 String str=thisTime().replace("-","").replace(":","").replace(" ","");
   		 String str1=eet.replace("-","").replace(":","").replace(" ","");
   		 int et=Integer.parseInt(str.substring(4));
   		 int et1=Integer.parseInt(str1.substring(4));
   		 int t=0;
   		 if(et1-et<=0)
   		 {
   			 t++;
   			 if(t<2)
   			 {
   		        trayIcon.displayMessage("警告", "这是一个警告提示!", TrayIcon.MessageType.WARNING);
   			 }
   		 }
        }
    }, 0, 30000); 
    //监听结束
	}
	catch(Exception ex)
	{
		System.out.println("Timer exception"); 
	} 
 }
	 public  String thisTime()
		{
		   String result="";
		   SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		   TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//获取中国的时区
		   df1.setTimeZone(timeZoneChina);//设置系统时区
		   String mctime=df1.format(new Date());
		   result=mctime; 
		   return result;
	    }
	 public static void main(String[] args) 
	 {
	  new MyTray();
	 }
}