package com.syhd.dynamicProgram;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1.4 BuyBook问题
 * 
 * @author hadoop
 *
 */
public class Buybook {

	static class Book {
		public static final int PRICE = 8;
		static Map<String, Double> bookmap = new HashMap<String, Double>();
		static double[] perference = new double[5];
		static {
			bookmap.put("0-0-0-0-0-", Double.valueOf(0));
			perference[4] = 0;
			perference[3] = 0.05;
			perference[2] = 0.1;
			perference[1] = 0.2;
			perference[0] = 0.25;
		}
		int chapter;
	}

	public static int[] dealBookArr(int[] book, int i) {
		int[] copy = Arrays.copyOf(book, book.length);
		for (int j = i; j < copy.length; j++) {
			if (copy[j] > 0) {
				copy[j] = book[j] - 1;
			}
		}
		Arrays.sort(copy);
		return copy;
	}

	public static double dpSort(int[] book) {
		String key = getKey(book);
		Double value = Book.bookmap.get(key);
		if (value != null) {
			return value;
		}
		double[] arr = new double[5];
		for (int i = 0; i < book.length; i++) {
			if (book[i] > 0) {
				int[] copy = dealBookArr(book, i);
				arr[i] = (5 - i) * 8 * Book.perference[i] + dpSort(copy);

			}
		}
		Arrays.sort(arr);
		value = arr[4];
		Book.bookmap.put(key, value);
		return value;
	}

	public static String getKey(int... args) {
		StringBuilder sb = new StringBuilder();
		for (int i : args) {
			sb.append(i + "-");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		int[] book = { 1, 2, 1, 2, 2 };
		double a = dpSort(book);
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(df.format(a));
	}

}