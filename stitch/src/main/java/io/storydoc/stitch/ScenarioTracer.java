package io.storydoc.stitch;

public class ScenarioTracer {

    private StitchEngine stitchEngine;

    public ScenarioTracer(StitchEngine stitchEngine) {
        this.stitchEngine = stitchEngine;
    }

    public void beginTestCase(String testCaseName) {
        stitchEngine.setActive(true);
        JSONWriter json = new JSONWriter();
        json.obj();
        json.att("testCaseName");
        json.string(testCaseName);

        stitchEngine.add("TestScenario", "TestEntered", json.getValue());
    }

    public void endTestCase(String testCaseName) {
        JSONWriter json = new JSONWriter();
        json.obj();
        json.att("testCaseName");
        json.string(testCaseName);

        stitchEngine.add("TestScenario", "TestFinished", json.getValue());
        stitchEngine.setActive(false);
    }

    public void bdd(String noun, String text) {
        stitchEngine.setActive(true);
        JSONWriter json = new JSONWriter();
        json.obj();
        json.att("noun");
        json.string(noun);
        json.att("text");
        json.string(text);
        json.end();

        stitchEngine.add("TestScenario", "given", json.getValue());
    }



}
