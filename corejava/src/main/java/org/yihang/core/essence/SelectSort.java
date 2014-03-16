package org.yihang.core.essence;

import java.util.Comparator;

/**
 * 选择排序实现
 * 
 * 它的工作原理如下。
 * <ol>
 * <li>首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，</li>
 * <li>然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。</li>
 * <li>以此类推，直到所有元素均排序完毕。</li>
 * </ol>
 * 
 * @author yihang
 * 
 */
public class SelectSort implements Sort {

	@Override
	public <T> void sort(T[] a, Comparator<? super T> comparator) {
		for (int i = 0; i < a.length - 1; i++) {
			T first = a[i];
			for (int j = i + 1; j < a.length; j++) {
				if (comparator.compare(first, a[j]) > 0) {
					T t = a[i];
					a[i] = a[j];
					a[j] = t;
				}
			}
		}

	}

}
