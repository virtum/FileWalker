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
			findSubLeasfAndAddToList();
		}

		@Override
		public boolean hasNext() {
			return !currentLeafs.isEmpty();
		}

		public void findSubLeasfAndAddToList() {
			if (currentLeafs.isEmpty()) {
				if (!currentBranches.isEmpty()) {
					Branch branch = currentBranches.poll();
					Queue<Leaf> leafs = new LinkedList<>(branch.getChildLeafs());

					if (!leafs.isEmpty()) {
						if (branch.getChildBranches().isEmpty()) {
							currentLeafs.addAll(leafs);
							currentBranches.remove(branch);
						} else {
							currentLeafs.addAll(leafs);
							leafs.clear();
							currentBranches.addAll(0, branch.getChildBranches());
						}
					} else {
						List<Leaf> subLeafs = getLeafsFromSubBranches(branch);
						if (subLeafs.isEmpty() && !currentBranches.isEmpty()) {
							boolean notFound = true;
							while (notFound) {
								branch = currentBranches.poll();
								subLeafs = getLeafsFromSubBranches(branch);

								if (!subLeafs.isEmpty()) {
									notFound = false;
								}
							}
						}
						currentLeafs.addAll(subLeafs);
					}
				}
			}
		}

		private List<Leaf> getLeafsFromSubBranches(Branch branch) {
			List<Leaf> result = new ArrayList<>();

			List<Branch> branches = branch.getChildBranches();
			for (Branch subBranch : branches) {
				if (subBranch.getChildLeafs().isEmpty()) {
					if (subBranch.getChildBranches().isEmpty()) {
						return result;
					}
					getLeafsFromSubBranches(subBranch.getChildBranches().get(0));
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

			if (currentLeafs.isEmpty()) {
				findSubLeasfAndAddToList();
			}
			return next;
		}
	}
}