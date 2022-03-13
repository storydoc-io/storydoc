package io.storydoc.server.code.domain;

import org.springframework.stereotype.Service;

@Service
public class CodeDomainService {

    private final CodeStorage codeStorage;

    public CodeDomainService(CodeStorage codeStorage) {
        this.codeStorage = codeStorage;
    }

    public void createCodeExecution(CodeExecutionCoordinate coordinate, String name) {
        codeStorage.createCodeExecution(coordinate, name);
    }
}
