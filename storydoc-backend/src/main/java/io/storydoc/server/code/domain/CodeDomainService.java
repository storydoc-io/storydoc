package io.storydoc.server.code.domain;

import org.springframework.stereotype.Service;

@Service
public class CodeDomainService {

    private final CodeStorage codeStorage;

    private final SourceCodeConfigStorage sourceCodeConfigStorage;

    private final StitchConfigStorage stitchConfigStorage;

    public CodeDomainService(CodeStorage codeStorage, SourceCodeConfigStorage sourceCodeConfigStorage,  StitchConfigStorage stitchConfigStorage) {
        this.codeStorage = codeStorage;
        this.sourceCodeConfigStorage = sourceCodeConfigStorage;
        this.stitchConfigStorage = stitchConfigStorage;
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

    public StitchConfig getStitchConfig(StitchConfigCoordinate coordinate) {
        return StitchConfig.builder()
                .sourceCodeConfigId(coordinate.getStitchConfigId())
                .storage(stitchConfigStorage)
                .build();
    }
    
    public CodeExecution getCodeExecution(CodeExecutionCoordinate codeExecutionCoordinate) {
        return CodeExecution.builder()
                .codeExecutionId(codeExecutionCoordinate.getCodeExecutionId())
                .codeStorage(codeStorage)
                .storage(sourceCodeConfigStorage)
                .build();
    }

    public void createStitchConfig(StitchConfigCoordinate coordinate, String name) {
        stitchConfigStorage.createStitchConfig(coordinate, name);
    }
}
