package com.test;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1712553877823684583L;

	private Button download = new Button("Download");
	private Label lbookUrl = new Label("BookUrl");
	private TextField tbookUrl = new TextField("http://");

	public MainFrame() {
		super("Book");
		addWindowListener(new BookWindowListener());
//		setLayout(new FlowLayout());
		setLayout(new GridLayout(2,2));
		download.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("download  button clicked");

			}
		});

		add(lbookUrl);
		add(tbookUrl);
		add(download);
		pack();
		
		setVisible(true);
	}
}
