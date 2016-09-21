package com.filocha;

import java.util.Iterator;
import java.util.List;

public class TreeUtil {
	public static Iterable<Leaf> convert(Branch root) {
		return new LeafIterable(root);
	}

	static class LeafIterable implements Iterable<Leaf> {

		private Branch root;

		public LeafIterable(Branch root) {
			this.root = root;
		}

		@Override
		public Iterator<Leaf> iterator() {
			return new LeafIterator(root);
		}

	}

	static class LeafIterator implements Iterator<Leaf> {

		private Leaf next;
		private List<Leaf> currentLeafs;
		private List<Branch> currentBranches;

		public LeafIterator(Branch branch) {
			this.currentLeafs = branch.getChildLeafs();
			this.currentBranches = branch.getChildBranches();
		}

		@Override
		public boolean hasNext() {
			if (currentLeafs.isEmpty()) {
				return false;
			}
			return true;
		}

		@Override
		public Leaf next() {
			if (!currentBranches.isEmpty()) {
				checkIfSubBranchHasBranch(currentBranches);
			}

			// TODO change for queue
			next = currentLeafs.get(0);
			currentLeafs.remove(0);
			return this.next;
		}

		private void checkIfSubBranchHasBranch(List<Branch> branches) {
			// int numberOfBranches = branches.size();

			for (Leaf leaf : branches.get(0).getChildLeafs()) {
				addLeafsFromSubBranches(leaf);
			}
			currentBranches.remove(0);

		}

		private void addLeafsFromSubBranches(Leaf leaf) {
			currentLeafs.add(leaf);
		}
	}
}
