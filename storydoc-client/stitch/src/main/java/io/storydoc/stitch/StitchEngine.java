package io.storydoc.stitch;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

public class StitchEngine {

    private TestCaseEventStream testCaseEventStream;

    private SummaryEventStream summaryEventStream;

    private final StitchConfig config;

    public StitchEngine() {
        this(StitchConfig.DEFAULT);
    }

    public StitchEngine(StitchConfig config) {
        this.config = config;
        initRootPath();
        initSummary();
    }

    @SneakyThrows
    private void initRootPath() {
        Files.createDirectories(config.getRootPath());
    }

    private void initSummary() {
        summaryEventStream = new SummaryEventStream(getSummaryPath());
    }

    Path getSummaryPath() {
        return config.getRootPath().resolve("summary.txt");
    }

    @SneakyThrows
    public void beginTestCase(String ... nameFragments) {
        summaryEventStream.addEntry(nameFragments);
        Path path = getPath(nameFragments);
        Files.createDirectories(path.getParent());
        testCaseEventStream = new TestCaseEventStream(path);
    }

    private Path getPath(String[] fragments) {
        Path parentPath  = config.getRootPath();
        int lastFragmentIdx = fragments.length - 1;
        for (int idx = 0; idx < lastFragmentIdx; idx++) {
            String subFolderName = fragments[idx];
            parentPath = parentPath.resolve(subFolderName);
        }
        String fileName = fragments[lastFragmentIdx] + ".txt";
        Path path = parentPath.resolve(fileName);
        return path;
    }

    public void add(String modelName, String eventName, String jsonValue) {
        testCaseEventStream.add(String.format("%s|%s|%s", modelName, eventName, jsonValue));
    }

    public void endTestCase() {
        testCaseEventStream.close();
        testCaseEventStream = null;
    }
}
