package com.book.ui;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.book.Book;
import com.book.util.ParseBookList;


public class Main {
	
	
	private static List<Book> bookList = new ArrayList<Book>();
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		logger.info("start");
		bookList = loadBookList();	
		MainPanel mpanel = new MainPanel("BOOK",bookList);
	}
	
	public static List<Book> loadBookList() {
		List<Book> bookList = new ArrayList<Book>();
		try {
			bookList = ParseBookList.getBookList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookList;
	}
}
