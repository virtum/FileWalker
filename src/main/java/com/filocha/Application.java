package com.filocha;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		// startSingleObserver();
		SpringApplication.run(Application.class, args);
	}

	// public static void startSingleObserver() throws IOException {
	// List<Subscriber<? super Path>> observers = new ArrayList<>();
	//
	// Observable<Path> myObservable = Observable.create(sub -> {
	// observers.add(sub);
	// });
	//
	// Action1<Path> observer1 = s -> {
	// System.out.println("Observer1 received: " + s.getFileName());
	// };
	//
	// Action1<Path> observer2 = s -> {
	// System.out.printl65n("Observer2 received: " + s.getFileName());
	// };
	//
	// myObservable.observeOn(Schedulers.computation()).subscribe(observer1);
	// myObservable.subscribe(observer2);
	//
	// boolean recursive = true;
	// Path dir = Paths.get("C:\\Temp\\");
	// WatchDir watcher = new WatchDir(dir, recursive);
	//
	// Executors.newSingleThreadExecutor().submit(() -> {
	// while (true) {
	// // Path child = watcher.watch();
	// //
	// // for (Subscriber<? super Path> subscriber : observers) {
	// // subscriber.onNext(child);
	// // }
	// }
	// });
	// }
}
