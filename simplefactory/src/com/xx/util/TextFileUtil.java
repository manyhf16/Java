package com.xx.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TextFileUtil {

	public static List<String[]> readLinesAndSplitWith(String fileClasspath) {
		return readLinesAndSplitWith(fileClasspath, "\t", "utf-8");
	}

	public static List<String[]> readLinesAndSplitWith(String fileClasspath, String separator, String encoding) {
		InputStream is = TextFileUtil.class.getResourceAsStream(fileClasspath);
		if (is == null) {
			return Collections.emptyList();
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding))) {
			String line = null;
			List<String[]> list = new ArrayList<>();
			while (true) {
				line = br.readLine();
				if (line == null) {
					break;
				}
				list.add(line.split(separator));
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeLines(List<String[]> lines, String fileClasspath) {
		int lastSlashIdx = fileClasspath.lastIndexOf("/");
		String classpath = fileClasspath.substring(0, lastSlashIdx + 1);
		String filename = fileClasspath.substring(lastSlashIdx + 1);
		URL url = TextFileUtil.class.getResource(classpath);
		if (url == null) {
			throw new RuntimeException("classpath is not exists:" + classpath);
		}
		try (PrintWriter pw = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream(url.getPath() + filename), "utf-8"))) {
			for (String[] line : lines) {
				for (String w : line) {
					pw.print(w);
					pw.print("\t");
				}
				pw.println();
			}
			pw.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
