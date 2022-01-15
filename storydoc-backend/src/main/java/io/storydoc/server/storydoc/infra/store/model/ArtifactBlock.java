package io.storydoc.server.storydoc.infra.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactBlock extends Block {

    private List<Artifact> artifacts;

}
