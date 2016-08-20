package org.yihang.core.essence;

import java.util.LinkedList;

import org.junit.Test;

public class TestHash {
	int size = 0; 								// map实际大小
	LinkedList<Object>[] table = simuNew(4); 	// 初始化map
	boolean rehashing = false; 					// 是否发生了rehash

	@Test
	public void test01() {
		for (int i = 0; i < 8; i++) {
			Object object = i + "";
			simuPut(table, object);
			size++;
		}
	}

	// 模拟创建新的 HashMap
	@SuppressWarnings("unchecked")
	private LinkedList<Object>[] simuNew(int cap) {
		LinkedList<Object>[] table = new LinkedList[cap];
		for (int i = 0; i < table.length; i++) {
			table[i] = new LinkedList<Object>();
		}
		return table;
	}

	// 打印整个HashMap内容
	private void printResult() {
		for (int i = 0; i < table.length; i++) {
			LinkedList<Object> list = table[i];
			System.out.print("[" + i + "]\t");
			for (int j = 0; j < list.size(); j++) {
				System.out.print("(" + list.get(j) + ") -> ");
				if (j == list.size() - 1) {
					System.out.print("null");
				}
			}
			System.out.println();
		}
	}

	// 模拟HashMap.put
	private void simuPut(LinkedList<Object>[] table, Object object) {
		int index = (hash(object) & table.length - 1);
		LinkedList<Object> list = table[index];
		if (size < table.length * 0.75) {
			list.addFirst(object);
			if (!rehashing) {
				System.out.println("=========== hash ===========");
				printResult();
			}
		} else {
			System.out.println("=========== rehash ===========");
			LinkedList<Object>[] nTable = simuNew(table.length * 2);
			simuRehash(nTable, object);
		}

	}

	// 模拟HashMap.rehash
	private void simuRehash(LinkedList<Object>[] nTable, Object object) {
		rehashing = true;
		for (int i = 0; i < table.length; i++) {
			LinkedList<Object> list = table[i];
			for (int j = 0; j < list.size(); j++) {
				simuPut(nTable, list.get(j));
			}
		}
		simuPut(nTable, object);
		table = nTable;
		printResult();
		rehashing = false;
	}

	private final int hash(Object k) {
		int h = 0;
		h ^= k.hashCode();
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

}
