package zpark.test.job;

import java.util.Arrays;
import java.util.Comparator;

public class TestSort {
	
	public static void main(String[] args) {
		String[] array = new String[]{"5", "4","7","6",	"2",	"8",	"1"};
		sort(array, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		System.out.println(Arrays.toString(array));
	}
	
	public static <T> void sort(T[] a, Comparator<? super T> comparator) {
		quicksort(a, 0, a.length - 1, comparator);
	}

	private static <T> void quicksort(T[] a, final int bi, final int ei,
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
