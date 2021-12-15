package io.storydoc.server.code.app;

import io.storydoc.server.code.infra.EnterTraceLine;
import io.storydoc.server.code.infra.ExitTraceLine;
import io.storydoc.server.code.infra.LogFileScanner;
import io.storydoc.server.code.infra.TraceLine;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodeService {

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

}
