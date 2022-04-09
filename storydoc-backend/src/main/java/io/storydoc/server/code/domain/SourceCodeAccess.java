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


    @SneakyThrows
    public List<String> getSource(String className, List<String> sourceRoots) {


        List<String> result;
        try (Stream<String> lines = Files.lines(getFilePath(sourceRoots, className))) {
            return lines.collect(Collectors.toList());
        }
    }


    private Path getFilePath(List<String> sourceRoots, String className) {
        for (String sourceRoot : sourceRoots) {
            Path path = Paths.get(sourceRoot + File.separator + className.replace(".", File.separator) + ".java").toAbsolutePath();
            if (Files.exists(path)) {
                return path;
            }
        }
        throw new IllegalArgumentException("no source for " + className + " in " + sourceRoots);
    }

}
