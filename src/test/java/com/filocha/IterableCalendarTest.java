package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.Iterator;

import org.junit.Test;

public class IterableCalendarTest {

	@Test
	public void shouldCorrectlyFindTuesdaysAndFridays() {
		LocalDate date = LocalDate.of(2016, 9, 19);
		IterableCalendar calendar = new IterableCalendar(date);

		Iterator<LocalDate> iterator = calendar.iterator();
		LocalDate result = iterator.next();

		LocalDate expected = LocalDate.of(2016, 9, 20);
		assertThat(result, equalTo(expected));

		result = iterator.next();
		expected = LocalDate.of(2016, 9, 23);
		assertThat(result, equalTo(expected));

		result = iterator.next();
		expected = LocalDate.of(2016, 9, 27);
		assertThat(result, equalTo(expected));

		result = iterator.next();
		expected = LocalDate.of(2016, 9, 30);
		assertThat(result, equalTo(expected));

	}

	@Test
	public void shouldCreateIndependentIterators() {
		LocalDate date = LocalDate.of(2016, 9, 19);
		IterableCalendar calendar = new IterableCalendar(date);

		Iterator<LocalDate> iterator = calendar.iterator();
		Iterator<LocalDate> iterator1 = calendar.iterator();

		iterator1.next();
		LocalDate result = iterator.next();

		LocalDate expected = LocalDate.of(2016, 9, 20);
		assertThat(result, equalTo(expected));

	}

	@Test
	public void shouldConvertIterableToStream() {
		LocalDate date = LocalDate.of(2016, 9, 19);
		IterableCalendar calendar = new IterableCalendar(date);

		Iterator<LocalDate> iterator = calendar.iterator();
		StreamUtils.asStream(iterator).limit(30).forEach(System.out::println);
	}
}
