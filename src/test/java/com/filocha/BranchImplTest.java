package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.filocha.dirWatcher.Leaf;
import com.filocha.dirWatcher.TreeUtil;
import com.google.common.collect.Lists;

public class BranchImplTest {

	@Test
	public void shouldReturnEmptyIterator() {
		// given
		BranchImpl branch = new BranchImpl("branch");

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
		BranchImpl branch = new BranchImpl("branch");
		Leaf leaf = new LeafImpl("leaf");
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
		BranchImpl branch = new BranchImpl("branch");
		Leaf leaf = new LeafImpl("leaf");
		branch.addChildLeaf(leaf);

		Leaf leaf1 = new LeafImpl("leaf1");
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
		BranchImpl root = new BranchImpl("root");
		Leaf leaf = new LeafImpl("leaf");
		root.addChildLeaf(leaf);

		Leaf leaf1 = new LeafImpl("leaf1");
		root.addChildLeaf(leaf1);

		BranchImpl subBranch = new BranchImpl("subBranch");
		Leaf leaf2 = new LeafImpl("leaf2");
		Leaf leaf3 = new LeafImpl("leaf3");
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
		BranchImpl root = new BranchImpl("root");
		Leaf leaf = new LeafImpl("leaf");
		root.addChildLeaf(leaf);

		Leaf leaf1 = new LeafImpl("leaf1");
		root.addChildLeaf(leaf1);

		BranchImpl subBranch = new BranchImpl("subBranch");
		Leaf leaf2 = new LeafImpl("leaf2");
		subBranch.addChildLeaf(leaf2);

		Leaf leaf3 = new LeafImpl("leaf3");
		subBranch.addChildLeaf(leaf3);

		root.addChildBranch(subBranch);

		BranchImpl subBranch1 = new BranchImpl("subBranch1");
		Leaf leaf4 = new LeafImpl("leaf4");
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
		BranchImpl root = new BranchImpl("root");
		BranchImpl subBranch1 = new BranchImpl("subBranch1");
		BranchImpl subBranch2 = new BranchImpl("subBranch2");
		BranchImpl subBranch3 = new BranchImpl("subBranch3");
		Leaf leaf = new LeafImpl("leaf");

		root.addChildBranch(subBranch1);
		root.addChildBranch(subBranch3);
		subBranch1.addChildBranch(subBranch2);
		subBranch2.addChildLeaf(leaf);

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
		BranchImpl root = new BranchImpl("root");
		Leaf leaf = new LeafImpl("leaf");
		root.addChildLeaf(leaf);

		BranchImpl subBranch1 = new BranchImpl("subBranch1");
		root.addChildBranch(subBranch1);

		Leaf leaf1 = new LeafImpl("leaf1");
		subBranch1.addChildLeaf(leaf1);

		BranchImpl subBranch2 = new BranchImpl("subBranch2");
		subBranch1.addChildBranch(subBranch2);

		Leaf leaf2 = new LeafImpl("leaf2");
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
		BranchImpl root = new BranchImpl("root");
		Leaf leaf = new LeafImpl("leaf");
		root.addChildLeaf(leaf);

		BranchImpl subBranch1 = new BranchImpl("subBranch1");
		root.addChildBranch(subBranch1);

		Leaf leaf1 = new LeafImpl("leaf1");
		subBranch1.addChildLeaf(leaf1);

		BranchImpl subBranch2 = new BranchImpl("subBranch2");
		subBranch1.addChildBranch(subBranch2);

		Leaf leaf2 = new LeafImpl("leaf2");
		subBranch2.addChildLeaf(leaf2);

		BranchImpl subBranch3 = new BranchImpl("subBranch3");
		root.addChildBranch(subBranch3);

		Leaf leaf3 = new LeafImpl("leaf3");
		subBranch3.addChildLeaf(leaf3);

		BranchImpl subBranch4 = new BranchImpl("subBranch4");
		subBranch3.addChildBranch(subBranch4);

		Leaf leaf4 = new LeafImpl("leaf4");
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
		BranchImpl root = new BranchImpl("root");

		BranchImpl subBranch1 = new BranchImpl("subBranch1");
		root.addChildBranch(subBranch1);

		BranchImpl subBranch2 = new BranchImpl("subBranch2");
		subBranch1.addChildBranch(subBranch2);

		BranchImpl subBranch3 = new BranchImpl("subBranch3");
		subBranch2.addChildBranch(subBranch3);

		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		assertThat(leafs.isEmpty(), equalTo(true));
	}

