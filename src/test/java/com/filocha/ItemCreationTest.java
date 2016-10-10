package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.filocha.itemCreation.ItemCreationService;
import com.filocha.itemCreation.ItemCreationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItemCreationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	@Value("${local.server.port}")
	private String port;

	@Test
	public void shouldCreateFolder() {
		String folderName = "temp";
		ItemCreationService service = new ItemCreationServiceImpl();

		boolean result = service.createItem(folderName);

		assertThat(result, equalTo(true));
	}

	@Test
	public void shouldReceiveHttpGet() throws IOException {
		String folderToCreate = "12345";
		restTemplate.getForObject("http://localhost:" + port + "/addItem?name=" + folderToCreate, String.class);

		String home = System.getProperty("user.home");
		String root = home + "/walker/";
		File temp = new File(root + folderToCreate);

		assertThat(temp.exists(), equalTo(true));

		FileUtils.deleteDirectory(temp);

	}

}
