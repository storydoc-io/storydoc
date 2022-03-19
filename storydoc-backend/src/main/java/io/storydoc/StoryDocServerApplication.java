package io.storydoc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableAspectJAutoProxy
public class StoryDocServerApplication {

	@Value("${server.port}")
	long port;

	public static void main(String[] args) {

		SpringApplication.run(StoryDocServerApplication.class, args);


	}

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		System.out.println(String.format("Tell your story on http://localhost:%s",  port));
	}

}
