package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class TreeNodeTest {

	@Test
	public void shouldCheckFirstElement() {
		Iterator<TreeNode<String>> iterator = getSet1().iterator();

		if (iterator.hasNext()) {
			TreeNode<String> result = iterator.next();
			System.out.println(iterator.next());
			assertThat(result.data, equalTo("root"));
		}

		StreamUtils.asStream(iterator).forEach(System.out::println);

	}

	public List<String> getDirStructure() {
		List<String> dirs = new ArrayList<>();
		try {
			Path startPath = Paths.get("c:/Temp");
			Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					dirs.add(dir.toString());
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dirs;
	}

	public static TreeNode<String> getSet1() {
		TreeNode<String> root = new TreeNode<String>("root");
		{
			TreeNode<String> node0 = root.addChild("node0");
			TreeNode<String> node1 = root.addChild("node1");
			TreeNode<String> node2 = root.addChild("node2");
			{
				TreeNode<String> node20 = node2.addChild("node20");
				TreeNode<String> node21 = node2.addChild("node21");
				{
					TreeNode<String> node210 = node21.addChild("node210");
					TreeNode<String> node211 = node21.addChild("node211");
				}
			}
			TreeNode<String> node3 = root.addChild("node3");
			{
				TreeNode<String> node30 = node3.addChild("node30");
			}
		}

		return root;
	}
}
