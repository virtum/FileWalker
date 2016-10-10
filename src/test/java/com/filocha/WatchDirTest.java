package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;

import com.filocha.dirWatcher.WatchDir;

import rx.subjects.ReplaySubject;

public class WatchDirTest {

	@Test
	public void shouldReceiveChangesInFolders() throws IOException {
		String home = System.getProperty("java.io.tmpdir");
		File temp = new File(home + "/Temp");
		temp.mkdir();

		String randomName = UUID.randomUUID().toString().substring(28);
		String root = temp + "/" + randomName + "/";

		boolean recursive = true;
		Path dir = Paths.get(temp.getAbsolutePath());
		WatchDir watcher = new WatchDir(dir, recursive);

		ReplaySubject<Path> results = ReplaySubject.create();
		watcher.createObservable().subscribe(results);

		File file = new File(root);
		file.mkdirs();

		Path result = results.toBlocking().first();

		assertThat(file.exists(), equalTo(true));
		assertThat(result.toFile().getName(), equalTo(file.getName()));

		FileUtils.deleteDirectory(file);
	}
}
