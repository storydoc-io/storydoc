package io.storydoc.stitch;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CodeExecutionTracer {

    private StitchEngine stitchEngine;

    public boolean isActive(){
        return stitchEngine.isActive();
    }

    public void enter(String declaringTypeName, String functionName) {
        JSONWriter json = new JSONWriter();
        json.obj();
            json.att("typeName");
            json.string(declaringTypeName);

            json.att("functionName");
            json.string(functionName);
        json.end();

        stitchEngine.add("CodeExecution", "MethodCalled", json.getValue());
    }
}
