package org.yihang.core.essence;

import java.util.Comparator;

/**
 * 快速排序实现 步骤为：
 * <ol>
 * <li>从数列中挑出一个元素，称为 "基准"（pivot），</li>
 * <li>重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，
 * 该基准就处于数列的中间位置。这个称为分区（partition）操作。</li>
 * <li>递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。</li>
 * </ol>
 * 
 * @author yihang
 * 
 */
public class QuickSort implements Sort {

	@Override
	public <T> void sort(T[] a, Comparator<? super T> comparator) {
		quicksort(a, 0, a.length - 1, comparator);
	}

	private <T> void quicksort(T[] a, final int bi, final int ei,
			Comparator<? super T> comparator) {
		if (bi < ei) {
			T x = a[bi];
			int i = bi;
			int j = ei;
			while (i < j) {
				// 有没有比x小的，没有向左找
				while (i < j && comparator.compare(a[j], x) >= 0) {
					j--;
				}
				// 填左边坑
				if (i < j) {
					a[i] = a[j];
					i++;
				}
				// 有没有比x大的，没有向右找
				while (i < j && comparator.compare(a[i], x) < 0) {
					i++;
				}
				// 填右边坑
				if (i < j) {
					a[j] = a[i];
					j--;
				}
			}
			a[i] = x;
			quicksort(a, bi, i - 1, comparator);
			quicksort(a, i + 1, ei, comparator);
		}
	}

}
