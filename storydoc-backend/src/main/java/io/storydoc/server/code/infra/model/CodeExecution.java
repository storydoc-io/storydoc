package io.storydoc.server.code.infra.model;

import io.storydoc.server.code.domain.CodeExecutionId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeExecution {

    private CodeExecutionId id;

}
