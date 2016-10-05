package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import com.filocha.itemCreation.ItemCreationService;
import com.filocha.itemCreation.ItemCreationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItemCreationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldCreateFolder() {
		String folderName = "temp";
		ItemCreationService service = new ItemCreationServiceImpl();

		boolean result = service.createItem(folderName);

		assertThat(result, equalTo(true));
	}

	@Ignore
	@Test
	public void shouldReceiveHttpGet() throws RestClientException,
			InterruptedException, ExecutionException {
		String result1 = restTemplate
				.getForEntity("http://localhost:8080/temp", String.class)
				.getBody();

		String result = restTemplate.getForObject("http://localhost:8080/temp",
				String.class);

		System.out.println(result);
		System.out.println(result1);
	}

}
