package com.test;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
public class test {
	
	private static Logger logger = LoggerFactory.getLogger(test.class);
	private static String STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class";
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test TagNode
//		TagNode tagNode = new TagNode();
//		tagNode.setText("<div>ppppp</div>");
//		System.out.println(tagNode.getText());
//		System.out.println(tagNode.toHtml());
		
//		System.out.println(logger.getName());
		System.out.println(logger.getClass().getName());
		logger.trace("test.main() trace");
		logger.info("test.main() info");
		logger.debug("test.main() debug");
		System.out.println(((ch.qos.logback.classic.Logger) logger).getLevel());
		Set staticLoggerBinderPathSet = new LinkedHashSet();
	    try {
	      Enumeration paths;
	      ClassLoader loggerFactoryClassLoader = LoggerFactory.class.getClassLoader();
	      
	      if (loggerFactoryClassLoader == null)
	        paths = ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH);
	      else {
	        paths = loggerFactoryClassLoader.getResources(STATIC_LOGGER_BINDER_PATH);
	      }

	      while (paths.hasMoreElements()) {
	        URL path = (URL)paths.nextElement();
	        staticLoggerBinderPathSet.add(path);
	        System.out.println(path.getPath());
	      }
	    } catch (IOException ioe) {
//	      Util.report("Error getting resources from path", ioe);
	    	System.out.println("Error getting resources from path");
	    	ioe.printStackTrace();
	    }
	    
	    StaticLoggerBinder t = StaticLoggerBinder.getSingleton();
	    System.out.println(t.getClass().getName());
	    System.out.println(t.getLoggerFactory().getClass().getName());
	    
	    ClassLoader testClassLoader = test.class.getClassLoader();
	    Enumeration<URL> eurl;
		try {
			eurl = testClassLoader.getResources("com/book/book.class");
			while(eurl.hasMoreElements()) {
				URL path = (URL)eurl.nextElement();
				System.out.println(path.getPath());
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

}
