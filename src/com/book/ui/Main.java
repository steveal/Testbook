package com.book.ui;

import java.util.ArrayList;
import java.util.List;

import com.book.Book;
import com.book.util.ParseBookList;


public class Main {
	
	static List<Book> bookList = new ArrayList<Book>();
	
	public static void main(String[] args) {
		bookList = loadBookList();
		MainPanel mpanel = new MainPanel("BOOK",bookList);
		System.out.println("mpanel close");
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
