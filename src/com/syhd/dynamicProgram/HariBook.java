package com.syhd.dynamicProgram;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HariBook {
	public static void main(String[] args) {
		List<Integer> bookNums = Arrays.asList(new Integer[] { 1, 2, 1, 2, 2 });
		cheapest(bookNums);
	}

	public static void cheapest(List<Integer> bookNums) {
		Collections.sort(bookNums, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});
		for (int i : bookNums) {
			System.out.println(i);
		}

	}
}
