package io.storydoc.server.infra;

import io.storydoc.stitch.CodeExecutionTracer;
import io.storydoc.stitch.ScenarioTracer;
import io.storydoc.stitch.StitchConfig;
import io.storydoc.stitch.StitchEngine;

public class StitchFactory {

    public static StitchFactory INSTANCE = new StitchFactory();

    private StitchConfig config;

    private StitchEngine stitchEngine;

    private CodeExecutionTracer codeExecutionTracer;

    private ScenarioTracer scenarioTracer;

    private StitchFactory() {
         config = StitchConfig.DEFAULT;
         stitchEngine = new StitchEngine(config);

         codeExecutionTracer = new CodeExecutionTracer(stitchEngine);

         scenarioTracer = new ScenarioTracer(stitchEngine);
    }

    static public CodeExecutionTracer getCodeExecutionTracer() {
        return INSTANCE.codeExecutionTracer;
    }

    static public ScenarioTracer getScenarioTracer() {
        return INSTANCE.scenarioTracer;
    }

}
