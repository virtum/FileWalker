package com.filocha;

import java.io.File;

public class FileLeafAdapter implements Leaf {
	private final File adapted;

	public FileLeafAdapter(File adapted) {
		this.adapted = adapted;
	}

	@Override
	public String getName() {
		return adapted.getName();
	}
}
