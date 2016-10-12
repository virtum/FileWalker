package com.filocha;

import com.filocha.dirWatcher.Leaf;

public class LeafImpl implements Leaf {

	private String name;

	public LeafImpl(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
