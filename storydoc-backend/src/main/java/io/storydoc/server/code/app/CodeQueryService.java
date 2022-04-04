package io.storydoc.server.code.app;

import io.storydoc.server.code.domain.SourceCodeConfigCoordinate;
import io.storydoc.server.code.domain.SourceCodeConfigStorage;
import io.storydoc.server.code.infra.model.SourceCodeConfig;
import org.springframework.stereotype.Service;

@Service
public class CodeQueryService {

    private final SourceCodeConfigStorage sourceCodeConfigStorage;

    public CodeQueryService(SourceCodeConfigStorage sourceCodeConfigStorage) {
        this.sourceCodeConfigStorage = sourceCodeConfigStorage;
    }

    public SourceCodeConfigDTO getSourceCodeConfig(SourceCodeConfigCoordinate coordinate) {
        SourceCodeConfig sourceCodeConfig = sourceCodeConfigStorage.load(coordinate);
        return SourceCodeConfigDTO.builder()
                .id(sourceCodeConfig.getId())
                .dirs(sourceCodeConfig.getDirs())
                .build();
    }

}
