package io.storydoc.server.code.domain;

public interface CodeStorage {
    void createCodeExecution(CodeExecutionCoordinate coordinate, String name);
}
