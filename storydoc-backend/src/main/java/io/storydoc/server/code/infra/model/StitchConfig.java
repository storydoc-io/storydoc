package io.storydoc.server.code.infra.model;

import io.storydoc.server.code.domain.StitchConfigId;
import io.storydoc.server.storydoc.domain.Artifact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StitchConfig implements Artifact {

    private StitchConfigId id;
    private String dir;

}
