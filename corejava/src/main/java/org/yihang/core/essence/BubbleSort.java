package org.yihang.core.essence;

import java.util.Comparator;

/**
 * 冒泡排序实现 冒泡排序算法的运作如下：
 * <ol>
 * <li>比较相邻的元素。如果第一个比第二个大，就交换他们两个。</li>
 * <li>对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。</li>
 * <li>针对所有的元素重复以上的步骤，除了最后一个。</li>
 * <li>持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。</li>
 * </ol>
 * 
 * @author yihang
 * 
 */
public class BubbleSort implements Sort {

	@Override
	public <T> void sort(T[] a, Comparator<? super T> comparator) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = 0; j < a.length - 1 - i; j++) {
				if (comparator.compare(a[j], a[j + 1]) > 0) {
					T t = a[j];
					a[j] = a[j + 1];
					a[j + 1] = t;
				}
			}
		}

	}

}
