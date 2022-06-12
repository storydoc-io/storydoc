package io.storydoc.stitch;

public class ScenarioTracer {

    private StitchEngine stitchEngine;

    public ScenarioTracer(StitchEngine stitchEngine) {
        this.stitchEngine = stitchEngine;
    }

    public void beginTestCase(String... nameFragments) {
        stitchEngine.beginTestCase(nameFragments);
    }

    public void endTestCase(String... nameFragments) {
        stitchEngine.endTestCase();
    }

    public void bdd(String noun, String text) {
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
