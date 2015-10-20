package com.book.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.book.Book;

public class ParseBookList {

	public static String BOOK_LIST_FILE = "./file/book.xml";



	public static void getFileContent(String path) {
		File f = new File(path);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String temp = "";
			boolean flag = true;
			do {

				temp = br.readLine();
				if (null == temp) {
					flag = false;
				} else {
					System.out.println(temp);
				}
			} while (flag);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 遍历当前节点元素下面的所有(元素的)子节点
	 * 
	 * @param node
	 */
	public static List<Book> getBookList() throws Exception {

		List<Book> bookList = new ArrayList<Book>();

		SAXReader reader = new SAXReader();
		// 通过read方法读取一个文件 转换成Document对象
		Document document = reader.read(new File(ParseBookList.BOOK_LIST_FILE));
		// 获取根节点元素对象
		Element node = document.getRootElement();

		Iterator<Element> it = node.elementIterator();

		while (it.hasNext()) {
			Element e = it.next();
			String bookName = "";
			String bookAddress = "";
			String bookStore = "";

			List<Attribute> list = e.attributes();
			for (Attribute attr : list) {
				String attrName = attr.getName();
				String attrValue = attr.getValue();
				if ("name".equals(attrName)) {
					bookName = attrValue;
				} else if ("url".equals(attrName)) {
					bookAddress = attrValue;
				} else if ("store".equals(attrName)) {
					bookStore = attrValue;
				}
			}

			Book b = new Book(bookAddress, bookName);
			b.setBookStore(bookStore);
			bookList.add(b);
		}
		return bookList;
	}

	public static void WriteBookList(List<Book> bookList) throws Exception {
		
		Set<Book> set = new HashSet<Book>();
		set.addAll(bookList);
		
		bookList.clear();
		bookList.addAll(set);
		
		// 通过read方法读取一个文件 转换成Document对象
		// Document document = reader.read(new
		// File(ParseBookList.BOOK_LIST_FILE));
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("booklist");
		for (Book b : bookList) {
			Element ebook = root.addElement("book");
			ebook.addAttribute("name", b.getBookName());			
			ebook.addAttribute("url", b.getBookAddress());
			ebook.addAttribute("store", b.getBookStore());
		}

		// 紧凑的格式
		// OutputFormat format = OutputFormat.createCompactFormat();
		// 排版缩进的格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置编码
		format.setEncoding("UTF-8");
		// 创建XMLWriter对象,指定了写出文件及编码格式
		// XMLWriter writer = new XMLWriter(new FileWriter(new
		// File("src//a.xml")),format);
		XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(ParseBookList.BOOK_LIST_FILE)), "UTF-8"),
				format);
		// 写入
		writer.write(document);
		// 立即写入
		writer.flush();
		// 关闭操作
		writer.close();
	}
	
	public static void main(String[] args) {

		
//			List<Book> bookList = new ArrayList<Book>();
//			Book b = new Book("bookaddress","bookname");
//			b.setBookStore("bookstore");
//			bookList.add(b);
//			try {
//				WriteBookList(bookList);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		List<Book> bookList;
		try {
			bookList = getBookList();
			System.out.println(bookList.size());
			for(Book b : bookList) {
				System.out.println(b);
			}
			Set set = new HashSet();
			set.addAll(bookList);
			
			bookList.clear();
			bookList.addAll(set);
			System.out.println("-------------------");
			System.out.println(bookList.size());
			for(Book b : bookList) {
				System.out.println(b);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
