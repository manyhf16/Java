package zpark.ext.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class PackageUtil {

    private static String getDecodePath(String path) {
        try {
            return URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return path;
        }
    }

    public static void scanPackage(ClassfileVisitor classfileVisitor, String... packageNames) {
        try {
            for (String packageName : packageNames) {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                String packagePath = packageName.replace('.', '/');
                Enumeration<URL> resources = cl.getResources(packagePath);
                while (resources.hasMoreElements()) {
                    URL url = resources.nextElement();
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        doFileProcess(getDecodePath(url.getPath()), packageName, classfileVisitor);
                    } else if (protocol.equals("jar")) {
                        doJarProcess(getDecodePath(url.getPath()), classfileVisitor);
                    } else {
                        throw new RuntimeException("Not support yet");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doJarProcess(String filepath, ClassfileVisitor classfileVisitor) throws IOException {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(filepath.replaceAll("file:/(.*)!.*", "$1"));
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry e = entries.nextElement();
                if (!e.isDirectory() && e.getName().endsWith(".class")) {
                    String className = e.getName().replace("/", ".").replaceAll("\\.class", "");
                    classfileVisitor.visit(className);
                }
            }
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void doFileProcess(String filepath, String packageName, ClassfileVisitor classfileVisitor)
            throws IOException {
        File file = new File(filepath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String path = getDecodePath(f.getPath());
                if (f.isDirectory()) {
                    String subName = packageName + "." + path.substring(path.lastIndexOf(File.separator) + 1);
                    doFileProcess(path, subName, classfileVisitor);
                } else {
                    String className = packageName + "." + f.getName().replaceAll("\\.class", "");
                    classfileVisitor.visit(className);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        PackageUtil.scanPackage(new Container(), "zpark.ext.test");
        System.out.println(Container.getManagedClassMap());
    }
}
