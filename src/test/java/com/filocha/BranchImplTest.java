package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

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

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

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

		List<Leaf> leafs = Lists.newArrayList(iterator);
		assertThat(leafs, containsInAnyOrder(leaf, leaf1));
	}

	@Ignore
	@Test
	public void shouldReturnBranchWithThreeLeafsOneInAnotherBranch() {
		// given
		BranchImpl root = new BranchImpl();
		Leaf leaf = new LeafImpl();
		root.addChildLeaf(leaf);

		Leaf leaf1 = new LeafImpl();
		root.addChildLeaf(leaf1);

		BranchImpl subBranch = new BranchImpl();
		Leaf leaf2 = new LeafImpl();
		subBranch.addChildLeaf(leaf2);
		root.addChildBranch(subBranch);

		// when
		Iterable<Leaf> result = TreeUtil.convert(root);
		Iterator<Leaf> iterator = result.iterator();

		// then
		assertThat(iterator.hasNext(), equalTo(true));
		assertThat(iterator.next(), equalTo(leaf));
		assertThat(iterator.next(), equalTo(leaf1));
		assertThat(iterator.next(), equalTo(leaf2));
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
