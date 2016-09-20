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
			next = currentLeafs.get(0);
			return true;
		}

		@Override
		public Leaf next() {
			return this.next;
		}
	}
}
