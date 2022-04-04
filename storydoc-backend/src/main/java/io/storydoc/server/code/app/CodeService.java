package io.storydoc.server.code.app;

import io.storydoc.server.code.domain.*;
import io.storydoc.server.code.infra.EnterTraceLine;
import io.storydoc.server.code.infra.ExitTraceLine;
import io.storydoc.server.code.infra.LogFileScanner;
import io.storydoc.server.code.infra.TraceLine;
import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodeService {

    private final IDGenerator idGenerator;

    private final CodeDomainService domainService;

    private final SourceCodeAccess sourceCodeAccess;

    public CodeService(IDGenerator idGenerator, CodeDomainService codeDomainService, SourceCodeAccess sourceCodeAccess) {
        this.idGenerator = idGenerator;
        this.domainService = codeDomainService;
        this.sourceCodeAccess = sourceCodeAccess;
    }


    public CodeTraceDTO getExecution() {
        InputStream inputstream = this.getClass().getResourceAsStream("/json-log-file.log");

        LogFileScanner logFileScanner = new LogFileScanner();
        List<TraceLine> logLines = logFileScanner.scan(inputstream);

        List<CodeTraceItemDTO> items = extractItems(logLines);

        return CodeTraceDTO.builder()
            .items(items)
            .build();
    }

    private List<CodeTraceItemDTO> extractItems(List<TraceLine> traceLines) {
        return traceLines.stream()
            .map(traceLine -> {
                if (traceLine instanceof EnterTraceLine) {
                    EnterTraceLine enterTraceLine = (EnterTraceLine) traceLine;
                    return CodeTraceItemDTO.builder()
                            .type("ENTER")
                            .threadName(enterTraceLine.getThreadName())
                            .cid(enterTraceLine.getCid())
                            .className(enterTraceLine.getClassName())
                            .methodName(enterTraceLine.getMethodName())
                            .params(enterTraceLine.getParams())
                            .build();
                } else {
                    ExitTraceLine exitTraceLine = (ExitTraceLine) traceLine;
                    return CodeTraceItemDTO.builder()
                            .type("EXIT")
                            .cid(exitTraceLine.getCid())
                            .resultValue(exitTraceLine.getReturnValue())
                            .build();
                }
            })
            .collect(Collectors.toList());
    }

    public CodeExecutionCoordinate createCodeExecution(BlockCoordinate blockCoordinate, String name) {
        CodeExecutionId codeExecutionId = new CodeExecutionId(idGenerator.generateID(CodeExecutionId.ID_PREFIX));
        CodeExecutionCoordinate coordinate = CodeExecutionCoordinate.of(blockCoordinate, codeExecutionId);
        domainService.createCodeExecution(coordinate, name);
        return coordinate;
    }

    public SourceCodeDTO getSource(String className) {
        return SourceCodeDTO.builder()
                .lines(sourceCodeAccess.getSource(className))
                .build();
    }

    public SourceCodeConfigCoordinate createSourceCodeConfig(BlockCoordinate blockCoordinate, String name) {
        SourceCodeConfigId codeExecutionId = new SourceCodeConfigId(idGenerator.generateID(SourceCodeConfigId.ID_PREFIX));
        SourceCodeConfigCoordinate coordinate = SourceCodeConfigCoordinate.of(blockCoordinate, codeExecutionId);
        domainService.createSourceCodeConfig(coordinate, name);
        return coordinate;
    }
}
