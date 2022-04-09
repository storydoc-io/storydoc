package io.storydoc.server.code.domain;

import org.springframework.stereotype.Service;

@Service
public class CodeDomainService {

    private final CodeStorage codeStorage;

    private final SourceCodeConfigStorage sourceCodeConfigStorage;

    public CodeDomainService(CodeStorage codeStorage, SourceCodeConfigStorage sourceCodeConfigStorage) {
        this.codeStorage = codeStorage;
        this.sourceCodeConfigStorage = sourceCodeConfigStorage;
    }

    public void createCodeExecution(CodeExecutionCoordinate coordinate, String name) {
        codeStorage.createCodeExecution(coordinate, name);
    }

    public void createSourceCodeConfig(SourceCodeConfigCoordinate coordinate, String name) {
        sourceCodeConfigStorage.createSourceCodeConfig(coordinate, name);
    }

    public SourceCodeConfig getSourceCodeConfig(SourceCodeConfigCoordinate coordinate) {
        return SourceCodeConfig.builder()
                .sourceCodeConfigId(coordinate.getSourceCodeConfigId())
                .storage(sourceCodeConfigStorage)
                .build();
    }

    public CodeExecution getCodeExecution(CodeExecutionCoordinate codeExecutionCoordinate) {
        return CodeExecution.builder()
                .codeExecutionId(codeExecutionCoordinate.getCodeExecutionId())
                .codeStorage(codeStorage)
                .storage(sourceCodeConfigStorage)
                .build();
    }
}
