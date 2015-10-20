package com.book.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.book.Book;
import com.book.BookChapter;

public class Util {
	
	private static Logger logger = LoggerFactory.getLogger(Util.class);

	public static String createBookIndexPage(String store, Book b) {
		String content = "";
		for (BookChapter bc : b.getChapterList()) {
			content += "<dd><a href=\"" + bc.getChapterFileName()
					+ "\" target=\"_blank\">" + bc.getChapterName()
					+ "</a></dd>" + "\n";
		}
		String html = "<html>"
				+ "\n"
				+ "<head>"
				+ "\n"
				+ "<title>"
				+ b.getBookName()
				+ "</title>"
				+ "\n"
				+ "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">"
				+ "\n" + "</head>" + "\n" + "<body bgcolor=\"#C7EDCC\">" + "\n"
				+ "<h1 align=\"center\">" + b.getBookName() + "</h1>" + "\n"
				+ "<dl>" + content + "</dl>" + "\n" + "</body>" + "\n"
				+ "</html>";
		return html;
	}

	private static String getChapterName(String html) {

		String chapterName = "";
		Lexer mLexer = new Lexer(new Page(html));
		Parser parser = new Parser(mLexer, new DefaultParserFeedback(
				DefaultParserFeedback.QUIET));

		NodeFilter nfChapterName = new TagNameFilter("h1");
		NodeList nlChapterNmae;
		try {
			nlChapterNmae = parser.parse(nfChapterName);
			Node[] nodelist = nlChapterNmae.toNodeArray();
			for (Node n : nodelist) {
				chapterName = n.toPlainTextString();
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return chapterName;
	}

	private static String getChapterContent(String html) {
		String content = "";

		Lexer mLexer = new Lexer(new Page(html));
		Parser parser = new Parser(mLexer, new DefaultParserFeedback(
				DefaultParserFeedback.QUIET));

		NodeFilter nfContent = new AndFilter(new TagNameFilter("div"),
				new HasAttributeFilter("id", "content"));
		NodeList nlContent;
		try {
			nlContent = parser.parse(nfContent);
			for (Node n : nlContent.toNodeArray()) {
				content = n.toHtml();
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return content;
	}

	public static String getChapter(String url, BookChapter bc) throws Exception {
		String response;

		response = Util.getPageResponse(url + bc.getChapterFileName());

		String chapterName = getChapterName(response);
		String content = getChapterContent(response);

		String script = "\n" + "<script type=\"text/javascript\">" + "\n" + "var prevpage = " + "\""
				+ bc.getPreviousChapterFileName() + "\"" + "\n" + "var nextpage = " + "\"" + bc.getNextChapterFileName()
				+ "\"" + "\n" + "var indexpage = \"index.html\"" + "\n" + "document.onkeydown=function(event){" + "\n"
				+ "var e = event || window.event || arguments.callee.caller.arguments[0];" + "\n"
				+ "if(e && e.keyCode==37){" + "\n" + "location = prevpage;" + "\n" + "}" + "\n"
				// + "if(e && e.keyCode==13){" + "\n"
				// + "location = indexpage;" + "\n"
				// + "}" + "\n"
				+ "if(e && e.keyCode==39){" + "\n" + "location = nextpage;" + "\n" + "}" + "\n" + "};" + "\n"
				+ "</script>" + "\n";
		String html = "<html>" + "<head>" + "<title>" + chapterName + "</title>"
				+ "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">" + script + "</head>"
				+ "<body bgcolor=\"#C7EDCC\"   style=\"line-height:1.5\">" + "<a href=\""
				+ bc.getPreviousChapterFileName() + "\">Previous Chapter" + "</a>" + "<br/>" + "<a href=\""
				+ bc.getNextChapterFileName() + "\">Next Chapter" + "</a>" + "<br/>"
				+ "<a href=\"index.html\">Index</a>" + "<br/>" + "<h1>" + chapterName + "</h1>" + "<p>" + content
				+ "</p>" + "<a href=\"" + bc.getPreviousChapterFileName() + "\">Previous Chapter" + "</a>" + "<br/>"
				+ "<a href=\"" + bc.getNextChapterFileName() + "\">Next Chapter" + "</a>" + "</body>" + "</html>";

		return html;

	}

	public static List<BookChapter> getChapterList(String url) throws Exception {
		List<BookChapter> bookChapterList = new ArrayList<BookChapter>();
		String reponse;

		reponse = getPageResponse(url);

		// String reponse = Util.readFileByLines("./file/indexpage.html");

		Lexer mLexer = new Lexer(new Page(reponse));
		Parser parser = new Parser(mLexer, new DefaultParserFeedback(DefaultParserFeedback.QUIET));

		NodeFilter nfChapterList = new AndFilter(new TagNameFilter("a"), new HasParentFilter(new TagNameFilter("dd")));
		NodeList nlChapterNmae;
		try {
			nlChapterNmae = parser.parse(nfChapterList);
			Node[] nodelist = nlChapterNmae.toNodeArray();
			for (Node node : nodelist) {
				TagNode tagNode = new TagNode();
				// 一旦得到了TagNode ， 就可以得到其中的属性值
				tagNode.setText(node.toHtml());
				String href = tagNode.getAttribute("href");
				BookChapter bc = new BookChapter(node.toPlainTextString(), href, "");
				bookChapterList.add(bc);

			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bookChapterList.get(0).setNextChapterFileName(bookChapterList.get(1).getChapterFileName());
		int length = bookChapterList.size();
		for (int i = 1; i < length - 1; i++) {
			bookChapterList.get(i).setNextChapterFileName(bookChapterList.get(i + 1).getChapterFileName());
			bookChapterList.get(i).setPreviousChapterFileName(bookChapterList.get(i - 1).getChapterFileName());
		}
		bookChapterList.get(length - 1)
				.setPreviousChapterFileName(bookChapterList.get(length - 2).getChapterFileName());

		return bookChapterList;
	}

	public static String getPageResponse(String url) throws Exception{
		return sendGet(url, null);
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) throws Exception{
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = "";
			if (null == param) {
				urlNameString = url;
			} else {
				urlNameString = url + "?" + param;
			}
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(10000);
			// connection.setRequestProperty("Content-Type",
			// "text/html; charset=utf-8");
			// 建立实际的连接
			
			if (connection.getResponseCode() >= 300) {
				logger.error("HTTP Request is not success, " + "URL is " + url + "Response code is " + connection.getResponseCode());
	            throw new Exception("HTTP Request is not success, Response code is " + connection.getResponseCode());
	        }
			
			
			// 获取所有响应头字段
//			Map<String, List<String>> map = connection.getHeaderFields();
			
			// 遍历所有的响应头字段
//			 for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			 }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("Get URL Failed,URL is " + url);
			throw e;
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static void writeFile(String fileName, String content) {
		try {
			File file = new File(fileName);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(content);
			bufferWritter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFileByLines(String fileName) {
		String content = "";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String temp;
			while (null != (temp = reader.readLine())) {
				content += temp + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return content;
	}
	
	public static void main(String[] args) {
		try {
			String xx = sendGet("http://logback.qos.ch/manual/introduction.htmlxx",null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(xx);
	}
}
