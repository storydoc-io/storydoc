package io.storydoc.stitch;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CodeExecutionTracer {

    private StitchEngine stitchEngine;

    public void methodEnter(String cid, String declaringTypeName, String functionName) {
        String value = getValue(cid, declaringTypeName, functionName);
        stitchEngine.add("CodeExecution", "MethodCalled", value);
    }

    public void methodReturn(String cid, String declaringTypeName, String functionName) {
        String value = getValue(cid, declaringTypeName, functionName);
        stitchEngine.add("CodeExecution", "MethodReturn", value);
    }

    private String getValue(String cid, String declaringTypeName, String functionName) {
        JSONWriter json = new JSONWriter();
        json.obj();
        json.att("cid");
        json.string(cid);

        json.att("typeName");
        json.string(declaringTypeName);

        json.att("functionName");
        json.string(functionName);
        json.end();

        return json.getValue();
    }
}
