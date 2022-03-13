package io.storydoc.server.ui.infra.json.screendesign;

import io.storydoc.server.storydoc.domain.Artifact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDesign implements Artifact {
    private String id;
    private Container rootContainer;
}
