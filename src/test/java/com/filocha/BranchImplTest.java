package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

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
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf, leaf1));
	}

	@Test
	public void shouldReturnBranchWithFourLeafsOneInAnotherBranch() {
		// given
		BranchImpl root = new BranchImpl();
		Leaf leaf = new LeafImpl();
		root.addChildLeaf(leaf);

		Leaf leaf1 = new LeafImpl();
		root.addChildLeaf(leaf1);

		BranchImpl subBranch = new BranchImpl();
		Leaf leaf2 = new LeafImpl();
		Leaf leaf3 = new LeafImpl();
		subBranch.addChildLeaf(leaf2);
		subBranch.addChildLeaf(leaf3);
		root.addChildBranch(subBranch);

		// when
		Iterable<Leaf> result = TreeUtil.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf, leaf1, leaf2, leaf3));

	}

	@Test
	public void shouldReturnBranchWithFiveLeafsFromTwoSubBranches() {
		// given
		BranchImpl root = new BranchImpl();
		Leaf leaf = new LeafImpl();
		root.addChildLeaf(leaf);

		Leaf leaf1 = new LeafImpl();
		root.addChildLeaf(leaf1);

		BranchImpl subBranch = new BranchImpl();
		Leaf leaf2 = new LeafImpl();
		subBranch.addChildLeaf(leaf2);

		Leaf leaf3 = new LeafImpl();
		subBranch.addChildLeaf(leaf3);

		root.addChildBranch(subBranch);

		BranchImpl subBranch1 = new BranchImpl();
		Leaf leaf4 = new LeafImpl();
		subBranch1.addChildLeaf(leaf4);

		root.addChildBranch(subBranch1);

		// when
		Iterable<Leaf> result = TreeUtil.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf, leaf1, leaf2, leaf3, leaf4));
	}
}
