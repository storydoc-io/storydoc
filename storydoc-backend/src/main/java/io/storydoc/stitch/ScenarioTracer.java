package io.storydoc.stitch;

public class ScenarioTracer {

    private StitchEngine stitchEngine;

    public ScenarioTracer(StitchEngine stitchEngine) {
        this.stitchEngine = stitchEngine;
    }

    public void beginTestCase(String testCaseName) {
        stitchEngine.setActive(true);
        stitchEngine.add(testCaseName);
    }

    public void endTestCase(String testCaseName) {
        stitchEngine.add(testCaseName);
        stitchEngine.setActive(false);
    }

}
