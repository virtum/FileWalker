package com.filocha;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
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
}
