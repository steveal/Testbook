package com.book.ui;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.book.Book;
import com.book.util.ParseBookList;
import com.book.util.Util;

public class MainPanel extends JFrame {
	private GridBagLayout g = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();

	private JLabel lbookName, lstoreAddress, lbookContentUrl, JInfo;
	private JTextField tbookName, tstoreAddress, tbookContentUrl;
	private JButton bdownload;

	private BookTable bookTable;
	private JTable table;

	private static final ImageIcon icon = new ImageIcon("./file/image/title.png"); // 将要显示到托盘中的图标
	
	private SystemTray tray;
	private PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
	private final MenuItem show = new MenuItem("open");
	private  final MenuItem exit = new MenuItem("exit");
	
	private List<Book> bookList = new ArrayList<Book>();

	
	
	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public MainPanel(String title,List<Book> bookList) {
		super(title);
		this.bookList = bookList;
		setSize(505, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponet();
		setLayout(g);
		setVisible(true);
		setLocationRelativeTo(null);// 设居中显示;
		{
			// Tray

			pop.add(show);
			pop.add(exit);
			
			tray = SystemTray.getSystemTray();
			TrayIcon trayIcon = new TrayIcon(icon.getImage(), "book tray", pop);
			trayIcon.setImageAutoSize(true);
			trayIcon.setToolTip("book tray");

			// 选项注册事件
			ActionListener al2 = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 退出程序
					if (e.getSource() == exit) {
						System.exit(0);// 退出程序
					}
					// 打开程序
					if (e.getSource() == show) {
						
						getPanel().setVisible(true);
					}
				}
			};
			exit.addActionListener(al2);
			show.addActionListener(al2);

			try {
				tray.add(trayIcon); // 将托盘图标添加到系统的托盘实例中
			} catch (AWTException ex) {
				ex.printStackTrace();
			}

		}
	}

	private void addComponet() {
		{
			lbookName = new JLabel("书名");
			add(g, c, lbookName, 0, 0, 1, 1);

			tbookName = new JTextField(15);
			add(g, c, tbookName, 1, 0, 2, 1);
		}
		{
			lstoreAddress = new JLabel("下载到 ");
			add(g, c, lstoreAddress, 0, 1, 1, 1);

			tstoreAddress = new JTextField(15);
			add(g, c, tstoreAddress, 1, 1, 2, 1);
		}
		{
			lbookContentUrl = new JLabel("下载网址 ");
			add(g, c, lbookContentUrl, 0, 2, 1, 1);

			tbookContentUrl = new JTextField(15);
			add(g, c, tbookContentUrl, 1, 2, 2, 1);
		}

		{
			bdownload = new JButton("Download");
			add(g, c, bdownload, 2, 3, 1, 1);
			bdownload.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent arg0) {
					// String store = "F:\\book\\linyu\\";
					// String bookIndex = "http://www.xbiquge.com/0_311/";
					// String bookName = "灵域";
					String store = tstoreAddress.getText();
					if(!store.endsWith("\\") && !store.endsWith("/")) {
						store = store + File.separator;
					}
					File f = new File(store);
					if(!f.exists()) {
						f.mkdirs();
					}					
					
					String bookName = tbookName.getText();
					String bookIndex = tbookContentUrl.getText();
					if(!bookIndex.endsWith("\\") && !bookIndex.endsWith("/")) {
						bookIndex = bookIndex + "\\";
					}
					
					Book b = new Book(bookIndex, bookName);
					b.setBookStore(store);
					try{
						Util.DownloadBook(b, store);
					}catch(Exception e) {
						//e.printStackTrace();
					}
					javax.swing.JOptionPane.showMessageDialog(null,"Download Complete!");
					bookList.add(b);
					table.updateUI();
					table.repaint();
				}
			});

		}
		{

			
			// String[] columnNames = {"A","B"}; //列名
			// String  [][]tableVales={{"A1","B1"},{"A2","B2"},{"A3","B3"},{"A4","B4"},{"A5","B5"}};
			// //数据
			// tableModel = new DefaultTableModel(tableVales,columnNames);
			// table = new JTable(tableModel);
			// JScrollPane scrollPane = new JScrollPane(table); //支持滚动
			// add(g,c,scrollPane,0,4,3,3);

			bookTable = new BookTable(bookList);
			table = new JTable(bookTable);
			JScrollPane scrollPane = new JScrollPane(table); // 支持滚动
			add(g, c, scrollPane, 0, 4, 3, 3);
			table.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(MouseEvent e) {// 仅当鼠标单击时响应
					if (2 == e.getClickCount()) {
						// 得到选中的行列的索引值

						Book b = bookList.get(table.getSelectedRow());
						System.out.println(b);
						// 得到选中的单元格的值，表格中都是字符串
						// javax.swing.JOptionPane.showMessageDialog(null, b);
						Desktop dt = Desktop.getDesktop();
						try {
							dt.open(new File(b.getBookStore() + "\\index.html"));
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}

				}

			});
		}

	}

	public void add(GridBagLayout g, GridBagConstraints c, JComponent jc, int x, int y, int gw, int gh) {
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = gw;
		c.gridheight = gh;
		g.setConstraints(jc, c);
		add(jc);
	}
	
	/***
     * 重写此方法让用户确认是否需要关闭
     * */
    @Override
    protected void processWindowEvent(WindowEvent e) {
        boolean flag = false;
        
        if (e.getID() == WindowEvent.WINDOW_CLOSING) { 
          //关闭的提示选择
          int result= JOptionPane.showConfirmDialog(
                              this,
                              ("确认要关闭吗？"),
                              ("关闭"),
                              JOptionPane.YES_NO_OPTION);

              if(result == JOptionPane.NO_OPTION){
                  //不关闭，系统托盘？？？？
                  flag = true;
                  getPanel().setVisible(false);
                  getPanel().dispose();
                  return;
              }else{
                  try {
					ParseBookList.WriteBookList(bookList);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
              }
        }
         if(!flag){
           //点击的了YES,那么交给上面去处理关闭的处理
            super.processWindowEvent(e);
          }
    }
    
    private MainPanel getPanel() {
    	return this;
    }
	public static void main(String[] args2) {

		
	}
}
