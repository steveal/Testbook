package com.test;

import org.htmlparser.nodes.TagNode;


public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TagNode tagNode = new TagNode();
		tagNode.setText("<div>ppppp</div>");
		System.out.println(tagNode.getText());
		System.out.println(tagNode.toHtml());
	}

}
