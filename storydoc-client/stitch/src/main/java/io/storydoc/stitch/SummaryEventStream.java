package io.storydoc.stitch;

import lombok.SneakyThrows;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;

public class SummaryEventStream {

    PrintWriter printWriter;

    @SneakyThrows
    public SummaryEventStream(Path summaryPath) {
        FileOutputStream fileOutputStream = new FileOutputStream(summaryPath.toFile());
        printWriter = new PrintWriter(fileOutputStream);

    }

    public void add(String line) {
        printWriter.println(line);
    }

    public void close() {
        printWriter.close();
    }

    public void addEntry(String[] nameFragments) {
        JSONWriter json = new JSONWriter();
        json.array();
        for (String nameFragment: nameFragments) {
            json.string(nameFragment);
        }
        json.end();
        printWriter.println(json.getValue());
        printWriter.flush();
    }

}
