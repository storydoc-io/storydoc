package io.storydoc.stitch;

import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.UUID;

public class StitchEngineTest  {

    Logger logger = LoggerFactory.getLogger(StitchEngineTest.class);

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Test
    public void test_engine() {

        StitchConfig config = StitchConfig.builder()
                .rootPath(tmpFolder.getRoot().toPath())
                .build();
        StitchEngine stitchEngine = new StitchEngine(config);

        ScenarioTracer scenarioTracer = new ScenarioTracer(stitchEngine);
        CodeExecutionTracer executionTracer = new CodeExecutionTracer(stitchEngine);

        for(int idxTest=0; idxTest<5; idxTest++){
            String testCaseName = aTestCaseName();
            scenarioTracer.beginTestCase(testCaseName);

            scenarioTracer.bdd("given", "a given");

            String cid = aCorrelationId();
            String typeName = aTypeName();
            String functionName = aFunctionName();
            executionTracer.methodEnter(cid, typeName, functionName);
            executionTracer.methodReturn(cid, typeName, functionName);

            scenarioTracer.endTestCase(testCaseName);
        }

        logRootFolder();
        logSummary(stitchEngine);
    }

    private void logSummary(StitchEngine stitchEngine) {
        logFileContent("summary.txt: ", stitchEngine.getSummaryPath());
    }

    private String aTestCaseName() {
        return "test-case-" + UUID.randomUUID();
    }

    private String aCorrelationId() {
        return UUID.randomUUID().toString();
    }

    private String aTypeName() {
        return "type-" + UUID.randomUUID().toString();
    }

    private String aFunctionName() {
        return "method-" + UUID.randomUUID().toString();
    }

    private void logRootFolder() {
        recursiveLogFolderStructure(tmpFolder.getRoot(), "");
    }

    private void recursiveLogFolderStructure(File parent, String indent) {
        if (indent.equals("")) {
            logger.info("<root>");
        } else {
            logger.info(indent + "d " + parent.getName());
        }
        for(String fn : parent.list()) {
            File dirElem = new File(parent, fn);
            if (dirElem.isDirectory()) {
                recursiveLogFolderStructure(dirElem, "  " + indent);
            } else {
                logger.info(indent + "- " + dirElem.getName());
            }
        }
    }

    @SneakyThrows
    private void logFileContent(String msg, Path path) {
        FileInputStream fileInputStream = new FileInputStream(path.toFile());
        logger.info(msg);
        logger.info(new String(fileInputStream.readAllBytes()));
    }

}