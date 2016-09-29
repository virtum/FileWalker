package com.filocha;

import java.util.ArrayList;
import java.util.List;

import com.filocha.dirWatcher.Branch;
import com.filocha.dirWatcher.Leaf;

public class BranchImpl implements Branch {

	private List<Branch> childBranches = new ArrayList<>();
	private List<Leaf> childLeafs = new ArrayList<>();

	public void addChildBranch(Branch branch) {
		childBranches.add(branch);
	}

	public void addChildLeaf(Leaf leaf) {
		childLeafs.add(leaf);
	}

	@Override
	public List<Branch> getChildBranches() {
		return childBranches;
	}

	@Override
	public List<Leaf> getChildLeafs() {
		return childLeafs;
	}

}