	@Test
	public void shouldLeafFromSubBranchIfBranchDoNotHaveLeafs() {
		BranchImpl root = new BranchImpl("root");

		BranchImpl subBranch1 = new BranchImpl("subBranch1");
		root.addChildBranch(subBranch1);

		BranchImpl subBranch2 = new BranchImpl("subBranch2");
		subBranch1.addChildBranch(subBranch2);

		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		assertThat(leafs.isEmpty(), equalTo(true));
	}

	@Test
	public void shouldFindFilesInSubBranches() {
		BranchImpl root = new BranchImpl("root");

		BranchImpl subBranch1 = new BranchImpl("subBranch1");
		root.addChildBranch(subBranch1);

		Leaf leaf11 = new LeafImpl("leaf11");
		subBranch1.addChildLeaf(leaf11);

		BranchImpl subBranch11 = new BranchImpl("subBranch11");
		subBranch1.addChildBranch(subBranch11);

		Leaf leaf111 = new LeafImpl("leaf111");
		subBranch11.addChildLeaf(leaf111);

		BranchImpl subBranch111 = new BranchImpl("subBranch111");
		subBranch11.addChildBranch(subBranch111);

		Leaf leaf1111 = new LeafImpl("leaf1111");
		subBranch111.addChildLeaf(leaf1111);

		BranchImpl subBranch2 = new BranchImpl("subBranch2");
		root.addChildBranch(subBranch2);

		Leaf leaf2 = new LeafImpl("leaf2");
		subBranch2.addChildLeaf(leaf2);

		// when
		TreeUtil util = new TreeUtil();
		Iterable<Leaf> result = util.convert(root);
		Iterator<Leaf> iterator = result.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs.size(), equalTo(4));
	}

	@Test
	public void shouldFindLeafInSecondBranch() {
		// given
		BranchImpl root = new BranchImpl("root");

		BranchImpl subBranch0 = new BranchImpl("subBranch0");
		root.addChildBranch(subBranch0);

		BranchImpl subBranch1 = new BranchImpl("subBranch1");
		root.addChildBranch(subBranch1);

		BranchImpl subBranch11 = new BranchImpl("subBranch11");
		subBranch1.addChildBranch(subBranch11);

		Leaf leaf111 = new LeafImpl("leaf111");
		subBranch11.addChildLeaf(leaf111);

		// when
		TreeUtil tree = new TreeUtil();
		Iterable<Leaf> iterable = tree.convert(root);
		Iterator<Leaf> iterator = iterable.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf111));

	}

	@Test
	public void nextShouldWorkWithoutHasnext() {

		// given
		BranchImpl root = new BranchImpl("root");
		BranchImpl branchOne = new BranchImpl("branchOne");
		BranchImpl branchTwo = new BranchImpl("branchtwo");
		root.addChildBranch(branchOne);
		branchOne.addChildBranch(branchTwo);
		Leaf leafOne = new LeafImpl("leafOne");
		branchTwo.addChildLeaf(leafOne);

		// when
		Iterable<Leaf> leafIterable = new TreeUtil().convert(root);
		Iterator<Leaf> leafIterator = leafIterable.iterator();

		// then
		assertThat(leafIterator.next(), equalTo(leafOne));

	}

	@Test
	public void shoudlFindLeafInAnotherCombination() {
		BranchImpl root = new BranchImpl("root");

		BranchImpl branch1 = new BranchImpl("branch1");
		root.addChildBranch(branch1);

		BranchImpl branch2 = new BranchImpl("branch2");
		root.addChildBranch(branch2);

		LeafImpl leaf = new LeafImpl("leaf");
		branch2.addChildLeaf(leaf);

		// when
		TreeUtil tree = new TreeUtil();
		Iterable<Leaf> iterable = tree.convert(root);
		Iterator<Leaf> iterator = iterable.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf));
	}

	@Test
	public void shoudlFindLeafInAnotherCombination2() {
		BranchImpl root = new BranchImpl("root");
		BranchImpl branch1 = new BranchImpl("branch1");
		BranchImpl branch2 = new BranchImpl("branch2");
		BranchImpl branch3 = new BranchImpl("branch3");
		BranchImpl branch4 = new BranchImpl("branch4");
		LeafImpl leaf = new LeafImpl("leaf");
		LeafImpl leaf1 = new LeafImpl("leaf");

		root.addChildBranch(branch1);
		root.addChildBranch(branch2);
		branch1.addChildBranch(branch3);
		branch1.addChildBranch(branch4);
		branch2.addChildLeaf(leaf);
		branch4.addChildLeaf(leaf1);

		// when
		TreeUtil tree = new TreeUtil();
		Iterable<Leaf> iterable = tree.convert(root);
		Iterator<Leaf> iterator = iterable.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf, leaf1));
	}

	@Test
	public void shoudlFindLeafInAnotherCombination3() {
		BranchImpl root = new BranchImpl("root");

		BranchImpl branch1 = new BranchImpl("branch1");
		root.addChildBranch(branch1);

		BranchImpl branch2 = new BranchImpl("branch2");
		root.addChildBranch(branch2);

		LeafImpl leaf = new LeafImpl("leaf");
		branch2.addChildLeaf(leaf);

		BranchImpl branch3 = new BranchImpl("branch3");
		branch1.addChildBranch(branch3);

		BranchImpl branch4 = new BranchImpl("branch4");
		branch1.addChildBranch(branch4);

		LeafImpl leaf1 = new LeafImpl("leaf1");
		branch4.addChildLeaf(leaf1);

		BranchImpl branch5 = new BranchImpl("branch5");
		branch4.addChildBranch(branch5);

		LeafImpl leaf2 = new LeafImpl("leaf2");
		branch5.addChildLeaf(leaf2);

		// when
		TreeUtil tree = new TreeUtil();
		Iterable<Leaf> iterable = tree.convert(root);
		Iterator<Leaf> iterator = iterable.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf, leaf1, leaf2));
	}

	@Test
	public void shoudlFindLeafInAnotherCombination4() {
		// given
		BranchImpl root = new BranchImpl("root");
		BranchImpl branch1 = new BranchImpl("branch1");
		BranchImpl branch2 = new BranchImpl("branch2");
		BranchImpl branch3 = new BranchImpl("branch3");
		BranchImpl branch4 = new BranchImpl("branch4");
		LeafImpl leaf1 = new LeafImpl("leaf1");
		LeafImpl leaf2 = new LeafImpl("leaf2");

		root.addChildBranch(branch1);
		root.addChildBranch(branch2);
		branch2.addChildBranch(branch3);
		branch2.addChildLeaf(leaf1);
		branch3.addChildBranch(branch4);
		branch4.addChildLeaf(leaf2);

		// when
		TreeUtil tree = new TreeUtil();
		Iterable<Leaf> iterable = tree.convert(root);
		Iterator<Leaf> iterator = iterable.iterator();
		List<Leaf> leafs = Lists.newArrayList(iterator);

		// then
		assertThat(leafs, containsInAnyOrder(leaf1, leaf2));
	}

	@Test(expected = NoSuchElementException.class)
	public void shoudlThrowExceptionWhenThereIsNoElementLeft() {
		// given
		BranchImpl root = new BranchImpl("root");

		// when
		TreeUtil tree = new TreeUtil();
		Iterable<Leaf> iterable = tree.convert(root);
		Iterator<Leaf> iterator = iterable.iterator();

		// then
		assertThat(iterator.next(), equalTo(null));
	}

}