package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import rx.subjects.ReplaySubject;

public class WatchDirTest {

	@Test
	public void shouldReceiveChangesInFolders() {
		boolean recursive = true;
		Path dir = Paths.get("C:\\Temp\\");
		WatchDir watcher = new WatchDir(dir, recursive);

		ReplaySubject<Path> results = ReplaySubject.create();
		watcher.createObservable().subscribe(results);

		File file = new File("C:\\Temp\\xxx");
		file.mkdirs();

		Path result = results.toBlocking().first();

		assertThat(file.exists(), equalTo(true));
		assertThat(result.toFile().getName(), equalTo(file.getName()));

		file.delete();
	}
}
