package com.filocha;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Application {

	public static final List<String> dirs = new ArrayList<>();

	public static void main(String[] args) {

		FileBranchAdapter root = new FileBranchAdapter(new File("c:/Temp/"));

		Iterable<Leaf> items = TreeUtil.convert(root);
		Iterator<Leaf> iterator = items.iterator();

		StreamUtils.asStream(iterator)
				.forEach(it -> System.out.println(it.getName()));

	}

}
