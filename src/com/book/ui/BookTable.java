package com.book.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.book.Book;

public class BookTable extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1988636362278749078L;

	private List<Book> bookList = new ArrayList<Book>();
	
	private String[] columnsName = new String[]{"Name","URL","Store"};
	public BookTable(List<Book> bookList) {
		this.bookList = bookList;
	}
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnsName.length;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return bookList.size();
	}
	
	public String getColumnName(int rowIndex){
		return columnsName[rowIndex];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Book b =  bookList.get(rowIndex);
		if(0==columnIndex) {
			return b.getBookName();
		}else if (1==columnIndex) {
			return b.getBookAddress();
		}else if (2==columnIndex) {
			return b.getBookStore();
		} 
		return null;
	}

}
