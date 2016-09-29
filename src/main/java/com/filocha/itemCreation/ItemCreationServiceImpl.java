package com.filocha.itemCreation;

import java.io.File;

public class ItemCreationServiceImpl implements ItemCreationService {

	@Override
	public boolean createItem(String name) {
		System.out.println("item name: " + name);
		String home = System.getProperty("user.home");
		File temp = new File(home + "/walker");
		temp.mkdir();

		String root = temp + "/" + name + "/";
		File file = new File(root);

		// TODO check if name represents file or folder
		file.mkdirs();

		if (file.exists()) {
			return true;
		}
		return false;
	}
}
