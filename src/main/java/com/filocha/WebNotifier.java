package com.filocha;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import rx.Observable;
import rx.functions.Action1;

@RestController
public class WebNotifier {

	@Autowired
	private MessageSendingOperations<String> messagingTemplate;

	@PostConstruct
	public void notifyWithFolderTreeStructureChanges() {
		boolean recursive = true;
		Path dir = Paths.get("C:\\Temp\\");
		WatchDir watcher = new WatchDir(dir, recursive);

		watcher.createObservable().subscribe(o -> {
			messagingTemplate.convertAndSend("/topic/greetings",
					o.toFile().getName());
		});
	}

	@MessageMapping("/hello")
	public void notifyWithFullFolderStructure() {
		FileBranchAdapter root = new FileBranchAdapter(new File("c:/Temp/"));

		TreeUtil util = new TreeUtil();
		Iterable<Leaf> items = util.convert(root);

		Observable<Leaf> observable = Observable.from(items);

		Action1<Leaf> observer1 = s -> {
			messagingTemplate.convertAndSend("/topic/greetings", s.getName());
		};

		observable.subscribe(observer1);
	}
}
