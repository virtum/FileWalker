package com.filocha.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Iterator;

class CalendarIterator implements Iterator<LocalDate> {

	private LocalDate current = LocalDate.now();

	public CalendarIterator(LocalDate current) {
		this.current = current;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public LocalDate next() {
		current = current.plusDays(1);
		while (current.getDayOfWeek() != DayOfWeek.TUESDAY && current.getDayOfWeek() != DayOfWeek.FRIDAY) {
			current = current.plusDays(1);
		}
		return current;
	}

}
