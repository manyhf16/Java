package org.yihang.core.essence;

import org.junit.Assert;
import org.junit.Test;

public class TestSort {

	@Test
	public void testQuick() {
		Integer[] array = { 9, 10, 15, 14, 13, 8 };
		QuickSort sort = new QuickSort();
		final StringBuilder sb = new StringBuilder();
		sort.sort(array, (a, b) -> {sb.append("_"); return a.compareTo(b);});
		System.out.println(sb.toString().length());
		Assert.assertArrayEquals(new Integer[] { 8, 9, 10, 13, 14, 15 }, array);
	}

	@Test
	public void testSelect() {
		Integer[] array = { 9, 10, 15, 14, 13, 8 };
		SelectSort sort = new SelectSort();
		final StringBuilder sb = new StringBuilder();
		sort.sort(array, (a, b) -> {sb.append("_"); return a.compareTo(b);});
		System.out.println(sb.toString().length());
		Assert.assertArrayEquals(new Integer[] { 8, 9, 10, 13, 14, 15 }, array);
	}

	@Test
	public void testBubble() {
		Integer[] array = { 9, 10, 15, 14, 13, 8 };
		BubbleSort sort = new BubbleSort();
		final StringBuilder sb = new StringBuilder();
		sort.sort(array, (a, b) -> {sb.append("_"); return a.compareTo(b);});
		System.out.println(sb.toString().length());
		Assert.assertArrayEquals(new Integer[] { 8, 9, 10, 13, 14, 15 }, array);
	}
	
	@Test
	public void testMerge() {
		Integer[] array = { 9, 10, 15, 14, 13, 8 };
		MergeSort sort = new MergeSort();
		final StringBuilder sb = new StringBuilder();
		sort.sort(array, (a, b) -> {sb.append("_"); return a.compareTo(b);});
		System.out.println(sb.toString().length());
		Assert.assertArrayEquals(new Integer[] { 8, 9, 10, 13, 14, 15 }, array);
	}

}
