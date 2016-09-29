package com.filocha.dirWatcher;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.Subscriber;

public class WatchDir {
	private WatchService watcher;
	private Map<WatchKey, Path> keys;
	private boolean recursive;
	private boolean trace = false;
	List<Subscriber<? super Path>> observers = new ArrayList<>();

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	/**
	 * Register the given directory with the WatchService
	 */
	private void register(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE,
				ENTRY_MODIFY);
		if (trace) {
			Path prev = keys.get(key);
			if (prev == null) {
			} else {
				if (!dir.equals(prev)) {
				}
			}
		}
		keys.put(key, dir);
	}

	/**
	 * Register the given directory, and all its sub-directories, with the
	 * WatchService.
	 */
	private void registerAll(final Path start) throws IOException {
		// register directory and sub-directories
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				register(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * Creates a WatchService and registers the given directory
	 */
	public WatchDir(Path dir, boolean recursive) {
		try {
			this.watcher = FileSystems.getDefault().newWatchService();
			this.keys = new HashMap<WatchKey, Path>();
			this.recursive = recursive;

			registerAll(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// enable trace after initial registration
		this.trace = true;
		initialize();
	}

	public Observable<Path> createObservable() {
		Observable<Path> myObservable = Observable.create(sub -> {
			observers.add(sub);
		});
		return myObservable;
	}

	public void initialize() {
		Executors.newSingleThreadExecutor().submit(() -> {
			while (true) {
				Path path = watch();

				for (Subscriber<? super Path> item : observers) {
					item.onNext(path);
				}
			}
		});
	}

	/**
	 * Process all events for keys queued to the watcher
	 */
	private Path watch() {
		Path child = null;
		// wait for key to be signalled
		WatchKey key = null;
		try {
			key = watcher.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Path dir = keys.get(key);

		for (WatchEvent<?> event : key.pollEvents()) {
			WatchEvent.Kind kind = event.kind();

			// Context for directory entry event is the file name of entry
			WatchEvent<Path> ev = cast(event);
			Path name = ev.context();
			child = dir.resolve(name);

			// if directory is created, and watching recursively, then
			// register it and its sub-directories
			if (recursive && (kind == ENTRY_CREATE)) {
				try {
					if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
						registerAll(child);
					}
				} catch (IOException x) {
					// ignore to keep sample readbale
				}
			}
		}

		// reset key and remove from set if directory no longer accessible
		boolean valid = key.reset();
		if (!valid) {
			keys.remove(key);

			// all directories are inaccessible
			if (keys.isEmpty()) {
			}
		}
		return child;
	}
}
