package com.filocha.dirWatcher;

import java.util.ArrayList;
import java.util.Collections;
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

		@Override
		public Leaf next() {
			next = currentLeafs.poll();

			if (currentLeafs.isEmpty()) {
				findSubLeasfAndAddToList();
			}
			return next;
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
						currentBranches.addAll(0, branch.getChildBranches());
						while (true) {
							branch = currentBranches.poll();

							List<Branch> branches = branch.getChildBranches();
							List<Leaf> subLeafs = branch.getChildLeafs();
							if (!subLeafs.isEmpty()) {
								currentLeafs.addAll(subLeafs);
								currentBranches.addAll(0, branches);
								break;
							}
							if (subLeafs.isEmpty()) {
								currentBranches.addAll(0, branches);
							}
							if (currentBranches.isEmpty()) {
								break;
							}
						}
						//
						// List<Leaf> subLeafs =
						// getLeafsFromSubBranches(branch);
						// if (subLeafs.isEmpty() && !currentBranches.isEmpty())
						// {
						// boolean notFound = true;
						// while (notFound) {
						// branch = currentBranches.poll();
						// subLeafs = getLeafsFromSubBranches(branch);
						//
						// if (!subLeafs.isEmpty()) {
						// notFound = false;
						// }
						// }
						// }
						// currentLeafs.addAll(subLeafs);
					}
				}
			}
		}

		private List<Leaf> getLeafsFromSubBranches(Branch branch) {
			List<Leaf> result = new ArrayList<>();
			result = branch.getChildLeafs();

			if (!result.isEmpty()) {
				return result;
			}

			currentBranches.addAll(0, branch.getChildBranches());
			Branch subBranch = currentBranches.poll();

			if (subBranch == null) {
				return Collections.<Leaf>emptyList();
			}
			if (subBranch.getChildLeafs().isEmpty()) {
				if (subBranch.getChildBranches().isEmpty()) {
					return Collections.<Leaf>emptyList();
				}
			}

			if (subBranch.getChildLeafs().isEmpty()) {
				return getLeafsFromSubBranches(subBranch.getChildBranches().get(0));
			}

			result.addAll(subBranch.getChildLeafs());
			subBranch.getChildLeafs().clear();
			return result;
		}

	}
}