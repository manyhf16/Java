package com.xx.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.tribes.util.Arrays;
import org.junit.Test;

import com.xx.util.TextFileUtil;

public class TestTextFileUtil {

	@Test
	public void test1() {
		List<String[]> users = TextFileUtil.readLinesAndSplitWith("/users.data");
		for (String[] line : users) {
			System.out.println(Arrays.toString(line));
		}
	}

	@Test
	public void test2() {
		List<String[]> lines = new ArrayList<>();
		lines.add(new String[] { "1", "张三" });
		lines.add(new String[] { "2", "李四" });
		lines.add(new String[] { "3", "王五" });
		lines.add(new String[] { "4", "赵六" });
		TextFileUtil.writeLines(lines, "/users.data");
	}

}
