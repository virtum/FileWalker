package com.filocha.dirWatcher;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class TreeUtil {
	public Iterable<Leaf> convert(Branch root) {
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
		private LinkedList<Leaf> currentLeafs;
		private LinkedList<Branch> currentBranches;

		public LeafIterator(Branch branch) {
			this.currentLeafs = new LinkedList<>(branch.getChildLeafs());
			this.currentBranches = new LinkedList<>(branch.getChildBranches());
			findSubLeafsAndAddToList();
		}

		@Override
		public boolean hasNext() {
			return !currentLeafs.isEmpty();
		}

		@Override
		public Leaf next() {
			next = currentLeafs.poll();

			if (currentLeafs.isEmpty()) {
				findSubLeafsAndAddToList();
			}
			if (next == null) {
				throw new NoSuchElementException();
			}
			return next;
		}

		public void findSubLeafsAndAddToList() {
			while (true) {
				if (!currentLeafs.isEmpty()) {
					return;
				}

				if (currentBranches.isEmpty()) {
					return;
				}

				Branch branch = currentBranches.poll();
				currentLeafs.addAll(branch.getChildLeafs());
				currentBranches.addAll(0, branch.getChildBranches());
			}
		}
	}
}
