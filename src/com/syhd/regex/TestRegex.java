package com.syhd.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {
	public static void main(String[] args) {
			String uri = "/piapia-vr-portal/news00001003.html";
			 Pattern pattern = Pattern.compile("(news[0-9]{8}.html)|(comment[0-9]{8}.html)|news.html)");
			 Matcher matcher = pattern.matcher(uri);
			 while (matcher.find()) {
				 System.out.println(matcher.group());
			 }
			 uri = "/piapia-vr-portal/comment00001003.html";
			 matcher = pattern.matcher(uri);
			 while (matcher.find()) {
				 System.out.println(matcher.group());
			 }
		}
}

