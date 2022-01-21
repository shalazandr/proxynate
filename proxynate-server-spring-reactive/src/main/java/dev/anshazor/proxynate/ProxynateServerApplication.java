package dev.anshazor.proxynate;

import dev.anshazor.proxynate.service.MappingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;

@SpringBootApplication
public class ProxynateServerApplication {

	@Autowired
	private MappingsService mappingsService;

	public static void main(String[] args) {
		SpringApplication.run(ProxynateServerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initMappings() throws IOException {
		mappingsService.initMappings();
	}

}
