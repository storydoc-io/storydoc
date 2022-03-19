package io.storydoc.stitch;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CodeExecutionTracer {

    private StitchEngine stitchEngine;

    public boolean isActive(){
        return stitchEngine.isActive();
    }

    public void enter(String declaringTypeName, String name, int lineNr) {
        stitchEngine.add(String.format("%s %s %d ", declaringTypeName, name, lineNr));
    }
}
