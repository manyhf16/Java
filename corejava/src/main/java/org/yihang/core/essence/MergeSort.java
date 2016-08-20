package org.yihang.core.essence;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergeSort implements Sort {

	private static final Logger logger = LoggerFactory.getLogger(MergeSort.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> void sort(T[] a, Comparator<? super T> comparator) {
		split(a, 0, a.length - 1, (T[]) new Object[a.length], comparator);
	}

	private <T> void merge(T[] a, int bi, int mi, int ei, T[] r, Comparator<? super T> comparator) {
		int i = bi;
		int j = mi + 1;
		int k = 0;
		while (i <= mi && j <= ei) {
			if (comparator.compare(a[i], a[j]) < 0) {
				r[k++] = a[i++];
			} else {
				r[k++] = a[j++];
			}
		}
		while (i <= mi)
			r[k++] = a[i++];

		while (j <= ei)
			r[k++] = a[j++];

		for (i = 0; i < k; i++)
			a[bi + i] = r[i];
		
		if (logger.isTraceEnabled()) {
			printSorted(a, bi, mi, ei);
		}
	}

	private <T> void printSorted(T[] a, int bi, int mi, int ei) {
		StringBuilder sb = new StringBuilder(128);
		sb.append("begin:" + bi + " middle:" + mi + " end:" + ei + " ");
		sb.append("sorted: [");
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + (i != a.length - 1 ? " " : ""));
		}
		sb.append("]");
		logger.trace(sb.toString());
	}

	private <T> void split(T[] a, int bi, int ei, T[] r, Comparator<? super T> comparator) {
		int mi = (bi + ei) / 2;
		if (bi < ei) {
			if (logger.isTraceEnabled()) {
				printArray(a, bi, mi, ei);
			}
			split(a, bi, mi, r, comparator); 		// 左边继续拆分，归并
			split(a, mi + 1, ei, r, comparator); 	// 右边继续拆分，归并
			merge(a, bi, mi, ei, r, comparator); 	// 本次结果归并
		}
	}

	private <T> void printArray(T[] a, int bi, int mi, int ei) {
		StringBuilder sb = new StringBuilder(256);
		sb.append("begin:" + bi + " middle:" + mi + " end:" + ei + " array:");
		sb.append("[");
		for (int i = bi; i <= ei; i++) {
			sb.append(a[i] + (i != ei ? " " : ""));
		}
		sb.append("]");
		logger.trace(sb.toString());
	}

	public static void main(String[] args) {
		MergeSort sort = new MergeSort();
		Integer[] array = new Integer[] { 8, 6, 4, 3, 9, 2, 5, 7, 1 };
		sort.sort(array, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
	}

}
