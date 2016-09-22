package com.filocha;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FileBranchAdapter implements Branch {
	private final File adapted;

	public FileBranchAdapter(File adapted) {
		this.adapted = adapted;
	}

	@Override
	public List<Branch> getChildBranches() {
		return Stream.of(adapted.listFiles()).filter(it -> it.isDirectory())
				.map(it -> new FileBranchAdapter(it))
				.collect(Collectors.toList());
	}

	@Override
	public List<Leaf> getChildLeafs() {
		return Stream.of(adapted.listFiles()).filter(it -> it.isFile())
				.map(it -> new FileLeafAdapter(it))
				.collect(Collectors.toList());
	}

}
