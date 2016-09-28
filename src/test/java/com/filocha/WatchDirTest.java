package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.Test;

import rx.subjects.ReplaySubject;

public class WatchDirTest {

	@Test
	public void shouldReceiveChangesInFolders() {
		String home = System.getProperty("user.home") + "\\Temp\\";
		String randomName = UUID.randomUUID().toString().substring(28);
		String root = home + randomName + "\\";

		boolean recursive = true;
		Path dir = Paths.get(home);
		WatchDir watcher = new WatchDir(dir, recursive);

		ReplaySubject<Path> results = ReplaySubject.create();
		watcher.createObservable().subscribe(results);

		File file = new File(root);
		file.mkdirs();

		Path result = results.toBlocking().first();

		assertThat(file.exists(), equalTo(true));
		assertThat(result.toFile().getName(), equalTo(file.getName()));

		file.delete();
	}
}
