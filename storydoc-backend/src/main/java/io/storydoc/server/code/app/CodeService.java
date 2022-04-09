package io.storydoc.server.code.app;

import io.storydoc.server.code.domain.*;
import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import org.springframework.stereotype.Service;

@Service
public class CodeService {

    private final IDGenerator idGenerator;

    private final CodeDomainService domainService;

    private final SourceCodeAccess sourceCodeAccess;

    private final SourceCodeConfigStorage sourceCodeConfigStorage;

    public CodeService(IDGenerator idGenerator, CodeDomainService codeDomainService, SourceCodeAccess sourceCodeAccess, SourceCodeConfigStorage sourceCodeConfigStorage) {
        this.idGenerator = idGenerator;
        this.domainService = codeDomainService;
        this.sourceCodeAccess = sourceCodeAccess;
        this.sourceCodeConfigStorage = sourceCodeConfigStorage;
    }

    public CodeExecutionCoordinate createCodeExecution(BlockCoordinate blockCoordinate, String name) {
        CodeExecutionId codeExecutionId = new CodeExecutionId(idGenerator.generateID(CodeExecutionId.ID_PREFIX));
        CodeExecutionCoordinate coordinate = CodeExecutionCoordinate.of(blockCoordinate, codeExecutionId);
        domainService.createCodeExecution(coordinate, name);
        return coordinate;
    }

    public void setStitchDetails(CodeExecutionCoordinate coordinate, String stitchFile, String lineFrom, String lineTo) {
        CodeExecution codeExecution = domainService.getCodeExecution(coordinate);
        codeExecution.setStitchDetails(coordinate, stitchFile, lineFrom, lineTo);
    }

    public SourceCodeDTO getSource(String className, SourceCodeConfigCoordinate configCoord) {
        io.storydoc.server.code.infra.model.SourceCodeConfig sourceCodeConfig  = sourceCodeConfigStorage.load(configCoord);
        return SourceCodeDTO.builder()
                .lines(sourceCodeAccess.getSource(className, sourceCodeConfig.getDirs()))
                .build();
    }

    public SourceCodeConfigCoordinate createSourceCodeConfig(BlockCoordinate blockCoordinate, String name) {
        SourceCodeConfigId codeExecutionId = new SourceCodeConfigId(idGenerator.generateID(SourceCodeConfigId.ID_PREFIX));
        SourceCodeConfigCoordinate coordinate = SourceCodeConfigCoordinate.of(blockCoordinate, codeExecutionId);
        domainService.createSourceCodeConfig(coordinate, name);
        return coordinate;
    }

    public void setSourcePath(SourceCodeConfigCoordinate coordinate, String path) {
        SourceCodeConfig sourceCodeConfig = domainService.getSourceCodeConfig(coordinate);
        sourceCodeConfig.setSourcePath(coordinate, path);
    }

    public void setSourceConfigForExecution(CodeExecutionCoordinate codeExecutionCoordinate, SourceCodeConfigCoordinate sourceCodeConfigCoordinate) {
        CodeExecution codeExecution = domainService.getCodeExecution(codeExecutionCoordinate);
        codeExecution.setSourceConfig(codeExecutionCoordinate, sourceCodeConfigCoordinate);
    }

 }
