package com.syhd.dynamicProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test7 {

	/**
	 * @param args
	 */
	public double total = 0.00;
	boolean b = false;
	double result = 10000000000000000.00;

	public static void main(String[] args) {
		List<Integer> bookNums = Arrays.asList(new Integer[] { 1, 2, 1, 2, 2 });

		Test7 t = new Test7();
		t.sortList(bookNums);
		double d = t.getsum(bookNums);
		System.out.println("d" + d);
	}

	public List<Integer> sortList(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i; j < list.size() - 1; j++) {
				Integer tempj = list.get(j + 1);
				Integer tempi = list.get(i);
				if (tempi < tempj) {
					int temp = tempj;
					list.set(j + 1, tempi);
					list.set(i, temp);
				}

			}
		}
		return list;
	}

	public double getsum(List<Integer> list) {
		if (isAllOne(list)) {
			double sum = 0.00;
			double res = 0.00;
			for (int i = 0; i < list.size(); i++) {
				sum += list.get(i);
			}
			if (sum == 1) {
				res = sum * 1.00 * 8.00;
			} else if (sum == 2) {
				res = sum * 0.95 * 8.00;
			} else if (sum == 3) {
				res = sum * 0.90 * 8.00;
			} else if (sum == 4) {
				res = sum * 0.80 * 8.00;
			} else if (sum == 5) {
				res = sum * 0.75 * 8.00;
			}
			return res;
		} else {
			// 数据处理,取最小
			this.sortList(list);

			List<Double> minList = new ArrayList<Double>();

			if (list.get(4) >= 1) {
				List<Integer> tempList = new ArrayList<Integer>();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) > 1) {
						tempList.add(list.get(i) - 1);
					} else {
						tempList.add(0);
					}

				}
				this.sortList(tempList);
				double d = 5.00 * 8.00 * 0.75 + getsum(tempList);
				// if ( d < result) {
				// result = d;
				// System.out.println("1+"+result);
				// }
				minList.add(d);

			}

			if (list.get(3) >= 1) {
				List<Integer> tempList = new ArrayList<Integer>();
				for (int i = 0; i < list.size() - 1; i++) {
					if (list.get(i) > 1) {
						tempList.add(list.get(i) - 1);
					} else {
						tempList.add(0);
					}
				}
				tempList.add(list.get(4));
				this.sortList(tempList);
				double d = 4.00 * 8.00 * 0.80 + getsum(tempList);
				// if (d < result) {
				// result = d;
				// System.out.println("2+"+result);
				// }
				minList.add(d);
			}

			if (list.get(2) >= 1) {
				List<Integer> tempList = new ArrayList<Integer>();
				for (int i = 0; i < list.size() - 2; i++) {
					if (list.get(i) > 1) {
						tempList.add(list.get(i) - 1);
					} else {
						tempList.add(0);
					}
				}
				tempList.add(list.get(4));
				tempList.add(list.get(3));
				this.sortList(tempList);
				double d = 3.00 * 8.00 * 0.9 + getsum(tempList);
				// if (d < result) {
				// result = d;
				// System.out.println("3+"+result);
				// }
				minList.add(d);
			}

			if (list.get(1) >= 1) {
				List<Integer> tempList = new ArrayList<Integer>();
				for (int i = 0; i < list.size() - 3; i++) {
					if (list.get(i) > 1) {
						tempList.add(list.get(i) - 1);
					} else {
						tempList.add(0);
					}
				}
				tempList.add(list.get(4));
				tempList.add(list.get(3));
				tempList.add(list.get(2));

				this.sortList(tempList);
				double d = 2.00 * 8.00 * 0.95 + getsum(tempList);
				// if (d < result) {
				// result = d;
				// System.out.println("4+"+result);
				// }
				minList.add(d);
			}

			if (list.get(0) >= 1) {
				List<Integer> tempList = new ArrayList<Integer>();
				for (int i = 0; i < list.size() - 4; i++) {
					if (list.get(i) > 1) {
						tempList.add(list.get(i) - 1);
					} else {
						tempList.add(0);
					}
				}
				tempList.add(list.get(4));
				tempList.add(list.get(3));
				tempList.add(list.get(2));
				tempList.add(list.get(1));

				this.sortList(tempList);
				double d = 1.00 * 8.00 * 1.00 + getsum(tempList);
				// if (d < result) {
				// result = d;
				// System.out.println("5+"+result);
				// }
				minList.add(d);
			}
			result = this.getMin(minList);
			return result;
		}
	}

	public boolean isAllOne(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > 1) {
				return false;
			}
		}
		return true;
	}

	public void clearList(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == 0) {
				list.remove(i);
				i--;
			}
		}
	}

	public double getMin(List<Double> list) {
		double d = 100000000.00;
		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i));
			if (list.get(i) < d) {
				d = list.get(i);
			}
		}
		return d;
	}

}