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
			if (currentLeafs.isEmpty() && currentBranches.isEmpty()) {
				return false;
			}
			if (currentLeafs.isEmpty() && !currentBranches.isEmpty()) {
				Branch branch = currentBranches.get(0);
				if (branch.getChildBranches().isEmpty()) {
					getSubBranchLeafs(branch);
					currentBranches.remove(branch);
				} else {
					getSubBranchLeafs(branch);
				}
			}
			return true;
		}

		@Override
		public Leaf next() {
			// TODO change for queue
			next = currentLeafs.get(0);
			currentLeafs.remove(0);

			return this.next;
		}

		private void getSubBranchLeafs(Branch branch) {
			List<Leaf> leafs = branch.getChildLeafs();
			for (Iterator<Leaf> iterator = leafs.iterator(); iterator.hasNext();) {
				Leaf leaf = iterator.next();
				addLeafsFromSubBranches(leaf);
				iterator.remove();
			}
		}

		private void addLeafsFromSubBranches(Leaf leaf) {
			currentLeafs.add(leaf);
		}
	}
}
