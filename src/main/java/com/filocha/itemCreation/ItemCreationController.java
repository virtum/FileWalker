package com.filocha.itemCreation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemCreationController {

	@Autowired
	private ItemCreationService service;

	@CrossOrigin
	@RequestMapping(value = "addItem", method = RequestMethod.GET)
	public boolean getItemName(
			@RequestParam(required = false, value = "name") String name) {
		return service.createItem(name);
	}

	@RequestMapping(value = "temp", method = RequestMethod.GET)
	public String getTemp() {
		return "Hi";
	}
}
