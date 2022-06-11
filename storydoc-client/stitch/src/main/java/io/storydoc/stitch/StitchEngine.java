package io.storydoc.stitch;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

public class StitchEngine {

    private TestCaseEventStream eventStream;

    private final StitchConfig config;

    public StitchEngine() {
        this(StitchConfig.DEFAULT);
    }

    public StitchEngine(StitchConfig config) {
        this.config = config;
        initRootPath();
    }

    @SneakyThrows
    private void initRootPath() {
        Files.createDirectories(config.getRootPath());

    }

    public void beginTestCase(String testCaseName) {
        eventStream = new TestCaseEventStream(getPath(testCaseName));
    }

    private Path getPath(String testCaseName) {
        return config.getRootPath().resolve(testCaseName + ".txt");
    }

    public void add(String modelName, String eventName, String jsonValue) {
        eventStream.add(String.format("%s|%s|%s", modelName, eventName, jsonValue));
    }

    public void endTestCase() {
        eventStream.close();
        eventStream = null;
    }
}
