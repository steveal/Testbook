package com.test;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {

	private JFrame frame = new JFrame("登录XX");
	private Container c = frame.getContentPane();
	private JTextField username = new JTextField();
	private JPasswordField password = new JPasswordField();
	private JButton ok = new JButton("确定");
	private JButton cancel = new JButton("取消");
	public Login(){
		frame.setSize(300,200);
		c.setLayout(new BorderLayout());
		initFrame();
		frame.setVisible(true);
	}

	private void initFrame() {
		
		//顶部
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		titlePanel.add(new JLabel("系统管理员登录"));
		c.add(titlePanel,"North");
		
		//中部表单
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(null);
		JLabel l1 = new JLabel("用户名:");
		l1.setBounds(50, 20, 50, 20);
		JLabel l2 = new JLabel("密    码:");
		l2.setBounds(50, 60, 50, 20);
		fieldPanel.add(l1);
		fieldPanel.add(l2);
		username.setBounds(110,20,120,20);
		password.setBounds(110,60,120,20);
		fieldPanel.add(username);
		fieldPanel.add(password);
		c.add(fieldPanel,"Center");
		
		//底部按钮
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		c.add(buttonPanel,"South");
	}
	
	public static void main(String[] args){
		new Login();
	}
	
}