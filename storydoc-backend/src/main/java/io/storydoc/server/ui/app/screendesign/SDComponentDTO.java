package io.storydoc.server.ui.app.screendesign;

import io.storydoc.server.ui.domain.screendesign.SDComponentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SDComponentDTO {
    private String name;
    private String type;
    private String label;
    private int x;
    private int y;
    private SDComponentId id;
    private List<ComponentAttributeDTO> attributes;
}
