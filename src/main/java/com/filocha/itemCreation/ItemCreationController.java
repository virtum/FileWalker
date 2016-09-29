package com.filocha.itemCreation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemCreationController {

	@Autowired
	private ItemCreationService service;

	@RequestMapping(value = "createItem", method = RequestMethod.POST)
	public boolean getItemName(String name) {
		return service.createItem(name);
	}

}
