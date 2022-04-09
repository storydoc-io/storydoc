package io.storydoc.server.code.infra.model;

import io.storydoc.server.code.domain.CodeExecutionId;
import io.storydoc.server.storydoc.domain.Artifact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeExecution implements Artifact {

    private CodeExecutionId id;
    private String stitchFile;
    private String lineFrom;
    private String lineTo;

}
