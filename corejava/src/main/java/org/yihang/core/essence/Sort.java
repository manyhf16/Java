package org.yihang.core.essence;

import java.util.Comparator;

/**
 * 展示基本的排序算法
 * 
 * @author yihang
 * @see QuickSort 
 * @see SelectSort 
 * @see BubbleSort
 */
@FunctionalInterface
public interface Sort {

	public abstract <T> void sort(T[] a, Comparator<? super T> comparator);

}