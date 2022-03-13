package io.storydoc.server.code.domain;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SourceCodeAccess {

    private String sourceRoot = "../samples/spring-petclinic/src/main/java";

    @SneakyThrows
    public List<String> getSource(String className) {
        List<String> result;
        try (Stream<String> lines = Files.lines(getFilePath(className))) {
            return lines.collect(Collectors.toList());
        }
    }

    private Path getFilePath(String className) {
        return Paths.get(sourceRoot + File.separator + className.replace(".", File.separator) + ".java").toAbsolutePath();
    }

}
