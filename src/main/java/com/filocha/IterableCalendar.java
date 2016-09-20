package com.filocha;

import java.time.LocalDate;
import java.util.Iterator;

public class IterableCalendar implements Iterable<LocalDate> {
	private LocalDate current = LocalDate.now();

	public IterableCalendar(LocalDate start) {
		this.current = start;
	}

	@Override
	public Iterator<LocalDate> iterator() {
		return new CalendarIterator(current);
	}
}
