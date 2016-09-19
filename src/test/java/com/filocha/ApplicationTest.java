package com.filocha;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ApplicationTest {

	@Test
	public void shouldTest() {
		int result = 1 + 1;

		assertThat(result, equalTo(3));
	}

}
