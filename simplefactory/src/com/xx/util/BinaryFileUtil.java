package com.xx.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class BinaryFileUtil {

	public static <T> List<T> readObjects(Class<T> c, String fileClasspath) {
		InputStream is = TextFileUtil.class.getResourceAsStream(fileClasspath);
		if (is == null) {
			return Collections.emptyList();
		}
		List<T> list = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(is)) {
			while (true) {
				T o = c.cast(ois.readObject());
				if (o == null) {
					break;
				}
				list.add(o);
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> void writeObjects(Collection<T> list, String fileClasspath) {
		int lastSlashIdx = fileClasspath.lastIndexOf("/");
		String classpath = fileClasspath.substring(0, lastSlashIdx + 1);
		String filename = fileClasspath.substring(lastSlashIdx + 1);
		URL url = TextFileUtil.class.getResource(classpath);
		if (url == null) {
			throw new RuntimeException("classpath is not exists:" + classpath);
		}
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(url.getPath() + filename))) {
			for (Object o : list) {
				os.writeObject(o);
			}
			os.writeObject(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
