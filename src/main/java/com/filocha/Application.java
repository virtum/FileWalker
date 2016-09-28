// package com.filocha;
//
// import java.io.IOException;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.concurrent.Executors;
//
// import org.springframework.boot.autoconfigure.SpringBootApplication;
//
// import rx.Observable;
// import rx.Subscriber;
// import rx.functions.Action1;
// import rx.schedulers.Schedulers;
//
// @SpringBootApplication
// public class Application {
//
// public static void main(String[] args) throws IOException {
// startSingleObserver();
// // SpringApplication.run(Application.class, args);
//
// // FileBranchAdapter root = new FileBranchAdapter(new File("c:/Temp/"));
// //
// // TreeUtil util = new TreeUtil();
// // Iterable<Leaf> items = util.convert(root);
// // // Iterator<Leaf> iterator = items.iterator();
// // //
// // // StreamUtils utils = new StreamUtils();
// // // Stream<Leaf> stream = utils.asStream(iterator);
// //
// // Observable<Leaf> observable = Observable.from(items);
// //
// // Action1<Leaf> observer1 = s -> {
// // System.out.println("Observer1 received: " + s.getName());
// // };
// //
// // observable.subscribe(observer1);
// //
// // while (true) {
// //
// // }
// }
//
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
// System.out.println("Observer2 received: " + s.getFileName());
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
// }
