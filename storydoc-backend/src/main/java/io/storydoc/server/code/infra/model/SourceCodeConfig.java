package io.storydoc.server.code.infra.model;

import io.storydoc.server.code.domain.SourceCodeConfigId;
import io.storydoc.server.storydoc.domain.Artifact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourceCodeConfig implements Artifact {

    private SourceCodeConfigId id;
    private List<String> dirs;

}
