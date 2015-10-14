package com.test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestJFrame extends JFrame implements ActionListener {
	GridBagLayout g = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();

	TestJFrame(String str) {
		super(str);
		setSize(300, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(g);
		// 调用方法
		addComponent();
		submit.addActionListener(this);
		setVisible(true);
		setLocationRelativeTo(null);// 设居中显示;
	}

	// 在这个方法中将会添加所有的组件;
	// 使用的网格包布局;希望楼主能看懂;
	public void addComponent() {
		// 个人信息登记
		noteInformation = new JLabel("个人信息登记：");
		add(g, c, noteInformation, 0, 0, 1, 1);
		// 用户名
		userName = new JLabel("用户名：");
		add(g, c, userName, 0, 1, 1, 1);
		// 用户名输入框
		textUserName = new JTextField(10);
		add(g, c, textUserName, 1, 1, 2, 1);
		// 密码：
		password = new JLabel("密码：");
		add(g, c, password, 0, 2, 1, 1);
		// 密码输入框
		textUserPassword = new JTextField(10);
		add(g, c, textUserPassword, 1, 2, 2, 1);
		// 性别
		sex = new JLabel("性别:");
		add(g, c, sex, 0, 3, 1, 1);
		// 男 女单选框
		sexMan = new JRadioButton("男");
		add(g, c, sexMan, 1, 3, 1, 1);
		sexGirl = new JRadioButton("女");
		add(g, c, sexGirl, 2, 3, 1, 1);
		ButtonGroup group = new ButtonGroup();
		group.add(sexMan);
		group.add(sexGirl);
		// 出生日期
		birthday = new JLabel("出生日期:");
		add(g, c, birthday, 0, 4, 1, 1);
		// 复选框及其内容
		String[] YEARS = new String[65];
		for (int i = 1950, k = 0; i <= 2014; i++, k++) {
			YEARS[k] = i + "年";
		}
		year = new JComboBox(YEARS);
		add(g, c, year, 1, 4, 1, 1);
		// 复选框及内容
		month = new JComboBox(MONTH);
		add(g, c, month, 2, 4, 1, 1);
		// submit按钮
		submit = new JButton("submit");
		c.insets = new Insets(7, 0, 4, 0);
		add(g, c, submit, 1, 5, 1, 1);

		result = new JTextArea(15, 20);
		add(g, c, result, 0, 6, 3, 4);

	}

	/*
	 * public void ActionPerformed(ActionEvent e) { String
	 * s=textUserName.getText(); String t=textUserPassword.getText(); String
	 * k=sexMan.getText(); String v=sexGirl.getText(); String a=(String)
	 * year.getSelectedItem(); String b=(String)month.getSelectedItem(); String
	 * num="用户名："+s+"\n"+"密码: "+t+"性别: "+(k==null?v:k)+"\n"+"出生日期:"+a+" "+b;
	 * result.append(num); }
	 */
	public void add(GridBagLayout g, GridBagConstraints c, JComponent jc, int x, int y, int gw, int gh) {
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = gw;
		c.gridheight = gh;
		g.setConstraints(jc, c);
		add(jc);
	}

	public static void main(String args[]) {
		new TestJFrame("个人信息登记表");
	}

	JLabel noteInformation, userName, password;
	JLabel sex, birthday;
	JTextField textUserName, textUserPassword;
	JRadioButton sexMan, sexGirl;
	JComboBox year, month;
	JButton submit;
	JTextArea result;

	final String[] MONTH = { "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月" };


	public void actionPerformed(ActionEvent arg0) {

		String s = textUserName.getText();
		String t = textUserPassword.getText();
		String k = sexMan.getText();
		String v = sexGirl.getText();
		String a = (String) year.getSelectedItem();
		String b = (String) month.getSelectedItem();
		String num = "用户名：" + s + "\n" + "密码: " + t + "\n 性别: " + (k == null ? v : k) + "\n" + "出生日期:" + a + " " + b;
		result.setText(num);
	}
	
	

}