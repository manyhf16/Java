package org.yihang.core.essence;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

public class TestSort {

	@Test
	public void testQuick() {
		Integer[] array = { 9, 10, 15, 14, 13, 8 };
		QuickSort sort = new QuickSort();
		sort.sort(array, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		Assert.assertArrayEquals(new Integer[] { 8, 9, 10, 13, 14, 15 }, array);
	}
	
	@Test
	public void testSelect() {
		Integer[] array = { 9, 10, 15, 14, 13, 8 };
		SelectSort sort = new SelectSort();
		sort.sort(array, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		Assert.assertArrayEquals(new Integer[] { 8, 9, 10, 13, 14, 15 }, array);
	}
	
	@Test
	public void testBubble() {
		Integer[] array = { 9, 10, 15, 14, 13, 8 };
		BubbleSort sort = new BubbleSort();
		sort.sort(array, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		Assert.assertArrayEquals(new Integer[] { 8, 9, 10, 13, 14, 15 }, array);
	}

}
