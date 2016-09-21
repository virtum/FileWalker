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

public class BranchImplTest {

	@Test
	public void shouldReturnEmptyIterator() {
		// given
		BranchImpl branch = new BranchImpl();

		// when
		Iterable<Leaf> result = TreeUtil.convert(branch);
		Iterator<Leaf> iterator = result.iterator();

		// then
		assertThat(iterator.hasNext(), equalTo(false));
	}

	@Test
	public void shouldReturnBranchWithOneLeaf() {
		// given
		BranchImpl branch = new BranchImpl();
		Leaf leaf = new LeafImpl();
		branch.addChildLeaf(leaf);

		// when
		Iterable<Leaf> result = TreeUtil.convert(branch);
		Iterator<Leaf> iterator = result.iterator();

		// then
		assertThat(iterator.hasNext(), equalTo(true));
		assertThat(iterator.next(), equalTo(leaf));
	}

	@Test
	public void shouldReturnBranchWithTwoLeafs() {
		// given
		BranchImpl branch = new BranchImpl();
		Leaf leaf = new LeafImpl();
		branch.addChildLeaf(leaf);

		Leaf leaf1 = new LeafImpl();
		branch.addChildLeaf(leaf1);

		// when
		Iterable<Leaf> result = TreeUtil.convert(branch);
		Iterator<Leaf> iterator = result.iterator();

		// then
		assertThat(iterator.hasNext(), equalTo(true));
		assertThat(iterator.next(), equalTo(leaf));
		assertThat(iterator.next(), equalTo(leaf1));
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

}
