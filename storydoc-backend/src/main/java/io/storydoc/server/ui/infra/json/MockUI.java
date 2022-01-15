package io.storydoc.server.ui.infra.json;

import io.storydoc.server.storydoc.domain.Artifact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MockUI implements Artifact {

    private String id;
    private List<Screenshot> screenshots;

}
