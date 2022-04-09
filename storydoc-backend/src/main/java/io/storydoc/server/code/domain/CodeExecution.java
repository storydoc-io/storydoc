package io.storydoc.server.code.domain;

import lombok.Builder;

@Builder
public class CodeExecution {
    public static final String ARTIFACT_TYPE = CodeExecution.class.getName();;
    private CodeExecutionId codeExecutionId;
    private SourceCodeConfigStorage storage;
    private CodeStorage codeStorage;

    public void setSourceConfig(CodeExecutionCoordinate codeExecutionCoordinate, SourceCodeConfigCoordinate sourceCodeConfigCoordinate) {
        codeStorage.setSourceConfig(codeExecutionCoordinate, sourceCodeConfigCoordinate);
    }

    public void setStitchDetails(CodeExecutionCoordinate coordinate, String stitchFile, String lineFrom, String lineTo) {
        codeStorage.setStitchDetails(coordinate, stitchFile, lineFrom, lineTo);
    }
}
