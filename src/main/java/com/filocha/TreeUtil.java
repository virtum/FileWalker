package com.filocha;

import java.util.ArrayList;
import java.util.Collections;
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
				List<Leaf> leafs = branch.getChildLeafs();

				if (!leafs.isEmpty()) {
					if (branch.getChildBranches().isEmpty()) {
						currentLeafs.addAll(leafs);
						currentBranches.remove(branch);
					} else {
						currentLeafs.addAll(leafs);
						leafs.clear();
					}
				} else {
					List<Leaf> subLeafs = getLeafsFromSubBranches(branch);
					if (subLeafs.isEmpty()) {
						return false;
					}
					currentLeafs.addAll(subLeafs);
				}
			}
			return true;
		}

		private List<Leaf> getLeafsFromSubBranches(Branch branch) {
			if (!currentLeafs.isEmpty()) {
				return Collections.emptyList();
			}

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
			// TODO change for queue
			next = currentLeafs.get(0);
			currentLeafs.remove(0);

			return this.next;
		}
	}
}