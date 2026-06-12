package com.research.assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class AssistantApplication {

	public static void main(String[] args) {
		Path currentDir = Paths.get("").toAbsolutePath();
		Path envPath = currentDir.resolve(".env");

		if (!Files.exists(envPath)) {
			Path moduleEnvPath = currentDir.resolve("assistant").resolve(".env");
			if (Files.exists(moduleEnvPath)) {
				envPath = moduleEnvPath;
			} else if (currentDir.getParent() != null) {
				Path parentEnvPath = currentDir.getParent().resolve(".env");
				if (Files.exists(parentEnvPath)) {
					envPath = parentEnvPath;
				}
			}
		}

		Dotenv.configure()
				.ignoreIfMalformed()
				.ignoreIfMissing()
				.directory(envPath.getParent().toString())
				.filename(envPath.getFileName().toString())
				.systemProperties()
				.load();

		SpringApplication.run(AssistantApplication.class, args);
	}
}