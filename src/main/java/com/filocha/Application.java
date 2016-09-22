package com.filocha;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Application {

	public static final List<String> dirs = new ArrayList<>();

	public static void main(String[] args) {
		File file = new File("C:/Temp");

		File[] f = file.listFiles();

		for (File file2 : f) {
			System.out.println(file2.getName());
		}

	}

	public List<String> getDirStructure() {
		List<String> dirs = new ArrayList<>();
		try {
			Path startPath = Paths.get("c:/Temp");
			Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir,
						BasicFileAttributes attrs) {
					dirs.add(dir.toString());
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dirs;
	}
}
