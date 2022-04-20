package io.storydoc.stitch;

import org.junit.Test;

import java.util.UUID;

public class StitchEngineTest  {

    @Test
    public void test_engine() {

        StitchConfig config = new StitchConfig();
        StitchEngine stitchEngine = new StitchEngine(config);

        ScenarioTracer scenarioTracer = new ScenarioTracer(stitchEngine);

        String testCaseName = "test-case-" + UUID.randomUUID();
        scenarioTracer.beginTestCase(testCaseName);

        scenarioTracer.endTestCase(testCaseName);

    }


}