package com.filocha.dirWatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
		}

		@Override
		public boolean hasNext() {
			if (currentLeafs.isEmpty() && currentBranches.isEmpty()) {
				return false;
			}
			if (!currentBranches.isEmpty()) {
				if (currentLeafs.isEmpty()) {
					Branch branch = currentBranches.poll();
					Queue<Leaf> leafs = new LinkedList<>(
							branch.getChildLeafs());

					if (!leafs.isEmpty()) {
						if (branch.getChildBranches().isEmpty()) {
							currentLeafs.addAll(leafs);
							currentBranches.remove(branch);
						} else {
							currentLeafs.addAll(leafs);
							leafs.clear();
							currentBranches.addAll(0,
									branch.getChildBranches());
						}
					} else {
						List<Leaf> subLeafs = getLeafsFromSubBranches(branch);
						if (subLeafs.isEmpty()) {
							return false;
						}
						currentLeafs.addAll(subLeafs);
					}
				}
			}
			return true;
		}

		private List<Leaf> getLeafsFromSubBranches(Branch branch) {
			List<Leaf> result = new ArrayList<>();

			List<Branch> branches = branch.getChildBranches();
			for (Branch subBranch : branches) {
				if (subBranch.getChildLeafs().isEmpty()) {
					if (subBranch.getChildBranches().isEmpty()) {
						return result;
					}
					getLeafsFromSubBranches(
							subBranch.getChildBranches().get(0));
				} else {
					result.addAll(subBranch.getChildLeafs());
					subBranch.getChildLeafs().clear();
					return result;
				}
			}
			return result;
		}

		@Override
		public Leaf next() {
			next = currentLeafs.poll();

			return this.next;
		}
	}
}