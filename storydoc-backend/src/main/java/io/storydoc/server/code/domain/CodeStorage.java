package io.storydoc.server.code.domain;

import io.storydoc.server.code.infra.model.CodeExecution;

public interface CodeStorage {
    void createCodeExecution(CodeExecutionCoordinate coordinate, String name);
    void setSourceConfig(CodeExecutionCoordinate codeExecutionCoordinate, SourceCodeConfigCoordinate sourceCodeConfigCoordinate);
    void setStitchDetails(CodeExecutionCoordinate coordinate, String stitchFile, String testClass, String testMethod);
    CodeExecution load(CodeExecutionCoordinate coordinate);
}
