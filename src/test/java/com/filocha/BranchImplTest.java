package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.filocha.dirWatcher.Leaf;
import com.filocha.dirWatcher.TreeUtil;
import com.google.common.collect.Lists;

public class BranchImplTest {
	@Test
	public void shouldReturnEmptyIterator() {
		// given
		BranchImpl branch = new BranchImpl();

		// when
		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(branch);
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
		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(branch);
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
		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(branch);
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
		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(root);
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
		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf, leaf1, leaf2, leaf3, leaf4));
	}

	@Test
	public void shouldFindLeafsInBranches() {
		// given
		BranchImpl root = new BranchImpl();
		BranchImpl subBranch1 = new BranchImpl();
		BranchImpl subBranch2 = new BranchImpl();
		BranchImpl subBranch3 = new BranchImpl();

		root.addChildBranch(subBranch1);
		subBranch1.addChildBranch(subBranch2);
		Leaf leaf = new LeafImpl();
		subBranch2.addChildLeaf(leaf);
		root.addChildBranch(subBranch3);

		// when
		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf));
	}

	@Test
	public void shouldFindLeafsInOneSubBranchesBranch() {
		BranchImpl root = new BranchImpl();
		Leaf leaf = new LeafImpl();
		root.addChildLeaf(leaf);

		BranchImpl subBranch1 = new BranchImpl();
		root.addChildBranch(subBranch1);

		Leaf leaf1 = new LeafImpl();
		subBranch1.addChildLeaf(leaf1);

		BranchImpl subBranch2 = new BranchImpl();
		subBranch1.addChildBranch(subBranch2);

		Leaf leaf2 = new LeafImpl();
		subBranch2.addChildLeaf(leaf2);

		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		assertThat(leafs, containsInAnyOrder(leaf, leaf1, leaf2));

	}

	@Test
	public void shouldFindLeafsInSubBranches() {
		// given
		BranchImpl root = new BranchImpl();
		Leaf leaf = new LeafImpl();
		root.addChildLeaf(leaf);

		BranchImpl subBranch1 = new BranchImpl();
		root.addChildBranch(subBranch1);

		Leaf leaf1 = new LeafImpl();
		subBranch1.addChildLeaf(leaf1);

		BranchImpl subBranch2 = new BranchImpl();
		subBranch1.addChildBranch(subBranch2);

		Leaf leaf2 = new LeafImpl();
		subBranch2.addChildLeaf(leaf2);

		BranchImpl subBranch3 = new BranchImpl();
		root.addChildBranch(subBranch3);

		Leaf leaf3 = new LeafImpl();
		subBranch3.addChildLeaf(leaf3);

		BranchImpl subBranch4 = new BranchImpl();
		subBranch3.addChildBranch(subBranch4);

		Leaf leaf4 = new LeafImpl();
		subBranch4.addChildLeaf(leaf4);

		// when
		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf, leaf1, leaf2, leaf3, leaf4));
	}

	@Test
	public void shouldReturnEmptyListIfSubBranchDoNotHaveLeafs() {
		BranchImpl root = new BranchImpl();

		BranchImpl subBranch1 = new BranchImpl();
		root.addChildBranch(subBranch1);

		BranchImpl subBranch2 = new BranchImpl();
		subBranch1.addChildBranch(subBranch2);

		BranchImpl subBranch3 = new BranchImpl();
		subBranch2.addChildBranch(subBranch3);

		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		assertThat(leafs.isEmpty(), equalTo(true));
	}

	@Test
	public void shouldLeafFromSubBranchIfBranchDoNotHaveLeafs() {
		BranchImpl root = new BranchImpl();

		BranchImpl subBranch1 = new BranchImpl();
		root.addChildBranch(subBranch1);

		BranchImpl subBranch2 = new BranchImpl();
		subBranch1.addChildBranch(subBranch2);

		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		assertThat(leafs.isEmpty(), equalTo(true));
	}

}