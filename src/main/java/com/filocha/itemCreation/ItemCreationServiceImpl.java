package com.filocha.itemCreation;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class ItemCreationServiceImpl implements ItemCreationService {

	@Override
	public boolean createItem(String name) {
		String home = System.getProperty("java.io.tmpdir");
		String root = home + "/walker/";
		File temp = new File(root);
		temp.mkdir();

		return createIfFolderNotExist(root, name);
	}

	public boolean createIfFolderNotExist(String root, String name) {
		String[] folders = name.split("/");
		String newRoot = root;

		for (String string : folders) {
			File file = new File(newRoot + "/" + string);
			if (!string.contains(".")) {
				file.mkdirs();
				newRoot += "/" + string;
			} else {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
}
