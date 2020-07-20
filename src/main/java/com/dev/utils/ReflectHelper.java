package com.dev.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author Leethea_廖南洲
 * @version 1.0  反射 helper
 * @date 2020/4/9 9:49
 **/
public class ReflectHelper {

	/**
	 * @param clazz 接口
	 * @return 全部的实现类
	 **/
	public static List<Class> getClassByInterface(Class<?> clazz) {
		//判断是否是一个接口
		if (clazz.isInterface()) {
			try {
				return getAllClass(clazz.getPackage().getName())
						.stream()
						.filter(item-> clazz.isAssignableFrom(item) && (!clazz.equals(item)))
						.collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @param packageName 包名
	 * @return
	 */
	private static ArrayList<Class> getAllClass(String packageName) {
		ArrayList<Class> list = new ArrayList<>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');
		try {
			ArrayList<File> fileList = new ArrayList<>();
			Enumeration<URL> enumeration = classLoader.getResources(path);
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();
				String protocol = url.getProtocol();
				if ("jar".equalsIgnoreCase(protocol)) {
					JarURLConnection connection = (JarURLConnection) url.openConnection();
					if (connection != null) {
						JarFile jarFile = connection.getJarFile();
						list.addAll(findClass(jarFile, packageName));
						continue;
					}
				}
				fileList.add(new File(URLDecoder.decode(url.getFile(), "UTF-8")));
			}
			for (File file : fileList) {
				list.addAll(findClass(file, packageName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static ArrayList<Class> findClass(File file, String packageName) {
		ArrayList<Class> list = new ArrayList<>();
		if (!file.exists()) {
			return list;
		}
		File[] files = file.listFiles();
		for (File file2 : Objects.requireNonNull(files)) {
			if (file2.isDirectory()) {
				assert !file2.getName().contains(".");
				List<Class> arrayList = findClass(file2, packageName + "." + file2.getName());
				list.addAll(arrayList);
			} else if (file2.getName().endsWith(".class")) {
				try {
					//保存的类文件不需要后缀.class
					Class classes = Class.forName(packageName + '.' + StringUtils.substringBeforeLast(file2.getName(), "."));
					//不查询抽象类和接口
					if (Modifier.isAbstract(classes.getModifiers()) || classes.isInterface()) {
						continue;
					}
					list.add(classes);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	private static List<Class> findClass(JarFile file, String packageName) {
		List<Class> list = new ArrayList<>();
		Enumeration<JarEntry> jarEntryEnumeration = file.entries();
		while (jarEntryEnumeration.hasMoreElements()) {
			JarEntry entry = jarEntryEnumeration.nextElement();
			String jarEntryName = entry.getName();
			//这里我们需要过滤不是class文件和不在basePack包名下的类
			if (jarEntryName.contains(".class") && jarEntryName.replaceAll("/", ".").startsWith(packageName)) {
				String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
				try {
					Class classes = Class.forName(className);
					//不查询抽象类
					if (Modifier.isAbstract(classes.getModifiers())) {
						continue;
					}
					list.add(classes);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}


}
