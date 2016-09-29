package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.filocha.itemCreation.ItemCreationService;
import com.filocha.itemCreation.ItemCreationServiceImpl;

public class ItemCreationTest {

	@Test
	public void shouldCreateFolder() {
		String folderName = "temp";
		ItemCreationService service = new ItemCreationServiceImpl();

		boolean result = service.createItem(folderName);

		assertThat(result, equalTo(true));
	}

}
