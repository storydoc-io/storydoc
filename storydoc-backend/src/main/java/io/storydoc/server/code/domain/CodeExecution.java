package io.storydoc.server.code.domain;

import lombok.Builder;

@Builder
public class CodeExecution {
    public static final String ARTIFACT_TYPE = CodeExecution.class.getName();;
    private CodeExecutionId codeExecutionId;
    private SourceCodeConfigStorage storage;
    private CodeStorage codeStorage;

    public void setStitchDetails(CodeExecutionCoordinate coordinate, String stitchFile, String testClass, String testMethod) {
        codeStorage.setStitchDetails(coordinate, stitchFile, testClass, testMethod);
    }
}
