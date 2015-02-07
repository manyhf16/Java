package org.yihang.core.essence;

import java.util.LinkedList;

import org.junit.Test;

public class TestHash {
    
    @Test
    public void test01() {
        LinkedList<Object>[] table = new LinkedList[16];
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<Object>();
        }
        for (int i = 0; i < 20; i++) {
            Object object = i + "";
            int index = (object.hashCode() % table.length);
            // System.out.println("i=" + i + " hashcode=" + (""+i).hashCode() +
            // " index=" + index);
            LinkedList<Object> list = table[index];
            list.add(object);
        }
        for (int i = 0; i < table.length; i++) {
            LinkedList<Object> list = table[i];
            // print1(i, list);
            print2(i, list);
        }
    }

	public static void print2(int i, LinkedList<Object> list) {
	    System.out.print("["+i+"]\t");
		for (Object j : list) {
			System.out.print("*");
		}
		System.out.println();
	}

	public static void print1(int i, LinkedList<Object> list) {
		System.out.println("list[" + i + "]:" + list);
	}

}
