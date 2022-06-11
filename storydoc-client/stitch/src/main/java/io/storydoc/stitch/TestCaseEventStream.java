package io.storydoc.stitch;

import lombok.SneakyThrows;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;

public class TestCaseEventStream {

    private PrintWriter printWriter;

    @SneakyThrows
    public TestCaseEventStream(Path path) {
        FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
        printWriter = new PrintWriter(fileOutputStream);
    }

    public void add(String line) {
        printWriter.println(line);
    }

    public void close() {
        printWriter.close();
    }
}
